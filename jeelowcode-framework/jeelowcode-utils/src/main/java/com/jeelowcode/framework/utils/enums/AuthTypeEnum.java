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
 * 表单开发-认证类型
 */
public enum AuthTypeEnum {

    authOpen("authOpen", "不登录可查询"),
    authFalse("authFalse", "需要登录"),
    authTrue("authTrue", "需要登录和鉴权")
    ;

    private final String type;
    private final String name;

    public String getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    AuthTypeEnum(String type, String name) {
        this.type = type;
        this.name = name;
    }

    public static AuthTypeEnum getByType(String type) {
        // 使用流来查找匹配的
        Optional<AuthTypeEnum> matchingEnum = Arrays.stream(AuthTypeEnum.values())
                .filter(dbFormTypeEnum -> FuncBase.equals(dbFormTypeEnum.getType(), type))
                .findFirst(); // findFirst()会返回第一个匹配的元素，或者如果找不到则返回一个空的Optional
        // 检查是否找到了匹配的枚举项
        if (matchingEnum.isPresent()) {
            return matchingEnum.get();
        }
        return null;

    }
}
