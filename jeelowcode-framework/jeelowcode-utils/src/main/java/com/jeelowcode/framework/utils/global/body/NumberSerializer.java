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
package com.jeelowcode.framework.utils.global.body;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.annotation.JacksonStdImpl;

import java.io.IOException;

/**
 * Long 序列化规则
 *
 * 会将超长 long 值转换为 string，解决前端 JavaScript 最大安全整数是 2^53-1 的问题
 *
 * @author 星语
 */
@JacksonStdImpl
public class NumberSerializer extends com.fasterxml.jackson.databind.ser.std.NumberSerializer {

    private static final long MAX_SAFE_INTEGER = 9007199254740991L;
    private static final long MIN_SAFE_INTEGER = -9007199254740991L;

    public static final NumberSerializer INSTANCE = new NumberSerializer(Number.class);

    public NumberSerializer(Class<? extends Number> rawType) {
        super(rawType);
    }

    @Override
    public void serialize(Number value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        // 超出范围 序列化位字符串
        if (value.longValue() > MIN_SAFE_INTEGER && value.longValue() < MAX_SAFE_INTEGER) {
            super.serialize(value, gen, serializers);
        } else {
            gen.writeString(value.toString());
        }
    }
}
