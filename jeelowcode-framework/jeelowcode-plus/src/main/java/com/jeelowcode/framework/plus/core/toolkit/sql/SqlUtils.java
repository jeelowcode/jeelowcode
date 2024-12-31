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



import com.jeelowcode.framework.plus.core.enums.SqlLike;
import com.jeelowcode.framework.plus.core.toolkit.Constants;
import com.jeelowcode.framework.plus.core.toolkit.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * SqlUtils工具类
 * !!! 本工具不适用于本框架外的类使用 !!!
 *
 * @author Caratacus
 * @since 2016-11-13
 */
public abstract class SqlUtils implements Constants {

    private static final Pattern pattern = Pattern.compile("\\{@((\\w+?)|(\\w+?:\\w+?)|(\\w+?:\\w+?:\\w+?))}");

    /**
     * 用%连接like
     *
     * @param str 原字符串
     * @return like 的值
     */
    public static String concatLike(Object str, SqlLike type) {
        switch (type) {
            case LEFT:
                return PERCENT + str;
            case RIGHT:
                return str + PERCENT;
            default:
                return PERCENT + str + PERCENT;
        }
    }

    public static List<String> findPlaceholder(String sql) {
        Matcher matcher = pattern.matcher(sql);
        List<String> list = new ArrayList<>();
        while (matcher.find()) {
            list.add(matcher.group());
        }
        return list;
    }


    public static String getNewSelectBody(String selectBody, String alisa, String asAlisa, String escapeSymbol) {
        String[] split = selectBody.split(COMMA);
        StringBuilder sb = new StringBuilder();
        boolean asA = asAlisa != null;
        for (String body : split) {
            final String sa = alisa.concat(DOT);
            if (asA) {
                int as = body.indexOf(AS);
                if (as < 0) {
                    sb.append(sa).append(body).append(AS).append(escapeColumn(asAlisa.concat(DOT).concat(body), escapeSymbol));
                } else {
                    String column = body.substring(0, as);
                    String property = body.substring(as + 4);
                    property = StringUtils.getTargetColumn(property);
                    sb.append(sa).append(column).append(AS).append(escapeColumn(asAlisa.concat(DOT).concat(property), escapeSymbol));
                }
            } else {
                sb.append(sa).append(body);
            }
            sb.append(COMMA);
        }
        return sb.deleteCharAt(sb.length() - 1).toString();
    }

    private static String escapeColumn(String column, String escapeSymbol) {
        return escapeSymbol.concat(column).concat(escapeSymbol);
    }
}
