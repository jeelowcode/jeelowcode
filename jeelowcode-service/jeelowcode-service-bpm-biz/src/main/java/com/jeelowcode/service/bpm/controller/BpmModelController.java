package com.jeelowcode.service.bpm.controller;

import com.jeelowcode.service.bpm.config.convert.definition.BpmModelConvert;
import com.jeelowcode.service.bpm.controller.vo.model.*;
import com.jeelowcode.service.bpm.service.IBpmModelService;
import com.jeelowcode.tool.framework.common.pojo.CommonResult;
import com.jeelowcode.tool.framework.common.pojo.PageResult;
import com.jeelowcode.tool.framework.common.util.io.IoUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.io.IOException;

import static com.jeelowcode.tool.framework.common.pojo.CommonResult.success;

@Tag(name = "管理后台 - 流程模型")
@RestController
@RequestMapping("/bpm/model")
@Validated
public class BpmModelController {

    @Resource
    private IBpmModelService modelService;

    @GetMapping("/page")
    @Operation(tags = "流程管理",summary = "获得模型分页")
    public CommonResult<PageResult<BpmModelPageItemRespVO>> getModelPage(BpmModelPageReqVO pageVO) {
        return success(modelService.getModelPage(pageVO));
    }

    @GetMapping("/get")
    @Operation(tags = "流程管理",summary = "获得模型")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('bpm:model:query')")
    public CommonResult<BpmModelRespVO> getModel(@RequestParam("id") String id) {
        BpmModelRespVO model = modelService.getModel(id);
        return success(model);
    }

    @PostMapping("/create")
    @Operation(tags = "流程管理",summary = "新建模型")
    @PreAuthorize("@ss.hasPermission('bpm:model:create')")
    public CommonResult<String> createModel(@Valid @RequestBody BpmModelCreateReqVO createRetVO) {
        return success(modelService.createModel(createRetVO, null));
    }

    @PutMapping("/update")
    @Operation(tags = "流程管理",summary = "修改模型")
    @PreAuthorize("@ss.hasPermission('bpm:model:update')")
    public CommonResult<Boolean> updateModel(@Valid @RequestBody BpmModelUpdateReqVO modelVO) {
        modelService.updateModel(modelVO);
        return success(true);
    }

    @PostMapping("/import")
    @Operation(tags = "流程管理",summary = "导入模型")
    @PreAuthorize("@ss.hasPermission('bpm:model:import')")
    public CommonResult<String> importModel(@Valid BpmModeImportReqVO importReqVO) throws IOException {
        BpmModelCreateReqVO createReqVO = BpmModelConvert.INSTANCE.convert(importReqVO);
        // 读取文件
        String bpmnXml = IoUtils.readUtf8(importReqVO.getBpmnFile().getInputStream(), false);
        return success(modelService.createModel(createReqVO, bpmnXml));
    }

    @PostMapping("/deploy")
    @Operation(tags = "流程管理",summary = "部署模型")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('bpm:model:deploy')")
    public CommonResult<Boolean> deployModel(@RequestParam("id") String id) {
        modelService.deployModel(id);
        return success(true);
    }

    @PutMapping("/update-state")
    @Operation(tags = "流程管理",summary = "修改模型的状态", description = "实际更新的部署的流程定义的状态")
    @PreAuthorize("@ss.hasPermission('bpm:model:update')")
    public CommonResult<Boolean> updateModelState(@Valid @RequestBody BpmModelUpdateStateReqVO reqVO) {
        modelService.updateModelState(reqVO.getId(), reqVO.getState());
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(tags = "流程管理",summary = "删除模型")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('bpm:model:delete')")
    public CommonResult<Boolean> deleteModel(@RequestParam("id") String id) {
        modelService.deleteModel(id);
        return success(true);
    }
}
