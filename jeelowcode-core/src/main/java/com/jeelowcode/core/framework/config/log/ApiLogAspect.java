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
package com.jeelowcode.core.framework.config.log;



import cn.iocoder.yudao.framework.common.util.servlet.ServletUtils;
import com.jeelowcode.core.framework.adapter.YudaoAdapter;
import com.jeelowcode.core.framework.utils.Func;
import com.jeelowcode.core.framework.utils.FuncWeb;
import com.jeelowcode.framework.exception.JeeLowCodeException;
import com.jeelowcode.framework.exception.JeeLowCodeMoreException;
import com.jeelowcode.framework.global.JeeLowCodeBaseConstant;
import com.jeelowcode.framework.utils.tool.spring.SpringUtils;
import com.jeelowcode.framework.utils.utils.FuncBase;
import com.jeelowcode.core.framework.params.model.LogRequestApiModel;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import io.swagger.v3.oas.annotations.Operation;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.web.util.ContentCachingRequestWrapper;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.time.LocalDateTime;
import java.util.Map;

/**
 * 请求日志切面
 */
@Slf4j
@Aspect
@Component
public class ApiLogAspect {



	// JeeLowCode
	@Pointcut("execution(* com.jeelowcode.core.framework.controller.*Controller.*(..)))")
	private void pointcutJeeLowCode() {}

	// 芋道
	@Pointcut("@annotation(io.swagger.v3.oas.annotations.Operation)")
	private void pointcutYudao() {}


	/**
	 * 切入点是所有控制住
	 * @param point
	 * @return
	 * @throws Throwable
	 */
	@Around("pointcutJeeLowCode()||pointcutYudao()")
	public Object around(ProceedingJoinPoint point) throws Throwable {
		//获取类名
		String className = point.getTarget().getClass().getName();
		//获取方法
		String methodName = point.getSignature().getName();

		MethodSignature signature = (MethodSignature) point.getSignature();
		Method method = signature.getMethod();
		Operation operation = method.getAnnotation(Operation.class);
		String modelName="其他模块";
		if(Func.isNotEmpty(operation) && Func.isNotEmpty(operation.tags())){
			modelName =operation.tags()[0];
		}

		String apiName="请求日志";
		if(Func.isNotEmpty(operation) && Func.isNotEmpty(operation.summary())){
			apiName =operation.summary();
		}
		if (Func.equals("刷新令牌", apiName)){
			return point.proceed();
		}

		// 发送异步日志事件
		long beginTime = System.currentTimeMillis();

		LogRequestApiModel logApiModel = getRequestParam(methodName, className, apiName,modelName);
		//执行方法
		String error="";
		try{
			return point.proceed();
		}catch (JeeLowCodeMoreException e){
			String message = e.getMessage();
			Map<String,String> map = Func.json2Bean(message, Map.class);
			String title = map.get("title");
			String e1 = map.get("e");
			error=getErrorStr(e);
			throw new JeeLowCodeMoreException(title,e1);
		}catch (JeeLowCodeException e){
			error=getErrorStr(e);
			throw new JeeLowCodeException(e.getMessage());
		}catch (Exception e){
			error=getErrorStr(e);
			throw e;
		}finally {
			//执行时长(毫秒)
			long time = System.currentTimeMillis() - beginTime;
			//记录日志
			this.publishEvent(logApiModel, time,error);
		}

	}

	//获取具体错误信息
	private static String getErrorStr(Exception e){
		StringBuffer sb=new StringBuffer();
		sb.append(e).append("\r\n");
		StackTraceElement[] trace = e.getStackTrace();
		for (StackTraceElement traceElement : trace){
			sb.append("\tat ").append(traceElement).append("\r\n");
		}
		return sb.toString();
	}

	private static LogRequestApiModel getRequestParam(String methodName, String methodClass, String title,String modelTitle) {
		try{
			HttpServletRequest request = FuncWeb.getRequest();
			ContentCachingRequestWrapper requestWrapper = new ContentCachingRequestWrapper(request);

			YudaoAdapter jeeLowCodeAdapter = SpringUtils.getBean(YudaoAdapter.class);

			LogRequestApiModel logApiModel=new LogRequestApiModel();
			logApiModel.setId(IdWorker.getId());
			logApiModel.setTenantId(jeeLowCodeAdapter.getTenantId());
			logApiModel.setCreateUser(jeeLowCodeAdapter.getOnlineUserId());
			logApiModel.setCreateUserName(jeeLowCodeAdapter.getOnlineUserName());
			logApiModel.setCreateTime(LocalDateTime.now());

			logApiModel.setIp(ServletUtils.getClientIP(request));//ip
			logApiModel.setTitle(title);
			logApiModel.setModelTitle(modelTitle);//模块名称
			logApiModel.setMethodName(methodName);//方法名称
			logApiModel.setMethodClass(methodClass);//类名称
			logApiModel.setRequestUri(FuncWeb.getPath(requestWrapper.getRequestURI()));//请求url
			logApiModel.setRequestParams(FuncWeb.getRequestParams(requestWrapper));//请求参数
			logApiModel.setRequestMethod(requestWrapper.getMethod());//请求方式


			return logApiModel;
		}catch (Exception e){
			e.printStackTrace();
		}
		return null;
	}

	private static void publishEvent(LogRequestApiModel logApiModel, long time, String error) {
		try{
			if(FuncBase.isEmpty(logApiModel)){
				return;
			}
			String requestUri = logApiModel.getRequestUri();
			if(Func.equals(requestUri, JeeLowCodeBaseConstant.REQUEST_URL_START+"/apilog/page") || Func.equals(requestUri,JeeLowCodeBaseConstant.REQUEST_URL_START+"/apilog/detail")){
				return;//本身查看
			}


			logApiModel.setTime(time);//耗时
			logApiModel.setError(error);

			SpringUtils.getApplicationContext().publishEvent(new ApiLogEvent(logApiModel));
		}catch (Exception e){
			e.printStackTrace();
		}
	}


}
