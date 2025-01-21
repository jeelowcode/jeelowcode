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


import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.jeelowcode.framework.plus.component.DbManager;
import com.jeelowcode.core.framework.entity.*;
import com.jeelowcode.core.framework.mapper.FormFieldForeignkeyMapper;
import com.jeelowcode.core.framework.mapper.FormRoleDataRuleMapper;
import com.jeelowcode.core.framework.mapper.JeeLowCodeMapper;
import com.jeelowcode.core.framework.params.SaveImportDataParam;
import com.jeelowcode.core.framework.params.model.DataSourceConfigModel;
import com.jeelowcode.core.framework.params.model.DbFieldCodeListModel;
import com.jeelowcode.core.framework.params.model.ExcelImportDataDictModel;
import com.jeelowcode.core.framework.params.vo.SummaryTopModel;
import com.jeelowcode.core.framework.params.vo.role.DbFormRoleDataRuleVo;
import com.jeelowcode.core.framework.service.*;
import com.jeelowcode.core.framework.utils.Func;
import com.jeelowcode.framework.exception.JeeLowCodeException;
import com.jeelowcode.framework.plus.SqlHelper;
import com.jeelowcode.framework.plus.build.buildmodel.wrapper.SqlInfoQueryWrapper;
import com.jeelowcode.framework.plus.build.buildmodel.wrapper.SqlInfoUpdateWrapper;
import com.jeelowcode.framework.plus.entity.FieldModel;
import com.jeelowcode.framework.plus.core.model.SqlFormatModel;
import com.jeelowcode.framework.plus.core.model.OrderByModel;
import com.jeelowcode.framework.plus.core.model.WhereModel;
import com.jeelowcode.framework.tenant.annotation.JeeLowCodeTenantIgnore;
import com.jeelowcode.framework.utils.adapter.IJeeLowCodeAdapter;
import com.jeelowcode.framework.utils.component.redis.JeeLowCodeRedisUtils;
import com.jeelowcode.framework.utils.constant.JeeLowCodeConstant;
import com.jeelowcode.framework.utils.enums.*;
import com.jeelowcode.framework.utils.model.ExecuteEnhanceModel;
import com.jeelowcode.framework.utils.model.ResultDataModel;
import com.jeelowcode.framework.utils.tool.spring.SpringUtils;
import com.jeelowcode.framework.utils.utils.FuncBase;
import com.jeelowcode.framework.utils.utils.JeeLowCodeUtils;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import net.sf.jsqlparser.JSQLParserException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 低代码平台
 * 该接口下所有业务不用租户过滤
 */
@Slf4j
@Service
public class FrameServiceImpl implements IFrameService {

    @Autowired
    private IFrameSqlService sqlService;

    @Autowired
    private JeeLowCodeMapper jeeLowCodeMapper;

    @Autowired
    private IFormService dbFormService;

    @Autowired
    private DbManager dbManager;

    @Autowired
    private FormFieldForeignkeyMapper fieldForeignkeyMapper;

    @Autowired
    private IJeeLowCodeAdapter jeeLowCodeAdapter;

    @Autowired
    private IDbFormRoleService dbFormRoleService;

    @Autowired
    private FormRoleDataRuleMapper roleDataRuleMapper;


    @Autowired
    private JeeLowCodeRedisUtils jeeLowCodeRedisUtils;

    @Autowired
    private IExcelFileDataService excelDataService;

    @Autowired
    private IExcelFileService excelFileService;

    @Autowired
    private IJeeLowCodeAdapter proxyAdapter;

    @Autowired
    private IReportService reportService;


    //校验表是否存在 true=存在  false=不存在
    @JeeLowCodeTenantIgnore
    @Override
    public boolean checkTable(String tableName) {
        //判断head表是否存在
        LambdaQueryWrapper<FormEntity> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(FormEntity::getTableName, tableName);
        wrapper.select(FormEntity::getId);
        List<FormEntity> list = dbFormService.list(wrapper);
        if (FuncBase.isNotEmpty(list)) {
            return true;
        }

        return copyCheckDbTable(tableName);
    }

    //校验数据库是否存在
    @JeeLowCodeTenantIgnore
    public boolean checkDbTable(String tableName) {
        String sql = SqlHelper.getTableExistSql(tableName);
        List<Map<String, Object>> dataMapList = sqlService.getSimpleData(sql);

        if (FuncBase.isEmpty(dataMapList)) {//不存在
            return false;
        }
        Map<String, Object> dataMap = dataMapList.get(0);
        Integer cou = JeeLowCodeUtils.getMap2Integer(dataMap, "cou");
        if (Func.equals(cou, -1)) {
            cou = JeeLowCodeUtils.getMap2Integer(dataMap, "COU");
        }
        if (FuncBase.isNotEmpty(cou) && cou > 0) {
            return true;
        }
        return false;

    }

    @JeeLowCodeTenantIgnore
    public boolean copyCheckDbTable(String tableName) {
        String sql = SqlHelper.getTableExistSql(tableName);
        List<Map<String, Object>> dataMapList = sqlService.getSimpleData(sql);

        if (FuncBase.isEmpty(dataMapList)) {//不存在
            return false;
        }
        Map<String, Object> dataMap = dataMapList.get(0);
        Integer cou = JeeLowCodeUtils.getMap2Integer(dataMap, "cou");
        if (Func.equals(cou, -1)) {
            cou = JeeLowCodeUtils.getMap2Integer(dataMap, "COU");
        }
        if (FuncBase.isNotEmpty(cou) && cou > 0) {
            if (cou == 1) {
                return false;
            }
            return true;
        }
        return false;

    }

    //同步数据到数据库
    @Override
    public void syncDb(Long dbFormId, String syncModel) throws JSQLParserException {

        //获取表名
        FormEntity formEntity = dbFormService.getById(dbFormId);

        String tableName = formEntity.getTableName();
        String tableDescribe = formEntity.getTableDescribe();
        Long desformWebId = formEntity.getDesformWebId();
        Long groupDbformId = formEntity.getGroupDbformId();

        //获取现有的字段
        List<FormFieldEntity> selectFieldList = dbFormService.getDbFieldList(dbFormId);

        List<FieldModel> fieldModelList = selectFieldList.stream()
                .map(field -> {
                    FieldModel fieldModel = new FieldModel();
                    fieldModel.setFieldCode(field.getFieldCode());
                    fieldModel.setFieldName(field.getFieldName());
                    fieldModel.setFieldLen(field.getFieldLen());
                    fieldModel.setFieldPointLen(field.getFieldPointLen());
                    fieldModel.setFieldDefaultVal(field.getFieldDefaultVal());
                    fieldModel.setFieldType(JeeLowCodeFieldTypeEnum.getByFieldType(field.getFieldType()));
                    fieldModel.setIsPrimaryKey(field.getIsPrimaryKey());
                    fieldModel.setIsNull(field.getIsNull());
                    return fieldModel;
                })
                .collect(Collectors.toList());

        //强制同步数据库-也就是先删除，再创建表
        boolean checkDbTable = checkDbTable(tableName);
        if (!checkDbTable || (FuncBase.isNotEmpty(syncModel) && FuncBase.equals(syncModel, JeeLowCodeConstant.SYNC_DB_FORCE))) {//表不存在 或者 强制更新
            if (checkDbTable) {//数据库存在 ，则需要删除
                String dropTableDdl = SqlHelper.getDropTableSql(tableName);
                sqlService.executeDDL(dropTableDdl);
            }
            List<String> ddlList = SqlHelper.createTable(tableName, tableDescribe, fieldModelList);
            ddlList.stream().forEach(ddl -> {
                sqlService.executeDDL(ddl);
            });

            //处理索引
            this.handleIndex(dbFormId);

            //更新状态

            this.syncSuccess(dbFormId, desformWebId, groupDbformId);
            return;
        }
        //部分更新
        //获取备注字段
        String tableFieldCommentSql = SqlHelper.getTableFieldComment(tableName);
        List<Map<String, Object>> dataMapList = sqlService.getSimpleData(tableFieldCommentSql);
        Map<String, String> remarkMap = Func.dataMapList2ResultSet(dataMapList);

        List<String> ddlList = new ArrayList<>();
        //数据库存在该表，需要判断那些是新增，那些是删除，那些 是修改
        Map<String, FieldModel> columnMetaMap = dbManager.getDbColumnMetaMap(tableName, remarkMap);
        List<String> addColumnSql = dbManager.getAddColumnDdl(tableName, fieldModelList, columnMetaMap);
        List<String> updateColumnDdl = dbManager.getUpdateColumnDdl(tableName, fieldModelList, columnMetaMap);
        List<String> dropColumnDdl = dbManager.getDropColumnDdl(tableName, fieldModelList, columnMetaMap);

        ddlList.addAll(addColumnSql);
        ddlList.addAll(updateColumnDdl);
        ddlList.addAll(dropColumnDdl);

        ddlList.stream().forEach(ddl -> {
            sqlService.executeDDL(ddl);
        });

        //处理索引
        this.handleIndex(dbFormId);

        //更新状态
        this.syncSuccess(dbFormId, desformWebId, groupDbformId);

    }


    /**
     * 删除表
     *
     * @param tableName
     */

    @Override
    public boolean dropTable(String tableName) {
        try {
            String ddl = SqlHelper.getDropTableSql(tableName);
            sqlService.executeDDL(ddl);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public ResultDataModel getDataList(Long dbFormId,Map<String, Object> params){
        Page page=new Page(1,JeeLowCodeConstant.NOT_PAGE);
        return this.getDataList(dbFormId,page,params);
    }

    //获取表数据
    @Override
    public ResultDataModel getDataList(Long dbFormId, Page page, Map<String, Object> params) {
        FormEntity formEntity = dbFormService.getFormEntityById(dbFormId);

        SqlInfoQueryWrapper.Wrapper wrapper = this.getDataQueryWrapper(formEntity, params);
        if (Func.isEmpty(wrapper)) {
            return new ResultDataModel();
        }

        List<Map<String, Object>> dataList = null;
        Long total = 0L;

        long size = Func.isEmpty(page)?JeeLowCodeConstant.NOT_PAGE: page.getSize();
        if (size == JeeLowCodeConstant.NOT_PAGE) {//列表
            dataList = sqlService.getDataListByPlus(wrapper,params);
            total = (long) dataList.size();
        } else {//分页
            IPage<Map<String, Object>> pages = sqlService.getDataIPageByPlus(page, wrapper,params);
            //处理字典回显
            dataList = pages.getRecords();
            total = pages.getTotal();
        }

        //处理blob等特殊字段
        Map<String, JeeLowCodeFieldTypeEnum> fieldTypeEnumMap = dbFormService.getFieldCodeAndTypeEnum(dbFormId);
        Func.handlePlusDataList(dataList, fieldTypeEnumMap);

        //返回结果
        return ResultDataModel.fomat(total, dataList);

    }

    //获取统计数据
    @Override
    public ResultDataModel getDataSummaryList(Long dbFormId, Map<String, Object> params) {
        FormEntity formEntity = dbFormService.getFormEntityById(dbFormId);
        formEntity.setOrderbyConfig(null);//统计不用排序
        //处理底部统计
        List<FormSummaryEntity> summaryBottomList = dbFormService.getSummarySettingList(dbFormId, "bottom");
        Map<String, Map<String, Object>> resultBottomMap = new LinkedHashMap<>();
        if (Func.isNotEmpty(summaryBottomList)) {
            Map<Long, Map<String, Object>> bottomMap = new HashMap();
            //并行流处理
            summaryBottomList.stream().forEach(summaryEntity -> {
                Long id = summaryEntity.getId();
                String fieldCode = summaryEntity.getFieldCode();
                String summarySql = summaryEntity.getSummarySql();
                String summaryLabel = summaryEntity.getSummaryLabel();

                SqlInfoQueryWrapper.Wrapper wrapper = getDataQueryWrapper(formEntity, params, Func.getSummaryFieldList(fieldCode, summarySql), true);
                if (Func.isEmpty(wrapper)) {
                    return;
                }
                Map<String, Object> dataMap = sqlService.getDataOneByPlus(wrapper,params);
                String value = Func.getMap2Str(dataMap, fieldCode);
                Map<String, Object> labelMap = new HashMap<>();
                labelMap.put(summaryLabel, value);
                bottomMap.put(id, labelMap);
            });
            //取值
            summaryBottomList.stream().forEach(summaryEntity -> {
                Long id = summaryEntity.getId();
                String fieldCode = summaryEntity.getFieldCode();
                Map<String, Object> labelMap = bottomMap.get(id);
                if (Func.isEmpty(labelMap)) {
                    return;
                }
                resultBottomMap.put(fieldCode, labelMap);
            });
        }
        //处理顶部统计
        List<SummaryTopModel> topModelList = new ArrayList<>();
        List<FormSummaryEntity> summaryTopList = dbFormService.getSummarySettingList(dbFormId, "top");
        if (Func.isNotEmpty(summaryTopList)) {
            Map<Long, SummaryTopModel> topMap = new ConcurrentHashMap();
            //并行流处理
            try {
                Func.jeelowcodeForkJoinPool().submit(() -> summaryTopList.parallelStream().forEach(summaryEntity -> {
                    Long id = summaryEntity.getId();
                    String summarySql = summaryEntity.getSummarySql();
                    String summaryLabel = summaryEntity.getSummaryLabel();
                    String summaryJson = summaryEntity.getSummaryJson();
                    if (Func.isEmpty(summarySql)) {
                        return;
                    }

                    SqlInfoQueryWrapper.Wrapper wrapper = getDataQueryWrapper(formEntity, params, false);
                    //拿到原sql,也就是数据源
                    SqlFormatModel sqlFormatModel = wrapper.buildSql();
                    String sql = sqlFormatModel.getSql();
                    Map<String, Object> sqlParams = sqlFormatModel.getDataMap();

                    String newSql = "";
                    if (summarySql.contains(JeeLowCodeConstant.JEELOWCODE_SUMMARY_TABLE)) {//需要替换数据源
                        newSql = summarySql.replace(JeeLowCodeConstant.JEELOWCODE_SUMMARY_TABLE, sql);
                    } else {//直接运行自定义sql
                        newSql = SqlHelper.getPublicSql(summarySql);
                    }

                    List<Map<String, Object>> dataMapList = sqlService.getDataListByPlus(newSql, sqlParams);

                    SummaryTopModel topModel = new SummaryTopModel();
                    topModel.setSummaryLabel(summaryLabel);
                    topModel.setSummaryJson(summaryJson);
                    topModel.setDataMapList(dataMapList);
                    topMap.put(id, topModel);
                })).get();
            } catch (InterruptedException | ExecutionException e) {
                throw new RuntimeException(e.getMessage());
            }
            //按顺序取出来
            summaryTopList.stream().forEach(summaryEntity -> {
                Long id = summaryEntity.getId();
                SummaryTopModel topModel = topMap.get(id);
                if (Func.isEmpty(topModel)) {
                    return;
                }
                topModelList.add(topModel);
            });

        }

        //封装
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("bottomMap", resultBottomMap);
        resultMap.put("topList", topModelList);

        ResultDataModel resultDataModel = new ResultDataModel();
        resultDataModel.setRecords(Collections.singletonList(resultMap));
        return resultDataModel;
    }


    //获取表数据 -不分页
    @Override
    public ResultDataModel getExportDataList(Long dbFormId, Map<String, Object> params) {
        return dataList(dbFormId, params);
    }


    //公共列表
    private ResultDataModel dataList(Long dbFormId, Map<String, Object> params) {
        FormEntity formEntity = dbFormService.getFormEntityById(dbFormId);

        SqlInfoQueryWrapper.Wrapper wrapper = this.getDataQueryWrapper(formEntity, params);
        if (Func.isEmpty(wrapper)) {
            return new ResultDataModel();
        }
        List<Map<String, Object>> dataMapList = sqlService.getDataListByPlus(wrapper);

        Long pid = Func.getMap2Long(params, "pid");
        if (Func.isNotEmpty(pid) && Func.equals(dbFormService.getTableType(dbFormId), TableTypeEnum.TREE.getType())) {//当前是树结构，并且是懒加载模式
            this.checkTreehasChildren(formEntity.getTableName(), dataMapList);
        }
        //处理blob等特殊字段
        Map<String, JeeLowCodeFieldTypeEnum> fieldTypeEnumMap = dbFormService.getFieldCodeAndTypeEnum(dbFormId);
        Func.handlePlusDataList(dataMapList, fieldTypeEnumMap);
        return ResultDataModel.fomatList(dataMapList);
    }


    //获取表详情
    @Override
    public ResultDataModel getDataDetail(Long dbFormId, Long id, Map<String, Object> params) {
        FormEntity formEntity = dbFormService.getFormEntityById(dbFormId);

        //查所有
        SqlInfoQueryWrapper.Wrapper wrapper = this.getDataQueryWrapper(formEntity, params);
        Map<String, Object> dataMap = sqlService.getDataOneByPlus(wrapper);
        if (FuncBase.isEmpty(dataMap)) {
            return new ResultDataModel();
        }
        //处理blob等特殊字段
        Map<String, JeeLowCodeFieldTypeEnum> fieldTypeEnumMap = dbFormService.getFieldCodeAndTypeEnum(dbFormId);
        Func.handlePlusDataMap(dataMap, fieldTypeEnumMap);


        String tableThemeTemplate = dbFormService.getTableThemeTemplate(dbFormId);
        if (Func.equals(ThemeTemplateEnum.ERP.getType(), tableThemeTemplate)) {//当前是ERP模式,不用把附表查出来
            return ResultDataModel.fomatMap(dataMap);
        }
        Map<String, Object> subTableDataMap = new HashMap<>();
        //如果是主附表，需要把附表信息查出来
        List<String> allSubTableNameList = dbFormService.getAllSubTableNameList(dbFormId);
        if (Func.isNotEmpty(allSubTableNameList)) {//存在附表
            FrameServiceImpl proxyService = SpringUtils.getBean(FrameServiceImpl.class);

            for (String subTable : allSubTableNameList) {
                Long subDbFormId = dbFormService.getDbFormIdByTableName(subTable);

                //获取外键
                FormFieldForeignkeyEntity foreignkeyEntity = dbFormService.getFieldForeignkeyEntity(subDbFormId, formEntity.getTableName());
                if (Func.isEmpty(foreignkeyEntity)) {
                    continue;
                }
                Map<String, Object> subParamMap = new HashMap<>();
                subParamMap.put(foreignkeyEntity.getFieldCode(), id);//根据id来查询

                Map<String, Object> bodyMap = new HashMap<>();
                bodyMap.put(ParamEnum.COLUMN.getCode(), "id");
                bodyMap.put(ParamEnum.ORDER.getCode(), "asc");
                subParamMap.put(ParamEnum.REQUEST_PARAM_BODY.getCode(), bodyMap);

                ResultDataModel subDataModel = proxyService.getDataList(subDbFormId,subParamMap);

                List<Map<String, Object>> subDataList = subDataModel.getRecords();
                subTableDataMap.put(subTable, subDataList);
            }
        }
        dataMap.put("jeelowcode_subtable_data", subTableDataMap);

        return ResultDataModel.fomatMap(dataMap);
    }


    //保存数据
    @Transactional(rollbackFor = Exception.class)
    @Override
    public ExecuteEnhanceModel saveData(Long dbFormId, Map<String, Object> params) {
        FrameServiceImpl proxyService = SpringUtils.getBean(FrameServiceImpl.class);
        ExecuteEnhanceModel mainModel = proxyService.savePublicData(dbFormId, params);
        List<String> allSubTableNameList = dbFormService.getAllSubTableNameList(dbFormId);
        if (Func.isEmpty(allSubTableNameList)) {//当前不是主表
            return mainModel;
        }
        //保存子表数据
        this.saveOrUpdateSubTableData(dbFormId, Func.toLong(mainModel.getId()), params);
        return mainModel;
    }

    //保存数据-批量新增
    @Transactional(rollbackFor = Exception.class)
    @Override
    public List<String> saveBatchData(Long dbformId, List<JSONObject> dataMapList) {
        FrameServiceImpl proxyService = SpringUtils.getBean(FrameServiceImpl.class);
        List<String> idList = new ArrayList<>();
        for (Map<String, Object> dataMap : dataMapList) {
            ExecuteEnhanceModel enhanceModel = proxyService.savePublicData(dbformId, dataMap);//保存数据
            //保存子表数据
            this.saveOrUpdateSubTableData(dbformId, Func.toLong(enhanceModel.getId()), dataMap);
            idList.add(enhanceModel.getId());
        }
        return idList;
    }


    //增强切面
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void saveImportData(Long dbFormId, SaveImportDataParam param) {

        Long fieldId = param.getFieldId();
        List<ExcelFileDataEntity> entityList = param.getEntityList();
        ExcelImportDataDictModel dictModel = param.getDictModel();

        // 获取数据库字段列表
        List<FormFieldEntity> dbFieldList = dbFormService.getDbFieldList(dbFormId);
        if (Func.isEmpty(dbFieldList)) {
            return;
        }
        Set<String> fieldCodeSet = dbFieldList.stream().map(FormFieldEntity::getFieldCode).collect(Collectors.toSet());
        if (Func.isEmpty(fieldCodeSet)) {
            return;
        }

        // 获取表名
        String tableName = dbFormService.getTableName(dbFormId);

        //处理id
        AtomicReference<Integer> importState = new AtomicReference<>(1);//成功
        String redisKey = "excel:import:task_" + fieldId;//判断是否
        AtomicReference<Boolean> breakFlag = new AtomicReference<>(false);
        try {
            Func.jeelowcodeForkJoinPool().submit(() -> entityList.parallelStream().forEach(entity -> {
                if (breakFlag.get()) {//说明退出
                    return;
                }

                String errorReason = "";
                String handleResult = "";
                try {
                    //单条入库
                    this.saveImportDataMap(entity.getDataJson(), fieldCodeSet, dictModel, tableName);
                    handleResult = "SUCCESS";
                } catch (JeeLowCodeException e) {
                    errorReason = e.getMessage();
                    handleResult = "FAIL";
                } catch (Exception e) {
                    errorReason = "数据格式有误";
                    handleResult = "FAIL";
                }
                String redisKeyData = "excel:import_data:task_" + fieldId + ":" + handleResult + ":" + entity.getId();
                jeeLowCodeRedisUtils.set(redisKeyData, handleResult, 2, TimeUnit.DAYS);//存2天

                //更新表
                ExcelFileDataEntity updateEntity = new ExcelFileDataEntity();
                updateEntity.setId(entity.getId());
                updateEntity.setHandleState(YNEnum.Y.getCode());//已处理
                updateEntity.setHandleResult(handleResult);
                updateEntity.setHandleTime(DateUtil.date());
                updateEntity.setErrorReason(errorReason);
                excelDataService.updateById(updateEntity);


                //判断是否存在暂停，取消
                if (!jeeLowCodeRedisUtils.hasKey(redisKey)) {
                    return;
                }
                String redisVal = (String) jeeLowCodeRedisUtils.get(redisKey);
                if (Func.equals(redisVal, "STOP_TASK")) {//暂停
                    jeeLowCodeRedisUtils.del(redisKey);
                    breakFlag.set(true);
                } else if (Func.equals(redisVal, "CANCLE_TASK")) {//取消
                    jeeLowCodeRedisUtils.del(redisKey);
                    log.error("手动取消导入任务：" + fieldId);
                    breakFlag.set(true);
                    importState.set(-2);
                }
            })).get();
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e.getMessage());
        }

        //如果是取消的话，需要回滚事务
        Integer importStateInt = importState.get();
        if (importStateInt == -2) {//取消，需要删除数据
            excelFileService.rollback(fieldId);
        }


        //获取成功总数
        LambdaQueryWrapper<ExcelFileDataEntity> successWrapper = new LambdaQueryWrapper<>();
        successWrapper.eq(ExcelFileDataEntity::getExcelFileId, fieldId);
        successWrapper.eq(ExcelFileDataEntity::getHandleResult, "SUCCESS");
        long successNum = excelDataService.count(successWrapper);

        //获取失败总数
        LambdaQueryWrapper<ExcelFileDataEntity> errorWrapper = new LambdaQueryWrapper<>();
        errorWrapper.eq(ExcelFileDataEntity::getExcelFileId, fieldId);
        errorWrapper.eq(ExcelFileDataEntity::getHandleResult, "FAIL");
        long failNum = excelDataService.count(errorWrapper);


        //1.存入标题
        ExcelFileEntity fileEntity = new ExcelFileEntity();
        fileEntity.setId(fieldId);
        fileEntity.setImportState(Func.toStr(importState.get()));//修改状态
        fileEntity.setSuccessNum(Func.toInt(successNum));
        fileEntity.setErrorNum(Func.toInt(failNum));
        excelFileService.updateById(fileEntity);

    }

    //获取数据报表数据-分页
    @Override
    public ResultDataModel getReportDataList(String reportCode, Page page, Map<String, Object> params) {
        ReportEntity reportEntity = reportService.getReportEntityByCode(reportCode);

        SqlInfoQueryWrapper.Wrapper wrapper = this.getReportDataQueryWrapper(reportEntity, params);
        if (Func.isEmpty(wrapper)) {//无数据源,可以单独
            return new ResultDataModel();
        }

        List<Map<String, Object>> dataList = null;
        Long total = 0L;

        long size = Func.isEmpty(page)?JeeLowCodeConstant.NOT_PAGE: page.getSize();
        if (size == JeeLowCodeConstant.NOT_PAGE) {//列表
            dataList = sqlService.getDataListByPlus(wrapper);
            total = (long) dataList.size();
        } else {//分页
            IPage<Map<String, Object>> pages = sqlService.getDataIPageByPlus(page, wrapper);
            //处理字典回显
            dataList = pages.getRecords();
            total = pages.getTotal();
        }

        //处理blob等特殊字段
        Map<String, JeeLowCodeFieldTypeEnum> fieldTypeEnumMap = dbFormService.getReportFieldCodeAndTypeEnum(reportEntity.getId());
        Func.handlePlusDataList(dataList, fieldTypeEnumMap);

        //处理字典回显
        reportService.dictView(reportEntity.getId(), dataList);
        //返回结果
        return ResultDataModel.fomat(total, dataList);
    }


    /**
     * 单个保存
     *
     * @param dataJsonStr
     * @param fieldCodeSet
     * @param dictModel
     * @param tableName
     */
    private void saveImportDataMap(String dataJsonStr, Set<String> fieldCodeSet, ExcelImportDataDictModel dictModel, String tableName) {
        Map<String, Object> dataMap = Func.json2Bean(dataJsonStr, Map.class);
        Map<String, Object> collect = dataMap.entrySet().stream().filter(item -> fieldCodeSet.contains(item.getKey()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));//移除不属于数据库的列
        this.handleDict(dictModel, collect);//处理字典，字典值回显
        proxyAdapter.initSaveDefaultData(collect);// 处理默认值
        sqlService.insertDataByPlus(tableName, collect);//入库
    }


    //编辑数据
    @Transactional(rollbackFor = Exception.class)
    @Override
    public ExecuteEnhanceModel editData(Long dbFormId, Long dataId, Map<String, Object> params) {
        FrameServiceImpl proxyService = SpringUtils.getBean(FrameServiceImpl.class);
        //编辑主表
        ExecuteEnhanceModel enhanceModel = proxyService.editPublicData(dbFormId, dataId, params);

        List<String> allSubTableNameList = dbFormService.getAllSubTableNameList(dbFormId);
        if (Func.isEmpty(allSubTableNameList)) {//当前不是主表
            return enhanceModel;
        }

        //保存子表数据-新增或者修改
        this.saveOrUpdateSubTableData(dbFormId, dataId, params);
        return enhanceModel;

    }

    //编辑数据-批量
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void editBatchData(Long dbformId, List<JSONObject> jsonObjectList) {
        jsonObjectList.stream().forEach(jsonObject -> {
            Long id = jsonObject.getLong("id");
            jsonObject.remove("id");
            this.editData(dbformId, id, jsonObject);
        });
    }


    //删除数据
    @Transactional(rollbackFor = Exception.class)
    @Override
    public ExecuteEnhanceModel delData(Long dbFormId, Map<String, Object> params) {
        List<Long> dataIdList = (List<Long>) params.get("dataIdList");
        String whereFieldCode = (String) params.get("whereFieldCode");


        boolean serviceTableFlag = dbFormService.isServiceTable(dbFormId);
        if (!serviceTableFlag) {//当前不是业务表
            return null;
        }
        String tableName = dbFormService.getTableName(dbFormId);//获取表名

        Integer tableType = dbFormService.getTableType(dbFormId);
        if (Func.equals(tableType, TableTypeEnum.TREE.getType())) {//树结构
            SqlInfoQueryWrapper.Wrapper wrapper = SqlHelper.getQueryWrapper()
                    .setTableName(tableName)
                    .setWhere(where -> {
                        where.in("pid", dataIdList);
                        where.eq("is_deleted", 0);
                    })
                    .build();
            List<Map<String, Object>> dataMapList = sqlService.getDataListByPlus(wrapper);
            if (Func.isNotEmpty(dataMapList)) {
                throw new JeeLowCodeException("存在子集，无法删除");
            }
        }

        //删除主表
        SqlInfoUpdateWrapper.Wrapper wrapper = SqlHelper.getUpdateWrapper()
                .setTableName(tableName)
                .setColumn("is_deleted", -1)
                .setWhere(where -> {
                    where.in(whereFieldCode, dataIdList);
                });

        sqlService.delDataByPlus(wrapper);
        //如果是主附表的话，也要把附表关联的数据去掉
        if (Func.equals(TableTypeEnum.MAIN.getType(), tableType)) {//当前是主表，需要删除附表数据
            this.delSubData(tableName, dataIdList);
        }

        ExecuteEnhanceModel saveDataModel = new ExecuteEnhanceModel();
        saveDataModel.setId(FuncBase.toStr(dataIdList));
        return saveDataModel;
    }


    //根据主表删除附表
    private void delSubData(String mainTableName, List<Long> mainDataIdList) {
        //获取我的所有附表
        LambdaQueryWrapper<FormFieldForeignkeyEntity> foreignkeyWrapper = new LambdaQueryWrapper<>();
        foreignkeyWrapper.eq(FormFieldForeignkeyEntity::getMainTable, mainTableName);
        foreignkeyWrapper.select(FormFieldForeignkeyEntity::getDbformId, FormFieldForeignkeyEntity::getFieldCode);
        List<FormFieldForeignkeyEntity> entityList = fieldForeignkeyMapper.selectList(foreignkeyWrapper);
        if (Func.isEmpty(entityList)) {//没有附表
            return;
        }

        for (FormFieldForeignkeyEntity entity : entityList) {
            Long sub_dbformId = entity.getDbformId();
            String sub_fieldCode = entity.getFieldCode();
            IFrameService proxyService = SpringUtils.getBean(IFrameService.class);
            Map<String, Object> params = new HashMap<>();
            params.put("dataIdList", mainDataIdList);
            params.put("whereFieldCode", sub_fieldCode);
            proxyService.delData(sub_dbformId, params);//走通用删除
        }

    }


    //公共保存
    public ExecuteEnhanceModel savePublicData(Long dbFormId, Map<String, Object> params) {
        boolean serviceTableFlag = dbFormService.isServiceTable(dbFormId);
        if (!serviceTableFlag) {//当前不是业务表
            return null;
        }

        String tableName = dbFormService.getTableName(dbFormId);//获取表名
        List<FormFieldEntity> fieldList = dbFormService.getDbFieldList(dbFormId);

        Map<String, Object> insertDataMap = Func.getSaveOrUpdateMap(fieldList, params);

        sqlService.insertDataByPlus(tableName, insertDataMap);
        //处理子表

        String resultId = JeeLowCodeUtils.getMap2Str(insertDataMap, "id");
        ExecuteEnhanceModel saveDataModel = new ExecuteEnhanceModel();
        saveDataModel.setId(resultId);
        return saveDataModel;
    }


    //公共编辑
    public ExecuteEnhanceModel editPublicData(Long dbFormId, Long dataId, Map<String, Object> params) {

        boolean serviceTableFlag = dbFormService.isServiceTable(dbFormId);
        if (!serviceTableFlag) {//当前不是业务表
            return null;
        }

        String tableName = dbFormService.getTableName(dbFormId);//获取表名
        List<FormFieldEntity> fieldList = dbFormService.getDbFieldList(dbFormId);//所有字段

        Map<String, Object> dataMap = Func.getSaveOrUpdateMap(fieldList, params);
        dataMap.remove("id");


        SqlInfoUpdateWrapper.Wrapper wrapper = SqlHelper.getUpdateWrapper()
                .setTableName(tableName)
                .setMap(dataMap)
                .setWhere(where -> {
                    where.eq("id", dataId);
                }).build();

        sqlService.updateDataByPlus(wrapper);

        ExecuteEnhanceModel saveDataModel = new ExecuteEnhanceModel();
        saveDataModel.setId(FuncBase.toStr(dataId));
        return saveDataModel;
    }


    //处理索引
    private void handleIndex(Long dbFormId) {
        String tableName = dbFormService.getTableName(dbFormId);
        List<FormIndexEntity> indexList = dbFormService.getIndexList(dbFormId);
        if (FuncBase.isEmpty(dbFormService)) {
            return;
        }
        //已存在的索引
        Set<String> dbExistIndexNameSet = new HashSet<>();
        //当前需要新增的索引
        Set<String> nowIndexNameSet = new HashSet<>();

        String ddl = SqlHelper.getIndexAllByTableSql(tableName);

        List<Map<String, Object>> dataMapList = sqlService.getSimpleData(ddl);
        Set<String> indexNames = dataMapList.stream()
                .flatMap(dataMap -> Stream.of(
                        JeeLowCodeUtils.getMap2Str(dataMap, "index_name").toLowerCase(),
                        JeeLowCodeUtils.getMap2Str(dataMap, "INDEX_NAME").toLowerCase()
                ))
                .distinct() // 去重
                .filter(FuncBase::isNotEmpty)
                .collect(Collectors.toCollection(HashSet::new)); // 使用HashSet以保证唯一性

        dbExistIndexNameSet.addAll(indexNames);
        //新增完成
        indexList.stream()
                .filter(indexField -> FuncBase.isNotEmpty(indexField.getIndexFieldCodeList()))
                .forEach(indexField -> {
                    nowIndexNameSet.add(indexField.getIndexName());//当前存在的
                    this.addTableIndex(tableName, indexField.getIndexName(), FuncBase.toStrList(indexField.getIndexFieldCodeList()), dbExistIndexNameSet);
                });
        //删除多余索引
        dbExistIndexNameSet.stream()
                .filter(indexName -> !nowIndexNameSet.contains(indexName))
                .forEach(indexName -> this.dropTableIndex(tableName, indexName));

    }


    /**
     * 新增/编辑数据
     * 主副表处理，保存主表的时候，要处理附表数据
     *
     * @param mainDbFormId
     * @param mainId
     * @param params
     */
    private void saveOrUpdateSubTableData(Long mainDbFormId, Long mainId, Map<String, Object> params) {
        //如果是erp模式的话，是自己单独新增的，不用走该方法
        String tableThemeTemplate = dbFormService.getTableThemeTemplate(mainDbFormId);
        if (Func.equals(ThemeTemplateEnum.ERP.getType(), tableThemeTemplate)) {//当前是ERP模式
            return;
        }

        FrameServiceImpl proxyService = SpringUtils.getBean(FrameServiceImpl.class);
        //如果当前是主表，则要判断附表问题
        params.put("id", mainId);
        String mainTableName = dbFormService.getTableName(mainDbFormId);

        //获取所有子表
        List<String> subTableNameList = dbFormService.getAllSubTableNameList(mainDbFormId);
        if (Func.isEmpty(subTableNameList)) {
            return;
        }
        for (String subTableName : subTableNameList) {
            if (!params.containsKey(subTableName)) {//没有参数，则不做处理
                continue;
            }

            //子表参数
            List<Map<String, Object>> subParamList = (List) params.get(subTableName);
            if (Func.isEmpty(subParamList)) {//有key，但是是空的，[] 说明全部情况了，则需要删除
                subParamList = new ArrayList<>();
            }

            //存储参数中的所有id
            Set<String> idSet = Func.list2SetId(subParamList);

            //获取子表基本信息
            FormEntity sudFormEntity = dbFormService.getFormEntityByName(subTableName);
            Long subFormId = sudFormEntity.getId();

            //找到外键列
            FormFieldForeignkeyEntity foreignkeyEntity = dbFormService.getFieldForeignkeyEntity(subFormId, mainTableName);
            if (Func.isEmpty(foreignkeyEntity)) {
                continue;
            }

            String subFieldCode = foreignkeyEntity.getFieldCode();
            String mainField = foreignkeyEntity.getMainField();

            //值
            Object obj = params.get(mainField);
            if (Func.isEmpty(obj)) {//值为空则跳过
                continue;
            }

            //清除已经删除的旧数据
            List<Map<String, Object>> oldDataList = sqlService.getDataListByField(subTableName, subFieldCode, obj, Func.toStrList("id"));
            oldDataList.stream()
                    .filter(oldData -> !idSet.contains(Func.getMap2Str(oldData, "id")))
                    .forEach(oldData -> {
                        sqlService.baseDelDataById(subTableName, Func.toLong(Func.getMap2Str(oldData, "id")));
                    });

            //遍历每一行进行新增、修改操作
            for (Map<String, Object> subParam : subParamList) {
                subParam.put(subFieldCode, obj);//设置值

                Long subId = Func.getMap2Long(subParam, "id");
                if (Func.isEmpty(subId)) {//没有id,则是新增
                    proxyService.savePublicData(subFormId, subParam);//保存子表
                } else {
                    proxyService.editPublicData(subFormId, subId, subParam);//保存子表
                }
            }
        }


    }


    /**
     * 获取查询构造
     *
     * @param formEntity
     * @param params     参数
     *                   selfSelectSqlList        自定义sqlList
     * @return
     */
    private SqlInfoQueryWrapper.Wrapper getDataQueryWrapper(FormEntity formEntity, Map<String, Object> params) {
        return this.getDataQueryWrapper(formEntity, params, null, true);
    }

    private SqlInfoQueryWrapper.Wrapper getDataQueryWrapper(FormEntity formEntity, Map<String, Object> params, boolean aliasFlag) {
        return this.getDataQueryWrapper(formEntity, params, null, aliasFlag);
    }

    private SqlInfoQueryWrapper.Wrapper getDataQueryWrapper(FormEntity formEntity, Map<String, Object> params, List<String> selfSelectSqlList, boolean aliasFlag) {
        SqlInfoQueryWrapper.Wrapper queryWrapper = SqlHelper.getQueryWrapper();
        //表视图
        Integer tableClassify = formEntity.getTableClassify();
        if (Func.equals(tableClassify, TableClassifyEnum.SERVICE.getType())) {//业务表
            String tableName = formEntity.getTableName();
            queryWrapper.setTableName(tableName);
            List<String> select = Func.isNotEmpty(selfSelectSqlList) ? selfSelectSqlList : this.getFieldList(formEntity.getId(), params);
            //查询列
            queryWrapper.select(select, aliasFlag);
            queryWrapper.setWhere(where -> {
                where.eq("is_deleted", 0);
            }).build();
        } else {//显示表-视图
            String dataSourcesConfigJsonStr = formEntity.getDataSourcesConfig();
            if (Func.isEmpty(dataSourcesConfigJsonStr)) {//没有配置数据源
                return null;
            }
            DataSourceConfigModel dataSourceConfigModel = Func.json2Bean(dataSourcesConfigJsonStr, DataSourceConfigModel.class);
            if (Func.isEmpty(dataSourceConfigModel) || Func.isEmpty(dataSourceConfigModel.getExecuteSql())) {
                return null;
            }
            //直接执行数据源
            queryWrapper.setTableSql(dataSourceConfigModel.getExecuteSql());
        }

        //处理排序排序================
        this.handleOrderBy(queryWrapper, params, formEntity.getOrderbyConfig());

        //处理默认条件============
        this.handleDefaultWhere(queryWrapper, formEntity.getWhereConfig());

        //处理搜索where=================
        this.handleWhere(formEntity.getId(), queryWrapper, params);

        //处理租户数据权限
        this.handleTenantDataRole(formEntity.getId(), queryWrapper, params);

        return queryWrapper;
    }

    /**
     * 获取报表wrapper
     *
     * @param reportEntity
     * @param params
     * @return
     */
    private SqlInfoQueryWrapper.Wrapper getReportDataQueryWrapper(ReportEntity reportEntity, Map<String, Object> params) {
        SqlInfoQueryWrapper.Wrapper queryWrapper = SqlHelper.getQueryWrapper();
        //自定义sql
        String dataSourcesConfigJsonStr = reportEntity.getDataSourcesConfig();
        if (Func.isEmpty(dataSourcesConfigJsonStr)) {//没有配置数据源
            return null;
        }
        DataSourceConfigModel dataSourceConfigModel = Func.json2Bean(dataSourcesConfigJsonStr, DataSourceConfigModel.class);
        if (Func.isEmpty(dataSourceConfigModel) || Func.isEmpty(dataSourceConfigModel.getExecuteSql())) {
            return null;
        }
        //直接执行数据源
        queryWrapper.setTableSql(dataSourceConfigModel.getExecuteSql());

        //处理排序排序================
        this.handleOrderBy(queryWrapper, params, null);

        //处理搜索where=================
        List<Map<String, String>> whereFieldMapList = jeeLowCodeMapper.getReportWhereFieldList(reportEntity.getId());
        this.handleWhere(whereFieldMapList, queryWrapper, params);

        return queryWrapper;
    }


    //处理排序
    private void handleOrderBy(SqlInfoQueryWrapper.Wrapper wrapper, Map<String, Object> params, String orderbyConfigStr) {
        Map<String, Object> seleOrderByMap = (Map<String, Object>) params.get(ParamEnum.REQUEST_PARAM_BODY.getCode());
        if (Func.isNotEmpty(seleOrderByMap)) {
            String column = JeeLowCodeUtils.getMap2Str(seleOrderByMap, ParamEnum.COLUMN.getCode());
            String order = JeeLowCodeUtils.getMap2Str(seleOrderByMap, ParamEnum.ORDER.getCode());
            if (FuncBase.isNotEmpty(column) && FuncBase.isNotEmpty(order) && (FuncBase.equals(order, "desc") || FuncBase.equals(order, "asc"))) {
                if (FuncBase.equals(order, "desc")) {
                    wrapper.orderByDesc(column);
                } else {
                    wrapper.orderByAsc(column);
                }
                return;
            }
        }


        String column = JeeLowCodeUtils.getMap2Str(params, ParamEnum.COLUMN.getCode());
        String order = JeeLowCodeUtils.getMap2Str(params, ParamEnum.ORDER.getCode());
        if (FuncBase.isNotEmpty(column) && FuncBase.isNotEmpty(order) && (FuncBase.equals(order, "desc") || FuncBase.equals(order, "asc"))) {
            if (FuncBase.equals(order, "desc")) {
                wrapper.orderByDesc(column);
            } else {
                wrapper.orderByAsc(column);
            }
            return;
        }
        if (Func.isEmpty(orderbyConfigStr)) {
            return;
        }

        //默认id 倒序排序
        List<OrderByModel> orderByList = JSONUtil.toList(orderbyConfigStr, OrderByModel.class);

        //遍历
        orderByList.forEach(model -> {
            String orderBy = model.getOrder();
            String orderByColumn = model.getColumn();
            switch (orderBy) {
                case "asc":
                    wrapper.orderByAsc(orderByColumn);
                    break;
                default:
                    wrapper.orderByDesc(orderByColumn);
            }
        });

    }

    //处理默认where
    private void handleDefaultWhere(SqlInfoQueryWrapper.Wrapper wrapper, String whereConfig) {
        if (Func.isEmpty(whereConfig)) {
            return;
        }


        WhereModel whereModel = Func.json2Bean(whereConfig, WhereModel.class);
        if (Func.isEmpty(whereModel)) {
            return;
        }
        List<String> whereList = whereModel.getWhereList();
        if (Func.isEmpty(whereList)) {
            return;
        }


        String applySql = whereList.stream()
                .filter(wheresql -> !wheresql.trim().isEmpty()) // 过滤掉空或只含空白字符的字符串
                .collect(Collectors.joining(" AND "));

        if (Func.isEmpty(applySql)) {
            return;
        }

        //拼接
        wrapper.setWhere(where -> {
            where.apply(applySql);
        }).build();
    }


    //处理where
    private void handleWhere(Long dbFormId, SqlInfoQueryWrapper.Wrapper wrapper, Map<String, Object> params) {
        String allQueryField = Func.getMap2Str(params, ParamEnum.ALL_QUERY_FIELD.getCode());
        List<Map<String, String>> whereFieldMapList =null;
        if(Func.isNotEmpty(allQueryField) && Func.equals(allQueryField,"Y")){
            //特殊情况，是全部放开
            whereFieldMapList = jeeLowCodeMapper.getAllDbWhereFieldList(dbFormId);
        }else{
            whereFieldMapList = jeeLowCodeMapper.getDbWhereFieldList(dbFormId);
        }
        handleWhere(whereFieldMapList, wrapper, params);
    }

    private void handleWhere(List<Map<String, String>> whereFieldMapList, SqlInfoQueryWrapper.Wrapper wrapper, Map<String, Object> params) {
        //开启查询的列
        if (FuncBase.isEmpty(whereFieldMapList)) {
            return;
        }
        //如果是多选的时候，要做特殊处理，因为多选的时候 ，数据库存储的是 11,22,33
        String moreSelectFieldListStr = (String) params.get(ParamEnum.MORE_SELECT_FIELD.getCode());
        List<String> moreSelectFieldList = null;
        if (FuncBase.isNotEmpty(moreSelectFieldListStr)) {
            moreSelectFieldList = FuncBase.toStrList(moreSelectFieldListStr);
        }

        for (Map<String, String> whereFieldMap : whereFieldMapList) {
            String field_code = whereFieldMap.get("field_code");//字段
            String field_type = whereFieldMap.get("field_type");//类型
            String query_mode = whereFieldMap.get("query_mode");//EQ LIKE
            JeeLowCodeFieldTypeEnum fieldTypeEnum = JeeLowCodeFieldTypeEnum.getByFieldType(field_type);

            Object obj = params.get(field_code);
            if (FuncBase.isEmpty(obj)) {
                continue;
            }
            //类型转换 111,22,33
            if (FuncBase.equals(query_mode, QueryModelEnum.EQ.getCode())) {//精确
                if (FuncBase.isNotEmpty(moreSelectFieldList) && moreSelectFieldList.contains(field_code)) {//在多选里面
                    Object finalObj = Func.paramParse2Object(obj, fieldTypeEnum);
                    wrapper.setWhere(where -> {
                        where.and(param -> {
                            param.eq(field_code, finalObj)
                                    .or().likeLeft(field_code, "," + finalObj)
                                    .or().likeRight(field_code, finalObj + ",")
                                    .or().like(field_code, "," + finalObj + ",");
                        });
                    });
                } else {
                    Object finalObj = Func.paramParse2Object(obj, fieldTypeEnum);
                    wrapper.setWhere(where -> {
                        where.eq(field_code, finalObj);
                    });
                }

            } else if (FuncBase.equals(query_mode, QueryModelEnum.RANGE.getCode())) {//范围
                String listStr = FuncBase.toStr(obj);
                List<String> paramList = FuncBase.toStrList(listStr);// 1<=x<2
                Object leftVal = paramList.get(0);
                Object rightVal = null;
                if(paramList.size()>1){
                     leftVal = paramList.get(0);
                     rightVal = paramList.get(1);
                }
                //有可能是null字符串
                Object finalLeftVal1tmp =null;
                if (Func.isNotEmpty(leftVal) && !Func.equals(leftVal, "null")) {
                    finalLeftVal1tmp = Func.paramParse2Object(leftVal, fieldTypeEnum);
                }
                Object finalRightVal1tmp=null;
                if (Func.isNotEmpty(rightVal)  && !Func.equals(rightVal, "null")) {
                    finalRightVal1tmp = Func.paramParse2Object(rightVal, fieldTypeEnum);
                }

                Object finalLeftVal1=finalLeftVal1tmp;
                Object finalRightVal1=finalRightVal1tmp;

                if (FuncBase.isNotEmpty(finalLeftVal1) && FuncBase.isNotEmpty(finalRightVal1)) {
                    wrapper.setWhere(where -> {
                        where.between(field_code, finalLeftVal1, finalRightVal1);//区间
                    });
                } else if (FuncBase.isNotEmpty(finalLeftVal1)) {//>=
                    wrapper.setWhere(where -> {
                        where.ge(field_code, finalLeftVal1);
                    });

                } else if (FuncBase.isNotEmpty(finalRightVal1)) {//<=
                    wrapper.setWhere(where -> {
                        where.le(field_code, finalRightVal1);
                    });
                }
            } else {
                Object objVal = Func.paramParse2Object(obj, fieldTypeEnum);
                wrapper.setWhere(where -> {
                    if (objVal instanceof DateTime) {
                        where.eq(field_code, objVal);
                    } else {
                        where.like(field_code, objVal);
                    }
                });
            }
        }

    }


    //处理租户数据权限
    private void handleTenantDataRole(Long dbFormId, SqlInfoQueryWrapper.Wrapper wrapper, Map<String, Object> params) {
        //获取当前的租户
        Long tenantId = jeeLowCodeAdapter.getTenantId();
        if (Func.isEmpty(tenantId) || Func.equals(tenantId, -1L)) {
            return;//没有登录
        }
        List<DbFormRoleDataRuleVo> dbFormRoleDataRuleVoList = dbFormRoleService.listRoleData(tenantId, dbFormId);
        if (Func.isEmpty(dbFormRoleDataRuleVoList)) {
            return;
        }

        for (DbFormRoleDataRuleVo vo : dbFormRoleDataRuleVoList) {
            String enableState = vo.getEnableState();
            String ruleSql = vo.getRuleSql();
            if (Func.isEmpty(ruleSql)) {
                continue;
            }
            if (Func.isNotEmpty(enableState) && Func.equals(enableState, YNEnum.N.getCode())) {
                continue;
            }
            //下面是需要执行的语句
            //替换SQL里面的值,全部都是从当前用户里面获取
            String finalRuleSql = Func.replaceParam(ruleSql, params, null, jeeLowCodeAdapter);
            wrapper.setWhere(where -> {
                where.apply(finalRuleSql);
            });
        }
    }

    /**
     * 同步完成，更新状态
     *
     * @param dbFormId
     */
    private void syncSuccess(Long dbFormId, Long desformWebId, Long groupDbformId) {
        //更新状态
        FormEntity updateEntity = new FormEntity();
        updateEntity.setId(dbFormId);
        updateEntity.setDesformWebId(desformWebId);
        updateEntity.setGroupDbformId(groupDbformId);
        updateEntity.setIsDbSync(YNEnum.Y.getCode());
        dbFormService.updateById(updateEntity);
    }


    /**
     * 添加索引
     *
     * @param tableName
     * @param indexName
     * @param fieldCodeList
     */
    private void addTableIndex(String tableName, String indexName, List<String> fieldCodeList, Set<String> dbExistIndexNameSet) {
        if (dbExistIndexNameSet.contains(indexName)) {//存在，则删除
            this.dropTableIndex(tableName, indexName);
        }
        //删除完成，然后新建
        String ddl = SqlHelper.createIndex(tableName, indexName, fieldCodeList);
        sqlService.executeDDL(ddl);
    }

    /**
     * 删除索引
     *
     * @param tableName
     * @param indexName
     */
    private void dropTableIndex(String tableName, String indexName) {
        String ddl = SqlHelper.getDropIndex(tableName, indexName);
        sqlService.executeDDL(ddl);
    }


    /**
     * 获取表格显示列
     *
     * @param dbFormId
     * @return
     */
    private List<String> getFieldList(Long dbFormId, Map<String, Object> params) {
        DbFieldCodeListModel dbFieldCodeList = dbFormService.getDbFieldCodeList(dbFormId);
        List<String> selectList = dbFieldCodeList.getSelectList();

        if (FuncBase.isNotEmpty(params)) {//默认
            //判断是否是字典 系统特定参与默认都是jeeLowCode_开头
            List<String> list = JeeLowCodeUtils.getMap2List(params, ParamEnum.DICT_TABLE_FIELD.getCode());//自定义列  id,name,age,sex  id,name,age,sex  id,name
            if (FuncBase.isNotEmpty(list)) {//查字典数据
                //取交集
                List<String> intersectionList = selectList.stream()
                        .filter(list::contains)
                        .distinct()
                        .collect(Collectors.toList());

                return intersectionList;
            }
        }

        return selectList;
    }

    /**
     * 校验树表是否有子集
     *
     * @param tableName
     * @param treeDataList
     */
    private void checkTreehasChildren(String tableName, List<Map<String, Object>> treeDataList) {
        if (Func.isEmpty(treeDataList)) {
            return;
        }
        try {
            Func.jeelowcodeForkJoinPool().submit(() -> treeDataList.parallelStream().forEach(dataMap -> {
                Long id = Func.getMap2Long(dataMap, "id");

                SqlInfoQueryWrapper.Wrapper wrapper = SqlHelper.getQueryWrapper()
                        .setTableName(tableName)
                        .select("id")
                        .setWhere(where -> {
                            where.eq("is_deleted", 0);
                            where.eq("pid", id);
                        }).build();
                Map<String, Object> subMap = sqlService.getDataOneByPlus(wrapper);
                boolean hasChildren = Func.isNotEmpty(subMap);
                dataMap.put("hasChildren", hasChildren);
                dataMap.put("leaf", !hasChildren);
            })).get();
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e.getMessage());
        }
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
