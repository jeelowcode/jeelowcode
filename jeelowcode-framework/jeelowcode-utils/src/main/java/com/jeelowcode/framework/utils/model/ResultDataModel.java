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
package com.jeelowcode.framework.utils.model;

import com.jeelowcode.framework.utils.utils.FuncBase;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class ResultDataModel {
    private boolean exitFlag=false;//是否退出
    private Long total;
    private List<Map<String,Object>> records;//列表



    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }

    public List<Map<String, Object>> getRecords() {
        return records;
    }

    public void setRecords(List<Map<String, Object>> records) {
        this.records = records;
    }

    public boolean isExitFlag() {
        return exitFlag;
    }

    public void setExitFlag(boolean exitFlag) {
        this.exitFlag = exitFlag;
    }


    public ResultDataModel(){
        this.total=0L;
        this.setRecords(new ArrayList<>());
    }

    /**
     * 取一条
     * @param list
     * @return
     */
    public static ResultDataModel fomatOne(List<Map<String, Object>> list){
        if(FuncBase.isEmpty(list)){
            ResultDataModel resultDataModel = new ResultDataModel();
            resultDataModel.setTotal(0L);
            resultDataModel.setRecords(new ArrayList<>());
            return resultDataModel;
        }
        Map<String, Object> tmpMap = list.get(0);
        list.clear();
        list.add(tmpMap);
        ResultDataModel model=new ResultDataModel();
        model.setTotal(FuncBase.toLong(list.size()));
        model.setRecords(list);
        return model;
    }

    /**
     * 只处理数据，不改变总数的情况下
     * @param list
     * @return
     */
    public static ResultDataModel fomatList(List<Map<String, Object>> list){
        if(FuncBase.isEmpty(list)){
            ResultDataModel resultDataModel = new ResultDataModel();
            resultDataModel.setTotal(0L);
            resultDataModel.setRecords(new ArrayList<>());
            return resultDataModel;
        }
        ResultDataModel model=new ResultDataModel();
        model.setRecords(list);
        return model;
    }

    /**
     * 只处理数据，不改变总数的情况下
     * @param dataMap
     * @return
     */
    public static ResultDataModel fomatMap(Map<String, Object> dataMap){
        List<Map<String, Object>> list = Collections.singletonList(dataMap);
        ResultDataModel model=new ResultDataModel();
        model.setRecords(list);
        return model;
    }

    /**
     * 处理数据和改变总数的情况下
     * @param total
     * @param list
     * @return
     */
    public static ResultDataModel fomat(long total,List<Map<String, Object>> list){
        ResultDataModel model=new ResultDataModel();
        model.setTotal(total);
        model.setRecords(list);
        return model;
    }


}
