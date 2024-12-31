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


public class SQLParserChain {
    private AbstractSQLParser sqlParserChain;

    public AbstractSQLParser getSqlParserChain() {
        return sqlParserChain;
    }

    public void setSqlParserChain(AbstractSQLParser... sqlParserChains) {
        sqlParserChain = sqlParserChains[0];//第一个

        // 首先，检查是否有至少两个解析器，因为我们需要至少两个来设置链
        if (sqlParserChains == null || sqlParserChains.length < 2) {
            return;
        }

        // 遍历解析器数组，设置每个解析器的下一个解析器
        for (int i = 0; i < sqlParserChains.length - 1; i++) {
            sqlParserChains[i].setNextSQLParse(sqlParserChains[i + 1]);
        }

    }
}
