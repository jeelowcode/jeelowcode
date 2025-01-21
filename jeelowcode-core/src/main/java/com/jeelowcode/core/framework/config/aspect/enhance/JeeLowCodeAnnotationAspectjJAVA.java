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
package com.jeelowcode.core.framework.config.aspect.enhance;


import com.jeelowcode.core.framework.config.aspect.enhance.criteria.*;
import com.jeelowcode.core.framework.config.aspect.enhance.model.*;
import com.jeelowcode.core.framework.config.aspect.enhance.plugin.*;
import com.jeelowcode.core.framework.config.listener.JeeLowCodeListener;
import com.jeelowcode.core.framework.entity.EnhanceJavaEntity;
import com.jeelowcode.core.framework.utils.Func;
import com.jeelowcode.framework.exception.JeeLowCodeException;
import com.jeelowcode.framework.utils.model.ExecuteEnhanceModel;
import com.jeelowcode.framework.utils.model.ResultDataModel;
import com.jeelowcode.framework.utils.tool.spring.SpringUtils;
import com.jeelowcode.framework.utils.utils.FuncBase;
import groovy.lang.GroovyObject;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.LocalVariableTableParameterNameDiscoverer;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

import static com.jeelowcode.framework.utils.constant.EnhanceConstant.*;

/**
 * @author JX
 * @create 2024-08-12 10:26
 * @dedescription:
 */
@Aspect
@Component
@Slf4j
public class JeeLowCodeAnnotationAspectjJAVA {

    public final static String EXPRESSION = "execution(* com.jeelowcode.core.framework.service.impl.FrameServiceImpl.*(..))";

    //需要处理的增强
    private static Map<String, List<EnhanceJavaEntity>> pluginNames = new HashMap<>();

    //需要处理的切面方法
    public static Map<String, String> aspectMethodNameMap = new HashMap<>();

    @Autowired
    private JeeLowCodeListener jeeLowCodeListener;

    @Before(value = EXPRESSION)
    public void before(JoinPoint joinPoint) throws Throwable {

        BuildEnhanceContext buildEnhanceContext = this.getContextAndPlugins("start",joinPoint);//
        if (FuncBase.isEmpty(buildEnhanceContext)) {
            return;
        }
        List<EnhanceJavaEntity> enhanceJavaEntityList = buildEnhanceContext.getEntitys();
        if (Func.isEmpty(enhanceJavaEntityList)) {
            return;
        }
        EnhanceContext context = buildEnhanceContext.getContext();
        //执行增强
        for (EnhanceJavaEntity enhanceJavaEntity : enhanceJavaEntityList) {
            this.executeEnhance(enhanceJavaEntity, context, JavaEnhanceEventState.START.getName(), joinPoint);
            if (FuncBase.isNotEmpty(context.getResult()) && context.getResult().isExitFlag()) {
                break;
            }
        }


    }


    @AfterReturning(value = EXPRESSION, returning = "returnVal")
    public Object afterReturning(JoinPoint joinPoint, Object returnVal) throws Throwable {
        //获取参数
        BuildEnhanceContext buildEnhanceContext = this.getContextAndPlugins("end",joinPoint);
        if (FuncBase.isEmpty(buildEnhanceContext)) {
            return returnVal;
        }

        if (!(returnVal instanceof ResultDataModel) && !(returnVal instanceof ExecuteEnhanceModel)) {
            return returnVal;
        }

        List<EnhanceJavaEntity> enhanceJavaEntityList = buildEnhanceContext.getEntitys();
        if (Func.isEmpty(enhanceJavaEntityList)) {//无增强
            return returnVal;
        }
        EnhanceContext context = buildEnhanceContext.getContext();
        boolean resultFlag = false;
        //封装返回值
        if (returnVal instanceof ResultDataModel) {//列表相关
            ResultDataModel resultDataModel = (ResultDataModel) returnVal;
            EnhanceResult enhanceResult = new EnhanceResult();
            enhanceResult.setRecords(resultDataModel.getRecords());
            enhanceResult.setTotal(resultDataModel.getTotal());
            context.getParam().setList(resultDataModel.getRecords());
            context.setResult(enhanceResult);
            resultFlag = true;
        } else if (returnVal instanceof ExecuteEnhanceModel) {//新增，修改，删除
            ExecuteEnhanceModel executeEnhanceModel = (ExecuteEnhanceModel) returnVal;
            EnhanceResult enhanceResult = new EnhanceResult();
            enhanceResult.setId(executeEnhanceModel.getId());
            enhanceResult.setExitFlag(executeEnhanceModel.isExitFlag());
            context.setResult(enhanceResult);
        }

        //集合操作
        List<EnhanceJavaEntity> setOperations = buildEnhanceContext.getSetOperations();

        executeSetOperation(context, setOperations);
        //执行插件
        for (EnhanceJavaEntity enhanceJavaEntity : enhanceJavaEntityList) {
            //里面集合存在，不用执行
            if (Func.isNotEmpty(setOperations) && setOperations.contains(enhanceJavaEntity)){
                continue;
            }
            this.executeEnhance(enhanceJavaEntity, context, JavaEnhanceEventState.END.getName(), joinPoint);
            if (FuncBase.isNotEmpty(context.getResult()) && context.getResult().isExitFlag()) {
                break;
            }

        }

        //返回结果
        Object result = getResult(context);
        if (resultFlag) {
            ResultDataModel returnValData = (ResultDataModel) returnVal;
            ResultDataModel resultDataModel = (ResultDataModel) result;
            returnValData.setRecords(resultDataModel.getRecords());
            returnValData.setTotal(resultDataModel.getTotal());
            return returnValData;
        } else {
            ExecuteEnhanceModel returnValData = (ExecuteEnhanceModel) returnVal;
            ExecuteEnhanceModel resultDataModel = (ExecuteEnhanceModel) result;
            returnValData.setId(resultDataModel.getId());
            return returnValData;
        }


    }


    @Around(value = EXPRESSION)
    public Object aroudAdvice(ProceedingJoinPoint joinPoint) throws Throwable {
        BuildEnhanceContext buildEnhanceContext = this.getContextAndPlugins("around",joinPoint);
        if (FuncBase.isEmpty(buildEnhanceContext)) {
            return joinPoint.proceed();
        }
        List<EnhanceJavaEntity> enhanceJavaEntityList = buildEnhanceContext.getEntitys();
        if (Func.isEmpty(enhanceJavaEntityList)) {
            return joinPoint.proceed();
        }
        EnhanceContext context = buildEnhanceContext.getContext();


        for (EnhanceJavaEntity enhanceJavaEntity : enhanceJavaEntityList) {
            this.executeEnhance(enhanceJavaEntity, context, JavaEnhanceEventState.AROUND.getName(), joinPoint);

            if (FuncBase.isNotEmpty(context.getResult()) && context.getResult().isExitFlag()) {
                break;
            }
        }
        if (Func.isEmpty(context.getResult()) || Func.isEmpty(context.getResult().getRecords())) {
            return joinPoint.proceed();
        }

        return getResult(context);
    }

    @AfterThrowing(value = EXPRESSION, throwing = "ex")
    public void afterThrowing(JoinPoint joinPoint, Exception ex) throws Throwable {
        BuildEnhanceContext buildEnhanceContext = this.getContextAndPlugins("throwing",joinPoint);
        if (FuncBase.isEmpty(buildEnhanceContext)) {
            return;
        }
        List<EnhanceJavaEntity> enhanceJavaEntityList = buildEnhanceContext.getEntitys();
        if (Func.isEmpty(enhanceJavaEntityList)) {
            return;
        }
        EnhanceContext context = buildEnhanceContext.getContext();
        for (EnhanceJavaEntity enhanceJavaEntity : enhanceJavaEntityList) {
            this.executeEnhance(enhanceJavaEntity, context, JavaEnhanceEventState.THROWING.getName(), joinPoint);
            if (FuncBase.isNotEmpty(context.getResult()) && context.getResult().isExitFlag()) {
                break;
            }

        }
    }


    public static void initPluginNames(Map<String, List<EnhanceJavaEntity>> initPluginNames) {
        pluginNames = initPluginNames;
    }

    public static void initAspectMethodNameMap(Map<String, String> initAspectMethodNameMap) {
        aspectMethodNameMap = initAspectMethodNameMap;
    }

    /**
     *
     * @param type start end around throwing
     * @param joinPoint
     * @return
     */
    public BuildEnhanceContext getContextAndPlugins(String type,JoinPoint joinPoint) {
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        // 获取方法名称
        String methodName = methodSignature.getName();
        if (!aspectMethodNameMap.containsKey(methodName)) {//不在拦截范围内 add
            return null;
        }
        //处理参数
        Object[] args = joinPoint.getArgs();
        LocalVariableTableParameterNameDiscoverer discoverer = new LocalVariableTableParameterNameDiscoverer();
        String[] parameterNames = discoverer.getParameterNames(methodSignature.getMethod());
        Map<String, Object> paramMap = new HashMap<>();
        for (int i = 0; i < parameterNames.length; i++) {
            paramMap.put(parameterNames[i], args[i]);
        }

        //判断该表单，当前的功能是否有增强
        Long dbFormId = (Long) paramMap.get("dbFormId");
        String buttonCode = aspectMethodNameMap.get(methodName);
        String key = dbFormId + "_" + buttonCode;
        List<EnhanceJavaEntity> enhanceJavaEntityAllList = pluginNames.getOrDefault(key, null);
        if (Func.isEmpty(enhanceJavaEntityAllList)) {//不存在增强
            return null;
        }
        //获取专属增强
        List<EnhanceJavaEntity> enhanceJavaEntityList=new ArrayList<>();
        for(EnhanceJavaEntity javaEntity:enhanceJavaEntityAllList){
            String javaClassUrl = javaEntity.getJavaClassUrl();//
            String javaType = javaEntity.getJavaType();
            BaseAdvicePlugin plugin = PluginManager.getPlugin(javaClassUrl);//只有class spring 方式
            switch (type) {
                case "start"://前置增强
                    if (!(plugin instanceof BeforeAdvicePlugin)) {
                        break;
                    }
                    enhanceJavaEntityList.add(javaEntity);
                    break;
                case "end"://后置增强
                    if (!(plugin instanceof AfterAdvicePlugin)) {
                        break;
                    }
                    enhanceJavaEntityList.add(javaEntity);
                    break;
                case "around":
                    //如果是在线编辑和http增强，只有环绕
                    if (FuncBase.equals(javaType, JavaEnhanceEnum.ONLINIE.getType())) {
                        //执行在线增强
                        enhanceJavaEntityList.add(javaEntity);
                        break;
                    } else if (FuncBase.equals(javaType, JavaEnhanceEnum.HTTP.getType())) {
                        //执行http增强
                        enhanceJavaEntityList.add(javaEntity);
                        break;
                    }
                    if (!(plugin instanceof AroundAdvicePlugin)) {
                        break;
                    }
                    enhanceJavaEntityList.add(javaEntity);
                    break;
                case "throwing":
                    enhanceJavaEntityList.add(javaEntity);
                    break;
                default:
                    break;
            }
        }


        //判断是否是列表，导出增强
        List<EnhanceJavaEntity> setOperation = enhanceJavaEntityList.stream()
                .filter(enhanceJavaEntity -> (FuncBase.equals(enhanceJavaEntity.getButtonCode(), ENHANCE_LIST) ||
                        FuncBase.equals(enhanceJavaEntity.getButtonCode(), ENHANCE_EXPORT)) && FuncBase.isNotEmpty(PluginManager.getPlugin(enhanceJavaEntity)))
                .collect(Collectors.toList());
        if (setOperation.size() < 2) {
            //如果只有一条，则直接执行，不用走集合操作
            setOperation.clear();
        }


        EnhanceParam param = new EnhanceParam();
        param.setDbFormId(dbFormId);
        param.setList((ArrayList) paramMap.getOrDefault("dataList", null));
        param.setParams((Map<String, Object>) paramMap.getOrDefault("params", null));

        //把参数放入到上下文
        EnhanceContext context = new EnhanceContext();
        context.setParam(param);

        BuildEnhanceContext buildEnhanceContext = new BuildEnhanceContext(context, enhanceJavaEntityList, setOperation);
        return buildEnhanceContext;
    }

    //执行增强
    public void executeEnhance(EnhanceJavaEntity enhanceJavaEntity, EnhanceContext context, String type, JoinPoint joinPoint) throws Throwable {
        String javaType = enhanceJavaEntity.getJavaType();
        if (FuncBase.equals(javaType, JavaEnhanceEnum.SPRING.getType()) || (FuncBase.equals(javaType, JavaEnhanceEnum.CLASS.getType()))) {
            //执行spring增强与class增强
            this.executeSpringEnhance(enhanceJavaEntity, type, context, joinPoint);

        } else if (FuncBase.equals(javaType, JavaEnhanceEnum.ONLINIE.getType())) {
            //执行在线增强
            this.executeOnLineScript(enhanceJavaEntity, type, context, joinPoint);

        } else if (FuncBase.equals(javaType, JavaEnhanceEnum.HTTP.getType())) {
            //执行http增强
            this.executeHttpEnhance(enhanceJavaEntity, type, context, joinPoint);
        }
    }


    /*
     * 执行spring增强
     */
    private void executeSpringEnhance(EnhanceJavaEntity enhanceJavaEntity, String type, EnhanceContext context, JoinPoint joinPoint) throws Throwable {
        String javaClassUrl = enhanceJavaEntity.getJavaClassUrl();//
        BaseAdvicePlugin plugin = PluginManager.getPlugin(javaClassUrl);
        switch (type) {
            case "start"://前置增强
                if (!(plugin instanceof BeforeAdvicePlugin)) {
                    break;
                }
                //执行
                PluginManager.executePlugin(javaClassUrl, context);
                break;
            case "end"://后置增强
                if (!(plugin instanceof AfterAdvicePlugin)) {
                    break;
                }
                //执行
                PluginManager.executePlugin(javaClassUrl, context);
                break;
            case "around":
                if (!(plugin instanceof AroundAdvicePlugin)) {
                    break;
                }

                //环绕-前置
                PluginManager.executeAroundBeforePlugin(plugin, context);

                if (Func.isEmpty(context.getResult()) || Func.isEmpty(context.getResult().getRecords())) {
                    context.setResult(new EnhanceResult());
                }
                if (context.getResult().isExitFlag()) {//说明终止，不用往下走
                    break;
                }

                //执行原方法
                ProceedingJoinPoint proceedingJoinPoint = (ProceedingJoinPoint) joinPoint;
                Object proceed = proceedingJoinPoint.proceed();
                if (proceed instanceof ResultDataModel) {
                    EnhanceResult enhanceResult = context.getResult();
                    enhanceResult.setRecords(((ResultDataModel) proceed).getRecords());
                    enhanceResult.setTotal(((ResultDataModel) proceed).getTotal());
                } else if (proceed instanceof ExecuteEnhanceModel) {
                    EnhanceResult enhanceResult = context.getResult();
                    enhanceResult.setId(((ExecuteEnhanceModel) proceed).getId());
                }

                //环绕-后置
                PluginManager.executeAroundAfterPlugin(plugin, context);
                break;
            case "throwing":
                if (!(plugin instanceof ThrowAdvicePlugin)) {
                    break;
                }
                PluginManager.executePlugin(javaClassUrl, context);
                break;
            default:
                break;
        }
    }

    /*
     *  执行Online增强---只有环绕增强
     */
    private void executeOnLineScript(EnhanceJavaEntity enhanceJavaEntity, String type, EnhanceContext context, JoinPoint joinPoint) throws Throwable {
        if (!FuncBase.equals(type, JavaEnhanceEventState.AROUND.getName())) {
            return;
        }

        Class<?> onlineClass = jeeLowCodeListener.getGroovyClassLoader().parseClass(enhanceJavaEntity.getOnlineScript());
        GroovyObject groovyObject = (GroovyObject) onlineClass.newInstance();
        groovyObject.invokeMethod("beforeExecute", context);

        //只有前置
        if (context.getOnlyBefore()) {
            return;
        }

        //执行原方法
        ProceedingJoinPoint proceedingJointpoint = (ProceedingJoinPoint) joinPoint;
        Object proceed = proceedingJointpoint.proceed();
        if (proceed instanceof ResultDataModel) {
            EnhanceResult enhanceResult = context.getResult();
            enhanceResult.setRecords(((ResultDataModel) proceed).getRecords());
            enhanceResult.setTotal(((ResultDataModel) proceed).getTotal());
        } else if (proceed instanceof ExecuteEnhanceModel) {
            EnhanceResult enhanceResult = context.getResult();
            enhanceResult.setId(((ExecuteEnhanceModel) proceed).getId());
        }
        groovyObject.invokeMethod("afterExecute", context);


    }

    private void executeHttpEnhance(EnhanceJavaEntity enhanceJavaEntity, String type, EnhanceContext context, JoinPoint joinPoint) {
        if (!FuncBase.equals(type, JavaEnhanceEventState.AROUND.getName())) {
            return;
        }
        context.setBefore(true);
        String accept = null;
        for (int i = 0; i < 3; i++) {
            try {
                accept = Func.sendPost(enhanceJavaEntity.getJavaClassUrl(), context);

                EnhanceRespModel respBody = Func.json2Bean(accept, EnhanceRespModel.class);
                if (respBody.checkStatus()) {
                    context = respBody.getData();
                    context.setBefore(false);
                    break;
                }
                Map<String, Object> errorMap = new HashMap<>();
                errorMap.put("body", Func.json2Str(context));
                errorMap.put("url", enhanceJavaEntity.getJavaClassUrl());
                errorMap.put("respBody", Func.json2Str(respBody));

                log.error("发送HTTP增强失败：" + Func.json2Str(errorMap));
                Thread.sleep(2000);//休息2秒
            } catch (Exception e) {
                log.error("发送HTTP增强失败-e：" + e.getMessage());
            }
        }
        if (context.getOnlyBefore()) {
            return;
        }

        try {
            ProceedingJoinPoint proceedingJointpoint = (ProceedingJoinPoint) joinPoint;
            Object proceed = proceedingJointpoint.proceed();
            if (proceed instanceof ResultDataModel) {
                EnhanceResult enhanceResult = context.getResult();
                enhanceResult.setRecords(((ResultDataModel) proceed).getRecords());
                enhanceResult.setTotal(((ResultDataModel) proceed).getTotal());
            } else if (proceed instanceof ExecuteEnhanceModel) {
                EnhanceResult enhanceResult = context.getResult();
                enhanceResult.setId(((ExecuteEnhanceModel) proceed).getId());
            }
        } catch (Throwable e) {
            throw new JeeLowCodeException("发送HTTP增强失败：" + e.getMessage());
        }


        for (int i = 0; i < 3; i++) {
            try {
                accept = Func.sendPost(enhanceJavaEntity.getJavaClassUrl(), context);

                EnhanceRespModel respBody = Func.json2Bean(accept, EnhanceRespModel.class);
                if (respBody.checkStatus()) {
                    context = respBody.getData();
                    break;
                }
                Map<String, Object> errorMap = new HashMap<>();
                errorMap.put("body", Func.json2Str(context));
                errorMap.put("url", enhanceJavaEntity.getJavaClassUrl());
                errorMap.put("respBody", Func.json2Str(respBody));

                log.error("发送HTTP增强失败：" + Func.json2Str(errorMap));
                Thread.sleep(2000);//休息2秒
            } catch (Exception e) {
                log.error("发送HTTP增强失败-e：" + e.getMessage());
            }
        }

    }


    private void executeSetOperation(EnhanceContext context, List<EnhanceJavaEntity> javaEntityList) throws ClassNotFoundException, InstantiationException, IllegalAccessException {
        if (Func.isEmpty(javaEntityList) || javaEntityList.size() < 2) {
            return;
        }
        //只有后置增强才有集合操作
        List<EnhanceJavaEntity> afterJavaList = javaEntityList.stream().filter(javaEntity -> {
            Object o = null;
            if (Func.equals(JavaEnhanceEnum.SPRING.getType(), javaEntity.getJavaType())) {
                o = SpringUtils.getBean(javaEntity.getJavaClassUrl());
            } else if (Func.equals(JavaEnhanceEnum.CLASS.getType(), javaEntity.getJavaType())) {
                try {
                    o = Class.forName(javaEntity.getJavaClassUrl()).newInstance();
                } catch (InstantiationException | IllegalAccessException | ClassNotFoundException ignored) {}
            }
            return o instanceof AfterAdvicePlugin;
        }).collect(Collectors.toList());

        if (Func.isEmpty(afterJavaList) || afterJavaList.size() < 2) {
            if (Func.isNotEmpty(afterJavaList)) {
                javaEntityList.removeAll(afterJavaList);
            }
            return;
        }




        //根据序号排序
        afterJavaList.sort(
                Comparator.comparing(
                        EnhanceJavaEntity::getSort, Comparator.nullsLast(Integer::compareTo)));


        for (int i = 0; i < afterJavaList.size() - 1; i++) {
            EnhanceJavaEntity leftEntity = null;
            EnhanceJavaEntity rightEntity = null;
            if (i == 0) {
                leftEntity = afterJavaList.get(i);
                rightEntity = afterJavaList.get(i + 1);
            } else {
                rightEntity = afterJavaList.get(i + 1);
            }

            switch (rightEntity.getListResultHandleType()) {
                case "1":
                    //执行合集操作
                    executSetOperationOr(leftEntity, rightEntity, context);
                    break;
                case "2":
                    //执行差集操作
                    executSetOperationDiffer(leftEntity, rightEntity, context);
                    break;
                case "3":
                    //执行并集操作
                    executSetOperationUnion(leftEntity, rightEntity, context);
                    break;
                case "4":
                    //执行交集操作
                    executSetOperationInterSerction(leftEntity, rightEntity, context);
                    break;
                default:
                case "0":
                    //执行串行操作
                    executSetOperationAnd(leftEntity, rightEntity, context);
                    break;
            }

        }

    }

    //串行
    private void executSetOperationAnd(EnhanceJavaEntity leftEntity, EnhanceJavaEntity rightEntity, EnhanceContext context) throws ClassNotFoundException, InstantiationException, IllegalAccessException {

        AfterAdvicePlugin leftPlugin = judgeLeftPlugin(leftEntity);
        AfterAdvicePlugin rightPlugin = (AfterAdvicePlugin)PluginManager.getPlugin(rightEntity);
        AndCriteriaFilterAdvicePlugin andAdvicePlugin = new AndCriteriaFilterAdvicePlugin(leftPlugin, rightPlugin);
        andAdvicePlugin.execute(context);

    }

    //合集
    private void executSetOperationOr(EnhanceJavaEntity leftEntity, EnhanceJavaEntity rightEntity, EnhanceContext context) throws ClassNotFoundException, InstantiationException, IllegalAccessException {
        AfterAdvicePlugin leftPlugin = judgeLeftPlugin(leftEntity);
        AfterAdvicePlugin rightPlugin = (AfterAdvicePlugin)PluginManager.getPlugin(rightEntity);
        OrCriteriaFilterAdvicePlugin orAdvicePlugin = new OrCriteriaFilterAdvicePlugin(leftPlugin, rightPlugin);
        orAdvicePlugin.execute(context);

    }

    //差集
    private void executSetOperationDiffer(EnhanceJavaEntity leftEntity, EnhanceJavaEntity rightEntity, EnhanceContext context) throws ClassNotFoundException, InstantiationException, IllegalAccessException {
        AfterAdvicePlugin leftPlugin = judgeLeftPlugin(leftEntity);
        AfterAdvicePlugin rightPlugin = (AfterAdvicePlugin)PluginManager.getPlugin(rightEntity);
        DifferenceCriteriaFilterAdvicePlugin differPlugin = new DifferenceCriteriaFilterAdvicePlugin(leftPlugin, rightPlugin);
        differPlugin.execute(context);
    }

    //并集
    private void executSetOperationUnion(EnhanceJavaEntity leftEntity, EnhanceJavaEntity rightEntity, EnhanceContext context) throws ClassNotFoundException, InstantiationException, IllegalAccessException {

        AfterAdvicePlugin leftPlugin = judgeLeftPlugin(leftEntity);
        AfterAdvicePlugin rightPlugin = (AfterAdvicePlugin)PluginManager.getPlugin(rightEntity);
        UnionCriteriaFilterAdvicePlugin unionPlugin = new UnionCriteriaFilterAdvicePlugin(leftPlugin, rightPlugin);
        unionPlugin.execute(context);


    }

    //交集
    private void executSetOperationInterSerction(EnhanceJavaEntity leftEntity, EnhanceJavaEntity rightEntity, EnhanceContext context) throws ClassNotFoundException, InstantiationException, IllegalAccessException {
        AfterAdvicePlugin leftPlugin = judgeLeftPlugin(leftEntity);
        AfterAdvicePlugin rightPlugin = (AfterAdvicePlugin)PluginManager.getPlugin(rightEntity);
        IntersectionCriteriaFilterAdvicePlugin intersectionPlugin = new IntersectionCriteriaFilterAdvicePlugin(leftPlugin, rightPlugin);
        intersectionPlugin.execute(context);


    }



    public Object getResult(EnhanceContext context) {
        if (Func.isEmpty(context.getResult().getRecords()) && FuncBase.isEmpty(context.getResult().getId())) {
            return ResultDataModel.fomat(0L, new ArrayList<>());
        }
        if (FuncBase.isEmpty(context.getResult().getRecords())) {
            ExecuteEnhanceModel enhanceModel = new ExecuteEnhanceModel();
            enhanceModel.setId(context.getResult().getId());
            enhanceModel.setExitFlag(context.getResult().isExitFlag());

            return enhanceModel;
        } else {

            ResultDataModel resultDataModel = new ResultDataModel();
            resultDataModel.setRecords(context.getResult().getRecords());
            resultDataModel.setTotal(context.getResult().getTotal());
            resultDataModel.setExitFlag(context.getResult().isExitFlag());
            return resultDataModel;
        }
    }

    //判断左边是否为空
    public AfterAdvicePlugin judgeLeftPlugin(EnhanceJavaEntity leftEntity) {
        AfterAdvicePlugin leftPlugin = null;
        if (FuncBase.isNotEmpty(leftEntity)) {
            leftPlugin = (AfterAdvicePlugin)PluginManager.getPlugin(leftEntity);
        } else {
            leftPlugin = enhanceContext -> {
            };
        }
        return leftPlugin;
    }

    //新增增强
    public static void addPlugin(EnhanceJavaEntity javaEntity){
        try {
            String key  = javaEntity.getDbformId()+"_"+javaEntity.getButtonCode();
            // 1.处理 pluginNames
            pluginNames.merge(key, new ArrayList<>(Collections.singletonList(javaEntity)), (oldValue, newValue) -> {
                oldValue.addAll(newValue);
                return oldValue;
            });

            // 2.处理 plugins
            if (Func.equals(javaEntity.getJavaType(), JavaEnhanceEnum.SPRING.getType())){
                String javaClassUrl = javaEntity.getJavaClassUrl();
                Object bean = SpringUtils.getBean(javaClassUrl);
                if (Func.isNotEmpty(bean) && bean instanceof BaseAdvicePlugin) {
                    PluginManager.addPlugin(javaClassUrl, (BaseAdvicePlugin) bean);
                }
            }else if (Func.equals(javaEntity.getJavaType(), JavaEnhanceEnum.CLASS.getType())){
                Class<?> enhanceClass = Class.forName(javaEntity.getJavaClassUrl());
                BaseAdvicePlugin plugin = (BaseAdvicePlugin) enhanceClass.newInstance();
                PluginManager.addPlugin(javaEntity.getJavaClassUrl(), plugin);
            }

        } catch (Exception e) {
            throw new JeeLowCodeException("增强插件加载失败");
        }
    }

    //修改增强
    public static void updatePlugins(EnhanceJavaEntity javaEntity){
        try {
            String key  = javaEntity.getDbformId()+"_"+javaEntity.getButtonCode();
            AtomicReference<String> oldJavaClassUrlAtomicRe = new AtomicReference<>();
            // 1.处理 pluginNames
            pluginNames.merge(key, new ArrayList<>(Collections.singletonList(javaEntity)), (oldValue, newValue) -> {
                // 先删除再新增
                oldValue.removeIf(oldJavaEntity -> {
                    if (FuncBase.equals(oldJavaEntity.getId(), javaEntity.getId())) {
                        oldJavaClassUrlAtomicRe.set(oldJavaEntity.getJavaClassUrl());
                        return true;
                    }else {
                        return false;
                    }
                });
                oldValue.add(javaEntity);
                return oldValue;
            });

            // 2.处理 plugins 先删除再新增
            removePlugins(oldJavaClassUrlAtomicRe.get());
            String javaType = javaEntity.getJavaType();
            String javaClassUrl = javaEntity.getJavaClassUrl();
            if (Func.equals(javaType, JavaEnhanceEnum.SPRING.getType())){
                Object bean = SpringUtils.getBean(javaClassUrl);
                if (Func.isNotEmpty(bean) && bean instanceof BaseAdvicePlugin) {
                    PluginManager.addPlugin(javaClassUrl, (BaseAdvicePlugin) bean);
                }
            }else if (Func.equals(javaType, JavaEnhanceEnum.CLASS.getType())){
                Class<?> enhanceClass = Class.forName(javaClassUrl);
                BaseAdvicePlugin plugin = (BaseAdvicePlugin) enhanceClass.newInstance();
                PluginManager.addPlugin(javaClassUrl, plugin);
            }
        } catch (Exception e) {
            throw new JeeLowCodeException("增强插件加载失败");
        }
    }

    //删除增强
    public static void deletePlugins(EnhanceJavaEntity javaEntity){
        String key = javaEntity.getDbformId() + "_" + javaEntity.getButtonCode();
        // 1.处理 pluginNames
        List<EnhanceJavaEntity> javaEntityList = pluginNames.getOrDefault(key, new ArrayList<>());
        AtomicReference<String> oldJavaClassUrlAtomicRe = new AtomicReference<>();
        javaEntityList.removeIf(oldJavaEntity -> {
            if (oldJavaEntity.getId().equals(javaEntity.getId())) {
                oldJavaClassUrlAtomicRe.set(oldJavaEntity.getJavaClassUrl());
                return true;
            }else {
                return false;
            }
        });
        if (FuncBase.isEmpty(javaEntityList)) {
            pluginNames.remove(key);
        }

        // 2.处理 plugins
        removePlugins(oldJavaClassUrlAtomicRe.get());
    }

    /**
     * 删除 plugins 中的增强
     */
    private static void removePlugins(String oldJavaClassUrl){
        if (Func.isEmpty(oldJavaClassUrl)) {
            return;
        }
        Set<String> JavaClassUrlSet = pluginNames.values().stream().flatMap(Collection::stream)
                .map(EnhanceJavaEntity::getJavaClassUrl).collect(Collectors.toSet());
        if (!JavaClassUrlSet.contains(oldJavaClassUrl)) {
            PluginManager.removePlugin(oldJavaClassUrl);
        }
    }
}
