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
import com.jeelowcode.framework.plus.chain.SQLParserChainClient;
import com.jeelowcode.framework.plus.entity.SqlInfoEntity;

/**
 * 数据库模板
 */
public abstract class AbstractDbTemplate {
    
    //初始化数据库类型
    protected abstract void initDbColunmTypes(SqlInfoEntity sqlInfoEntity);

    //格式化sql上下文
    protected abstract void formatSQLInterpretContext(SQLInterpretContext context);

    //TODO 不一样的类型在之类处理
    public final void operation(SqlInfoEntity sqlInfoEntity){
        //初始化数据库特有类型
        initDbColunmTypes(sqlInfoEntity);
        //处理上下文
        formatSQLInterpretContext(sqlInfoEntity.getContext());
        //处理sql/ddl
        SQLParserChainClient.sqlParser(sqlInfoEntity);
    }
}
