/*
Apache License
Version 2.0, January 2004
http://www.apache.org/licenses/
本软件受适用的国家软件著作权法（包括国际条约）和开源协议 双重保护许可。

开源协议中文释意如下：
1.JeeLowCode开源版本无任何限制，在遵循本开源协议（Apache2.0）条款下，【允许商用】使用，不会造成侵权行为。
2.允许基于本平台软件开展业务系统开发。
3.在任何情况下，您不得使用本软件开发可能被认为与【本软件竞争】的软件。

最终解释权归：http://www.jeelowcode.com
*/
package com.jeelowcode.core.framework.controller;

import com.jeelowcode.framework.global.JeeLowCodeBaseConstant;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.jeelowcode.core.framework.config.aspect.enhancereport.JeeLowCodeAnnotationAspectjReport;
import com.jeelowcode.framework.utils.model.global.BaseWebResult;
import com.jeelowcode.core.framework.config.validate.ReportCopyValidate;
import com.jeelowcode.core.framework.entity.ReportEntity;
import com.jeelowcode.core.framework.params.PageReportParam;
import com.jeelowcode.core.framework.params.ReportAddOrUpdateParam;
import com.jeelowcode.core.framework.params.vo.PageVo;
import com.jeelowcode.core.framework.params.vo.ReportConfigVo;
import com.jeelowcode.core.framework.params.vo.ReportVo;
import com.jeelowcode.core.framework.params.vo.webconfigreport.WebConfigVo;
import com.jeelowcode.core.framework.service.IReportService;
import com.jeelowcode.core.framework.utils.Func;
import com.jeelowcode.core.framework.utils.FuncWeb;
import com.jeelowcode.framework.utils.annotation.JeelowCodeValidate;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Tag(name = "低代码框架-报表接口")
@RestController
@AllArgsConstructor
@RequestMapping(JeeLowCodeBaseConstant.REQUEST_URL_START + "/report")
public class ReportController extends BaseController {

    private final IReportService reportService;


    @PreAuthorize("@ss.hasPermission('jeelowcode:report:create')")
    @PostMapping("/save")
    @ApiOperationSupport(order = 1)
    @Operation(tags = "报表统计",summary = "数据报表 - 保存")
    public BaseWebResult saveDbFormConfig(@RequestBody ReportAddOrUpdateParam param, HttpServletRequest req) {
        reportService.saveReportConfig(param);

        //刷新插件
        ReportVo report = param.getReport();
        JeeLowCodeAnnotationAspectjReport.refreshPlugin("ADD", report.getReportCode(),report.getJavaConfig());
        return BaseWebResult.success("成功");
    }

    @PreAuthorize("@ss.hasPermission('jeelowcode:report:update')")
    @PutMapping("/update")
    @ApiOperationSupport(order = 2)
    @Operation(tags = "报表统计",summary = "数据报表 - 修改")
    public BaseWebResult updateDbFormConfig(@RequestBody ReportAddOrUpdateParam param) {
        Long reportId = param.getReport().getId();
        ReportEntity oldEntity = reportService.getReportEntityById(reportId);
        String oldJavaConfig = oldEntity.getJavaConfig();
        String newJavaConfig = param.getReport().getJavaConfig();

        reportService.updateReportConfig(param);

        ReportVo report = param.getReport();
        //刷新插件 有->无
        if(Func.isNotEmpty(oldJavaConfig) && Func.isEmpty(newJavaConfig)){
            JeeLowCodeAnnotationAspectjReport.refreshPlugin("DEL", report.getReportCode(),oldJavaConfig);
        }else{
            JeeLowCodeAnnotationAspectjReport.refreshPlugin("ADD", report.getReportCode(),report.getJavaConfig());
        }



        return BaseWebResult.success("成功");
    }

    @PreAuthorize("@ss.hasPermission('jeelowcode:report:delete')")
    @DeleteMapping("/delete")
    @ApiOperationSupport(order = 3)
    @Operation(tags = "报表统计",summary = "数据报表 - 删除")
    public BaseWebResult deleteReportConfig(@RequestBody List<Long> reportIdList) {
        reportService.deleteReportConfig(reportIdList);
        return BaseWebResult.success("成功");
    }

    @PreAuthorize("@ss.hasPermission('jeelowcode:report:query')")
    @PostMapping("/detail")
    @ApiOperationSupport(order = 4)
    @Operation(tags = "报表统计",summary = "数据报表 - 获取详情")
    public BaseWebResult getReportConfig(Long reportId, @RequestBody List<String> typeList) {
        ReportConfigVo vo = reportService.getReportConfig(reportId);
        return BaseWebResult.success(vo);
    }

    @PreAuthorize("@ss.hasPermission('jeelowcode:report:query')")
    @PostMapping("/page")
    @ApiOperationSupport(order = 5)
    @Operation(tags = "报表统计",summary = "数据 - 获取分页数据")
    public BaseWebResult getPageReportConfig(@RequestBody PageReportParam param, PageVo pageVo) {
        Page page = FuncWeb.getPage(pageVo.getPageNo(), pageVo.getPageSize());
        IPage<ReportEntity> pages = reportService.getPageReportConfig(param, page);
        return BaseWebResult.success(pages);
    }

    @GetMapping("/check/report-code")
    @ApiOperationSupport(order = 6)
    @Operation(tags = "报表统计",summary = "校验报表编号是否存在")
    public BaseWebResult checkReport(String reportCode) {
        LambdaQueryWrapper<ReportEntity> wrapper=new LambdaQueryWrapper<>();
        wrapper.eq(ReportEntity::getReportCode,reportCode);
        long count = reportService.count(wrapper);
        return BaseWebResult.success(count>0);
    }

    @GetMapping("/get/web-config")
    @ApiOperationSupport(order = 7)
    @Operation(tags = "报表统计",summary = "获取页面信息配置")
    public BaseWebResult getWebConfig(@RequestParam("reportCode")String reportCode) {
        WebConfigVo webConfig = reportService.getWebConfig(reportCode);
        return BaseWebResult.success(webConfig);
    }

    @JeelowCodeValidate(title = "判断表名是否合法", validateClass = ReportCopyValidate.class)
    @PreAuthorize("@ss.hasPermission('jeelowcode:report:create')")
    @GetMapping({"/copy/{oldReportCode}"})
    @ApiOperationSupport(order = 6)
    @Operation(tags = "报表统计",summary = "复制表")
    public BaseWebResult copy(@PathVariable("oldReportCode") String oldReportCode, String reportCode) {
        reportService.copy(oldReportCode,reportCode);
        return BaseWebResult.success("复制成功");
    }

}
