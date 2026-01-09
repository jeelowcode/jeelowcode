package com.jeelowcode.service.system.service;

import com.jeelowcode.service.system.dto.LoginLogCreateReqDTO;
import com.jeelowcode.service.system.controller.vo.logger.loginlog.LoginLogPageReqVO;
import com.jeelowcode.tool.framework.common.pojo.PageResult;
import com.jeelowcode.service.system.entity.LoginLogDO;

import javax.validation.Valid;

/**
 * 登录日志 Service 接口
 */
public interface ILoginLogService {

    /**
     * 获得登录日志分页
     *
     * @param pageReqVO 分页条件
     * @return 登录日志分页
     */
    PageResult<LoginLogDO> getLoginLogPage(LoginLogPageReqVO pageReqVO);

    /**
     * 创建登录日志
     *
     * @param reqDTO 日志信息
     */
    void createLoginLog(@Valid LoginLogCreateReqDTO reqDTO);

}
