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
package com.jeelowcode.framework.utils.component.cache;


import com.jeelowcode.framework.utils.annotation.JeelowCodeCache;
import com.jeelowcode.framework.utils.component.properties.JeeLowCodeProperties;
import com.jeelowcode.framework.utils.component.redis.JeeLowCodeRedisUtils;
import com.jeelowcode.framework.utils.constant.JeeRedisConstants;
import com.jeelowcode.framework.utils.model.global.BaseWebResult;
import com.jeelowcode.framework.utils.utils.FuncBase;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

import java.util.ArrayList;
import java.util.List;

/**
 * JeeLowCode低代码
 * 缓存相关
 */
@Aspect
public class JeelowCodeCacheAspect {

    private ExpressionParser parser = new SpelExpressionParser();

    private final JeeLowCodeRedisUtils jeeLowCodeRedisUtils;


    public JeelowCodeCacheAspect(JeeLowCodeRedisUtils jeeLowCodeRedisUtils) {
        this.jeeLowCodeRedisUtils=jeeLowCodeRedisUtils;
    }

    @Around("@annotation(jeelowCodeCache)")
    public Object aroundMethod(ProceedingJoinPoint joinPoint,JeelowCodeCache jeelowCodeCache) throws Throwable {
        if (JeeLowCodeProperties.getDebug()) {//开发环境，直接跳过
            return joinPoint.proceed();
        }

        MethodSignature signature = (MethodSignature) joinPoint.getSignature();

        boolean nullIsSave = jeelowCodeCache.nullIsSave();

        StandardEvaluationContext context = new StandardEvaluationContext();
        // 获取缓存key
        Object[] args = joinPoint.getArgs();

        String[] parameterNames = signature.getParameterNames();

        for (int i = 0; i < parameterNames.length; i++) {
            context.setVariable(parameterNames[i], args[i]);
        }

        String redisKey = JeeRedisConstants.JEELOWCODE_PREFIX + "cache:" + parser.parseExpression(jeelowCodeCache.cacheNames()).getValue(context, String.class);

        if (jeeLowCodeRedisUtils.hasKey(redisKey)) {

            Object cachedResultObj = jeeLowCodeRedisUtils.get(redisKey);
            Class reflexClass = jeelowCodeCache.reflexClass();
            try {
                String cachedResultStr = String.valueOf(cachedResultObj);
                if(cachedResultStr==null){
                    return null;
                }else if(FuncBase.equals(cachedResultStr,"")){
                    return "";
                }else if(FuncBase.equals(cachedResultStr,"[]")){
                    return new ArrayList<>();
                }

                if (FuncBase.jsonIsArray(cachedResultStr)) {//当前是jsonarray，则需要转换
                    List list = FuncBase.json2List(cachedResultStr, reflexClass);
                    return list;
                } else if (FuncBase.jsonIsObject(cachedResultStr)) {//当前是json，则需要转换
                    Object obj = FuncBase.json2Bean(cachedResultStr, reflexClass);
                    return obj;
                } else {//基本数据类型
                    Class<?> longClass = Long.class;
                    if (longClass.isAssignableFrom(reflexClass)) {//因为redis存把long存进去识别为int
                        return FuncBase.toLong(FuncBase.toStr(cachedResultObj));
                    } else {
                        return cachedResultObj;
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }


        }

        // 如果缓存中没有数据，则继续执行目标方法
        Object result = joinPoint.proceed();
        if (!nullIsSave && FuncBase.isEmpty(result)) {//只存有值部分
            return result;
        }

        if (FuncBase.isNotEmpty(result) && (result instanceof BaseWebResult)) {//这个类型，需要成功才做缓存
            BaseWebResult baseWebResult = (BaseWebResult) result;
            if (baseWebResult.isSuccess()) {
                // 将结果缓存
                jeeLowCodeRedisUtils.set(redisKey, FuncBase.json2Str(result), jeelowCodeCache.expire(), jeelowCodeCache.timeUnit());
            }
        } else if (FuncBase.isNotEmpty(result) && (result instanceof Throwable)) {//异常类不做

        } else {
            String resultStr = FuncBase.json2Str(result);
            if (FuncBase.jsonIsJson(resultStr)) {//当前是json，则需要转换
                jeeLowCodeRedisUtils.set(redisKey, resultStr, jeelowCodeCache.expire(), jeelowCodeCache.timeUnit());
            } else {//不是json，则是基本数据类型，转为字符串存储
                jeeLowCodeRedisUtils.set(redisKey, result, jeelowCodeCache.expire(), jeelowCodeCache.timeUnit());
            }
        }

        return result;
    }



}