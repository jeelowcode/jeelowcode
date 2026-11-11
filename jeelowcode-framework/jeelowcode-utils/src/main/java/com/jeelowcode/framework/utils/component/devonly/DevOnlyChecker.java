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
package com.jeelowcode.framework.utils.component.devonly;

/**
 * 字段比对表达不了的附加业务规则。
 *
 * 实现类位于 jeelowcode-core，须注册为 Spring Bean。
 * 一旦配置，切面以 isRestricted 结果为准，不再依赖字段快照是否命中。
 */
public interface DevOnlyChecker {

    boolean isRestricted(Object[] args);

    /**
     * policy=SANITIZE 时清洗参数，本期未启用。
     */
    default void sanitize(Object[] args) {
    }

    /**
     * 未配置业务规则时使用的空实现。
     */
    class Noop implements DevOnlyChecker {

        @Override
        public boolean isRestricted(Object[] args) {
            return false;
        }
    }
}
