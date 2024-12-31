package cn.iocoder.yudao.module.system.service.jeelowcode;

import cn.hutool.core.util.ObjectUtil;
import cn.iocoder.yudao.module.system.dal.dataobject.permission.MenuDO;
import cn.iocoder.yudao.module.system.service.permission.MenuService;
import cn.iocoder.yudao.module.system.service.permission.PermissionService;
import com.jeelowcode.module.api.JeeLowCodeApi;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * 低代码权限判断处理
 */
@Service
@Slf4j
public class JeeLowCodePermissionServiceImpl implements IJeeLowCodePermissionService {

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @Resource
    private PermissionService permissionService;

    @Resource
    private MenuService menuService;

    @Autowired
   private JeeLowCodeApi jeeLowCodeApi;


    static List<String> jeeLowCodePrePermissionList_dbform = new ArrayList<>();
    static List<String> jeeLowCodePrePermissionList_report = new ArrayList<>();

    //报表多个查询
    static {
        jeeLowCodePrePermissionList_dbform.add("jeelowcode:dbform-data:query:");//列表
        jeeLowCodePrePermissionList_dbform.add("jeelowcode:dbform-data:create:");//保存
        jeeLowCodePrePermissionList_dbform.add("jeelowcode:dbform-data:update:");//编辑
        jeeLowCodePrePermissionList_dbform.add("jeelowcode:dbform-data:delete:");//删除
        jeeLowCodePrePermissionList_dbform.add("jeelowcode:dbform-data:export:");//导出
        jeeLowCodePrePermissionList_dbform.add("jeelowcode:dbform-data:import:");//导入

        jeeLowCodePrePermissionList_report.add("jeelowcode:report-data:query:");//列表
        jeeLowCodePrePermissionList_report.add("jeelowcode:report-data:export:");//导出
    }

    /**
     * 校验是否有权限
     *
     * @param userId
     * @param permission
     * @return true=有权限  false =无权限,继续走系统自带的
     */
    @Override
    public boolean hasAnyPermission(Long userId, Set<Long> roleIds, String permission) {
        if (ObjectUtil.isEmpty(permission)) {
            return false;
        }
        boolean skipFlag=true;
        Long dbFormId = null;
        for (String preStr : jeeLowCodePrePermissionList_dbform) {
            if (permission.startsWith(preStr)) {
                dbFormId = Long.valueOf(permission.replace(preStr, ""));
                skipFlag=false;
                break;
            }
        }

        List<String> reportCodeList=null;//报表code列
        for (String preStr : jeeLowCodePrePermissionList_report) {
            if (permission.startsWith(preStr)) {
                String reportCodeStr = permission.replace(preStr, "");
                reportCodeList = Pattern.compile(",")
                        .splitAsStream(reportCodeStr)
                        .collect(Collectors.toList());
                skipFlag=false;
                break;
            }
        }





        //说明不是在本次拦截范围内
        if (skipFlag) {
            return false;
        }
        //根据角色id来判断我是否是开发者，如果有开发者，则说明直接可以访问
        boolean devUserFlag=false;
        String isDevUserRedisKey="JEE_LOW_CODE:USER:"+userId+":DEVUSER";
        if(!stringRedisTemplate.hasKey(isDevUserRedisKey)){//没有数据，则查询获取 TODO 优化
            //所有菜单列表
            Set<Long> menuIdList = permissionService.getRoleMenuListByRoleId(roleIds);
            if(ObjectUtil.isNotEmpty(menuIdList)){
                List<MenuDO> menuList = menuService.getMenuList(menuIdList);//菜单列表
                for(MenuDO menuDO:menuList){
                    String component = menuDO.getComponent();
                    if(ObjectUtil.isNotEmpty(component) && ObjectUtil.equal(component,"lowdesign/tableDesign/index")){
                        devUserFlag=true;//说明当前是开发人员
                        break;
                    }
                }
            }
            String redisVal=devUserFlag?"true":"false";
            stringRedisTemplate.opsForValue().set(isDevUserRedisKey, redisVal, 300, TimeUnit.SECONDS);//5分钟
            devUserFlag =ObjectUtil.equal(redisVal,"true");
        }else{
            String redisVal = stringRedisTemplate.opsForValue().get(isDevUserRedisKey);
            devUserFlag =ObjectUtil.equal(redisVal,"true");
        }
        if(devUserFlag){//开发者，则直接可以访问
            return true;
        }


        //表单开发
        if(ObjectUtil.isNotEmpty(dbFormId)){
            String authType = jeeLowCodeApi.getDbformAuthType(dbFormId);
            if(ObjectUtil.isEmpty(authType)){
                return false;
            }
            //查找缓存，获取类型
            switch (authType) {
                case "authOpen"://不登录可查询，直接放开，说明可以访问
                    return true;
                case "authTrue"://说明强制校验，则走系统自带的校验，全匹配
                    return false;
                case "authFalse"://说明，只需要登录，即可访问
                    return true;
                default:
                    return false;//默认走系统自带
            }

        }else if(ObjectUtil.isNotEmpty(reportCodeList)){//数据报表
            for(String reportCode:reportCodeList){
                String authType = jeeLowCodeApi.getReportAuthType(reportCode);

                if(ObjectUtil.isEmpty(authType)){
                    return false;
                }
                //查找缓存，获取类型
                switch (authType) {
                    case "authTrue"://说明强制校验，则走系统自带的校验，全匹配
                        return false;
                }
            }
            return true;
        }

        return false;

    }

    /**
     * 转为list
     * @param permission
     * @return
     */
    @Override
    public List<String> permission2List(String permission){
        if (!permission.startsWith("jeelowcode:report-data:query:")) {
            List<String> permissionList=new ArrayList<>();
            permissionList.add(permission);
            return permissionList;
        }

        String reportCodeListStr = permission.replace("jeelowcode:report-data:query:", "");
        List<String> list = Arrays.asList(reportCodeListStr.split(","));

        List<String> prefixedList = list.stream()
                .map(item -> "jeelowcode:report-data:query:" + item)
                .collect(Collectors.toList());
        return prefixedList;
    }
}
