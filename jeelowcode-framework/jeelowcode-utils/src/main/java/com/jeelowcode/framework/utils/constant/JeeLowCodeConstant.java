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
package com.jeelowcode.framework.utils.constant;

/**
 * 系统常量
 */
public interface JeeLowCodeConstant {
    
    String JEELOWCODE_SUMMARY_TABLE="#{jeelowcode_summary_table}";

    //强制同步数据库
    String SYNC_DB_FORCE = "force";

    //历史数据类型
    String HISTORY_DESFORM="desform";
    String HISTORY_JS="js";
    String HISTORY_SQL="sql";
    String HISTORY_JAVA="java";

    //Postgresql数据库 模式
    String POSTGRESQL_SCHEMA=".public";

    //默认别名
    String TABLE_ALIAS="tbl";

    //Excel导入序号
    String EXCEL_IMPORT_STEP="jeelowcode_excel_import_step";
    String EXCEL_IMPORT_ID="jeelowcode_excel_import_id";

    String BACK_SLASH = "\\";

    String DOT = ".";

    String SPACE = " ";

    String EMPTY = "";

    // \src\main\java\
    String PROGRAM_DIR = "\\src\\main\\java\\";

    // \target\classes
    String CLASSES_DIR = "\\target\\classes";

    // .java
    String JAVA_SUFFIX = ".java";

    String USER_DIR = System.getProperty("user.dir");

    //不分页
    Integer NOT_PAGE=-999;

    // 本地开发环境
    String APPLICATION_LOCAL = "local";
}
