package com.jeelowcode.service.system.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.jeelowcode.service.system.dto.OperateLogCreateReqDTO;
import com.jeelowcode.service.system.dto.OperateLogV2CreateReqDTO;
import com.jeelowcode.service.system.dto.OperateLogV2PageReqDTO;
import com.jeelowcode.service.system.controller.vo.logger.operatelog.OperateLogPageReqVO;
import com.jeelowcode.service.system.service.IOperateLogService;
import com.jeelowcode.tool.framework.common.pojo.PageResult;
import com.jeelowcode.tool.framework.common.util.object.BeanUtils;
import com.jeelowcode.tool.framework.common.util.string.StrUtils;
import com.jeelowcode.service.system.entity.OperateLogDO;
import com.jeelowcode.service.system.entity.OperateLogV2DO;
import com.jeelowcode.service.system.entity.AdminUserDO;
import com.jeelowcode.service.system.mapper.OperateLogMapper;
import com.jeelowcode.service.system.mapper.OperateLogV2Mapper;
import com.jeelowcode.service.system.service.IAdminUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;
import java.util.Collection;

import static com.jeelowcode.tool.framework.common.util.collection.CollectionUtils.convertSet;
import static com.jeelowcode.service.system.entity.OperateLogDO.JAVA_METHOD_ARGS_MAX_LENGTH;
import static com.jeelowcode.service.system.entity.OperateLogDO.RESULT_MAX_LENGTH;

/**
 * 操作日志 Service 实现类
 *
 * @author 芋道源码
 */
@Service
@Validated
@Slf4j
public class OperateLogServiceImpl implements IOperateLogService {

    @Resource
    private OperateLogMapper operateLogMapper;
    @Resource
    private OperateLogV2Mapper operateLogV2Mapper;

    @Resource
    private IAdminUserService userService;

    @Override
    public void createOperateLog(OperateLogCreateReqDTO createReqDTO) {
        OperateLogDO log = BeanUtils.toBean(createReqDTO, OperateLogDO.class);
        log.setJavaMethodArgs(StrUtils.maxLength(log.getJavaMethodArgs(), JAVA_METHOD_ARGS_MAX_LENGTH));
        log.setResultData(StrUtils.maxLength(log.getResultData(), RESULT_MAX_LENGTH));
        operateLogMapper.insert(log);
    }

    @Override
    public PageResult<OperateLogDO> getOperateLogPage(OperateLogPageReqVO pageReqVO) {
        // 处理基于用户昵称的查询
        Collection<Long> userIds = null;
        if (StrUtil.isNotEmpty(pageReqVO.getUserNickname())) {
            userIds = convertSet(userService.getUserListByNickname(pageReqVO.getUserNickname()), AdminUserDO::getId);
            if (CollUtil.isEmpty(userIds)) {
                return PageResult.empty();
            }
        }
        // 查询分页
        return operateLogMapper.selectPage(pageReqVO, userIds);
    }

    // ======================= LOG V2 =======================

    @Override
    public void createOperateLogV2(OperateLogV2CreateReqDTO createReqDTO) {
        OperateLogV2DO log = BeanUtils.toBean(createReqDTO, OperateLogV2DO.class);
        operateLogV2Mapper.insert(log);
    }

    @Override
    public PageResult<OperateLogV2DO> getOperateLogPage(OperateLogV2PageReqDTO pageReqDTO) {
        return operateLogV2Mapper.selectPage(pageReqDTO);
    }

}
