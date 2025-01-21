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
package com.jeelowcode.core.framework.mapper;

import com.jeelowcode.core.framework.entity.FormFieldDictEntity;
import com.jeelowcode.framework.utils.annotation.JeelowCodeCache;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;


public interface FormFieldDictMapper extends BaseMapper<FormFieldDictEntity> {

    default void delByDbformId(Long dbFormId){
        QueryWrapper<FormFieldDictEntity> dictWrapper = new QueryWrapper();
        dictWrapper.eq("dbform_id", dbFormId);
        this.delete(dictWrapper);
    }

    default void delByDbformIdAndCode(Long dbFormId,String fieldCode){
        QueryWrapper<FormFieldDictEntity> dictWrapper = new QueryWrapper();
        dictWrapper.eq("dbform_id", dbFormId);
        dictWrapper.eq("field_code", fieldCode);
        this.delete(dictWrapper);
    }



    @JeelowCodeCache(cacheNames = "'FormFieldDictMapper:getByDbFormId:' + #dbformId", reflexClass = FormFieldDictEntity.class,nullIsSave = true)
    default List<FormFieldDictEntity> getByDbFormId(Long dbformId){
        LambdaQueryWrapper<FormFieldDictEntity> dictWrapper = new LambdaQueryWrapper<>();
        dictWrapper.eq(FormFieldDictEntity::getDbformId, dbformId);
        dictWrapper.orderByAsc(FormFieldDictEntity::getId);
        return this.selectList(dictWrapper);
    }

    default FormFieldDictEntity getFormFieldSysDictEntity(Long dbformId,String fieldCode){

        //获取字典表信息
        LambdaQueryWrapper<FormFieldDictEntity> wrapper = new LambdaQueryWrapper<>();
        wrapper.select(FormFieldDictEntity::getDictType, FormFieldDictEntity::getDictCode, FormFieldDictEntity::getDictTable, FormFieldDictEntity::getDictText, FormFieldDictEntity::getDictTableColumn,FormFieldDictEntity::getDictTextFormatter);
        wrapper.eq(FormFieldDictEntity::getDbformId, dbformId);
        wrapper.eq(FormFieldDictEntity::getFieldCode, fieldCode);
        wrapper.isNotNull(FormFieldDictEntity::getDictCode);

        FormFieldDictEntity dictEntity = this.selectOne(wrapper);
        return dictEntity;
    }

}
