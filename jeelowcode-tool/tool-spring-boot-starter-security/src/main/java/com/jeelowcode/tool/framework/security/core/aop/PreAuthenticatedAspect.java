package com.jeelowcode.tool.framework.security.core.aop;

import com.jeelowcode.tool.framework.security.core.annotations.PreAuthenticated;
import com.jeelowcode.tool.framework.security.core.util.SecurityFrameworkUtils;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

import static com.jeelowcode.tool.framework.common.exception.enums.GlobalErrorCodeConstants.UNAUTHORIZED;
import static com.jeelowcode.tool.framework.common.exception.util.ServiceExceptionUtil.exception;

@Aspect
@Slf4j
public class PreAuthenticatedAspect {

    @Around("@annotation(preAuthenticated)")
    public Object around(ProceedingJoinPoint joinPoint, PreAuthenticated preAuthenticated) throws Throwable {
        if (SecurityFrameworkUtils.getLoginUser() == null) {
            throw exception(UNAUTHORIZED);
        }
        return joinPoint.proceed();
    }

}
