package com.jeelowcode.service.infra.service;

import com.jeelowcode.tool.framework.common.pojo.PageResult;
import com.jeelowcode.service.infra.dto.ApiErrorLogCreateReqDTO;
import com.jeelowcode.service.infra.controller.vo.logger.ApiErrorLogPageReqVO;
import com.jeelowcode.service.infra.entity.ApiErrorLogDO;

/**
 * API 错误日志 Service 接口
 *
 * @author 芋道源码
 */
public interface IApiErrorLogService {

    /**
     * 创建 API 错误日志
     *
     * @param createReqDTO API 错误日志
     */
    void createApiErrorLog(ApiErrorLogCreateReqDTO createReqDTO);

    /**
     * 获得 API 错误日志分页
     *
     * @param pageReqVO 分页查询
     * @return API 错误日志分页
     */
    PageResult<ApiErrorLogDO> getApiErrorLogPage(ApiErrorLogPageReqVO pageReqVO);

    /**
     * 更新 API 错误日志已处理
     *
     * @param id API 日志编号
     * @param processStatus 处理结果
     * @param processUserId 处理人
     */
    void updateApiErrorLogProcess(Long id, Integer processStatus, Long processUserId);

    /**
     * 清理 exceedDay 天前的错误日志
     *
     * @param exceedDay 超过多少天就进行清理
     * @param deleteLimit 清理的间隔条数
     */
    Integer cleanErrorLog(Integer exceedDay, Integer deleteLimit);

}
