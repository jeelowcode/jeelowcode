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
import com.jeelowcode.framework.plus.build.buildmodel.ddl.SqlInfoCreateIndexModel;
import com.jeelowcode.framework.plus.entity.DbColunmTypesEntity;
import com.jeelowcode.framework.plus.utils.PlusUtils;
import com.jeelowcode.framework.plus.core.model.SqlFormatModel;
import com.jeelowcode.framework.utils.utils.FuncBase;
import net.sf.jsqlparser.JSQLParserException;

import java.util.List;
import java.util.stream.Collectors;

/***
 * 索引相关
 */
public class BuildCreateIndex implements ISQLExpression<List<SqlFormatModel>> {



    @Override
    public List<SqlFormatModel> interpret(SQLInterpretContext context) throws JSQLParserException {
        SqlInfoCreateIndexModel model = context.getCreateIndexModel();
        DbColunmTypesEntity dbColunmTypes = context.getDbColunmTypes();
        if(FuncBase.isEmpty(model)){
            return null;
        }
        //存在自定义ddl
        List<String> executeDdlList = model.getExecuteDdlList();
        if(FuncBase.isNotEmpty(executeDdlList)){
            return PlusUtils.ddl2SqlFormatModel(executeDdlList);
        }

        String symbol = dbColunmTypes.getSymbol();
        Boolean upperFlag = dbColunmTypes.getUpperFlag();

        String tableName = FuncBase.concatSymbol(model.getTableName(),symbol,upperFlag);
        String indexName = FuncBase.concatSymbol(model.getIndexName(),symbol,upperFlag);
        String schemaName = FuncBase.concatSymbol(context.getSchemaName(),symbol);

        List<String> indexFieldCodeList = model.getIndexFieldCodeList();

        List<String> list = indexFieldCodeList.stream()
                .map(key -> FuncBase.concatSymbol( key , symbol,upperFlag))
                .collect(Collectors.toList());

        String str = "CREATE INDEX %s ON %s.%s (%s)";
        String ddl = String.format(str, indexName, schemaName,tableName, String.join(",", list));
        return PlusUtils.ddl2SqlFormatModel(ddl);


    }

}
