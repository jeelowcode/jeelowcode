package com.jeelowcode.service.infra.mapper;

import com.jeelowcode.service.infra.controller.vo.logger.ApiAccessLogPageReqVO;
import com.jeelowcode.service.infra.entity.ApiAccessLogDO;
import com.jeelowcode.tool.framework.common.pojo.PageResult;
import com.jeelowcode.tool.framework.mybatis.core.mapper.BaseMapperX;
import com.jeelowcode.tool.framework.mybatis.core.query.LambdaQueryWrapperX;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDateTime;

/**
 * API 访问日志 Mapper
 *
 * @author 芋道源码
 */
@Mapper
public interface ApiAccessLogMapper extends BaseMapperX<ApiAccessLogDO> {

    default PageResult<ApiAccessLogDO> selectPage(ApiAccessLogPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<ApiAccessLogDO>()
                .eqIfPresent(ApiAccessLogDO::getUserId, reqVO.getUserId())
                .eqIfPresent(ApiAccessLogDO::getUserType, reqVO.getUserType())
                .eqIfPresent(ApiAccessLogDO::getApplicationName, reqVO.getApplicationName())
                .likeIfPresent(ApiAccessLogDO::getRequestUrl, reqVO.getRequestUrl())
                .betweenIfPresent(ApiAccessLogDO::getBeginTime, reqVO.getBeginTime())
                .geIfPresent(ApiAccessLogDO::getDuration, reqVO.getDuration())
                .eqIfPresent(ApiAccessLogDO::getResultCode, reqVO.getResultCode())
                .orderByDesc(ApiAccessLogDO::getId)
        );
    }

    /**
     * 物理删除指定时间之前的日志
     *
     * @param createTime 最大时间
     * @param limit 删除条数，防止一次删除太多
     * @return 删除条数
     */
    @Delete("DELETE FROM infra_api_access_log WHERE create_time < #{createTime} LIMIT #{limit}")
    Integer deleteByCreateTimeLt(@Param("createTime") LocalDateTime createTime, @Param("limit") Integer limit);

}
