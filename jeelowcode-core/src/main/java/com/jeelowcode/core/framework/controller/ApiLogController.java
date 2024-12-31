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

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.jeelowcode.core.framework.entity.LogRequestApiEntity;
import com.jeelowcode.core.framework.entity.LogRequestErrorApiEntity;
import com.jeelowcode.core.framework.params.LogRequestApiParam;
import com.jeelowcode.core.framework.params.vo.PageVo;
import com.jeelowcode.core.framework.service.IApiLogService;
import com.jeelowcode.core.framework.utils.Func;
import com.jeelowcode.core.framework.utils.FuncWeb;
import com.jeelowcode.framework.global.JeeLowCodeBaseConstant;
import com.jeelowcode.framework.utils.model.global.BaseWebResult;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@Tag(name = "低代码框架-API请求接口")
@RestController
@AllArgsConstructor
@RequestMapping(JeeLowCodeBaseConstant.REQUEST_URL_START + "/apilog")
public class ApiLogController extends BaseController {

    private final IApiLogService apiLogService;

    @PreAuthorize("@ss.hasPermission('api:log')")
    @GetMapping("/detail")
    @ApiOperationSupport(order = 1)
    @Operation(tags = "审计日志",summary = "增强-详情")
    public BaseWebResult getDetail(LogRequestApiParam param) {
        String logType = param.getLogType();
        if (Func.equals(logType, "error")) {
            LogRequestErrorApiEntity detailEntity = apiLogService.getErrorApiLogDetail(param.getId());
            return BaseWebResult.success(detailEntity);
        }
        LogRequestApiEntity detailEntity = apiLogService.getInfoApiLogDetail(param.getId());
        return BaseWebResult.success(detailEntity);

    }


    @PreAuthorize("@ss.hasPermission('api:log')")
    @DeleteMapping("/delete")
    @ApiOperationSupport(order = 3)
    @Operation(tags = "审计日志",summary = "删除（真实删除）")
    public BaseWebResult del(LogRequestApiParam param) {
        DateTime delDateTime = DateUtil.parse(param.getDelDate(), "yyyy-MM-dd");
        String logType = param.getLogType();
        if (Func.equals(logType, "error")) {
            apiLogService.clearApiErrorLog(delDateTime);
            return BaseWebResult.success("成功");
        }
        apiLogService.clearApiLog(delDateTime);
        return BaseWebResult.success("成功");
    }




    @PreAuthorize("@ss.hasPermission('api:log')")
    @GetMapping("/page")
    @ApiOperationSupport(order = 3)
    @Operation(tags = "审计日志",summary = "获取列表(分页)")
    public BaseWebResult page(LogRequestApiParam param, PageVo pageVo) {
        Page page = FuncWeb.getPage(pageVo.getPageNo(), pageVo.getPageSize());
        String logType = param.getLogType();
        if (Func.equals(logType, "error")) {
            IPage<LogRequestErrorApiEntity> pages = apiLogService.getErrorApiLogPage(param,page);
            return BaseWebResult.success(pages);
        }
        IPage<LogRequestApiEntity> pages = apiLogService.getInfoApiLogPage(param,page);
        return BaseWebResult.success(pages);
    }


}
