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
package com.jeelowcode.core.framework.utils;


import com.jeelowcode.core.framework.config.aspect.enhance.model.EnhanceContext;
import com.jeelowcode.core.framework.config.aspect.enhance.model.EnhanceResult;
import com.jeelowcode.core.framework.config.aspect.enhancereport.model.EnhanceReportContext;
import com.jeelowcode.framework.utils.constant.JeeLowCodeConstant;
import com.jeelowcode.framework.utils.enums.ParamEnum;
import com.jeelowcode.framework.utils.tool.AesUtil;
import com.jeelowcode.framework.utils.tool.spring.SpringUtils;
import com.jeelowcode.framework.utils.utils.FuncBase;
import com.jeelowcode.framework.utils.utils.FuncWebBase;
import com.jeelowcode.framework.utils.utils.JeeLowCodeUtils;
import com.jeelowcode.framework.utils.component.properties.JeeLowCodeProperties;
import com.jeelowcode.core.framework.params.DictLabelParam;
import com.jeelowcode.core.framework.params.DictTableParam;
import com.jeelowcode.core.framework.params.TreeParentParam;
import com.jeelowcode.core.framework.service.impl.FormServiceImpl;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.web.util.ContentCachingRequestWrapper;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.*;

/**
 * 网络工具包集合，工具类快捷方式
 */
public class FuncWeb extends FuncWebBase {


    //获取分页
    public static Page getPage(Map<String, Object> params){
        Integer pageNo = Func.getMap2Int(params, ParamEnum.PAGE_NO.getCode());
        Integer pageSize = Func.getMap2Int(params, ParamEnum.PAGE_SIZE.getCode());
        pageNo = FuncBase.isEmpty(pageNo) ? 1 : pageNo;
        pageSize = FuncBase.isEmpty(pageSize) ? JeeLowCodeConstant.NOT_PAGE : pageSize;

        Page page=new Page<>(pageNo,pageSize);//分页
        return page;
    }
    public static Page getPage(Integer pageNo,Integer pageSize){
        Map<String, Object> params=new HashMap<>();
        params.put(ParamEnum.PAGE_NO.getCode(),pageNo);
        params.put( ParamEnum.PAGE_SIZE.getCode(),pageSize);
        return getPage(params);
    }

    /**
     * 增强内设置返回结果<br/>
     * 不往下执行框架的查询以及后续增强
     *
     * @param enhanceContext
     * @param page
     */
    public static void setPageResult(EnhanceContext enhanceContext, IPage<Map<String, Object>> page) {
        setPageResult(enhanceContext, page, true);
    }

    /**
     * 增强内设置返回结果
     *
     * @param enhanceContext
     * @param page
     * @param exitFlag       exitFlag=true则不往下执行框架的查询以及后续增强
     */
    public static void setPageResult(EnhanceContext enhanceContext, IPage<Map<String, Object>> page, boolean exitFlag) {
        List<Map<String, Object>> records;
        long total;
        if (Func.isEmpty(page) || Func.isEmpty(page.getRecords())) {
            records = new ArrayList<>();
            total = 0;
        } else {
            records = page.getRecords();
            total = page.getTotal();
        }
        // exitFlag=true：不往下执行框架的查询以及后续增强
        // id：用于新增、修改和删除，这里null就可以
        enhanceContext.setResult(new EnhanceResult(exitFlag, null, total, records));
    }

    /**
     * 报表增强内设置返回结果
     *
     * @param enhanceReportContext
     * @param list
     */
    public static void setReportResult(EnhanceReportContext enhanceReportContext, List<Map<String, Object>> list) {
        if (Func.isEmpty(list)) {
            list = new ArrayList<>();
        }
        enhanceReportContext.getResult().setTotal((long) list.size());
        enhanceReportContext.getResult().setRecords(list);
    }

    /**
     * 增强内设置返回结果<br/>
     * 不往下执行框架的查询以及后续增强
     *
     * @param enhanceContext
     * @param map
     */
    public static void setDetailResult(EnhanceContext enhanceContext, Map<String, Object> map) {
        setDetailResult(enhanceContext, map, true);
    }

    /**
     * 增强内设置返回结果
     *
     * @param enhanceContext
     * @param map
     * @param exitFlag exitFlag=true则不往下执行框架的查询以及后续增强
     */
    public static void setDetailResult(EnhanceContext enhanceContext, Map<String, Object> map, boolean exitFlag) {
        // id：用于新增、修改和删除，这里null就可以
        enhanceContext.setResult(new EnhanceResult(exitFlag, null, 1L, Collections.singletonList(map)));
    }

    /**
     * 获取所有参数
     *
     * @param request
     * @return
     */
    public static Map<String, Object> getParameterMap(HttpServletRequest request) {
        ContentCachingRequestWrapper requestWrapper = new ContentCachingRequestWrapper(request);
        Map<String, Object> resultMap = new HashMap<>();

        //获取所有参数
        Map<String, String[]> parameterMap = requestWrapper.getParameterMap();
        Iterator<Map.Entry<String, String[]>> iterator = parameterMap.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, String[]> map = iterator.next();

            String key = map.getKey();
            String[] values = map.getValue();

            // 字符串计数器
            String vauleStr = "";
            if (FuncBase.isNotEmpty(values)) {
                // value不是字符数组,转成字符串
                if (!(values instanceof String[])) {
                    vauleStr = values.toString();
                } else {
                    for (String val : values) {
                        vauleStr = val + ",";
                    }
                    vauleStr = vauleStr.substring(0, vauleStr.length() - 1);
                }
            } else {
                vauleStr = "";
            }
            // 重新封装请求参数:
            resultMap.put(key, vauleStr);
        }

        if (requestWrapper.getMethod().equalsIgnoreCase("POST")) {
            try {
                BufferedReader reader = requestWrapper.getReader();
                StringBuilder sb = new StringBuilder();
                String line;
                while (null != (line = reader.readLine())) {
                    sb.append(line);
                }
                Map<String, Object> bodyMap =Func.json2Bean(sb.toString(), Map.class);
                if(FuncBase.isEmpty(bodyMap)){
                    bodyMap=new HashMap<>();
                }
                resultMap.put(ParamEnum.REQUEST_PARAM_BODY.getCode(),bodyMap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        Map<String,Object> bodyMap = (Map<String,Object>)resultMap.get(ParamEnum.REQUEST_PARAM_BODY.getCode());
        if(FuncBase.isEmpty(bodyMap)){
            bodyMap=new HashMap<>();
            resultMap.put(ParamEnum.REQUEST_PARAM_BODY.getCode(),bodyMap);
        }
        //特殊参数值处理
        if(bodyMap.containsKey(ParamEnum.DICT_TABLE_FIELD.getCode())){//字典表显示列
            handleSelfParam_dictTable(bodyMap,ParamEnum.DICT_TABLE_FIELD);
        }
        if(bodyMap.containsKey(ParamEnum.DICT_LABEL.getCode())){//字典表回显
            handleSelfParam_dictLabel(bodyMap,ParamEnum.DICT_LABEL);
        }
        if(bodyMap.containsKey(ParamEnum.TREE_PARENT.getCode())){//父级树表
            handleSelfParam_treeParent(bodyMap,ParamEnum.TREE_PARENT);
        }

        return resultMap;
    }

    /**
     * 获取body参数 转为map
     * @param request
     * @return
     */
    public static Map<String, Object> getParameterBodyMap(HttpServletRequest request) {
        Map<String, Object> params = getParameterMap(request);
        Map<String,Object> bodyMap = (Map<String,Object>)params.get(ParamEnum.REQUEST_PARAM_BODY.getCode());
        return bodyMap;
    }

    /**
     * 获取body参数 转为对象
     * @param request
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> T getParameterBodyObj(HttpServletRequest request, Class<T> clazz) {
        Map<String,Object> bodyMap = getParameterBodyMap(request);

        String jsonStr = Func.json2Str(bodyMap);
        return Func.json2Bean(jsonStr,clazz);
    }


    /**
     * 字典表自定义列
     *
     * @param resultMap
     * @param paramEnum
     */
    private static void handleSelfParam_dictTable(Map<String, Object> resultMap, ParamEnum paramEnum) {
        String str = JeeLowCodeUtils.getMap2Str(resultMap, paramEnum.getCode());//自定义列  id,name,age,sex  id,name,age,sex
        if (FuncBase.isEmpty(str)) {
            return;
        }
        FormServiceImpl proxyService = SpringUtils.getBean(FormServiceImpl.class);
        //解密
        String jsonStr = AesUtil.decryptFormBase64ToString(str, JeeLowCodeProperties.getAesKey());
        DictTableParam dictTableParam = Func.json2Bean(jsonStr, DictTableParam.class);
        if(Func.isEmpty(dictTableParam.getDbformId())){
            return;
        }
        List<String> fieldList = proxyService.getDictTableFieldList(dictTableParam.getDbformId(), dictTableParam.getFieldCodeList());

        resultMap.put(paramEnum.getCode(),fieldList);//转为字符串

    }

    /**
     * 字典表回显
     *
     * @param resultMap
     * @param paramEnum
     */
    private static void handleSelfParam_dictLabel(Map<String, Object> resultMap, ParamEnum paramEnum) {
        String str = JeeLowCodeUtils.getMap2Str(resultMap, paramEnum.getCode());//自定义列  id,name,age,sex  id,name,age,sex
        if (FuncBase.isEmpty(str)) {
            return;
        }
        FormServiceImpl proxyService = SpringUtils.getBean(FormServiceImpl.class);
        //解密
        String jsonStr = AesUtil.decryptFormBase64ToString(str, JeeLowCodeProperties.getAesKey());
        List<DictLabelParam> labelParamList = Func.json2List(jsonStr, DictLabelParam.class);

        for (DictLabelParam param : labelParamList) {
            String code = param.getCode();// where id =
            String label = param.getLabel();//selct name

            if(Func.isEmpty(param.getDbformId())){
                continue;
            }

            List<String> selectFieldList = new ArrayList<>();
            selectFieldList.add(code);
            selectFieldList.add(label);



            List<String> fieldList = proxyService.getDictTableFieldList(param.getDbformId(), selectFieldList);
            param.setFieldList(fieldList);

        }

        resultMap.put(paramEnum.getCode(), labelParamList);//转为字符串

    }

    /**
     * 上级鼠标
     *
     * @param resultMap
     * @param paramEnum
     */
    private static void handleSelfParam_treeParent(Map<String, Object> resultMap, ParamEnum paramEnum) {
        String str = JeeLowCodeUtils.getMap2Str(resultMap, paramEnum.getCode());//自定义列  id,name,age,sex  id,name,age,sex
        if (FuncBase.isEmpty(str)) {
            return;
        }

        //解密
        String jsonStr = AesUtil.decryptFormBase64ToString(str, JeeLowCodeProperties.getAesKey());
        List<TreeParentParam> paramList = Func.json2List(jsonStr, TreeParentParam.class);

        resultMap.put(paramEnum.getCode(), paramList);//转为字符串

    }

}
