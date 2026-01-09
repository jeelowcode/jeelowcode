package com.jeelowcode.service.bpm.controller;


import com.jeelowcode.service.bpm.controller.vo.activity.BpmActivityRespVO;
import com.jeelowcode.service.bpm.service.IBpmActivityService;
import com.jeelowcode.tool.framework.common.pojo.CommonResult;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

import static com.jeelowcode.tool.framework.common.pojo.CommonResult.success;

@Tag(name = "管理后台 - 流程活动实例")
@RestController
@RequestMapping("/bpm/activity")
@Validated
public class BpmActivityController {

    @Resource
    private IBpmActivityService activityService;

    @GetMapping("/list")
    @Operation(tags = "流程管理",summary = "生成指定流程实例的高亮流程图",
            description = "只高亮进行中的任务。不过要注意，该接口暂时没用，通过前端的 ProcessViewer.vue 界面的 highlightDiagram 方法生成")
    @Parameter(name = "processInstanceId", description = "流程实例的编号", required = true)
    @PreAuthorize("@ss.hasPermission('bpm:task:query')")
    public CommonResult<List<BpmActivityRespVO>> getActivityList(
            @RequestParam("processInstanceId") String processInstanceId) {
        return success(activityService.getActivityListByProcessInstanceId(processInstanceId));
    }
}
