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

import cn.hutool.core.bean.BeanUtil;
import com.jeelowcode.framework.global.JeeLowCodeBaseConstant;
import com.jeelowcode.framework.utils.model.global.BaseWebResult;
import com.jeelowcode.core.framework.config.validate.DbFormCopyValidate;
import com.jeelowcode.core.framework.config.validate.TableNameValidate;
import com.jeelowcode.core.framework.entity.FormEntity;
import com.jeelowcode.core.framework.params.DbFormAddOrUpdateParam;
import com.jeelowcode.core.framework.params.DictLabelParam;
import com.jeelowcode.core.framework.params.PageDbFormParam;
import com.jeelowcode.core.framework.params.TreeParentParam;
import com.jeelowcode.core.framework.params.model.AllTableModel;
import com.jeelowcode.core.framework.params.model.ExplainDatasourceSqlModel;
import com.jeelowcode.core.framework.params.model.ExplainSqlFieldModel;
import com.jeelowcode.core.framework.params.vo.*;
import com.jeelowcode.core.framework.params.vo.role.DbFormRoleButtonVo;
import com.jeelowcode.core.framework.params.vo.role.DbFormRoleFieldVo;
import com.jeelowcode.core.framework.params.vo.webconfig.WebConfigRoleFieldVo;
import com.jeelowcode.core.framework.params.vo.webconfig.WebConfigVo;
import com.jeelowcode.core.framework.service.IDbFormRoleService;
import com.jeelowcode.core.framework.service.IFormService;
import com.jeelowcode.core.framework.service.IFrameService;
import com.jeelowcode.core.framework.service.IFrameSqlService;
import com.jeelowcode.core.framework.utils.Func;
import com.jeelowcode.core.framework.utils.FuncWeb;
import com.jeelowcode.framework.constants.FrameErrorCodeConstants;
import com.jeelowcode.framework.exception.JeeLowCodeException;
import com.jeelowcode.framework.exception.JeeLowCodeMoreException;
import com.jeelowcode.framework.plus.SqlHelper;
import com.jeelowcode.framework.plus.build.buildmodel.wrapper.SqlInfoQueryWrapper;
import com.jeelowcode.framework.tenant.annotation.JeeLowCodeTenantIgnore;
import com.jeelowcode.framework.utils.adapter.IJeeLowCodeAdapter;
import com.jeelowcode.framework.utils.annotation.JeelowCodeValidate;
import com.jeelowcode.framework.utils.enums.DbFormTypeEnum;
import com.jeelowcode.framework.utils.enums.ParamEnum;
import com.jeelowcode.framework.utils.model.ResultDataModel;
import com.jeelowcode.framework.utils.utils.FuncBase;
import com.jeelowcode.framework.utils.utils.JeeLowCodeUtils;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

@Tag(name = "低代码框架-框架接口")
@RestController
@AllArgsConstructor
@RequestMapping(JeeLowCodeBaseConstant.REQUEST_URL_START + "/dbform")
public class DbFormController extends BaseController {

    private final IFrameService frameService;

    private final IFormService dbFormService;

    private final IJeeLowCodeAdapter jeeLowCodeAdapter;

    private final IFrameSqlService sqlService;

    private final IDbFormRoleService dbFormRoleService;

    @JeelowCodeValidate(title = "自定义校验-表名是否正确", validateClass = TableNameValidate.class)
    @PreAuthorize("@ss.hasPermission('jeelowcode:dbform:create')")
    @PostMapping("/save")
    @ApiOperationSupport(order = 1)
    @Operation(tags = "表单开发",summary = "表单开发 - 保存")
    public BaseWebResult saveDbFormConfig(@RequestBody DbFormAddOrUpdateParam param, HttpServletRequest req) {
        //基本信息校验完成
        dbFormService.saveDbFormConfig(param);
        return BaseWebResult.success("成功");
    }

    @PreAuthorize("@ss.hasPermission('jeelowcode:dbform:update')")
    @PutMapping("/update")
    @ApiOperationSupport(order = 2)
    @Operation(tags = "表单开发",summary = "表单开发 - 修改")
    public BaseWebResult updateDbFormConfig(@RequestBody DbFormAddOrUpdateParam param) {
        dbFormService.updateDbFormConfig(param);
        return BaseWebResult.success("成功");
    }

    @PreAuthorize("@ss.hasPermission('jeelowcode:dbform:delete')")
    @DeleteMapping("/delete")
    @ApiOperationSupport(order = 3)
    @Operation(tags = "表单开发",summary = "表单开发 - 删除")
    public BaseWebResult deleteDbFormConfig(@RequestBody List<Long> dbFormIdList) {
        dbFormService.deleteDbFormConfig(dbFormIdList, true);
        return BaseWebResult.success("成功");
    }

    @PreAuthorize("@ss.hasPermission('jeelowcode:dbform:query')")
    @PostMapping("/detail")
    @ApiOperationSupport(order = 4)
    @Operation(tags = "表单开发",summary = "表单开发 - 获取详情")
    public BaseWebResult getDetailDbFormConfig(Long dbFormId, @RequestBody List<String> typeList) {
        List<DbFormTypeEnum> enumList = typeList.stream()
                .map(type -> DbFormTypeEnum.getByType(type))
                .collect(Collectors.toList());
        DbFormConfigVo vo = dbFormService.getDetailDbFormConfig(dbFormId, enumList);
        return BaseWebResult.success(vo);
    }

    @PreAuthorize("@ss.hasPermission('jeelowcode:dbform:query')")
    @PostMapping("/page")
    @ApiOperationSupport(order = 5)
    @Operation(tags = "表单开发",summary = "表单开发 - 获取分页数据")
    public BaseWebResult getPageDbFormConfig(@RequestBody PageDbFormParam param, PageVo pageVo) {
        Page page = FuncWeb.getPage(pageVo.getPageNo(), pageVo.getPageSize());
        IPage<FormEntity> pages = dbFormService.getPageDbFormConfig(param, page);
        IPage<FormEntityPageVo> vopages = toVoBean(pages, FormEntityPageVo.class);
        List<FormEntityPageVo> records = vopages.getRecords();
        super.formatFormEntityPageVo(records,dbFormService);
        return BaseWebResult.success(vopages);
    }


    @JeelowCodeValidate(title = "判断表名是否合法", validateClass = DbFormCopyValidate.class)
    @PreAuthorize("@ss.hasPermission('jeelowcode:dbform:create')")
    @GetMapping({"/copy/{dbformId}"})
    @ApiOperationSupport(order = 6)
    @Operation(tags = "表单开发",summary = "复制表")
    public BaseWebResult copy(@PathVariable("dbformId") Long dbformId, String tableName) {
        dbFormService.copyDbFormConfig(dbformId, tableName);
        return BaseWebResult.success("复制成功");
    }

    @PreAuthorize("@ss.hasPermission('jeelowcode:dbform:sync')")
    @JeeLowCodeTenantIgnore
    @PostMapping({"/sync-db/{dbformId}"})
    @ApiOperationSupport(order = 7)
    @Operation(tags = "表单开发",summary = "同步数据库")
    public BaseWebResult syncDb(@PathVariable("dbformId") Long dbformId, String syncModel) {
        try {
            frameService.syncDb(dbformId, syncModel);
        } catch (Exception e) {
            throw new JeeLowCodeMoreException("同步失败", e.getMessage());
        }
        return BaseWebResult.success("同步完成");
    }

    @GetMapping("/check/table")
    @JeeLowCodeTenantIgnore
    @ApiOperationSupport(order = 8)
    @Operation(tags = "表单开发",summary = "校验表明是否存在")
    public BaseWebResult checkTable(String tableName) {
        boolean flag = frameService.checkTable(tableName);
        return BaseWebResult.success(flag);
    }


    @GetMapping("/get/web-config")
    @ApiOperationSupport(order = 9)
    @Operation(tags = "表单开发",summary = "获取页面信息配置")
    public BaseWebResult getWebConfig(@RequestParam("dbformId")Long dbformId) {
        WebConfigVo webConfig = dbFormService.getWebConfig(dbformId);
        //判断是否有租户字段权限
        Long tenantId = jeeLowCodeAdapter.getTenantId();
        if(Func.isNotEmpty(tenantId) && Func.notEquals(tenantId,"-1")){
            //字段权限->部分租户只能看到部分字段
            List<DbFormRoleFieldVo> roleFieldVoList = dbFormRoleService.listRoleField(tenantId, dbformId,false);
            if(Func.isNotEmpty(roleFieldVoList)){
                List<WebConfigRoleFieldVo> webConfigRoleFieldVoList = BeanUtil.copyToList(roleFieldVoList, WebConfigRoleFieldVo.class);
                webConfig.setWebConfigRoleFieldVoList(webConfigRoleFieldVoList);
            }
            //按钮权限->部分租户只能看到部分按钮
            List<DbFormRoleButtonVo> roleButtonVoList = dbFormRoleService.listRoleButton(tenantId, dbformId, false);
            if(Func.isNotEmpty(roleButtonVoList)){
                List<String> webConfigRoleButtonVoList = roleButtonVoList.stream()
                        .map(DbFormRoleButtonVo::getButtonCode) // 提取 buttonCode
                        .collect(Collectors.toList()); // 收集到新的列表中
                webConfig.setWebConfigRoleButtonVoList(webConfigRoleButtonVoList);
            }

        }
        return BaseWebResult.success(webConfig);
    }




    @PostMapping("/get/dict-table-web-config/{dbformId}")
    @ApiOperationSupport(order = 10)
    @Operation(tags = "表单开发",summary = "获取字典表页面信息配置")
    public BaseWebResult getDictTableWebConfig(@PathVariable("dbformId") Long dbformId, HttpServletRequest req) {
        Map<String, Object> bodyParams = FuncWeb.getParameterBodyMap(req);

        List<String> fieldList = JeeLowCodeUtils.getMap2List(bodyParams, ParamEnum.DICT_TABLE_FIELD.getCode());//自定义列 id,name,age,sex
        if (FuncBase.isEmpty(fieldList)) {//查字典数据
            return BaseWebResult.error(FrameErrorCodeConstants.FRAME_PARAM_NULL_ERROR);
        }
        //获取指定列配置
        WebConfigVo webConfig = dbFormService.getWebConfig(dbformId, fieldList);
        webConfig.setButtonList(null);

        return BaseWebResult.success(webConfig);
    }

    @PostMapping("/get/table-label")
    @ApiOperationSupport(order = 11)
    @Operation(tags = "表单开发",summary = "表字段回显")
    public BaseWebResult getTableLabel(HttpServletRequest req) {
        Map<String, Object> bodyParams = FuncWeb.getParameterBodyMap(req);
        List<DictLabelParam> labelParamList = (List<DictLabelParam>) bodyParams.get(ParamEnum.DICT_LABEL.getCode());

        if (FuncBase.isEmpty(labelParamList)) {
            return BaseWebResult.error(FrameErrorCodeConstants.FRAME_PARAM_NULL_ERROR);
        }
        Map<String, List<Map<String, Object>>> resultMap = new ConcurrentHashMap<>();

        try {
            Func.jeelowcodeForkJoinPool().submit(() -> labelParamList.parallelStream().forEach(labelParam ->{
                Long dbformId = labelParam.getDbformId();
                List<String> fieldList = labelParam.getFieldList();
                String code = labelParam.getCode();
                String label = labelParam.getLabel();
                List<String> dataList = labelParam.getDataList();
                List<Long> userIdList = labelParam.getUserIdList();//用户id
                List<Long> deptIdList = labelParam.getDeptIdList();//部门id

                if (Func.isNotEmpty(dbformId)) { //自定义表
                    String mapKey = dbformId + "&" + label;
                    if (FuncBase.isEmpty(dataList)) {
                        List<Map<String, Object>> resultList = new ArrayList<>();
                        resultMap.put(FuncBase.toStr(dbformId), resultList);
                        return;
                    }

                    List<Map<String, Object>> resultList =new ArrayList<>();
                    for(String dataId:dataList){
                        Map<String, Object> params =new HashMap<>();
                        params.put(ParamEnum.DICT_TABLE_FIELD.getCode(),fieldList);
                        params.put(code,dataId);//id=1
                        params.put(ParamEnum.PAGE_NO.getCode(),1);
                        params.put(ParamEnum.PAGE_SIZE.getCode(),1000);
                        params.put(ParamEnum.ALL_QUERY_FIELD.getCode(),"Y");
                        ResultDataModel model = frameService.getDataList(dbformId, params);
                        List<Map<String, Object>> tmpList = model.getRecords();
                        if(Func.isEmpty(tmpList)){
                            continue;
                        }
                        resultList.addAll(tmpList);
                    }

                    resultMap.put(mapKey, resultList);
                }
                if (Func.isNotEmpty(userIdList)) {//用户表回显
                    List<Map<String, Object>> userViewList = jeeLowCodeAdapter.getUserViewList(userIdList);
                    resultMap.put("userList", userViewList);
                }
                if (Func.isNotEmpty(deptIdList)) {//部门表回显
                    List<Map<String, Object>> deptViewList = jeeLowCodeAdapter.getDeptViewList(deptIdList);
                    resultMap.put("deptList", deptViewList);
                }
            })).get();
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e.getMessage());
        }

        return BaseWebResult.success(resultMap);
    }


    @GetMapping("/get/all-table")
    @ApiOperationSupport(order = 13)
    @Operation(tags = "表单开发",summary = "获取所有表名")
    public BaseWebResult getAllTable(String systemFlag) {
        List<AllTableModel> list = dbFormService.getAllTable(systemFlag);
        return BaseWebResult.success(list);
    }

    @GetMapping("/clear-cache")
    @ApiOperationSupport(order = 14)
    @Operation(tags = "表单开发",summary = "清除缓存")
    public BaseWebResult clearCache() {
        dbFormService.cleanCache();
        return BaseWebResult.success("成功");
    }

    @PostMapping("/get/tree-parent")
    @ApiOperationSupport(order = 10)
    @Operation(tags = "表单开发",summary = "获取字典表页面信息配置")
    public BaseWebResult getTreeParentList( HttpServletRequest req) {
        Map<String, Object> bodyParams = FuncWeb.getParameterBodyMap(req);
        if(!bodyParams.containsKey(ParamEnum.TREE_PARENT.getCode())){
            return BaseWebResult.error(FrameErrorCodeConstants.FRAME_PARAM_NULL_ERROR);
        }


        List<TreeParentParam> paramList = (List<TreeParentParam>) bodyParams.get(ParamEnum.TREE_PARENT.getCode());

        Map<String,List<Map<String, Object>>> resultMap=new HashMap<>();
        paramList.stream().forEach(param ->{
            Long dbformId = param.getDbformId();
            List<Map<String, Object>> resultDataMapList = dbFormService.getTreeParentList(dbformId, param);
            resultMap.put(dbformId.toString(),resultDataMapList);
        });
        return BaseWebResult.success(resultMap);
    }



    @PreAuthorize("@ss.hasPermission('jeelowcode:dbform:create')")
    @PostMapping("/explain/datasource-sql")
    @ApiOperationSupport(order = 11)
    @Operation(tags = "表单开发",summary = "解释数据源SQL的运行结果")
    public BaseWebResult explainDatasourceSql(@RequestBody ExplainDatasourceSqlModel model) {
        Page page = FuncWeb.getPage(1, 10);
        SqlInfoQueryWrapper.Wrapper queryWrapper = SqlHelper.getQueryWrapper();
        queryWrapper.setTableSql(model.getExplainSql());
        try{
            IPage<Map<String, Object>> pages = sqlService.getDataIPageByPlus(page, queryWrapper);
            return BaseWebResult.success(pages);
        }catch (Exception  e){
            throw new JeeLowCodeMoreException("SQL执行错误",e.getMessage());
        }

    }

    @PreAuthorize("@ss.hasPermission('jeelowcode:dbform:create')")
    @PostMapping("/explain/sqlfield")
    @ApiOperationSupport(order = 11)
    @Operation(tags = "表单开发",summary = "解释数据源SQL字段类型")
    public BaseWebResult explainSqlField(@RequestBody List<ExplainSqlFieldModel> modelList) {
        Map<String, Object> resultMap = dbFormService.explainSqlField(modelList);
        return BaseWebResult.success(resultMap);
    }

    @PreAuthorize("@ss.hasPermission('jeelowcode:dbform:create')")
    @GetMapping("/get/not-in-dbform-tables")
    @ApiOperationSupport(order = 11)
    @Operation(tags = "表单开发",summary = "获取未在表单开发里面的表")
    public BaseWebResult getNotInDbformTables() {
        List<NotInDbformTablesVo> voList = dbFormService.getNotInDbformTables();
        return BaseWebResult.success(voList);
    }

    @PreAuthorize("@ss.hasPermission('jeelowcode:dbform:create')")
    @GetMapping("/get/tables-field")
    @ApiOperationSupport(order = 11)
    @Operation(tags = "表单开发",summary = "获取表字段")
    public BaseWebResult getTableField(String tableName) {
        List<TableFieldModelVo> voList = dbFormService.getTableFieldComment(tableName);
        return BaseWebResult.success(voList);
    }
}
