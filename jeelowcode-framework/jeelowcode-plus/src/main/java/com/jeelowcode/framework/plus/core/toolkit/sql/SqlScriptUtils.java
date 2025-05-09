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


import com.jeelowcode.framework.plus.core.toolkit.Constants;
import com.jeelowcode.framework.plus.core.toolkit.StringPool;
import com.jeelowcode.framework.plus.core.toolkit.StringUtils;

/**
 * <p>
 * sql 脚本工具类
 * </p>
 *
 * @author miemie
 * @since 2018-08-15
 */
public abstract class SqlScriptUtils implements Constants {


    /**
     * <p>
     * 安全入参:  #{入参,mapping}
     * </p>
     *
     * @param param   入参
     * @param mapping 映射
     * @return 脚本
     */
    public static String safeParam(final String param, final String mapping) {
        String target = StringPool.HASH_LEFT_BRACE + param;
        if (StringUtils.isBlank(mapping)) {
            return target + StringPool.RIGHT_BRACE;
        }
        return target + StringPool.COMMA + mapping + StringPool.RIGHT_BRACE;
    }


}
