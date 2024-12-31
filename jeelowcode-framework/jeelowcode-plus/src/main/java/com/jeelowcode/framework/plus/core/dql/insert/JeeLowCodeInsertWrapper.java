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
package com.jeelowcode.framework.plus.core.dql.insert;



import com.jeelowcode.framework.plus.core.conditions.AbstractWrapper;
import com.jeelowcode.framework.plus.core.conditions.SharedString;
import com.jeelowcode.framework.plus.core.conditions.segments.MergeSegments;
import com.jeelowcode.framework.plus.core.dql.update.Update;
import com.jeelowcode.framework.plus.core.toolkit.CollectionUtils;
import com.jeelowcode.framework.plus.core.toolkit.Constants;
import com.jeelowcode.framework.plus.core.toolkit.StringUtils;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author JX
 * @create 2024-03-27 17:47
 * @dedescription:
 */
public class JeeLowCodeInsertWrapper<T> extends AbstractWrapper<T, String, JeeLowCodeInsertWrapper<T>>
        implements Update<JeeLowCodeInsertWrapper<T>, String> {
    private final List<String> sqlSet;


    public JeeLowCodeInsertWrapper() {
        // 如果无参构造函数，请注意实体 NULL 情况 SET 必须有否则 SQL 异常
        this(null);
    }

    public JeeLowCodeInsertWrapper(T entity) {
        super.setEntity(entity);
        super.initNeed();
        this.sqlSet = new ArrayList<>();
    }

    private JeeLowCodeInsertWrapper(T entity, List<String> sqlSet, AtomicInteger paramNameSeq,
                                    Map<String, Object> paramNameValuePairs, MergeSegments mergeSegments, SharedString paramAlias,
                                    SharedString lastSql, SharedString sqlComment, SharedString sqlFirst) {
        super.setEntity(entity);
        this.sqlSet = sqlSet;
        this.paramNameSeq = paramNameSeq;
        this.paramNameValuePairs = paramNameValuePairs;
        this.expression = mergeSegments;
        this.paramAlias = paramAlias;
        this.lastSql = lastSql;
        this.sqlComment = sqlComment;
        this.sqlFirst = sqlFirst;
    }



    @Override
    public String getSqlSet() {
        if (CollectionUtils.isEmpty(sqlSet)) {
            return null;
        }
        return String.join(Constants.COMMA, sqlSet);
    }

    @Override
    public JeeLowCodeInsertWrapper<T> set(boolean condition, String column, Object val, String mapping) {
        return maybeDo(condition, () -> {
            String sql = formatParam(mapping, val);
            sqlSet.add(column + Constants.EQUALS + sql);
        });
    }

    @Override
    public JeeLowCodeInsertWrapper<T> setSql(boolean condition, String setSql, Object... params) {
        if (condition && StringUtils.isNotBlank(setSql)) {
            sqlSet.add(formatSqlMaybeWithParam(setSql, params));
        }
        return typedThis;
    }


    @Override
    protected JeeLowCodeInsertWrapper<T> instance() {
        return new JeeLowCodeInsertWrapper<>(getEntity(), null, paramNameSeq, paramNameValuePairs, new MergeSegments(),
                paramAlias, SharedString.emptyString(), SharedString.emptyString(), SharedString.emptyString());
    }

    @Override
    public void clear() {
        super.clear();
        sqlSet.clear();
    }


}
