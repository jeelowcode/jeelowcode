package com.jeelowcode.service.infra.api.impl;

import com.jeelowcode.service.infra.api.ApiAccessLogApi;
import com.jeelowcode.service.infra.dto.ApiAccessLogCreateReqDTO;
import com.jeelowcode.service.infra.service.IApiAccessLogService;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;

/**
 * API 访问日志的 API 实现类
 *
 * @author 芋道源码
 */
@Service
@Validated
public class ApiAccessLogApiImpl implements ApiAccessLogApi {

    @Resource
    private IApiAccessLogService apiAccessLogService;

    @Override
    public void createApiAccessLog(ApiAccessLogCreateReqDTO createDTO) {
        apiAccessLogService.createApiAccessLog(createDTO);
    }

}
