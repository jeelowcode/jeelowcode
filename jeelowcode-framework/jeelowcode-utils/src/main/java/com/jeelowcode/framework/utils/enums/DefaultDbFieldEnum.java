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
 * 数据库默认字段
 */
public enum DefaultDbFieldEnum {

    ID("id", "id"),
    P_ID("pid", "pid"),
    CREATE_USER("create_user", "创建人"),
    CREATE_TIME("create_time", "创建时间"),
    CREATE_DEPT("create_dept", "创建部门"),
    IS_DELETED("is_deleted", "是否删除"),
    TENANT_ID("tenant_id", "租户id"),
    UPDATE_USER("update_user", "更新人"),
    UPDATE_TIME("update_time", "更新时间"),
    ;


    private final String fieldCode;
    private final String fieldName;


    public String getFieldCode() {
        return fieldCode;
    }

    public String getFieldName() {
        return fieldName;
    }

    DefaultDbFieldEnum(String fieldCode, String fieldName) {
        this.fieldCode = fieldCode;
        this.fieldName = fieldName;
    }
}
