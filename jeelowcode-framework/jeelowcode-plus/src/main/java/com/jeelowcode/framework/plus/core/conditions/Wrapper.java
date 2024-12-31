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
package com.jeelowcode.framework.plus.core.conditions;



import com.jeelowcode.framework.plus.core.conditions.segments.MergeSegments;
import com.jeelowcode.framework.plus.core.conditions.segments.NormalSegmentList;
import com.jeelowcode.framework.plus.core.toolkit.Constants;
import com.jeelowcode.framework.plus.core.toolkit.StringPool;
import com.jeelowcode.framework.plus.core.toolkit.StringUtils;
import org.springframework.util.CollectionUtils;

import java.util.Objects;

/**
 * @author JX
 * @create 2024-03-26 9:53
 * @dedescription:
 */
public abstract class Wrapper<T> implements ISqlSegment {

    /**
     * 实体对象（子类实现）
     *
     * @return 泛型 T
     */
    public abstract T getEntity();

    public String getSqlSelect() {
        return null;
    }

    public String getSqlSet() {
        return null;
    }

    public String getSqlComment() {
        return null;
    }

    public String getSqlFirst() {
        return null;
    }

    /**
     * 获取 MergeSegments
     */
    public abstract MergeSegments getExpression();

    /**
     * 获取自定义SQL 简化自定义XML复杂情况
     * <p>
     * 使用方法: `select xxx from table` + ${ew.customSqlSegment}
     * <p>
     * 注意事项:
     * 1. 逻辑删除需要自己拼接条件 (之前自定义也同样)
     * 2. 不支持wrapper中附带实体的情况 (wrapper自带实体会更麻烦)
     * 3. 用法 ${ew.customSqlSegment} (不需要where标签包裹,切记!)
     * 4. ew是wrapper定义别名,不能使用其他的替换
     */
    public String getCustomSqlSegment() {
        MergeSegments expression = getExpression();
        if (Objects.nonNull(expression)) {
            NormalSegmentList normal = expression.getNormal();
            String sqlSegment = getSqlSegment();
            if (StringUtils.isNotBlank(sqlSegment)) {
                if (normal.isEmpty()) {
                    return sqlSegment;
                } else {
                    return Constants.WHERE + StringPool.SPACE + sqlSegment;
                }
            }
        }
        return StringPool.EMPTY;
    }

    public String getHavingCustomSqlSegment() {
        MergeSegments expression = getExpression();
        if (Objects.nonNull(expression)) {
            NormalSegmentList normal = expression.getNormal();
            String sqlSegment = getSqlSegment();
            if (StringUtils.isNotBlank(sqlSegment)) {
                if (normal.isEmpty()) {
                    return sqlSegment;
                } else {
                    return Constants.HAVING + StringPool.SPACE + sqlSegment;
                }
            }
        }
        return StringPool.EMPTY;
    }

    //和上面一样，只是不返回where ，直接原样凭接
    public String appendCustomSqlSegment() {
        MergeSegments expression = getExpression();
        if (Objects.nonNull(expression)) {
            String sqlSegment = getSqlSegment();
            sqlSegment=sqlSegment.substring(1,sqlSegment.length()-1);//去掉左右括号（）
            if (StringUtils.isNotBlank(sqlSegment)) {
                return sqlSegment;
            }
        }
        return StringPool.EMPTY;
    }

    /**
     * 查询条件为空(包含entity)
     */
    public boolean isEmptyOfWhere() {
        return isEmptyOfNormal() ;
    }

    /**
     * 查询条件不为空(包含entity)
     */
    public boolean isNonEmptyOfWhere() {
        return !isEmptyOfWhere();
    }

    @Deprecated
    public boolean nonEmptyOfWhere() {
        return isNonEmptyOfWhere();
    }

    /**
     * 查询条件为空(不包含entity)
     */
    public boolean isEmptyOfNormal() {
        return CollectionUtils.isEmpty(getExpression().getNormal());
    }

    /**
     * 查询条件为空(不包含entity)
     */
    public boolean isNonEmptyOfNormal() {
        return !isEmptyOfNormal();
    }

    @Deprecated
    public boolean nonEmptyOfNormal() {
        return isNonEmptyOfNormal();
    }


    /**
     * 获取格式化后的执行sql
     *
     * @return sql
     * @since 3.3.1
     */
    public String getTargetSql() {
        return getSqlSegment().replaceAll("#\\{.+?}", "?");
    }

    /**
     * 条件清空
     *
     * @since 3.3.1
     */
    abstract public void clear();
}
