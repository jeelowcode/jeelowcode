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
package com.jeelowcode.core.framework.config.btncommand.receiver;

import com.jeelowcode.core.framework.entity.ReportEntity;
import com.jeelowcode.core.framework.params.vo.role.DbFormRoleFieldVo;
import com.jeelowcode.framework.excel.model.ExcelTitleModel;
import com.jeelowcode.framework.exception.JeeLowCodeException;
import com.jeelowcode.framework.utils.enums.YNEnum;
import com.jeelowcode.framework.utils.model.ResultDataModel;
import com.jeelowcode.framework.utils.tool.spring.SpringUtils;
import com.jeelowcode.core.framework.config.virtualization.VirtualizationFieldPluginManager;
import com.jeelowcode.core.framework.entity.FormFieldWebEntity;
import com.jeelowcode.core.framework.mapper.FormFieldWebMapper;
import com.jeelowcode.core.framework.params.model.WebFormatConfigModel;
import com.jeelowcode.core.framework.service.IFormService;
import com.jeelowcode.core.framework.utils.Func;
import org.apache.commons.collections4.map.LinkedMap;

import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

/**
 * 公共
 */
public class ButtonReceiverBase {

    //移除不是导入项目
    public void removeNotExport(LinkedMap<String, ExcelTitleModel> headTitleMap, List<DbFormRoleFieldVo> roleFieldVoList) {
        if(Func.isEmpty(roleFieldVoList)){
            return;
        }
        Map<String, DbFormRoleFieldVo> roleDisableMap =roleFieldVoList.stream()
                    .collect(Collectors.toMap(DbFormRoleFieldVo::getFieldCode, entity -> entity));

        Iterator<Map.Entry<String, ExcelTitleModel>> iterator = headTitleMap.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, ExcelTitleModel> entry = iterator.next();
            String key = entry.getKey();
            if(roleDisableMap.containsKey(key)){//存在权限问题
                DbFormRoleFieldVo roleVo = roleDisableMap.get(key);
                //如果全部为空话，看最大的那个,最大的那个为N说明是没有权限，需要剔除
                if(Func.isEmpty(roleVo.getFormIsEdit()) && Func.isEmpty(roleVo.getFormIsView()) && Func.isEmpty(roleVo.getListIsView())&&Func.equals(roleVo.getEnableState(), YNEnum.N.getCode())){
                    iterator.remove();
                }
            }
        }
    }
    /**
     *
     * 移除不是界面显示的列表-列表数据
     *
     * @param records
     */
    public void removeNotWebView(Long dbFormId, List<Map<String, Object>> records,List<DbFormRoleFieldVo> roleFieldVoList) {
        if (Func.isEmpty(records)) {
            return;
        }
        Map<String, DbFormRoleFieldVo> roleDisableMap =null;
        if(Func.isNotEmpty(roleFieldVoList)){
            //转为map
            roleDisableMap = roleFieldVoList.stream()
                    .collect(Collectors.toMap(DbFormRoleFieldVo::getFieldCode, entity -> entity));
        }

        IFormService formService = SpringUtils.getBean(IFormService.class);
        Set<String> webViewFieldSet = new HashSet<>(formService.getWebViewFieldList(dbFormId));

        Map<String, DbFormRoleFieldVo> finalRoleDisableMap = roleDisableMap;
        try {
            Func.jeelowcodeForkJoinPool().submit(() -> records.parallelStream().forEach(recordMap -> recordMap.keySet().removeIf(key -> {
                if(Func.equals(key,"jeelowcode_subtable_data") || Func.equals(key,"hasChildren") || Func.equals(key,"leaf")){
                    return false;
                }
                boolean noRoleFlag=false;
                if(Func.isNotEmpty(finalRoleDisableMap) && finalRoleDisableMap.containsKey(key)){
                    DbFormRoleFieldVo roleVo = finalRoleDisableMap.get(key);
                    //如果全部为空话，看最大的那个,最大的那个为N说明是没有权限，需要剔除
                    if(Func.isEmpty(roleVo.getFormIsEdit()) && Func.isEmpty(roleVo.getFormIsView()) && Func.isEmpty(roleVo.getListIsView())&&Func.equals(roleVo.getEnableState(),YNEnum.N.getCode())){
                        noRoleFlag=true;
                    }
                }
                return !webViewFieldSet.contains(key) || noRoleFlag;
            }))).get();
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    /**
     * 处理字段凭接（虚拟化字段）
     *
     * @param dbFormId
     * @param records
     */
    public void webViewAppend(Long dbFormId, List<Map<String, Object>> records) {
        FormFieldWebMapper fieldWebMapper = SpringUtils.getBean(FormFieldWebMapper.class);

        // 获取表单的字段对应的字典
        IFormService formService = SpringUtils.getBean(IFormService.class);
        Map<String, Map<String, Object>> fieldDictMap = formService.getFieldDict(dbFormId);

        List<FormFieldWebEntity> webEntityList = fieldWebMapper.webEntityListAndFormatConfigIsNotNull(dbFormId);
        webEntityList.stream().forEach(web->{
            String fieldCode = web.getFieldCode();
            String formatConfig = web.getFormatConfig();
            if(Func.isEmpty(formatConfig)){
                return;
            }
            WebFormatConfigModel webFormatConfigModel = Func.json2Bean(formatConfig, WebFormatConfigModel.class);
            if(Func.isEmpty(webFormatConfigModel)){
                return;
            }
            String formatType = webFormatConfigModel.getFormatType();
            if(Func.isEmpty(formatType) || !Func.equals(formatType,"java")){
                return;
            }
            WebFormatConfigModel.Json  formatJson= webFormatConfigModel.getFormatJson();
            WebFormatConfigModel.JavaModel javaModel = formatJson.getJava();
            String valueType = javaModel.getValueType();
            List<WebFormatConfigModel.JavaGroup> groupList = javaModel.getGroup();
            WebFormatConfigModel.JavaCustom custom = javaModel.getCustom();
            switch (valueType){
                case "group"://表达式
                    records.stream().forEach(record -> {
                        record.put(fieldCode, this.getGroupVal(fieldDictMap, groupList, new HashMap<>(record)));
                    });
                    break;
                case "custom":
                    records.stream().forEach(record -> {
                        //运行插件
                        String result = VirtualizationFieldPluginManager.executePlugin(custom.getJavaPath(), new HashMap<>(record));
                        record.put(fieldCode, result);
                    });
                    break;
            }
        });
    }


    //获取分组
    private String getGroupVal(Map<String, Map<String, Object>> fieldDictMap, List<WebFormatConfigModel.JavaGroup> groupList, Map<String, Object> dataMap) {
        this.replaceDict(fieldDictMap, dataMap);// 替换字典值
        String formatVal = ""; // 用于存储格式化后的值
        for (WebFormatConfigModel.JavaGroup group : groupList) {
            String value = group.getValue();
            String type = group.getType();
            if(Func.isEmpty(value)){
                continue;
            }
            switch (type) {
                case "CONCAT":
                    formatVal += value;
                    break;
                case "CALCULATE":
                    formatVal += Func.executeJavaExpress(value, dataMap);
                    break;
            }
        }
        return formatVal;
    }

    /**
     * 替换字典值
     */
    private void replaceDict(Map<String, Map<String, Object>> fieldDictMap, Map<String, Object> dataMap){
        if (Func.isEmpty(fieldDictMap) || Func.isEmpty(dataMap)) {
            return;
        }
        fieldDictMap.keySet().stream().forEach(field -> {
            Object oldValue = dataMap.get(field);
            if (Func.isEmpty(oldValue)) {
                return;
            }
            Map<String, Object> dictMap = fieldDictMap.get(field);
            if (Func.isEmpty(dictMap)) {
                return;
            }
            Object newValue = dictMap.get(oldValue.toString());
            if (Func.isEmpty(newValue)) {
                return;
            }
            dataMap.put(field, newValue);
        });
    }
}
