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
package com.jeelowcode.framework.plus.enums;


import com.jeelowcode.framework.plus.template.*;
import com.jeelowcode.framework.utils.utils.FuncBase;
import com.jeelowcode.framework.plus.template.*;

import java.util.Arrays;
import java.util.Optional;


/**
 * 数据库类型
 */
public enum DbTypeEnum {

    MYSQL("mysql", "Mysql数据库", DbMysql.class),
    ORACLE("oracle", "oracle数据库", DbOracle.class),
    POSTGRESQL("postgresql", "Postgresql数据库", DbPostgresql.class),
    DM("dm dbms", "达梦数据库", DbDm.class);

    /**
     * 数据库类型
     */
    private final String dbCode;
    /**
     * 数据名称
     */
    private final String name;

    /**
     * 实现类
     */
    private final Class<? extends AbstractDbTemplate> dbTemplate;

    public static DbTypeEnum getByDbCode(String dbCode) {

        // 使用流来查找匹配的DbProductEnum
        Optional<DbTypeEnum> matchingEnum = Arrays.stream(DbTypeEnum.values())
                .filter(dbTypeEnum -> FuncBase.equals(dbTypeEnum.getDbCode(), dbCode))
                .findFirst(); // findFirst()会返回第一个匹配的元素，或者如果找不到则返回一个空的Optional
        // 检查是否找到了匹配的枚举项
        if (matchingEnum.isPresent()) {
            return matchingEnum.get();
        }
        return null;

    }

    public String getDbCode() {
        return dbCode;
    }

    public String getName() {
        return name;
    }

    public Class<? extends AbstractDbTemplate> getDbTemplate() {
        return dbTemplate;
    }

    DbTypeEnum(String dbCode, String name, Class<? extends AbstractDbTemplate> dbTemplate) {
        this.dbCode = dbCode;
        this.name = name;
        this.dbTemplate = dbTemplate;
    }
}
