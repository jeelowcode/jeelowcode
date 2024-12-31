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
import com.jeelowcode.framework.plus.build.buildmodel.ddl.SqlInfoAlterModel;
import com.jeelowcode.framework.plus.build.buildmodel.ddl.SqlInfoCommentModel;
import com.jeelowcode.framework.plus.entity.DbColunmTypesEntity;
import com.jeelowcode.framework.plus.entity.FieldModel;
import com.jeelowcode.framework.plus.entity.SqlInfoEntity;
import com.jeelowcode.framework.plus.utils.PlusUtils;
import com.jeelowcode.framework.plus.core.ddl.alter.JeeLowCodeAlterTable;
import com.jeelowcode.framework.utils.enums.JeeLowCodeFieldTypeEnum;
import com.jeelowcode.framework.utils.tool.StringPool;
import com.jeelowcode.framework.utils.utils.FuncBase;
import com.jeelowcode.framework.plus.build.buildmodel.ddl.SqlInfoDdlModel;
import com.jeelowcode.framework.plus.build.buildmodel.ddl.SqlInfoDropModel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * mysql数据库
 */
public class DbMysql extends AbstractDbTemplate {

    /**
     * 初始化数据库类型
     *
     * @return
     */
    @Override
    public void initDbColunmTypes(SqlInfoEntity sqlInfoEntity) {
        SQLInterpretContext context = sqlInfoEntity.getContext();

        DbColunmTypesEntity mysqlEntity = new DbColunmTypesEntity();
        mysqlEntity.setSymbol("`%s`");
        mysqlEntity.setUpperFlag(false);//不用转大写
        mysqlEntity.setJeeLowCodeString("varchar", 65535);
        mysqlEntity.setJeeLowCodeInteger("int", 0);
        mysqlEntity.setJeeLowCodeBigInt("bigint", 0);
        mysqlEntity.setJeeLowCodeDate("date", 0);
        mysqlEntity.setJeeLowCodeDateTime("datetime", 0);
        mysqlEntity.setJeeLowCodeTime("time", 0);
        mysqlEntity.setJeeLowCodeBigDecimal("decimal", 65,30);
        mysqlEntity.setJeeLowCodeText("text", 0);
        mysqlEntity.setJeeLowCodeLongText("longtext", 0);
        mysqlEntity.setJeeLowCodeBlob("blob", 0);
        context.setDbColunmTypes(mysqlEntity);
    }

    @Override
    protected void formatSQLInterpretContext(SQLInterpretContext context) {
        //处理是否为空问题
        this.handleAlterModel(context);
        //处理备注
        this.handleCommentModel(context);
        //处理删除
        this.handleDroptModel(context);
        //处理自定义ddl
        this.handleDdlModel(context);
    }

    private void handleAlterModel(SQLInterpretContext context) {
        SqlInfoAlterModel alterModel = context.getAlterModel();
        if(FuncBase.isEmpty(alterModel)){
            return;
        }
        FieldModel fieldModel = alterModel.getFieldModel();
        fieldModel.setDbNowIsNull(null);//不做处理
    }

    private void handleCommentModel(SQLInterpretContext context) {
        SqlInfoCommentModel commentModel = context.getCommentModel();
        if (FuncBase.isEmpty(commentModel)) {
            return;
        }
        String schemaName = context.getSchemaName();
        DbColunmTypesEntity dbColunmTypes = context.getDbColunmTypes();
        String tableName = commentModel.getTableName();
        String tableAlias = commentModel.getTableAlias();
        List<FieldModel> fieldModelList = commentModel.getFieldModelList();

        List<String> ddlList = new ArrayList<>();
        //处理表备注
        if (FuncBase.isNotEmpty(tableAlias)) {
            String tableCommentFormat = "ALTER TABLE %s.%s COMMENT '%s';";
            String ddl = String.format(tableCommentFormat, schemaName, tableName, tableAlias);
            ddlList.add(ddl);
        }

        //处理字段备注
        if (FuncBase.isNotEmpty(fieldModelList)) {
            String symbol = dbColunmTypes.getSymbol();
            Boolean upperFlag = dbColunmTypes.getUpperFlag();

            for (FieldModel fieldModel : fieldModelList) {
                String fieldCode = FuncBase.concatSymbol(fieldModel.getFieldCode(), symbol, upperFlag);
                String fieldName = fieldModel.getFieldName();
                JeeLowCodeFieldTypeEnum fieldTypeEnum = fieldModel.getFieldType();
                //类型
                DbColunmTypesEntity.TypeEntity typeEntity = PlusUtils.jeelowCodeType2DbType(fieldTypeEnum, dbColunmTypes);
                String fieldType = PlusUtils.getDbType(fieldTypeEnum, typeEntity.getDbType(), fieldModel.getFieldLen(), fieldModel.getFieldPointLen(),typeEntity.getDbMaxLen());
                String defaultValSql = PlusUtils.getDefaultValSql(fieldModel.getFieldDefaultVal(), fieldModel.getIsNull(),null);
                String mySqlstr = "ALTER TABLE %s.%s MODIFY COLUMN %s %s %s COMMENT '%s';";
                String ddl = String.format(mySqlstr, schemaName, tableName, fieldCode, fieldType, defaultValSql, fieldName);
                ddlList.add(ddl);
            }
        }
        //设置自定义ddl
        commentModel.setExecuteDdlList(ddlList);

    }

    private void handleDroptModel(SQLInterpretContext context) {
        SqlInfoDropModel dropModel = context.getDropModel();
        if (FuncBase.isEmpty(dropModel)) {
            return;
        }
        DbColunmTypesEntity dbColunmTypes = context.getDbColunmTypes();
        String symbol = dbColunmTypes.getSymbol();
        Boolean upperFlag = dbColunmTypes.getUpperFlag();

        SqlInfoDropModel.DropType dropType = dropModel.getDropType();
        String tableName = FuncBase.concatSymbol(dropModel.getTableName(), symbol, upperFlag);
        String indexName = FuncBase.concatSymbol(dropModel.getIndexName(), symbol, upperFlag);
        String schemaName = FuncBase.concatSymbol(context.getSchemaName(), symbol);

        //删除索引存在差异
        if (FuncBase.equals(dropType, SqlInfoDropModel.DropType.DROP_INDEX)) {
            JeeLowCodeAlterTable dropIndex = new JeeLowCodeAlterTable();
            dropIndex.setTableName(schemaName + StringPool.DOT + tableName);
            dropIndex.dropIndex(indexName);
            List<String> ddlList = Collections.singletonList(dropIndex.getFullSQL());
            dropModel.setExecuteDdlList(ddlList);
        }
    }

    private void handleDdlModel(SQLInterpretContext context) {
        SqlInfoDdlModel ddlModel = context.getDdlModel();
        if (FuncBase.isEmpty(ddlModel)) {
            return;
        }
        String ddl = "";
        String schemaName = context.getSchemaName();
        switch (ddlModel.getType()) {
            case INDEX_EXIST://索引是否存在
                ddl = this.formatIndexExistSql(schemaName, ddlModel.getMap());
                break;
            case INDEX_ALL://全部索引
                ddl = this.formatIndexAllSql(schemaName, ddlModel.getMap());
                break;
            case TABLE_EXIST://表是否存在
                ddl = this.formatTableExistSql(schemaName, ddlModel.getMap());
                break;
            case TABLE_FIELD_COMMENT://表字段备注
                ddl = this.formatTableFieldCommentSql(context.getSchemaName(), ddlModel.getMap());
                break;
            case ALL_TABLE_NAME://所有表名
                ddl = this.formatGetTableNameSql(schemaName);
                break;
            case TABLE_COMMENT://表备注
                ddl = this.formatGetTableCommentSql(context.getSchemaName(), ddlModel.getMap());
                break;
        }
        List<String> ddlList = Collections.singletonList(ddl);
        ddlModel.setExecuteDdlList(ddlList);
    }

    //索引是否存在
    private String formatIndexExistSql(String schemaName, Map<String, Object> map) {
        String tableName = FuncBase.getMap2Str(map, "tableName");
        String indexName = FuncBase.getMap2Str(map, "indexName");
        String mysqlFormatStr = "select count(*) as \"cou\" from `information_schema`.`statistics` where `non_unique` =1 and `table_schema`='%s' and `table_name` ='%s' and `index_name` ='%s' ";
        return String.format(mysqlFormatStr, schemaName, tableName, indexName);
    }

    //获取所有索引
    private String formatIndexAllSql(String schemaName, Map<String, Object> map) {
        String tableName = FuncBase.getMap2Str(map, "tableName");
        String mysqlFormatStr = "select index_name as \"index_name\" from `information_schema`.`statistics` where `non_unique` =1 and `table_schema`='%s' and `table_name` ='%s'";
        return String.format(mysqlFormatStr, schemaName, tableName);

    }

    //表名是否存在
    private String formatTableExistSql(String schemaName, Map<String, Object> map) {
        String tableName = FuncBase.getMap2Str(map, "tableName");
        String mysqlFormatStr = "SELECT COUNT(*) AS \"cou\" FROM `information_schema`.`TABLES` WHERE `table_schema`='%s' AND `table_name`='%s'";
        return String.format(mysqlFormatStr, schemaName, tableName);
    }

    //表字段备注
    private String formatTableFieldCommentSql(String schemaName, Map<String, Object> map) {
        String tableName = FuncBase.getMap2Str(map, "tableName");
        String oracleFormatStr="SELECT COLUMN_NAME AS \"fieldCode\", COLUMN_COMMENT AS \"fieldName\" FROM information_schema.COLUMNS WHERE TABLE_SCHEMA = '%s' AND TABLE_NAME = '%s';";
        return String.format(oracleFormatStr, schemaName, tableName);
    }

    //表名是否存在
    private String formatGetTableNameSql(String schemaName) {
        String mysqlFormatStr = "SELECT table_name AS \"table_name\" FROM `information_schema`.`TABLES` WHERE `table_schema`='%s' ";
        return String.format(mysqlFormatStr, schemaName);
    }

    //获取表备注
    private String formatGetTableCommentSql(String schemaName, Map<String, Object> map) {
        String tableName = FuncBase.getMap2Str(map, "tableName");
        String mysqlFormatStr="SELECT TABLE_COMMENT AS \"table_comment\"  FROM information_schema.TABLES WHERE TABLE_SCHEMA = '%s' AND TABLE_NAME = '%s';";
        return String.format(mysqlFormatStr, schemaName, tableName);
    }
}
