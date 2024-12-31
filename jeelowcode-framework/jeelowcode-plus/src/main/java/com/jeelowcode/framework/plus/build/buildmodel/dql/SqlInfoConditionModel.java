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
package com.jeelowcode.framework.plus.build.buildmodel.dql;

import com.jeelowcode.framework.plus.build.ISQLExpression;
import com.jeelowcode.framework.plus.build.build.dql.BuildCondition;
import com.jeelowcode.framework.plus.build.build.dql.BuildHaving;
import com.jeelowcode.framework.plus.build.build.dql.BuildWhere;
import com.jeelowcode.framework.plus.build.buildmodel.ExpressionModel;
import com.jeelowcode.framework.plus.build.buildmodel.SqlInfoBaseModel;
import com.jeelowcode.framework.plus.core.model.SqlFormatModel;
import com.jeelowcode.framework.utils.utils.FuncBase;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

/**
 * @author JX
 * @create 2024-07-30 10:17
 * @dedescription:
 */
public class SqlInfoConditionModel<T> extends SqlInfoBaseModel {


    private List<ExpressionModel> expressions = new ArrayList<>();

    public static SqlInfoConditionModel.Builder builder() {
        return new SqlInfoConditionModel.Builder();
    }

    public static class Builder {

        private SqlInfoConditionModel conditionModel = new SqlInfoConditionModel();

        public SqlInfoConditionModel.Builder gt(String column, Object value) {
            conditionModel.expressions.add(buildExpression(SqlInfoConditionModel.WhereType.GT, column, value));
            return this;
        }

        public SqlInfoConditionModel.Builder ge(String column, Object value) {
            conditionModel.expressions.add(buildExpression(SqlInfoConditionModel.WhereType.GE, column, value));
            return this;
        }

        public SqlInfoConditionModel.Builder ne(String column, Object value) {
            if (FuncBase.isEmpty(value)) {
                return this;
            }
            conditionModel.expressions.add(buildExpression(SqlInfoConditionModel.WhereType.NE, column, value));
            return this;
        }

        public SqlInfoConditionModel.Builder eq(String column, Object value) {
            conditionModel.expressions.add(buildExpression(SqlInfoConditionModel.WhereType.EQ, column, value));
            return this;
        }

        public SqlInfoConditionModel.Builder lt(String column, Object value) {
            conditionModel.expressions.add(buildExpression(SqlInfoConditionModel.WhereType.LT, column, value));
            return this;
        }

        public SqlInfoConditionModel.Builder le(String column, Object value) {
            conditionModel.expressions.add(buildExpression(SqlInfoConditionModel.WhereType.LE, column, value));
            return this;
        }


        public SqlInfoConditionModel.Builder between(String column, Object... value) {
            conditionModel.expressions.add(buildExpression(SqlInfoConditionModel.WhereType.BETWEEN, column, value));
            return this;
        }

        public SqlInfoConditionModel.Builder notbetween(String column, Object... value) {
            conditionModel.expressions.add(buildExpression(SqlInfoConditionModel.WhereType.NOT_BETWEEN, column, value));
            return this;
        }

        public SqlInfoConditionModel.Builder in(boolean condition, String column, Object... value) {
            if (!condition) {
                return this;
            }
            return this.in(column, value);
        }

        public SqlInfoConditionModel.Builder in(String column, Object... value) {
            if (FuncBase.isEmpty(value)) {
                return this;
            }
            Object valueTmp = value[0];
            if (FuncBase.isEmpty(valueTmp)) {
                return this;
            }

            if (value.length == 1) {
                if (valueTmp instanceof List) {
                    List<Object> tmpList = (List<Object>) valueTmp;
                    if (tmpList.size() == 1) {
                        this.eq(column, tmpList.get(0));
                        return this;
                    }

                }
            }
            conditionModel.expressions.add(buildExpression(SqlInfoConditionModel.WhereType.IN, column, value));

            return this;
        }

        public SqlInfoConditionModel.Builder notIn(String column, Object... value) {
            conditionModel.expressions.add(buildExpression(SqlInfoConditionModel.WhereType.NOT_IN, column, value));
            return this;
        }

        public SqlInfoConditionModel.Builder like(boolean condition, String column, Object... value) {
            if (!condition) {
                return this;
            }
            return like(column, value);
        }

        public SqlInfoConditionModel.Builder like(String column, Object... value) {
            conditionModel.expressions.add(buildExpression(SqlInfoConditionModel.WhereType.LIKE, column, value));
            return this;
        }

        public SqlInfoConditionModel.Builder notLike(String column, Object... value) {
            conditionModel.expressions.add(buildExpression(SqlInfoConditionModel.WhereType.NOT_LIKE, column, value));
            return this;
        }

        public SqlInfoConditionModel.Builder likeLeft(String column, Object... value) {
            conditionModel.expressions.add(buildExpression(SqlInfoConditionModel.WhereType.LIKE_LEFT, column, value));
            return this;
        }

        public SqlInfoConditionModel.Builder likeRight(String column, Object... value) {
            conditionModel.expressions.add(buildExpression(SqlInfoConditionModel.WhereType.LIKE_RIGHT, column, value));
            return this;
        }

        public SqlInfoConditionModel.Builder isNull(String column, Object... value) {
            conditionModel.expressions.add(buildExpression(SqlInfoConditionModel.WhereType.IS_NULL, column, value));
            return this;
        }

        public SqlInfoConditionModel.Builder isNotNull(String column, Object... value) {
            conditionModel.expressions.add(buildExpression(SqlInfoConditionModel.WhereType.IS_NOT_NULL, column, value));
            return this;
        }

        public SqlInfoConditionModel.Builder apply(String apply) {
            conditionModel.expressions.add(buildExpression(WhereType.APPLY, apply, null));
            return this;
        }

        public SqlInfoConditionModel.Builder or() {
            conditionModel.expressions.add(buildExpression(SqlInfoConditionModel.WhereType.OR, null, null));
            return this;
        }

        public SqlInfoConditionModel.Builder or(Consumer<SqlInfoConditionModel.Builder> orBuilderConsumer) {
            conditionModel.expressions.add(buildExpression(SqlInfoConditionModel.WhereType.OR_BUILD, null, orBuilderConsumer));
            return this;
        }

        public SqlInfoConditionModel.Builder and(Consumer<SqlInfoConditionModel.Builder> andBuilderConsumer) {
            conditionModel.expressions.add(buildExpression(SqlInfoConditionModel.WhereType.AND_BUILD, null, andBuilderConsumer));
            return this;
        }


        public SqlInfoWhereModel<BuildWhere> buildWhere() {
            ISQLExpression<SqlFormatModel> expression = new BuildWhere();
            SqlInfoWhereModel whereModel = new SqlInfoWhereModel();
            List<ExpressionModel> expressions = conditionModel.getExpressions();
            whereModel.setExpressions(expressions);
            whereModel.setSqlExpression(expression);
            return whereModel;
        }

        public SqlInfoHavingModel<BuildHaving> buildHaving() {
            ISQLExpression<SqlFormatModel> expression = new BuildHaving();

            SqlInfoHavingModel havingModel = new SqlInfoHavingModel();
            List<ExpressionModel> expressions = conditionModel.getExpressions();
            havingModel.setExpressions(expressions);
            havingModel.setSqlExpression(expression);
            return havingModel;
        }

        public SqlInfoConditionModel<BuildCondition> build() {
            return conditionModel;
        }
    }

    public List<ExpressionModel> getExpressions() {
        return expressions;
    }


    public enum WhereType {
        GT, //>
        GE,   //>=
        NE,   //!=
        EQ,   //=
        LT,   //<
        LE,   //<=
        BETWEEN,
        NOT_BETWEEN,
        IN,
        NOT_IN,
        LIKE,
        NOT_LIKE,
        LIKE_LEFT,
        LIKE_RIGHT,
        IS_NULL,
        IS_NOT_NULL,
        OR,
        OR_BUILD,
        AND_BUILD,
        APPLY
    }

    private static ExpressionModel buildExpression(SqlInfoConditionModel.WhereType whereType, String column, Object... value) {
        ExpressionModel expression = new ExpressionModel();
        expression.setWhereType(whereType);
        expression.setColumn(column);
        expression.setValue(value);
        return expression;
    }

    public void setExpressions(List<ExpressionModel> expressions) {
        this.expressions = expressions;
    }
}
