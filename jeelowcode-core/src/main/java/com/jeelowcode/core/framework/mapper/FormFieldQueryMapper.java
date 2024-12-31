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

import com.jeelowcode.core.framework.entity.FormFieldQueryEntity;
import com.jeelowcode.framework.utils.annotation.JeelowCodeCache;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;


public interface FormFieldQueryMapper extends BaseMapper<FormFieldQueryEntity> {

    default void delByDbformId(Long dbFormId){
        QueryWrapper<FormFieldQueryEntity> queryWrapper = new QueryWrapper();
        queryWrapper.eq("dbform_id", dbFormId);
        this.delete(queryWrapper);
    }

    @JeelowCodeCache(cacheNames = "'FormFieldQueryMapper:getByFormId:' + #dbFormId", reflexClass = FormFieldQueryEntity.class)
    default List<FormFieldQueryEntity> getByFormId(Long dbFormId){
        LambdaQueryWrapper<FormFieldQueryEntity> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(FormFieldQueryEntity::getDbformId, dbFormId);
        queryWrapper.orderByAsc(FormFieldQueryEntity::getId);
        return this.selectList(queryWrapper);
    }
}
