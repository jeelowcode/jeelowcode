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
import com.jeelowcode.framework.plus.build.buildmodel.ddl.SqlInfoCommentModel;
import com.jeelowcode.framework.plus.entity.DbColunmTypesEntity;
import com.jeelowcode.framework.plus.entity.FieldModel;
import com.jeelowcode.framework.plus.utils.PlusUtils;
import com.jeelowcode.framework.plus.core.model.SqlFormatModel;
import com.jeelowcode.framework.utils.utils.FuncBase;
import net.sf.jsqlparser.JSQLParserException;

import java.util.ArrayList;
import java.util.List;


/**
 * 备注 相关
 */
public class BuildComment implements ISQLExpression<List<SqlFormatModel>> {


    @Override
    public List<SqlFormatModel> interpret(SQLInterpretContext context) throws JSQLParserException {
        SqlInfoCommentModel commentModel = context.getCommentModel();
        if (FuncBase.isEmpty(commentModel)) {
            return null;
        }

        //存在自定义ddl
        List<String> executeDdlList = commentModel.getExecuteDdlList();
        if (FuncBase.isNotEmpty(executeDdlList)) {
            return PlusUtils.ddl2SqlFormatModel(executeDdlList);
        }


        DbColunmTypesEntity dbColunmTypes = context.getDbColunmTypes();

        String symbol = dbColunmTypes.getSymbol();
        Boolean upperFlag = dbColunmTypes.getUpperFlag();

        String schemaName = FuncBase.concatSymbol(context.getSchemaName(), symbol);
        String tableName = FuncBase.concatSymbol(commentModel.getTableName(), symbol, upperFlag);
        String tableAlias = commentModel.getTableAlias();
        List<FieldModel> fieldModelList = commentModel.getFieldModelList();

        List<String> ddlList = new ArrayList<>();
        String tableDdl = this.tableComment(schemaName, tableName, tableAlias);
        if (FuncBase.isNotEmpty(tableDdl)) {
            ddlList.add(tableDdl);
        }
        List<String> columnDdlList = this.columnComment(dbColunmTypes, schemaName, tableName, fieldModelList);
        if (FuncBase.isNotEmpty(columnDdlList)) {
            ddlList.addAll(columnDdlList);
        }

        return PlusUtils.ddl2SqlFormatModel(ddlList);
    }

    /**
     * 表备注
     *
     * @return
     */
    private String tableComment(String schemaName, String tableName, String tableAlias) {
        if (FuncBase.isEmpty(tableAlias)) {
            return null;
        }
        String str = "COMMENT ON TABLE %s.%s IS '%s';";
        String ddl = String.format(str, schemaName, tableName, tableAlias);
        return ddl;
    }

    private List<String> columnComment(DbColunmTypesEntity dbColunmTypes, String schemaName, String tableName, List<FieldModel> fieldModelList) {

        if (FuncBase.isEmpty(fieldModelList)) {
            return null;
        }
        String symbol = dbColunmTypes.getSymbol();
        Boolean upperFlag = dbColunmTypes.getUpperFlag();

        List<String> ddlList = new ArrayList<>();
        for (FieldModel fieldModel : fieldModelList) {
            String fieldCode = FuncBase.concatSymbol(fieldModel.getFieldCode(), symbol, upperFlag);
            String fieldName = fieldModel.getFieldName();

            String str = "COMMENT ON COLUMN %s.%s.%s IS '%s';";
            String ddl = String.format(str, schemaName, tableName, fieldCode, fieldName);
            ddlList.add(ddl);
        }
        return ddlList;
    }

}
