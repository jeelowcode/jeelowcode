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
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.jeelowcode.framework.utils.model.global.BaseWebResult;
import com.jeelowcode.core.framework.utils.Func;
import com.jeelowcode.core.framework.utils.FuncWeb;
import com.jeelowcode.framework.exception.JeeLowCodeException;
import com.jeelowcode.framework.utils.model.ResultDataModel;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;

@Slf4j
@Tag(name = "低代码框架-报表接口")
@RestController
@AllArgsConstructor
@RequestMapping(JeeLowCodeBaseConstant.REQUEST_URL_START + "/report-data")
public class ReportDataController extends BaseController {

    @PreAuthorize("@ss.hasPermission('jeelowcode:report-data:query:'+#reportCode)")//根据报表编号 来判断是否有权限访问
    @PostMapping({"/list/{reportCode}"})
    @ApiOperationSupport(order = 2)
    @Operation(tags = "报表统计", summary = "获取数据列表 - 单个列表")
    public BaseWebResult getDataList(@PathVariable("reportCode") String reportCode, HttpServletRequest req) {
        Map<String, Object> params = FuncWeb.getParameterBodyMap(req);
        ResultDataModel pages = getReportDataList(reportCode, params);
        return BaseWebResult.success(pages);
    }


    @PreAuthorize("@ss.hasPermission('jeelowcode:report-data:query:'+#reportCodeListStr)")//根据报表编号来判断是否有权限访问
    @PostMapping({"/batch/list/{reportCodeListStr}"})
    @ApiOperationSupport(order = 2)
    @Operation(tags = "报表统计", summary = "获取数据列表 - 多个列表")
    public BaseWebResult getDataPage(@PathVariable("reportCodeListStr") String reportCodeListStr, HttpServletRequest req) {
        Map<String, Object> params = FuncWeb.getParameterBodyMap(req);
        List<String> reportCodeList = Func.toStrList(reportCodeListStr);
        Map<String, ResultDataModel> resultMap = new ConcurrentHashMap<>();
        try {
            Func.jeelowcodeForkJoinPool().submit(() -> reportCodeList.parallelStream().forEach(reportCode -> {
                // 处理代码
                Map<String, Object> subParam = (Map) params.get(reportCode);
                if (Func.isEmpty(subParam)) {
                    subParam = new HashMap<>();
                }
                ResultDataModel pages = getReportDataList(reportCode, subParam);
                resultMap.put(reportCode, pages);
            })).get();
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e.getMessage());
        }
        return BaseWebResult.success(resultMap);
    }


}
