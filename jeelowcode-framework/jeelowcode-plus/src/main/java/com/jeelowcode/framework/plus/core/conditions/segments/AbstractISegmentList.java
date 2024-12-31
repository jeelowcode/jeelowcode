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

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author JX
 * @create 2024-03-26 10:02
 * @dedescription:
 */
public abstract class AbstractISegmentList extends ArrayList<ISqlSegment> implements ISqlSegment, StringPool {

    /**
     * 最后一个值
     */
    ISqlSegment lastValue = null;
    /**
     * 刷新 lastValue
     */
    boolean flushLastValue = false;
    /**
     * 结果集缓存
     */
    private String sqlSegment = EMPTY;
    /**
     * 是否缓存过结果集
     */
    private boolean cacheSqlSegment = true;

    /**
     * 重写方法,做个性化适配
     *
     * @param c 元素集合
     * @return 是否添加成功
     */
    @Override
    public boolean addAll(Collection<? extends ISqlSegment> c) {
        List<ISqlSegment> list = new ArrayList<>(c);
        boolean goon = transformList(list, list.get(0), list.get(list.size() - 1));
        if (goon) {
            cacheSqlSegment = false;
            if (flushLastValue) {
                this.flushLastValue(list);
            }
            return super.addAll(list);
        }
        return false;
    }

    /**
     * 在其中对值进行判断以及更改 list 的内部元素
     *
     * @param list         传入进来的 ISqlSegment 集合
     * @param firstSegment ISqlSegment 集合里第一个值
     * @param lastSegment  ISqlSegment 集合里最后一个值
     * @return true 是否继续向下执行; false 不再向下执行
     */
    protected abstract boolean transformList(List<ISqlSegment> list, ISqlSegment firstSegment, ISqlSegment lastSegment);

    /**
     * 刷新属性 lastValue
     */
    private void flushLastValue(List<ISqlSegment> list) {
        lastValue = list.get(list.size() - 1);
    }

    /**
     * 删除元素里最后一个值</br>
     * 并刷新属性 lastValue
     */
    void removeAndFlushLast() {
        remove(size() - 1);
        flushLastValue(this);
    }

    @Override
    public String getSqlSegment() {
        if (cacheSqlSegment) {
            return sqlSegment;
        }
        cacheSqlSegment = true;
        sqlSegment = childrenSqlSegment();
        return sqlSegment;
    }

    /**
     * 只有该类进行过 addAll 操作,才会触发这个方法
     * <p>
     * 方法内可以放心进行操作
     *
     * @return sqlSegment
     */
    protected abstract String childrenSqlSegment();

    @Override
    public void clear() {
        super.clear();
        lastValue = null;
        sqlSegment = EMPTY;
        cacheSqlSegment = true;
    }
}