package com.jeelowcode.service.system.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.jeelowcode.service.system.controller.vo.tenant.packages.TenantPackagePageReqVO;
import com.jeelowcode.service.system.controller.vo.tenant.packages.TenantPackageSaveReqVO;
import com.jeelowcode.service.system.constant.ErrorCodeConstants;
import com.jeelowcode.service.system.service.ITenantPackageService;
import com.jeelowcode.service.system.service.ITenantService;
import com.jeelowcode.tool.framework.common.enums.CommonStatusEnum;
import com.jeelowcode.tool.framework.common.exception.util.ServiceExceptionUtil;
import com.jeelowcode.tool.framework.common.pojo.PageResult;
import com.jeelowcode.tool.framework.common.util.object.BeanUtils;
import com.jeelowcode.service.system.entity.TenantDO;
import com.jeelowcode.service.system.entity.TenantPackageDO;
import com.jeelowcode.service.system.mapper.TenantPackageMapper;
import com.baomidou.dynamic.datasource.annotation.DSTransactional;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;
import java.util.List;

import static com.jeelowcode.tool.framework.common.exception.util.ServiceExceptionUtil.exception;

/**
 * 租户套餐 Service 实现类
 *
 * @author 芋道源码
 */
@Service
@Validated
public class TenantPackageServiceImpl implements ITenantPackageService {

    @Resource
    private TenantPackageMapper tenantPackageMapper;

    @Resource
    @Lazy // 避免循环依赖的报错
    private ITenantService tenantService;

    @Override
    public Long createTenantPackage(TenantPackageSaveReqVO createReqVO) {
        // 插入
        TenantPackageDO tenantPackage = BeanUtils.toBean(createReqVO, TenantPackageDO.class);
        tenantPackageMapper.insert(tenantPackage);
        // 返回
        return tenantPackage.getId();
    }

    @Override
    @DSTransactional // 多数据源，使用 @DSTransactional 保证本地事务，以及数据源的切换
    public void updateTenantPackage(TenantPackageSaveReqVO updateReqVO) {
        // 校验存在
        TenantPackageDO tenantPackage = validateTenantPackageExists(updateReqVO.getId());
        // 更新
        TenantPackageDO updateObj = BeanUtils.toBean(updateReqVO, TenantPackageDO.class);
        tenantPackageMapper.updateById(updateObj);
        // 如果菜单发生变化，则修改每个租户的菜单
        if (!CollUtil.isEqualList(tenantPackage.getMenuIds(), updateReqVO.getMenuIds())) {
            List<TenantDO> tenants = tenantService.getTenantListByPackageId(tenantPackage.getId());
            tenants.forEach(tenant -> tenantService.updateTenantRoleMenu(tenant.getId(), updateReqVO.getMenuIds()));
        }
    }

    @Override
    public void deleteTenantPackage(Long id) {
        // 校验存在
        validateTenantPackageExists(id);
        // 校验正在使用
        validateTenantUsed(id);
        // 删除
        tenantPackageMapper.deleteById(id);
    }

    private TenantPackageDO validateTenantPackageExists(Long id) {
        TenantPackageDO tenantPackage = tenantPackageMapper.selectById(id);
        if (tenantPackage == null) {
            throw ServiceExceptionUtil.exception(ErrorCodeConstants.TENANT_PACKAGE_NOT_EXISTS);
        }
        return tenantPackage;
    }

    private void validateTenantUsed(Long id) {
        if (tenantService.getTenantCountByPackageId(id) > 0) {
            throw ServiceExceptionUtil.exception(ErrorCodeConstants.TENANT_PACKAGE_USED);
        }
    }

    @Override
    public TenantPackageDO getTenantPackage(Long id) {
        return tenantPackageMapper.selectById(id);
    }

    @Override
    public PageResult<TenantPackageDO> getTenantPackagePage(TenantPackagePageReqVO pageReqVO) {
        return tenantPackageMapper.selectPage(pageReqVO);
    }

    @Override
    public TenantPackageDO validTenantPackage(Long id) {
        TenantPackageDO tenantPackage = tenantPackageMapper.selectById(id);
        if (tenantPackage == null) {
            throw ServiceExceptionUtil.exception(ErrorCodeConstants.TENANT_PACKAGE_NOT_EXISTS);
        }
        if (tenantPackage.getStatus().equals(CommonStatusEnum.DISABLE.getStatus())) {
            throw exception(ErrorCodeConstants.TENANT_PACKAGE_DISABLE, tenantPackage.getName());
        }
        return tenantPackage;
    }

    @Override
    public List<TenantPackageDO> getTenantPackageListByStatus(Integer status) {
        return tenantPackageMapper.selectListByStatus(status);
    }

}
