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
package com.jeelowcode.core.framework.config.aspect.enhancereport;


import com.jeelowcode.core.framework.config.aspect.enhance.model.EnhanceResult;
import com.jeelowcode.core.framework.config.aspect.enhance.model.JavaEnhanceEnum;
import com.jeelowcode.core.framework.config.aspect.enhance.plugin.AfterAdvicePlugin;
import com.jeelowcode.core.framework.config.aspect.enhancereport.model.BuildEnhanceReportContext;
import com.jeelowcode.core.framework.config.aspect.enhancereport.model.EnhanceReportContext;
import com.jeelowcode.core.framework.config.aspect.enhancereport.model.EnhanceReportParam;
import com.jeelowcode.core.framework.config.aspect.enhancereport.plugin.ReportAfterAdvicePlugin;
import com.jeelowcode.core.framework.config.aspect.enhancereport.plugin.ReportBaseAdvicePlugin;
import com.jeelowcode.core.framework.config.aspect.enhancereport.plugin.ReportPluginManager;
import com.jeelowcode.core.framework.utils.Func;
import com.jeelowcode.framework.exception.JeeLowCodeException;
import com.jeelowcode.framework.utils.model.ResultDataModel;
import com.jeelowcode.framework.utils.tool.spring.SpringUtils;
import com.jeelowcode.framework.utils.utils.FuncBase;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.LocalVariableTableParameterNameDiscoverer;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * 报表统计
 */
@Aspect
@Component
@Slf4j
public class JeeLowCodeAnnotationAspectjReport {

    public final static String EXPRESSION = "execution(* com.jeelowcode.core.framework.service.impl.FrameServiceImpl.*(..))";

    //拦截方法
    public final static String ASPECT_METHODNAME ="getReportDataList";

    //需要处理的增强  key=报表code  value=增强
    private static Map<String, String> pluginNames = new HashMap<>();

    @AfterReturning(value = EXPRESSION, returning = "returnVal")
    public Object afterReturning(JoinPoint joinPoint, Object returnVal) throws Throwable {
        //获取参数
        BuildEnhanceReportContext buildEnhanceContext = this.getContextAndPlugins(joinPoint);
        if (FuncBase.isEmpty(buildEnhanceContext)) {//没有增强
            return returnVal;
        }

        //不是列表类增强
        if (!(returnVal instanceof ResultDataModel)) {
            return returnVal;
        }

        //增强列表
        String javaClass = buildEnhanceContext.getJavaClass();
        if (Func.isEmpty(javaClass)) {//无增强
            return returnVal;
        }
        EnhanceReportContext context = buildEnhanceContext.getContext();


        //把原来返回值封装到上下文context
        ResultDataModel resultDataModel = (ResultDataModel) returnVal;
        List<Map<String, Object>> records = resultDataModel.getRecords();
        if(Func.isEmpty(records)){
            records=new ArrayList<>();
        }

        EnhanceResult enhanceResult = new EnhanceResult();
        enhanceResult.setRecords(records);
        enhanceResult.setTotal((long)records.size());
        context.getParam().setList(records);
        context.setResult(enhanceResult);

        //执行增强
        this.executeJavaEnhance(javaClass, context);


        //返回结果
        ResultDataModel returnValData = (ResultDataModel) returnVal;
        resultDataModel = (ResultDataModel) getResult(context);
        returnValData.setRecords(resultDataModel.getRecords());
        returnValData.setTotal(resultDataModel.getTotal());
        return returnValData;

    }

    public BuildEnhanceReportContext getContextAndPlugins(JoinPoint joinPoint) {
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        // 获取方法名称
        String methodName = methodSignature.getName();
        if (Func.notEquals(methodName,ASPECT_METHODNAME)) {//不在拦截范围内 add
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
        String reportCode = (String) paramMap.get("reportCode");
        String javaClass = pluginNames.getOrDefault(reportCode, null);
        if (Func.isEmpty(javaClass)) {//不存在增强
            return null;
        }


        EnhanceReportParam param = new EnhanceReportParam();
        param.setReportCode(reportCode);
        param.setList((ArrayList) paramMap.getOrDefault("dataList", null));
        param.setParams((Map<String, Object>) paramMap.getOrDefault("params", null));

        //把参数放入到上下文
        EnhanceReportContext context = new EnhanceReportContext();
        context.setParam(param);

        BuildEnhanceReportContext buildEnhanceContext = new BuildEnhanceReportContext(context, javaClass);
        return buildEnhanceContext;
    }

    //执行JAVA增强
    private void executeJavaEnhance(String javaClassUrl, EnhanceReportContext context) throws Throwable {
        ReportBaseAdvicePlugin plugin = ReportPluginManager.getPlugin(javaClassUrl);
        if (!(plugin instanceof ReportAfterAdvicePlugin)) {
            return;
        }
        //执行
        ReportPluginManager.executePlugin(javaClassUrl, context);
    }

    public Object getResult(EnhanceReportContext context) {
        if (Func.isEmpty(context.getResult().getRecords()) && FuncBase.isEmpty(context.getResult().getId())) {
            return ResultDataModel.fomat(0L, new ArrayList<>());
        }

        ResultDataModel resultDataModel = new ResultDataModel();
        resultDataModel.setRecords(context.getResult().getRecords());
        resultDataModel.setTotal(context.getResult().getTotal());
        resultDataModel.setExitFlag(context.getResult().isExitFlag());
        return resultDataModel;
    }

    //刷新插件
    public  static void refreshPlugin(String type,String reportCode,String javaClass){
        try {
            if(Func.isEmpty(javaClass)){
                return;
            }

            if(Func.equals(type,"ADD") ||Func.equals(type,"UPDATE")){
                JavaEnhanceEnum javaEnhanceEnum=javaClass.indexOf(".")>0?JavaEnhanceEnum.CLASS:JavaEnhanceEnum.SPRING;
                if(Func.equals(javaEnhanceEnum,JavaEnhanceEnum.SPRING)){
                    Object bean = SpringUtils.getBean(javaClass);
                    if (Func.isNotEmpty(bean) && bean instanceof ReportBaseAdvicePlugin) {
                        ReportPluginManager.addPlugin(javaClass, (ReportBaseAdvicePlugin) bean);
                        pluginNames.put(reportCode,javaClass);
                    }
                }else{
                    Class<?> enhanceClass = Class.forName(javaClass);
                    ReportBaseAdvicePlugin plugin = (ReportBaseAdvicePlugin) enhanceClass.newInstance();
                    ReportPluginManager.addPlugin(javaClass, plugin);
                    pluginNames.put(reportCode,javaClass);
                }
            }else if(Func.equals(type,"DEL")){
                pluginNames.remove(reportCode,javaClass);
                if (!new HashSet<>(pluginNames.values()).contains(javaClass)) {
                    ReportPluginManager.removePlugin(javaClass);
                }
            }

        } catch (Exception e) {
            //e.printStackTrace();
            throw new JeeLowCodeException("增强["+javaClass+"]插件加载失败");
        }
    }


}
