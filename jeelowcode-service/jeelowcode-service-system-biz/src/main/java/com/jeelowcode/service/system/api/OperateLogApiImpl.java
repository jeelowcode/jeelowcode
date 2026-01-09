package com.jeelowcode.service.system.api;

import cn.hutool.core.collection.CollUtil;
import com.jeelowcode.service.system.dto.OperateLogCreateReqDTO;
import com.jeelowcode.service.system.dto.OperateLogV2CreateReqDTO;
import com.jeelowcode.service.system.dto.OperateLogV2PageReqDTO;
import com.jeelowcode.service.system.dto.OperateLogV2RespDTO;
import com.jeelowcode.service.system.config.convert.logger.OperateLogConvert;
import com.jeelowcode.service.system.entity.OperateLogV2DO;
import com.jeelowcode.service.system.entity.AdminUserDO;
import com.jeelowcode.service.system.service.IOperateLogService;
import com.jeelowcode.service.system.service.IAdminUserService;
import com.jeelowcode.tool.framework.common.pojo.PageResult;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;
import java.util.List;

import static com.jeelowcode.tool.framework.common.util.collection.CollectionUtils.convertSet;

/**
 * 操作日志 API 实现类
 *
 * @author 芋道源码
 */
@Service
@Validated
public class OperateLogApiImpl implements OperateLogApi {

    @Resource
    private IOperateLogService operateLogService;
    @Resource
    private IAdminUserService adminUserService;

    @Override
    public void createOperateLog(OperateLogCreateReqDTO createReqDTO) {
        operateLogService.createOperateLog(createReqDTO);
    }

    @Override
    @Async
    public void createOperateLogV2(OperateLogV2CreateReqDTO createReqDTO) {
        operateLogService.createOperateLogV2(createReqDTO);
    }

    @Override
    public PageResult<OperateLogV2RespDTO> getOperateLogPage(OperateLogV2PageReqDTO pageReqVO) {
        PageResult<OperateLogV2DO> operateLogPage = operateLogService.getOperateLogPage(pageReqVO);
        if (CollUtil.isEmpty(operateLogPage.getList())) {
            return PageResult.empty();
        }

        // 获取用户
        List<AdminUserDO> userList = adminUserService.getUserList(
                convertSet(operateLogPage.getList(), OperateLogV2DO::getUserId));
        return OperateLogConvert.INSTANCE.convertPage(operateLogPage, userList);
    }

}
