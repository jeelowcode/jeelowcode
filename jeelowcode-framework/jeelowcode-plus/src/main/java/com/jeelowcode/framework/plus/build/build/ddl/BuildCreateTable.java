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
import com.jeelowcode.framework.plus.build.buildmodel.ddl.SqlInfoCreateModel;
import com.jeelowcode.framework.plus.entity.DbColunmTypesEntity;
import com.jeelowcode.framework.plus.entity.FieldModel;
import com.jeelowcode.framework.plus.utils.PlusUtils;
import com.jeelowcode.framework.plus.core.ddl.create.JeeLowCodeCreateTable;
import com.jeelowcode.framework.plus.core.model.SqlFormatModel;
import com.jeelowcode.framework.utils.enums.JeeLowCodeFieldTypeEnum;
import com.jeelowcode.framework.utils.tool.StringPool;
import com.jeelowcode.framework.utils.utils.FuncBase;
import net.sf.jsqlparser.JSQLParserException;

import java.util.List;

/**
 * 建表相关
 */
public class BuildCreateTable implements ISQLExpression<List<SqlFormatModel>> {

    @Override
    public List<SqlFormatModel> interpret(SQLInterpretContext context) throws JSQLParserException {
        DbColunmTypesEntity dbColunmTypes = context.getDbColunmTypes();
        SqlInfoCreateModel creteModel = context.getCreateModel();
        if(FuncBase.isEmpty(creteModel)){
            return null;
        }
        //存在自定义ddl
        List<String> executeDdlList = creteModel.getExecuteDdlList();
        if(FuncBase.isNotEmpty(executeDdlList)){
            return PlusUtils.ddl2SqlFormatModel(executeDdlList);
        }

        String tableName = creteModel.getTableName();
        String schemaName = context.getSchemaName();
        List<FieldModel> tableOptions = creteModel.getTableOptions();

        String ddl = this.getDDL(schemaName,tableName, tableOptions, dbColunmTypes);
        return PlusUtils.ddl2SqlFormatModel(ddl);
    }

    /**
     * 获取ddl
     *
     * @param tableName
     * @param tableOptions
     * @param dbColunmTypes
     * @return
     */
    public String getDDL(String schemaName,String tableName, List<FieldModel> tableOptions, DbColunmTypesEntity dbColunmTypes) {
        Boolean upperFlag = dbColunmTypes.getUpperFlag();
        String symbol = dbColunmTypes.getSymbol();
        JeeLowCodeCreateTable createTable = new JeeLowCodeCreateTable();

        tableName=FuncBase.concatSymbol(tableName,symbol,upperFlag);
        schemaName=FuncBase.concatSymbol(schemaName,symbol);
        for (int i = 0; i < tableOptions.size(); i++) {
            FieldModel fieldModel = tableOptions.get(i);
            String fieldCode = FuncBase.concatSymbol(fieldModel.getFieldCode(),symbol,upperFlag);
            JeeLowCodeFieldTypeEnum fieldTypeEnum = fieldModel.getFieldType();
            //类型
            DbColunmTypesEntity.TypeEntity typeEntity = PlusUtils.jeelowCodeType2DbType(fieldTypeEnum, dbColunmTypes);
            Integer dbMaxLen = typeEntity.getDbMaxLen();
            if(dbMaxLen < fieldModel.getFieldLen()){
                fieldModel.setFieldLen(dbMaxLen);
            }
            String fieldType = PlusUtils.getDbType(fieldTypeEnum, typeEntity.getDbType(), fieldModel.getFieldLen(), fieldModel.getFieldPointLen(),typeEntity.getDbMaxLen());
            String defaultValSql = PlusUtils.getDefaultValSql(fieldModel.getFieldDefaultVal(), fieldModel.getIsNull(),null);

            createTable.addCoulumn(fieldCode, fieldType, defaultValSql);
        }
        createTable.setTableName(schemaName+ StringPool.DOT+tableName);//表名称
        String ddl = createTable.getDDL();
        return ddl;
    }


}
