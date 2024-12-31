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
package com.jeelowcode.core.framework.config.aspect.nologin;

import com.jeelowcode.core.framework.service.IFormService;
import com.jeelowcode.core.framework.utils.Func;
import com.jeelowcode.framework.constants.FrameErrorCodeConstants;
import com.jeelowcode.framework.exception.JeeLowCodeException;
import com.jeelowcode.framework.utils.enums.AuthTypeEnum;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.stereotype.Component;

/**
 * 不登录是否可以查看dbform配置
 */
@Aspect
@Component
public class JeeLowCodeNoLoginViewDbFormAspect {

    private ExpressionParser parser = new SpelExpressionParser();

    @Autowired
    private IFormService formService;

    @Around("@annotation(com.jeelowcode.framework.utils.annotation.JeeLowCodeNoLoginViewDbForm)")
    public Object aroundMethod(ProceedingJoinPoint joinPoint) throws Throwable {
        // 获取方法的参数
        Object[] args = joinPoint.getArgs();
        // 假设dbformId是第一个参数，你可以根据实际情况调整索引
        Long dbformId = (Long) args[0];
        if (Func.isEmpty(dbformId)) {
            throw new JeeLowCodeException(FrameErrorCodeConstants.FRAME_PARAM_NULL_ERROR.getMsg());
        }
        //判断该接口是否勾选 不登录可查看
        AuthTypeEnum authType = formService.getAuthType(dbformId);

        if (!Func.equals(authType, AuthTypeEnum.authOpen)) {//配置没有勾选,判断是否已经登录了
            throw new JeeLowCodeException(FrameErrorCodeConstants.FRAME_LOGIN_VIEW_ERROR.getMsg());
        }

        return joinPoint.proceed();
    }

}

