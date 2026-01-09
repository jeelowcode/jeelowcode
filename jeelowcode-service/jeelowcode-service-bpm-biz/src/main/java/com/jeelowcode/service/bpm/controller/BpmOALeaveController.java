package com.jeelowcode.service.bpm.controller;

import com.jeelowcode.service.bpm.controller.vo.oa.BpmOALeaveCreateReqVO;
import com.jeelowcode.service.bpm.controller.vo.oa.BpmOALeavePageReqVO;
import com.jeelowcode.service.bpm.controller.vo.oa.BpmOALeaveRespVO;
import com.jeelowcode.service.bpm.config.convert.oa.BpmOALeaveConvert;
import com.jeelowcode.service.bpm.entity.BpmOALeaveDO;
import com.jeelowcode.service.bpm.service.IBpmOALeaveService;
import com.jeelowcode.tool.framework.common.pojo.CommonResult;
import com.jeelowcode.tool.framework.common.pojo.PageResult;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;

import static com.jeelowcode.tool.framework.common.pojo.CommonResult.success;
import static com.jeelowcode.tool.framework.security.core.util.SecurityFrameworkUtils.getLoginUserId;
/**
 * OA 请假申请 Controller，用于演示自己存储数据，接入工作流的例子
 *
 * @author jason
 * @author 芋道源码
 */
@Tag(name = "管理后台 - OA 请假申请")
@RestController
@RequestMapping("/bpm/oa/leave")
@Validated
public class BpmOALeaveController {

    @Resource
    private IBpmOALeaveService leaveService;

    @PostMapping("/create")
    @PreAuthorize("@ss.hasPermission('bpm:oa-leave:create')")
    @Operation(tags = "流程管理",summary = "创建请求申请")
    public CommonResult<Long> createLeave(@Valid @RequestBody BpmOALeaveCreateReqVO createReqVO) {
        return success(leaveService.createLeave(getLoginUserId(), createReqVO));
    }

    @GetMapping("/get")
    @PreAuthorize("@ss.hasPermission('bpm:oa-leave:query')")
    @Operation(tags = "流程管理",summary = "获得请假申请")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    public CommonResult<BpmOALeaveRespVO> getLeave(@RequestParam("id") Long id) {
        BpmOALeaveDO leave = leaveService.getLeave(id);
        return success(BpmOALeaveConvert.INSTANCE.convert(leave));
    }

    @GetMapping("/page")
    @PreAuthorize("@ss.hasPermission('bpm:oa-leave:query')")
    @Operation(tags = "流程管理",summary = "获得请假申请分页")
    public CommonResult<PageResult<BpmOALeaveRespVO>> getLeavePage(@Valid BpmOALeavePageReqVO pageVO) {
        PageResult<BpmOALeaveDO> pageResult = leaveService.getLeavePage(getLoginUserId(), pageVO);
        return success(BpmOALeaveConvert.INSTANCE.convertPage(pageResult));
    }

}
