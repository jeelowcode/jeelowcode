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
package com.jeelowcode.core.framework.service.impl;

import com.jeelowcode.core.framework.entity.FormButtonEntity;
import com.jeelowcode.core.framework.mapper.FormButtonMapper;
import com.jeelowcode.core.framework.service.IFormButtonService;
import com.jeelowcode.framework.utils.annotation.JeelowCodeCache;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * button增强相关
 */

@Service
public class FormButtonServiceImpl extends ServiceImpl<FormButtonMapper, FormButtonEntity> implements IFormButtonService {

    //获取按钮增强列表
    @JeelowCodeCache(cacheNames = "'FormButtonServiceImpl:getButtonList:' + #dbFormId", reflexClass = FormButtonEntity.class,nullIsSave = true)
    public List<FormButtonEntity> getButtonList(Long dbFormId){
        LambdaQueryWrapper<FormButtonEntity> buttonWrapper = new LambdaQueryWrapper<>();
        buttonWrapper.eq(FormButtonEntity::getDbformId, dbFormId);
        List<FormButtonEntity> buttonList = this.list(buttonWrapper);
        return buttonList;
    }


    //复制
    @Override
    public void copy(Long sourceFormIdId,Long targetFormIdId){

        LambdaQueryWrapper<FormButtonEntity> buttonWrapper=new LambdaQueryWrapper<>();
        buttonWrapper.eq(FormButtonEntity::getDbformId,sourceFormIdId);
        buttonWrapper.orderByAsc(FormButtonEntity::getId);
        List<FormButtonEntity> buttonEntityList = this.list(buttonWrapper);
        //清空默认值并修改dbformId值
        buttonEntityList.stream().forEach(buttonEntity->{
            buttonEntity.setDbformId(targetFormIdId);
            buttonEntity.setId(null);
            buttonEntity.setTenantId(null);
            buttonEntity.setCreateDept(null);
            buttonEntity.setCreateTime(null);
            buttonEntity.setCreateUser(null);
            buttonEntity.setUpdateTime(null);
            buttonEntity.setUpdateUser(null);
            buttonEntity.setIsDeleted(null);
        });
        this.saveBatch(buttonEntityList);
    }

}
