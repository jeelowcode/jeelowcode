package com.jeelowcode.service.system.api;

import com.jeelowcode.service.system.dto.LoginLogCreateReqDTO;

import javax.validation.Valid;

/**
 * 登录日志的 API 接口
 *
 * @author 芋道源码
 */
public interface LoginLogApi {

    /**
     * 创建登录日志
     *
     * @param reqDTO 日志信息
     */
    void createLoginLog(@Valid LoginLogCreateReqDTO reqDTO);

}
