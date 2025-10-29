
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

    public void setResultModel(ResultDataModel resultDataModel){
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


    public void  setParamMore(Long dbFormId, Map<String, Object> params, List<Map<String, Object>> list, Long dataId, Page page){
        this.param = new EnhanceParam(dbFormId,params,list,dataId,page);
    }
}

