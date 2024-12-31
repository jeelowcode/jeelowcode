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
package com.jeelowcode.framework.plus.flyweight;

import com.jeelowcode.framework.exception.JeeLowCodeException;
import com.jeelowcode.framework.plus.enums.DbTypeEnum;
import com.jeelowcode.framework.plus.template.AbstractDbTemplate;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * 数据库享元工厂
 */
public class FlyweightDBFactory {

    /**
     * Db缓存池
     */
    private static final Map<DbTypeEnum, AbstractDbTemplate> DB_POOL = new HashMap<>();

    static {
        Arrays.stream(DbTypeEnum.values())
                .forEach(dbTypeEnum -> {
                    try {
                        Class<? extends AbstractDbTemplate> clazz = dbTypeEnum.getDbTemplate();
                        AbstractDbTemplate dbTemplate = clazz.newInstance();//初始化
                        DB_POOL.put(dbTypeEnum, dbTemplate);
                    } catch (Exception e) {

                    }
                });
    }

    /**
     * 获取数据库产品类型
     */
    public static AbstractDbTemplate getDbType(DbTypeEnum dbTypeEnum) {
        AbstractDbTemplate dbTemplate = DB_POOL.get(dbTypeEnum);
        if (dbTemplate == null) {
            throw new JeeLowCodeException("获取不到该数据类型，需要先添加到DB_POOL");
        } else {
            return dbTemplate;
        }
    }

}
