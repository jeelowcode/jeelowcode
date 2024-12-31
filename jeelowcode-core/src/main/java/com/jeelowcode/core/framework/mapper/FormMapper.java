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

import com.jeelowcode.core.framework.entity.FormEntity;
import com.jeelowcode.core.framework.utils.Func;
import com.jeelowcode.framework.utils.annotation.JeelowCodeCache;
import com.jeelowcode.framework.utils.enums.TableTypeEnum;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;
import java.util.stream.Collectors;


public interface FormMapper extends BaseMapper<FormEntity> {

    @JeelowCodeCache(cacheNames = "'FormMapper:getById:' + #dbFormId", reflexClass = FormEntity.class)
    default FormEntity getById(Long dbFormId) {
        LambdaQueryWrapper<FormEntity> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(FormEntity::getId, dbFormId);
        return this.selectOne(wrapper);
    }

    @JeelowCodeCache(cacheNames = "'FormMapper:getAllSubTableNameList:' + #dbFormId", reflexClass = String.class,nullIsSave = true)
    default List<String> getAllSubTableNameList(Long dbFormId) {
        LambdaQueryWrapper<FormEntity> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(FormEntity::getId, dbFormId);
        wrapper.eq(FormEntity::getTableType, TableTypeEnum.MAIN.getType());//主表
        wrapper.select(FormEntity::getSubTableListStr);
        FormEntity formEntity = this.selectOne(wrapper);
        if (Func.isEmpty(formEntity)) {
            return null;
        }
        String subListStr = formEntity.getSubTableListStr();
        if (Func.isEmpty(subListStr)) {
            return null;
        }
        return Func.toStrList(subListStr);
    }

    default Long getFormIdByTableName(String tableName){
        LambdaQueryWrapper<FormEntity> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(FormEntity::getTableName, tableName);
        wrapper.select(FormEntity::getId);
        FormEntity formEntity = this.selectOne(wrapper);
        return formEntity.getId();
    }
    default FormEntity getByTableName(String tableName){
        LambdaQueryWrapper<FormEntity> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(FormEntity::getTableName, tableName);
        wrapper.select(FormEntity::getId);
        FormEntity formEntity = this.selectOne(wrapper);
        return formEntity;
    }

    default List<Long> getDbFormIdByTableName(List<String> subTableNameList){
        LambdaQueryWrapper<FormEntity> wrapper = new LambdaQueryWrapper<>();
        wrapper.in(FormEntity::getTableName, subTableNameList);
        wrapper.select(FormEntity::getId);
        wrapper.orderByAsc(FormEntity::getSubTableSort, FormEntity::getId);
        List<FormEntity> list = this.selectList(wrapper);

        List<Long> idList = list.stream()
                .map(FormEntity::getId)  // 使用方法引用来简化代码
                .collect(Collectors.toList());// 收集结果到列表

        return idList;
    }


}
