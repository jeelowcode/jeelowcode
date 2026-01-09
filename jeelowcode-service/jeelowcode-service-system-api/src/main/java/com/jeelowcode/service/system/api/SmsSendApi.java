package com.jeelowcode.service.system.api;

import com.jeelowcode.service.system.dto.SmsSendSingleToUserReqDTO;

import javax.validation.Valid;

/**
 * 短信发送 API 接口
 *
 * @author 芋道源码
 */
public interface SmsSendApi {

    /**
     * 发送单条短信给 Admin 用户
     *
     * 在 mobile 为空时，使用 userId 加载对应 Admin 的手机号
     *
     * @param reqDTO 发送请求
     * @return 发送日志编号
     */
    Long sendSingleSmsToAdmin(@Valid SmsSendSingleToUserReqDTO reqDTO);

    /**
     * 发送单条短信给 Member 用户
     *
     * 在 mobile 为空时，使用 userId 加载对应 Member 的手机号
     *
     * @param reqDTO 发送请求
     * @return 发送日志编号
     */
    Long sendSingleSmsToMember(@Valid SmsSendSingleToUserReqDTO reqDTO);

}
