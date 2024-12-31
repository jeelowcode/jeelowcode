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
package com.jeelowcode.module.biz.controller;

import com.jeelowcode.framework.global.JeeLowCodeBaseConstant;
import com.jeelowcode.framework.tenant.annotation.JeeLowCodeTenantIgnore;
import com.jeelowcode.module.biz.service.IDemoService;
import com.jeelowcode.framework.utils.model.global.BaseWebResult;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * @author JX
 * @create 2024-02-26 14:59
 * @dedescription:
 */
@JeeLowCodeTenantIgnore
@Tag(name = "低代码框架 - demo接口")
@RestController
@AllArgsConstructor
@RequestMapping(JeeLowCodeBaseConstant.REQUEST_URL_START +"/demo")
public class DemoController {

    private final IDemoService demoService;



    @GetMapping({"/getData"})
    @ApiOperationSupport(order = 2)
    @Operation(summary = "获取demo数据")
    public BaseWebResult getData() {
        List<Map<String, Object>> dataMapList = demoService.getDemoData();
        return BaseWebResult.success(dataMapList);
    }

}
