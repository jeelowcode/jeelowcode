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

import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.jeelowcode.framework.utils.annotation.ApiEncryptAes;
import com.jeelowcode.framework.utils.tool.AesUtil;
import org.springframework.core.MethodParameter;
import org.springframework.core.annotation.Order;
import org.springframework.http.MediaType;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import java.util.Iterator;
import java.util.Map;

/**
 * 返回结果解密
 */
@Order
@ControllerAdvice
public class ApiDecryptResponseBodyAdvice implements ResponseBodyAdvice {

    @Override
    public boolean supports(MethodParameter methodParameter, @NonNull Class converterType) {
        return methodParameter.hasMethodAnnotation(ApiEncryptAes.class) ;

    }

    @Nullable
    @Override
    public Object beforeBodyWrite(Object body, @NonNull MethodParameter parameter, @NonNull MediaType selectedContentType,
                                  @NonNull Class selectedConverterType, @NonNull ServerHttpRequest request, @NonNull ServerHttpResponse response) {
        if (body == null) {
            return null;
        }
        ApiEncryptAes apiEncryptAes = parameter.getMethodAnnotation(ApiEncryptAes.class);
        if (apiEncryptAes == null) {
            return body;
        }
        String secretKey = AesUtil.secretKey;
        JSONObject jsonObject = JSONUtil.parseObj(body);
        jsonObject =long2String(jsonObject);

       // String bodyStr = JSONUtil.toJsonStr(body);

        response.getHeaders().setContentType(MediaType.TEXT_PLAIN);
        String result = AesUtil.encryptToBase64(jsonObject.toString(), secretKey);
        return result;

    }

    /**
     * 将JSON的long,转成String,以防止精度丢失
     */
    public JSONObject long2String(JSONObject jsonObject){

        Iterator iter = jsonObject.entrySet().iterator();
        while (iter.hasNext()) {
            Map.Entry entry = (Map.Entry) iter.next();

            if(entry.getValue() instanceof JSONObject){
                long2String(jsonObject.getJSONObject(entry.getKey().toString()));
            }
            if(entry.getValue() instanceof JSONArray){
                for (int i = 0; i < ((JSONArray) entry.getValue()).size(); i++) {
                    long2String(((JSONArray) entry.getValue()).getJSONObject(i));
                }
            }
            if(entry.getValue() instanceof Long){
                jsonObject.set(entry.getKey().toString(),String.valueOf(entry.getValue()));
            }

        }
        return jsonObject;
    }

}
