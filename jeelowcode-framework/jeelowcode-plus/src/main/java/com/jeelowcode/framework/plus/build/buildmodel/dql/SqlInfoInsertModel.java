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
import com.jeelowcode.framework.plus.build.build.dql.BuildInsert;
import com.jeelowcode.framework.plus.build.buildmodel.SqlInfoBaseModel;
import com.jeelowcode.framework.plus.core.model.SqlFormatModel;

import java.util.LinkedHashMap;
import java.util.Map;

public class SqlInfoInsertModel<T> extends SqlInfoBaseModel {

    private String tableName;

    private Map<String,Object> insertMap=new LinkedHashMap<>();

    private SqlInfoInsertModel (){}

    public static Builder builder(){
        return new Builder();
    }

    public static class Builder{
        private SqlInfoInsertModel insertModel = new SqlInfoInsertModel();

        public Builder setTableName(String tableName){
            insertModel.setTableName(tableName);
            return this;
        }
        public Builder addColumn(String column,Object val){
            insertModel.insertMap.put(column,val);
            return this;
        }

        public Builder addMap(Map<String,Object> insertMap){
            insertModel.insertMap.putAll(insertMap);
            return this;
        }

        public SqlInfoInsertModel<BuildInsert> build(){
            ISQLExpression<SqlFormatModel> expression = new BuildInsert();
            insertModel.setSqlExpression(expression);
            return insertModel;
        }

    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public Map<String, Object> getInsertMap() {
        return insertMap;
    }

    public void setInsertMap(Map<String, Object> insertMap) {
        this.insertMap = insertMap;
    }
}
