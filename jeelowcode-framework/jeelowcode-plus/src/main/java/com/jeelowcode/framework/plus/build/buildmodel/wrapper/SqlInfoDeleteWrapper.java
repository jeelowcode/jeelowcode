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
import com.jeelowcode.framework.plus.build.build.dql.BuildDelete;
import com.jeelowcode.framework.plus.build.build.dql.BuildWhere;
import com.jeelowcode.framework.plus.build.buildmodel.SQLInterpretContext;
import com.jeelowcode.framework.plus.build.buildmodel.dql.SqlInfoDeleteModel;
import com.jeelowcode.framework.plus.build.buildmodel.dql.SqlInfoWhereModel;
import com.jeelowcode.framework.plus.core.model.SqlFormatModel;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

/**
 * 删除-封装器
 */
public class SqlInfoDeleteWrapper {

    public static Wrapper wrapper(String dbSchemaName){
        return new Wrapper(dbSchemaName);
    }

    public static class Wrapper extends BaseWrapper {

        private String dbSchemaName;
        private SqlInfoDeleteModel.Builder builderDelete = SqlInfoDeleteModel.builder();
        private SqlInfoWhereModel.Builder builderWhere = SqlInfoWhereModel.builder();

        public Wrapper(String dbSchemaName) {
            this.dbSchemaName = dbSchemaName;
        }

        //逻辑删除
        public Wrapper setTableName(String tableName) {
            builderDelete.setTableName(tableName);
            initSet.add(BUILDER_DELETE);
            return this;
        }
        //true=真实删除，物理删除  false=逻辑删除
        public Wrapper setTableName(String tableName, boolean isReal) {
            builderDelete.setTableName(tableName,isReal);
            initSet.add(BUILDER_DELETE);
            return this;
        }

        public Wrapper setWhere(Consumer<SqlInfoWhereModel.Builder> whereBuilderConsumer) {
            whereBuilderConsumer.accept(builderWhere);
            initSet.add(BUILDER_WHERE);
            return this;
        }
        public Wrapper build() {
            return this;
        }

        public SqlFormatModel buildSql() {
            SQLInterpretContext context = new SQLInterpretContext(dbSchemaName);
            SqlInfoDeleteModel<BuildDelete> buildDelete = builderDelete.build();
            SqlInfoWhereModel<BuildWhere> buildWhere = builderWhere.buildWhere();

            List<ISQLExpression> expressionList=new ArrayList<>();
            if(initSet.contains(BUILDER_DELETE)){
                context.setDeleteModel(buildDelete);
                expressionList.add(new BuildDelete());
            }
            if(initSet.contains(BUILDER_WHERE)){
                context.setWhereModel(buildWhere);
                expressionList.add(new BuildWhere());
            }

            ISQLExpression expression = new AndExpression(expressionList);
            List<SqlFormatModel> sqlFormatModelList = SqlHelper.sqlExpression(context, expression);
            return sqlFormatModelList.get(0);
        }
    }


}