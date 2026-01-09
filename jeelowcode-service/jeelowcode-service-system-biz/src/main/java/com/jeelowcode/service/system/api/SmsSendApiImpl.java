package com.jeelowcode.service.system.api;

import com.jeelowcode.service.system.dto.SmsSendSingleToUserReqDTO;
import com.jeelowcode.service.system.service.ISmsSendService;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;

/**
 * 短信发送 API 接口
 *
 * @author 芋道源码
 */
@Service
@Validated
public class SmsSendApiImpl implements SmsSendApi {

    @Resource
    private ISmsSendService smsSendService;

    @Override
    public Long sendSingleSmsToAdmin(SmsSendSingleToUserReqDTO reqDTO) {
        return smsSendService.sendSingleSmsToAdmin(reqDTO.getMobile(), reqDTO.getUserId(),
                reqDTO.getTemplateCode(), reqDTO.getTemplateParams());
    }

    @Override
    public Long sendSingleSmsToMember(SmsSendSingleToUserReqDTO reqDTO) {
        return smsSendService.sendSingleSmsToMember(reqDTO.getMobile(), reqDTO.getUserId(),
                reqDTO.getTemplateCode(), reqDTO.getTemplateParams());
    }

}
