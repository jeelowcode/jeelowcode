package com.jeelowcode.service.system.service;

import com.jeelowcode.service.system.dto.OperateLogCreateReqDTO;
import com.jeelowcode.service.system.dto.OperateLogV2CreateReqDTO;
import com.jeelowcode.service.system.dto.OperateLogV2PageReqDTO;
import com.jeelowcode.service.system.controller.vo.logger.operatelog.OperateLogPageReqVO;
import com.jeelowcode.tool.framework.common.pojo.PageResult;
import com.jeelowcode.service.system.entity.OperateLogDO;
import com.jeelowcode.service.system.entity.OperateLogV2DO;

/**
 * 操作日志 Service 接口
 *
 * @author 芋道源码
 */
public interface IOperateLogService {

    /**
     * 记录操作日志
     *
     * @param createReqDTO 操作日志请求
     */
    void createOperateLog(OperateLogCreateReqDTO createReqDTO);

    /**
     * 获得操作日志分页列表
     *
     * @param pageReqVO 分页条件
     * @return 操作日志分页列表
     */
    PageResult<OperateLogDO> getOperateLogPage(OperateLogPageReqVO pageReqVO);

    // ======================= LOG V2 =======================

    /**
     * 记录操作日志 V2
     *
     * @param createReqDTO 创建请求
     */
    void createOperateLogV2(OperateLogV2CreateReqDTO createReqDTO);

    /**
     * 获得操作日志分页列表
     *
     * @param pageReqVO 分页条件
     * @return 操作日志分页列表
     */
    PageResult<OperateLogV2DO> getOperateLogPage(OperateLogV2PageReqDTO pageReqVO);

}
