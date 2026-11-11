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

import cn.hutool.json.JSONNull;
import cn.hutool.json.JSONUtil;
import com.jeelowcode.framework.utils.component.devonly.SqlFieldSnapshot;
import org.springframework.validation.BindingResult;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 把方法入参归一化成受限字段快照。
 *
 * 只处理 DTO 类参数；跳过 HttpServletRequest 等非 DTO 参数，避免序列化失败后
 * 触发失败关闭，导致生产环境表单/报表新增被一律拒绝。
 */
public final class SqlFieldDetector {

    private SqlFieldDetector() {
    }

    public static SqlFieldSnapshot snapshot(Object[] args, String[] fields) {
        SqlFieldSnapshot snapshot = SqlFieldSnapshot.empty();
        if (args == null || fields == null || fields.length == 0) {
            return snapshot;
        }
        for (Object arg : args) {
            if (!isDto(arg)) {
                continue;
            }
            walk(snapshot, "", toTree(arg), fields);
        }
        return snapshot;
    }

    /**
     * 只接受 DTO / Map / 集合 / 数组，排除 Servlet、Spring Web 等非业务参数。
     */
    private static boolean isDto(Object arg) {
        if (arg == null) {
            return false;
        }
        if (arg instanceof CharSequence || arg instanceof Number || arg instanceof Boolean
                || arg instanceof Character || arg instanceof Enum || arg instanceof Date) {
            return false;
        }
        if (arg instanceof ServletRequest || arg instanceof ServletResponse
                || arg instanceof MultipartFile || arg instanceof BindingResult
                || arg instanceof Class || arg instanceof ClassLoader) {
            return false;
        }
        if (arg instanceof Map || arg instanceof Iterable || arg.getClass().isArray()) {
            return true;
        }
        String name = arg.getClass().getName();
        return !name.startsWith("java.") && !name.startsWith("javax.")
                && !name.startsWith("org.springframework.") && !name.startsWith("com.sun.");
    }

    private static Object toTree(Object arg) {
        if (arg instanceof CharSequence) {
            Object parsed = tryParse(arg.toString());
            return parsed == null ? arg.toString() : parsed;
        }
        if (arg instanceof Map || arg instanceof Iterable) {
            return arg;
        }
        if (arg.getClass().isArray()) {
            int length = Array.getLength(arg);
            List<Object> list = new ArrayList<>(length);
            for (int i = 0; i < length; i++) {
                list.add(Array.get(arg, i));
            }
            return list;
        }
        return JSONUtil.parseObj(JSONUtil.toJsonStr(arg));
    }

    @SuppressWarnings("unchecked")
    private static void walk(SqlFieldSnapshot snapshot, String parent, Object node, String[] fields) {
        if (node == null || node instanceof JSONNull) {
            return;
        }
        if (node instanceof Map) {
            for (Object entry : ((Map<?, ?>) node).entrySet()) {
                Map.Entry<?, ?> item = (Map.Entry<?, ?>) entry;
                String leafName = String.valueOf(item.getKey());
                String path = SqlFieldPath.leaf(parent, leafName);
                Object value = item.getValue();
                if (value == null || value instanceof JSONNull) {
                    continue;
                }
                boolean matched = SqlFieldPath.matches(fields, path, leafName);
                if (matched && isScalar(value)) {
                    snapshot.put(path, String.valueOf(value));
                } else if (value instanceof CharSequence) {
                    String text = value.toString();
                    if (matched) {
                        // 受检字段本身按整体字符串比较（如 whereConfig）
                        snapshot.put(path, text);
                    }
                    Object parsed = tryParse(text);
                    if (parsed != null) {
                        // JSON 字符串二次下钻（如 dataSourcesConfig / formatConfig）
                        walk(snapshot, path, parsed, fields);
                    }
                } else {
                    walk(snapshot, path, value, fields);
                }
            }
            return;
        }
        if (node instanceof Iterable) {
            int index = 0;
            for (Object item : (Iterable<?>) node) {
                walk(snapshot, SqlFieldPath.listItem(parent, item, index++), item, fields);
            }
            return;
        }
        if (node instanceof CharSequence) {
            String path = parent;
            if (SqlFieldPath.matches(fields, path, SqlFieldPath.leafName(path))) {
                snapshot.put(path, node.toString());
            }
        }
    }

    private static boolean isScalar(Object value) {
        return value instanceof Number || value instanceof Boolean || value instanceof Character
                || value instanceof Enum || value instanceof Date;
    }

    /**
     * 仅对形如 JSON 对象/数组的字符串做二次解析，普通文本返回 null。
     */
    private static Object tryParse(String text) {
        if (text == null) {
            return null;
        }
        String trimmed = text.trim();
        if (!trimmed.startsWith("{") && !trimmed.startsWith("[")) {
            return null;
        }
        try {
            return JSONUtil.parse(trimmed);
        } catch (Exception e) {
            return null;
        }
    }
}
