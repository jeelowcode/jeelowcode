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
package com.jeelowcode.framework.plus.build.build.ddl;


import com.jeelowcode.framework.plus.build.ISQLExpression;
import com.jeelowcode.framework.plus.build.buildmodel.SQLInterpretContext;
import com.jeelowcode.framework.plus.build.buildmodel.ddl.SqlInfoAlterModel;
import com.jeelowcode.framework.plus.entity.DbColunmTypesEntity;
import com.jeelowcode.framework.plus.entity.FieldModel;
import com.jeelowcode.framework.plus.utils.PlusUtils;
import com.jeelowcode.framework.plus.core.ddl.alter.JeeLowCodeAlterTable;
import com.jeelowcode.framework.plus.core.model.SqlFormatModel;
import com.jeelowcode.framework.utils.enums.JeeLowCodeFieldTypeEnum;
import com.jeelowcode.framework.utils.tool.StringPool;
import com.jeelowcode.framework.utils.utils.FuncBase;
import net.sf.jsqlparser.JSQLParserException;

import java.util.List;

/**
 * 修改表相关
 */
public class BuildAlter implements ISQLExpression<List<SqlFormatModel>> {

    @Override
    public List<SqlFormatModel> interpret(SQLInterpretContext context) throws JSQLParserException {
        DbColunmTypesEntity dbColunmTypes = context.getDbColunmTypes();
        SqlInfoAlterModel alterModel = context.getAlterModel();

        if(FuncBase.isEmpty(alterModel)){
            return null;
        }
        //存在自定义ddl
        List<String> executeDdlList = alterModel.getExecuteDdlList();
        if(FuncBase.isNotEmpty(executeDdlList)){
            return PlusUtils.ddl2SqlFormatModel(executeDdlList);
        }

        String ddl="";
        switch (alterModel.getAlterType()) {
            case ADD_COLUMN:
                ddl = getAddColumnDDL(context.getSchemaName(),alterModel.getTableName(),alterModel.getFieldModel(),dbColunmTypes);
                break;
            case UPDATE_COLUMN:
                ddl = getModifyColumnDDL(context.getSchemaName(),alterModel.getTableName(),alterModel.getFieldModel(),dbColunmTypes);
                break;
        }
        return PlusUtils.ddl2SqlFormatModel(ddl);
    }


    //获取增加字段ddl
    private String getAddColumnDDL(String schemaName,String tableName, FieldModel fieldModel, DbColunmTypesEntity dbColunmTypes) {
        String symbol = dbColunmTypes.getSymbol();
        Boolean upperFlag = dbColunmTypes.getUpperFlag();

        tableName= FuncBase.concatSymbol(tableName,symbol,upperFlag);
        schemaName= FuncBase.concatSymbol(schemaName,symbol);

        JeeLowCodeFieldTypeEnum fieldTypeEnum = fieldModel.getFieldType();
        DbColunmTypesEntity.TypeEntity typeEntity = PlusUtils.jeelowCodeType2DbType(fieldTypeEnum, dbColunmTypes);
        String fieldType = PlusUtils.getDbType(fieldTypeEnum, typeEntity.getDbType(), fieldModel.getFieldLen(), fieldModel.getFieldPointLen(),typeEntity.getDbMaxLen());
        String defaultValSql = PlusUtils.getDefaultValSql(fieldModel.getFieldDefaultVal(), fieldModel.getIsNull(),fieldModel.getDbNowIsNull());
        String fieldCode = FuncBase.concatSymbol(fieldModel.getFieldCode(),symbol,upperFlag);

        JeeLowCodeAlterTable alterTable = new JeeLowCodeAlterTable();
        alterTable.setTableName(schemaName+ StringPool.DOT+tableName);
        alterTable.addCoulumn(fieldCode, fieldType, defaultValSql);
        String ddl = alterTable.getFullSQL();
        return ddl;
    }

    //修改字段
    private String getModifyColumnDDL(String schemaName,String tableName, FieldModel fieldModel, DbColunmTypesEntity dbColunmTypes) {
        String symbol = dbColunmTypes.getSymbol();
        Boolean upperFlag = dbColunmTypes.getUpperFlag();


        tableName=FuncBase.concatSymbol(tableName,symbol,upperFlag);
        schemaName= FuncBase.concatSymbol(schemaName,symbol);

        JeeLowCodeFieldTypeEnum fieldTypeEnum = fieldModel.getFieldType();
        DbColunmTypesEntity.TypeEntity typeEntity = PlusUtils.jeelowCodeType2DbType(fieldTypeEnum, dbColunmTypes);
        String fieldType = PlusUtils.getDbType(fieldTypeEnum, typeEntity.getDbType(), fieldModel.getFieldLen(), fieldModel.getFieldPointLen(),typeEntity.getDbMaxLen());
        String defaultValSql = PlusUtils.getDefaultValSql(fieldModel.getFieldDefaultVal(), fieldModel.getIsNull(),fieldModel.getDbNowIsNull());
        String fieldCode = fieldModel.getFieldCode();

        JeeLowCodeAlterTable alterTable = new JeeLowCodeAlterTable();
        alterTable.setTableName(schemaName+ StringPool.DOT+tableName);
        alterTable.modifyColumn(FuncBase.concatSymbol(fieldCode,symbol,upperFlag), fieldType, defaultValSql);
        return alterTable.getFullSQL();
    }

}
