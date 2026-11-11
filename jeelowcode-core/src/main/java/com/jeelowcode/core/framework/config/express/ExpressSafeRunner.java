
package com.jeelowcode.core.framework.config.express;

import com.jeelowcode.framework.exception.JeeLowCodeException;
import com.jeelowcode.framework.utils.utils.FuncBase;
import com.ql.util.express.DefaultContext;
import com.ql.util.express.ExpressRunner;
import com.ql.util.express.Operator;
import com.ql.util.express.config.QLExpressRunStrategy;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Date;
import java.util.Map;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 表达式安全执行器（QLExpress 沙箱模式）
 *
 * <p>背景：表达式内容由表单设计者通过 dbform 保存接口写入并持久化（format_config），属于不可信输入，
 * 若直接交给 QLExpress 默认配置执行，可 import/new 任意类、调用任意静态方法，构成存储型代码执行。
 *
 * <p>这里统一开启 QLExpress 沙箱模式：禁止 import、禁止显式引用 Java 类、禁止取字段/调用 Java 方法，
 * 仅允许：四则运算、比较、逻辑、三元、字符串拼接、Map 的 .key 取值，以及下方注册的受控安全函数。
 * 同时限制表达式长度、数组长度、执行超时，异常信息对外脱敏。
 */
@Slf4j
public final class ExpressSafeRunner {

    //表达式最大长度
    private static final int MAX_EXPRESS_LENGTH = 1000;

    //单次执行超时时间(毫秒)
    private static final long TIMEOUT_MILLIS = 2000L;

    //脚本可申请的最大数组长度(防止大数组导致 OOM)
    private static final int MAX_ARR_LENGTH = 1000;

    //#{变量} 占位符
    private static final Pattern VAR_PATTERN = Pattern.compile("\\#\\{([^}]+)\\}");

    //变量名白名单：字母、数字、下划线、中文
    private static final Pattern VAR_NAME_PATTERN = Pattern.compile("^[A-Za-z0-9_\\u4e00-\\u9fa5]+$");

    //全局唯一的执行器（复用指令集缓存，避免每次执行重复编译）
    private static final ExpressRunner RUNNER;

    static {
        //沙箱模式：禁止 import / 显式引用 Java 类 / 调用 Java 方法（JVM 级全局开关，本项目仅此处使用 QLExpress）
        QLExpressRunStrategy.setSandBoxMode(true);
        //黑名单兜底：即使沙箱被绕过，默认的高危方法（Runtime.exec、ProcessBuilder.start 等）仍然被拦截
        QLExpressRunStrategy.setForbidInvokeSecurityRiskMethods(true);
        //限制脚本可申请的最大数组长度
        QLExpressRunStrategy.setMaxArrLength(MAX_ARR_LENGTH);

        RUNNER = new ExpressRunner();
        registerSafeFunction(RUNNER);
    }

    private ExpressSafeRunner() {
    }

    /**
     * 执行表达式
     *
     * @param express 表达式（可包含 #{字段名} 占位符）
     * @param dataMap 上下文数据
     * @return 执行结果字符串
     */
    public static String execute(String express, Map<String, Object> dataMap) {
        if (FuncBase.isEmpty(express)) {
            return "";
        }
        if (express.length() > MAX_EXPRESS_LENGTH) {
            throw new JeeLowCodeException("表达式长度超过限制，最大允许" + MAX_EXPRESS_LENGTH + "个字符");
        }

        String expressTmp = replaceVar(express);

        DefaultContext<String, Object> context = new DefaultContext<String, Object>();
        if (dataMap != null) {
            dataMap.forEach((key, val) -> context.put(key, val));
        }

        try {
            Object result = RUNNER.execute(expressTmp, context, null, true, false, TIMEOUT_MILLIS);
            return FuncBase.toStr(result);
        } catch (Exception e) {
            //异常信息不外泄，详细信息只落日志
            log.warn("表达式执行失败，express={}", expressTmp, e);
            throw new JeeLowCodeException("表达式执行失败，请检查表达式配置是否正确");
        }
    }

    /**
     * 将 #{变量} 替换为可直接参与运算的变量名，并校验变量名合法性
     */
    private static String replaceVar(String express) {
        Matcher matcher = VAR_PATTERN.matcher(express);
        StringBuffer sb = new StringBuffer();
        while (matcher.find()) {
            String varName = matcher.group(1).trim();
            if (!VAR_NAME_PATTERN.matcher(varName).matches()) {
                throw new JeeLowCodeException("表达式变量名不合法，只允许字母、数字、下划线和中文");
            }
            matcher.appendReplacement(sb, varName);
        }
        matcher.appendTail(sb);
        return sb.toString();
    }

    //-------------------------------------------------------------------------
    // 受控安全函数：沙箱模式下脚本无法调用任何 Java 方法，这里按需开放有限能力
    //-------------------------------------------------------------------------
    private static void registerSafeFunction(ExpressRunner runner) {
        //数学
        addFunction(runner, "abs", list -> toDecimal(list, 0).abs());
        addFunction(runner, "ceil", list -> toDecimal(list, 0).setScale(0, RoundingMode.CEILING));
        addFunction(runner, "floor", list -> toDecimal(list, 0).setScale(0, RoundingMode.FLOOR));
        addFunction(runner, "sqrt", list -> BigDecimal.valueOf(Math.sqrt(toDecimal(list, 0).doubleValue())));
        addFunction(runner, "pow", list -> toDecimal(list, 0).pow(toDecimal(list, 1).intValue()));
        addFunction(runner, "number", list -> toDecimal(list, 0));
        addFunction(runner, "decimal", list -> toDecimal(list, 0).setScale(toDecimal(list, 1).intValue(), RoundingMode.HALF_UP));

        //字符串
        addFunction(runner, "len", list -> toStr(list, 0).length());
        addFunction(runner, "upper", list -> toStr(list, 0).toUpperCase());
        addFunction(runner, "lower", list -> toStr(list, 0).toLowerCase());
        addFunction(runner, "trim", list -> toStr(list, 0).trim());
        addFunction(runner, "concat", list -> toStr(list, 0) + toStr(list, 1));
        addFunction(runner, "substring", list -> {
            String str = toStr(list, 0);
            int begin = Math.min(Math.max(toDecimal(list, 1).intValue(), 0), str.length());
            if (list.length > 2) {
                int end = Math.min(Math.max(toDecimal(list, 2).intValue(), 0), str.length());
                return str.substring(Math.min(begin, end), end);
            }
            return str.substring(begin);
        });
        addFunction(runner, "indexOf", list -> toStr(list, 0).indexOf(toStr(list, 1)));
        addFunction(runner, "contains", list -> toStr(list, 0).contains(toStr(list, 1)));
        addFunction(runner, "startsWith", list -> toStr(list, 0).startsWith(toStr(list, 1)));
        addFunction(runner, "endsWith", list -> toStr(list, 0).endsWith(toStr(list, 1)));
        addFunction(runner, "replace", list -> toStr(list, 0).replace(toStr(list, 1), toStr(list, 2)));
        addFunction(runner, "split", list -> {
            String str = toStr(list, 0);
            String sep = toStr(list, 1);
            if (FuncBase.isEmpty(sep)) {
                return Arrays.asList(str);
            }
            return Arrays.asList(str.split(Pattern.quote(sep)));
        });

        //空值处理
        addFunction(runner, "isEmpty", list -> {
            Object val = arg(list, 0);
            return val == null || "".equals(FuncBase.toStr(val).trim());
        });
        addFunction(runner, "nvl", list -> {
            Object val = arg(list, 0);
            if (val == null || "".equals(FuncBase.toStr(val).trim())) {
                return arg(list, 1);
            }
            return val;
        });

        //日期
        addFunction(runner, "now", list -> LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        addFunction(runner, "today", list -> LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        addFunction(runner, "dateFormat", list -> {
            Object val = arg(list, 0);
            if (val == null) {
                return "";
            }
            String pattern = list.length > 1 ? toStr(list, 1) : "yyyy-MM-dd HH:mm:ss";
            if (val instanceof Date) {
                return new SimpleDateFormat(pattern).format((Date) val);
            }
            if (val instanceof LocalDateTime) {
                return ((LocalDateTime) val).format(DateTimeFormatter.ofPattern(pattern));
            }
            if (val instanceof LocalDate) {
                return ((LocalDate) val).format(DateTimeFormatter.ofPattern(pattern));
            }
            return FuncBase.toStr(val);
        });
    }

    private static void addFunction(ExpressRunner runner, String name, Function<Object[], Object> function) {
        try {
            runner.addFunction(name, new Operator() {
                @Override
                public Object executeInner(Object[] list) throws Exception {
                    return function.apply(list);
                }
            });
        } catch (Exception e) {
            log.warn("注册表达式安全函数失败，name={}", name, e);
        }
    }

    private static Object arg(Object[] list, int index) {
        if (list == null || list.length <= index) {
            return null;
        }
        return list[index];
    }

    private static String toStr(Object[] list, int index) {
        return FuncBase.toStr(arg(list, index));
    }

    private static BigDecimal toDecimal(Object[] list, int index) {
        Object val = arg(list, index);
        if (val instanceof BigDecimal) {
            return (BigDecimal) val;
        }
        String str = FuncBase.toStr(val).trim();
        if (FuncBase.isEmpty(str)) {
            return BigDecimal.ZERO;
        }
        return new BigDecimal(str);
    }
}
