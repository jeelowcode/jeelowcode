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
import cn.hutool.json.JSONObject;
import com.jeelowcode.core.framework.config.btncommand.ButtonInvoker;
import com.jeelowcode.core.framework.config.btncommand.button.*;
import com.jeelowcode.core.framework.config.btncommand.param.*;
import com.jeelowcode.core.framework.config.btncommand.receiver.*;
import com.jeelowcode.core.framework.params.model.ExcelImportResultModel;
import com.jeelowcode.core.framework.params.model.ExcelModel;
import com.jeelowcode.core.framework.params.model.ExcelTemplateModel;
import com.jeelowcode.core.framework.params.vo.FormEntityPageVo;
import com.jeelowcode.core.framework.service.IFormService;
import com.jeelowcode.core.framework.utils.Func;
import com.jeelowcode.core.framework.utils.FuncWeb;
import com.jeelowcode.framework.exception.JeeLowCodeException;
import com.jeelowcode.framework.utils.model.ExecuteEnhanceModel;
import com.jeelowcode.framework.utils.model.ResultDataModel;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

@Tag(name = "低代码框架-公共框架接口")
public class BaseController {


    /**
     * 转换类型
     * @param source
     * @param targetType
     * @param <S>
     * @param <T>
     * @return
     */
    public static <S, T> List<T> toVoBean(List<S> source, Class<T> targetType) {
        if (source == null) {
            return null;
        }
        List<T> list = BeanUtil.copyToList(source, targetType);

        return list;
    }

    /**
     * 转换类型
     * @param source
     * @param targetType
     * @param <S>
     * @param <T>
     * @return
     */
    public static <S, T> IPage<T> toVoBean(IPage<S> source, Class<T> targetType) {
        if (source == null) {
            return null;
        }
        List<T> list = BeanUtil.copyToList(source.getRecords(), targetType);

        IPage<T> resultPage=new Page<>();
        resultPage.setTotal(source.getTotal());
        resultPage.setRecords(list);
        resultPage.setPages(source.getPages());
        resultPage.setSize(source.getSize());
        resultPage.setCurrent(source.getCurrent());
        return resultPage;
    }


    //获取分页数据列表
    protected ResultDataModel getDataPage(Long dbformId, Map<String, Object> params){
        Page page = FuncWeb.getPage(params);

        //封装参数
        ButtonParamList buttonParam = new ButtonParamList();
        buttonParam.setDbFormId(dbformId);
        buttonParam.setParams(params);
        buttonParam.setPage(page);
        //执行者绑定参数
        IButtonCommandReceiver receiver = new ButtonReceiverList(buttonParam);

        //执行者和命令绑定
        ListButtonCommand command = new ListButtonCommand(receiver);

        //创建调用者
        ButtonInvoker<ResultDataModel> invoker = new ButtonInvoker();
        invoker.setButtonCommand(command);//调用者设置命令
        ResultDataModel resultDataModel = invoker.executeCommand();//调用者下发命令

        return resultDataModel;
    }

    //获取统计
    protected Map getSummaryData(Long dbformId, Map<String, Object> params){
        //封装参数
        ButtonParamSummary buttonParam = new ButtonParamSummary();
        buttonParam.setDbFormId(dbformId);
        buttonParam.setParams(params);

        //执行者绑定参数
        IButtonCommandReceiver receiver = new ButtonReceiverSummary(buttonParam);

        //执行者和命令绑定
        SummaryButtonCommand command = new SummaryButtonCommand(receiver);

        //创建调用者
        ButtonInvoker<Map> invoker = new ButtonInvoker();
        invoker.setButtonCommand(command);//调用者设置命令
        Map map = invoker.executeCommand();//调用者下发命令
        return map;
    }

    //获取详情数据列表
    protected ResultDataModel getDataDetail(Long dbformId,Long dataId, Map<String, Object> params){
        //封装参数
        params.put("id",dataId);//把id放到参数集合里面

        ButtonParamDetail buttonParam = new ButtonParamDetail();
        buttonParam.setDbFormId(dbformId);
        buttonParam.setDataId(dataId);
        buttonParam.setParams(params);

        //执行者绑定参数
        IButtonCommandReceiver receiver = new ButtonReceiverDetail(buttonParam);

        //执行者和命令绑定
        DetailsButtonCommand command = new DetailsButtonCommand(receiver);

        //创建调用者
        ButtonInvoker<ResultDataModel> invoker = new ButtonInvoker();
        invoker.setButtonCommand(command);//调用者设置命令
        ResultDataModel resultDataModel = invoker.executeCommand();//调用者下发命令
        return resultDataModel;
    }

    //新增数据
    protected String addJsonData(Long dbformId, JSONObject jsonObject){
        ButtonParamAdd buttonParam = new ButtonParamAdd();
        buttonParam.setDbFormId(dbformId);
        buttonParam.setParams(jsonObject);
        IButtonCommandReceiver receiver = new ButtonReceiverAdd(buttonParam);

        //执行者和命令绑定
        AddButtonCommand command = new AddButtonCommand(receiver);

        //创建调用者
        ButtonInvoker<ExecuteEnhanceModel> invoker = new ButtonInvoker();
        invoker.setButtonCommand(command);//调用者设置命令
        ExecuteEnhanceModel saveDataModel = invoker.executeCommand();//调用者下发命令

        return saveDataModel.getId();
    }
    protected List<String> addJsonData(Long dbformId, List<JSONObject> jsonObjectList){
        ButtonParamAddBatch buttonParam = new ButtonParamAddBatch();
        buttonParam.setDbFormId(dbformId);
        buttonParam.setDataMapList(jsonObjectList);

        IButtonCommandReceiver receiver = new ButtonReceiverAddBatch(buttonParam);

        //执行者和命令绑定
        IButtonCommand command = new AddBatchButtonCommand(receiver);

        //创建调用者
        ButtonInvoker<List<String>> invoker = new ButtonInvoker();
        invoker.setButtonCommand(command);//调用者设置命令
        List<String> idList = invoker.executeCommand();//调用者下发命令
        return idList;
    }

    //编辑数据
    protected void editJsonData(Long dbformId, JSONObject jsonObject){
        Long id = jsonObject.getLong("id");

        //封装参数
        ButtonParamEdit buttonParam = new ButtonParamEdit();
        buttonParam.setDbFormId(dbformId);
        buttonParam.setDataId(id);
        buttonParam.setParams(jsonObject);

        //执行者绑定参数
        IButtonCommandReceiver receiver = new ButtonReceiverEdit(buttonParam);

        //执行者和命令绑定
        EditButtonCommand command = new EditButtonCommand(receiver);

        //创建调用者
        ButtonInvoker<ExecuteEnhanceModel> invoker = new ButtonInvoker();
        invoker.setButtonCommand(command);//调用者设置命令
        invoker.executeCommand();//调用者下发命令
    }

    protected void editJsonData(Long dbformId, List<JSONObject> jsonObjectList){
        //封装参数
        ButtonParamEditBatch buttonParam = new ButtonParamEditBatch();
        buttonParam.setDbFormId(dbformId);
        buttonParam.setJsonObjectList(jsonObjectList);

        //执行者绑定参数
        IButtonCommandReceiver receiver = new ButtonReceiverEditBatch(buttonParam);

        //执行者和命令绑定
        EditBatchButtonCommand command = new EditBatchButtonCommand(receiver);

        //创建调用者
        ButtonInvoker<ExecuteEnhanceModel> invoker = new ButtonInvoker();
        invoker.setButtonCommand(command);//调用者设置命令
        invoker.executeCommand();//调用者下发命令

    }

    //删除数据
    protected void deleteDataById(Long dbformId, List<Long> dataIdList){

        //封装参数
        ButtonParamDel delButtonParam = new ButtonParamDel();
        delButtonParam.setDbFormId(dbformId);
        delButtonParam.setDataIdList(dataIdList);

        //执行者绑定参数
        IButtonCommandReceiver receiver = new ButtonReceiverDel(delButtonParam);

        //执行者和命令绑定
        DelButtonCommand command = new DelButtonCommand(receiver);

        //创建调用者
        ButtonInvoker<ExecuteEnhanceModel> invoker = new ButtonInvoker();
        invoker.setButtonCommand(command);//调用者设置命令
        invoker.executeCommand();//调用者下发命令

    }

    //导出模板
    protected ExcelTemplateModel exportExcelTemplate(Long dbformId){
        //封装参数
        ButtonParamExportTemplate buttonParam = new ButtonParamExportTemplate();
        buttonParam.setDbFormId(dbformId);
        //执行者绑定参数
        IButtonCommandReceiver receiver = new ButtonReceiverExportTemplate(buttonParam);

        //执行者和命令绑定
        ExportTemplateButtonCommand command = new ExportTemplateButtonCommand(receiver);

        //创建调用者
        ButtonInvoker<ExcelTemplateModel> invoker = new ButtonInvoker();
        invoker.setButtonCommand(command);//调用者设置命令
        ExcelTemplateModel excelTemplateModel = invoker.executeCommand();//调用者下发命令
        return excelTemplateModel;
    }
    //导出Excel数据
    protected ExcelModel exportExcelData(Long dbformId, Map<String, Object> params ){

        //封装参数
        ButtonParamExport buttonParam = new ButtonParamExport();
        buttonParam.setDbFormId(dbformId);
        buttonParam.setParams(params);


        //执行者绑定参数
        IButtonCommandReceiver receiver = new ButtonReceiverExport(buttonParam);

        //执行者和命令绑定
        ExportButtonCommand command = new ExportButtonCommand(receiver);

        //创建调用者
        ButtonInvoker<ExcelModel> invoker = new ButtonInvoker();
        invoker.setButtonCommand(command);//调用者设置命令
        ExcelModel excelModel = invoker.executeCommand();//调用者下发命令
        return excelModel;
    }

    //导入Excel数据
    protected ExcelImportResultModel importExcelData(ButtonParamImport buttonParam){
        //执行者绑定参数
        IButtonCommandReceiver receiver = new ButtonReceiverImport(buttonParam);

        //执行者和命令绑定
        ImportButtonCommand command = new ImportButtonCommand(receiver);

        //创建调用者
        ButtonInvoker<ExcelImportResultModel> invoker = new ButtonInvoker();
        invoker.setButtonCommand(command);//调用者设置命令
        ExcelImportResultModel resultModel = invoker.executeCommand();//调用者下发命令
        return resultModel;//批次号
    }

    //-----------  数据统计  -------------------

    //获取分页数据列表
    protected ResultDataModel getReportDataList(String reportCode, Map<String, Object> params){
        Page page = FuncWeb.getPage(params);

        //封装参数
        ButtonParamReportList buttonParam = new ButtonParamReportList();
        buttonParam.setReportCode(reportCode);
        buttonParam.setParams(params);
        buttonParam.setPage(page);
        //执行者绑定参数
        ButtonReceiverReportList receiver = new ButtonReceiverReportList(buttonParam);

        //执行者和命令绑定
        ListButtonCommand command = new ListButtonCommand(receiver);

        //创建调用者
        ButtonInvoker<ResultDataModel> invoker = new ButtonInvoker();
        invoker.setButtonCommand(command);//调用者设置命令
        ResultDataModel resultDataModel = invoker.executeCommand();//调用者下发命令

        return resultDataModel;
    }


    protected void formatFormEntityPageVo(List<FormEntityPageVo> records, IFormService dbFormService){
        try {
            Func.jeelowcodeForkJoinPool().submit(() -> records.parallelStream().forEach(vo ->{
                Map<String, Object> couMap = dbFormService.getFormCou(vo.getId());
                Integer js_cou = Func.getMap2IntDefault(couMap, "js_cou",0);
                Integer scss_cou = Func.getMap2IntDefault(couMap, "scss_cou",0);
                Integer button_cou = Func.getMap2IntDefault(couMap, "button_cou",0);
                Integer java_cou = Func.getMap2IntDefault(couMap, "java_cou",0);
                Integer sql_cou = Func.getMap2IntDefault(couMap, "sql_cou",0);
                Integer field_cou = Func.getMap2IntDefault(couMap, "field_cou",0);

                vo.setJsCou(js_cou);
                vo.setScssCou(scss_cou);
                vo.setButtonCou(button_cou);
                vo.setJavaCou(java_cou);
                vo.setSqlCou(sql_cou);
                vo.setFieldCou(field_cou);
            })).get();
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    //导出Excel数据
    protected ExcelModel exportReportData(String reportCode, Map<String, Object> params ){

        //封装参数
        ButtonParamReportExport buttonParam = new ButtonParamReportExport();
        buttonParam.setReportCode(reportCode);
        buttonParam.setParams(params);

        //执行者绑定参数
        IButtonCommandReceiver receiver = new ButtonReceiverReportExport(buttonParam);

        //执行者和命令绑定
        ExportButtonCommand command = new ExportButtonCommand(receiver);

        //创建调用者
        ButtonInvoker<ExcelModel> invoker = new ButtonInvoker();
        invoker.setButtonCommand(command);//调用者设置命令
        ExcelModel excelModel = invoker.executeCommand();//调用者下发命令
        return excelModel;
    }
}
