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
package com.jeelowcode.framework.tenant.aspect;


import cn.iocoder.yudao.framework.tenant.core.context.TenantContextHolder;
import com.jeelowcode.framework.tenant.utils.JeeLowCodeTenantUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;


@Aspect
public class JeeLowCodeTenantAspect {

    public JeeLowCodeTenantAspect() {
    }


    // 定义一个切点，用于匹配带有Administrator注解的类
    @Pointcut("@within(com.jeelowcode.framework.tenant.annotation.JeeLowCodeTenantIgnore)")
    private void pointcutClass() {}

    // 定义一个切点，用于匹配带有Administrator注解的方法
    @Pointcut("@annotation(com.jeelowcode.framework.tenant.annotation.JeeLowCodeTenantIgnore)")
    private void pointcutMethod() {}

    @Around("pointcutClass() || pointcutMethod()")
    public Object around(ProceedingJoinPoint point) throws Throwable {
        Object obj;
        boolean oldjeelowcodeIgnore = JeeLowCodeTenantUtils.isIgnore();
        boolean oldIgnore = TenantContextHolder.isIgnore();
        try {
            JeeLowCodeTenantUtils.setIgnore(Boolean.TRUE);
            TenantContextHolder.setIgnore(true);//芋道
            obj = point.proceed();
        } finally {
            JeeLowCodeTenantUtils.setIgnore(oldjeelowcodeIgnore);
            TenantContextHolder.setIgnore(oldIgnore);
        }

        return obj;
    }

}
