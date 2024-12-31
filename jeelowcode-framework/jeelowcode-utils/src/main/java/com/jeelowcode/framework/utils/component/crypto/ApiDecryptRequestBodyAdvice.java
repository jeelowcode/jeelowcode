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
package com.jeelowcode.framework.utils.component.crypto;

import com.jeelowcode.framework.utils.annotation.ApiDecryptAes;
import com.jeelowcode.framework.utils.tool.AesUtil;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.lang.NonNull;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.RequestBodyAdvice;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.nio.charset.Charset;

/**
 * 参数加密
 */
@ControllerAdvice
public class ApiDecryptRequestBodyAdvice  implements RequestBodyAdvice {
    @Override
    public boolean supports(MethodParameter methodParameter, @NonNull Type targetType, @NonNull Class<? extends HttpMessageConverter<?>> converterType) {
        return methodParameter.hasMethodAnnotation(ApiDecryptAes.class);
    }

    @Override
    public Object handleEmptyBody(Object body, @NonNull HttpInputMessage inputMessage, @NonNull MethodParameter parameter,
                                  @NonNull Type targetType, @NonNull Class<? extends HttpMessageConverter<?>> converterType) {
        return body;
    }

    @NonNull
    @Override
    public HttpInputMessage beforeBodyRead(HttpInputMessage inputMessage, @NonNull MethodParameter parameter,
                                           @NonNull Type targetType, @NonNull Class<? extends HttpMessageConverter<?>> converterType) throws IOException {

        ApiDecryptAes apiDecryptAes = parameter.getMethodAnnotation(ApiDecryptAes.class);
        if (apiDecryptAes == null) {
            return inputMessage;
        }
        //key
        String aesKey = AesUtil.secretKey;

        // 判断 body 是否为空
        InputStream messageBody = inputMessage.getBody();
        if (messageBody.available() <= 0) {
            return inputMessage;
        }
        //加密字符
        String bodyStr = StreamUtils.copyToString(messageBody, Charset.defaultCharset());
        bodyStr=bodyStr.replaceAll("\"","");

        //解密字符
        byte[] decrypt = AesUtil.decryptFormBase64(bodyStr, aesKey);

        InputStream inputStream = new ByteArrayInputStream(decrypt);

        return new HttpInputMessage() {
            @Override
            public InputStream getBody() throws IOException {
                return inputStream;  // 再将输入流返回
            }
            @Override
            public HttpHeaders getHeaders() {
                return inputMessage.getHeaders();
            }
        };

    }




    @NonNull
    @Override
    public Object afterBodyRead(@NonNull Object body, @NonNull HttpInputMessage inputMessage, @NonNull MethodParameter parameter, @NonNull Type targetType, @NonNull Class<? extends HttpMessageConverter<?>> converterType) {
        return body;
    }

}
