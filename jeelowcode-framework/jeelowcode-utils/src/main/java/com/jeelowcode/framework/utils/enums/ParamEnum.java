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
package com.jeelowcode.framework.utils.enums;


/**
 * 请求参数-特定参数名
 */
public enum ParamEnum {
    //分页相关
    PAGE_NO("pageNo", "当前页面"),
    PAGE_SIZE("pageSize", "页面条数"),
    COLUMN("column", "字典"),
    ORDER("order", "排序类  desc  asc"),
    DICT_TABLE_FIELD("jeeLowCode_dictTableField", "字典表自定义列"),
    DICT_LABEL("jeeLowCode_dictLabel", "字典表回显列"),
    TREE_PARENT("jeeLowCode_treeParent", "获取指定列的所有上级"),
    REQUEST_PARAM_BODY("requestParamBody", "body参数"),
    MORE_SELECT_FIELD("more_select_field", "body参数"),
    REQUEST_REPORT_CODES("jeeLowCode_report_codes", "报表code 多个通过,隔开"),

    ALL_QUERY_FIELD("jeeLowCode_all_query_field", "所有字段都可以查询"),
    ;

    /**
     * code类型
     */
    private final String code;
    /**
     * 数据名称
     */
    private final String name;


    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    ParamEnum(String code, String name) {
        this.code = code;
        this.name = name;
    }

}
