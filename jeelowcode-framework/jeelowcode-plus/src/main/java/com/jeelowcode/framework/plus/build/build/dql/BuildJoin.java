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
package com.jeelowcode.framework.plus.build.build.dql;


import com.jeelowcode.framework.plus.build.ISQLExpression;
import com.jeelowcode.framework.plus.build.buildmodel.SQLInterpretContext;
import com.jeelowcode.framework.plus.build.buildmodel.dql.SqlInfoJoinModel;
import com.jeelowcode.framework.plus.core.model.SqlFormatModel;
import net.sf.jsqlparser.JSQLParserException;
/**
 * left join ,inner join ,right join相关
 */
public class BuildJoin implements ISQLExpression<SqlFormatModel>  {


    @Override
    public SqlFormatModel interpret(SQLInterpretContext context) throws JSQLParserException {

        SqlInfoJoinModel model = context.getJoinModel();
        SqlInfoJoinModel.JoinType joinType = model.getJoinType();
        String onStr = model.getOnStr();
        String tableName = model.getTableName();

        String joinSymbolStr="";
        switch (joinType) {
            case LEFT_JOIN:
                joinSymbolStr = "LEFT JOIN";
                break;
            case RIGHT_JOIN:
                joinSymbolStr = "RIGHT JOIN";
                break;
            case INNER_JOIN:
                joinSymbolStr = "INNER JOIN";
                break;
        }
        String sql= joinSymbolStr+" "+tableName+" on "+onStr;

        SqlFormatModel sqlFormatModel = new SqlFormatModel(sql);
        return sqlFormatModel;
    }



}
