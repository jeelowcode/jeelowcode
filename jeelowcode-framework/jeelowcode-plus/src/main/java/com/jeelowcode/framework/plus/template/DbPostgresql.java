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
import com.jeelowcode.framework.plus.build.buildmodel.ddl.SqlInfoDdlModel;
import com.jeelowcode.framework.plus.entity.DbColunmTypesEntity;
import com.jeelowcode.framework.plus.entity.FieldModel;
import com.jeelowcode.framework.plus.entity.SqlInfoEntity;
import com.jeelowcode.framework.plus.utils.PlusUtils;
import com.jeelowcode.framework.utils.enums.JeeLowCodeFieldTypeEnum;
import com.jeelowcode.framework.utils.utils.FuncBase;
import com.jeelowcode.framework.utils.constant.JeeLowCodeConstant;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import static com.jeelowcode.framework.plus.build.buildmodel.ddl.SqlInfoAlterModel.AlterType.UPDATE_COLUMN;

/**
 * @author JX
 * @create 2024-07-31 11:08
 * @dedescription:
 */
public class DbPostgresql extends AbstractDbTemplate {
    @Override
    protected void initDbColunmTypes(SqlInfoEntity sqlInfoEntity) {
        SQLInterpretContext context = sqlInfoEntity.getContext();

        DbColunmTypesEntity postgreEntity = new DbColunmTypesEntity();
        postgreEntity.setSymbol("");
        postgreEntity.setUpperFlag(false);//不用转大写
        postgreEntity.setJeeLowCodeString("varchar", 65535);
        postgreEntity.setJeeLowCodeInteger("int4", 0);
        postgreEntity.setJeeLowCodeBigInt("int8", 0);
        postgreEntity.setJeeLowCodeDate("date", 0);
        postgreEntity.setJeeLowCodeDateTime("timestamp", 0);
        postgreEntity.setJeeLowCodeTime("time", 0);
        postgreEntity.setJeeLowCodeBigDecimal("numeric", 1000,null);
        postgreEntity.setJeeLowCodeText("text", 0);
        postgreEntity.setJeeLowCodeLongText("text", 0);
        postgreEntity.setJeeLowCodeBlob("bytea", 0);
        context.setDbColunmTypes(postgreEntity);
    }

    @Override
    protected void formatSQLInterpretContext(SQLInterpretContext context) {
        //处理 schemaName
        this.handleSchemaName(context);
        //处理修改表
        this.handleAlterModel(context);
        //处理自定义ddl
        this.handleDdlModel(context);
    }

    private void handleSchemaName(SQLInterpretContext context) {
        String schemaName = context.getSchemaName();
        if (FuncBase.isEmpty(schemaName)) {
            return;
        }
        context.setSchemaName(schemaName + JeeLowCodeConstant.POSTGRESQL_SCHEMA);
    }

    private void handleAlterModel(SQLInterpretContext context) {
        SqlInfoAlterModel alterModel = context.getAlterModel();
        if (FuncBase.isEmpty(alterModel)) {
            return;
        }
        DbColunmTypesEntity dbColunmTypes = context.getDbColunmTypes();
        String schemaName = context.getSchemaName();
        String tableName = alterModel.getTableName();
        FieldModel fieldModel = alterModel.getFieldModel();

        JeeLowCodeFieldTypeEnum fieldTypeEnum = fieldModel.getFieldType();
        DbColunmTypesEntity.TypeEntity typeEntity = PlusUtils.jeelowCodeType2DbType(fieldTypeEnum, dbColunmTypes);
        String fieldType = PlusUtils.getDbType(fieldTypeEnum, typeEntity.getDbType(), fieldModel.getFieldLen(), fieldModel.getFieldPointLen(),typeEntity.getDbMaxLen());
        String fieldCode = fieldModel.getFieldCode();

        SqlInfoAlterModel.AlterType alterType = alterModel.getAlterType();
        if (FuncBase.equals(alterType, UPDATE_COLUMN)) {//修改列 存在差异
            String ddl = "ALTER TABLE %s.%s ALTER COLUMN %s TYPE %s USING %s::%s";
            //设置自定义ddl
            alterModel.setExecuteDdlList(Collections.singletonList(String.format(ddl, schemaName, tableName, fieldCode, fieldType, fieldCode, fieldType)));
        }
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
                ddl = this.formatTableExistSql(ddlModel.getMap());
                break;
            case TABLE_FIELD_COMMENT://表字段备注
                ddl = this.formatTableFieldCommentSql(ddlModel.getMap());
                break;
            case ALL_TABLE_NAME://所有表名
                ddl = this.formatGetTableNameSql();
                break;
            case TABLE_COMMENT://表备注
                ddl = this.formatGetTableCommentSql(ddlModel.getMap());
                break;
        }
        List<String> ddlList = Collections.singletonList(ddl);
        ddlModel.setExecuteDdlList(ddlList);
    }

    //索引是否存在
    private String formatIndexExistSql(Map<String, Object> map) {
        String indexName = FuncBase.getMap2Str(map, "indexName");
        String postgreStr = "SELECT count(*) as \"cou\"  FROM pg_indexes WHERE indexname = '%s'";
        return String.format(postgreStr, indexName);
    }

    //获取所有索引
    private String formatIndexAllSql(Map<String, Object> map) {
        String tableName = FuncBase.getMap2Str(map, "tableName");
        String postgreFormatStr = "SELECT indexname AS \"index_name\" FROM pg_indexes WHERE tablename =  '%s' and indexname not like '%s_pkey';";
        return String.format(postgreFormatStr, tableName, tableName);
    }

    //表名是否存在
    private String formatTableExistSql(Map<String, Object> map) {
        String tableName = FuncBase.getMap2Str(map, "tableName");
        String postgreFormatStr = "SELECT count(*) as \"cou\" FROM pg_tables  WHERE schemaname = 'public' AND tablename  = '%s';";
        return String.format(postgreFormatStr, tableName);
    }

    //表名是否存在
    private String formatGetTableNameSql() {
        String postgreFormatStr = "SELECT tablename as \"table_name\" FROM pg_tables  WHERE schemaname = 'public';";
        return String.format(postgreFormatStr);
    }

    //表字段备注
    private String formatTableFieldCommentSql(Map<String, Object> map) {
        String tableName = FuncBase.getMap2Str(map, "tableName");
        String oracleFormatStr="SELECT A.attname AS \"fieldCode\",col_description (A.attrelid,A.attnum) AS \"fieldName\" FROM pg_attribute A WHERE A.attrelid='%s' :: REGCLASS AND A.attnum> 0 AND NOT A.attisdropped ORDER BY A.attnum;";
        return String.format(oracleFormatStr,  tableName);
    }

    //获取表备注
    private String formatGetTableCommentSql(Map<String, Object> map) {
        String tableName = FuncBase.getMap2Str(map, "tableName");
        String mysqlFormatStr="SELECT d.description AS \"table_comment\" FROM pg_catalog.pg_class pc JOIN pg_catalog.pg_description d ON pc.OID=d.objoid WHERE pc.relname='%s' AND pc.relkind='r' AND d.objsubid=0;";
        return String.format(mysqlFormatStr, tableName);
    }
}
