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
package com.jeelowcode.framework.plus.chain;


import com.jeelowcode.framework.plus.chain.parser.DataPermissionsSQLParser;
import com.jeelowcode.framework.plus.chain.parser.StandardSQLParser;
import com.jeelowcode.framework.plus.chain.parser.TenantSQLParser;
import com.jeelowcode.framework.plus.entity.SqlInfoEntity;
import com.jeelowcode.framework.utils.utils.FuncBase;

/**
 * 使用责任链
 */
public class SQLParserChainClient {


    //处理sql
    public static void sqlParser(SqlInfoEntity sqlInfoEntity){

        //标准解析 -> 数据权限 -> 租户权限

        //标准SQL
        AbstractSQLParser standardSQLParser = new StandardSQLParser(AbstractSQLParser.levelStandard);
        //数据权限
        AbstractSQLParser dataPermissionsSQLParser = new DataPermissionsSQLParser(AbstractSQLParser.levelDataPermissions);
        //租户权限
        AbstractSQLParser tenantSQLParser = new TenantSQLParser(AbstractSQLParser.levelTenant);

        //单独要某一个
        Integer sqlParserLevel = sqlInfoEntity.getSqlParserLevel();
        if(FuncBase.isNotEmpty(sqlParserLevel)){
            switch (sqlParserLevel){
                case 3:
                    standardSQLParser.processed(AbstractSQLParser.levelStandard,sqlInfoEntity);
                    break;
                case 2:
                    dataPermissionsSQLParser.processed(AbstractSQLParser.levelDataPermissions,sqlInfoEntity);
                    break;
                case 1:
                    tenantSQLParser.processed(AbstractSQLParser.levelTenant,sqlInfoEntity);
                    break;
            }
            return;
        }


        SQLParserChain sqlParserChain=new SQLParserChain();
        sqlParserChain.setSqlParserChain(standardSQLParser,dataPermissionsSQLParser,tenantSQLParser);
        AbstractSQLParser sqlParser = sqlParserChain.getSqlParserChain();

        //先走标准sql解析
        sqlParser.processed(AbstractSQLParser.levelStandard, sqlInfoEntity);
    }


}
