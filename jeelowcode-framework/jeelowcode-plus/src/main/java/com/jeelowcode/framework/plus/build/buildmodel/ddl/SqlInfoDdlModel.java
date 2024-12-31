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
package com.jeelowcode.framework.plus.build.buildmodel.ddl;

import com.jeelowcode.framework.plus.build.ISQLExpression;
import com.jeelowcode.framework.plus.build.build.ddl.BuildDdl;
import com.jeelowcode.framework.plus.core.model.SqlFormatModel;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 */
public class SqlInfoDdlModel<T> extends SqlInfoBaseDdlModel {

    private DdlType type;//操作类型

    private Map<String,Object> map;//参数

    private SqlInfoDdlModel(){}

    public static Builder builder(){
        return new Builder();
    }


    public static class Builder{

        private SqlInfoDdlModel ddlModel = new SqlInfoDdlModel();

        ISQLExpression<List<SqlFormatModel>> expression = new BuildDdl();

        public SqlInfoDdlModel<BuildDdl> buildIndexExist(String tableName,String indexName){
            Map<String,Object> map=new HashMap<>();
            map.put("tableName",tableName);
            map.put("indexName",indexName);
            ddlModel.type= DdlType.INDEX_EXIST;
            ddlModel.map=map;
            ddlModel.setSqlExpression(expression);
            return ddlModel;
        }
        public SqlInfoDdlModel<BuildDdl> buildIndexAllByTable(String tableName){
            Map<String,Object> map=new HashMap<>();
            map.put("tableName",tableName);
            ddlModel.type= DdlType.INDEX_ALL;
            ddlModel.map=map;
            ddlModel.setSqlExpression(expression);
            return ddlModel;
        }
        public SqlInfoDdlModel<BuildDdl> buildTableExist(String tableName){
            Map<String,Object> map=new HashMap<>();
            map.put("tableName",tableName);
            ddlModel.type= DdlType.TABLE_EXIST;
            ddlModel.map=map;
            ddlModel.setSqlExpression(expression);
            return ddlModel;
        }
        public SqlInfoDdlModel<BuildDdl> buildGetAllTableName(){
            Map<String,Object> map=new HashMap<>();
            ddlModel.type= DdlType.ALL_TABLE_NAME;
            ddlModel.map=map;
            ddlModel.setSqlExpression(expression);
            return ddlModel;
        }
        public SqlInfoDdlModel<BuildDdl> buildFieldComment(String tableName){
            Map<String,Object> map=new HashMap<>();
            map.put("tableName",tableName);
            ddlModel.type= DdlType.TABLE_FIELD_COMMENT;
            ddlModel.map=map;
            ddlModel.setSqlExpression(expression);
            return ddlModel;
        }
        public SqlInfoDdlModel<BuildDdl> buildTableComment(String tableName){
            Map<String,Object> map=new HashMap<>();
            map.put("tableName",tableName);
            ddlModel.type= DdlType.TABLE_COMMENT;
            ddlModel.map=map;
            ddlModel.setSqlExpression(expression);
            return ddlModel;
        }
    }


    public enum DdlType{
        INDEX_EXIST,//索引是否存在
        INDEX_ALL,//所有索引
        TABLE_EXIST,//表名是否存在
        ALL_TABLE_NAME,//所有表名
        TABLE_COMMENT,//表备注
        TABLE_FIELD_COMMENT;//表字段备注
    }

    public DdlType getType() {
        return type;
    }

    public Map<String, Object> getMap() {
        return map;
    }
}
