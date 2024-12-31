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
package com.jeelowcode.framework.plus.core.conditions;


import com.jeelowcode.framework.plus.core.toolkit.StringPool;

import java.io.Serializable;

/**
 * 共享查询字段
 *
 * @author miemie
 * @since 2018-11-20
 */
public class SharedString implements Serializable {
    private static final long serialVersionUID = -1536422416594422874L;

    /**
     * 共享的 string 值
     */
    private String stringValue;

    /**
     * SharedString 里是 ""
     */
    public static SharedString emptyString() {
        return new SharedString(StringPool.EMPTY);
    }

    /**
     * 置 empty
     *
     * @since 3.3.1
     */
    public void toEmpty() {
        stringValue = StringPool.EMPTY;
    }

    /**
     * 置 null
     *
     * @since 3.3.1
     */
    public void toNull() {
        stringValue = null;
    }

    public SharedString() {

    }

    public SharedString(String stringValue) {
        this.stringValue = stringValue;
    }

    public String getStringValue() {
        return stringValue;
    }

    public void setStringValue(String stringValue) {
        this.stringValue = stringValue;
    }
}
