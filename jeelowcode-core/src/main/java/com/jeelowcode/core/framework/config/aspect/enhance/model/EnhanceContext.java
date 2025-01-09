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
package com.jeelowcode.core.framework.config.aspect.enhance.model;

import com.jeelowcode.framework.utils.model.ResultDataModel;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import java.io.*;
import java.util.List;
import java.util.Map;

/**
 * @author JX
 * @create 2024-08-12 9:14
 * @dedescription: 增强执行上下文
 */
public class EnhanceContext extends BaseEnhanceContext implements Serializable{
    //参数
    private EnhanceParam param=new EnhanceParam();
    //结果
    private EnhanceResult result=new EnhanceResult();


    public EnhanceParam getParam() {
        return param;
    }

    public void setParam(EnhanceParam param) {
        this.param = param;
    }

    public EnhanceResult getResult() {
        return result;
    }

    public void setResult(EnhanceResult result) {
        this.result = result;
    }

    public void setResult(ResultDataModel resultDataModel){
        this.result.setExitFlag(resultDataModel.isExitFlag());
        this.result.setRecords(resultDataModel.getRecords());
        this.result.setTotal(resultDataModel.getTotal());
    }

    //通过序列化和反序列化实现深拷贝
    @Override
    public EnhanceContext clone(){
        try {
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(bos);
            oos.writeObject(this);
            ByteArrayInputStream bis = new ByteArrayInputStream(bos.toByteArray());
            ObjectInputStream ois  = new ObjectInputStream(bis);
            return (EnhanceContext) ois.readObject();
        } catch (IOException |ClassNotFoundException e ) {
            e.printStackTrace();
            return null;
        }
    }

    public void setParam(Long dbFormId,Map<String,Object> params,List<Map<String,Object>> list){
        this.param = new EnhanceParam(dbFormId,params,list);
    }

    public void setResult(boolean exitFlag,String id,Long total,List<Map<String,Object>> records){
        this.result = new EnhanceResult(exitFlag,id,total,records);
    }

    public void  setParam(Long dbFormId, Map<String, Object> params, List<Map<String, Object>> list, Long dataId, Page page, List<Long> dataIdList){
        this.param = new EnhanceParam(dbFormId,params,list,dataId,page,dataIdList);
    }
}

