package com.jeelowcode.service.system.api;

import com.jeelowcode.service.system.service.IRoleService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Collection;

/**
 * 角色 API 实现类
 *
 * @author 芋道源码
 */
@Service
public class RoleApiImpl implements RoleApi {

    @Resource
    private IRoleService roleService;

    @Override
    public void validRoleList(Collection<Long> ids) {
        roleService.validateRoleList(ids);
    }
}
