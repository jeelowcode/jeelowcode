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
package com.jeelowcode.framework.plus.core.toolkit;


import com.jeelowcode.framework.plus.core.dql.select.JeeLowCodeQueryWrapper;
import com.jeelowcode.framework.plus.core.conditions.segments.MergeSegments;
import com.jeelowcode.framework.plus.core.dql.update.JeeLowCodeUpdateWrapper;

/**
 * Wrapper 条件构造
 *
 * @author Caratacus
 */
public final class Wrappers {

    /**
     * 空的 EmptyWrapper
     */
    private static final JeeLowCodeQueryWrapper<?> emptyWrapper = new EmptyWrapper<>();

    private Wrappers() {
        // ignore
    }

    /**
     * 获取 QueryWrapper&lt;T&gt;
     *
     * @param <T> 实体类泛型
     * @return QueryWrapper&lt;T&gt;
     */
    public static <T> JeeLowCodeQueryWrapper<T> query() {
        return new JeeLowCodeQueryWrapper<>();
    }

    /**
     * 获取 QueryWrapper&lt;T&gt;
     *
     * @param entity 实体类
     * @param <T>    实体类泛型
     * @return QueryWrapper&lt;T&gt;
     */
    public static <T> JeeLowCodeQueryWrapper<T> query(T entity) {
        return new JeeLowCodeQueryWrapper<>(entity);
    }

    /**
     * 获取 QueryWrapper&lt;T&gt;
     *
     * @param entityClass 实体类class
     * @param <T>    实体类泛型
     * @return QueryWrapper&lt;T&gt;
     */
    public static <T> JeeLowCodeQueryWrapper<T> query(Class<T> entityClass) {
        return new JeeLowCodeQueryWrapper<>(entityClass);
    }



    /**
     * 获取 UpdateWrapper&lt;T&gt;
     *
     * @param <T> 实体类泛型
     * @return UpdateWrapper&lt;T&gt;
     */
    public static <T> JeeLowCodeUpdateWrapper<T> update() {
        return new JeeLowCodeUpdateWrapper<>();
    }

    /**
     * 获取 UpdateWrapper&lt;T&gt;
     *
     * @param entity 实体类
     * @param <T>    实体类泛型
     * @return UpdateWrapper&lt;T&gt;
     */
    public static <T> JeeLowCodeUpdateWrapper<T> update(T entity) {
        return new JeeLowCodeUpdateWrapper<>(entity);
    }


    /**
     * 获取 EmptyWrapper&lt;T&gt;
     *
     * @param <T> 任意泛型
     * @return EmptyWrapper&lt;T&gt;
     * @see EmptyWrapper
     */
    @SuppressWarnings("unchecked")
    public static <T> JeeLowCodeQueryWrapper<T> emptyWrapper() {
        return (JeeLowCodeQueryWrapper<T>) emptyWrapper;
    }

    /**
     * 一个空的QueryWrapper子类该类不包含任何条件
     *
     * @param <T>
     * @see JeeLowCodeQueryWrapper
     */
    private static class EmptyWrapper<T> extends JeeLowCodeQueryWrapper<T> {

        private static final long serialVersionUID = -2515957613998092272L;

        @Override
        public T getEntity() {
            return null;
        }

        @Override
        public EmptyWrapper<T> setEntity(T entity) {
            throw new UnsupportedOperationException();
        }

        @Override
        public JeeLowCodeQueryWrapper<T> setEntityClass(Class<T> entityClass) {
            throw new UnsupportedOperationException();
        }

        @Override
        public Class<T> getEntityClass() {
            return null;
        }

        @Override
        public String getSqlSelect() {
            return null;
        }

        @Override
        public MergeSegments getExpression() {
            return null;
        }

        @Override
        public boolean isEmptyOfWhere() {
            return true;
        }

        @Override
        public boolean isEmptyOfNormal() {
            return true;
        }


        public boolean isNonEmptyOfEntity() {
            return !isEmptyOfEntity();
        }


        public boolean isEmptyOfEntity() {
            return true;
        }

        @Override
        protected void initNeed() {
        }

        @Override
        public EmptyWrapper<T> last(boolean condition, String lastSql) {
            throw new UnsupportedOperationException();
        }

        @Override
        public String getSqlSegment() {
            return null;
        }

        @Override
        protected String columnsToString(String... columns) {
            return null;
        }

        @Override
        protected String columnToString(String column) {
            return null;
        }

        @Override
        protected EmptyWrapper<T> instance() {
            throw new UnsupportedOperationException();
        }

        @Override
        public void clear() {
            throw new UnsupportedOperationException();
        }
    }
}
