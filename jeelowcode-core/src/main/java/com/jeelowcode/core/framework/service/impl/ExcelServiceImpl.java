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
package com.jeelowcode.core.framework.service.impl;

import cn.hutool.core.date.DateUtil;
import com.jeelowcode.core.framework.entity.*;
import com.jeelowcode.core.framework.mapper.JeeLowCodeMapper;
import com.jeelowcode.core.framework.mapper.ReportFieldMapper;
import com.jeelowcode.core.framework.params.SaveImportDataParam;
import com.jeelowcode.core.framework.service.*;
import com.jeelowcode.core.framework.utils.Func;
import com.jeelowcode.framework.excel.model.ExcelTitleModel;
import com.jeelowcode.framework.exception.JeeLowCodeException;
import com.jeelowcode.framework.utils.component.redis.JeeLowCodeRedisUtils;
import com.jeelowcode.framework.utils.enums.YNEnum;
import com.jeelowcode.framework.utils.model.ExecuteEnhanceModel;
import com.jeelowcode.framework.utils.tool.spring.SpringUtils;
import com.jeelowcode.framework.utils.utils.FuncBase;
import com.jeelowcode.framework.utils.utils.JeeLowCodeUtils;
import com.jeelowcode.core.framework.params.model.ExcelImportDataDictModel;
import com.jeelowcode.core.framework.params.model.ExcelModel;
import com.jeelowcode.core.framework.params.model.ExcelTemplateModel;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.map.LinkedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

/**
 *
 */
@Slf4j
@Service
public class ExcelServiceImpl implements IExcelService {

    @Autowired
    private IFormService formService;

    @Autowired
    private JeeLowCodeMapper jeeLowCodeMapper;

    @Autowired
    private IFrameService frameService;

    @Autowired
    private IExcelFileDataService excelDataService;

    @Autowired
    private JeeLowCodeRedisUtils jeeLowCodeRedisUtils;

    @Autowired
    private IExcelFileService excelFileService;

    @Autowired
    private IReportService reportService;

    @Autowired
    private ReportFieldMapper reportFieldMapper;

    /**
     * 导出Excel模版
     *
     * @param dbformId
     */
    @Override
    public ExcelTemplateModel getExportExcelTemplate(Long dbformId) {
        FormEntity formEntity = formService.getById(dbformId);
        String sheetName = formEntity.getTableDescribe();//表描述

        List<Map<String, Object>> excelFieldList = jeeLowCodeMapper.getExcelFieldList(dbformId);

        //Excel头部
        LinkedMap<String, ExcelTitleModel> headMap = new LinkedMap<>();

        //Excel内容
        List<Map<String, Object>> dataList = new ArrayList<>();

        Map<String, Object> excelDataMap = new HashMap<>();

        for (Map<String, Object> dataMap : excelFieldList) {
            String field_code = JeeLowCodeUtils.getMap2Str(dataMap, "field_code");
            String field_name = JeeLowCodeUtils.getMap2Str(dataMap, "field_name");
            String import_example_txt = JeeLowCodeUtils.getMap2Str(dataMap, "import_example_txt");

            //封装头部
            ExcelTitleModel titleModel = new ExcelTitleModel();
            titleModel.setTitle(field_name);
            //处理字典表
            Map<String, String> dictMap = formService.getDictKeyLabelMap(dbformId, field_code);
            List<String> dictLabelList = dictMap.values().stream()
                    .collect(Collectors.toList());
            titleModel.setDropdownOptionList(dictLabelList);

            //封装头部
            headMap.put(field_code, titleModel);

            //封装数据内容
            excelDataMap.put(field_code, import_example_txt);
        }
        dataList.add(excelDataMap);

        ExcelTemplateModel resultModel = new ExcelTemplateModel();
        resultModel.setSheetName(sheetName);
        resultModel.setHeadTitleMap(headMap);
        resultModel.setDataList(dataList);
        return resultModel;

    }

    /**
     * 获取导出基本信息
     *
     * @param dbformId
     */
    @Override
    public ExcelModel getExcelModel(Long dbformId) {
        FormEntity formEntity = formService.getById(dbformId);
        String sheetName = formEntity.getTableDescribe();//表描述

        List<Map<String, Object>> excelFieldList = jeeLowCodeMapper.getExcelFieldList(dbformId);

        //Excel头部
        LinkedMap<String, ExcelTitleModel> headMap = new LinkedMap<>();

        for (Map<String, Object> dataMap : excelFieldList) {
            String field_code = JeeLowCodeUtils.getMap2Str(dataMap, "field_code");
            String field_name = JeeLowCodeUtils.getMap2Str(dataMap, "field_name");
            //封装头部
            ExcelTitleModel titleModel = new ExcelTitleModel();
            titleModel.setTitle(field_name);
            //处理字典表
            Map<String, String> dictMap = formService.getDictKeyLabelMap(dbformId, field_code);
            if (Func.isNotEmpty(dictMap) && dictMap.size() <= 20) {
                List<String> dictLabelList = dictMap.values().stream()
                        .collect(Collectors.toList());
                titleModel.setDropdownOptionList(dictLabelList);
            }
            headMap.put(field_code, titleModel);
        }

        ExcelModel resultModel = new ExcelModel();
        resultModel.setSheetName(sheetName);
        resultModel.setHeadTitleMap(headMap);
        return resultModel;
    }

    /**
     * 获取导出基本信息
     *
     */
    @Override
    public ExcelModel getExcelReportModel(String reportCode) {
        ReportEntity reportEntity = reportService.getReportEntityByCode(reportCode);

        String sheetName = reportEntity.getReportName();//表描述

        LambdaQueryWrapper<ReportFieldEntity> wrapper=new LambdaQueryWrapper<>();
        wrapper.eq(ReportFieldEntity::getReportId,reportEntity.getId());
        wrapper.eq(ReportFieldEntity::getIsExport, YNEnum.Y.getCode());
        List<ReportFieldEntity> fieldEntityList = reportFieldMapper.selectList(wrapper);

        //Excel头部
        LinkedMap<String, ExcelTitleModel> headMap = new LinkedMap<>();

        for (ReportFieldEntity fieldEntity : fieldEntityList) {
            String fieldCode = fieldEntity.getFieldCode();
            String fieldName = fieldEntity.getFieldName();

            //封装头部
            ExcelTitleModel titleModel = new ExcelTitleModel();
            titleModel.setTitle(fieldName);
            headMap.put(fieldCode, titleModel);
        }

        ExcelModel resultModel = new ExcelModel();
        resultModel.setSheetName(sheetName);
        resultModel.setHeadTitleMap(headMap);
        return resultModel;
    }


    /**
     * 处理临时库的数据
     *
     * @param fieldId
     */
    @Async("asyncPoolTaskExecutor")
    public void handleTempTable(ServletRequestAttributes sra, Long dbFormId, Long fieldId) {
        RequestContextHolder.setRequestAttributes(sra, true);
        //获取未处理的记录
        LambdaQueryWrapper<ExcelFileDataEntity> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(ExcelFileDataEntity::getExcelFileId, fieldId);
        wrapper.eq(ExcelFileDataEntity::getHandleState, YNEnum.N.getCode());//未处理
        wrapper.orderByAsc(ExcelFileDataEntity::getSort);
        List<ExcelFileDataEntity> entityList = excelDataService.list(wrapper);
        if (Func.isEmpty(entityList)) {
            return;
        }
        //获取字典
        ExcelImportDataDictModel dictModel = this.formatExcelImportDataList(dbFormId);
        //处理导入
        SaveImportDataParam param=new SaveImportDataParam();
        param.setFieldId(fieldId);
        param.setEntityList(entityList);
        param.setDictModel(dictModel);
        frameService.saveImportData(dbFormId,param);
    }


    //获取字典
    private ExcelImportDataDictModel formatExcelImportDataList(Long dbformId) {

        List<FormFieldEntity> fieldList = formService.getDbFieldList(dbformId);
        //字典集合
        Map<String, Map<String, String>> dictMaps = new HashMap<>();
        List<String> keyList = new ArrayList<>();
        for (FormFieldEntity formFieldEntity : fieldList) {
            String fieldCode = formFieldEntity.getFieldCode();
            Map<String, String> dictMap = formService.getDictKeyLabelMap(dbformId, fieldCode);//字典列表 男：1
            if (FuncBase.isEmpty(dictMap)) {
                continue;
            }
            //进行反转 key=男  value =1
            Map<String, String> labelKeyMap = new HashMap<>();
            for (Map.Entry<String, String> entry : dictMap.entrySet()) {
                String key = entry.getKey();
                String value = entry.getValue();
                labelKeyMap.put(value, key);
            }
            dictMaps.put(fieldCode, labelKeyMap);
            keyList.add(fieldCode);
        }
        ExcelImportDataDictModel dictModel = new ExcelImportDataDictModel();
        dictModel.setDictMaps(dictMaps);
        dictModel.setKeyList(keyList);
        return dictModel;

    }

    //处理字典
    private void handleDict(ExcelImportDataDictModel dictModel, Map<String, Object> dataMap) {
        if (Func.isEmpty(dictModel)) {
            return;
        }
        Map<String, Map<String, String>> dictMaps = dictModel.getDictMaps();
        List<String> keyList = dictModel.getKeyList();
        if (Func.isEmpty(keyList)) {
            return;
        }

        for (String key : keyList) {
            String val = JeeLowCodeUtils.getMap2Str(dataMap, key);//值 男
            if (FuncBase.isEmpty(val)) {
                continue;
            }
            Map<String, String> valMap = dictMaps.get(key);// 男-》1
            String valStr = valMap.get(val);
            if (FuncBase.isEmpty(valStr)) {
                continue;
            }
            dataMap.put(key, valStr);
        }
    }
}
