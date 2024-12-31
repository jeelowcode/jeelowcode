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
package com.jeelowcode.framework.excel.write;

import com.jeelowcode.framework.excel.handler.CustomSheetWriteHandler;
import com.jeelowcode.framework.excel.handler.RowFormatSetTextHandler;
import com.jeelowcode.framework.excel.model.ExcelTitleModel;
import com.jeelowcode.framework.exception.JeeLowCodeException;
import com.jeelowcode.framework.utils.utils.FuncBase;
import com.alibaba.excel.EasyExcel;
import org.apache.commons.collections4.map.LinkedMap;

import javax.servlet.http.HttpServletResponse;
import java.net.URLEncoder;
import java.util.*;

public class ExcelExportUtils {

    /**
     * 封装表头信息
     * @param headMap {name:姓名}  {age:年龄}
     * @return
     */
    private static List<List<String>> getExcelHeaderList(LinkedMap<String, ExcelTitleModel> headMap){
        //处理头部
        List<List<String>> head = new ArrayList<>();
        headMap.forEach((code, model) -> {
            List<String> headList = new ArrayList<>();
            headList.add(model.getTitle());
            head.add(headList);
        });
        return head;
    }

    /**
     * 封装表数据
     * @param headMap
     * @param dataList ｛name:张三｝｛age:18｝
     * @return
     */
    private static  List<List<Object>> getExcelDataList(LinkedMap<String, ExcelTitleModel> headMap, List<Map<String, Object>> dataList){
        List<List<Object>> data=new ArrayList<>();
        //处理数据
        dataList.stream().forEach(dataMap->{
            List<Object> tmpList=new ArrayList<>();
            headMap.forEach((code,title)->{
                Object tmpVal = dataMap.get(code);
                tmpList.add(tmpVal);
            });
            data.add(tmpList);
        });
        return data;
    }


    /**
     * 动态表头导出
     * @param response
     * @param sheetName
     * @param headMap
     * @param dataList
     */
    public static void exportExcel(HttpServletResponse response, String sheetName, LinkedMap<String, ExcelTitleModel> headMap, List<Map<String, Object>> dataList) {
        try {
             if(FuncBase.isEmpty(headMap)){
                 throw new JeeLowCodeException("表头不允许为空");
             }


            //设置mime类型
            response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
            //设置编码
            response.setCharacterEncoding("utf-8");
            // 这里URLEncoder.encode可以防止中文乱码 当然和easyexcel没有关系
            String outFileName = URLEncoder.encode(sheetName, "UTF-8");
            //设置响应头信息 Content-disposition
            response.setHeader("Content-disposition", "attachment;filename=" + outFileName + ".xlsx");

            //处理头部
            List<List<String>> head =getExcelHeaderList(headMap);
             //处理表数据
            List<List<Object>> data=getExcelDataList(headMap,dataList);
            //处理下拉内容
            Map<Integer, List<String>> dropdownOptionsMap=getDropdownOptionsMap(headMap);

            RowFormatSetTextHandler rowFormatSetTextHandler = new RowFormatSetTextHandler();
            rowFormatSetTextHandler.setDropdownOptionsMap(dropdownOptionsMap);
            EasyExcel.write(response.getOutputStream())
                    .registerWriteHandler(rowFormatSetTextHandler)
                    .registerWriteHandler(new CustomSheetWriteHandler())// 初始化单元格为文本格式
                    .registerWriteHandler(new CustomColumnWidthStyleStrategy())// 单元格宽度
                    .registerWriteHandler(CellStyleUtils.getCellStyleStrategy())// 单元格样式
                    .head(head)
                    .sheet(sheetName).doWrite(data);
        } catch (Exception e) {
            throw new JeeLowCodeException(e.getMessage());
        }
    }

    /**
     * 处理下拉列表
     * @param headMap
     * @return
     */
    private static Map<Integer, List<String>> getDropdownOptionsMap(LinkedMap<String, ExcelTitleModel> headMap){
        Map<Integer, List<String>> dropdownOptionsMap=new HashMap<>();

        int index = 0; // 初始化序号变量
        Iterator<Map.Entry<String, ExcelTitleModel>> iterator = headMap.entrySet().iterator();
        while (iterator.hasNext()) {

            Map.Entry<String, ExcelTitleModel> entry = iterator.next();
            ExcelTitleModel model = entry.getValue();
            List<String> dropdownOptionList = model.getDropdownOptionList();
            if(FuncBase.isNotEmpty(dropdownOptionList)){
                dropdownOptionsMap.put(index,dropdownOptionList);
            }
            ++index;
        }
        return dropdownOptionsMap;
    }

}