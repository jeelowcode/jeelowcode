
/*
Apache License
Version 2.0, January 2004
http://www.apache.org/licenses/
本软件受适用的国家软件著作权法（包括国际条约）和开源协议 双重保护许可。

开源协议中文释意如下：
1.JeeLowCode开源版本无任何限制，在遵循本开源协议（Apache2.0）条款下，允许商用使用，不会造成侵权行为。
2.允许基于本平台软件开展业务系统开发。
3.在任何情况下，您不得使用本软件开发可能被认为与本软件竞争的软件。

最终解释权归：http://www.jeelowcode.com
*/
package com.jeelowcode.framework.tenant.utils;


import com.jeelowcode.framework.tenant.parse.JeeLowCodeTenantParse;
import com.jeelowcode.framework.tenant.parse.JeeLowCodeTenantParse;
import com.jeelowcode.framework.utils.utils.FuncBase;
import com.alibaba.ttl.TransmittableThreadLocal;
import net.sf.jsqlparser.JSQLParserException;
import net.sf.jsqlparser.parser.CCJSqlParserUtil;
import net.sf.jsqlparser.statement.Statement;
import net.sf.jsqlparser.statement.delete.Delete;
import net.sf.jsqlparser.statement.insert.Insert;
import net.sf.jsqlparser.statement.select.Select;
import net.sf.jsqlparser.statement.update.Update;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 租户工具类
 */
public class JeeLowCodeTenantUtils {

    //租户列
    public static final String TENANT_ID_COLUMN = "tenant_id";

    //获取当前线程租户编号
    private static final ThreadLocal<Long> TENANT_ID = new TransmittableThreadLocal<>();
    //当前线程是否忽略多租户 true=忽略  false=不忽略
    private static final ThreadLocal<Boolean> IGNORE = new TransmittableThreadLocal<>();
    //不进行多租户处理的表列表
    private static Set<String> IGNORE_TABLES = new HashSet<>();
    //不进行多租户的url
    private static Set<String> IGNORE_URLS = Collections.emptySet();
    //不进行多租户处理的表列表-以*结尾
    private static Set<String> IGNORE_LIKE_TABLES = new HashSet<>();

    private static String TMP_PARAM="jeelowcode_param_mjkj_tmp_";


    private static String fomatSql(String sql) {
        try{
            sql=sql.replaceAll(",jdbcType=","_jeelowcode_jdbcType_");
            String patternString = "#\\{([^}]+)\\}"; // 正则表达式字符串
            Pattern pattern = Pattern.compile(patternString);
            Matcher matcher = pattern.matcher(sql);

            StringBuffer sb = new StringBuffer();
            while (matcher.find()) {
                String variableName = matcher.group(1); // 获取#{}内的变量名
                matcher.appendReplacement(sb, TMP_PARAM + variableName);
            }
            matcher.appendTail(sb);

            sql = sb.toString();
        }catch (Exception e){
            e.printStackTrace();
        }
        return sql;
    }

    private static String resetSql(String sql){
        try{
            sql=sql.replaceAll("_jeelowcode_jdbcType_",",jdbcType=");
            String patternString = TMP_PARAM+"([a-zA-Z0-9_.]+)(?:,(\\s*jdbcType\\s*=\\s*[a-zA-Z]+))?"; // 正则表达式字符串
            Pattern pattern = Pattern.compile(patternString);
            Matcher matcher = pattern.matcher(sql);

            StringBuffer sb = new StringBuffer();
            while (matcher.find()) {
                String variableName = matcher.group(1); // 获取括号内的变量名
                String jdbcType = matcher.group(2); // 获取括号内的变量名
                if(FuncBase.isEmpty(jdbcType)){
                    matcher.appendReplacement(sb, "#{" + variableName + "}");
                }else{
                    matcher.appendReplacement(sb, "#{" + variableName+","+jdbcType + "}");
                }
            }
            matcher.appendTail(sb);

            return sb.toString();
        }catch (Exception e){
            e.printStackTrace();
        }
        return sql;
    }

    /**
     * 解析多租户sql
     *
     * @param sql
     * @return
     */
    public static String parseTenantSql(String sql) throws JSQLParserException {
        Statement statement = CCJSqlParserUtil.parse(fomatSql(sql));
        if (statement instanceof Select) {//查询
            Select select = (Select) statement;
            JeeLowCodeTenantParse.processSelect(select);
        } else if (statement instanceof Update) {//修改
            Update update = (Update) statement;
            JeeLowCodeTenantParse.processUpdate(update);
        } else if (statement instanceof Delete) {//删除
            Delete delete = (Delete) statement;
            JeeLowCodeTenantParse.processDelete(delete);
        } else if (statement instanceof Insert) {//新增
            Insert insert = (Insert) statement;
            JeeLowCodeTenantParse.processInsert(insert);
        }
        String new_sql = statement.toString();
        return resetSql(new_sql);
    }

    //获得租户编号
    public static Long getTenantId() {
        return TENANT_ID.get();
    }

    //设置租户
    public static void setTenantId(Long tenantId) {
        TENANT_ID.set(tenantId);
    }

    //设置当前线程是否忽略多租户
    public static void setIgnore(Boolean ignore) {
        IGNORE.set(ignore);
    }

    //当前是否忽略租户
    public static boolean isIgnore() {
        return Boolean.TRUE.equals(IGNORE.get());
    }

    //忽略列表
    public static Set<String> getIgnoreTables() {
        return IGNORE_TABLES;
    }

    //忽略列表
    public static Set<String> getIgnoreLikeTables() {
        return IGNORE_LIKE_TABLES;
    }


    public static Set<String> getIGNORE_URLS() {
        return IGNORE_URLS;
    }

    public void setIGNORE_URLS(Set<String> IGNORE_URLS) {
        this.IGNORE_URLS = IGNORE_URLS;
    }

    /**
     * 初始化-排除表
     *
     * @param tables
     */
    public static void initIgnoreTables(Set<String> tables) {
        if (IGNORE_TABLES != null && IGNORE_TABLES.size() > 0) {//只需要初始化一次
            return;
        }
        for (String table : tables) {
            if (table.contains("*")) {
                IGNORE_LIKE_TABLES.add(table);
            }else {
                IGNORE_TABLES.add(table);
            }
        }
    }


    /**
     * 初始化-排除url
     *
     * @param urls
     */
    public static void initIgnoreUrl(Set<String> urls) {
        if (IGNORE_URLS != null && IGNORE_URLS.size() > 0) {//只需要初始化一次
            return;
        }
        IGNORE_URLS=urls;
    }

    //清空当前线程
    public static void clearIgnore() {
        IGNORE.remove();
    }


    //清空当前线程
    public static void clear() {
        TENANT_ID.remove();
        IGNORE.remove();
    }


}
