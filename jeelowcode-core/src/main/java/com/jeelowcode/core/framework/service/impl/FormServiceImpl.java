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
import com.jeelowcode.core.framework.config.aspect.enhance.JeeLowCodeAnnoaionAspectjSQL;
import com.jeelowcode.core.framework.config.aspect.enhance.JeeLowCodeAnnotationAspectjJAVA;
import com.jeelowcode.core.framework.config.aspect.enhance.plugin.BaseAdvicePlugin;
import com.jeelowcode.core.framework.config.aspect.enhance.plugin.PluginManager;
import com.jeelowcode.core.framework.config.btncommand.definable.DefinablePluginManager;
import com.jeelowcode.framework.plus.component.DbManager;
import com.jeelowcode.core.framework.entity.*;
import com.jeelowcode.core.framework.mapper.*;
import com.jeelowcode.core.framework.params.DbFormAddOrUpdateParam;
import com.jeelowcode.core.framework.params.PageDbFormParam;
import com.jeelowcode.core.framework.params.TreeParentParam;
import com.jeelowcode.core.framework.params.model.*;
import com.jeelowcode.core.framework.params.vo.*;
import com.jeelowcode.core.framework.params.vo.webconfig.*;
import com.jeelowcode.core.framework.service.*;
import com.jeelowcode.core.framework.utils.Func;
import com.jeelowcode.framework.constants.FrameErrorCodeConstants;
import com.jeelowcode.framework.exception.JeeLowCodeException;
import com.jeelowcode.framework.plus.SqlHelper;
import com.jeelowcode.framework.plus.build.buildmodel.wrapper.SqlInfoQueryWrapper;
import com.jeelowcode.framework.plus.entity.FieldModel;
import com.jeelowcode.framework.utils.adapter.IJeeLowCodeAdapter;
import com.jeelowcode.framework.utils.annotation.JeelowCodeCache;
import com.jeelowcode.framework.utils.component.properties.JeeLowCodeProperties;
import com.jeelowcode.framework.utils.component.redis.JeeLowCodeRedisUtils;
import com.jeelowcode.framework.utils.constant.EnhanceConstant;
import com.jeelowcode.framework.utils.constant.JeeRedisConstants;
import com.jeelowcode.framework.utils.enums.*;
import com.jeelowcode.framework.utils.model.JeeLowCodeDict;
import com.jeelowcode.framework.utils.model.ResultDataModel;
import com.jeelowcode.framework.utils.params.JeeLowCodeDictParam;
import com.jeelowcode.framework.utils.tool.spring.SpringUtils;
import com.jeelowcode.framework.utils.utils.FuncBase;
import com.jeelowcode.framework.utils.utils.JeeLowCodeUtils;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import groovy.lang.GroovyClassLoader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;


/**
 * 表单开发-业务相关
 * 该接口下所有业务不用租户过滤
 */
@Service
public class FormServiceImpl extends ServiceImpl<FormMapper, FormEntity> implements IFormService {

    @Autowired
    private IFrameService frameService;

    @Autowired
    private IFrameSqlService frameSqlService;

    @Autowired
    private FormFieldMapper fieldMapper;

    @Autowired
    private FormIndexMapper indexMapper;

    @Autowired
    private FormFieldDictMapper fieldDictMapper;

    @Autowired
    private FormFieldExportMapper fieldExportMapper;

    @Autowired
    private FormFieldForeignkeyMapper fieldForeignkeyMapper;

    @Autowired
    private FormFieldWebMapper fieldWebMapper;

    @Autowired
    private FormFieldQueryMapper fieldQueryMapper;

    @Autowired
    private FormSummaryMapper summaryMapper;

    @Autowired
    private IFormButtonService formButtonService;

    @Autowired
    private IEnhanceJsService jsService;

    @Autowired
    private IFormButtonService buttonService;

    @Autowired
    private IEnhanceJavaService javaService;

    @Autowired
    private IEnhanceSqlService sqlService;


    @Autowired
    private IJeeLowCodeAdapter jeeLowCodeAdapter;

    @Autowired
    private JeeLowCodeRedisUtils redisUtil;

    @Autowired
    private JeeLowCodeMapper jeeLowCodeMapper;

    @Autowired
    private IJeeLowCodeConfigService jeeLowCodeConfigService;

    @Autowired
    private DbManager dbManager;

    @Autowired
    private AdapterMapper adapterMapper;

    @Autowired
    private IBatchService batchService;

    @Autowired
    private EnhanceJavaMapper enhanceJavaMapper;

    @Autowired
    private EnhanceJsMapper enhanceJsMapper;

    @Autowired
    private EnhanceSqlMapper enhanceSqlMapper;

    @Autowired
    private ReportFieldMapper reportFieldMapper;

    //判断是否是业务表
    @Override
    public boolean isServiceTable(Long dbFormId) {
        FormEntity entity = baseMapper.getById(dbFormId);
        Integer tableClassify = entity.getTableClassify();
        return FuncBase.equals(tableClassify, TableClassifyEnum.SERVICE.getType());
    }

    //获取主题模板
    @Override
    public String getTableThemeTemplate(Long dbFormId) {
        FormEntity entity = baseMapper.getById(dbFormId);
        return entity.getThemeTemplate();
    }

    //获取表类型
    @Override
    public Integer getTableType(Long dbFormId) {
        FormEntity entity = baseMapper.getById(dbFormId);
        Integer tableType = entity.getTableType();
        return tableType;
    }

    //获取认证类型

    @Override
    public AuthTypeEnum getAuthType(Long dbFormId) {
        FormEntity formEntity = baseMapper.getById(dbFormId);
        String dataConfig = formEntity.getDataConfig();
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


    //获取表名称
    @Override
    public String getTableName(Long dbFormId) {
        FormEntity entity = baseMapper.getById(dbFormId);
        if (FuncBase.isEmpty(entity)) {
            throw new JeeLowCodeException("表不存在");
        }
        return entity.getTableName();
    }

    //校验字段是否存在
    @Override
    public boolean fieldCodeIsExist(Long dbFormId, String fieldCode) {
        List<FormFieldEntity> dataList = fieldMapper.getByDbFormIdAndFieldCode(dbFormId, fieldCode);
        return Func.isNotEmpty(dataList);
    }

    //获取所有附表列表
    @Override
    public List<String> getAllSubTableNameList(Long dbFormId) {
        return baseMapper.getAllSubTableNameList(dbFormId);
    }

    //根据表名获取id
    @Override
    public Long getDbFormIdByTableName(String tableName) {
        return baseMapper.getFormIdByTableName(tableName);
    }

    @Override
    public List<Long> getDbFormIdByTableName(List<String> subTableNameList) {
        return baseMapper.getDbFormIdByTableName(subTableNameList);
    }


    //是否是主表
    @Override
    public FormEntity getFormEntityById(Long dbFormId) {
        FormEntity entity = baseMapper.getById(dbFormId);
        if (FuncBase.isEmpty(entity)) {
            throw new JeeLowCodeException("表不存在");
        }
        return entity;
    }

    @Override
    public FormEntity getFormEntityByName(String tableName) {
        return baseMapper.getByTableName(tableName);
    }


    @Override
    public Map<String, Object> getFormCou(Long dbFormId) {
        return jeeLowCodeMapper.getFormCou(dbFormId);
    }


    //获取所有字段列表
    @Override
    public List<FormFieldEntity> getFieldList(Long dbFormId) {
        return fieldMapper.getByDbFormId(dbFormId);
    }

    //获取数据库字段列表
    @Override
    public List<FormFieldEntity> getDbFieldList(Long dbFormId) {
        return fieldMapper.getDbFieldList(dbFormId);
    }

    //获取数据库字段
    @JeelowCodeCache(cacheNames = "'FormServiceImpl:getDbFieldCodeList:' + #dbFormId", reflexClass = DbFieldCodeListModel.class)
    @Override
    public DbFieldCodeListModel getDbFieldCodeList(Long dbFormId) {
        //获取数据库字段
        List<String> selectList = fieldMapper.getDbFieldCodeStrList(dbFormId);

        List<String> asList = BeanUtil.copyToList(selectList, String.class);

        //获取虚拟化字段
        List<FormFieldWebEntity> webEntityList = fieldWebMapper.getFormatConfig(dbFormId);
        //处理虚拟化字段
        Func.handleFieldFormatConfig(selectList, asList, webEntityList);

        DbFieldCodeListModel resulModel = new DbFieldCodeListModel();
        resulModel.setSelectList(selectList);
        resulModel.setAsList(asList);
        return resulModel;
    }

    @Override
    public List<FormFieldDictEntity> getFieldDictList(Long dbFormId) {
        return fieldDictMapper.getByDbFormId(dbFormId);
    }

    @Override
    public List<FormFieldQueryEntity> getFieldQueryList(Long dbFormId) {
        return fieldQueryMapper.getByFormId(dbFormId);
    }

    @Override
    public List<FormFieldWebEntity> getFieldWebList(Long dbFormId) {
        return fieldWebMapper.getByFormId(dbFormId);
    }

    //获取索引列表
    @Override
    public List<FormIndexEntity> getIndexList(Long dbFormId) {
        return indexMapper.getByDbFormId(dbFormId);
    }


    //保存表单开发信息
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long saveDbFormConfig(DbFormAddOrUpdateParam param) {
        long formId = IdWorker.getId();
        this.saveOrupdateDbFormConfig(param, formId);
        return formId;
    }

    //修改表单开发配置
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateDbFormConfig(DbFormAddOrUpdateParam param) {
        this.saveOrupdateDbFormConfig(param, param.getDbForm().getId());
    }


    private void saveOrupdateDbFormConfig(DbFormAddOrUpdateParam param, Long formId) {
        //1.保存主表
        this.saveOrUpdateDbForm(formId, param.getDbForm());

        DbFormDeletedVo delIdVo = param.getDelIdVo();
        if (Func.isEmpty(delIdVo)) {
            delIdVo = new DbFormDeletedVo();
        }

        //2.保存字段属性
        Long desformWebId = param.getDbForm().getDesformWebId();
        Long groupDbformId = param.getDbForm().getGroupDbformId();
        this.saveOrUpdateDbFormField(formId, desformWebId, groupDbformId, param.getFieldList(), delIdVo.getFieldList());

        //3.保存字典属性
        this.saveOrUpdateDbFormDict(formId, param.getDictList(), delIdVo.getDictList());

        //4。保存导出属性
        this.saveOrUpdateDbFormExport(formId, param.getExportList(), delIdVo.getExportList());

        //5.保存外键配置
        this.saveOrUpdateDbFormForeignkey(formId, param.getForeignkeyList(), delIdVo.getForeignkeyList());

        //6.保存页面属性配置
        this.saveOrUpdateDbFormWeb(formId, param.getWebList(), delIdVo.getWebList());

        //7.保存查询配置
        this.saveOrUpdateDbFormQuery(formId, param.getQueryList(), delIdVo.getQueryList());

        //8.绑定主附表关系
        this.bindMainTable(param.getDbForm(), param.getForeignkeyList());

        //9.保存索引
        this.saveOrUpdateDbFormIndex(formId, param.getIndexList(), delIdVo.getIndexList());

        //10.保存统计
        this.saveOrUpdateDbFormSummary(formId, param.getSummaryList(), delIdVo.getSummaryList());

        //清空缓存
        String redisKeyPreFix = JeeRedisConstants.JEELOWCODE_DBFORM;
        Set<String> keys = redisUtil.keys(redisKeyPreFix);
        if (Func.isNotEmpty(keys)) {
            redisUtil.delList(keys);
        }
    }

    //删除 - 表单开发
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteDbFormConfig(List<Long> dbFormIdList, boolean dropTableFlag) {
        dbFormIdList.stream().forEach(dbFormId -> {
            FormEntity formEntity = baseMapper.selectById(dbFormId);
            if (FuncBase.isEmpty(formEntity)) {
                return;
            }

            if (dropTableFlag) {//删除实体表
                String tableName = formEntity.getTableName();
                frameService.dropTable(tableName);//删除表
            }

            //解除主附表关系
            this.unBindMainTable(formEntity);

            baseMapper.deleteById(dbFormId);//删除主表
            //1.删除 - 属性表
            fieldMapper.delByFormId(dbFormId);
            //2.删除 - 字典表
            fieldDictMapper.delByDbformId(dbFormId);
            //3.删除 - 导出表
            fieldExportMapper.delByDbformId(dbFormId);
            //4.删除 - 外键表
            fieldForeignkeyMapper.delByDbformId(dbFormId);
            //5.查询
            fieldQueryMapper.delByDbformId(dbFormId);
            //6.删除 - 页面表
            fieldWebMapper.delByDbformId(dbFormId);
            //7.删除 - 索引表
            indexMapper.delByDbFormId(dbFormId);
            //8.删除 - 统计
            summaryMapper.delByDbFormId(dbFormId);
            //9.删除 - java增强
            enhanceJavaMapper.delByDbFormId(dbFormId);
            //10.删除 - js增强
            enhanceJsMapper.delByDbFormId(dbFormId);
            //11.删除 - sql增强
            enhanceSqlMapper.delByDbFormId(dbFormId);
        });

    }


    //复制表 - 表单开发
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void copyDbFormConfig(Long dbformId, String tableName) {
        //复制主表
        FormEntity entity = baseMapper.selectById(dbformId);
        DbFormVo dbFormVo = BeanUtil.copyProperties(entity, DbFormVo.class);
        dbFormVo.setId(null);
        dbFormVo.setIsDbSync(null);
        dbFormVo.setTableName(tableName);
        dbFormVo.setSubTableListStr(null);
        dbFormVo.setTableDescribe(dbFormVo.getTableDescribe() + "_copy");


        //获取字段表
        List<FormFieldEntity> fieldEntityList = fieldMapper.getByDbFormId(dbformId);
        List<DbFormFieldVo> fieldList = Func.copyDbFormFieldList(fieldEntityList, DbFormFieldVo.class);

        //字典表
        List<FormFieldDictEntity> dictEntityList = fieldDictMapper.getByDbFormId(dbformId);
        List<DbFormFieldDictVo> dictList = Func.copyDbFormFieldList(dictEntityList, DbFormFieldDictVo.class);

        //导出表
        List<FormFieldExportEntity> exportEntityList = fieldExportMapper.getByFormId(dbformId);
        List<DbFormFieldExportVo> exportList = Func.copyDbFormFieldList(exportEntityList, DbFormFieldExportVo.class);

        //外键表
        List<FormFieldForeignkeyEntity> foreignkeyEntityList = fieldForeignkeyMapper.getByFormId(dbformId);
        List<DbFormFieldForeignkeyVo> foreignkeyList = Func.copyDbFormFieldList(foreignkeyEntityList, DbFormFieldForeignkeyVo.class);
        foreignkeyList.stream().forEach(vo -> {
            vo.setMainField(null);
            vo.setMainTable(null);
        });

        //界面
        List<FormFieldWebEntity> webEntityList = fieldWebMapper.getByFormId(dbformId);
        List<DbFormFieldWebVo> webList = Func.copyDbFormFieldList(webEntityList, DbFormFieldWebVo.class);

        //查询
        List<FormFieldQueryEntity> queryEntityList = fieldQueryMapper.getByFormId(dbformId);
        List<DbFormFieldQueryVo> queryList = Func.copyDbFormFieldList(queryEntityList, DbFormFieldQueryVo.class);

        //统计
        List<FormSummaryEntity> summaryEntityList = summaryMapper.getByFormId(dbformId);
        List<DbFormSummaryVo> summaryList = Func.copyDbFormFieldList(summaryEntityList, DbFormSummaryVo.class);

        //索引
       /* List<FormIndexEntity> indexEntityList = indexMapper.getByDbFormId(dbformId);
        List<DbFormIndexVo> indexList = Func.copyDbFormFieldList(indexEntityList, DbFormIndexVo.class);
*/

        //封装DbFormAddOrUpdateParam 参数，统一走save方法
        DbFormAddOrUpdateParam param = new DbFormAddOrUpdateParam();
        param.setDbForm(dbFormVo);
        param.setFieldList(fieldList);
        param.setDictList(dictList);
        param.setExportList(exportList);
        param.setForeignkeyList(foreignkeyList);
        param.setWebList(webList);
        param.setQueryList(queryList);
        param.setIndexList(null);//索引不复制，oracle等其他数据库下，索引名称是全局的
        param.setSummaryList(summaryList);

        //新主键
        Long newDbformId = this.saveDbFormConfig(param);

        //处理js增强
        jsService.copy(dbformId, newDbformId);

        //处理java增强
        javaService.copy(dbformId, newDbformId, entity.getTableName(), tableName);

        //处理sql增强
        sqlService.copy(dbformId, newDbformId);

        //处理按钮增强
        buttonService.copy(dbformId, newDbformId);

    }


    //所有模块
    @Override
    public DbFormConfigVo getDetailDbFormConfig(Long dbFormId) {
        List<DbFormTypeEnum> typeList = new ArrayList<>();
        typeList.add(DbFormTypeEnum.ALL);
        return this.getDetailDbFormConfig(dbFormId, typeList);
    }

    //详情 - 表单开发  定制模块
    @Override
    public DbFormConfigVo getDetailDbFormConfig(Long dbFormId, List<DbFormTypeEnum> typeList) {
        return this.getDetailDbFormConfig(dbFormId, typeList, null);
    }

    @Override
    public DbFormConfigVo getDetailDbFormConfig(Long dbFormId, List<DbFormTypeEnum> typeList, List<String> selfFieldList) {//自定义列
        DbFormConfigVo resultVo = new DbFormConfigVo();

        IFormService proxyFormService = SpringUtils.getBean(IFormService.class);
        //表单配置
        FormEntity formEntity = proxyFormService.getFormEntityById(dbFormId);
        resultVo.setDbForm(formEntity);


        //字段信息
        List<FormFieldEntity> formFieldList = fieldMapper.getByDbFormId(dbFormId);
        resultVo.setFieldList(formFieldList);

        if (typeList.contains(DbFormTypeEnum.ALL) || typeList.contains(DbFormTypeEnum.DICT)) {//表单开发-页面字典配置表
            List<FormFieldDictEntity> formFieldDictList = fieldDictMapper.getByDbFormId(dbFormId);
            resultVo.setDictList(formFieldDictList);
        }
        if (typeList.contains(DbFormTypeEnum.ALL) || typeList.contains(DbFormTypeEnum.EXPORT)) {//表单开发-导出配置表
            List<FormFieldExportEntity> formFieldExportList = fieldExportMapper.getByFormId(dbFormId);
            resultVo.setExportList(formFieldExportList);
        }
        if (typeList.contains(DbFormTypeEnum.ALL) || typeList.contains(DbFormTypeEnum.FOREIGNKEY)) {//表单开发-导出配置表
            List<FormFieldForeignkeyEntity> fieldForeignkeyList = fieldForeignkeyMapper.getByFormId(dbFormId);
            resultVo.setForeignkeyList(fieldForeignkeyList);
        }
        if (typeList.contains(DbFormTypeEnum.ALL) || typeList.contains(DbFormTypeEnum.WEB)) {//表单开发-页面字段表
            List<FormFieldWebEntity> formFieldWebList = fieldWebMapper.getByFormId(dbFormId);
            resultVo.setWebList(formFieldWebList);
        }
        if (typeList.contains(DbFormTypeEnum.ALL) || typeList.contains(DbFormTypeEnum.QUERY)) {//表单开发-查询配置
            List<FormFieldQueryEntity> formFieldQueryList = fieldQueryMapper.getByFormId(dbFormId);
            resultVo.setQueryList(formFieldQueryList);
        }

        if (typeList.contains(DbFormTypeEnum.ALL) || typeList.contains(DbFormTypeEnum.SUMMARY)) {//表单开发-统计配置
            List<FormSummaryEntity> summaryTopList = summaryMapper.getTopSummaryList(dbFormId);
            resultVo.setSummaryTopList(summaryTopList);

            List<FormSummaryEntity> summaryBottomList = summaryMapper.getBottomSummaryList(dbFormId);
            resultVo.setSummaryBottomList(summaryBottomList);
        }
        if (typeList.contains(DbFormTypeEnum.ALL) || typeList.contains(DbFormTypeEnum.INDEX)) {//表单开发-索引
            List<FormIndexEntity> indexEntityList = indexMapper.getByDbFormId(dbFormId);
            resultVo.setIndexList(indexEntityList);
        }

        //不为空，说明有自定义列，需要剔除部分
        if (FuncBase.isNotEmpty(selfFieldList)) {
            List<FormFieldEntity> fieldEntityList = resultVo.getFieldList();
            Iterator<FormFieldEntity> iterator = fieldEntityList.iterator();
            while (iterator.hasNext()) {
                FormFieldEntity next = iterator.next();
                String fieldCode = next.getFieldCode();
                if (!selfFieldList.contains(fieldCode)) {
                    iterator.remove();//没有在指定列里面，则剔除
                }
            }
        }

        return resultVo;
    }


    //分页 - 表单开发
    @Override
    public IPage<FormEntity> getPageDbFormConfig(PageDbFormParam param, Page page) {
        LambdaQueryWrapper<FormEntity> wrapper = new LambdaQueryWrapper<>();
        wrapper.like(FuncBase.isNotEmpty(param.getTableName()), FormEntity::getTableName, param.getTableName());
        wrapper.like(FuncBase.isNotEmpty(param.getTableDescribe()), FormEntity::getTableDescribe, param.getTableDescribe());
        wrapper.eq(FuncBase.isNotEmpty(param.getTableType()), FormEntity::getTableType, param.getTableType());
        wrapper.eq(FuncBase.isNotEmpty(param.getTableClassify()), FormEntity::getTableClassify, param.getTableClassify());
        wrapper.eq(FuncBase.isNotEmpty(param.getIsDbSync()), FormEntity::getIsDbSync, param.getIsDbSync());
        wrapper.eq(FuncBase.isNotEmpty(param.getId()), FormEntity::getId, param.getId());
        wrapper.eq(FuncBase.isNotEmpty(param.getGroupDbformId()), FormEntity::getGroupDbformId, param.getGroupDbformId());
        wrapper.orderByDesc(FormEntity::getId);

        //查询部分字段
        wrapper.select(FormEntity::getId,
                FormEntity::getTableType,
                FormEntity::getTableName,
                FormEntity::getTableDescribe,
                FormEntity::getTableClassify,
                FormEntity::getIsDbSync);

        IPage<FormEntity> pages = baseMapper.selectPage(page, wrapper);
        return pages;
    }

    //获取页面配置
    @Override
    public WebConfigVo getWebConfig(Long dbFormId) {
        return this.getWebConfig(dbFormId, null);//所有字段
    }

    @Override
    public WebConfigVo getWebConfig(Long dbFormId, List<String> selfFieldList) {
        List<DbFormTypeEnum> typeList = new ArrayList<>();
        typeList.add(DbFormTypeEnum.WEB);
        typeList.add(DbFormTypeEnum.DICT);
        typeList.add(DbFormTypeEnum.QUERY);
        typeList.add(DbFormTypeEnum.EXPORT);
        typeList.add(DbFormTypeEnum.SUMMARY);
        if (Func.equals(TableTypeEnum.SUB.getType(), this.getTableType(dbFormId))) {//如果是附表，则吧外键查出来
            typeList.add(DbFormTypeEnum.FOREIGNKEY);
        }

        //页面基本配置
        DbFormConfigVo dbFormConfig = this.getDetailDbFormConfig(dbFormId, typeList, selfFieldList);

        //获取按钮增强
        List<FormButtonEntity> buttonList = formButtonService.getButtonList(dbFormId);

        //获取Js增强
        List<EnhanceJsEntity> jsList = jsService.getJsList(dbFormId);

        //转为一条返回给前端
        List<FormFieldEntity> fieldList = dbFormConfig.getFieldList();//字段列表
        List<FormFieldDictEntity> dictList = dbFormConfig.getDictList();//字典列表
        List<FormFieldWebEntity> webList = dbFormConfig.getWebList();//页面列表
        List<FormFieldQueryEntity> queryList = dbFormConfig.getQueryList();//查询列表
        List<FormFieldExportEntity> exportList = dbFormConfig.getExportList();//导出列表
        List<FormSummaryEntity> summaryTopList = dbFormConfig.getSummaryTopList();
        List<FormSummaryEntity> summaryBottomList = dbFormConfig.getSummaryBottomList();//底部统计配置
        List<FormFieldForeignkeyEntity> foreignkeyList = dbFormConfig.getForeignkeyList();//外键

        //转为map
        Map<String, FormFieldDictEntity> dictMap = dictList.stream()
                .collect(Collectors.toMap(FormFieldDictEntity::getFieldCode, Function.identity()));

        //转为map
        Map<String, FormFieldWebEntity> webMap = webList.stream()
                .collect(Collectors.toMap(FormFieldWebEntity::getFieldCode, Function.identity()));

        //转为map
        Map<String, FormFieldQueryEntity> queryMap = queryList.stream()
                .collect(Collectors.toMap(FormFieldQueryEntity::getFieldCode, Function.identity()));

        //转为map
        Map<String, FormFieldExportEntity> exportMap = exportList.stream()
                .collect(Collectors.toMap(FormFieldExportEntity::getFieldCode, Function.identity()));

        //转为map
        Map<String, FormSummaryEntity> summaryBottomMap = summaryBottomList.stream()
                .collect(Collectors.toMap(FormSummaryEntity::getFieldCode, Function.identity()));

        Map<String, FormFieldForeignkeyEntity> foreignkeyMap = foreignkeyList.stream()
                .collect(Collectors.toMap(FormFieldForeignkeyEntity::getFieldCode, Function.identity()));


        List<WebConfigFieldVo> formFieldEntityExpList = fieldList.stream()
                .map(fieldEntity -> {
                    WebConfigFieldVo fieldEntityExp = BeanUtil.copyProperties(fieldEntity, WebConfigFieldVo.class);

                    FormFieldDictEntity dictEntity = dictMap.get(fieldEntity.getFieldCode());
                    fieldEntityExp.setDictEntity(BeanUtil.copyProperties(dictEntity, WebConfigFieldDictVo.class));

                    FormFieldWebEntity webEntity = webMap.get(fieldEntity.getFieldCode());
                    fieldEntityExp.setWebEntity(BeanUtil.copyProperties(webEntity, WebConfigFieldWebVo.class));

                    FormFieldQueryEntity queryEntity = queryMap.get(fieldEntity.getFieldCode());
                    fieldEntityExp.setQueryEntity(BeanUtil.copyProperties(queryEntity, WebConfigFieldQueryVo.class));

                    FormFieldExportEntity exportEntity = exportMap.get(fieldEntity.getFieldCode());
                    fieldEntityExp.setExportEntity(BeanUtil.copyProperties(exportEntity, WebConfigFieldExportVo.class));

                    FormSummaryEntity bottomSummaryEntity = summaryBottomMap.get(fieldEntity.getFieldCode());
                    fieldEntityExp.setSummaryEntity(BeanUtil.copyProperties(bottomSummaryEntity, DbFormSummaryVo.class));

                    FormFieldForeignkeyEntity foreignkeyEntity = foreignkeyMap.get(fieldEntity.getFieldCode());
                    fieldEntityExp.setForeignkeyEntity(BeanUtil.copyProperties(foreignkeyEntity, WebConfigFieldForeignkeyVo.class));

                    return fieldEntityExp;
                })
                .collect(Collectors.toList());

        //如果是主附表，则需要把附表的id查询出来
        List<String> subTableNameList = this.getAllSubTableNameList(dbFormId);
        List<Long> subDbFormIdList = new ArrayList<>();
        if (Func.isNotEmpty(subTableNameList)) {
            subDbFormIdList = this.getDbFormIdByTableName(subTableNameList);
        }

        //封装结果返回
        WebConfigVo webConfigVo = new WebConfigVo();
        webConfigVo.setFieldList(formFieldEntityExpList);
        webConfigVo.setDbForm(BeanUtil.copyProperties(dbFormConfig.getDbForm(), WebConfigFormVo.class));
        webConfigVo.setButtonList(BeanUtil.copyToList(buttonList, WebConfigButtonVo.class));
        webConfigVo.setJsList(BeanUtil.copyToList(jsList, WebConfigJsVo.class));
        webConfigVo.setSubDbFormIdList(subDbFormIdList);//主附表信息
        boolean summaryTopOpenFlag = false;
        if (Func.isNotEmpty(summaryTopList)) {
            summaryTopOpenFlag = summaryTopList.stream()
                    .anyMatch(top -> Func.equals(top.getSummaryShow(),YNEnum.Y.getCode()));
        }
        webConfigVo.setSummaryTopOpenFlag(summaryTopOpenFlag);//顶部是否开启
        return webConfigVo;
    }

    //获取字段类型
    @Override
    public JeeLowCodeFieldTypeEnum getFieldTypeEnum(Long dbFormId, String fieldCode) {
        List<FormFieldEntity> formFieldEntityList = fieldMapper.getByDbFormIdAndFieldCode(dbFormId, fieldCode);
        FormFieldEntity formFieldEntity = formFieldEntityList.get(0);
        String fieldType = formFieldEntity.getFieldType();
        return JeeLowCodeFieldTypeEnum.getByFieldType(fieldType);
    }

    /**
     * 获取字典数据
     *
     * @param dbFormId
     * @param fieldCode
     * @return key=1  value=男
     */
    @Override
    public Map<String, String> getDictKeyLabelMap(Long dbFormId, String fieldCode) {
        Map<String, String> dictMap = new LinkedHashMap<>();

        FormFieldDictEntity dictEntity = fieldDictMapper.getFormFieldSysDictEntity(dbFormId, fieldCode);
        if (FuncBase.isEmpty(dictEntity)) {
            return dictMap;
        }

        String dictType = dictEntity.getDictType();
        String dictCode = dictEntity.getDictCode();
        String dictTableDbFormId = dictEntity.getDictTable();
        String dictText = dictEntity.getDictText();

        if (FuncBase.isNotEmpty(dictType) && FuncBase.equals(dictType, DictTypeEnum.DICT.getType())) {//单纯就是字典
            JeeLowCodeDictParam param = new JeeLowCodeDictParam();
            param.setDictCodeList(FuncBase.toStrList(dictCode));
            List<JeeLowCodeDict> dictList = jeeLowCodeAdapter.getDictList(param);
            if (FuncBase.isEmpty(dictList)) {
                return dictMap;
            }
            //
            List<JeeLowCodeDict.DictData> dataList = dictList.get(0).getDataList();
            dictMap = dataList.stream()
                    .collect(Collectors.toMap(
                            JeeLowCodeDict.DictData::getVal,
                            JeeLowCodeDict.DictData::getLabel
                    ));
        } else if (FuncBase.isNotEmpty(dictType) && FuncBase.equals(dictType, DictTypeEnum.TABLE.getType())) {//表数据
            List<String> selectList = new ArrayList<>();
            selectList.add(dictCode);
            selectList.add(dictText);

            Map<String, Object> params = new HashMap<>();
            params.put(ParamEnum.DICT_TABLE_FIELD.getCode(), selectList);
            params.put(ParamEnum.PAGE_NO.getCode(), 1);
            params.put(ParamEnum.PAGE_SIZE.getCode(), 1000);
            ResultDataModel model = frameService.getDataList(Func.toLong(dictTableDbFormId), params);
            if (Func.isNotEmpty(model) && Func.isNotEmpty(model.getRecords())) {
                dictMap = model.getRecords().stream()
                        .collect(Collectors.toMap(
                                dataMap -> JeeLowCodeUtils.getMap2Str(dataMap, dictCode),
                                dataMap -> JeeLowCodeUtils.getMap2Str(dataMap, dictText)
                        ));

            }
            //自定义查询


        }

        return dictMap;
    }

    //格式化数据列表（回显数据，处理时间等）
    @Override
    public void formatDataList(Long dbformId, List<Map<String, Object>> dataMapList) {
        if (FuncBase.isEmpty(dataMapList)) {
            return;
        }

        List<FormFieldEntity> fieldList = this.getFieldList(dbformId);
        //字典集合
        Map<String, Map<String, String>> dictMaps = new HashMap<>();
        List<String> keyList = new ArrayList<>();
        for (FormFieldEntity formFieldEntity : fieldList) {
            String fieldCode = formFieldEntity.getFieldCode();
            Map<String, String> dictMap = this.getDictKeyLabelMap(dbformId, fieldCode);//字典列表
            if (FuncBase.isEmpty(dictMap)) {
                continue;
            }
            dictMaps.put(fieldCode, dictMap);
            keyList.add(fieldCode);
        }
        //数据里面会存在id，或者字典等，如：1=男，需要转为文字
        dataMapList.forEach(dataMap -> {
            for (String key : keyList) {
                String val = JeeLowCodeUtils.getMap2Str(dataMap, key);
                if (FuncBase.isEmpty(val)) {
                    continue;
                }
                Map<String, String> valMap = dictMaps.get(key);
                String valStr = valMap.get(val);
                if (Func.isEmpty(valStr) && val.contains(",")) {
                    List<String> valList = Func.toStrList(val);
                    for (String tmpVal : valList) {//有可能是多选的情况下111,222,333
                        String tmpValStr = valMap.get(tmpVal);
                        if (Func.isEmpty(tmpValStr)) {
                            continue;
                        }
                        if (Func.isEmpty(valStr)) {
                            valStr = tmpValStr;
                        } else {
                            valStr += "、" + tmpValStr;
                        }

                    }
                }

                if (FuncBase.isEmpty(valStr)) {
                    continue;
                }
                dataMap.put(key, valStr);
            }

            // 转换并格式化所有值
            Map<String, String> formattedMap = dataMap.entrySet().stream()
                    .collect(Collectors.toMap(
                            Map.Entry::getKey,
                            entry -> JeeLowCodeUtils.formatDate(entry.getValue())
                    ));

            // 使用格式化后的映射替换原始映射
            dataMap.clear();
            dataMap.putAll(formattedMap);
        });

    }


    //获取字段 名称：code
    @Override
    public Map<String, String> getFieldNameAndCode(Long dbFormId) {
        return fieldMapper.getFieldNameAndCodeMap(dbFormId);
    }

    //获取所有表
    @Override
    public List<AllTableModel> getAllTable(String systemFlag) {

        LambdaQueryWrapper<FormEntity> wrapper = new LambdaQueryWrapper<>();
        wrapper.select(FormEntity::getId, FormEntity::getTableName, FormEntity::getTableDescribe, FormEntity::getTableType, FormEntity::getSubTableListStr);//获取表名和表描述
        wrapper.orderByDesc(FormEntity::getId);
        List<FormEntity> formList = baseMapper.selectList(wrapper);

        //获取所有字段
        List<FormFieldEntity> fieldList = fieldMapper.getAllList();

        //组装数据
        Map<Long, AllTableModel> tableMap = new HashMap<>();

        Map<Long, FormEntity> formMap = formList.stream()
                .collect(Collectors.toMap(FormEntity::getId, Function.identity()));
        fieldList.stream()
                .filter(formField -> formMap.containsKey(formField.getDbformId()))
                .collect(Collectors.groupingBy(FormFieldEntity::getDbformId))
                .forEach((dbformId, formFields) -> {
                    FormEntity form = formMap.get(dbformId);
                    List<AllTableFieldModel> fieldModelList = formFields.stream()
                            .map(formField -> {
                                AllTableFieldModel fieldModel = new AllTableFieldModel();
                                fieldModel.setFieldCode(formField.getFieldCode());
                                fieldModel.setFieldName(formField.getFieldName());
                                fieldModel.setFieldType(formField.getFieldType());
                                return fieldModel;
                            })
                            .collect(Collectors.toList());

                    AllTableModel tableModel = new AllTableModel();
                    tableModel.setTableId(FuncBase.toStr(form.getId()));
                    tableModel.setTableName(form.getTableName());
                    tableModel.setTableDescribe(form.getTableDescribe());
                    tableModel.setSubTableListStr(form.getSubTableListStr());
                    tableModel.setFieldModelList(fieldModelList);
                    tableModel.setTableType(form.getTableType());

                    //处理授权类型
                    String dataConfig = form.getDataConfig();
                    List<String> dataConfigList = FuncBase.toStrList(dataConfig);
                    for (String type : dataConfigList) {
                        AuthTypeEnum authTypeEnum = AuthTypeEnum.getByType(type);
                        if (Func.isEmpty(authTypeEnum)) {
                            continue;
                        }
                        tableModel.setAuthType(authTypeEnum.getType());
                        break;
                    }

                    tableMap.put(dbformId, tableModel);
                });

        List<AllTableModel> resultList = new ArrayList<>();
        for (FormEntity formEntity : formList) {
            Long dbformId = formEntity.getId();
            if (!tableMap.containsKey(dbformId)) {
                continue;
            }
            AllTableModel allTableModel = tableMap.get(dbformId);
            resultList.add(allTableModel);
        }

        //处理系统表
        if (Func.isNotEmpty(systemFlag) && Func.equals(systemFlag, YNEnum.Y.getCode())) {

            List<TableModel> systemTableList = new ArrayList<>();
            systemTableList.add(new TableModel("system_dept", "部门信息"));
            systemTableList.add(new TableModel("system_users", "用户信息"));


            systemTableList.stream().forEach(tableModel -> {
                String tableName = tableModel.getTableName();
                String tableDescribe = tableModel.getTableDescribe();


                String tableFieldCommentSql = SqlHelper.getTableFieldComment(tableName);
                //获取备注字段
                List<Map<String, Object>> dataMapList = frameSqlService.getSimpleData(tableFieldCommentSql);
                Map<String, String> remarkMap = Func.dataMapList2ResultSet(dataMapList);

                Map<String, FieldModel> dbColumnMetaMap = dbManager.getDbColumnMetaMap(tableName, remarkMap);
                //封装为
                List<AllTableFieldModel> fieldModelList = new ArrayList<>();
                for (String key : dbColumnMetaMap.keySet()) {
                    FieldModel fieldModel = dbColumnMetaMap.get(key);
                    AllTableFieldModel allTableFieldModel = BeanUtil.copyProperties(fieldModel, AllTableFieldModel.class);
                    List<JeeLowCodeFieldTypeEnum> selectFieldTypeList = fieldModel.getJavaFieldTypeList();//可能多个，oracle number的话对应多个
                    Integer fieldPointLen = fieldModel.getFieldPointLen();
                    if (fieldPointLen > 0) {
                        allTableFieldModel.setFieldType(JeeLowCodeFieldTypeEnum.BIGDECIMAL.getFieldType());
                    } else {
                        allTableFieldModel.setFieldType(selectFieldTypeList.get(0).getFieldType());
                    }
                    fieldModelList.add(allTableFieldModel);
                    // 处理键和值
                }

                AllTableModel allTableModel = new AllTableModel();
                allTableModel.setTableId(tableName);
                allTableModel.setTableName(tableName);
                allTableModel.setTableDescribe(tableDescribe);
                allTableModel.setFieldModelList(fieldModelList);
                if (Func.isNotEmpty(resultList)) {
                    resultList.add(0, allTableModel);//放到第一位
                } else {
                    resultList.add(allTableModel);
                }

            });
        }

        return resultList;
    }

    //获取字典表显示列
    @Override
    public List<String> getDictTableFieldList(Long dbFormId, List<String> fieldList) {
        //获取本表的所有字段
        DbFieldCodeListModel dbFieldCodeList = this.getDbFieldCodeList(dbFormId);
        List<String> asList = dbFieldCodeList.getAsList();
        List<String> selectList = dbFieldCodeList.getSelectList();

        List<String> resultList = new ArrayList<>();
        Iterator<String> iterator = fieldList.iterator();
        while (iterator.hasNext()) {
            String next = iterator.next();
            for (int i = 0; i < asList.size(); i++) {
                String asStr = asList.get(i);
                String selectStr = selectList.get(i);
                if (Func.equals(next, asStr)) {//一样
                    resultList.add(selectStr);
                }
            }
        }

        if (!resultList.contains("id")) {//把id加上
            resultList.add("id");
        }
        return resultList;
    }

    /**
     * 获取外键列-只允许有一个外键
     *
     * @param dbFormId
     * @return
     */
    @Override
    public FormFieldForeignkeyEntity getFieldForeignkeyEntity(Long dbFormId, String mainTable) {
        return fieldForeignkeyMapper.getByFormIdAndMainTable(dbFormId, mainTable);
    }

    //新增or修改 表单开发-开发属性
    private void saveOrUpdateDbForm(long formId, DbFormVo vo) {

        String dbSync = "";

        Integer tableClassify = vo.getTableClassify();
        if (FuncBase.equals(tableClassify, TableClassifyEnum.SERVICE.getType())) {//业务表
            if (FuncBase.isEmpty(vo.getId())) {//新增
                dbSync = YNEnum.N.getCode();
            }
        } else {//其他为显示表
            dbSync = YNEnum.Y.getCode();
        }


        FormEntity entity = new FormEntity();
        entity.setId(formId);
        entity.setTableName(vo.getTableName());
        entity.setTableDescribe(vo.getTableDescribe());
        entity.setTableType(vo.getTableType());
        entity.setTableClassify(vo.getTableClassify());
        entity.setTableIdType(vo.getTableIdType());
        entity.setTableSelect(vo.getTableSelect());
        if (FuncBase.isNotEmpty(dbSync)) {
            entity.setIsDbSync(dbSync);//默认的未同步
        }
        entity.setIsDesForm(vo.getIsDesForm());
        entity.setSubTableMapping(vo.getSubTableMapping());
        entity.setSubTableSort(vo.getSubTableSort());
        entity.setSubTableTitle(vo.getSubTableTitle());
        entity.setThemeTemplate(vo.getThemeTemplate());
        entity.setDesformWebId(vo.getDesformWebId());
        entity.setTreeStyle(vo.getTreeStyle());
        entity.setTreeLabelField(vo.getTreeLabelField());
        entity.setTreeMode(vo.getTreeMode());
        entity.setOperateMenuStyle(vo.getOperateMenuStyle());
        entity.setGroupDbformId(vo.getGroupDbformId());
        entity.setFormStyle(Integer.valueOf((vo.getFormStyle())));
        entity.setOrderbyConfig(vo.getOrderbyConfig());
        entity.setWhereConfig(vo.getWhereConfig());
        entity.setSubTableListStr(vo.getSubTableListStr());
        entity.setViewDefaultField(vo.getViewDefaultField());
        entity.setDataConfig(vo.getDataConfig());
        entity.setBasicFunction(vo.getBasicFunction());
        entity.setBasicConfig(vo.getBasicConfig());
        entity.setTableConfig(vo.getTableConfig());
        entity.setUpdateTime(LocalDateTime.now());
        entity.setDataSourcesConfig(vo.getDataSourcesConfig());//表视图数据来源
        entity.setTableStyle(vo.getTableStyle());//单表样式
        if (FuncBase.isEmpty(vo.getId())) {//新增
            baseMapper.insert(entity);
        } else {//修改
            baseMapper.updateById(entity);
        }

    }


    //新增or修改 表单开发-表单字段属性
    private void saveOrUpdateDbFormField(Long formId, Long desformWebId, Long groupDbformId, List<DbFormFieldVo> fieldList, List<Long> delIdList) {
        List<FormFieldEntity> oldFieldList = fieldMapper.getByDbFormId(formId);

        if (Func.isNotEmpty(delIdList)) {
            fieldMapper.deleteBatchIds(delIdList);//删除
        }

        List<FormFieldEntity> addList=new ArrayList<>();
        List<FormFieldEntity> updateList=new ArrayList<>();
        Integer sortNum = 0;
        for (DbFormFieldVo vo : fieldList) {
            Long id = vo.getId();
            id = Func.isNotEmpty(id) ? id : IdWorker.getId();

            FormFieldEntity entity = new FormFieldEntity();
            entity.setId(id);
            entity.setDbformId(formId);
            entity.setFieldCode(vo.getFieldCode());
            entity.setFieldName(vo.getFieldName());
            entity.setFieldLen(vo.getFieldLen());
            entity.setFieldPointLen(vo.getFieldPointLen());
            entity.setFieldDefaultVal(vo.getFieldDefaultVal());
            entity.setFieldType(vo.getFieldType());
            entity.setFieldRemark(vo.getFieldRemark());
            entity.setIsPrimaryKey(vo.getIsPrimaryKey());
            entity.setIsNull(vo.getIsNull());
            entity.setIsDb(vo.getIsDb());
            entity.setSortNum(++sortNum);
            if (FuncBase.isEmpty(vo.getId())) {//新增
                addList.add(entity);
            } else if (Func.equals(vo.getIsModify(), YNEnum.Y.getCode())) {//如果是N的时候，不修改
                updateList.add(entity);
            }

        }

        if(Func.isNotEmpty(addList)){
            batchService.saveBatch(addList,fieldMapper,FormFieldMapper.class,FormFieldEntity.class);
        }

        if(Func.isNotEmpty(updateList)){
            batchService.updateBatchById(updateList,fieldMapper,FormFieldMapper.class,FormFieldEntity.class);
        }

        //如果不是业务表则不需要进行同步
        boolean serviceTableFlag = this.isServiceTable(formId);
        if (!serviceTableFlag) {
            return;
        }

        List<FormFieldEntity> newFieldList = fieldMapper.getByDbFormId(formId);
        //判断新-旧列是否一致
        boolean isSync = Func.dbFormFieldEquals(oldFieldList, newFieldList);

        if (!isSync) {//存在在不一样，则需要同步
            FormEntity saveOrUpdateEntity = new FormEntity();
            saveOrUpdateEntity.setIsDbSync(YNEnum.N.getCode());//默认的未同步
            saveOrUpdateEntity.setId(formId);
            saveOrUpdateEntity.setDesformWebId(desformWebId);
            saveOrUpdateEntity.setGroupDbformId(groupDbformId);
            baseMapper.updateById(saveOrUpdateEntity);
        }

    }

    //新增or修改 表单开发-保存字典
    private void saveOrUpdateDbFormDict(long formId, List<DbFormFieldDictVo> newDictList, List<Long> delIdList) {
        if (Func.isNotEmpty(delIdList)) {
            fieldDictMapper.deleteBatchIds(delIdList);
        }
        List<FormFieldDictEntity> addList=new ArrayList<>();
        List<FormFieldDictEntity> updateList=new ArrayList<>();

        //处理新值
        newDictList.stream().forEach(vo -> {
            String fieldCode = vo.getFieldCode();

            //获取id,判断是否是旧的
            Long id = vo.getId();
            id = Func.isNotEmpty(id) ? id : IdWorker.getId();

            FormFieldDictEntity saveOrUpdateEntity = new FormFieldDictEntity();
            saveOrUpdateEntity.setId(id);
            saveOrUpdateEntity.setDbformId(formId);
            saveOrUpdateEntity.setFieldCode(fieldCode);
            saveOrUpdateEntity.setDictType(vo.getDictType());
            saveOrUpdateEntity.setDictCode(vo.getDictCode());
            saveOrUpdateEntity.setDictTable(vo.getDictTable());
            saveOrUpdateEntity.setDictText(vo.getDictText());
            saveOrUpdateEntity.setDictTableColumn(vo.getDictTableColumn());//1
            saveOrUpdateEntity.setDictTextFormatter(vo.getDictTextFormatter());
            if (Func.isEmpty(vo.getId())) {//新增
                addList.add(saveOrUpdateEntity);
            } else if (Func.equals(vo.getIsModify(), YNEnum.Y.getCode())) {//如果是N的时候，不修改
                updateList.add(saveOrUpdateEntity);
            }
        });

        if(Func.isNotEmpty(addList)){
            batchService.saveBatch(addList,fieldDictMapper, FormFieldDictMapper.class,FormFieldDictEntity.class);
        }

        if(Func.isNotEmpty(updateList)){
            batchService.updateBatchById(updateList,fieldDictMapper,FormFieldDictMapper.class,FormFieldDictEntity.class);
        }


    }

    //新增or修改 表单开发-保存导出属性
    private void saveOrUpdateDbFormExport(long formId, List<DbFormFieldExportVo> exportList, List<Long> delIdList) {
        if (Func.isNotEmpty(delIdList)) {
            fieldExportMapper.deleteBatchIds(delIdList);
        }

        List<FormFieldExportEntity> addList=new ArrayList<>();
        List<FormFieldExportEntity> updateList=new ArrayList<>();

        exportList.stream().forEach(vo -> {
            String fieldCode = vo.getFieldCode();
            //获取id,判断是否是旧的
            Long id = vo.getId();
            id = Func.isNotEmpty(id) ? id : IdWorker.getId();

            FormFieldExportEntity saveOrUpdateEntity = new FormFieldExportEntity();
            saveOrUpdateEntity.setId(id);
            saveOrUpdateEntity.setDbformId(formId);
            saveOrUpdateEntity.setFieldCode(fieldCode);
            saveOrUpdateEntity.setIsExport(vo.getIsExport());
            saveOrUpdateEntity.setImportExampleTxt(vo.getImportExampleTxt());

            if (Func.isEmpty(vo.getId())) {//新增
                addList.add(saveOrUpdateEntity);
            } else if (Func.equals(vo.getIsModify(), YNEnum.Y.getCode())) {//如果是N的时候，不修改
                updateList.add(saveOrUpdateEntity);
            }
        });
        if(Func.isNotEmpty(addList)){
            batchService.saveBatch(addList,fieldExportMapper, FormFieldExportMapper.class,FormFieldExportEntity.class);
        }

        if(Func.isNotEmpty(updateList)){
            batchService.updateBatchById(updateList,fieldExportMapper,FormFieldExportMapper.class,FormFieldExportEntity.class);
        }

    }

    //新增or修改 表单开发-保存外键配置表
    private void saveOrUpdateDbFormForeignkey(long formId, List<DbFormFieldForeignkeyVo> foreignkeyList, List<Long> delIdList) {
        if (Func.isNotEmpty(delIdList)) {
            fieldForeignkeyMapper.deleteBatchIds(delIdList);
        }

        List<FormFieldForeignkeyEntity> addList=new ArrayList<>();
        List<FormFieldForeignkeyEntity> updateList=new ArrayList<>();

        foreignkeyList.stream().forEach(vo -> {
            //获取id,判断是否是旧的
            Long id = vo.getId();
            id = Func.isNotEmpty(id) ? id : IdWorker.getId();

            FormFieldForeignkeyEntity saveOrUpdateEntity = new FormFieldForeignkeyEntity();
            saveOrUpdateEntity.setId(id);
            saveOrUpdateEntity.setDbformId(formId);
            saveOrUpdateEntity.setFieldCode(vo.getFieldCode());
            saveOrUpdateEntity.setMainTable(vo.getMainTable());
            saveOrUpdateEntity.setMainField(vo.getMainField());
            if (Func.isEmpty(vo.getId())) {//新增
                addList.add(saveOrUpdateEntity);
            } else if (Func.equals(vo.getIsModify(), YNEnum.Y.getCode())) {//如果是N的时候，不修改
                updateList.add(saveOrUpdateEntity);
            }
        });

        if(Func.isNotEmpty(addList)){
            batchService.saveBatch(addList,fieldForeignkeyMapper, FormFieldForeignkeyMapper.class,FormFieldForeignkeyEntity.class);
        }

        if(Func.isNotEmpty(updateList)){
            batchService.updateBatchById(updateList,fieldForeignkeyMapper,FormFieldForeignkeyMapper.class,FormFieldForeignkeyEntity.class);
        }
    }

    //新增or修改 表单开发-保存页面属性表
    private void saveOrUpdateDbFormWeb(long formId, List<DbFormFieldWebVo> webList, List<Long> delIdList) {
        if (Func.isNotEmpty(delIdList)) {
            fieldWebMapper.deleteBatchIds(delIdList);
        }

        List<FormFieldWebEntity> addList=new ArrayList<>();
        List<FormFieldWebEntity> updateList=new ArrayList<>();


        webList.stream().forEach(vo -> {
            String fieldCode = vo.getFieldCode();
            //获取id,判断是否是旧的
            Long id = vo.getId();
            id = Func.isNotEmpty(id) ? id : IdWorker.getId();

            FormFieldWebEntity saveOrUpdateEntity = new FormFieldWebEntity();
            saveOrUpdateEntity.setId(id);
            saveOrUpdateEntity.setDbformId(formId);
            saveOrUpdateEntity.setFieldCode(fieldCode);
            saveOrUpdateEntity.setIsDbSelect(vo.getIsDbSelect());
            saveOrUpdateEntity.setIsShowList(vo.getIsShowList());
            saveOrUpdateEntity.setIsShowForm(vo.getIsShowForm());
            saveOrUpdateEntity.setIsShowColumn(vo.getIsShowColumn());
            saveOrUpdateEntity.setIsShowSort(vo.getIsShowSort());
            saveOrUpdateEntity.setIsRequired(vo.getIsRequired());
            saveOrUpdateEntity.setControlType(vo.getControlType());
            saveOrUpdateEntity.setControlsConfig(vo.getControlsConfig());
            saveOrUpdateEntity.setCellWidth(vo.getCellWidth());
            saveOrUpdateEntity.setCellWidthType(vo.getCellWidthType());
            saveOrUpdateEntity.setVerifyConfig(vo.getVerifyConfig());
            saveOrUpdateEntity.setFormatConfig(vo.getFormatConfig());
            if (Func.isEmpty(vo.getId())) {//新增
                addList.add(saveOrUpdateEntity);
            } else if (Func.equals(vo.getIsModify(), YNEnum.Y.getCode())) {//如果是N的时候，不修改
                updateList.add(saveOrUpdateEntity);
            }

        });

        if(Func.isNotEmpty(addList)){
            batchService.saveBatch(addList,fieldWebMapper, FormFieldWebMapper.class,FormFieldWebEntity.class);
        }

        if(Func.isNotEmpty(updateList)){
            batchService.updateBatchById(updateList,fieldWebMapper,FormFieldWebMapper.class,FormFieldWebEntity.class);
        }
    }

    //新增or修改 表单开发-查询配置
    private void saveOrUpdateDbFormQuery(long formId, List<DbFormFieldQueryVo> queryList, List<Long> delIdList) {
        if (Func.isNotEmpty(delIdList)) {
            fieldQueryMapper.deleteBatchIds(delIdList);
        }

        List<FormFieldQueryEntity> addList=new ArrayList<>();
        List<FormFieldQueryEntity> updateList=new ArrayList<>();

        queryList.stream().forEach(vo -> {
            String fieldCode = vo.getFieldCode();
            Long id = vo.getId();
            id = Func.isNotEmpty(id) ? id : IdWorker.getId();

            FormFieldQueryEntity saveOrUpdateEntity = new FormFieldQueryEntity();
            saveOrUpdateEntity.setId(id);
            saveOrUpdateEntity.setDbformId(formId);
            saveOrUpdateEntity.setFieldCode(fieldCode);
            saveOrUpdateEntity.setQueryIsDb(vo.getQueryIsDb());
            saveOrUpdateEntity.setQueryIsWeb(vo.getQueryIsWeb());
            saveOrUpdateEntity.setQueryMode(vo.getQueryMode());
            saveOrUpdateEntity.setQueryConfig(vo.getQueryConfig());
            saveOrUpdateEntity.setQueryDefaultVal(vo.getQueryDefaultVal());
            if (Func.isEmpty(vo.getId())) {//新增
                addList.add(saveOrUpdateEntity);
            } else if (Func.isEmpty(vo.getIsModify()) || Func.notEquals(vo.getIsModify(), YNEnum.N.getCode())) {//如果是N的时候，不修改
                updateList.add(saveOrUpdateEntity);
            }

        });

        if(Func.isNotEmpty(addList)){
            batchService.saveBatch(addList,fieldQueryMapper, FormFieldQueryMapper.class,FormFieldQueryEntity.class);
        }

        if(Func.isNotEmpty(updateList)){
            batchService.updateBatchById(updateList,fieldQueryMapper,FormFieldQueryMapper.class,FormFieldQueryEntity.class);
        }

    }

    //新增or修改 表单开发-统计配置
    private void saveOrUpdateDbFormSummary(long formId, List<DbFormSummaryVo> summaryList, List<Long> delIdList) {
        if (Func.isNotEmpty(delIdList)) {
            summaryMapper.deleteBatchIds(delIdList);
        }

        List<FormSummaryEntity> addList=new ArrayList<>();
        List<FormSummaryEntity> updateList=new ArrayList<>();

        summaryList.stream().forEach(vo -> {
            Long id = vo.getId();
            id = Func.isNotEmpty(id) ? id : IdWorker.getId();

            FormSummaryEntity saveOrUpdateEntity = new FormSummaryEntity();
            saveOrUpdateEntity.setId(id);
            saveOrUpdateEntity.setDbformId(formId);
            saveOrUpdateEntity.setFieldCode(vo.getFieldCode());
            saveOrUpdateEntity.setSummaryType(vo.getSummaryType());
            saveOrUpdateEntity.setSummaryShow(vo.getSummaryShow());
            saveOrUpdateEntity.setSummarySql(vo.getSummarySql());
            saveOrUpdateEntity.setSummaryLabel(vo.getSummaryLabel());
            saveOrUpdateEntity.setSummaryJson(vo.getSummaryJson());
            if (Func.isEmpty(vo.getId())) {//新增
                addList.add(saveOrUpdateEntity);
            } else if (Func.equals(vo.getIsModify(), YNEnum.Y.getCode())) {//如果是N的时候，不修改
                updateList.add(saveOrUpdateEntity);
            }

        });

        if(Func.isNotEmpty(addList)){
            batchService.saveBatch(addList,summaryMapper, FormSummaryMapper.class,FormSummaryEntity.class);
        }

        if(Func.isNotEmpty(updateList)){
            batchService.updateBatchById(updateList,summaryMapper,FormSummaryMapper.class,FormSummaryEntity.class);
        }
    }


    //新增or修改 表单开发-保存页面属性表
    private void saveOrUpdateDbFormIndex(long formId, List<DbFormIndexVo> indexList, List<Long> delIdList) {
        if (Func.isNotEmpty(delIdList)) {
            indexMapper.deleteBatchIds(delIdList);
        }

        if (FuncBase.isEmpty(indexList)) {
            return;
        }

        List<FormIndexEntity> addList=new ArrayList<>();
        List<FormIndexEntity> updateList=new ArrayList<>();

        indexList.stream().forEach(vo -> {
            Long id = vo.getId();
            id = Func.isNotEmpty(id) ? id : IdWorker.getId();

            FormIndexEntity saveOrUpdateEntity = new FormIndexEntity();
            saveOrUpdateEntity.setId(id);
            saveOrUpdateEntity.setDbformId(formId);
            saveOrUpdateEntity.setIndexName(vo.getIndexName());
            saveOrUpdateEntity.setIndexFieldCodeList(vo.getIndexFieldCodeList());
            saveOrUpdateEntity.setIndexType(vo.getIndexType());
            if (Func.isEmpty(vo.getId())) {//新增
                addList.add(saveOrUpdateEntity);
            } else if (Func.equals(vo.getIsModify(), YNEnum.Y.getCode())) {//如果是N的时候，不修改
                updateList.add(saveOrUpdateEntity);
            }
        });

        if(Func.isNotEmpty(addList)){
            batchService.saveBatch(addList,indexMapper, FormIndexMapper.class,FormIndexEntity.class);
        }

        if(Func.isNotEmpty(updateList)){
            batchService.updateBatchById(updateList,indexMapper,FormIndexMapper.class,FormIndexEntity.class);
        }

    }


    //解除主附表 关系
    private void unBindMainTable(FormEntity formEntity) {
        Integer tableType = formEntity.getTableType();
        if (Func.notEquals(tableType, TableTypeEnum.SUB.getType()) && Func.notEquals(tableType, TableTypeEnum.SUB.getType())) {//3 4为主附表
            return;
        }

        String tableName = formEntity.getTableName();

        LambdaQueryWrapper<FormEntity> wrapper = new LambdaQueryWrapper<>();
        wrapper.like(FormEntity::getSubTableListStr, tableName);
        List<FormEntity> subFormEntityList = baseMapper.selectList(wrapper);
        if (FuncBase.isEmpty(subFormEntityList)) {
            return;
        }
        //我当前在这些表的子表里面
        subFormEntityList.stream().forEach(subFormEntity -> {
            this.unBindMainTable(subFormEntity, tableName);
        });


    }

    //主表解除某一个表
    private void unBindMainTable(FormEntity mainFormEntity, String unBindTableName) {
        if (FuncBase.isEmpty(mainFormEntity)) {
            return;
        }

        String subTableListStr = mainFormEntity.getSubTableListStr();
        List<String> subTableList = FuncBase.toStrList(subTableListStr);

        List<String> newSubTableList = subTableList.stream().filter(subTable -> !FuncBase.equals(subTable, unBindTableName)).collect(Collectors.toList());

        String newSubTableStr = String.join(",", newSubTableList);
        FormEntity updatEntity = new FormEntity();
        updatEntity.setId(mainFormEntity.getId());
        updatEntity.setDesformWebId(mainFormEntity.getDesformWebId());
        updatEntity.setGroupDbformId(mainFormEntity.getGroupDbformId());
        updatEntity.setSubTableListStr(newSubTableStr);
        baseMapper.updateById(updatEntity);

    }


    //绑定主附表 关系
    private void bindMainTable(DbFormVo dbFormVo, List<DbFormFieldForeignkeyVo> foreignkeyList) {
        Integer tableType = dbFormVo.getTableType();
        if (Func.notEquals(tableType, TableTypeEnum.SUB.getType()) || FuncBase.isEmpty(foreignkeyList)) {//4为主附表  如果是附表的话，需要绑定到主表
            return;
        }
        String tableName = dbFormVo.getTableName();

        //绑定到附表
        foreignkeyList.stream().forEach(vo -> {
            String mainTable = vo.getMainTable();//主表
            if (FuncBase.isEmpty(mainTable)) {
                return;
            }
            //绑定到主表
            LambdaQueryWrapper<FormEntity> wrapper = new LambdaQueryWrapper<>();
            wrapper.eq(FormEntity::getTableName, mainTable);
            FormEntity mainFormEntity = baseMapper.selectOne(wrapper);
            if (FuncBase.isEmpty(mainFormEntity)) {
                throw new JeeLowCodeException(FrameErrorCodeConstants.FRAME_MAIN_TABLE_NOT.getMsg());
            }

            String subTableListStr = mainFormEntity.getSubTableListStr();
            List<String> subTableList = FuncBase.toStrNewList(subTableListStr);
            Func.addStr2List(subTableList, tableName);//添加到list

            String newSubTableStr = String.join(",", subTableList);

            FormEntity updatMainEntity = new FormEntity();
            updatMainEntity.setId(mainFormEntity.getId());
            updatMainEntity.setDesformWebId(mainFormEntity.getDesformWebId());
            updatMainEntity.setGroupDbformId(mainFormEntity.getGroupDbformId());
            updatMainEntity.setSubTableListStr(newSubTableStr);
            baseMapper.updateById(updatMainEntity);
        });


    }

    @Override
    public void cleanCache() {
        Set<String> keys = redisUtil.keys(JeeRedisConstants.JEELOWCODE_PREFIX);
        redisUtil.delList(keys);
    }


    /**
     * 初始化所有脚本
     */
    @Override
    public void initOnlineScript(GroovyClassLoader groovyClassLoader) {
        LambdaQueryWrapper<EnhanceJavaEntity> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(EnhanceJavaEntity::getJavaType, EnhanceConstant.ENHANCE_TYPE_ONLINE_EDIT);
        queryWrapper.eq(EnhanceJavaEntity::getIsDeleted, 0);
        queryWrapper.isNotNull(EnhanceJavaEntity::getOnlineScript);
        queryWrapper.select(EnhanceJavaEntity::getOnlineScript);
        List<EnhanceJavaEntity> list = javaService.list(queryWrapper);
        for (EnhanceJavaEntity enhanceJavaEntity : list) {
            try {
                groovyClassLoader.parseClass(enhanceJavaEntity.getOnlineScript());
            } catch (Exception e) {

            }
        }
    }

    //初始化自定义按钮
    @Override
    public void initConfigBtnCommand() {
        Map<String, String> btnConfigMap = jeeLowCodeConfigService.getBtnCommandConfig();
        if (Func.isEmpty(btnConfigMap)) {
            return;
        }
        //初始化
        DefinablePluginManager.allDefinableMap = btnConfigMap;
    }


    //获取树级所有上级
    @Override
    public List<Map<String, Object>> getTreeParentList(Long dbformId, TreeParentParam treeParentParam) {
        List<String> fieldList = treeParentParam.getFieldList();
        String code = treeParentParam.getCode();
        List<Long> idList = treeParentParam.getValList();
        if (!fieldList.contains(DefaultDbFieldEnum.P_ID.getFieldCode())) {
            fieldList.add(DefaultDbFieldEnum.P_ID.getFieldCode());
        }

        //查询本级
        String tableName = this.getTableName(dbformId);
        SqlInfoQueryWrapper.Wrapper wrapper = SqlHelper.getQueryWrapper()
                .setTableName(tableName)
                .select(fieldList, true)
                .setWhere(where -> {
                    where.in(code, idList);
                })
                .build();
        List<Map<String, Object>> dataMapList = frameSqlService.getDataListByPlus(wrapper);
        if (Func.isEmpty(dataMapList)) {//为空
            return dataMapList;
        }

        List<Map<String, Object>> resultDataMapList = new ArrayList<>();
        resultDataMapList.addAll(dataMapList);

        //确保唯一
        Set<Long> uniIdList = new HashSet<>();
        uniIdList.addAll(idList);


        //抽取pid，往上查找
        Set<Long> pidSet = dataMapList.stream()
                .filter(dataMap -> {
                    Long pid = Func.getMap2LongDefault(dataMap, DefaultDbFieldEnum.P_ID.getFieldCode(), -99L);
                    if (uniIdList.contains(pid)) {//存在
                        return false;
                    }
                    uniIdList.add(pid);
                    return true;
                })
                .map(dataMap -> Func.getMap2LongDefault(dataMap, DefaultDbFieldEnum.P_ID.getFieldCode(), -99L)) // 提取pid值
                .collect(Collectors.toSet()); //


        do {
            Set<Long> finalPidSet = pidSet;
            SqlInfoQueryWrapper.Wrapper pidWrapper = SqlHelper.getQueryWrapper()
                    .setTableName(tableName)
                    .select(fieldList, true)
                    .setWhere(where -> {
                        where.in("id", finalPidSet);
                    }).build();
            dataMapList = frameSqlService.getDataListByPlus(pidWrapper);


            if (Func.isEmpty(dataMapList)) {
                break; // 没有数据，跳出循环
            }
            resultDataMapList.addAll(dataMapList);

            pidSet = dataMapList.stream() //
                    .filter(dataMap -> {
                        Long pid = Func.getMap2LongDefault(dataMap, DefaultDbFieldEnum.P_ID.getFieldCode(), -99L);
                        if (uniIdList.contains(pid)) {//存在
                            return false;
                        }
                        uniIdList.add(pid);
                        return true;
                    })
                    .map(dataMap -> Func.getMap2LongDefault(dataMap, DefaultDbFieldEnum.P_ID.getFieldCode(), -99L))
                    .collect(Collectors.toCollection(HashSet::new));

        } while (!pidSet.isEmpty());

        return resultDataMapList;
    }

    //获取合计字段列表
    @Override
    public List<FormSummaryEntity> getSummarySettingList(Long dbformId, String summaryType) {
        List<FormSummaryEntity> entityList = summaryMapper.getSummaryList(dbformId, summaryType);
        return entityList;
    }

    //获取界面显示列
    @Override
    public List<String> getWebViewFieldList(Long dbformId) {
        List<String> webViewFieldList = jeeLowCodeMapper.getWebViewFieldList(dbformId);

        //默认列
        List<String> defaultFieldList = new ArrayList<>();
        defaultFieldList.add(DefaultDbFieldEnum.ID.getFieldCode());
        defaultFieldList.add(DefaultDbFieldEnum.TENANT_ID.getFieldCode());
        defaultFieldList.add(DefaultDbFieldEnum.CREATE_USER.getFieldCode());
        defaultFieldList.add(DefaultDbFieldEnum.CREATE_TIME.getFieldCode());
        defaultFieldList.add(DefaultDbFieldEnum.CREATE_DEPT.getFieldCode());
        defaultFieldList.add(DefaultDbFieldEnum.UPDATE_USER.getFieldCode());
        defaultFieldList.add(DefaultDbFieldEnum.UPDATE_TIME.getFieldCode());
        Integer tableType = this.getTableType(dbformId);
        if (Func.equals(tableType, TableTypeEnum.TREE.getType())) {//树形结构强制查pid
            defaultFieldList.add(DefaultDbFieldEnum.P_ID.getFieldCode());
        }

        webViewFieldList.addAll(defaultFieldList);

        return webViewFieldList;
    }


    @Override
    public void initEnhancePluginManager() {
        //所有JAVA增强
        List<EnhanceJavaEntity> allJavaEnhanceList = javaService.getAllJavaEnhance();

        //那个form_button 需要增强
        Map<String, List<EnhanceJavaEntity>> pluginJavaMap = new HashMap<>();
        Map<String, List<EnhanceSqlEntity>> pluginSqlMap = new HashMap<>();
        //Java插件
        allJavaEnhanceList.forEach(enhanceJavaEntity -> {
                    Long dbformId = enhanceJavaEntity.getDbformId();
                    String buttonCode = enhanceJavaEntity.getButtonCode();

                    //那个功能，那个form有增强
                    String pluginKey = dbformId + "_" + buttonCode;
                    pluginJavaMap.merge(pluginKey, new ArrayList<>(Collections.singletonList(enhanceJavaEntity)), (oldValue, newValue) -> {
                        oldValue.addAll(newValue);
                        return oldValue;
                    });

                    //初始化控件
                    try {
                        if (FuncBase.equals(enhanceJavaEntity.getJavaType(), EnhanceConstant.ENHANCE_TYPE_SPRING)) {//spring方式
                            String javaClassUrl = enhanceJavaEntity.getJavaClassUrl();//
                            Object bean = SpringUtils.getBean(javaClassUrl);
                            if (Func.isNotEmpty(bean) && bean instanceof BaseAdvicePlugin) {
                                PluginManager.addPlugin(javaClassUrl, (BaseAdvicePlugin) bean);
                            }
                        } else if (FuncBase.equals(enhanceJavaEntity.getJavaType(), EnhanceConstant.ENHANCE_TYPE_CLASS)) {//class方式
                            Class<?> enhanceClass = Class.forName(enhanceJavaEntity.getJavaClassUrl());
                            BaseAdvicePlugin plugin = (BaseAdvicePlugin) enhanceClass.newInstance();
                            PluginManager.addPlugin(enhanceJavaEntity.getJavaClassUrl(), plugin);
                        }
                    } catch (Exception e) {
                        log.error("初始化增强插件失败:{}", e);
                    }
                }

        );


        //SQL插件--那个表单_功能 有sql增强
        List<EnhanceSqlEntity> allSqlEntityList = sqlService.getAllSqlEnhance();
        allSqlEntityList.forEach(sqlEntity -> {
            Long dbformId = sqlEntity.getDbformId();
            String buttonCode = sqlEntity.getButtonCode();
            String key = dbformId + "_" + buttonCode;
            pluginSqlMap.merge(key, new ArrayList<>(Collections.singletonList(sqlEntity)), (oldValue, newValue) -> {
                oldValue.addAll(newValue);
                return oldValue;
            });
        });

        //初始化切面增强
        JeeLowCodeAnnotationAspectjJAVA.initPluginNames(pluginJavaMap);
        JeeLowCodeAnnoaionAspectjSQL.initSqlPlugins(pluginSqlMap);

        Map<String, String> enhanceCodeConfig = jeeLowCodeConfigService.getEnhanceCodeConfig();
        JeeLowCodeAnnotationAspectjJAVA.initAspectMethodNameMap(enhanceCodeConfig);
    }


    //获取字段枚举
    @Override
    public Map<String, JeeLowCodeFieldTypeEnum> getFieldCodeAndTypeEnum(Long dbformId) {
        List<FormFieldEntity> fieldList = this.getFieldList(dbformId);

        return fieldList.stream()
                .collect(Collectors.toMap(
                        FormFieldEntity::getFieldCode,
                        fieldEntity -> JeeLowCodeFieldTypeEnum.getByFieldType(fieldEntity.getFieldType())
                ));
    }

    //获取报表字段枚举
    @Override
    public Map<String, JeeLowCodeFieldTypeEnum> getReportFieldCodeAndTypeEnum(Long reportId) {
        List<ReportFieldEntity> fieldList = reportFieldMapper.getByDbReportId(reportId);
        return fieldList.stream().collect(Collectors.toMap(
                ReportFieldEntity::getFieldCode,
                fieldEntity -> JeeLowCodeFieldTypeEnum.getByFieldType(fieldEntity.getFieldType())
        ));
    }

    //解析sql
    public Map<String, Object> explainSqlField(List<ExplainSqlFieldModel> modelList) {
        Map<String, Map<String, FormFieldEntity>> fieldMaps = new HashMap<>();
        Map<String, Map<String, FormFieldDictEntity>> dictMaps = new HashMap<>();
        Map<String, Map<String, FormFieldQueryEntity>> queryMaps = new HashMap<>();
        Map<String, Map<String, FormFieldWebEntity>> webMaps = new HashMap<>();

        List<FormFieldEntity> fieldList = new ArrayList<>();
        List<FormFieldDictEntity> dictList = new ArrayList<>();
        List<FormFieldQueryEntity> queryList = new ArrayList<>();
        List<FormFieldWebEntity> webList = new ArrayList<>();


        for (ExplainSqlFieldModel model : modelList) {
            String fieldCode = model.getValue();//字段 nj_name
            String alias = model.getAlias();
            String tableName = model.getTableName();//表名称
            String type = model.getType();//field=表字段
            String controlType = model.getControlType();//控件类型 //text控件    custom=自定义
            String value = model.getValue();

            //控件类型
            if (Func.equals(controlType, "text") && Func.equals("field", type)) {
                Long dbFormId = this.getDbFormIdByTableName(tableName);
                if (Func.isEmpty(dbFormId)) {
                    continue;
                }
                //处理字段表
                Map<String, FormFieldEntity> fieldEntityMap = fieldMaps.get(tableName);
                if (Func.isEmpty(fieldEntityMap)) {
                    List<FormFieldEntity> selectFieldList = this.getFieldList(dbFormId);
                    fieldEntityMap = selectFieldList.stream()
                            .collect(Collectors.toMap(FormFieldEntity::getFieldCode, entity -> entity));
                    fieldMaps.put(tableName, fieldEntityMap);
                }
                FormFieldEntity fieldEntity = fieldEntityMap.get(fieldCode);
                if (Func.isNotEmpty(fieldEntity)) {
                    if (Func.isNotEmpty(alias)) {
                        fieldEntity.setFieldCode(alias.toLowerCase());
                    }
                    fieldList.add(fieldEntity);
                }

                //处理字典表
                Map<String, FormFieldDictEntity> fieldDictEntityMap = dictMaps.get(tableName);
                if (Func.isEmpty(fieldDictEntityMap)) {
                    List<FormFieldDictEntity> selectDictList = this.getFieldDictList(dbFormId);
                    fieldDictEntityMap = selectDictList.stream()
                            .collect(Collectors.toMap(FormFieldDictEntity::getFieldCode, entity -> entity));
                    dictMaps.put(tableName, fieldDictEntityMap);
                }
                FormFieldDictEntity formFieldDictEntity = fieldDictEntityMap.get(fieldCode);
                if (Func.isNotEmpty(formFieldDictEntity)) {
                    if (Func.isNotEmpty(alias)) {
                        formFieldDictEntity.setFieldCode(alias);
                    }
                    dictList.add(formFieldDictEntity);
                }

                //处理查询表
                Map<String, FormFieldQueryEntity> fieldQueryEntityMap = queryMaps.get(tableName);
                if (Func.isEmpty(fieldQueryEntityMap)) {
                    List<FormFieldQueryEntity> selectQueryList = this.getFieldQueryList(dbFormId);
                    fieldQueryEntityMap = selectQueryList.stream()
                            .collect(Collectors.toMap(FormFieldQueryEntity::getFieldCode, entity -> entity));
                    queryMaps.put(tableName, fieldQueryEntityMap);
                }
                FormFieldQueryEntity formFieldQueryEntity = fieldQueryEntityMap.get(fieldCode);
                if (Func.isNotEmpty(formFieldQueryEntity)) {
                    if (Func.isNotEmpty(alias)) {
                        formFieldQueryEntity.setFieldCode(alias);
                    }
                    queryList.add(formFieldQueryEntity);
                }

                //处理web表
                Map<String, FormFieldWebEntity> fieldWebEntityMap = webMaps.get(tableName);
                if (Func.isEmpty(fieldWebEntityMap)) {
                    List<FormFieldWebEntity> selectWebList = this.getFieldWebList(dbFormId);
                    fieldWebEntityMap = selectWebList.stream()
                            .collect(Collectors.toMap(FormFieldWebEntity::getFieldCode, entity -> entity));
                    webMaps.put(tableName, fieldWebEntityMap);
                }
                FormFieldWebEntity formFieldWebEntity = fieldWebEntityMap.get(fieldCode);
                if (Func.isNotEmpty(formFieldWebEntity)) {
                    if (Func.isNotEmpty(alias)) {
                        formFieldWebEntity.setFieldCode(alias);
                    }
                    webList.add(formFieldWebEntity);
                }

                //处理结果返回

            } else if (Func.equals(controlType, "custom")) {
                if (!value.toLowerCase().contains(" as ")) {//没有别名的话，直接返回
                    continue;
                }
                fieldCode = value.toLowerCase().split(" as ")[1];
                if (Func.isNotEmpty(alias)) {
                    fieldCode = alias;
                }
                fieldCode = fieldCode.toLowerCase().replaceAll(" ", "").trim();
                //构建空的
                FormFieldEntity fieldEntity = new FormFieldEntity();
                fieldEntity.setFieldCode(fieldCode);
                fieldList.add(fieldEntity);

                FormFieldDictEntity fieldDictEntity = new FormFieldDictEntity();
                fieldDictEntity.setFieldCode(fieldCode);
                dictList.add(fieldDictEntity);

                FormFieldQueryEntity formFieldQueryEntity = new FormFieldQueryEntity();
                formFieldQueryEntity.setFieldCode(fieldCode);
                queryList.add(formFieldQueryEntity);

                FormFieldWebEntity formFieldWebEntity = new FormFieldWebEntity();
                formFieldWebEntity.setFieldCode(fieldCode);
                webList.add(formFieldWebEntity);

            }

        }

        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("fieldList", fieldList);
        resultMap.put("dictList", dictList);
        resultMap.put("queryList", queryList);
        resultMap.put("webList", webList);
        return resultMap;
    }

    //获取未在表单开发里面的表
    @Override
    public List<NotInDbformTablesVo> getNotInDbformTables() {
        List<NotInDbformTablesVo> resultVoList = new ArrayList<>();

        String allTableNameSql = SqlHelper.getAllTableNameSql();
        List<Map<String, Object>> dataMapList = frameSqlService.getSimpleData(allTableNameSql);
        if (Func.isEmpty(dataMapList)) {
            return resultVoList;
        }
        //获取所有表单的名称
        LambdaQueryWrapper<FormEntity> wrapper = new LambdaQueryWrapper<>();
        wrapper.select(FormEntity::getTableName);
        List<FormEntity> formEntityList = baseMapper.selectList(wrapper);
        List<String> dbFormTableNameList = formEntityList.stream()
                .filter(Objects::nonNull)
                .map(FormEntity::getTableName)
                .map(String::toLowerCase) // 将字符串转换为小写
                .collect(Collectors.toList());
        //
        List<String> existList = new ArrayList<>();
        for (Map<String, Object> dataMap : dataMapList) {
            String table_name = Func.getMap2Str(dataMap, "table_name").toLowerCase();
            if (dbFormTableNameList.contains(table_name)) {
                continue;
            }
            if (existList.contains(table_name)) {
                continue;
            }
            existList.add(table_name);

            //剔除部分开头的表
            boolean flag = false;
            List<String> excludeTableNamesList = JeeLowCodeProperties.getExcludeTableNames();
            if (excludeTableNamesList != null && !excludeTableNamesList.isEmpty()){
                for (String excludeTable : excludeTableNamesList) {
                    if (table_name.startsWith(excludeTable)) {
                        flag = true;
                        break;
                    }
                }
            }
            if (flag) {
                continue;
            }
            if (table_name.contains("$")) {
                continue;
            }

            //获取表备注
            String table_comment = "";
            String tableCommentSql = SqlHelper.getTableComment(table_name);
            List<Map<String, Object>> tableCommentMapList = frameSqlService.getSimpleData(tableCommentSql);
            if (Func.isNotEmpty(tableCommentMapList)) {
                Map<String, Object> tableCommentMap = tableCommentMapList.get(0);
                table_comment = Func.getMap2Str(tableCommentMap, "table_comment");
            }
            if (Func.isEmpty(table_comment)) {
                table_comment = table_name;
            }

            NotInDbformTablesVo vo = new NotInDbformTablesVo();
            vo.setTableName(table_name);
            vo.setTableDescribe(table_comment);
            resultVoList.add(vo);
        }
        return resultVoList;
    }

    //获取表备注字段
    @Override
    public List<TableFieldModelVo> getTableFieldComment(String tableName) {
        List<TableFieldModelVo> resultVoList = new ArrayList<>();

        String tableFieldCommentSql = SqlHelper.getTableFieldComment(tableName);
        List<Map<String, Object>> dataMapList = frameSqlService.getSimpleData(tableFieldCommentSql);
        Map<String, String> remarkMap = Func.dataMapList2ResultSet(dataMapList);

        Map<String, FieldModel> columnMetaMap = dbManager.getDbColumnMetaMap(tableName, remarkMap);
        for (Map.Entry<String, FieldModel> entry : columnMetaMap.entrySet()) {
            String key = entry.getKey();
            FieldModel model = entry.getValue();
            TableFieldModelVo vo = BeanUtil.copyProperties(model, TableFieldModelVo.class);
            //处理fieldType
            JeeLowCodeFieldTypeEnum fieldTypeEnum = model.getJavaFieldTypeList().get(0);
            vo.setFieldType(fieldTypeEnum.getFieldType());
            resultVoList.add(vo);
        }
        return resultVoList;
    }

    // 获取表单的字段对应的字典
    @Override
    public Map<String, Map<String, Object>> getFieldDict(Long dbFormId) {
        List<Map<String, Object>> fieldDictList = adapterMapper.getDbFormFieldDict(dbFormId);
        if (FuncBase.isEmpty(fieldDictList)) {
            return null;
        }
        Map<String, Map<String, Object>> resultMap = new HashMap<>();
        // resultMap {"sex": {"1": "男","0": "女"},"flag": {"1": "是","0": "否"}}
        for (Map<String, Object> fieldDict : fieldDictList) {
            String fieldCode = JeeLowCodeUtils.getMap2Str(fieldDict, "field_code");
            String value = JeeLowCodeUtils.getMap2Str(fieldDict, "value");
            String label = JeeLowCodeUtils.getMap2Str(fieldDict, "label");
            if (FuncBase.isEmpty(fieldCode) || (FuncBase.isEmpty(value) && FuncBase.isEmpty(label))) {
                continue;
            }
            if (resultMap.containsKey(fieldCode)) {
                resultMap.get(fieldCode).put(value, label);
            } else {
                Map<String, Object> dictMap = new HashMap<>();
                dictMap.put(value, label);
                resultMap.put(fieldCode, dictMap);
            }
        }
        return resultMap;
    }
}
