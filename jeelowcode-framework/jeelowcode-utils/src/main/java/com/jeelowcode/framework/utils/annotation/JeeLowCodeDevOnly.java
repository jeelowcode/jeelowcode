/*
Apache License
Version 2.0, January 2004
http://www.apache.org/licenses/LICENSE-2.0
本软件受适用的国家软件著作权法（包括国际条约）和开源协议 双重保护许可。

开源协议中文释意如下：
1.JeeLowCode开源版本无任何限制，在遵循本开源协议（Apache2.0）条款下，【允许商用】使用，不会造成侵权行为。
2.允许基于本平台软件开展业务系统开发。
3.在任何情况下，您不得使用本软件开发可能被认为与【本软件竞争】的软件。

最终解释权归：http://www.jeelowcode.com
*/
package com.jeelowcode.framework.utils.annotation;

import com.jeelowcode.framework.utils.component.devonly.DevOnlyChecker;
import com.jeelowcode.framework.utils.component.devonly.OldSqlValueProvider;
import com.jeelowcode.framework.utils.enums.DevOnlyPolicy;
import com.jeelowcode.framework.utils.enums.WriteMode;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 标记「编写 SQL / 在线脚本」类写操作入口：仅 local、dev 环境允许。
 * 生产环境按 fields + oldValue 做字段级旧值比对，仅当 SQL 字段发生变更时拒绝；
 * 需要额外业务规则（如在线增强仅允许停用）时通过 checker 判定。
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface JeeLowCodeDevOnly {

    /**
     * 受检字段名或路径。为空表示该方法是整接口受限写入（如 SQL 预览），无条件拒绝。
     */
    String[] fields() default {};

    /**
     * 旧值读取器，非 Noop 时启用「与库中旧值比对」。
     */
    Class<? extends OldSqlValueProvider> oldValue() default OldSqlValueProvider.Noop.class;

    /**
     * 附加业务规则。配置后以 checker 结果为准，不受字段比对是否命中影响。
     */
    Class<? extends DevOnlyChecker> checker() default DevOnlyChecker.Noop.class;

    /**
     * 写入模式。CREATE 强制旧快照为空，防止「请求携带已存在 id 把新增伪装成修改」。
     */
    WriteMode mode() default WriteMode.AUTO;

    /**
     * 命中受限后的处理策略。
     */
    DevOnlyPolicy policy() default DevOnlyPolicy.REJECT;

    /**
     * 自定义拒绝提示，为空时使用内置提示。
     */
    String message() default "";
}
