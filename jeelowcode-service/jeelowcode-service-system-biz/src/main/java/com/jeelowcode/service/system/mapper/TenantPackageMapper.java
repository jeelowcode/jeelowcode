package com.jeelowcode.service.system.mapper;

import com.jeelowcode.service.system.controller.vo.tenant.packages.TenantPackagePageReqVO;
import com.jeelowcode.service.system.entity.TenantPackageDO;
import com.jeelowcode.tool.framework.common.pojo.PageResult;
import com.jeelowcode.tool.framework.mybatis.core.mapper.BaseMapperX;
import com.jeelowcode.tool.framework.mybatis.core.query.LambdaQueryWrapperX;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 租户套餐 Mapper
 *
 * @author 芋道源码
 */
@Mapper
public interface TenantPackageMapper extends BaseMapperX<TenantPackageDO> {

    default PageResult<TenantPackageDO> selectPage(TenantPackagePageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<TenantPackageDO>()
                .likeIfPresent(TenantPackageDO::getName, reqVO.getName())
                .eqIfPresent(TenantPackageDO::getStatus, reqVO.getStatus())
                .likeIfPresent(TenantPackageDO::getRemark, reqVO.getRemark())
                .betweenIfPresent(TenantPackageDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(TenantPackageDO::getId));
    }

    default List<TenantPackageDO> selectListByStatus(Integer status) {
        return selectList(TenantPackageDO::getStatus, status);
    }
}
