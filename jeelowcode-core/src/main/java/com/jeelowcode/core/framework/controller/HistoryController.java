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
import com.jeelowcode.core.framework.service.IHistoryService;
import com.jeelowcode.core.framework.utils.FuncWeb;
import com.jeelowcode.framework.utils.annotation.ApiEncryptAes;
import com.jeelowcode.framework.utils.constant.JeeLowCodeConstant;
import com.jeelowcode.core.framework.params.vo.PageVo;
import com.jeelowcode.framework.utils.model.global.BaseWebResult;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@Tag(name = "低代码框架-历史接口")
@RestController
@AllArgsConstructor
@RequestMapping(JeeLowCodeBaseConstant.REQUEST_URL_START+"/history")
public class HistoryController extends BaseController {

    private final IHistoryService historyService;

    @PreAuthorize("@ss.hasPermission('jeelowcode:web')")
    @ApiEncryptAes //加密
    @GetMapping("/getDetail")
    @ApiOperationSupport(order = 3)
    @Operation(tags = "历史增强",summary = "获取列表(分页)")
    public BaseWebResult getDetail(Long historyId,String type) {
        switch (type){
            case JeeLowCodeConstant.HISTORY_DESFORM :
                return BaseWebResult.success(historyService.getDesFormInfo(historyId));
            case JeeLowCodeConstant.HISTORY_JS :
                return BaseWebResult.success(historyService.getHistoryJsInfo(historyId));
            case JeeLowCodeConstant.HISTORY_SQL :
                return BaseWebResult.success(historyService.getHistorySqlInfo(historyId));
            case JeeLowCodeConstant.HISTORY_JAVA :
                return BaseWebResult.success(historyService.getHistoryJavaInfo(historyId));
        }

        return BaseWebResult.success("成功");
    }

    @PreAuthorize("@ss.hasPermission('jeelowcode:web')")
    @GetMapping("/page")
    @ApiOperationSupport(order = 3)
    @Operation(tags = "历史增强",summary = "获取列表(分页)")
    public BaseWebResult page(Long id,String type,PageVo pageVo) {
        Page page = FuncWeb.getPage(pageVo.getPageNo(), pageVo.getPageSize());

        switch (type){
            case JeeLowCodeConstant.HISTORY_DESFORM ://表单设计
                return BaseWebResult.success(historyService.getDesFormPages(id, page));
            case JeeLowCodeConstant.HISTORY_JS ://js增强
                return BaseWebResult.success(historyService.getHistoryJsPages(id, page));
            case JeeLowCodeConstant.HISTORY_SQL ://sql增强
                return BaseWebResult.success(historyService.getHistorySqlPages(id, page));
            case JeeLowCodeConstant.HISTORY_JAVA ://java增强
                return BaseWebResult.success(historyService.getHistoryJavaPages(id, page));
        }

        return BaseWebResult.success("成功");
    }


}
