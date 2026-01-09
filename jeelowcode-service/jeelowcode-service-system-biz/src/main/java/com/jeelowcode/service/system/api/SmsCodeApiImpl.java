package com.jeelowcode.service.system.api;

import com.jeelowcode.service.system.dto.SmsCodeValidateReqDTO;
import com.jeelowcode.service.system.dto.SmsCodeSendReqDTO;
import com.jeelowcode.service.system.dto.SmsCodeUseReqDTO;
import com.jeelowcode.service.system.service.ISmsCodeService;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;

/**
 * 短信验证码 API 实现类
 *
 * @author 芋道源码
 */
@Service
@Validated
public class SmsCodeApiImpl implements SmsCodeApi {

    @Resource
    private ISmsCodeService smsCodeService;

    @Override
    public void sendSmsCode(SmsCodeSendReqDTO reqDTO) {
        smsCodeService.sendSmsCode(reqDTO);
    }

    @Override
    public void useSmsCode(SmsCodeUseReqDTO reqDTO) {
        smsCodeService.useSmsCode(reqDTO);
    }

    @Override
    public void validateSmsCode(SmsCodeValidateReqDTO reqDTO) {
        smsCodeService.validateSmsCode(reqDTO);
    }

}
