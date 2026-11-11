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
package com.jeelowcode.framework.plus.build.build.dql;


import com.jeelowcode.framework.plus.build.ISQLExpression;
import com.jeelowcode.framework.plus.build.buildmodel.SQLInterpretContext;
import com.jeelowcode.framework.plus.build.buildmodel.dql.SqlInfoOrderModel;
import com.jeelowcode.framework.plus.core.dql.select.JeeLowCodeQueryWrapper;
import com.jeelowcode.framework.plus.core.exceptions.JeelowcodeSqlException;
import com.jeelowcode.framework.plus.core.model.SqlFormatModel;
import net.sf.jsqlparser.JSQLParserException;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

/**
 * 排序
 */
public class BuildOrderBy implements ISQLExpression<SqlFormatModel>  {

    /**
     * ORDER BY 列名安全格式：字段名 或 别名.字段名，不允许表达式、子查询、注释等任何其他内容
     * 这是防止 ORDER BY 注入的最后一道闸门，任何未经白名单的场景在此都会被拦截
     */
    private static final Pattern SAFE_ORDER_BY_COLUMN_PATTERN =
            Pattern.compile("^[A-Za-z_][A-Za-z0-9_]*(\\.[A-Za-z_][A-Za-z0-9_]*)?$");

    /**
     * 校验排序字段是否安全，不安全则直接抛异常
     */
    private void checkColumn(String column) {
        if (column == null || !SAFE_ORDER_BY_COLUMN_PATTERN.matcher(column.trim()).matches()) {
            throw new JeelowcodeSqlException("Discovering SQL injection order by column: " + column);
        }
    }

    @Override
    public SqlFormatModel interpret(SQLInterpretContext context) throws JSQLParserException {
        JeeLowCodeQueryWrapper wrapper=new JeeLowCodeQueryWrapper().checkSqlInjection();

        SqlInfoOrderModel sqlInfoOrderModel = context.getOrderModel();
        List<SqlInfoOrderModel.OrderByModel> orderModels = sqlInfoOrderModel.getOrderByList();
        orderModels.forEach(orderModel -> {
            String[] columns = orderModel.getColumns();//列
            boolean isAsc = orderModel.isAsc();
            Arrays.asList(columns).forEach(this::checkColumn);
            wrapper.orderBy(true,isAsc, Arrays.asList(columns));
        });

        String orderByStr = wrapper.getCustomSqlSegment();

        return new SqlFormatModel(orderByStr);
    }



}
