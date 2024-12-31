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
package com.jeelowcode.framework.tenant.parse;

import cn.hutool.core.collection.CollUtil;
import com.jeelowcode.framework.exception.JeeLowCodeException;
import com.jeelowcode.framework.tenant.utils.JeeLowCodeTenantUtils;
import com.jeelowcode.framework.utils.utils.FuncBase;
import com.jeelowcode.framework.utils.utils.FuncWebBase;
import net.sf.jsqlparser.expression.*;
import net.sf.jsqlparser.expression.operators.conditional.AndExpression;
import net.sf.jsqlparser.expression.operators.conditional.OrExpression;
import net.sf.jsqlparser.expression.operators.relational.*;
import net.sf.jsqlparser.schema.Column;
import net.sf.jsqlparser.schema.Table;
import net.sf.jsqlparser.statement.delete.Delete;
import net.sf.jsqlparser.statement.insert.Insert;
import net.sf.jsqlparser.statement.select.*;
import net.sf.jsqlparser.statement.update.Update;
import net.sf.jsqlparser.statement.update.UpdateSet;
import org.springframework.util.AntPathMatcher;

import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 租户工具类
 */
public class JeeLowCodeTenantParse {
    private final AntPathMatcher pathMatcher;

    public JeeLowCodeTenantParse() {
        this.pathMatcher = new AntPathMatcher();
    }

    /**
     * select 处理查询语句
     * @param select
     */
    public static void processSelect(Select select) {
        JeeLowCodeTenantParse parse=new JeeLowCodeTenantParse();

        final String whereSegment = "";
        parse.processSelectBody(select.getSelectBody(), whereSegment);
        List<WithItem> withItemsList = select.getWithItemsList();
        if (!FuncBase.isEmpty(withItemsList)) {
            withItemsList.forEach(withItem -> parse.processSelectBody(withItem, whereSegment));
        }
    }

    /**
     * update 语句处理
     */
    public static void processUpdate(Update update) {
        JeeLowCodeTenantParse parse=new JeeLowCodeTenantParse();
        final Table table = update.getTable();
        final Object obj=null;
        ArrayList<UpdateSet> sets = update.getUpdateSets();
        if (!FuncBase.isEmpty(sets)) {
            sets.forEach(us -> us.getExpressions().forEach(ex -> {
                if (ex instanceof SubSelect) {
                    parse.processSelectBody(((SubSelect) ex).getSelectBody(), (String) obj);
                }
            }));
        }
        update.setWhere(parse.andExpression(table, update.getWhere(), (String) obj));
    }

    /**
     * delete 语句处理
     */
    public static void processDelete(Delete delete) {
        JeeLowCodeTenantParse parse=new JeeLowCodeTenantParse();
        final Object obj=null;
        delete.setWhere(parse.andExpression(delete.getTable(), delete.getWhere(), (String) obj));
    }

    /**
     * 新增
     * @param insert
     */
    public static void processInsert(Insert insert) {
        JeeLowCodeTenantParse parse=new JeeLowCodeTenantParse();

        Object obj=null;
        if (parse.ignoreTable(insert.getTable().getName())) {
            // 过滤退出执行
            return;
        }
        List<Column> columns = insert.getColumns();
        if (FuncBase.isEmpty(columns)) {
            // 针对不给列名的insert 不处理
            return;
        }
        String tenantIdColumn = JeeLowCodeTenantUtils.TENANT_ID_COLUMN;
        if (parse.ignoreInsert(columns, tenantIdColumn)) {
            // 针对已给出租户列的insert 不处理
            return;
        }
        columns.add(new Column(tenantIdColumn));

        // fixed gitee pulls/141 duplicate update
        List<Expression> duplicateUpdateColumns = insert.getDuplicateUpdateExpressionList();
        if (FuncBase.isNotEmpty(duplicateUpdateColumns)) {
            EqualsTo equalsTo = new EqualsTo();
            equalsTo.setLeftExpression(new StringValue(tenantIdColumn));
            equalsTo.setRightExpression(parse.getTenantId());
            duplicateUpdateColumns.add(equalsTo);
        }

        Select select = insert.getSelect();
        if (select != null && (select.getSelectBody() instanceof PlainSelect)) { //fix github issue 4998  修复升级到4.5版本的问题
            parse.processInsertSelect(select.getSelectBody(), (String) obj);
        } else if (insert.getItemsList() != null) {
            // fixed github pull/295
            ItemsList itemsList = insert.getItemsList();
            Expression tenantId = parse.getTenantId();
            if (itemsList instanceof MultiExpressionList) {
                ((MultiExpressionList) itemsList).getExpressionLists().forEach(el -> el.getExpressions().add(tenantId));
            } else {
                List<Expression> expressions = ((ExpressionList) itemsList).getExpressions();
                if (FuncBase.isNotEmpty(expressions)) {//fix github issue 4998 jsqlparse 4.5 批量insert ItemsList不是MultiExpressionList 了，需要特殊处理
                    int len = expressions.size();
                    for (int i = 0; i < len; i++) {
                        Expression expression = expressions.get(i);
                        if (expression instanceof RowConstructor) {
                            ((RowConstructor) expression).getExprList().getExpressions().add(tenantId);
                        } else if (expression instanceof Parenthesis) {
                            RowConstructor rowConstructor = new RowConstructor()
                                    .withExprList(new ExpressionList(((Parenthesis) expression).getExpression(), tenantId));
                            expressions.set(i, rowConstructor);
                        } else {
                            if (len - 1 == i) { // (?,?) 只有最后一个expre的时候才拼接tenantId
                                expressions.add(tenantId);
                            }
                        }
                    }
                } else {
                    expressions.add(tenantId);
                }
            }
        } else {
            throw new JeeLowCodeException("Failed to process multiple-table update, please exclude the tableName or statementId");
        }
    }

    /**
     * 追加 SelectItem  ok
     *
     * @param selectItems SelectItem
     */
    private void appendSelectItem(List<SelectItem> selectItems) {
        if (FuncBase.isEmpty(selectItems)) {
            return;
        }
        if (selectItems.size() == 1) {
            SelectItem item = selectItems.get(0);
            if (item instanceof AllColumns || item instanceof AllTableColumns) {
                return;
            }
        }
        selectItems.add(new SelectExpressionItem(new Column("tenant_id")));
    }

    /**
     * ok
     * 处理 insert into select
     * <p>
     * 进入这里表示需要 insert 的表启用了多租户,则 select 的表都启动了
     *
     * @param selectBody SelectBody
     */
    private void processInsertSelect(SelectBody selectBody, final String whereSegment) {
        PlainSelect plainSelect = (PlainSelect) selectBody;
        FromItem fromItem = plainSelect.getFromItem();
        if (fromItem instanceof Table) {
            // fixed gitee pulls/141 duplicate update
            processPlainSelect(plainSelect, whereSegment);
            appendSelectItem(plainSelect.getSelectItems());
        } else if (fromItem instanceof SubSelect) {
            SubSelect subSelect = (SubSelect) fromItem;
            appendSelectItem(plainSelect.getSelectItems());
            processInsertSelect(subSelect.getSelectBody(), whereSegment);
        }
    }
    /**
     * ok
     * delete update 语句 where 处理
     */
    private Expression andExpression(Table table, Expression where, final String whereSegment) {
        //获得where条件表达式
        final Expression expression = buildTableExpression(table, where, whereSegment);
        if (expression == null) {
            return where;
        }
        if (where != null) {
            if (where instanceof OrExpression) {
                return new AndExpression(new Parenthesis(where), expression);
            } else {
                return new AndExpression(where, expression);
            }
        }
        return expression;
    }

    //ok
    private void processSelectBody(SelectBody selectBody, final String whereSegment) {
        if (selectBody == null) {
            return;
        }
        if (selectBody instanceof PlainSelect) {
            processPlainSelect((PlainSelect) selectBody, whereSegment);
        } else if (selectBody instanceof WithItem) {
            WithItem withItem = (WithItem) selectBody;
            processSelectBody(withItem.getSubSelect().getSelectBody(), whereSegment);
        } else {
            SetOperationList operationList = (SetOperationList) selectBody;
            List<SelectBody> selectBodyList = operationList.getSelects();
            if (FuncBase.isNotEmpty(selectBodyList)) {
                selectBodyList.forEach(body -> processSelectBody(body, whereSegment));
            }
        }
    }
    /**
     * ok
     * 处理 PlainSelect
     */
    private void processPlainSelect(final PlainSelect plainSelect, final String whereSegment) {
        //#3087 github
        List<SelectItem> selectItems = plainSelect.getSelectItems();
        if (FuncBase.isNotEmpty(selectItems)) {
            selectItems.forEach(selectItem -> processSelectItem(selectItem, whereSegment));
        }

        // 处理 where 中的子查询
        Expression where = plainSelect.getWhere();
        processWhereSubSelect(where, whereSegment);

        // 处理 fromItem
        FromItem fromItem = plainSelect.getFromItem();
        List<Table> list = processFromItem(fromItem, whereSegment);
        List<Table> mainTables = new ArrayList<>(list);

        // 处理 join
        List<Join> joins = plainSelect.getJoins();
        if (FuncBase.isNotEmpty(joins)) {
            mainTables = processJoins(mainTables, joins, whereSegment);
        }

        // 当有 mainTable 时，进行 where 条件追加
        if (FuncBase.isNotEmpty(mainTables)) {
            plainSelect.setWhere(builderExpression(where, mainTables, whereSegment));
        }
        //System.out.println("plainSelect======"+JSONUtil.toJsonStr(plainSelect));
    }

    //ok
    private void processSelectItem(SelectItem selectItem, final String whereSegment) {
        if (selectItem instanceof SelectExpressionItem) {
            SelectExpressionItem selectExpressionItem = (SelectExpressionItem) selectItem;
            final Expression expression = selectExpressionItem.getExpression();
            if (expression instanceof SubSelect) {
                processSelectBody(((SubSelect) expression).getSelectBody(), whereSegment);
            } else if (expression instanceof Function) {
                processFunction((Function) expression, whereSegment);
            }
        }
    }

    /**
     * ok
     * 处理函数
     * <p>支持: 1. select fun(args..) 2. select fun1(fun2(args..),args..)<p>
     * <p> fixed gitee pulls/141</p>
     *
     * @param function
     */
    private void processFunction(Function function, final String whereSegment) {
        ExpressionList parameters = function.getParameters();
        if (parameters != null) {
            parameters.getExpressions().forEach(expression -> {
                if (expression instanceof SubSelect) {
                    processSelectBody(((SubSelect) expression).getSelectBody(), whereSegment);
                } else if (expression instanceof Function) {
                    processFunction((Function) expression, whereSegment);
                }
            });
        }
    }

    //ok
    private void processWhereSubSelect(Expression where, final String whereSegment) {
        if (where == null) {
            return;
        }
        if (where instanceof FromItem) {
            processOtherFromItem((FromItem) where, whereSegment);
            return;
        }
        if (where.toString().indexOf("SELECT") > 0) {
            // 有子查询
            if (where instanceof BinaryExpression) {
                // 比较符号 , and , or , 等等
                BinaryExpression expression = (BinaryExpression) where;
                processWhereSubSelect(expression.getLeftExpression(), whereSegment);
                processWhereSubSelect(expression.getRightExpression(), whereSegment);
            } else if (where instanceof InExpression) {
                // in
                InExpression expression = (InExpression) where;
                Expression inExpression = expression.getRightExpression();
                if (inExpression instanceof SubSelect) {
                    processSelectBody(((SubSelect) inExpression).getSelectBody(), whereSegment);
                }
            } else if (where instanceof ExistsExpression) {
                // exists
                ExistsExpression expression = (ExistsExpression) where;
                processWhereSubSelect(expression.getRightExpression(), whereSegment);
            } else if (where instanceof NotExpression) {
                // not exists
                NotExpression expression = (NotExpression) where;
                processWhereSubSelect(expression.getExpression(), whereSegment);
            } else if (where instanceof Parenthesis) {
                Parenthesis expression = (Parenthesis) where;
                processWhereSubSelect(expression.getExpression(), whereSegment);
            }
        }
    }



    /**
     * ok
     * 处理子查询等
     */
    private void processOtherFromItem(FromItem fromItem, final String whereSegment) {
        // 去除括号
        while (fromItem instanceof ParenthesisFromItem) {
            fromItem = ((ParenthesisFromItem) fromItem).getFromItem();
        }

        if (fromItem instanceof SubSelect) {
            SubSelect subSelect = (SubSelect) fromItem;
            if (subSelect.getSelectBody() != null) {
                processSelectBody(subSelect.getSelectBody(), whereSegment);
            }
        } else if (fromItem instanceof ValuesList) {
            //logger.debug("Perform a subQuery, if you do not give us feedback");
        } else if (fromItem instanceof LateralSubSelect) {
            LateralSubSelect lateralSubSelect = (LateralSubSelect) fromItem;
            if (lateralSubSelect.getSubSelect() != null) {
                SubSelect subSelect = lateralSubSelect.getSubSelect();
                if (subSelect.getSelectBody() != null) {
                    processSelectBody(subSelect.getSelectBody(), whereSegment);
                }
            }
        }
    }

    //ok
    private List<Table> processFromItem(FromItem fromItem, final String whereSegment) {
        // 处理括号括起来的表达式
        while (fromItem instanceof ParenthesisFromItem) {
            fromItem = ((ParenthesisFromItem) fromItem).getFromItem();
        }

        List<Table> mainTables = new ArrayList<>();
        // 无 join 时的处理逻辑
        if (fromItem instanceof Table) {
            Table fromTable = (Table) fromItem;
            mainTables.add(fromTable);
        } else if (fromItem instanceof SubJoin) {
            // SubJoin 类型则还需要添加上 where 条件
            List<Table> tables = processSubJoin((SubJoin) fromItem, whereSegment);
            mainTables.addAll(tables);
        } else {
            // 处理下 fromItem
            processOtherFromItem(fromItem, whereSegment);
        }
        return mainTables;
    }


    /**
     * ok
     * 处理 sub join
     *
     * @param subJoin subJoin
     * @return Table subJoin 中的主表
     */
    private List<Table> processSubJoin(SubJoin subJoin, final String whereSegment) {
        List<Table> mainTables = new ArrayList<>();
        if (subJoin.getJoinList() != null) {
            List<Table> list = processFromItem(subJoin.getLeft(), whereSegment);
            mainTables.addAll(list);
            mainTables = processJoins(mainTables, subJoin.getJoinList(), whereSegment);
        }
        return mainTables;
    }

    /**
     * 处理 joins
     *ok
     * @param mainTables 可以为 null
     * @param joins      join 集合
     * @return List<Table> 右连接查询的 Table 列表
     */
    private List<Table> processJoins(List<Table> mainTables, List<Join> joins, final String whereSegment) {
        // join 表达式中最终的主表
        Table mainTable = null;
        // 当前 join 的左表
        Table leftTable = null;

        if (mainTables.size() == 1) {
            mainTable = mainTables.get(0);
            leftTable = mainTable;
        }

        //对于 on 表达式写在最后的 join，需要记录下前面多个 on 的表名
        Deque<List<Table>> onTableDeque = new LinkedList<>();
        for (Join join : joins) {
            // 处理 on 表达式
            FromItem joinItem = join.getRightItem();

            // 获取当前 join 的表，subJoint 可以看作是一张表
            List<Table> joinTables = null;
            if (joinItem instanceof Table) {
                joinTables = new ArrayList<>();
                joinTables.add((Table) joinItem);
            } else if (joinItem instanceof SubJoin) {
                joinTables = processSubJoin((SubJoin) joinItem, whereSegment);
            }

            if (joinTables != null) {

                // 如果是隐式内连接
                if (join.isSimple()) {
                    mainTables.addAll(joinTables);
                    continue;
                }

                // 当前表是否忽略
                Table joinTable = joinTables.get(0);

                List<Table> onTables = null;
                // 如果不要忽略，且是右连接，则记录下当前表
                if (join.isRight()) {
                    mainTable = joinTable;
                    mainTables.clear();
                    if (leftTable != null) {
                        onTables = Collections.singletonList(leftTable);
                    }
                } else if (join.isInner()) {
                    if (mainTable == null) {
                        onTables = Collections.singletonList(joinTable);
                    } else {
                        onTables = Arrays.asList(mainTable, joinTable);
                    }
                    mainTable = null;
                    mainTables.clear();
                } else {
                    onTables = Collections.singletonList(joinTable);
                }

                if (mainTable != null && !mainTables.contains(mainTable)) {
                    mainTables.add(mainTable);
                }

                // 获取 join 尾缀的 on 表达式列表
                Collection<Expression> originOnExpressions = join.getOnExpressions();
                // 正常 join on 表达式只有一个，立刻处理
                if (originOnExpressions.size() == 1 && onTables != null) {
                    List<Expression> onExpressions = new LinkedList<>();
                    onExpressions.add(builderExpression(originOnExpressions.iterator().next(), onTables, whereSegment));
                    join.setOnExpressions(onExpressions);
                    leftTable = mainTable == null ? joinTable : mainTable;
                    continue;
                }
                // 表名压栈，忽略的表压入 null，以便后续不处理
                onTableDeque.push(onTables);
                // 尾缀多个 on 表达式的时候统一处理
                if (originOnExpressions.size() > 1) {
                    Collection<Expression> onExpressions = new LinkedList<>();
                    for (Expression originOnExpression : originOnExpressions) {
                        List<Table> currentTableList = onTableDeque.poll();
                        if (FuncBase.isEmpty(currentTableList)) {
                            onExpressions.add(originOnExpression);
                        } else {
                            onExpressions.add(builderExpression(originOnExpression, currentTableList, whereSegment));
                        }
                    }
                    join.setOnExpressions(onExpressions);
                }
                leftTable = joinTable;
            } else {
                processOtherFromItem(joinItem, whereSegment);
                leftTable = null;
            }
        }

        return mainTables;
    }

    /**
     * ok
     * 处理条件
     */
    private Expression builderExpression(Expression currentExpression, List<Table> tables, final String whereSegment) {
        // 没有表需要处理直接返回
        if (FuncBase.isEmpty(tables)) {
            return currentExpression;
        }
        // 构造每张表的条件
        List<Expression> expressions = tables.stream()
                .map(item -> buildTableExpression(item, currentExpression, whereSegment))
                .filter(Objects::nonNull)
                .collect(Collectors.toList());

        // 没有表需要处理直接返回
        if (FuncBase.isEmpty(expressions)) {
            return currentExpression;
        }

        // 注入的表达式
        Expression injectExpression = expressions.get(0);
        // 如果有多表，则用 and 连接
        if (expressions.size() > 1) {
            for (int i = 1; i < expressions.size(); i++) {
                injectExpression = new AndExpression(injectExpression, expressions.get(i));
            }
        }

        if (currentExpression == null) {
            return injectExpression;
        }
        if (currentExpression instanceof OrExpression) {
            return new AndExpression(new Parenthesis(currentExpression), injectExpression);
        } else {
            return new AndExpression(currentExpression, injectExpression);
        }
    }


    /**
     * 构建租户条件表达式
     *
     * @param table        表对象
     * @param where        当前where条件
     * @param whereSegment 所属Mapper对象全路径（在原租户拦截器功能中，这个参数并不需要参与相关判断）
     * @return 租户条件表达式
     *
     */
    private Expression buildTableExpression(final Table table, final Expression where, final String whereSegment) {
        if (ignoreTable(table.getName())) {
            return null;
        }
        return new EqualsTo(getAliasColumn(table), getTenantId());
    }


    /**
     * 租户字段别名设置
     * <p>tenantId 或 tableAlias.tenantId</p>
     *
     * @param table 表对象
     * @return 字段
     */
    private Column getAliasColumn(Table table) {
        StringBuilder column = new StringBuilder();
        // todo 该起别名就要起别名,禁止修改此处逻辑
        if (table.getAlias() != null) {
            column.append(table.getAlias().getName()).append(".");
        }
        column.append(JeeLowCodeTenantUtils.TENANT_ID_COLUMN);
        return new Column(column.toString());
    }

    //判断是否忽略租户
    private boolean ignoreTable(String tableName) {
        Set<String> ignoreLikeTables = JeeLowCodeTenantUtils.getIgnoreLikeTables();
        for(String table:ignoreLikeTables){
            if(table.endsWith("*")){// *结尾 -> tbl_*
                table = table.substring(0, table.length() - 1);
                if(tableName.startsWith(table)){//前缀开头
                    return true;//不需要进行租户过滤
                }
            }else if(table.startsWith("*")){// *开头 -> *_seq
                table = table.substring(1);
                if(tableName.endsWith(table)){//前缀开头
                    return true;//不需要进行租户过滤
                }
            }


        }
        HttpServletRequest request = FuncWebBase.getRequest();
        return JeeLowCodeTenantUtils.isIgnore() // 情况一，全局忽略多租户
                || JeeLowCodeTenantUtils.getIgnoreTables().contains(tableName) // 情况二，忽略多租户的表
                || isIgnoreUrl(request);// 情况二，忽略url

    }

    private boolean isIgnoreUrl(HttpServletRequest request) {
        if(FuncBase.isEmpty(request)){
            return false;
        }
        String requestURI = request.getRequestURI();
        if(FuncBase.isEmpty(requestURI)){
            return false;
        }
        // 快速匹配，保证性能
        if (CollUtil.contains(JeeLowCodeTenantUtils.getIGNORE_URLS(),requestURI)) {
            return true;
        }
        // 逐个 Ant 路径匹配
        for (String url : JeeLowCodeTenantUtils.getIGNORE_URLS()) {
            if (pathMatcher.match(url, request.getRequestURI())) {
                return true;
            }
        }
        return false;
    }


    //获取租户id
    private Expression getTenantId() {
        Long tenantId = JeeLowCodeTenantUtils.getTenantId();
        if(FuncBase.isEmpty(tenantId)){
            tenantId=-1L;
        }
        //拿到当前租户
        return new LongValue(tenantId);
    }

    /**
     * 忽略插入租户字段逻辑
     *
     * @param columns        插入字段
     * @param tenantIdColumn 租户 ID 字段
     * @return
     */
    private boolean ignoreInsert(List<Column> columns, String tenantIdColumn) {
        return columns.stream().map(Column::getColumnName).anyMatch(i -> i.equalsIgnoreCase(tenantIdColumn));
    }
}
