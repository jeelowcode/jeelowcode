/*
 * Copyright (c) 2011-2023, baomidou (jobob@qq.com).
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *//*
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
package com.jeelowcode.framework.plus.core.dql.update;



import com.jeelowcode.framework.plus.core.conditions.AbstractWrapper;
import com.jeelowcode.framework.plus.core.conditions.SharedString;
import com.jeelowcode.framework.plus.core.conditions.segments.MergeSegments;
import com.jeelowcode.framework.plus.core.toolkit.CollectionUtils;
import com.jeelowcode.framework.plus.core.toolkit.Constants;
import com.jeelowcode.framework.plus.core.toolkit.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Update 条件封装
 *
 * @author hubin miemie HCL
 * @since 2018-05-30
 */
public class JeeLowCodeUpdateWrapper<T> extends AbstractWrapper<T, String, JeeLowCodeUpdateWrapper<T>>
    implements Update<JeeLowCodeUpdateWrapper<T>, String> {

    /**
     * SQL 更新字段内容，例如：name='1', age=2
     */
    private final List<String> sqlSet;


    public JeeLowCodeUpdateWrapper() {
        // 如果无参构造函数，请注意实体 NULL 情况 SET 必须有否则 SQL 异常
        this(null);
    }

    public JeeLowCodeUpdateWrapper(T entity) {
        super.setEntity(entity);
        super.initNeed();
        this.sqlSet = new ArrayList<>();
    }

    private JeeLowCodeUpdateWrapper(T entity, List<String> sqlSet, AtomicInteger paramNameSeq,
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
    public JeeLowCodeUpdateWrapper<T> set(boolean condition, String column, Object val, String mapping) {
        return maybeDo(condition, () -> {
            String sql = formatParam(mapping, val);
            sqlSet.add(column + Constants.EQUALS + sql);
        });
    }

    @Override
    public JeeLowCodeUpdateWrapper<T> setSql(boolean condition, String setSql, Object... params) {
        if (condition && StringUtils.isNotBlank(setSql)) {
            sqlSet.add(formatSqlMaybeWithParam(setSql, params));
        }
        return typedThis;
    }


    @Override
    protected JeeLowCodeUpdateWrapper<T> instance() {
        return new JeeLowCodeUpdateWrapper<>(getEntity(), null, paramNameSeq, paramNameValuePairs, new MergeSegments(),
            paramAlias, SharedString.emptyString(), SharedString.emptyString(), SharedString.emptyString());
    }

    @Override
    public void clear() {
        super.clear();
        sqlSet.clear();
    }


}
