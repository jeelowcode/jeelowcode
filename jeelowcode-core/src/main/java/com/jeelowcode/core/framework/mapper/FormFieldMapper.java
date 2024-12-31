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

import com.jeelowcode.core.framework.entity.FormFieldEntity;
import com.jeelowcode.framework.utils.annotation.JeelowCodeCache;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jeelowcode.framework.utils.enums.YNEnum;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public interface FormFieldMapper extends BaseMapper<FormFieldEntity> {

    @JeelowCodeCache(cacheNames = "'FormFieldMapper:getByDbFormId:' + #dbFormId", reflexClass = FormFieldEntity.class)
    default List<FormFieldEntity> getByDbFormId(Long dbFormId){
        LambdaQueryWrapper<FormFieldEntity> formWrapper = new LambdaQueryWrapper<>();
        formWrapper.eq(FormFieldEntity::getDbformId, dbFormId);
        formWrapper.orderByAsc(FormFieldEntity::getSortNum);
        List<FormFieldEntity> formFieldList = this.selectList(formWrapper);
        return formFieldList;
    }

    default List<FormFieldEntity> getAllList(){
        LambdaQueryWrapper<FormFieldEntity> fieldWrapper = new LambdaQueryWrapper<>();
        fieldWrapper.select(FormFieldEntity::getDbformId, FormFieldEntity::getFieldCode, FormFieldEntity::getFieldName,FormFieldEntity::getFieldType);
        List<FormFieldEntity> fieldList = this.selectList(fieldWrapper);
        return fieldList;
    }


    //key =名称  code =编码
    default Map<String, String> getFieldNameAndCodeMap(Long dbFormId){
        LambdaQueryWrapper<FormFieldEntity> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(FormFieldEntity::getDbformId, dbFormId);
        wrapper.select(FormFieldEntity::getFieldCode, FormFieldEntity::getFieldName);
        List<FormFieldEntity> dataList = this.selectList(wrapper);

        Map<String, String> resultMap = dataList.stream()
                .collect(Collectors.toMap(
                        entity -> entity.getFieldName(),
                        entity -> entity.getFieldCode(),
                        (existingValue, newValue) -> existingValue
                ));
        return resultMap;
    }

    default List<FormFieldEntity> getByDbFormIdAndFieldCode(Long dbFormId,String fieldCode){
        LambdaQueryWrapper<FormFieldEntity> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(FormFieldEntity::getDbformId, dbFormId);
        wrapper.eq(FormFieldEntity::getFieldCode, fieldCode);
        wrapper.select(FormFieldEntity::getFieldType);
        List<FormFieldEntity> dataList = this.selectList(wrapper);
        return dataList;
    }



    //获取数据库字段列表
    default List<FormFieldEntity> getDbFieldList(Long dbFormId) {
        LambdaQueryWrapper<FormFieldEntity> formWrapper = new LambdaQueryWrapper<>();
        formWrapper.eq(FormFieldEntity::getDbformId, dbFormId);
        formWrapper.eq(FormFieldEntity::getIsDb, YNEnum.Y.getCode());
        formWrapper.orderByAsc(FormFieldEntity::getSortNum);
        List<FormFieldEntity> formFieldList = this.selectList(formWrapper);
        return formFieldList;
    }

    //获取数据库字段的 fieldcode
    default List<String> getDbFieldCodeStrList(Long dbFormId) {
        LambdaQueryWrapper<FormFieldEntity> formWrapper = new LambdaQueryWrapper<>();
        formWrapper.eq(FormFieldEntity::getDbformId, dbFormId);
        formWrapper.eq(FormFieldEntity::getIsDb, YNEnum.Y.getCode());
        formWrapper.orderByAsc(FormFieldEntity::getSortNum);
        formWrapper.select(FormFieldEntity::getFieldCode);
        List<FormFieldEntity> dbFieldList = this.selectList(formWrapper);

        List<String> selectList = dbFieldList.stream()
                .map(FormFieldEntity::getFieldCode)
                .collect(Collectors.toList());

        return selectList;
    }


    default void delByFormId(Long dbFormId){
        QueryWrapper<FormFieldEntity> fieldWrapper = new QueryWrapper();
        fieldWrapper.eq("dbform_id", dbFormId);
        this.delete(fieldWrapper);
    }

}
