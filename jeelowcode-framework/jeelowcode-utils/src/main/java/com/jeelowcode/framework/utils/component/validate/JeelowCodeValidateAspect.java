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
package com.jeelowcode.framework.utils.component.validate;

import com.jeelowcode.framework.utils.adapter.IJeelowCodeValidate;
import com.jeelowcode.framework.utils.annotation.JeelowCodeValidate;
import com.jeelowcode.framework.utils.tool.spring.SpringUtils;
import com.jeelowcode.framework.utils.utils.FuncWebBase;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.web.util.ContentCachingRequestWrapper;

import javax.servlet.http.HttpServletRequest;

/**
 * 自定义校验
 */
@Aspect
@Component
public class JeelowCodeValidateAspect {

    @Around("@annotation(jeelowCodeValidate)")
    public Object aroundMethod(ProceedingJoinPoint joinPoint,JeelowCodeValidate jeelowCodeValidate) throws Throwable {
        HttpServletRequest request = FuncWebBase.getRequest();
        ContentCachingRequestWrapper requestWrapper = new ContentCachingRequestWrapper(request);
        Class<? extends IJeelowCodeValidate>[] validateClassList = jeelowCodeValidate.validateClass();

        for (Class<? extends IJeelowCodeValidate> myclass : validateClassList) {
            IJeelowCodeValidate bean = SpringUtils.getBean(myclass);
            bean.validate(requestWrapper);
        }
        return joinPoint.proceed();
    }


}