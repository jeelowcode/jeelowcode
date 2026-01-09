package com.jeelowcode.service.system.controller;

import com.jeelowcode.tool.framework.common.pojo.CommonResult;
import com.jeelowcode.tool.framework.common.pojo.PageParam;
import com.jeelowcode.tool.framework.common.pojo.PageResult;
import com.jeelowcode.tool.framework.excel.core.util.ExcelUtils;
import com.jeelowcode.tool.framework.operatelog.core.annotations.OperateLog;
import com.jeelowcode.service.system.controller.vo.logger.operatelog.OperateLogPageReqVO;
import com.jeelowcode.service.system.controller.vo.logger.operatelog.OperateLogRespVO;
import com.jeelowcode.service.system.config.convert.logger.OperateLogConvert;
import com.jeelowcode.service.system.entity.OperateLogDO;
import com.jeelowcode.service.system.entity.AdminUserDO;
import com.jeelowcode.service.system.service.IOperateLogService;
import com.jeelowcode.service.system.service.IAdminUserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import static com.jeelowcode.tool.framework.common.pojo.CommonResult.success;
import static com.jeelowcode.tool.framework.common.util.collection.CollectionUtils.convertList;
import static com.jeelowcode.tool.framework.operatelog.core.enums.OperateTypeEnum.EXPORT;

@Tag(name = "管理后台 - 操作日志")
@RestController
@RequestMapping("/system/operate-log")
@Validated
public class OperateLogController {

    @Resource
    private IOperateLogService operateLogService;
    @Resource
    private IAdminUserService userService;

    @GetMapping("/page")
    @Operation(tags = "审查日志",summary = "查看操作日志分页列表")
    @PreAuthorize("@ss.hasPermission('system:operate-log:query')")
    public CommonResult<PageResult<OperateLogRespVO>> pageOperateLog(@Valid OperateLogPageReqVO pageReqVO) {
        PageResult<OperateLogDO> pageResult = operateLogService.getOperateLogPage(pageReqVO);
        // 获得拼接需要的数据
        Map<Long, AdminUserDO> userMap = userService.getUserMap(
                convertList(pageResult.getList(), OperateLogDO::getUserId));
        return success(new PageResult<>(OperateLogConvert.INSTANCE.convertList(pageResult.getList(), userMap),
                pageResult.getTotal()));
    }

    @Operation(tags = "审查日志",summary = "导出操作日志")
    @GetMapping("/export")
    @PreAuthorize("@ss.hasPermission('system:operate-log:export')")
    @OperateLog(type = EXPORT)
    public void exportOperateLog(HttpServletResponse response, @Valid OperateLogPageReqVO exportReqVO) throws IOException {
        exportReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<OperateLogDO> list = operateLogService.getOperateLogPage(exportReqVO).getList();
        // 输出
        Map<Long, AdminUserDO> userMap = userService.getUserMap(
                convertList(list, OperateLogDO::getUserId));
        ExcelUtils.write(response, "操作日志.xls", "数据列表", OperateLogRespVO.class,
                OperateLogConvert.INSTANCE.convertList(list, userMap));
    }

}
