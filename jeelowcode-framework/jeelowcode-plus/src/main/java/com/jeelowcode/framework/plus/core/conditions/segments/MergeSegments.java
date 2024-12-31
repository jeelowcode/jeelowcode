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
import com.jeelowcode.framework.plus.core.toolkit.StringPool;

import java.util.Arrays;
import java.util.List;

/**
 * @author JX
 * @create 2024-03-26 10:09
 * @dedescription:
 */
public class MergeSegments implements ISqlSegment {

    private final NormalSegmentList normal = new NormalSegmentList();
    private final GroupBySegmentList groupBy = new GroupBySegmentList();
    private final HavingSegmentList having = new HavingSegmentList();
    private final OrderBySegmentList orderBy = new OrderBySegmentList();


    private String sqlSegment = StringPool.EMPTY;

    private boolean cacheSqlSegment = true;

    public void add(ISqlSegment... iSqlSegments) {
        List<ISqlSegment> list = Arrays.asList(iSqlSegments);
        ISqlSegment firstSqlSegment = list.get(0);
        if (MatchSegment.ORDER_BY.match(firstSqlSegment)) {
            orderBy.addAll(list);
        } else if (MatchSegment.GROUP_BY.match(firstSqlSegment)) {
            groupBy.addAll(list);
        } else if (MatchSegment.HAVING.match(firstSqlSegment)) {
            having.addAll(list);
        } else {
            normal.addAll(list);
        }
        cacheSqlSegment = false;
    }

    @Override
    public String getSqlSegment() {
        if (cacheSqlSegment) {
            return sqlSegment;
        }
        cacheSqlSegment = true;
        if (normal.isEmpty()) {
            if (!groupBy.isEmpty() || !orderBy.isEmpty()) {
                sqlSegment = groupBy.getSqlSegment() + having.getSqlSegment() + orderBy.getSqlSegment();
            }
        } else {
            sqlSegment = normal.getSqlSegment() + groupBy.getSqlSegment() + having.getSqlSegment() + orderBy.getSqlSegment();
        }
        return sqlSegment;
    }

    /**
     * 清理
     *
     * @since 3.3.1
     */
    public void clear() {
        sqlSegment = StringPool.EMPTY;
        cacheSqlSegment = true;
        normal.clear();
        groupBy.clear();
        having.clear();
        orderBy.clear();
    }

    public NormalSegmentList getNormal() {
        return normal;
    }

    public GroupBySegmentList getGroupBy() {
        return groupBy;
    }

    public HavingSegmentList getHaving() {
        return having;
    }

    public OrderBySegmentList getOrderBy() {
        return orderBy;
    }

    public void setSqlSegment(String sqlSegment) {
        this.sqlSegment = sqlSegment;
    }

    public boolean isCacheSqlSegment() {
        return cacheSqlSegment;
    }

    public void setCacheSqlSegment(boolean cacheSqlSegment) {
        this.cacheSqlSegment = cacheSqlSegment;
    }
}