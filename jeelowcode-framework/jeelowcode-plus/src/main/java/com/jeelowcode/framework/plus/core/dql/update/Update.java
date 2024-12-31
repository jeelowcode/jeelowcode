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

import java.io.Serializable;

/**
 * @author miemie
 * @since 2018-12-12
 */
public interface Update<Children, R> extends Serializable {

    /**
     * 设置 更新 SQL 的 SET 片段
     *
     * @param column 字段
     * @param val    值
     * @return children
     */
    default Children set(R column, Object val) {
        return set(true, column, val);
    }

    /**
     * 设置 更新 SQL 的 SET 片段
     *
     * @param condition 是否加入 set
     * @param column    字段
     * @param val       值
     * @return children
     */
    default Children set(boolean condition, R column, Object val) {
        return set(condition, column, val, null);
    }

    /**
     * 设置 更新 SQL 的 SET 片段
     *
     * @param column  字段
     * @param val     值
     * @param mapping 例: javaType=int,jdbcType=NUMERIC,typeHandler=xxx.xxx.MyTypeHandler
     * @return children
     */
    default Children set(R column, Object val, String mapping) {
        return set(true, column, val, mapping);
    }

    /**
     * 设置 更新 SQL 的 SET 片段
     *
     * @param condition 是否加入 set
     * @param column    字段
     * @param val       值
     * @param mapping   例: javaType=int,jdbcType=NUMERIC,typeHandler=xxx.xxx.MyTypeHandler
     * @return children
     */
    Children set(boolean condition, R column, Object val, String mapping);

    /**
     * 设置 更新 SQL 的 SET 片段
     *
     * @param setSql set sql
     *               例1: setSql("id=1")
     *               例2: apply("dateColumn={0}", LocalDate.now())
     *               例3: apply("dateColumn={0}", LocalDate.now())
     *               例4: apply("name={0,javaType=int,jdbcType=NUMERIC,typeHandler=xxx.xxx.MyTypeHandler}", "老王")
     * @return children
     */
    default Children setSql(String setSql, Object... params) {
        return setSql(true, setSql, params);
    }

    /**
     * 设置 更新 SQL 的 SET 片段
     *
     * @param condition 执行条件
     * @param setSql    set sql
     *                  例1: setSql("id=1")
     *                  例2: apply("dateColumn={0}", LocalDate.now())
     *                  例3: apply("dateColumn={0}", LocalDate.now())
     *                  例4: apply("name={0,javaType=int,jdbcType=NUMERIC,typeHandler=xxx.xxx.MyTypeHandler}", "老王")
     * @return children
     */
    Children setSql(boolean condition, String setSql, Object... params);

    /**
     * 获取 更新 SQL 的 SET 片段
     */
    String getSqlSet();
}
