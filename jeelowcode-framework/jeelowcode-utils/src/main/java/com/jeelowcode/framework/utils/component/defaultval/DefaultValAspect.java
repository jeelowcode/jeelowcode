/*					
Apache License
Version 2.0, January 2004
http://www.apache.org/licenses/
本软件受适用的国家软件著作权法（包括国际条约）和开源协议 双重保护许可。

开源协议中文释意如下：
1.JeeLowCode开源版本无任何限制，在遵循本开源协议（Apache2.0）条款下，【允许商用】使用，不会造成侵权行为。
2.允许基于本平台软件开展业务系统开发。
3.在任何情况下，您不得使用本软件开发可能被认为与【本软件竞争】的软件。

最终解释权归：http://www.jeelowcode.com
*/
package com.jeelowcode.framework.utils.component.defaultval;

import com.jeelowcode.framework.global.JeeLowCodeBaseConstant;
import com.jeelowcode.framework.utils.adapter.IJeeLowCodeAdapter;
import com.jeelowcode.framework.utils.model.global.BaseEntity;
import com.jeelowcode.framework.utils.model.global.BaseTenantEntity;
import com.jeelowcode.framework.utils.utils.FuncBase;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * 默认值切面
 */
@Order(value = 0)
@Component
@Aspect
public class DefaultValAspect {

    private final IJeeLowCodeAdapter proxyAdapter;

    public final static String JEELOWCODE_EXPRESSION = "execution(* "+ JeeLowCodeBaseConstant.BASE_PACKAGES_CODE+".framework.service.impl.FrameServiceImpl.*(..))";//jeelowcode
    public final static String MYBATIS_EXPRESSION = "execution(* "+JeeLowCodeBaseConstant.BASE_PACKAGES_CODE+".*.mapper.*.*(..))";//mybatis


    //需要拦截的方法名称
    private static Map<String, String> aspectMethodNameMapp = new HashMap<>();

    private static String savePublicData="savePublicData";
    private static String editPublicData="editPublicData";
    private static String baseUpdateDataById="baseUpdateDataById";
    private static String baseUpdateDataByField="baseUpdateDataByField";
    private static String saveImportData="saveImportData";
    private static String insert="insert";
    private static String updateById="updateById";

    static {
        aspectMethodNameMapp.put(savePublicData, "新增");
        aspectMethodNameMapp.put(editPublicData, "编辑数据");
        aspectMethodNameMapp.put(baseUpdateDataById, "根据id编辑");
        aspectMethodNameMapp.put(baseUpdateDataByField, "根据字段编辑");

        aspectMethodNameMapp.put(insert, "mybatis-plus 自带新增");
        aspectMethodNameMapp.put(updateById, "mybatis-plus 自带编辑");
    }

    public DefaultValAspect(IJeeLowCodeAdapter proxyAdapter) {
        this.proxyAdapter = proxyAdapter;
    }

    @Pointcut(value = JEELOWCODE_EXPRESSION)
    private void aspectJeeLowCode() {
    }

    @Pointcut(value = MYBATIS_EXPRESSION)
    private void aspectPlus() {
    }


    @Around("aspectJeeLowCode() || aspectPlus()")
    public Object all(ProceedingJoinPoint joinPoint) throws Throwable {
        // 获取方法签名
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        // 获取方法名称
        String methodName = methodSignature.getName();

        if (!aspectMethodNameMapp.containsKey(methodName)) {//不在拦截范围内
            return joinPoint.proceed();
        }
        Object[] args = joinPoint.getArgs();
        if(FuncBase.equals(methodName,savePublicData)){
            this.savePublicData(args);
        }else if(FuncBase.equals(methodName,editPublicData)){
            this.editPublicData(args);
        }else if(FuncBase.equals(methodName,baseUpdateDataById)){
            this.baseUpdateDataById(args);
        }else if(FuncBase.equals(methodName,baseUpdateDataByField)){
            this.baseUpdateDataByField(args);
        }else if(FuncBase.equals(methodName,insert)){
            this.insertPlus(args);
        }else if(FuncBase.equals(methodName,updateById)){
            this.updateByIdPlus(args);
        }
        return joinPoint.proceed();

    }


    //新增
    private void savePublicData(Object[] args) throws Throwable {
        Map<String, Object> addDataMap = (Map<String, Object>) args[1];
        //处理默认值
        this.initAddMap(addDataMap);
    }

    //plus新增
    private void insertPlus(Object[] args) throws Throwable {
        Object obj = (Object) args[0];
        if (!(obj instanceof BaseEntity || obj instanceof BaseTenantEntity)) {//不属于我们的类型
            return;
        }

        LocalDateTime current = LocalDateTime.now();
        Long userId = proxyAdapter.getOnlineUserId();
        Long tenantId = proxyAdapter.getTenantId();
        Long deptId = proxyAdapter.getOnlineUserDeptId();

        //基本类
        if (obj instanceof BaseEntity) {//我们自定义的实体
            BaseEntity baseEntity = (BaseEntity) obj;
            Long createUser = baseEntity.getCreateUser();
            Long createDept = baseEntity.getCreateDept();

            if (FuncBase.isNotEmpty(current)) {
                baseEntity.setCreateTime(current);
            }
            if (FuncBase.isNotEmpty(userId) && FuncBase.isEmpty(createUser)) {
                baseEntity.setCreateUser(userId);
            }
            if (FuncBase.isNotEmpty(deptId) && FuncBase.isEmpty(createDept)) {
                baseEntity.setCreateDept(deptId);
            }
        }

        if (obj instanceof BaseTenantEntity) {//我们自定义的实体
            BaseTenantEntity baseEntity = (BaseTenantEntity) obj;
            Long selectTenantId = baseEntity.getTenantId();

            if (FuncBase.isEmpty(selectTenantId) && FuncBase.isNotEmpty(tenantId)) {
                baseEntity.setTenantId(tenantId);
            }
        }


    }

    //编辑
    private void editPublicData(Object[] args) throws Throwable {
        Map<String, Object> updateDataMap = (Map<String, Object>) args[2];
        this.initUpdateMap(updateDataMap);//初始化默认值
    }

    //编辑
    private void baseUpdateDataById(Object[] args) throws Throwable {
        Map<String, Object> updateDataMap = (Map<String, Object>) args[1];
        this.initUpdateMap(updateDataMap);//初始化默认值
    }

    //编辑
    private void baseUpdateDataByField(Object[] args) throws Throwable {
        Map<String, Object> updateDataMap = (Map<String, Object>) args[1];
        this.initUpdateMap(updateDataMap);//初始化默认值
    }


    private void initAddMap(Map<String, Object> updateDataMap) {
        if (FuncBase.isEmpty(updateDataMap)) {
            updateDataMap = new HashMap<>();
        }
        proxyAdapter.initSaveDefaultData(updateDataMap);
    }

    private void initUpdateMap(Map<String, Object> updateDataMap) {
        if (FuncBase.isEmpty(updateDataMap)) {
            updateDataMap = new HashMap<>();
        }
        proxyAdapter.initUpdateDefaultData(updateDataMap);
    }

    //plus 根据id来修改
    private void updateByIdPlus(Object[] args) throws Throwable {
       LocalDateTime current = LocalDateTime.now();


        Object obj = (Object) args[0];

        if (!(obj instanceof BaseEntity)) {//不属于我们的
            return;
        }

        Long userId = proxyAdapter.getOnlineUserId();
        BaseEntity baseEntity = (BaseEntity) obj;
        Long updateUser = baseEntity.getUpdateUser();

        baseEntity.setUpdateTime(current);
        if (FuncBase.isEmpty(updateUser) && FuncBase.isNotEmpty(userId)) {
            baseEntity.setUpdateUser(userId);
        }

    }

}
