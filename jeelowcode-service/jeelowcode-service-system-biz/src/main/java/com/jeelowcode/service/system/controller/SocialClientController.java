package com.jeelowcode.service.system.controller;

import com.jeelowcode.service.system.controller.vo.socail.client.SocialClientPageReqVO;
import com.jeelowcode.service.system.controller.vo.socail.client.SocialClientRespVO;
import com.jeelowcode.service.system.controller.vo.socail.client.SocialClientSaveReqVO;
import com.jeelowcode.tool.framework.common.pojo.CommonResult;
import com.jeelowcode.tool.framework.common.pojo.PageResult;
import com.jeelowcode.tool.framework.common.util.object.BeanUtils;
import com.jeelowcode.service.system.entity.SocialClientDO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;

import static com.jeelowcode.tool.framework.common.pojo.CommonResult.success;

@Tag(name = "管理后台 - 社交客户端")
@RestController
@RequestMapping("/system/social-client")
@Validated
public class SocialClientController {

    @Resource
    private com.jeelowcode.service.system.service.ISocialClientService ISocialClientService;

    @PostMapping("/create")
    @Operation(tags = "社交管理",summary = "创建社交客户端")
    @PreAuthorize("@ss.hasPermission('system:social-client:create')")
    public CommonResult<Long> createSocialClient(@Valid @RequestBody SocialClientSaveReqVO createReqVO) {
        return success(ISocialClientService.createSocialClient(createReqVO));
    }

    @PutMapping("/update")
    @Operation(tags = "社交管理",summary = "更新社交客户端")
    @PreAuthorize("@ss.hasPermission('system:social-client:update')")
    public CommonResult<Boolean> updateSocialClient(@Valid @RequestBody SocialClientSaveReqVO updateReqVO) {
        ISocialClientService.updateSocialClient(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(tags = "社交管理",summary = "删除社交客户端")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('system:social-client:delete')")
    public CommonResult<Boolean> deleteSocialClient(@RequestParam("id") Long id) {
        ISocialClientService.deleteSocialClient(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(tags = "社交管理",summary = "获得社交客户端")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('system:social-client:query')")
    public CommonResult<SocialClientRespVO> getSocialClient(@RequestParam("id") Long id) {
        SocialClientDO client = ISocialClientService.getSocialClient(id);
        return success(BeanUtils.toBean(client, SocialClientRespVO.class));
    }

    @GetMapping("/page")
    @Operation(tags = "社交管理",summary = "获得社交客户端分页")
    @PreAuthorize("@ss.hasPermission('system:social-client:query')")
    public CommonResult<PageResult<SocialClientRespVO>> getSocialClientPage(@Valid SocialClientPageReqVO pageVO) {
        PageResult<SocialClientDO> pageResult = ISocialClientService.getSocialClientPage(pageVO);
        return success(BeanUtils.toBean(pageResult, SocialClientRespVO.class));
    }

}
