package cn.iocoder.yudao.framework.tenant.core.aop;

import java.lang.annotation.*;

/**
 * 忽略租户，标记指定方法不进行租户的自动过滤
 * 可用于类和方法
 */
@Target({ElementType.TYPE,ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface TenantIgnore {
}
