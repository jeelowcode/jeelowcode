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
import com.jeelowcode.framework.utils.model.global.BaseWebResult;
import com.jeelowcode.core.framework.params.DataUniqueParam;
import com.jeelowcode.core.framework.service.IFormService;
import com.jeelowcode.core.framework.service.IFrameSqlService;
import com.jeelowcode.core.framework.utils.Func;
import com.jeelowcode.core.framework.utils.FuncWeb;
import com.jeelowcode.framework.constants.FrameErrorCodeConstants;
import com.jeelowcode.framework.plus.SqlHelper;
import com.jeelowcode.framework.plus.build.buildmodel.wrapper.SqlInfoQueryWrapper;
import com.jeelowcode.framework.utils.enums.JeeLowCodeFieldTypeEnum;
import com.jeelowcode.framework.utils.model.ResultDataModel;
import com.jeelowcode.framework.utils.utils.FuncBase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@Tag(name = "低代码框架-框架接口-数据相关")
@RestController
@AllArgsConstructor
@RequestMapping(JeeLowCodeBaseConstant.REQUEST_URL_START + "/dbform-data")
public class DbFormDataController extends BaseController {

    @Autowired
    private IFormService formService;

    @Autowired
    private IFrameSqlService sqlService;

    @PreAuthorize("@ss.hasPermission('jeelowcode:dbform-data:query:'+#dbformId)")//根据表id来判断是否有权限访问
    @PostMapping({"/list/{dbformId}"})
    @ApiOperationSupport(order = 2)
    @Operation(tags = "表单开发",summary = "获取表数据列表")
    public BaseWebResult getDataList(@PathVariable("dbformId") Long dbformId, HttpServletRequest req) {//id,name  id,age,
        Map<String, Object> params = FuncWeb.getParameterBodyMap(req);
        ResultDataModel resultDataModel = super.getDataPage(dbformId, params);
        return BaseWebResult.success(resultDataModel);
    }


    @PreAuthorize("@ss.hasPermission('jeelowcode:dbform-data:query:'+#dbformId)")//根据表id来判断是否有权限访问
    @PostMapping({"/summary/{dbformId}"})
    @ApiOperationSupport(order = 3)
    @Operation(tags = "表单开发",summary = "获取表数据统计")
    public BaseWebResult getDataSummary(@PathVariable("dbformId") Long dbformId, HttpServletRequest req) {
        Map<String, Object> params = FuncWeb.getParameterBodyMap(req);
        Map map = super.getSummaryData(dbformId, params);
        return BaseWebResult.success(map);
    }

    @PreAuthorize("@ss.hasPermission('jeelowcode:dbform-data:query:'+#dbformId)")//根据表id来判断是否有权限访问
    @PostMapping({"/detail/{dbformId}/{dataId}"})
    @ApiOperationSupport(order = 4)
    @Operation(tags = "表单开发",summary = "获取表数据详情")
    public BaseWebResult getDataDetail(@PathVariable("dbformId") Long dbformId, @PathVariable("dataId") Long dataId, HttpServletRequest req) {
        Map<String, Object> params = FuncWeb.getParameterBodyMap(req);
        ResultDataModel resultDataModel = super.getDataDetail(dbformId, dataId, params);

        if (FuncBase.isEmpty(resultDataModel) || FuncBase.isEmpty(resultDataModel.getRecords())) {
            return BaseWebResult.successNull();
        }
        return BaseWebResult.success(resultDataModel.getRecords().get(0));
    }


    @PreAuthorize("@ss.hasPermission('jeelowcode:dbform-data:create:'+#dbformId)")//根据表id来判断是否有权限访问
    @PostMapping({"/save/{dbformId}"})
    @ApiOperationSupport(order = 5)
    @Operation(tags = "表单开发",summary = "新增表数据")
    public BaseWebResult addData(@PathVariable("dbformId") Long dbformId, @RequestBody JSONObject jsonObject) {
        String id = super.addJsonData(dbformId, jsonObject);
        return BaseWebResult.success(id);
    }

    @PreAuthorize("@ss.hasPermission('jeelowcode:dbform-data:create:'+#dbformId)")//根据表id来判断是否有权限访问
    @PostMapping({"/save/batch/{dbformId}"})
    @ApiOperationSupport(order = 5)
    @Operation(tags = "表单开发",summary = "新增表数据-批量新增")
    public BaseWebResult addBatchData(@PathVariable("dbformId") Long dbformId, @RequestBody List<JSONObject> jsonList) {
        List<String> idList = super.addJsonData(dbformId, jsonList);
        return BaseWebResult.success(idList);
    }

    @PreAuthorize("@ss.hasPermission('jeelowcode:dbform-data:update:'+#dbformId)")//根据表id来判断是否有权限访问
    @PutMapping({"/edit/{dbformId}"})
    @ApiOperationSupport(order = 6)
    @Operation(tags = "表单开发",summary = "编辑表数据")
    public BaseWebResult editData(@PathVariable("dbformId") Long dbformId, @RequestBody JSONObject jsonObject) {
        super.editJsonData(dbformId,jsonObject);
        return BaseWebResult.success("成功");
    }

    @PreAuthorize("@ss.hasPermission('jeelowcode:dbform-data:update:'+#dbformId)")//根据表id来判断是否有权限访问
    @PutMapping({"/edit/batch/{dbformId}"})
    @ApiOperationSupport(order = 6)
    @Operation(tags = "表单开发",summary = "编辑表数据-批量")
    public BaseWebResult editBatchData(@PathVariable("dbformId") Long dbformId, @RequestBody List<JSONObject> jsonObjectList) {
        super.editJsonData(dbformId,jsonObjectList);
        return BaseWebResult.success("成功");
    }

    @PreAuthorize("@ss.hasPermission('jeelowcode:dbform-data:delete:'+#dbformId)")//根据表id来判断是否有权限访问
    @DeleteMapping({"/delete/{dbformId}"})
    @ApiOperationSupport(order = 7)
    @Operation(tags = "表单开发",summary = "根据id删除数据")
    public BaseWebResult delData(@PathVariable("dbformId") Long dbformId, @RequestBody List<Long> dataIdList) {
        super.deleteDataById(dbformId,dataIdList);
        return BaseWebResult.success("成功");
    }

    @PostMapping({"/unique/{dbformId}"})
    @ApiOperationSupport(order = 4)
    @Operation(tags = "表单开发",summary = "校验数据是否唯一")
    public BaseWebResult dataUnique(@PathVariable("dbformId") Long dbformId,@RequestBody DataUniqueParam param) {
        String fieldCode = param.getFieldCode();
        Long dataId = param.getDataId();
        String fieldVal = param.getFieldVal();
        if(Func.isEmpty(fieldCode) || Func.isEmpty(fieldVal)){
            return BaseWebResult.error(FrameErrorCodeConstants.FRAME_PARAM_NULL_ERROR);
        }

        //表名称
        String tableName = formService.getTableName(dbformId);
        boolean isExist = formService.fieldCodeIsExist(dbformId, fieldCode);
        if(!isExist){
            return BaseWebResult.success(true);
        }
        JeeLowCodeFieldTypeEnum fieldTypeEnum = formService.getFieldTypeEnum(dbformId, fieldCode);

        SqlInfoQueryWrapper.Wrapper wrapper = SqlHelper.getQueryWrapper()
                .setTableName(tableName)
                .select("id")
                .setWhere(where -> {
                    where.eq(fieldCode, Func.getfieldValObj(fieldTypeEnum,fieldVal));
                    where.ne("id", dataId);
                })
                .build();
        List<Map<String, Object>> dataMapList = sqlService.getDataListByPlus(wrapper);
        return BaseWebResult.success(Func.isNotEmpty(dataMapList));
    }

}
