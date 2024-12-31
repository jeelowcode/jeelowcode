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
package com.jeelowcode.framework.plus.template;


import com.jeelowcode.framework.plus.build.buildmodel.SQLInterpretContext;
import com.jeelowcode.framework.plus.build.buildmodel.ddl.SqlInfoDdlModel;
import com.jeelowcode.framework.plus.build.buildmodel.ddl.SqlInfoDropModel;
import com.jeelowcode.framework.plus.entity.DbColunmTypesEntity;
import com.jeelowcode.framework.plus.entity.SqlInfoEntity;
import com.jeelowcode.framework.utils.utils.FuncBase;

import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * 达梦 数据库
 */
public class DbDm extends AbstractDbTemplate {

    /**
     * 初始化数据库类型
     *
     * @return
     */
    @Override
    public void initDbColunmTypes(SqlInfoEntity sqlInfoEntity) {
        SQLInterpretContext context = sqlInfoEntity.getContext();
        DbColunmTypesEntity oracleEntity = new DbColunmTypesEntity();
        oracleEntity.setSymbol("\"%s\"");
        oracleEntity.setUpperFlag(true);//转大写
        oracleEntity.setJeeLowCodeString("varchar", 32767);
        oracleEntity.setJeeLowCodeInteger("int", 0);
        oracleEntity.setJeeLowCodeBigInt("bigint", 0);
        oracleEntity.setJeeLowCodeDate("date", 0);
        oracleEntity.setJeeLowCodeDateTime("timestamp", 0);
        oracleEntity.setJeeLowCodeTime("time", 0);
        oracleEntity.setJeeLowCodeBigDecimal("decimal", 22,null);
        oracleEntity.setJeeLowCodeText("text", 0);
        oracleEntity.setJeeLowCodeLongText("text", 0);
        oracleEntity.setJeeLowCodeBlob("blob", 0);
        context.setDbColunmTypes(oracleEntity);
    }

    @Override
    protected void formatSQLInterpretContext(SQLInterpretContext context) {
        //处理删除
        this.handleDroptModel(context);
        //处理自定义ddl
        this.handleDdlModel(context);
    }


    private void handleDroptModel(SQLInterpretContext context) {
        SqlInfoDropModel dropModel = context.getDropModel();
        if (FuncBase.isEmpty(dropModel)) {
            return;
        }
        dropModel.setCloumnFormat("(%s)");

    }


    private void handleDdlModel(SQLInterpretContext context) {
        SqlInfoDdlModel ddlModel = context.getDdlModel();
        if (FuncBase.isEmpty(ddlModel)) {
            return;
        }
        String ddl = "";
        switch (ddlModel.getType()) {
            case INDEX_EXIST://索引是否存在
                ddl = this.formatIndexExistSql(ddlModel.getMap());
                break;
            case INDEX_ALL://全部索引
                ddl = this.formatIndexAllSql(ddlModel.getMap());
                break;
            case TABLE_EXIST://表是否存在
                ddl = this.formatTableExistSql(context.getSchemaName(), ddlModel.getMap());
                break;
            case TABLE_FIELD_COMMENT://表字段备注
                ddl = this.formatTableFieldCommentSql(context.getSchemaName(), ddlModel.getMap());
                break;
            case ALL_TABLE_NAME://所有表名
                ddl = this.formatGetTableNameSql(context.getSchemaName());
                break;
            case TABLE_COMMENT://表备注
                ddl = this.formatGetTableCommentSql(context.getSchemaName(), ddlModel.getMap());
                break;
        }
        List<String> ddlList = Collections.singletonList(ddl);
        ddlModel.setExecuteDdlList(ddlList);
    }

    //索引是否存在
    private String formatIndexExistSql(Map<String, Object> map) {
        String indexName = FuncBase.getMap2Str(map, "indexName");
        String oracleFormatStr = "select count(*) as \"cou\" from USER_IND_COLUMNS where INDEX_NAME=upper('%s')";
        return String.format(oracleFormatStr, indexName);
    }

    //获取所有索引
    private String formatIndexAllSql(Map<String, Object> map) {
        String tableName = FuncBase.getMap2Str(map, "tableName");
        String oracleFormatStr = "select INDEX_NAME AS \"index_name\" from USER_IND_COLUMNS where \"TABLE_NAME\" =upper('%s') and COLUMN_NAME !='ID'";
        return String.format(oracleFormatStr,tableName);
    }

    //表名是否存在
    private String formatTableExistSql(String schemaName, Map<String, Object> map) {
        String tableName = FuncBase.getMap2Str(map, "tableName");
        String oracleFormatStr = "SELECT COUNT(*) as \"cou\" FROM ALL_TABLES WHERE upper(OWNER) = '%s' AND TABLE_NAME = upper('%s');";
        return String.format(oracleFormatStr, schemaName.toUpperCase(), tableName);

    }
    //表字段备注
    private String formatTableFieldCommentSql(String schemaName, Map<String, Object> map) {
        String tableName = FuncBase.getMap2Str(map, "tableName");
        String oracleFormatStr = "SELECT COLUMN_NAME as \"fieldCode\",COMMENTS as \"fieldName\" FROM ALL_COL_COMMENTS WHERE SCHEMA_NAME = '%s' AND TABLE_NAME = upper('%s');";
        return String.format(oracleFormatStr, schemaName, tableName);
    }

    //表名是否存在
    private String formatGetTableNameSql(String schemaName) {
        String oracleFormatStr = "SELECT TABLE_NAME as \"table_name\" FROM ALL_COL_COMMENTS WHERE SCHEMA_NAME = '%s';";
        return String.format(oracleFormatStr, schemaName);
    }

    //获取表备注
    private String formatGetTableCommentSql(String schemaName, Map<String, Object> map) {
        String tableName = FuncBase.getMap2Str(map, "tableName");
        String mysqlFormatStr="select COMMENTS AS \"table_comment\" from ALL_TAB_COMMENTS  WHERE OWNER ='%s' AND TABLE_NAME= upper('%s');";
        return String.format(mysqlFormatStr, schemaName, tableName);
    }
}
