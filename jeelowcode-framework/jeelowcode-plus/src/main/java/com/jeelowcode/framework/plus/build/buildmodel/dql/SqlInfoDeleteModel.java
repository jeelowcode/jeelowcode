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
import com.jeelowcode.framework.plus.build.build.dql.BuildDelete;
import com.jeelowcode.framework.plus.build.buildmodel.SqlInfoBaseModel;
import com.jeelowcode.framework.plus.core.model.SqlFormatModel;

public class SqlInfoDeleteModel<T> extends SqlInfoBaseModel {
    private String tableName;

    private Boolean isReal;

    private SqlInfoDeleteModel(){}

    public static Builder builder(){
        return new Builder();
    }


    public static class Builder {

        private SqlInfoDeleteModel sqlInfoDeleteModel = new SqlInfoDeleteModel();

        public Builder setTableName(String tableName) {
            sqlInfoDeleteModel.tableName = tableName;
            sqlInfoDeleteModel.isReal=false;//逻辑删除
            return this;
        }

        public Builder setTableName(String tableName,boolean isReal) {
            sqlInfoDeleteModel.tableName = tableName;
            sqlInfoDeleteModel.isReal=isReal;
            return this;
        }

        public SqlInfoDeleteModel<BuildDelete> build(){
            ISQLExpression<SqlFormatModel> expression = new BuildDelete();
            sqlInfoDeleteModel.setSqlExpression(expression);
            return sqlInfoDeleteModel;
        }
    }

    public String getTableName() {
        return tableName;
    }

    public Boolean getReal() {
        return isReal;
    }
}
