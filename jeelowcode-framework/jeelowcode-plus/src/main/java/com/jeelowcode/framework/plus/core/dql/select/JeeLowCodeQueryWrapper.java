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
package com.jeelowcode.framework.plus.core.dql.select;



import com.jeelowcode.framework.plus.core.conditions.AbstractWrapper;
import com.jeelowcode.framework.plus.core.conditions.SharedString;
import com.jeelowcode.framework.plus.core.conditions.segments.MergeSegments;
import com.jeelowcode.framework.plus.core.exceptions.JeelowcodeSqlException;
import com.jeelowcode.framework.plus.core.toolkit.CollectionUtils;
import com.jeelowcode.framework.plus.core.toolkit.StringPool;
import com.jeelowcode.framework.plus.core.toolkit.sql.SqlInjectionUtils;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Entity 对象封装操作类
 *
 * @author hubin miemie HCL
 * @since 2018-05-25
 */
public class JeeLowCodeQueryWrapper<T> extends AbstractWrapper<T, String, JeeLowCodeQueryWrapper<T>>
    implements Query<JeeLowCodeQueryWrapper<T>, T, String> {

    /**
     * 查询字段
     */
    protected final SharedString sqlSelect = new SharedString();



    public JeeLowCodeQueryWrapper() {
        this((T) null);
    }

    public JeeLowCodeQueryWrapper(T entity) {
        super.setEntity(entity);
        super.initNeed();
    }

    public JeeLowCodeQueryWrapper(Class<T> entityClass) {
        super.setEntityClass(entityClass);
        super.initNeed();
    }

    public JeeLowCodeQueryWrapper(T entity, String... columns) {
        super.setEntity(entity);
        super.initNeed();
        this.select(Arrays.asList(columns));
    }

    /**
     * 非对外公开的构造方法,只用于生产嵌套 sql
     *
     * @param entityClass 本不应该需要的
     */
    private JeeLowCodeQueryWrapper(T entity, Class<T> entityClass, AtomicInteger paramNameSeq,
                                   Map<String, Object> paramNameValuePairs, MergeSegments mergeSegments, SharedString paramAlias,
                                   SharedString lastSql, SharedString sqlComment, SharedString sqlFirst) {
        super.setEntity(entity);
        super.setEntityClass(entityClass);
        this.paramNameSeq = paramNameSeq;
        this.paramNameValuePairs = paramNameValuePairs;
        this.expression = mergeSegments;
        this.paramAlias = paramAlias;
        this.lastSql = lastSql;
        this.sqlComment = sqlComment;
        this.sqlFirst = sqlFirst;
    }

    private JeeLowCodeQueryWrapper(T entity, Class<T> entityClass, AtomicInteger paramNameSeq,
                                   Map<String, Object> paramNameValuePairs, MergeSegments mergeSegments, SharedString paramAlias,
                                   SharedString lastSql, SharedString sqlComment, SharedString sqlFirst, String tableName) {
        super.setEntity(entity);
        super.setEntityClass(entityClass);
        this.paramNameSeq = paramNameSeq;
        this.paramNameValuePairs = paramNameValuePairs;
        this.expression = mergeSegments;
        this.paramAlias = paramAlias;
        this.lastSql = lastSql;
        this.sqlComment = sqlComment;
        this.sqlFirst = sqlFirst;
    }

    /**
     * 检查 SQL 注入过滤
     */
    private boolean checkSqlInjection;

    /**
     * 开启检查 SQL 注入
     */
    public JeeLowCodeQueryWrapper<T> checkSqlInjection() {
        this.checkSqlInjection = true;
        return this;
    }

    @Override
    protected String columnToString(String column) {
        if (checkSqlInjection && SqlInjectionUtils.check(column)) {
            throw new JeelowcodeSqlException("Discovering SQL injection column: " + column);
        }
        return column;
    }

    @Override
    public JeeLowCodeQueryWrapper<T> select(boolean condition, List<String> columns) {
        if (condition && CollectionUtils.isNotEmpty(columns)) {
            this.sqlSelect.setStringValue(String.join(StringPool.COMMA, columns));
        }
        return typedThis;
    }



    @Override
    public String getSqlSelect() {
        return sqlSelect.getStringValue();
    }


    /**
     * 用于生成嵌套 sql
     * <p>
     * 故 sqlSelect 不向下传递
     * </p>
     */
    @Override
    protected JeeLowCodeQueryWrapper<T> instance() {
        return new JeeLowCodeQueryWrapper<>(getEntity(), getEntityClass(), paramNameSeq, paramNameValuePairs, new MergeSegments(),
            paramAlias, SharedString.emptyString(), SharedString.emptyString(), SharedString.emptyString());
    }

    @Override
    public void clear() {
        super.clear();
        sqlSelect.toNull();
    }

}
