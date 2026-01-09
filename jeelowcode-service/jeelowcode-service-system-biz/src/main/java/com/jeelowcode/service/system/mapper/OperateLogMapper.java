package com.jeelowcode.service.system.mapper;

import com.jeelowcode.service.system.controller.vo.logger.operatelog.OperateLogPageReqVO;
import com.jeelowcode.service.system.entity.OperateLogDO;
import com.jeelowcode.tool.framework.common.exception.enums.GlobalErrorCodeConstants;
import com.jeelowcode.tool.framework.common.pojo.PageResult;
import com.jeelowcode.tool.framework.mybatis.core.mapper.BaseMapperX;
import com.jeelowcode.tool.framework.mybatis.core.query.LambdaQueryWrapperX;
import org.apache.ibatis.annotations.Mapper;

import java.util.Collection;

@Mapper
public interface OperateLogMapper extends BaseMapperX<OperateLogDO> {

    default PageResult<OperateLogDO> selectPage(OperateLogPageReqVO reqVO, Collection<Long> userIds) {
        LambdaQueryWrapperX<OperateLogDO> query = new LambdaQueryWrapperX<OperateLogDO>()
                .likeIfPresent(OperateLogDO::getModule, reqVO.getModule())
                .inIfPresent(OperateLogDO::getUserId, userIds)
                .eqIfPresent(OperateLogDO::getType, reqVO.getType())
                .betweenIfPresent(OperateLogDO::getStartTime, reqVO.getStartTime());
        if (Boolean.TRUE.equals(reqVO.getSuccess())) {
            query.eq(OperateLogDO::getResultCode, GlobalErrorCodeConstants.SUCCESS.getCode());
        } else if (Boolean.FALSE.equals(reqVO.getSuccess())) {
            query.gt(OperateLogDO::getResultCode, GlobalErrorCodeConstants.SUCCESS.getCode());
        }
        query.orderByDesc(OperateLogDO::getId); // 降序
        return selectPage(reqVO, query);
    }

}
