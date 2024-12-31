/*
 * Copyright (c) 2011-2023, baomidou (jobob@qq.com).
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *//*
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
package com.jeelowcode.framework.plus.core.toolkit.sql;

import java.util.Objects;
import java.util.regex.Pattern;

/**
 * SQL 注入验证工具类
 *
 * @author hubin
 * @since 2021-08-15
 */
public class SqlInjectionUtils {
    /**
     * SQL语法检查正则：符合两个关键字（有先后顺序）才算匹配
     */
    private static final Pattern SQL_SYNTAX_PATTERN = Pattern.compile("(insert|delete|update|select|create|drop|truncate|grant|alter|deny|revoke|call|execute|exec|declare|show|rename|set)" +
        "\\s+.*(into|from|set|where|table|database|view|index|on|cursor|procedure|trigger|for|password|union|and|or)|(select\\s*\\*\\s*from\\s+)|(and|or)\\s+.*(like|=|>|<|in|between|is|not|exists)", Pattern.CASE_INSENSITIVE);
    /**
     * 使用'、;或注释截断SQL检查正则
     */
    private static final Pattern SQL_COMMENT_PATTERN = Pattern.compile("'.*(or|union|--|#|/\\*|;)", Pattern.CASE_INSENSITIVE);


    /**
     * 检查参数是否存在 SQL 注入
     *
     * @param value 检查参数
     * @return true 非法 false 合法
     */
    public static boolean check(String value) {
        Objects.requireNonNull(value);
        // 处理是否包含SQL注释字符 || 检查是否包含SQL注入敏感字符
        return SQL_COMMENT_PATTERN.matcher(value).find() || SQL_SYNTAX_PATTERN.matcher(value).find();
    }


}
