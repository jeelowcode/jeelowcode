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
package com.jeelowcode.framework.utils.model.global;


import com.jeelowcode.framework.code.JeeLowCodeErrorCode;
import com.jeelowcode.framework.constants.FrameErrorCodeConstants;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.util.Assert;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;


/**
 * 通用返回
 *
 */
public class BaseWebResult<T> implements Serializable {

    /**
     * 错误码
     */
    private Integer code;
    /**
     * 返回数据
     */
    private T data;
    /**
     * 错误提示，用户可阅读
     */
    private String msg;

    /**
     * 将传入的 result 对象，转换成另外一个泛型结果的对象
     *
     * 因为 A 方法返回的 CommonResult 对象，不满足调用其的 B 方法的返回，所以需要进行转换。
     *
     * @param result 传入的 result 对象
     * @param <T>    返回的泛型
     * @return 新的 CommonResult 对象
     */
    public static <T> BaseWebResult<T> error(BaseWebResult<?> result) {
        return error(result.getCode(), result.getMsg());
    }

    public static <T> BaseWebResult<T> error(Integer code, String message) {
        Assert.isTrue(!FrameErrorCodeConstants.SUCCESS.getCode().equals(code), "code 必须是错误的！");
        BaseWebResult<T> result = new BaseWebResult();
        result.code = code;
        result.msg = message;
        return result;
    }


    public static <T> BaseWebResult<T> error(JeeLowCodeErrorCode errorCode) {
        return error(errorCode.getCode(), errorCode.getMsg());
    }

    public static <T> BaseWebResult<T> error(JeeLowCodeErrorCode errorCode,T data) {
        BaseWebResult<T> result = new BaseWebResult();
        result.code = errorCode.getCode();
        result.msg = errorCode.getMsg();
        result.data=data;
        return result;
    }

    public static BaseWebResult successNull() {
        Map<String, Object> dataMap = new HashMap<>();

        BaseWebResult result = new BaseWebResult();
        result.code = FrameErrorCodeConstants.SUCCESS.getCode();
        result.data = dataMap;
        result.msg = "";
        return result;
    }

        public static <T> BaseWebResult<T> success(T data) {
        BaseWebResult<T> result = new BaseWebResult();
        result.code = FrameErrorCodeConstants.SUCCESS.getCode();
        result.data = data;
        result.msg = "";
        return result;
    }

    public static boolean isSuccess(Integer code) {
        return Objects.equals(code, FrameErrorCodeConstants.SUCCESS.getCode());
    }

    @JsonIgnore // 避免 jackson 序列化
    public boolean isSuccess() {
        return isSuccess(code);
    }

    @JsonIgnore // 避免 jackson 序列化
    public boolean isError() {
        return !isSuccess();
    }


    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
