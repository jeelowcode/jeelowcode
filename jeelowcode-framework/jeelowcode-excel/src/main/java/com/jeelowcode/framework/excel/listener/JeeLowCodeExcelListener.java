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
package com.jeelowcode.framework.excel.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 自定义监听器
 */
public class JeeLowCodeExcelListener extends AnalysisEventListener<Map<Integer, String>> {
    //表头数据（存储所有的表头数据）
    private List<Map<Integer, String>> headList = new ArrayList<>();
    //数据体
    private List<Map<Integer, String>> dataList = new ArrayList<>();


    @Override//这里会一行行的返回头
    public void invokeHeadMap(Map<Integer, String> headMap, AnalysisContext context) {
        //存储全部表头数据
        headList.add(headMap);
    }

    @Override// 处理每一行数据
    public void invoke(Map<Integer, String> data, AnalysisContext context) {
        dataList.add(data);
    }

    @Override// 全部处理结束执行
    public void doAfterAllAnalysed(AnalysisContext context) {
    }

    public List<Map<Integer, String>> getHeadList() {
        return headList;
    }

    public List<Map<Integer, String>> getDataList() {
        return dataList;
    }
}