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
package com.jeelowcode.core.framework.service;


import com.jeelowcode.core.framework.entity.*;
import com.jeelowcode.core.framework.params.DbFormAddOrUpdateParam;
import com.jeelowcode.core.framework.params.PageDbFormParam;
import com.jeelowcode.core.framework.params.TreeParentParam;
import com.jeelowcode.core.framework.params.model.AllTableModel;
import com.jeelowcode.core.framework.params.model.DbFieldCodeListModel;
import com.jeelowcode.core.framework.params.model.ExplainSqlFieldModel;
import com.jeelowcode.core.framework.params.vo.DbFormConfigVo;
import com.jeelowcode.core.framework.params.vo.NotInDbformTablesVo;
import com.jeelowcode.core.framework.params.vo.TableFieldModelVo;
import com.jeelowcode.core.framework.params.vo.webconfig.WebConfigVo;
import com.jeelowcode.framework.utils.enums.AuthTypeEnum;
import com.jeelowcode.framework.utils.enums.DbFormTypeEnum;
import com.jeelowcode.framework.utils.enums.JeeLowCodeFieldTypeEnum;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.jeelowcode.core.framework.entity.*;
import groovy.lang.GroovyClassLoader;

import java.util.List;
import java.util.Map;

/**
 * 表单开发-业务相关
 */
public interface IFormService extends IService<FormEntity> {

    //判断是否是业务表
    boolean isServiceTable(Long dbFormId);

    //获取表类型
    Integer getTableType(Long dbFormId);

    //获取主题模板
    String getTableThemeTemplate(Long dbFormId);

    //获取认证类型
    AuthTypeEnum getAuthType(Long dbFormId);

    //获取表名称
    String getTableName(Long dbFormId);

    //校验字段是否存在
    boolean fieldCodeIsExist(Long dbFormId,String fieldCode);

    //获取所有附表列表
    List<String> getAllSubTableNameList(Long dbFormId);

    //根据表名获取id
    Long getDbFormIdByTableName(String tableName);
    List<Long> getDbFormIdByTableName(List<String> subTableNameList);

    //获取表信息
    FormEntity getFormEntityById(Long dbFormId);
    FormEntity getFormEntityByName(String tableName);

    //获取数量
    Map<String,Object> getFormCou(Long dbFormId);


    //获取数据库字段列表
    List<FormFieldEntity> getFieldList(Long dbFormId);
    List<FormFieldEntity> getDbFieldList(Long dbFormId);
    DbFieldCodeListModel getDbFieldCodeList(Long dbFormId);

    //获取字典
    List<FormFieldDictEntity> getFieldDictList(Long dbFormId);
    List<FormFieldQueryEntity> getFieldQueryList(Long dbFormId);
    List<FormFieldWebEntity> getFieldWebList(Long dbFormId);

    //获取索引列表
    List<FormIndexEntity> getIndexList(Long dbFormId);

    //保存 - 表单开发
    Long saveDbFormConfig(DbFormAddOrUpdateParam param);

    //修改 - 表单开发
    void updateDbFormConfig(DbFormAddOrUpdateParam param);

    //删除 - 表单开发
    void deleteDbFormConfig(List<Long> dbFormIdList,boolean dropTableFlag);

    //复制表 - 表单开发
    void copyDbFormConfig(Long dbformId,String tableName);

    //详情 - 表单开发
    DbFormConfigVo getDetailDbFormConfig(Long dbFormId);//所有类型
    DbFormConfigVo getDetailDbFormConfig(Long dbFormId, List<DbFormTypeEnum> typeList);//自定义类型
    DbFormConfigVo getDetailDbFormConfig(Long dbFormId, List<DbFormTypeEnum> typeList,List<String> fieldList);//自定义列

    //分页 - 表单开发
    IPage<FormEntity> getPageDbFormConfig(PageDbFormParam param, Page page);

    //获取页面配置
    WebConfigVo getWebConfig(Long dbFormId);
    WebConfigVo getWebConfig(Long dbFormId,List<String> selfFieldList);


    //获取字段类型
    JeeLowCodeFieldTypeEnum getFieldTypeEnum(Long dbFormId, String fieldCode);

    //获取字典数据
    Map<String,String> getDictKeyLabelMap(Long dbFormId, String fieldCode);

    //格式化数据列表（回显数据，处理时间等）
    void formatDataList(Long dbformId,List<Map<String, Object>> dataMapList);

    //获取字段
    Map<String,String> getFieldNameAndCode(Long dbFormId);

    //获取所有表
    List<AllTableModel>  getAllTable(String systemFlag);

    //获取字典表显示列
    List<String> getDictTableFieldList(Long dbFormId,List<String> fieldList);

    //获取外键列
    FormFieldForeignkeyEntity getFieldForeignkeyEntity(Long dbFormId,String mainTable);

    //清空缓存
    void cleanCache();

    //初始化在线编码
    void initOnlineScript(GroovyClassLoader groovyClassLoader );

    //初始化自定义按钮
    void initConfigBtnCommand();

    //获取树级所有上级
    List<Map<String,Object>> getTreeParentList(Long dbformId, TreeParentParam treeParentParam);

    //获取统计设置列表
    List<FormSummaryEntity> getSummarySettingList(Long dbformId,String summaryType);

    //获取界面显示列
    List<String> getWebViewFieldList(Long dbformId);

    //初始化增强插件
    void initEnhancePluginManager();

    //获取字段枚举
    Map<String,JeeLowCodeFieldTypeEnum> getFieldCodeAndTypeEnum(Long dbformId);

    //获取报表字段枚举
    Map<String,JeeLowCodeFieldTypeEnum> getReportFieldCodeAndTypeEnum(Long reportId);

    //解析sql
    Map<String,Object> explainSqlField(List<ExplainSqlFieldModel> modelList);

    //获取未在表单开发里面的表
    List<NotInDbformTablesVo> getNotInDbformTables();

    //获取表备注字段
    List<TableFieldModelVo> getTableFieldComment(String tableName);

    // 获取表单的字段对应的字典
    Map<String, Map<String, Object>> getFieldDict(Long dbFormId);
}
