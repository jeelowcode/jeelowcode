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
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jeelowcode.core.framework.config.btncommand.param.ButtonParamImport;
import com.jeelowcode.core.framework.entity.ExcelFileDataEntity;
import com.jeelowcode.core.framework.entity.ExcelFileEntity;
import com.jeelowcode.core.framework.params.UpdateFileDataParam;
import com.jeelowcode.core.framework.params.vo.PageVo;
import com.jeelowcode.core.framework.service.*;
import com.jeelowcode.core.framework.utils.Func;
import com.jeelowcode.core.framework.utils.FuncWeb;
import com.jeelowcode.framework.constants.FrameErrorCodeConstants;
import com.jeelowcode.framework.excel.JeeLowCodeExcelUtils;
import com.jeelowcode.framework.excel.model.ExcelTitleModel;
import com.jeelowcode.framework.exception.JeeLowCodeException;
import com.jeelowcode.framework.utils.adapter.IJeeLowCodeAdapter;
import com.jeelowcode.framework.utils.component.redis.JeeLowCodeRedisUtils;
import com.jeelowcode.framework.utils.model.global.BaseWebResult;
import com.jeelowcode.core.framework.params.model.ExcelImportResultModel;
import com.jeelowcode.core.framework.params.model.ExcelModel;
import com.jeelowcode.core.framework.params.model.ExcelTemplateModel;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.jeelowcode.framework.utils.enums.YNEnum;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.apache.commons.collections4.map.LinkedMap;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.util.*;
import java.util.concurrent.TimeUnit;

@Tag(name = "低代码框架-框架接口-Excel相关")
@RestController
@AllArgsConstructor
@RequestMapping(JeeLowCodeBaseConstant.REQUEST_URL_START + "/excel")
public class ExcelController extends BaseController {

    private final IFormService formService;

    private final IExcelFileDataService excelDataService;

    private final IExcelFileService excelFileService;

    private final IExcelService excelService;

    private final JeeLowCodeRedisUtils jeeLowCodeRedisUtils;

    private final IJeeLowCodeAdapter jeeLowCodeAdapter;

    private final IJeeLowCodeConfigService jeeLowCodeConfigService;

    @PreAuthorize("@ss.hasPermission('jeelowcode:dbform-data:import:'+#dbformId)")
    @ApiOperationSupport(order = 1)
    @Operation(tags = "Excel模块",summary = "导出Excel模板")
    @GetMapping({"/exportExcelTemplate/{dbformId}"})
    public void exportExcelTemplate(@PathVariable("dbformId") Long dbformId, HttpServletResponse rsp) {
        ExcelTemplateModel excelTemplateModel = super.exportExcelTemplate(dbformId);
        //导出
        JeeLowCodeExcelUtils.exportExcel(rsp, excelTemplateModel.getSheetName(), excelTemplateModel.getHeadTitleMap(), excelTemplateModel.getDataList());
    }

    @PreAuthorize("@ss.hasPermission('jeelowcode:dbform-data:export:'+#dbformId)")
    @ApiOperationSupport(order = 2)
    @Operation(tags = "Excel模块",summary = "导出Excel数据")
    @PostMapping({"/exportExcel/{dbformId}"})
    public void exportExcelData(@PathVariable("dbformId") Long dbformId, HttpServletRequest req, HttpServletResponse rsp) {
        Map<String, Object> params = FuncWeb.getParameterBodyMap(req);
        ExcelModel excelModel = super.exportExcelData(dbformId, params);
        //导出
        JeeLowCodeExcelUtils.exportExcel(rsp, excelModel.getSheetName(), excelModel.getHeadTitleMap(), excelModel.getDataMapList());
    }

    @PreAuthorize("@ss.hasPermission('jeelowcode:dbform-data:import:'+#dbformId)")
    @ApiOperationSupport(order = 3)
    @Operation(tags = "Excel模块",summary = "导入Excel数据")
    @PostMapping({"/viewExcel/{dbformId}"})
    public BaseWebResult viewExcelData(@PathVariable("dbformId") Long dbformId, @RequestParam MultipartFile file) {
        try {
            String fileName = file.getOriginalFilename();
            InputStream inputStream = file.getInputStream();
            //获取所有key
            Map<String, String> fieldNameAndCodeMap = formService.getFieldNameAndCode(dbformId);

            List<Map<String, Object>> dataMapList = JeeLowCodeExcelUtils.importExcel(inputStream, 1, fieldNameAndCodeMap);
            if (Func.isEmpty(dataMapList)) {
                return BaseWebResult.error(FrameErrorCodeConstants.FRAME_IMPORT_EXCEL);
            }
            //封装参数
            ButtonParamImport buttonParam = new ButtonParamImport();
            buttonParam.setDbFormId(dbformId);
            buttonParam.setDataMapList(dataMapList);
            buttonParam.setFileName(fileName);//文件名称
            buttonParam.setServiceType("IMPORT_TEMPLATE");//业务类型

            ExcelImportResultModel resultModel = super.importExcelData(buttonParam);
            return BaseWebResult.success(resultModel);
        } catch (Exception e) {
            throw new JeeLowCodeException("导入失败");
        }
    }

    @PreAuthorize("@ss.hasPermission('jeelowcode:dbform-data:import:'+#dbformId)")
    @ApiOperationSupport(order = 4)
    @Operation(tags = "Excel模块",summary = "查询导入进度")
    @GetMapping({"/viewProgress/{dbformId}"})
    public BaseWebResult viewProgress(@PathVariable("dbformId") Long dbformId, String batchCode) {
        //因为走事务控制，只需要查是否存在即可
        ExcelFileEntity excelFileEntity = excelFileService.getById(Func.toLong(batchCode));
        return BaseWebResult.success(Func.isNotEmpty(excelFileEntity));
    }


    @PreAuthorize("@ss.hasPermission('jeelowcode:dbform-data:import:'+#dbformId)")
    @ApiOperationSupport(order = 4)
    @Operation(tags = "Excel模块",summary = "查询导入进度")
    @GetMapping({"/importProgress/{dbformId}"})
    public BaseWebResult importProgress(@PathVariable("dbformId") Long dbformId, String batchCode, String handleResult) {
        String redisKeyData = "";
        if (Func.isEmpty(handleResult)) {//总数
            redisKeyData = "excel:import_data:task_" + batchCode + ":*";
        } else {//成功或者失败数据
            redisKeyData = "excel:import_data:task_" + batchCode + ":" + handleResult + ":";
        }
        Set<String> keys = jeeLowCodeRedisUtils.keys(redisKeyData);
        if (Func.isEmpty(keys)) {
            return BaseWebResult.success(0);
        } else {
            return BaseWebResult.success(keys.size());
        }
    }

    @PreAuthorize("@ss.hasPermission('jeelowcode:dbform-data:import:'+#dbformId)")
    @ApiOperationSupport(order = 5)
    @Operation(tags = "Excel模块",summary = "操作 恢复/暂停/取消任务")
    @GetMapping({"/importOpTask/{dbformId}"})
    public BaseWebResult importOpTask(@PathVariable("dbformId") Long dbformId, Long batchCode, String taskState) {
        ServletRequestAttributes sra = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();


        if (Func.equals(taskState, "START_TASK")) {//开启
            excelService.handleTempTable(sra,dbformId, batchCode);
        } else {//暂停 取消
            String redisKey = "excel:import:task_" + batchCode;
            jeeLowCodeRedisUtils.set(redisKey, taskState, 2, TimeUnit.DAYS);//存2天
        }

        return BaseWebResult.success("成功");
    }

    @PreAuthorize("@ss.hasPermission('jeelowcode:dbform-data:import:'+#dbformId)")
    @ApiOperationSupport(order = 6)
    @Operation(tags = "Excel模块",summary = "下载错误数据数据")
    @GetMapping({"/downloadErrorExcel/{dbformId}"})
    public void downloadErrorExcel(@PathVariable("dbformId") Long dbformId, Long batchCode, HttpServletResponse rsp) {
        //获取失败数据
        LambdaQueryWrapper<ExcelFileDataEntity> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(ExcelFileDataEntity::getExcelFileId, batchCode);
        wrapper.eq(ExcelFileDataEntity::getHandleState, YNEnum.Y.getCode());
        wrapper.eq(ExcelFileDataEntity::getHandleResult, "FAIL");
        wrapper.orderByAsc(ExcelFileDataEntity::getSort);
        List<ExcelFileDataEntity> entityList = excelDataService.list(wrapper);

        List<Map<String, Object>> dataMapList = new ArrayList<>();
        for (ExcelFileDataEntity entity : entityList) {
            String dataJson = entity.getDataJson();
            Map<String, Object> dataMap = Func.json2Bean(dataJson, Map.class);
            dataMap.put("error_reason", entity.getErrorReason());
            dataMapList.add(dataMap);
        }


        ExcelTemplateModel excelTemplateModel = super.exportExcelTemplate(dbformId);
        LinkedMap<String, ExcelTitleModel> headTitleMap = excelTemplateModel.getHeadTitleMap();
        //加入错误原因
        ExcelTitleModel titleModel = new ExcelTitleModel();
        titleModel.setTitle("错误原因");
        headTitleMap.put("error_reason", titleModel);

        //导出
        JeeLowCodeExcelUtils.exportExcel(rsp, excelTemplateModel.getSheetName(), headTitleMap, dataMapList);

    }

    @PreAuthorize("@ss.hasPermission('jeelowcode:dbform-data:import:'+#dbformId)")
    @PostMapping("/run/import/{dbformId}")
    @ApiOperationSupport(order = 7)
    @Operation(tags = "Excel模块",summary = "开始导入")
    public BaseWebResult runImport(@PathVariable("dbformId") Long dbformId, Long batchCode) {
        try {
            //封装参数
            ButtonParamImport buttonParam = new ButtonParamImport();
            buttonParam.setDbFormId(dbformId);
            buttonParam.setBatchCode(batchCode);
            buttonParam.setServiceType("HANBLE_TEMPLATE");//业务类型
            super.importExcelData(buttonParam);
        } catch (Exception e) {
            throw new JeeLowCodeException("导入失败");
        }
        return BaseWebResult.success("成功");
    }

    @PreAuthorize("@ss.hasPermission('jeelowcode:dbform-data:import:'+#dbformId)")
    @PostMapping("/view/page/{dbformId}")
    @ApiOperationSupport(order = 7)
    @Operation(tags = "Excel模块",summary = "数据预览(分页)")
    public BaseWebResult page(@PathVariable("dbformId") Long dbformId, String handleResult,Long batchCode, PageVo pageVo) {
        Page page = FuncWeb.getPage(pageVo.getPageNo(), pageVo.getPageSize());
        LambdaQueryWrapper<ExcelFileDataEntity> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(ExcelFileDataEntity::getExcelFileId, batchCode);//batchCode就是id
        wrapper.eq(Func.isNotEmpty(handleResult),ExcelFileDataEntity::getHandleResult,handleResult);
        wrapper.orderByAsc(ExcelFileDataEntity::getSort);
        IPage<ExcelFileDataEntity> pages = excelDataService.page(page, wrapper);
        return BaseWebResult.success(pages);
    }

    @PreAuthorize("@ss.hasPermission('jeelowcode:dbform-data:import:'+#dbformId)")
    @PostMapping("/update/fileData/{dbformId}")
    @ApiOperationSupport(order = 7)
    @Operation(tags = "Excel模块",summary = "修改值")
    public BaseWebResult updateFileData(@PathVariable("dbformId") Long dbformId, @RequestBody UpdateFileDataParam param) {
        ExcelFileDataEntity updateEntity = new ExcelFileDataEntity();
        updateEntity.setId(param.getId());
        updateEntity.setDataJson(param.getJsonStr());
        excelDataService.updateById(updateEntity);
        return BaseWebResult.success("成功");
    }

    @PreAuthorize("@ss.hasPermission('jeelowcode:dbform-data:import:'+#dbformId)")
    @PostMapping("/del/fileData/{dbformId}")
    @ApiOperationSupport(order = 7)
    @Operation(tags = "Excel模块",summary = "删除值")
    public BaseWebResult delFileData(@PathVariable("dbformId") Long dbformId, @RequestBody UpdateFileDataParam param) {
        excelDataService.delById(param.getId());
        return BaseWebResult.success("成功");
    }


    @PreAuthorize("@ss.hasPermission('jeelowcode:dbform-data:import:'+#dbformId)")
    @PostMapping("/file/list/{dbformId}")
    @ApiOperationSupport(order = 7)
    @Operation(tags = "Excel模块",summary = "获取导入列表")
    public BaseWebResult fileList(@PathVariable("dbformId") Long dbformId, PageVo pageVo) {
        Long tenantId = jeeLowCodeAdapter.getTenantId();
        Integer excelFileDataDay = jeeLowCodeConfigService.getExcelFileDataDay();

        Page page = FuncWeb.getPage(pageVo.getPageNo(), pageVo.getPageSize());

        LambdaQueryWrapper<ExcelFileEntity> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(ExcelFileEntity::getDbformId, dbformId);
        wrapper.eq(ExcelFileEntity::getTenantId, tenantId);
        wrapper.orderByDesc(ExcelFileEntity::getId);
        Page pages = excelFileService.page(page, wrapper);
        List<ExcelFileEntity> records = pages.getRecords();
        if (Func.isNotEmpty(records)) {
            for (ExcelFileEntity fileEntity : records) {
                Long fileId = fileEntity.getId();//文件id
                //判断是否有数据
                LambdaQueryWrapper<ExcelFileDataEntity> wrapperData = new LambdaQueryWrapper<>();
                wrapperData.eq(ExcelFileDataEntity::getExcelFileId, fileId);
                long count = excelDataService.count(wrapperData);
                fileEntity.setViewFlag(count > 0);
            }
        }
        Map<String,Object> resultMap=new HashMap<>();
        resultMap.put("day",excelFileDataDay);
        resultMap.put("pages",pages);
        return BaseWebResult.success(resultMap);
    }

    @PreAuthorize("@ss.hasPermission('jeelowcode:dbform-data:import:'+#dbformId)")
    @PostMapping("/rollback/{dbformId}")
    @ApiOperationSupport(order = 7)
    @Operation(tags = "Excel模块",summary = "撤回(只允许本人撤销)")
    public BaseWebResult fileList(@PathVariable("dbformId") Long dbformId, Long batchCode) {
        LambdaQueryWrapper<ExcelFileEntity> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(ExcelFileEntity::getDbformId, dbformId);
        wrapper.eq(ExcelFileEntity::getId, batchCode);
        wrapper.eq(ExcelFileEntity::getCreateUser, jeeLowCodeAdapter.getOnlineUserId());
        ExcelFileEntity excelFileEntity = excelFileService.getOne(wrapper);
        if (Func.isEmpty(excelFileEntity)) {
            return BaseWebResult.error(FrameErrorCodeConstants.FRAME_IMPORT_EXCEL_NOT_DATA);
        }
        String importState = excelFileEntity.getImportState();
        if (Func.isNotEmpty(importState) && Func.notEquals(importState, "1")) {
            return BaseWebResult.error(FrameErrorCodeConstants.FRAME_IMPORT_EXCEL_STATE_ERROR);
        }
        //进行撤回
        excelFileService.rollback(excelFileEntity.getId());

        return BaseWebResult.success("成功");
    }

    @PreAuthorize("@ss.hasPermission('jeelowcode:report-data:export:'+#reportCode)")
    @ApiOperationSupport(order = 2)
    @Operation(tags = "Excel模块",summary = "导出报表数据")
    @PostMapping({"/exportReport/{reportCode}"})
    public void exportReportData(@PathVariable("reportCode") String reportCode, HttpServletRequest req, HttpServletResponse rsp) {
        Map<String, Object> params = FuncWeb.getParameterBodyMap(req);
        ExcelModel excelModel = super.exportReportData(reportCode, params);
        //导出
        JeeLowCodeExcelUtils.exportExcel(rsp, excelModel.getSheetName(), excelModel.getHeadTitleMap(), excelModel.getDataMapList());
    }
}
