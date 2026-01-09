package com.jeelowcode.service.system.controller;

import com.jeelowcode.service.system.controller.vo.sms.log.SmsLogPageReqVO;
import com.jeelowcode.service.system.controller.vo.sms.log.SmsLogRespVO;
import com.jeelowcode.tool.framework.common.pojo.CommonResult;
import com.jeelowcode.tool.framework.common.pojo.PageParam;
import com.jeelowcode.tool.framework.common.pojo.PageResult;
import com.jeelowcode.tool.framework.common.util.object.BeanUtils;
import com.jeelowcode.tool.framework.excel.core.util.ExcelUtils;
import com.jeelowcode.tool.framework.operatelog.core.annotations.OperateLog;
import com.jeelowcode.service.system.entity.SmsLogDO;
import com.jeelowcode.service.system.service.ISmsLogService;
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

import static com.jeelowcode.tool.framework.common.pojo.CommonResult.success;
import static com.jeelowcode.tool.framework.operatelog.core.enums.OperateTypeEnum.EXPORT;

@Tag(name = "管理后台 - 短信日志")
@RestController
@RequestMapping("/system/sms-log")
@Validated
public class SmsLogController {

    @Resource
    private ISmsLogService smsLogService;

    @GetMapping("/page")
    @Operation(tags = "短信管理",summary = "获得短信日志分页")
    @PreAuthorize("@ss.hasPermission('system:sms-log:query')")
    public CommonResult<PageResult<SmsLogRespVO>> getSmsLogPage(@Valid SmsLogPageReqVO pageReqVO) {
        PageResult<SmsLogDO> pageResult = smsLogService.getSmsLogPage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, SmsLogRespVO.class));
    }

    @GetMapping("/export-excel")
    @Operation(tags = "短信管理",summary = "导出短信日志 Excel")
    @PreAuthorize("@ss.hasPermission('system:sms-log:export')")
    @OperateLog(type = EXPORT)
    public void exportSmsLogExcel(@Valid SmsLogPageReqVO exportReqVO,
                                  HttpServletResponse response) throws IOException {
        exportReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<SmsLogDO> list = smsLogService.getSmsLogPage(exportReqVO).getList();
        // 导出 Excel
        ExcelUtils.write(response, "短信日志.xls", "数据", SmsLogRespVO.class,
                BeanUtils.toBean(list, SmsLogRespVO.class));
    }

}
