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
package com.jeelowcode.framework.utils.component.devonly;

import cn.hutool.json.JSONUtil;
import com.jeelowcode.framework.utils.utils.SqlFieldPath;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

/**
 * 受限字段快照：字段路径 -> 归一化后的 SQL 文本。
 *
 * diff 只保留「新值非空且与旧值不同」的路径；旧有新无（清空/删除）不计入变更。
 */
public final class SqlFieldSnapshot {

    private final Map<String, String> values = new LinkedHashMap<>();
    private final boolean restricted;

    private SqlFieldSnapshot(boolean restricted) {
        this.restricted = restricted;
    }

    public static SqlFieldSnapshot empty() {
        return new SqlFieldSnapshot(false);
    }

    /**
     * fields 为空时表示整个方法受限写入。
     */
    public static SqlFieldSnapshot restricted() {
        SqlFieldSnapshot snapshot = new SqlFieldSnapshot(true);
        snapshot.values.put(SqlFieldPath.RESTRICTED_KEY, "1");
        return snapshot;
    }

    /**
     * 写入一个字段值，空值不记入快照。
     */
    public void put(String path, String rawValue) {
        String value = normalize(rawValue);
        if (path == null || value.isEmpty()) {
            return;
        }
        values.put(path, value);
    }

    public boolean isEmpty() {
        return values.isEmpty();
    }

    public boolean isRestricted() {
        return restricted;
    }

    public Map<String, String> values() {
        return Collections.unmodifiableMap(values);
    }

    /**
     * 与旧快照比较，返回发生变更且新值非空的路径。
     */
    public Set<String> diff(SqlFieldSnapshot oldSnapshot) {
        Set<String> changed = new LinkedHashSet<>();
        Map<String, String> oldValues = oldSnapshot == null
                ? Collections.<String, String>emptyMap() : oldSnapshot.values;
        for (Map.Entry<String, String> entry : values.entrySet()) {
            String oldValue = oldValues.get(entry.getKey());
            if (oldValue == null || !oldValue.equals(entry.getValue())) {
                changed.add(entry.getKey());
            }
        }
        return changed;
    }

    /**
     * 归一化：trim、折叠空白、JSON 按键名排序后重新序列化，不做大小写折叠。
     */
    private static String normalize(String rawValue) {
        if (rawValue == null) {
            return "";
        }
        String value = rawValue.trim().replaceAll("\\s+", " ");
        if (value.isEmpty()) {
            return "";
        }
        if (value.startsWith("{") || value.startsWith("[")) {
            try {
                return JSONUtil.toJsonStr(sortTree(JSONUtil.parse(value)));
            } catch (Exception ignored) {
                // 非合法 JSON 时按普通文本比较
            }
        }
        return value;
    }

    @SuppressWarnings("unchecked")
    private static Object sortTree(Object node) {
        if (node instanceof Map) {
            Map<String, Object> sorted = new TreeMap<>();
            for (Object entry : ((Map<?, ?>) node).entrySet()) {
                Map.Entry<?, ?> item = (Map.Entry<?, ?>) entry;
                sorted.put(String.valueOf(item.getKey()), sortTree(item.getValue()));
            }
            return sorted;
        }
        if (node instanceof Iterable) {
            List<Object> list = new ArrayList<>();
            for (Object item : (Iterable<?>) node) {
                list.add(sortTree(item));
            }
            return list;
        }
        return node;
    }
}
