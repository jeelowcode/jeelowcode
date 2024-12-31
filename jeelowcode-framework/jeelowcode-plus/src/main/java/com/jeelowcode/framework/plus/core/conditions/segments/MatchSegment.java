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
package com.jeelowcode.framework.plus.core.conditions.segments;


import com.jeelowcode.framework.plus.core.conditions.ISqlSegment;
import com.jeelowcode.framework.plus.core.enums.SqlKeyword;
import com.jeelowcode.framework.plus.core.enums.WrapperKeyword;


import java.util.function.Predicate;

/**
 * @author JX
 * @create 2024-03-26 10:07
 * @dedescription:
 */
public enum MatchSegment {
    GROUP_BY(i -> i == SqlKeyword.GROUP_BY),
    ORDER_BY(i -> i == SqlKeyword.ORDER_BY),
    NOT(i -> i == SqlKeyword.NOT),
    AND(i -> i == SqlKeyword.AND),
    OR(i -> i == SqlKeyword.OR),
    AND_OR(i -> i == SqlKeyword.AND || i == SqlKeyword.OR),
    EXISTS(i -> i == SqlKeyword.EXISTS),
    HAVING(i -> i == SqlKeyword.HAVING),
    APPLY(i -> i == WrapperKeyword.APPLY);

    private final Predicate<ISqlSegment> predicate;

    public boolean match(ISqlSegment segment) {
        return getPredicate().test(segment);
    }

    MatchSegment(Predicate<ISqlSegment> predicate) {
        this.predicate = predicate;
    }

    public Predicate<ISqlSegment> getPredicate() {
        return predicate;
    }
}
