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


import com.jeelowcode.framework.utils.utils.FuncBase;

import java.util.Arrays;
import java.util.Optional;

/**
 * sql默认参数
 */
public enum SqlParamEnum {


    JEELOWCODE_ID("jeelowcode_id", "id"),
    JEELOWCODE_DATE_TIME("jeelowcode_date_time", "日期时间"),
    JEELOWCODE_DATE("jeelowcode_date", "日期"),
    JEELOWCODE_TIME("jeelowcode_time", "时间"),
    JEELOWCODE_IS_DELETED("jeelowcode_is_deleted", "是否删除"),
    JEELOWCODE_TENANT_ID("jeelowcode_tenant_id", "当前登录人租户"),
    JEELOWCODE_USER_ID("jeelowcode_user_id", "当前登录人id"),
    JEELOWCODE_USER_NAME("jeelowcode_user_name", "当前登录人账号"),
    JEELOWCODE_USER_NICKNAME("jeelowcode_user_nickname", "当前登录人名称"),
    JEELOWCODE_USER_DEPT("jeelowcode_user_dept", "当前登录人部门"),
    JEELOWCODE_USER_ALL_DEPT("jeelowcode_user_all_dept", "当前登录人所有部门（包括本部门）"),
    JEELOWCODE_MAIN_ID("jeelowcode_main_id", "主数据id"),
    JEELOWCODE_AUTO_WHERE("jeelowcode_auto_where", "自定义随机where");

    /**
     */
    private final String code;
    /**
     */
    private final String title;


    public String getCode() {
        return code;
    }

    public String getTitle() {
        return title;
    }

    SqlParamEnum(String code, String title) {
        this.code = code;
        this.title = title;
    }
    public static SqlParamEnum getByCode(String code) {
        Optional<SqlParamEnum> matchingEnum = Arrays.stream(SqlParamEnum.values())
                .filter(sqlParamEnum -> FuncBase.equals(sqlParamEnum.getCode(), code))
                .findFirst(); // findFirst()会返回第一个匹配的元素，或者如果找不到则返回一个空的Optional
        // 检查是否找到了匹配的枚举项
        if (matchingEnum.isPresent()) {
            return matchingEnum.get();
        }
        return null;

    }
}
