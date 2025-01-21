/*					Apache License
                           Version 2.0, January 2004
                        http://www.apache.org/licenses/
			本软件受适用的国家软件著作权法（包括国际条约）和开源协议 双重保护许可。

			开源协议中文释意如下：
			1.JeeLowCode开源版本无任何限制，在遵循本开源协议（Apache2.0）条款下，允许商用使用，不会造成侵权行为。
			2.允许基于本平台软件开展业务系统开发。
			3.在任何情况下，您不得使用本软件开发可能被认为与本软件竞争的软件。

			最终解释权归：http://www.jeelowcode.com*/
/*
Apache License
Version 2.0, January 2004
http://www.apache.org/licenses/
本软件受适用的国家软件著作权法（包括国际条约）和开源协议 双重保护许可。

开源协议中文释意如下：
1.JeeLowCode开源版本无任何限制，在遵循本开源协议（Apache2.0）条款下，允许商用使用，不会造成侵权行为。
2.允许基于本平台软件开展业务系统开发。
3.在任何情况下，您不得使用本软件开发可能被认为与本软件竞争的软件。

最终解释权归：http://www.jeelowcode.com
*/
package com.jeelowcode.core.framework.adapter;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.iocoder.yudao.framework.security.core.LoginUser;
import cn.iocoder.yudao.framework.security.core.util.SecurityFrameworkUtils;
import cn.iocoder.yudao.framework.tenant.config.TenantProperties;
import cn.iocoder.yudao.framework.tenant.core.context.TenantContextHolder;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.jeelowcode.core.framework.mapper.AdapterMapper;
import com.jeelowcode.core.framework.utils.Func;
import com.jeelowcode.core.framework.utils.FuncWeb;
import com.jeelowcode.framework.plus.SqlHelper;
import com.jeelowcode.framework.plus.build.buildmodel.wrapper.SqlInfoQueryWrapper;
import com.jeelowcode.framework.utils.adapter.IJeeLowCodeAdapter;
import com.jeelowcode.framework.utils.enums.DefaultDbFieldEnum;
import com.jeelowcode.framework.utils.model.*;
import com.jeelowcode.framework.utils.params.JeeLowCodeDeptParam;
import com.jeelowcode.framework.utils.params.JeeLowCodeDictParam;
import com.jeelowcode.framework.utils.params.JeeLowCodeUserParam;
import com.jeelowcode.framework.utils.utils.FuncBase;
import com.jeelowcode.framework.utils.utils.JeeLowCodeUtils;
import com.jeelowcode.core.framework.service.IFrameSqlService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.function.Consumer;
import java.util.stream.Collectors;

/**
 * 芋道源码适配器
 */
@Service
@Slf4j
public class YudaoAdapter implements IJeeLowCodeAdapter {

    @Autowired
    private IFrameSqlService sqlService;

    @Autowired
    private TenantProperties tenantProperties;

    @Autowired
    private AdapterMapper adapterMapper;

    //获取当前在线人id
    @Override
    public Long getOnlineUserId() {
        Long loginUserId = SecurityFrameworkUtils.getLoginUserId();
        return loginUserId;
    }

    //获取当前在线人账号
    @Override
    public String getOnlineUserName() {
        Long loginUserId = SecurityFrameworkUtils.getLoginUserId();
        String userName = adapterMapper.getUserName(loginUserId);
        return userName;
    }

    @Override
    public String getOnlineUserNickName() {
        Long loginUserId = SecurityFrameworkUtils.getLoginUserId();
        return adapterMapper.getNickname(loginUserId);
    }

    @Override
    public Long getOnlineUserDeptId() {
        Long loginUserId = SecurityFrameworkUtils.getLoginUserId();
        Long deptId = adapterMapper.getDeptId(loginUserId);
        return deptId;
    }

    //获取当前在线人id
    @Override
    public Long getTenantId() {
        //当前线程
        Long tenantId = TenantContextHolder.getTenantId();
        if (Func.isNotEmpty(tenantId) && tenantId > 0) {
            return tenantId;
        }
        //当前线程没有租户信息，则从登录用户获取
        LoginUser loginUser = SecurityFrameworkUtils.getLoginUser();
        if (FuncBase.isNotEmpty(loginUser)) {
            return loginUser.getTenantId();
        }
        return -1L;
    }

    //获取部门id
    @Override
    public Long getDeptId(Long userId){
        Long deptId = adapterMapper.getDeptId(userId);
        return deptId;
    }

    //判断请求是否排除多租户
    @Override
    public boolean getTenantIsIgnore() {
        HttpServletRequest request = FuncWeb.getRequest();
        if (!Func.isJeeLowCodeUrl(request)) {//低代码平台有自己一套租户逻辑
            return false;
        }

        boolean ignore = TenantContextHolder.isIgnore();
        return ignore;
    }

    //获取所有租户列表
    @Override
    public List<JeeLowTenant> getTenantList() {
        List<String> selectList = new ArrayList<>();
        selectList.add("id");
        selectList.add("name");

        SqlInfoQueryWrapper.Wrapper wrapper = SqlHelper.getQueryWrapper()
                .setTableName("system_tenant")
                .select(selectList, true)
                .setWhere(where -> {
                    where.eq("deleted", 0);
                    where.eq("status", 0);
                }).build();
        List<Map<String, Object>> tenantList = sqlService.getDataListByPlus(wrapper);

        return tenantList.stream()
                .map(tenantMap -> {
                    String id = JeeLowCodeUtils.getMap2Str(tenantMap, "id");
                    String name = JeeLowCodeUtils.getMap2Str(tenantMap, "name");
                    JeeLowTenant jeeLowTenant = new JeeLowTenant();
                    jeeLowTenant.setTenantId(id);
                    jeeLowTenant.setTenantName(name);
                    return jeeLowTenant;
                })
                .collect(Collectors.toList());
    }

    //获取字典列表
    @Override
    public List<JeeLowCodeDict> getDictList(JeeLowCodeDictParam param) {
        List<JeeLowCodeDict> resultList = new ArrayList<>();
        List<String> dictCodeList = param.getDictCodeList();
        Map<String, JeeLowCodeDictParam.DictCodeDataParam> paramMap = FuncBase.isEmpty(param.getParamMap()) ? new HashMap<>() : param.getParamMap();

        SqlInfoQueryWrapper.Wrapper wrapper = SqlHelper.getQueryWrapper()
                .setTableName("system_dict_type")
                .setWhere(where -> {
                    where.eq("deleted", 0);
                    where.eq("status", 0);
                    where.in("type", dictCodeList);
                }).build();
        List<Map<String, Object>> typeList = sqlService.getDataListByPlus(wrapper);

        typeList.stream()
                .map(typeMap -> {
                    String code = JeeLowCodeUtils.getMap2Str(typeMap, "type");
                    JeeLowCodeDictParam.DictCodeDataParam dictCodeDataParam = paramMap.get(code);

                    List<String> dictDataLabelList = FuncBase.isNotEmpty(dictCodeDataParam) ? dictCodeDataParam.getDictDataLabelList() : Collections.emptyList();
                    List<String> dictDataValueList = FuncBase.isNotEmpty(dictCodeDataParam) ? dictCodeDataParam.getDictDataValueList() : Collections.emptyList();

                    SqlInfoQueryWrapper.Wrapper wrapperData = SqlHelper.getQueryWrapper()
                            .setTableName("system_dict_data")
                            .select("value", "label")
                            .setWhere(where -> {
                                where.eq("deleted", 0);
                                where.eq("status", 0);
                                where.eq("dict_type", code);
                                where.in("label", dictDataLabelList);
                                where.in("value", dictDataValueList);
                            })
                            .orderByAsc("sort")
                            .build();
                    List<Map<String, Object>> dataList = sqlService.getDataListByPlus(wrapperData);

                    List<JeeLowCodeDict.DictData> list = dataList.stream()
                            .map(tmpMap -> {
                                String value = JeeLowCodeUtils.getMap2Str(tmpMap, "value");
                                String label = JeeLowCodeUtils.getMap2Str(tmpMap, "label");

                                JeeLowCodeDict.DictData data = new JeeLowCodeDict.DictData();
                                data.setLabel(label);
                                data.setVal(value);
                                return data;
                            })
                            .collect(Collectors.toList());

                    JeeLowCodeDict dict = new JeeLowCodeDict();
                    dict.setDataList(list);
                    dict.setDictCode(code);
                    return dict;
                })
                .forEach(resultList::add);

        return resultList;
    }

    //获取部门列表
    @Override
    public List<JeeLowCodeDept> getDeptList(JeeLowCodeDeptParam param) {

        String deptName = param.getDeptName();//部门名称搜索
        String type = Func.isEmpty(param.getType()) ? "all" : param.getType();
        List<Long> deptIdList = new ArrayList<>();//部门id列表,为空则查所有
        if (Func.equals(type, "now")) {//本级
            deptIdList.add(this.getOnlineUserDeptId());
        } else if (Func.equals(type, "sub")) {//本级以及下级
            deptIdList = this.getChildDeptIdList(this.getOnlineUserDeptId());
        }

        List<Long> finalDeptIdList = deptIdList;
        SqlInfoQueryWrapper.Wrapper wrapper = SqlHelper.getQueryWrapper()
                .select("id", "parent_id", "name")
                .setTableName("system_dept")
                .setWhere(where -> {
                    where.in(Func.isNotEmpty(finalDeptIdList), "id", finalDeptIdList);
                    where.like(FuncBase.isNotEmpty(deptName), "name", deptName);
                    where.eq("deleted", 0);
                })
                .orderByAsc("sort")
                .build();
        List<Map<String, Object>> deptList = sqlService.getDataListByPlus(wrapper);

        List<JeeLowCodeDept> resultList = deptList.stream()
                .map(deptMap -> {
                    String id = JeeLowCodeUtils.getMap2Str(deptMap, "id");
                    String pid = JeeLowCodeUtils.getMap2Str(deptMap, "parent_id");
                    String name = JeeLowCodeUtils.getMap2Str(deptMap, "name");

                    JeeLowCodeDept dept = new JeeLowCodeDept();
                    dept.setDeptId(id);
                    dept.setDeptPid(pid);
                    dept.setDeptName(name);
                    return dept;
                })
                .collect(Collectors.toList());
        return resultList;
    }

    //获取所有角色列表
    @Override
    public List<JeeLowCodeRole> getRoleList() {
        Long tenantId = TenantContextHolder.getTenantId();
        SqlInfoQueryWrapper.Wrapper wrapper = SqlHelper.getQueryWrapper()
                .select("id", "name")
                .setTableName("system_role")
                .setWhere(where -> {
                    where.in("deleted", 0);
                    where.eq("tenant_id", tenantId);
                })
                .orderByAsc("sort")
                .build();
        List<Map<String, Object>> roleList = sqlService.getDataListByPlus(wrapper);

        List<JeeLowCodeRole> resultList = roleList.stream()
                .map(roleMap -> {
                    String id = JeeLowCodeUtils.getMap2Str(roleMap, "id");
                    String name = JeeLowCodeUtils.getMap2Str(roleMap, "name");

                    JeeLowCodeRole role = new JeeLowCodeRole();
                    role.setRoleId(id);
                    role.setRoleName(name);
                    return role;
                })
                .collect(Collectors.toList());
        return resultList;
    }

    //回显用户
    @Override
    public List<Map<String, Object>> getUserViewList(List<Long> userIdList) {
        SqlInfoQueryWrapper.Wrapper wrapper = SqlHelper.getQueryWrapper()
                .select("id", "nickname")
                .setTableName("system_users")
                .setWhere(where -> {
                    where.in("id", userIdList);
                })
                .build();
        List<Map<String, Object>> dataList = sqlService.getDataListByPlus(wrapper);
        return dataList;
    }

    //回显部门
    @Override
    public List<Map<String, Object>> getDeptViewList(List<Long> deptIdList) {
        SqlInfoQueryWrapper.Wrapper wrapper = SqlHelper.getQueryWrapper()
                .select("id", "name")
                .setTableName("system_dept")
                .setWhere(where -> {
                    where.in("id", deptIdList);
                })
                .build();
        List<Map<String, Object>> dataList = sqlService.getDataListByPlus(wrapper);
        return dataList;
    }


    //获取用户分页列表
    @Override
    public Object getUserPage(Integer pageNo, Integer pageSize, JeeLowCodeUserParam param) {
        Page page = FuncWeb.getPage(pageNo, pageSize);
        Long deptId = param.getDeptId();
        Long roleId = param.getRoleId();
        String type = param.getType();//all  now sub

        String nickName = param.getNickName();
        String mobile = param.getMobile();
        List<String> fieldList = param.getFieldList();

        List<Long> deptIdList = new ArrayList<>();//部门id列表
        if (Func.isEmpty(deptId)) {//部门为空的时候，type才生效
            if (Func.equals(type, "now")) {//本级
                deptIdList.add(this.getOnlineUserDeptId());
            } else if (Func.equals(type, "sub")) {//本级以及下级
                deptIdList = this.getChildDeptIdList(this.getOnlineUserDeptId());
            }
        } else {//指定部门
            deptIdList.add(deptId);
        }


        Map<String, Object> params = new HashMap<>();
        params.put("nickName", nickName);
        params.put("mobile", mobile);

        IPage<JeeLowCodeUser> pages = null;
        if (Func.isNotEmpty(roleId)) {//说明有选择了角色
            pages = adapterMapper.getUserPageByRoleId(getTenantId(),page, roleId, deptIdList, params);
        } else {
            pages = adapterMapper.getUserPageByDeptId(getTenantId(),page, deptIdList, params);
        }
        if (Func.isEmpty(fieldList)) {//查所有
            return pages;
        }

        //存在自定义列
        Map<String, Consumer<JeeLowCodeUser>> fieldSetters = new HashMap<>();
        fieldSetters.put("userId", user -> user.setUserId(null));
        fieldSetters.put("nickName", user -> user.setNickName(null));
        fieldSetters.put("mobile", user -> user.setMobile(null));
        fieldSetters.put("email", user -> user.setEmail(null));
        fieldSetters.put("sex", user -> user.setSex(null));
        fieldSetters.put("post", user -> user.setPost(null));
        fieldSetters.put("avatar", user -> user.setAvatar(null));
        fieldSetters.put("deptName", user -> user.setDeptName(null));
        // 为其他字段添加更多的条目

        List<JeeLowCodeUser> records = pages.getRecords();
        records.stream().peek(user -> fieldSetters.forEach((fieldName, setter) -> {
            if (!fieldList.contains(fieldName)) {
                setter.accept(user);
            }
        }))
                .collect(Collectors.toList());
        return pages;
    }


    //初始化-新增数据默认项
    @Override
    public void initSaveDefaultData(Map<String, Object> map) {
        //默认值由系统赋值，不做修改
        map.remove(DefaultDbFieldEnum.CREATE_USER.getFieldCode());
        map.remove(DefaultDbFieldEnum.CREATE_TIME.getFieldCode());
        map.remove(DefaultDbFieldEnum.CREATE_DEPT.getFieldCode());
        map.remove(DefaultDbFieldEnum.IS_DELETED.getFieldCode());
        map.remove(DefaultDbFieldEnum.TENANT_ID.getFieldCode());
        map.remove(DefaultDbFieldEnum.UPDATE_USER.getFieldCode());
        map.remove(DefaultDbFieldEnum.UPDATE_TIME.getFieldCode());

        DateTime now = DateUtil.date();

        if (FuncBase.isEmpty(map.get(DefaultDbFieldEnum.ID.getFieldCode()))) {
            map.put(DefaultDbFieldEnum.ID.getFieldCode(), IdWorker.getId());
        }

        map.put(DefaultDbFieldEnum.CREATE_USER.getFieldCode(), this.getOnlineUserId());
        map.put(DefaultDbFieldEnum.CREATE_TIME.getFieldCode(), now);
        map.put(DefaultDbFieldEnum.CREATE_DEPT.getFieldCode(), this.getOnlineUserDeptId());
        map.put(DefaultDbFieldEnum.IS_DELETED.getFieldCode(), 0);
        map.put(DefaultDbFieldEnum.TENANT_ID.getFieldCode(), this.getTenantId());
    }

    //初始化-修改数据默认项
    @Override
    public void initUpdateDefaultData(Map<String, Object> map) {
        //默认值由系统赋值，不做修改
        map.remove(DefaultDbFieldEnum.CREATE_USER.getFieldCode());
        map.remove(DefaultDbFieldEnum.CREATE_TIME.getFieldCode());
        map.remove(DefaultDbFieldEnum.CREATE_DEPT.getFieldCode());
        map.remove(DefaultDbFieldEnum.IS_DELETED.getFieldCode());
        map.remove(DefaultDbFieldEnum.TENANT_ID.getFieldCode());
        map.remove(DefaultDbFieldEnum.UPDATE_USER.getFieldCode());
        map.remove(DefaultDbFieldEnum.UPDATE_TIME.getFieldCode());

        DateTime now = DateUtil.date();
        map.put(DefaultDbFieldEnum.UPDATE_USER.getFieldCode(), this.getOnlineUserId());
        map.put(DefaultDbFieldEnum.UPDATE_TIME.getFieldCode(), now);

    }

    //获取所有不用租户的表
    @Override
    public Set<String> getTenantIgnoreTable() {
        return tenantProperties.getIgnoreTables();
    }

    @Override
    public Set<String> getTenantIgnoreUrl() {
        return tenantProperties.getIgnoreUrls().stream()
                .filter(url -> !url.contains("jeelowcode"))
                .collect(Collectors.toCollection(HashSet::new));
    }

    //判断多租户是否启用
    @Override
    public boolean getTenantEnable() {
        return tenantProperties.getEnable();
    }

    //获取所有子部门
    private List<Long> getChildDeptIdList(Long id) {
        List<Long> children = new LinkedList<>();//子集
        children.add(id);
        // 遍历每一层
        List<Long> parentIdList = new ArrayList<>();
        parentIdList.add(id);

        for (int i = 0; i < Short.MAX_VALUE; i++) { // 使用 Short.MAX_VALUE 避免 bug 场景下，存在死循环

            List<Long> finalParentIdList = parentIdList;
            SqlInfoQueryWrapper.Wrapper wrapper = SqlHelper.getQueryWrapper()
                    .select("id")
                    .setTableName("system_dept")
                    .setWhere(where -> {
                        where.eq("deleted", 0);
                        where.in("parent_id", finalParentIdList);
                    })
                    .build();
            List<Map<String, Object>> deptList = sqlService.getDataListByPlus(wrapper);

            // 1. 如果没有子部门，则结束遍历
            if (Func.isEmpty(deptList)) {
                break;
            }

            List<Long> tmpIdList = deptList.stream()
                    .map(deptMap -> Func.getMap2Long(deptMap, "id"))
                    .filter(Objects::nonNull)
                    .collect(Collectors.toCollection(ArrayList::new));

            children.addAll(tmpIdList);//存入到总集合里面
            parentIdList = tmpIdList;//进行下一轮
        }
        return children;
    }
}