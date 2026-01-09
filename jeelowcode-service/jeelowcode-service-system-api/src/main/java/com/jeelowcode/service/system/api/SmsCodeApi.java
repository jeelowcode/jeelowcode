package com.jeelowcode.service.system.api;

import com.jeelowcode.service.system.dto.SmsCodeSendReqDTO;
import com.jeelowcode.service.system.dto.SmsCodeUseReqDTO;
import com.jeelowcode.service.system.dto.SmsCodeValidateReqDTO;
import com.jeelowcode.tool.framework.common.exception.ServiceException;

import javax.validation.Valid;

/**
 * 短信验证码 API 接口
 *
 * @author 芋道源码
 */
public interface SmsCodeApi {

    /**
     * 创建短信验证码，并进行发送
     *
     * @param reqDTO 发送请求
     */
    void sendSmsCode(@Valid SmsCodeSendReqDTO reqDTO);

    /**
     * 验证短信验证码，并进行使用
     * 如果正确，则将验证码标记成已使用
     * 如果错误，则抛出 {@link ServiceException} 异常
     *
     * @param reqDTO 使用请求
     */
    void useSmsCode(@Valid SmsCodeUseReqDTO reqDTO);

    /**
     * 检查验证码是否有效
     *
     * @param reqDTO 校验请求
     */
    void validateSmsCode(@Valid SmsCodeValidateReqDTO reqDTO);

}
