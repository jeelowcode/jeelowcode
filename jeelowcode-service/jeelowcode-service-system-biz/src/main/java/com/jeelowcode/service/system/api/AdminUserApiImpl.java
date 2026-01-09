package com.jeelowcode.service.system.api;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjUtil;
import com.jeelowcode.service.system.dto.AdminUserRespDTO;
import com.jeelowcode.service.system.entity.DeptDO;
import com.jeelowcode.service.system.entity.AdminUserDO;
import com.jeelowcode.service.system.service.IDeptService;
import com.jeelowcode.service.system.service.IAdminUserService;
import com.jeelowcode.tool.framework.common.util.object.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import static com.jeelowcode.tool.framework.common.util.collection.CollectionUtils.convertSet;

/**
 * Admin 用户 API 实现类
 *
 * @author 芋道源码
 */
@Service
public class AdminUserApiImpl implements AdminUserApi {

    @Resource
    private IAdminUserService userService;
    @Resource
    private IDeptService DeptService;

    @Override
    public AdminUserRespDTO getUser(Long id) {
        AdminUserDO user = userService.getUser(id);
        return BeanUtils.toBean(user, AdminUserRespDTO.class);
    }

    @Override
    public List<AdminUserRespDTO> getUserListBySubordinate(Long id) {
        // 1.1 获取用户负责的部门
        AdminUserDO user = userService.getUser(id);
        if (user == null) {
            return Collections.emptyList();
        }
        ArrayList<Long> deptIds = new ArrayList<>();
        DeptDO dept = DeptService.getDept(user.getDeptId());
        if (dept == null) {
            return Collections.emptyList();
        }
        if (ObjUtil.notEqual(dept.getLeaderUserId(), id)) { // 校验为负责人
            return Collections.emptyList();
        }
        deptIds.add(dept.getId());
        // 1.2 获取所有子部门
        List<DeptDO> childDeptList = DeptService.getChildDeptList(dept.getId());
        if (CollUtil.isNotEmpty(childDeptList)) {
            deptIds.addAll(convertSet(childDeptList, DeptDO::getId));
        }

        // 2. 获取部门对应的用户信息
        List<AdminUserDO> users = userService.getUserListByDeptIds(deptIds);
        users.removeIf(item -> ObjUtil.equal(item.getId(), id)); // 排除自己
        return BeanUtils.toBean(users, AdminUserRespDTO.class);
    }

    @Override
    public List<AdminUserRespDTO> getUserList(Collection<Long> ids) {
        List<AdminUserDO> users = userService.getUserList(ids);
        return BeanUtils.toBean(users, AdminUserRespDTO.class);
    }

    @Override
    public List<AdminUserRespDTO> getUserListByDeptIds(Collection<Long> deptIds) {
        List<AdminUserDO> users = userService.getUserListByDeptIds(deptIds);
        return BeanUtils.toBean(users, AdminUserRespDTO.class);
    }

    @Override
    public List<AdminUserRespDTO> getUserListByPostIds(Collection<Long> postIds) {
        List<AdminUserDO> users = userService.getUserListByPostIds(postIds);
        return BeanUtils.toBean(users, AdminUserRespDTO.class);
    }

    @Override
    public void validateUserList(Collection<Long> ids) {
        userService.validateUserList(ids);
    }

}
