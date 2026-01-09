package com.jeelowcode.service.infra.api.impl;

import com.jeelowcode.service.infra.api.ApiErrorLogApi;
import com.jeelowcode.service.infra.dto.ApiErrorLogCreateReqDTO;
import com.jeelowcode.service.infra.service.IApiErrorLogService;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;

/**
 * API 访问日志的 API 接口
 *
 * @author 芋道源码
 */
@Service
@Validated
public class ApiErrorLogApiImpl implements ApiErrorLogApi {

    @Resource
    private IApiErrorLogService apiErrorLogService;

    @Override
    public void createApiErrorLog(ApiErrorLogCreateReqDTO createDTO) {
        apiErrorLogService.createApiErrorLog(createDTO);
    }

}
