package com.jeelowcode.service.system.controller;

import cn.hutool.core.collection.CollUtil;
import com.jeelowcode.service.system.controller.vo.user.profile.UserProfileRespVO;
import com.jeelowcode.service.system.controller.vo.user.profile.UserProfileUpdatePasswordReqVO;
import com.jeelowcode.service.system.controller.vo.user.profile.UserProfileUpdateReqVO;
import com.jeelowcode.tool.framework.common.enums.UserTypeEnum;
import com.jeelowcode.tool.framework.common.pojo.CommonResult;
import com.jeelowcode.tool.framework.permission.core.annotation.DataPermission;
import com.jeelowcode.service.system.config.convert.user.UserConvert;
import com.jeelowcode.service.system.entity.DeptDO;
import com.jeelowcode.service.system.entity.PostDO;
import com.jeelowcode.service.system.entity.RoleDO;
import com.jeelowcode.service.system.entity.SocialUserDO;
import com.jeelowcode.service.system.entity.AdminUserDO;
import com.jeelowcode.service.system.service.IDeptService;
import com.jeelowcode.service.system.service.IPostService;
import com.jeelowcode.service.system.service.IPermissionService;
import com.jeelowcode.service.system.service.IRoleService;
import com.jeelowcode.service.system.service.ISocialUserService;
import com.jeelowcode.service.system.service.IAdminUserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;

import static com.jeelowcode.tool.framework.common.exception.util.ServiceExceptionUtil.exception;
import static com.jeelowcode.tool.framework.common.pojo.CommonResult.success;
import static com.jeelowcode.tool.framework.security.core.util.SecurityFrameworkUtils.getLoginUserId;
import static com.jeelowcode.service.infra.enums.ErrorCodeConstants.FILE_IS_EMPTY;

@Tag(name = "管理后台 - 用户个人中心")
@RestController
@RequestMapping("/system/user/profile")
@Validated
@Slf4j
public class UserProfileController {

    @Resource
    private IAdminUserService userService;
    @Resource
    private IDeptService DeptService;
    @Resource
    private IPostService postService;
    @Resource
    private IPermissionService permissionService;
    @Resource
    private IRoleService roleService;
    @Resource
    private ISocialUserService socialService;

    @GetMapping("/get")
    @Operation(tags = "用户管理",summary = "获得登录用户信息")
    @DataPermission(enable = false) // 关闭数据权限，避免只查看自己时，查询不到部门。
    public CommonResult<UserProfileRespVO> getUserProfile() {
        // 获得用户基本信息
        AdminUserDO user = userService.getUser(getLoginUserId());
        // 获得用户角色
        List<RoleDO> userRoles = roleService.getRoleListFromCache(permissionService.getUserRoleIdListByUserId(user.getId()));
        // 获得部门信息
        DeptDO dept = user.getDeptId() != null ? DeptService.getDept(user.getDeptId()) : null;
        // 获得岗位信息
        List<PostDO> posts = CollUtil.isNotEmpty(user.getPostIds()) ? postService.getPostList(user.getPostIds()) : null;
        // 获得社交用户信息
        List<SocialUserDO> socialUsers = socialService.getSocialUserList(user.getId(), UserTypeEnum.ADMIN.getValue());
        return success(UserConvert.INSTANCE.convert(user, userRoles, dept, posts, socialUsers));
    }

    @PutMapping("/update")
    @Operation(tags = "用户管理",summary = "修改用户个人信息")
    public CommonResult<Boolean> updateUserProfile(@Valid @RequestBody UserProfileUpdateReqVO reqVO) {
        userService.updateUserProfile(getLoginUserId(), reqVO);
        return success(true);
    }

    @PutMapping("/update-password")
    @Operation(tags = "用户管理",summary = "修改用户个人密码")
    public CommonResult<Boolean> updateUserProfilePassword(@Valid @RequestBody UserProfileUpdatePasswordReqVO reqVO) {
        userService.updateUserPassword(getLoginUserId(), reqVO);
        return success(true);
    }

    @RequestMapping(value = "/update-avatar",
            method = {RequestMethod.POST, RequestMethod.PUT}) // 解决 uni-app 不支持 Put 上传文件的问题
    @Operation(tags = "用户管理",summary = "上传用户个人头像")
    public CommonResult<String> updateUserAvatar(@RequestParam("avatarFile") MultipartFile file) throws Exception {
        if (file.isEmpty()) {
            throw exception(FILE_IS_EMPTY);
        }
        String avatar = userService.updateUserAvatar(getLoginUserId(), file.getInputStream());
        return success(avatar);
    }

}
