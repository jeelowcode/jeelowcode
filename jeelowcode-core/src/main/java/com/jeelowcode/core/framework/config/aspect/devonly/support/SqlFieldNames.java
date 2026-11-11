/*
Apache License
Version 2.0, January 2004
http://www.apache.org/licenses/LICENSE-2.0
本软件受适用的国家软件著作权法（包括国际条约）和开源协议 双重保护许可。

开源协议中文释意如下：
1.JeeLowCode开源版本无任何限制，在遵循本开源协议（Apache2.0）条款下，【允许商用】使用，不会造成侵权行为。
2.允许基于本平台软件开展业务系统开发。
3.在任何情况下，您不得使用本软件开发可能被认为与【本软件竞争】的软件。

最终解释权归：http://www.jeelowcode.com
*/
package com.jeelowcode.core.framework.config.aspect.devonly.support;

/**
 * 旧值快照提取使用的受检字段全集。
 *
 * 旧快照允许比新快照多出字段：SqlFieldSnapshot.diff 只遍历新值路径，
 * 因此 provider 复用该全集即可保证与新值侧路径逐字符一致。
 */
public final class SqlFieldNames {

    public static final String[] ALL = {
            "executeSql",
            "whereConfig",
            "summarySql",
            "sql.custom",
            "sql.group",
            "fun",
            "onlineScript",
            "ruleSql"
    };

    private SqlFieldNames() {
    }
}
