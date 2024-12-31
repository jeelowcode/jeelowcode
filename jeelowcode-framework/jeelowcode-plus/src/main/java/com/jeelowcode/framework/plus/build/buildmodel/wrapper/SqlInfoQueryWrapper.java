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
package com.jeelowcode.framework.plus.build.buildmodel.wrapper;

import com.jeelowcode.framework.plus.SqlHelper;
import com.jeelowcode.framework.plus.build.ISQLExpression;
import com.jeelowcode.framework.plus.build.build.AndExpression;
import com.jeelowcode.framework.plus.build.build.dql.*;
import com.jeelowcode.framework.plus.build.buildmodel.SQLInterpretContext;
import com.jeelowcode.framework.plus.build.buildmodel.dql.*;
import com.jeelowcode.framework.plus.core.model.SqlFormatModel;
import com.jeelowcode.framework.utils.constant.JeeLowCodeConstant;
import com.jeelowcode.framework.utils.utils.FuncBase;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

/**
 * 查询-封装器
 */
public class SqlInfoQueryWrapper {


    public static Wrapper wrapper(String dbSchemaName) {
        return new Wrapper(dbSchemaName);
    }

    public static class Wrapper extends BaseWrapper {

        private String dbSchemaName;
        private SqlInfoSelectModel.Builder builderSelect = SqlInfoSelectModel.builder();
        private SqlInfoWhereModel.Builder builderWhere = SqlInfoWhereModel.builder();
        private SqlInfoOrderModel.Builder builderOrderBy = SqlInfoOrderModel.builder();
        private SqlInfoGroupModel.Builder builderGroupBy = SqlInfoGroupModel.builder();
        private SqlInfoHavingModel.Builder builderHaving = SqlInfoHavingModel.builder();
        private SqlInfoJoinModel.Builder builderJoin = SqlInfoJoinModel.builder();

        public Wrapper(String dbSchemaName) {
            this.dbSchemaName = dbSchemaName;
        }

        //select 相关
        public Wrapper select(String... columns) {
            if (FuncBase.isNotEmpty(columns)) {
                builderSelect.setColumns(columns);
                initSet.add(BUILDER_SELECT);
            }
            return this;
        }

        public Wrapper select(List<String> columnsList, boolean aliasFlag) {
            if (FuncBase.isNotEmpty(columnsList)) {
                //处理别名
                if (aliasFlag) {
                    columnsList = FuncBase.fieldCodeAlias(columnsList);
                }

                builderSelect.setColumns(columnsList.toArray(new String[0]));
                initSet.add(BUILDER_SELECT);
            }
            return this;
        }

        public Wrapper setTableName(String tableName) {
            //处理别名
            builderSelect.setTableName(tableName, JeeLowCodeConstant.TABLE_ALIAS);
            initSet.add(BUILDER_SELECT);
            return this;
        }

        public Wrapper setTableSql(String tableNameSql) {
            //处理别名
            String sql = String.format("(%s)", tableNameSql);
            builderSelect.setTableName(sql, JeeLowCodeConstant.TABLE_ALIAS);
            initSet.add(BUILDER_SELECT);
            return this;
        }

        //where 相关
        public Wrapper setWhere(Consumer<SqlInfoWhereModel.Builder> whereBuilderConsumer) {
            whereBuilderConsumer.accept(builderWhere);
            initSet.add(BUILDER_WHERE);
            return this;
        }

        //order by相关
        public Wrapper orderByAsc(String... columns) {
            builderOrderBy.setOrderByAsc(columns);
            initSet.add(BUILDER_ORDERBY);
            return this;
        }

        public Wrapper orderByDesc(String... columns) {
            builderOrderBy.setOrderByDesc(columns);
            initSet.add(BUILDER_ORDERBY);
            return this;
        }

        //group by 相关
        public Wrapper groupByColumns(String... columns) {
            builderGroupBy.setColumns(columns);
            initSet.add(BUILDER_GROUPBY);
            return this;
        }

        //join 相关
        public Wrapper joinByinnerJoinTable(String tableName, String alias) {
            builderJoin.joinTable(tableName, alias);
            builderJoin.innerJoin();
            initSet.add(BUILDER_JOIN);
            return this;
        }

        public Wrapper joinByleftJoinTable(String tableName, String alias) {
            builderJoin.joinTable(tableName, alias);
            builderJoin.leftJoin();
            initSet.add(BUILDER_JOIN);
            return this;
        }

        public Wrapper joinByrightJoinTable(String tableName, String alias) {
            builderJoin.joinTable(tableName, alias);
            builderJoin.rightJoin();
            initSet.add(BUILDER_JOIN);
            return this;
        }

        public Wrapper joinOn(String leftStr, String rightStr) {
            builderJoin.on(leftStr, rightStr);
            initSet.add(BUILDER_JOIN);
            return this;
        }

        //构建having
        public Wrapper setHaving(Consumer<SqlInfoHavingModel.Builder> havingBuilderConsumer) {
            havingBuilderConsumer.accept(builderHaving);
            initSet.add(BUILDER_HAVING);
            return this;
        }

        public Wrapper build() {
            return this;
        }

        public SqlFormatModel buildSql() {
            SQLInterpretContext context = new SQLInterpretContext(dbSchemaName);
            SqlInfoSelectModel<BuildSelect> buildSelect = builderSelect.build();
            SqlInfoWhereModel<BuildWhere> buildWhere = builderWhere.buildWhere();
            SqlInfoOrderModel<BuildOrderBy> buildOrderBy = builderOrderBy.build();
            SqlInfoGroupModel<BuildDelete> buildGroupBy = builderGroupBy.build();
            SqlInfoJoinModel<SqlFormatModel> buildJoin = builderJoin.build();
            SqlInfoHavingModel<BuildHaving> buildHaving = builderHaving.buildHaving();

            List<ISQLExpression> expressionList = new ArrayList<>();
            if (initSet.contains(BUILDER_SELECT)) {
                context.setSelectModel(buildSelect);
                expressionList.add(new BuildSelect());
            }
            if (initSet.contains(BUILDER_WHERE)) {
                context.setWhereModel(buildWhere);
                expressionList.add(new BuildWhere());
            }
            if (initSet.contains(BUILDER_ORDERBY)) {
                context.setOrderModel(buildOrderBy);
                expressionList.add(new BuildOrderBy());
            }
            if (initSet.contains(BUILDER_GROUPBY)) {
                context.setGroupModel(buildGroupBy);
                expressionList.add(new BuildGroupBy());
            }
            if (initSet.contains(BUILDER_JOIN)) {
                context.setJoinModel(buildJoin);
                expressionList.add(new BuildJoin());
            }
            if (initSet.contains(BUILDER_HAVING)) {
                context.setHavingModel(buildHaving);
                expressionList.add(new BuildHaving());
            }

            if(FuncBase.isEmpty(expressionList)){
                return new SqlFormatModel();
            }
            List<SqlFormatModel> sqlFormatModelList = null;

            ISQLExpression expression = new AndExpression(expressionList);
            if (initSet.contains(BUILDER_SELECT)) {
                sqlFormatModelList = SqlHelper.sqlExpression(context, expression);
            } else {
                sqlFormatModelList = SqlHelper.parserExpression(context, expression);
            }

            return sqlFormatModelList.get(0);
        }
    }


}
