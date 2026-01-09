package com.jeelowcode.service.bpm.controller;

import cn.hutool.core.collection.CollUtil;
import com.jeelowcode.service.bpm.controller.vo.instance.BpmProcessInstanceCopyCreateReqVO;
import com.jeelowcode.service.bpm.controller.vo.instance.BpmProcessInstanceCopyMyPageReqVO;
import com.jeelowcode.service.bpm.controller.vo.instance.BpmProcessInstanceCopyPageItemRespVO;
import com.jeelowcode.service.bpm.config.convert.cc.BpmProcessInstanceCopyConvert;
import com.jeelowcode.service.bpm.entity.BpmProcessInstanceCopyDO;
import com.jeelowcode.service.bpm.service.IBpmProcessInstanceService;
import com.jeelowcode.service.bpm.service.IBpmTaskService;
import com.jeelowcode.service.bpm.service.IBpmProcessInstanceCopyService;
import com.jeelowcode.service.system.api.AdminUserApi;
import com.jeelowcode.service.system.dto.AdminUserRespDTO;
import com.jeelowcode.tool.framework.common.pojo.CommonResult;
import com.jeelowcode.tool.framework.common.pojo.PageResult;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.Map;
import java.util.stream.Stream;
import static com.jeelowcode.tool.framework.common.pojo.CommonResult.success;
import static com.jeelowcode.tool.framework.security.core.util.SecurityFrameworkUtils.getLoginUserId;
import static com.jeelowcode.tool.framework.common.util.collection.CollectionUtils.convertListByFlatMap;
import static com.jeelowcode.tool.framework.common.util.collection.CollectionUtils.convertSet;

@Tag(name = "管理后台 - 流程实例抄送")
@RestController
@RequestMapping("/bpm/process-instance/cc")
@Validated
public class BpmProcessInstanceCopyController {

    @Resource
    private IBpmProcessInstanceCopyService processInstanceCopyService;
    @Resource
    private IBpmProcessInstanceService bpmProcessInstanceService;

    @Resource
    private AdminUserApi adminUserApi;

    @Resource
    private IBpmTaskService bpmTaskService;

    @PostMapping("/create")
    @Operation(tags = "流程管理",summary = "抄送流程")
    @PreAuthorize("@ss.hasPermission('bpm:process-instance-cc:create')")
    public CommonResult<Boolean> createProcessInstanceCopy(@Valid @RequestBody BpmProcessInstanceCopyCreateReqVO createReqVO) {
        processInstanceCopyService.createProcessInstanceCopy(getLoginUserId(), createReqVO);
        return success(true);
    }

    @GetMapping("/my-page")
    @Operation(tags = "流程管理",summary = "获得抄送流程分页列表")
    @PreAuthorize("@ss.hasPermission('bpm:process-instance-cc:query')")
    public CommonResult<PageResult<BpmProcessInstanceCopyPageItemRespVO>> getProcessInstanceCopyPage(
            @Valid BpmProcessInstanceCopyMyPageReqVO pageReqVO) {
        PageResult<BpmProcessInstanceCopyDO> pageResult = processInstanceCopyService.getMyProcessInstanceCopyPage(getLoginUserId(), pageReqVO);
        if (CollUtil.isEmpty(pageResult.getList())) {
            return success(new PageResult<>(pageResult.getTotal()));
        }

        // 拼接返回
        Map<String, String> taskNameMap = bpmTaskService.getTaskNameByTaskIds(
                convertSet(pageResult.getList(), BpmProcessInstanceCopyDO::getTaskId));
        Map<String, String> processNameMap = bpmProcessInstanceService.getProcessInstanceNameMap(
                convertSet(pageResult.getList(), BpmProcessInstanceCopyDO::getProcessInstanceId));
        Map<Long, AdminUserRespDTO> userMap = adminUserApi.getUserMap(convertListByFlatMap(pageResult.getList(),
                copy -> Stream.of(copy.getStartUserId(), Long.parseLong(copy.getCreator()))));
        return success(BpmProcessInstanceCopyConvert.INSTANCE.convertPage(pageResult, taskNameMap, processNameMap, userMap));
    }

}
