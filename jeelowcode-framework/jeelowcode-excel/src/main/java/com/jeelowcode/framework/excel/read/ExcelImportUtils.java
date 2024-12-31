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
package com.jeelowcode.framework.excel.read;


import cn.hutool.core.util.IdUtil;
import com.jeelowcode.framework.excel.listener.JeeLowCodeExcelListener;
import com.jeelowcode.framework.exception.JeeLowCodeException;
import com.jeelowcode.framework.utils.constant.JeeLowCodeConstant;
import com.jeelowcode.framework.utils.utils.FuncBase;
import com.alibaba.excel.EasyExcelFactory;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 处理导入数据
 */
public class ExcelImportUtils {


    /**
     * 动态表头导入功能
     *
     * @param inputStream 文件
     * @return
     */
    public static List<Map<String, Object>> importExcel(InputStream inputStream,Integer headerCou, Map<String, String> fieldNameAndCodeMap) {
        try {
            Integer sheet=0;;

            // 首先校验传入文件是否为空
            if (inputStream == null) {
               throw new JeeLowCodeException("文件源为空");
            }
            // 引入监听器（此处需注意，监听器不可被Spring管理）
            JeeLowCodeExcelListener readListener = new JeeLowCodeExcelListener();
            // 开始处理excel
            EasyExcelFactory.read(inputStream, readListener)
                    .sheet(sheet)
                    .headRowNumber(headerCou)
                    .doRead();
            // 获取表头（验空）
            List<Map<Integer, String>> headList = readListener.getHeadList();
            if (FuncBase.isEmpty(headList)) {
                throw new JeeLowCodeException("Excel表头不能为空");
            }
            //获取头部,取最后一次解析的列头数据
            Map<Integer, String> excelHeadIdxNameMap = headList.get(headList.size() - 1);

            // 获取表数据(验空)
            List<Map<Integer, String>> dataList = readListener.getDataList();
            if (FuncBase.isEmpty(dataList)) {
                throw new JeeLowCodeException("Excel数据内容不能为空");
            }

            //封装数据体
            AtomicInteger atomicInteger= new AtomicInteger(1);
            List<Map<String, Object>> excelDataList = new ArrayList<Map<String, Object>>();
            for (Map<Integer, String> dataRow : dataList) {
                HashMap<String, Object> rowData = new HashMap<>();
                int step =atomicInteger.getAndIncrement();
                rowData.put(JeeLowCodeConstant.EXCEL_IMPORT_STEP,step);//处理序号
                rowData.put(JeeLowCodeConstant.EXCEL_IMPORT_ID,IdUtil.getSnowflakeNextId());//id


                excelHeadIdxNameMap.entrySet().forEach(columnHead -> {
                    String value = columnHead.getValue();
                    String fieldCode = fieldNameAndCodeMap.get(value);
                    if(FuncBase.isNotEmpty(fieldCode)){
                        rowData.put(fieldCode, dataRow.get(columnHead.getKey()));
                    }
                });
                if(FuncBase.isEmpty(rowData)){
                    continue;
                }
                excelDataList.add(rowData);
            }

            return excelDataList;
        } catch (Exception e) {
            e.printStackTrace();
            throw new JeeLowCodeException(e.getMessage());
        }

    }

}