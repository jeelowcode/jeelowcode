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
import com.jeelowcode.framework.plus.build.buildmodel.ddl.SqlInfoDropModel;
import com.jeelowcode.framework.plus.entity.DbColunmTypesEntity;
import com.jeelowcode.framework.plus.utils.PlusUtils;
import com.jeelowcode.framework.plus.core.ddl.alter.JeeLowCodeAlterTable;
import com.jeelowcode.framework.plus.core.ddl.drop.JeeLowCodeDropTable;
import com.jeelowcode.framework.plus.core.model.SqlFormatModel;
import com.jeelowcode.framework.utils.tool.StringPool;
import com.jeelowcode.framework.utils.utils.FuncBase;
import net.sf.jsqlparser.JSQLParserException;

import java.util.List;

/**
 * 删除表，字段，索引
 */
public class BuildDrop implements ISQLExpression<List<SqlFormatModel>> {


    @Override
    public List<SqlFormatModel> interpret(SQLInterpretContext context) throws JSQLParserException {
        SqlInfoDropModel dropModel = context.getDropModel();
        if (FuncBase.isEmpty(dropModel)) {
            return null;
        }
        //存在自定义ddl
        List<String> executeDdlList = dropModel.getExecuteDdlList();
        if(FuncBase.isNotEmpty(executeDdlList)){
            return PlusUtils.ddl2SqlFormatModel(executeDdlList);
        }

        DbColunmTypesEntity dbColunmTypes = context.getDbColunmTypes();
        String symbol = dbColunmTypes.getSymbol();
        Boolean upperFlag = dbColunmTypes.getUpperFlag();

        String tableName = FuncBase.concatSymbol(dropModel.getTableName(), symbol, upperFlag);
        String indexName = FuncBase.concatSymbol(dropModel.getIndexName(), symbol, upperFlag);
        String cloumnCode = FuncBase.concatSymbol(dropModel.getCloumnCode(), symbol, upperFlag);
        String schemaName = FuncBase.concatSymbol(context.getSchemaName(), symbol);

        String ddl = "";
        switch (dropModel.getDropType()) {
            case DROP_TABLE:
                ddl = this.dropTable(schemaName, tableName);
                break;
            case DROP_COLUMN:
                ddl = this.dropColumn(schemaName, tableName, cloumnCode,dropModel.getCloumnFormat());
                break;
            case DROP_INDEX:
                ddl = this.dropIndex(schemaName, indexName);
                break;
        }

        return PlusUtils.ddl2SqlFormatModel(ddl);
    }

    /**
     * 删除表
     *
     * @param schemaName
     * @param tableName
     * @return
     */
    private String dropTable(String schemaName, String tableName) {
        JeeLowCodeDropTable dropTable = new JeeLowCodeDropTable();
        dropTable.setTableName(schemaName + StringPool.DOT + tableName);
        return dropTable.getFullSQL();
    }

    /**
     * 删除字段
     *
     * @param schemaName
     * @param tableName
     * @param cloumnCode
     * @return
     */
    private String dropColumn(String schemaName, String tableName, String cloumnCode,String cloumnFormat) {
        if(FuncBase.isNotEmpty(cloumnFormat)){
            cloumnCode =String.format(cloumnFormat,cloumnCode);
        }
        JeeLowCodeAlterTable dropColumn = new JeeLowCodeAlterTable();
        dropColumn.setTableName(schemaName + StringPool.DOT + tableName);
        dropColumn.dropColumn(cloumnCode);
        return dropColumn.getFullSQL();
    }

    /**
     * 删除索引
     * @param schemaName
     * @param indexName
     * @return
     */
    private String dropIndex(String schemaName, String indexName) {
        String str = "DROP INDEX %s.%s";
        String ddl = String.format(str, schemaName, indexName);
        return ddl;
    }

}
