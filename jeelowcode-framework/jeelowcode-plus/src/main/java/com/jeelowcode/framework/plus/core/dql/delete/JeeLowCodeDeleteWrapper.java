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
package com.jeelowcode.framework.plus.core.dql.delete;


import com.jeelowcode.framework.plus.core.conditions.AbstractWrapper;
import com.jeelowcode.framework.plus.core.conditions.SharedString;
import com.jeelowcode.framework.plus.core.conditions.segments.MergeSegments;

import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author JX
 * @create 2024-03-27 17:46
 * @dedescription:
 */
public class JeeLowCodeDeleteWrapper<T> extends AbstractWrapper<T, String, JeeLowCodeDeleteWrapper<T>> {



    public JeeLowCodeDeleteWrapper() {
        this((T) null);
    }

    public JeeLowCodeDeleteWrapper(T entity) {
        super.setEntity(entity);
        super.initNeed();
    }


    public JeeLowCodeDeleteWrapper(AtomicInteger paramNameSeq,
                                   Map<String, Object> paramNameValuePairs, MergeSegments mergeSegments, SharedString paramAlias,
                                   SharedString lastSql, SharedString sqlComment){
        this.paramNameSeq = paramNameSeq;
        this.paramNameValuePairs = paramNameValuePairs;
        this.paramAlias = paramAlias;
        this.lastSql = lastSql;
        this.sqlComment = sqlComment;
        this.expression = new MergeSegments();
    }



    @Override
    protected JeeLowCodeDeleteWrapper instance() {
        return new JeeLowCodeDeleteWrapper( paramNameSeq , paramNameValuePairs , expression , paramAlias , SharedString.emptyString() , SharedString.emptyString());
    }


    public void orderby(){
        return;
    }

    public void groupBy(){
    }
}
