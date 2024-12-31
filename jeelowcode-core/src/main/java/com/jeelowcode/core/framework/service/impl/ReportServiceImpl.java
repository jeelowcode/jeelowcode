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

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jeelowcode.core.framework.config.aspect.enhance.JeeLowCodeAnnoaionAspectjSQL;
import com.jeelowcode.core.framework.config.aspect.enhance.JeeLowCodeAnnotationAspectjJAVA;
import com.jeelowcode.core.framework.config.aspect.enhance.plugin.BaseAdvicePlugin;
import com.jeelowcode.core.framework.config.aspect.enhance.plugin.PluginManager;
import com.jeelowcode.core.framework.config.aspect.enhancereport.JeeLowCodeAnnotationAspectjReport;
import com.jeelowcode.core.framework.config.aspect.enhancereport.plugin.ReportPluginManager;
import com.jeelowcode.core.framework.entity.*;
import com.jeelowcode.core.framework.mapper.ReportFieldMapper;
import com.jeelowcode.core.framework.mapper.ReportMapper;
import com.jeelowcode.core.framework.params.DbFormAddOrUpdateParam;
import com.jeelowcode.core.framework.params.PageReportParam;
import com.jeelowcode.core.framework.params.ReportAddOrUpdateParam;
import com.jeelowcode.core.framework.params.vo.*;
import com.jeelowcode.core.framework.params.vo.webconfigreport.WebConfigReportFieldVo;
import com.jeelowcode.core.framework.params.vo.webconfigreport.WebConfigReportVo;
import com.jeelowcode.core.framework.params.vo.webconfigreport.WebConfigVo;
import com.jeelowcode.core.framework.service.IBatchService;
import com.jeelowcode.core.framework.service.IReportService;
import com.jeelowcode.core.framework.utils.Func;
import com.jeelowcode.framework.exception.JeeLowCodeException;
import com.jeelowcode.framework.utils.adapter.IJeeLowCodeAdapter;
import com.jeelowcode.framework.utils.constant.EnhanceConstant;
import com.jeelowcode.framework.utils.constant.JeeLowCodeConstant;
import com.jeelowcode.framework.utils.enums.AuthTypeEnum;
import com.jeelowcode.framework.utils.enums.TableType2Enum;
import com.jeelowcode.framework.utils.enums.YNEnum;
import com.jeelowcode.framework.utils.model.JeeLowCodeDict;
import com.jeelowcode.framework.utils.params.JeeLowCodeDictParam;
import com.jeelowcode.framework.utils.tool.spring.SpringUtils;
import com.jeelowcode.framework.utils.utils.FuncBase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;


/**
 * 数据报表相关
 */
@Service
public class ReportServiceImpl extends ServiceImpl<ReportMapper, ReportEntity> implements IReportService {

    @Autowired
    private ReportFieldMapper reportFieldMapper;

    @Autowired
    private IBatchService batchService;

    @Autowired
    private IJeeLowCodeAdapter jeeLowCodeAdapter;

    //获取认证类型

    @Override
    public AuthTypeEnum getAuthType(String reportCode) {
        ReportEntity reportEntity = this.getReportEntityByCode(reportCode);
        String dataConfig = reportEntity.getDataConfig();
        if (FuncBase.isEmpty(dataConfig)) {
            return null;
        }
        List<String> dataConfigList = FuncBase.toStrList(dataConfig);
        for (String type : dataConfigList) {
            AuthTypeEnum authTypeEnum = AuthTypeEnum.getByType(type);
            if (Func.isNotEmpty(authTypeEnum)) {
                return authTypeEnum;
            }
        }
        return null;

    }


    //根据id获取
    @Override
    public ReportEntity getReportEntityById(Long reportId) {
        ReportEntity entity = baseMapper.getById(reportId);
        if (FuncBase.isEmpty(entity)) {
            throw new JeeLowCodeException("数据统计表不存在");
        }
        return entity;
    }

    //根据code获取
    @Override
    public ReportEntity getReportEntityByCode(String reportCode) {
        ReportEntity entity = baseMapper.getByCode(reportCode);
        if (FuncBase.isEmpty(entity)) {
            throw new JeeLowCodeException("数据报表不存在");
        }
        return entity;
    }


    //保存数据报表
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long saveReportConfig(ReportAddOrUpdateParam param) {
        long formId = IdWorker.getId();
        this.saveOrupdateReportConfig(param, formId);
        return formId;
    }

    //修改数据报表
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateReportConfig(ReportAddOrUpdateParam param) {
        this.saveOrupdateReportConfig(param, param.getReport().getId());
    }

    //删除 - 表单开发
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteReportConfig(List<Long> reportIdList) {
        reportIdList.stream().forEach(reportId -> {
            ReportEntity reportEntity = baseMapper.selectById(reportId);
            if (FuncBase.isEmpty(reportEntity)) {
                return;
            }

            //1.删除主表
            baseMapper.deleteById(reportId);
            //2.删除属性表
            QueryWrapper<ReportFieldEntity> fieldWrapper = new QueryWrapper();
            fieldWrapper.eq("report_id", reportId);
            reportFieldMapper.delete(fieldWrapper);

            JeeLowCodeAnnotationAspectjReport.refreshPlugin("DEL", reportEntity.getReportCode(),reportEntity.getJavaConfig());
        });

    }

    /**
     * 获取详情
     *
     * @param reportId
     * @return
     */
    @Override
    public ReportConfigVo getReportConfig(Long reportId) {
        ReportConfigVo resultVo = new ReportConfigVo();

        IReportService proxyReportService = SpringUtils.getBean(IReportService.class);
        //表单配置
        ReportEntity reportEntity = proxyReportService.getReportEntityById(reportId);
        resultVo.setReport(reportEntity);

        //字段信息
        List<ReportFieldEntity> fieldEntityList = reportFieldMapper.getByDbReportId(reportId);
        resultVo.setFieldList(fieldEntityList);

        return resultVo;
    }


    //分页 - 表单开发
    @Override
    public IPage<ReportEntity> getPageReportConfig(PageReportParam param, Page page) {
        LambdaQueryWrapper<ReportEntity> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(FuncBase.isNotEmpty(param.getGroupReportId()), ReportEntity::getGroupReportId, param.getGroupReportId());
        wrapper.like(FuncBase.isNotEmpty(param.getReportName()), ReportEntity::getReportName, param.getReportName());
        wrapper.like(FuncBase.isNotEmpty(param.getReportCode()), ReportEntity::getReportCode, param.getReportCode());
        wrapper.like(FuncBase.isNotEmpty(param.getJavaConfig()), ReportEntity::getJavaConfig, param.getJavaConfig());
        wrapper.orderByDesc(ReportEntity::getId);
        IPage<ReportEntity> pages = baseMapper.selectPage(page, wrapper);
        return pages;
    }

    //前端配置
    @Override
    public WebConfigVo getWebConfig(String reportCode) {
        ReportEntity reportEntity = baseMapper.getByCode(reportCode);

        //报表基本配置
        ReportConfigVo reportConfig = this.getReportConfig(reportEntity.getId());

        //转为一条返回给前端
        List<ReportFieldEntity> fieldList = reportConfig.getFieldList();//字段列表

        //封装结果返回
        WebConfigVo webConfigVo = new WebConfigVo();
        webConfigVo.setFieldList(fieldList);
        webConfigVo.setReportVo(BeanUtil.copyProperties(reportConfig.getReport(), WebConfigReportVo.class));
        return webConfigVo;
    }

    //回显字典
    @Override
    public void dictView(Long reportId, List<Map<String, Object>> records) {
        //获取字典code
        LambdaQueryWrapper<ReportFieldEntity> fieldWrapper = new LambdaQueryWrapper<>();
        fieldWrapper.isNotNull(ReportFieldEntity::getDictCode);
        fieldWrapper.eq(ReportFieldEntity::getReportId,reportId);
        List<ReportFieldEntity> fieldEntityList = reportFieldMapper.selectList(fieldWrapper);
        if (Func.isEmpty(fieldEntityList)) {
            return;
        }
        //封装为map
        Map<String, Map<String, String>> field2DictMap = new HashMap<>();
        for (ReportFieldEntity fieldEntity : fieldEntityList) {
            String fieldCode = fieldEntity.getFieldCode();
            String dictCode = fieldEntity.getDictCode();
            if (Func.isEmpty(dictCode)) {
                continue;
            }

            JeeLowCodeDictParam param = new JeeLowCodeDictParam();
            param.setDictCodeList(FuncBase.toStrList(dictCode));
            List<JeeLowCodeDict> dictList = jeeLowCodeAdapter.getDictList(param);
            if (Func.isEmpty(dictList)) {
                continue;
            }
            JeeLowCodeDict jeeLowCodeDict = dictList.get(0);
            List<JeeLowCodeDict.DictData> dataList = jeeLowCodeDict.getDataList();
            if (Func.isEmpty(dataList)) {
                continue;
            }
            Map<String, String> dictDataMap = new HashMap<>();
            for (JeeLowCodeDict.DictData dictData : dataList) {
                String label = dictData.getLabel();
                String val = dictData.getVal();
                dictDataMap.put(val, label);
            }
            field2DictMap.put(fieldCode, dictDataMap);
        }
        //遍历数据
        for (Map<String, Object> dataMap : records) {

            Set<Map.Entry<String, Map<String, String>>> entries = field2DictMap.entrySet();
            Iterator<Map.Entry<String, Map<String, String>>> iterator = entries.iterator();
            while (iterator.hasNext()) {
                Map.Entry<String, Map<String, String>> entity = iterator.next();

                String fieldCode = entity.getKey();
                String fieldVal = Func.getMap2Str(dataMap, fieldCode);

                Map<String, String> dictMap = entity.getValue();
                if (Func.isEmpty(fieldVal) || Func.isEmpty(dictMap)) {//没有值
                    continue;
                }
                String dictLabel = dictMap.get(fieldVal);
                if(Func.isEmpty(dictLabel)){
                    continue;
                }
                dataMap.put(fieldCode,dictLabel);

            }


        }


    }

    //初始化增强
    @Override
    public void initEnhancePluginManager() {
        //获取所有增强
        LambdaQueryWrapper<ReportEntity> wrapper=new LambdaQueryWrapper<>();
        wrapper.isNotNull(ReportEntity::getJavaConfig);
        List<ReportEntity> reportEntityList = baseMapper.selectList(wrapper);
        if(Func.isEmpty(reportEntityList)){
            return;
        }
        reportEntityList.forEach(reportEntity->{
            try{
                JeeLowCodeAnnotationAspectjReport.refreshPlugin("ADD",reportEntity.getReportCode(),reportEntity.getJavaConfig());
            }catch (Exception e){

            }
        });
    }

    //复制
    @Override
    public void copy(String reportCode, String newReportCode){
        long reportId = IdWorker.getId();
        //复制主表
        ReportEntity entity = baseMapper.getByCode(reportCode);
        ReportVo reportVo = BeanUtil.copyProperties(entity, ReportVo.class);
        reportVo.setId(null);
        reportVo.setReportCode(newReportCode);
        reportVo.setReportName(reportVo.getReportName() + "_copy");
        String oldEnhance = entity.getJavaConfig();
        String newEnhance;
        if (oldEnhance.contains(JeeLowCodeConstant.DOT)) {
            // class方式
            newEnhance = Func.copyClassEnhance(TableType2Enum.REPORT.getType(), oldEnhance, newReportCode);
        }else {
            // spring方式
            newEnhance = Func.copySpringEnhance(TableType2Enum.REPORT.getType(), oldEnhance, newReportCode);
        }
        reportVo.setJavaConfig(newEnhance);

        //字段
        List<ReportFieldEntity> fieldEntityList = reportFieldMapper.getByDbReportId(entity.getId());
        List<ReportFieldVo> fieldVoList = Func.copyReportFieldList(fieldEntityList, ReportFieldVo.class);

        ReportAddOrUpdateParam param=new ReportAddOrUpdateParam();
        param.setFieldList(fieldVoList);
        param.setReport(reportVo);
        this.saveOrupdateReportConfig(param,reportId);

    }



    //新增 或 修改
    private void saveOrupdateReportConfig(ReportAddOrUpdateParam param, Long reportId) {
        //1.保存主表
        this.saveOrUpdateReport(reportId, param.getReport());

        ReportDeletedVo delIdVo = param.getDelIdVo();
        if (Func.isEmpty(delIdVo)) {
            delIdVo = new ReportDeletedVo();
        }

        //2.保存字段属性
        this.saveOrUpdateReportField(reportId, param.getFieldList(), delIdVo.getFieldList());
    }

    //数据报表-主表
    private void saveOrUpdateReport(long reportId, ReportVo vo) {
        ReportEntity entity = new ReportEntity();
        entity.setId(reportId);
        entity.setReportName(vo.getReportName());
        entity.setReportCode(vo.getReportCode());
        entity.setGroupReportId(vo.getGroupReportId());
        entity.setJavaConfig(vo.getJavaConfig());
        entity.setDataConfig(vo.getDataConfig());
        entity.setTableConfig(vo.getTableConfig());
        entity.setUpdateTime(LocalDateTime.now());
        entity.setDataSourcesConfig(vo.getDataSourcesConfig());//表视图数据来源
        if (FuncBase.isEmpty(vo.getId())) {//新增
            baseMapper.insert(entity);
            return;
        }
        baseMapper.updateById(entity);
    }

    //数据报表-字段
    private void saveOrUpdateReportField(long reportId, List<ReportFieldVo> fieldList, List<Long> delIdList) {
        if (Func.isNotEmpty(delIdList)) {
            reportFieldMapper.deleteBatchIds(delIdList);//删除
        }

        List<ReportFieldEntity> addList = new ArrayList<>();
        List<ReportFieldEntity> updateList = new ArrayList<>();
        Integer sortNum = 0;
        for (ReportFieldVo vo : fieldList) {
            Long id = vo.getId();
            id = Func.isNotEmpty(id) ? id : IdWorker.getId();

            ReportFieldEntity entity = new ReportFieldEntity();
            entity.setId(id);
            entity.setReportId(reportId);
            entity.setFieldCode(vo.getFieldCode());
            entity.setFieldName(vo.getFieldName());
            entity.setFieldType(vo.getFieldType());
            entity.setSortNum(++sortNum);

            entity.setQueryIsDb(vo.getQueryIsDb());
            entity.setQueryIsWeb(vo.getQueryIsWeb());
            entity.setQueryMode(vo.getQueryMode());
            entity.setDictCode(vo.getDictCode());
            entity.setIsExport(vo.getIsExport());
            entity.setIsShowSort(vo.getIsShowSort());

            if (FuncBase.isEmpty(vo.getId())) {//新增
                addList.add(entity);
            } else if (Func.equals(vo.getIsModify(), YNEnum.Y.getCode())) {//如果是N的时候，不修改
                updateList.add(entity);
            }

        }

        if (Func.isNotEmpty(addList)) {//新增
            batchService.saveBatch(addList, reportFieldMapper, ReportFieldMapper.class, ReportFieldEntity.class);
        }

        if (Func.isNotEmpty(updateList)) {
            batchService.updateBatchById(updateList, reportFieldMapper, ReportFieldMapper.class, ReportFieldEntity.class);
        }

    }


}
