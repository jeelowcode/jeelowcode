package com.jeelowcode.service.system.controller;

import com.jeelowcode.service.system.controller.vo.socail.user.SocialUserBindReqVO;
import com.jeelowcode.service.system.controller.vo.socail.user.SocialUserPageReqVO;
import com.jeelowcode.service.system.controller.vo.socail.user.SocialUserUnbindReqVO;
import com.jeelowcode.tool.framework.common.enums.UserTypeEnum;
import com.jeelowcode.tool.framework.common.pojo.CommonResult;
import com.jeelowcode.tool.framework.common.pojo.PageResult;
import com.jeelowcode.tool.framework.common.util.object.BeanUtils;
import com.jeelowcode.service.system.controller.vo.socail.user.SocialUserRespVO;
import com.jeelowcode.service.system.config.convert.social.SocialUserConvert;
import com.jeelowcode.service.system.entity.SocialUserDO;
import com.jeelowcode.service.system.service.ISocialUserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;

import static com.jeelowcode.tool.framework.common.pojo.CommonResult.success;
import static com.jeelowcode.tool.framework.security.core.util.SecurityFrameworkUtils.getLoginUserId;

@Tag(name = "管理后台 - 社交用户")
@RestController
@RequestMapping("/system/social-user")
@Validated
public class SocialUserController {

    @Resource
    private ISocialUserService socialUserService;

    @PostMapping("/bind")
    @Operation(tags = "社交管理",summary = "社交绑定，使用 code 授权码")
    public CommonResult<Boolean> socialBind(@RequestBody @Valid SocialUserBindReqVO reqVO) {
        socialUserService.bindSocialUser(SocialUserConvert.INSTANCE.convert(
                getLoginUserId(), UserTypeEnum.ADMIN.getValue(), reqVO));
        return CommonResult.success(true);
    }

    @DeleteMapping("/unbind")
    @Operation(tags = "社交管理",summary = "取消社交绑定")
    public CommonResult<Boolean> socialUnbind(@RequestBody SocialUserUnbindReqVO reqVO) {
        socialUserService.unbindSocialUser(getLoginUserId(), UserTypeEnum.ADMIN.getValue(), reqVO.getType(), reqVO.getOpenid());
        return CommonResult.success(true);
    }

    // ==================== 社交用户 CRUD ====================

    @GetMapping("/get")
    @Operation(tags = "社交管理",summary = "获得社交用户")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('system:social-user:query')")
    public CommonResult<SocialUserRespVO> getSocialUser(@RequestParam("id") Long id) {
        SocialUserDO socialUser = socialUserService.getSocialUser(id);
        return success(BeanUtils.toBean(socialUser, SocialUserRespVO.class));
    }

    @GetMapping("/page")
    @Operation(tags = "社交管理",summary = "获得社交用户分页")
    @PreAuthorize("@ss.hasPermission('system:social-user:query')")
    public CommonResult<PageResult<SocialUserRespVO>> getSocialUserPage(@Valid SocialUserPageReqVO pageVO) {
        PageResult<SocialUserDO> pageResult = socialUserService.getSocialUserPage(pageVO);
        return success(BeanUtils.toBean(pageResult, SocialUserRespVO.class));
    }

}
