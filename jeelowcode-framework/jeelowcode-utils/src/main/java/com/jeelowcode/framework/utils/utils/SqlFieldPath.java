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
package com.jeelowcode.framework.utils.utils;

import java.util.Map;

/**
 * 受限字段路径的唯一生成入口。
 *
 * SqlFieldDetector（新值）与所有 OldSqlValueProvider（旧值）必须复用本类，
 * 否则新旧快照 key 不一致会把未变更字段判成「首次写入」，造成生产全量误拦。
 */
public final class SqlFieldPath {

    /** fields 为空时整接口受限的标记 key */
    public static final String RESTRICTED_KEY = "__jeeLowCodeRestricted__";

    private SqlFieldPath() {
    }

    /**
     * 拼接普通字段路径。
     */
    public static String leaf(String parent, String name) {
        if (parent == null || parent.isEmpty()) {
            return name;
        }
        return parent + "." + name;
    }

    /**
     * 生成列表项路径，优先用 id，其次 fieldCode，都没有则用下标。
     */
    public static String listItem(String parent, Object item, int index) {
        String key = "index=" + index;
        if (item instanceof Map) {
            Map<?, ?> map = (Map<?, ?>) item;
            Object id = map.get("id");
            if (id != null && !String.valueOf(id).trim().isEmpty()) {
                key = "id=" + String.valueOf(id).trim();
            } else {
                Object fieldCode = map.get("fieldCode");
                if (fieldCode != null && !String.valueOf(fieldCode).trim().isEmpty()) {
                    key = "fieldCode=" + String.valueOf(fieldCode).trim();
                }
            }
        }
        return (parent == null ? "" : parent) + "[" + key + "]";
    }

    /**
     * fields 支持裸叶子名、完整路径、以及后缀路径三种写法。
     */
    public static boolean matches(String[] fields, String path, String leafName) {
        if (fields == null || path == null) {
            return false;
        }
        for (String item : fields) {
            if (item == null) {
                continue;
            }
            String field = item.trim();
            if (field.isEmpty()) {
                continue;
            }
            if (field.equals(leafName) || field.equals(path) || path.endsWith("." + field)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 从路径中取出最后一段字段名（去掉列表下标部分）。
     */
    public static String leafName(String path) {
        if (path == null || path.isEmpty()) {
            return "";
        }
        String name = path;
        int dot = name.lastIndexOf('.');
        if (dot >= 0) {
            name = name.substring(dot + 1);
        }
        int bracket = name.indexOf('[');
        if (bracket >= 0) {
            name = name.substring(0, bracket);
        }
        return name;
    }
}
