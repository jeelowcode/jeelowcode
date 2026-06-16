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

import cn.hutool.json.JSONObject;
import com.jeelowcode.framework.global.JeeLowCodeBaseConstant;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.jeelowcode.core.framework.config.aspect.enhance.model.EnhanceContext;
import com.jeelowcode.core.framework.config.aspect.enhance.model.EnhanceParam;
import com.jeelowcode.core.framework.config.aspect.enhance.model.EnhanceRespModel;
import com.jeelowcode.framework.plus.component.DbManager;
import com.jeelowcode.framework.utils.model.global.BaseWebResult;
import com.jeelowcode.core.framework.entity.DesformEntity;
import com.jeelowcode.core.framework.service.*;
import com.jeelowcode.core.framework.utils.Func;
import com.jeelowcode.core.framework.utils.FuncWeb;
import com.jeelowcode.framework.constants.FrameErrorCodeConstants;
import com.jeelowcode.framework.tenant.annotation.JeeLowCodeTenantIgnore;
import com.jeelowcode.framework.utils.annotation.ApiEncryptAes;
import com.jeelowcode.framework.utils.annotation.JeeLowCodeNoLoginViewDbForm;
import com.jeelowcode.framework.utils.enums.YNEnum;
import com.jeelowcode.framework.utils.model.ResultDataModel;
import com.jeelowcode.framework.utils.utils.FuncBase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @author JX
 * @create 2024-02-26 14:59
 * @dedescription:
 */
@JeeLowCodeTenantIgnore
@Tag(name = "低代码框架 - 开放接口")
@RestController
@AllArgsConstructor
@RequestMapping(JeeLowCodeBaseConstant.REQUEST_URL_START+"/open")
public class OpenController extends BaseController {

    private final IFrameService frameService;

    private final IFormService formService;

    private final IFrameSqlService sqlService;

    private final DbManager dbManager;

    private final IDesFormService desFormService;

    @JeeLowCodeNoLoginViewDbForm
    @PostMapping({"/list/{dbformId}"})
    @ApiOperationSupport(order = 2)
    @Operation(tags = "开放接口",summary = "不登录可以查看 - 获取表数据列表")
    public BaseWebResult getDataList(@PathVariable("dbformId") Long dbformId, HttpServletRequest req) {
        Map<String, Object> params = FuncWeb.getParameterBodyMap(req);
        ResultDataModel resultDataModel = super.getDataPage(dbformId, params);
        return BaseWebResult.success(resultDataModel);
    }

    @JeeLowCodeNoLoginViewDbForm
    @PostMapping({"/detail/{dbformId}/{dataId}"})
    @ApiOperationSupport(order = 4)
    @Operation(tags = "开放接口",summary = "不登录可以查看 - 获取表数据详情")
    public BaseWebResult getDataDetail(@PathVariable("dbformId") Long dbformId, @PathVariable("dataId") Long dataId, HttpServletRequest req) {
        Map<String, Object> params = FuncWeb.getParameterBodyMap(req);
        ResultDataModel resultDataModel = super.getDataDetail(dbformId, dataId, params);
        if (FuncBase.isEmpty(resultDataModel) || FuncBase.isEmpty(resultDataModel.getRecords())) {
            return BaseWebResult.successNull();
        }
        return BaseWebResult.success(resultDataModel.getRecords().get(0));
    }

    @JeeLowCodeNoLoginViewDbForm
    @PostMapping({"/save/{dbformId}"})
    @ApiOperationSupport(order = 5)
    @Operation(tags = "开放接口",summary = "不登录可以查看 - 新增表数据")
    public BaseWebResult addData(@PathVariable("dbformId") Long dbformId, @RequestBody JSONObject jsonObject) {
        String id = super.addJsonData(dbformId, jsonObject);
        return BaseWebResult.success(id);
    }

    @ApiEncryptAes
    @GetMapping({"/desform/get/detail"})
    @ApiOperationSupport(order = 5)
    @Operation(tags = "开放接口",summary = "不登录可以查看 - 获取表单设计配置")
    public BaseWebResult desFormGetDetail(Long desFormId) {
        DesformEntity desformEntity = desFormService.getById(desFormId);
        String isOpen = desformEntity.getIsOpen();
        if(Func.equals(isOpen, YNEnum.N.getCode())){//没有对外访问
            return BaseWebResult.error(FrameErrorCodeConstants.FRAME_DESFORM_IS_OPEN);
        }
        return BaseWebResult.success(desformEntity);
    }

    @PostMapping({"/report/list/{reportCode}"})
    @ApiOperationSupport(order = 2)
    @Operation(tags = "开放接口",summary = "获取数据列表 - 单个")
    public BaseWebResult getDataList(@PathVariable("reportCode") String reportCode,  HttpServletRequest req) {//
        Map<String, Object> params = FuncWeb.getParameterBodyMap(req);
        ResultDataModel pages = getReportDataList(reportCode, params);
        return BaseWebResult.success(pages);
    }

    @PostMapping({"/httpEnhanceTest"})
    @ApiOperationSupport(order = 6)
    @Operation(tags = "开放接口",summary = "测试")
    public EnhanceRespModel httpEnhanceTest(@RequestBody EnhanceContext context) {
        EnhanceParam param = context.getParam();
        Map<String, Object> params = param.getParams();
        String s = FuncBase.json2Str(params);
        System.out.println("params------" + s);
        EnhanceRespModel respModel = new EnhanceRespModel();
        context.setOnlyBefore(true);
        respModel.setData(context);
        respModel.setStatus(200);

        return respModel;
    }

}
