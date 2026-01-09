package com.jeelowcode.service.system.api;

import com.jeelowcode.service.system.dto.LoginLogCreateReqDTO;
import com.jeelowcode.service.system.service.ILoginLogService;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;

/**
 * 登录日志的 API 实现类
 *
 * @author 芋道源码
 */
@Service
@Validated
public class LoginLogApiImpl implements LoginLogApi {

    @Resource
    private ILoginLogService loginLogService;

    @Override
    public void createLoginLog(LoginLogCreateReqDTO reqDTO) {
        loginLogService.createLoginLog(reqDTO);
    }

}
