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
package com.jeelowcode.framework.utils.utils;


import cn.hutool.core.date.DateUtil;

import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class JeeLowCodeUtils {


    /**
     * 使用正则获取所有的key
     * 获取sql中的#{key} 这个key组成的set
     */
    public static Set<String> getSqlRuleParams(String sql) {
        if (FuncBase.isEmpty(sql)) {
            return null;
        }
        Set<String> varParams = new HashSet<String>();
        String regex = "\\#\\{\\w+\\}";

        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(sql);
        while (m.find()) {
            String group = m.group();
            varParams.add(group.substring(group.indexOf("{") + 1, group.indexOf("}")));
        }
        return varParams;
    }

    /**
     * 获取map值
     * (map的value是单个的数据时使用)
     *
     * @param map 查询封装的一条数据
     * @param key 高查条件的key
     * @return
     */
    public static String getMap2Str(Map<String, Object> map, String key) {
        // 参数校验:
        if (FuncBase.isEmpty(key) || FuncBase.isEmpty(map)) {
            return "";
        }
        // 根据高查条件的key获取value
        Object o = map.get(key);
        if (FuncBase.isEmpty(o)) {
            return "";
        }
        // object -> String
        String value = FuncBase.toStr(o);
        // 返回
        return value;
    }

    public static List<String> getMap2List(Map<String, Object> map, String key) {
        // 参数校验:
        if (FuncBase.isEmpty(key) || FuncBase.isEmpty(map)) {
            return null;
        }
        // 根据高查条件的key获取value
        Object o = map.get(key);
       return (List<String>)o;
    }

    /**
     * 获取map值
     *
     * @param map
     * @param key
     * @return
     */
    public static Integer getMap2Integer(Map<String, Object> map, String key) {
        String str = getMap2Str(map, key);
        if (FuncBase.isEmpty(str)) {
            return -1;
        }
        return FuncBase.toInt(str);
    }


    /**
     * 获取map值
     *
     * @param map
     * @param key
     * @return
     */
    public static Long getMap2Long(Map<String, Object> map, String key) {
        String str = getMap2Str(map, key);
        if (FuncBase.isEmpty(str)) {
            return -1L;
        }
        return FuncBase.toLong(str);
    }

    /**
     * 获取map值
     * (map的value是单个的数据时使用)
     *
     * @param map 查询封装的一条数据
     * @param key 高查条件的key
     * @return
     */
    public static Date getMap2DateTime(Map<String, Object> map, String key) {
        // 参数校验:
        if (FuncBase.isEmpty(key) || FuncBase.isEmpty(map)) {
            return null;
        }
        // 根据高查条件的key获取value
        Object o = map.get(key);
        if (FuncBase.isEmpty(o)) {
            return null;
        }
        if (o instanceof Date) {
            if (o instanceof java.sql.Date || o instanceof Time) {
                return new Date(((Date) o).getTime());
            }
            return (Date) o;
        }
        // object -> String
        String value = FuncBase.toStr(o);
        return DateUtil.parseDateTime(value);
    }

    /**
     * 获取map值
     *
     * @param map
     * @param key
     * @return
     */
    public static Map<String, Object> getMap2Map(Map<String, Object> map, String key) {
        if (FuncBase.isEmpty(key) || FuncBase.isEmpty(map)) {
            return null;
        }
        return (Map<String, Object>)map.get(key);
    }

    // 提取日期时间格式化逻辑到单独的方法
    public static String formatDate(Object value) {
        if (value instanceof LocalDateTime) {
            return ((LocalDateTime) value).format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        } else if (value instanceof LocalDate) {
            return ((LocalDate) value).format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        } else if (value instanceof LocalTime) {
            return ((LocalTime) value).format(DateTimeFormatter.ofPattern("HH:mm:ss"));
        } else if (value instanceof java.sql.Time) {
            return ((java.sql.Time) value).toLocalTime().format(DateTimeFormatter.ofPattern("HH:mm:ss"));
        } else if (value instanceof java.sql.Timestamp) {
            return ((java.sql.Timestamp) value).toLocalDateTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        } else if (value instanceof Date) {
            return DateUtil.formatDate((Date)value);
        } else {
            return FuncBase.toStr(value);
        }
    }


}
