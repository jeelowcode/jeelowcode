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

import com.jeelowcode.core.framework.entity.FormSummaryEntity;
import com.jeelowcode.core.framework.utils.Func;
import com.jeelowcode.framework.utils.annotation.JeelowCodeCache;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jeelowcode.framework.utils.enums.YNEnum;

import java.util.List;

//统计配置
public interface FormSummaryMapper extends BaseMapper<FormSummaryEntity> {

    default void delByDbFormId(Long dbFormId){
        QueryWrapper<FormSummaryEntity> summaryWrapper = new QueryWrapper();
        summaryWrapper.eq("dbform_id", dbFormId);
        this.delete(summaryWrapper);
    }

    default List<FormSummaryEntity>  getByFormId(Long dbFormId){
        LambdaQueryWrapper<FormSummaryEntity> summaryWrapper = new LambdaQueryWrapper<>();
        summaryWrapper.eq(FormSummaryEntity::getDbformId, dbFormId);
        summaryWrapper.orderByAsc(FormSummaryEntity::getId);
        return this.selectList(summaryWrapper);
    }

    @JeelowCodeCache(cacheNames = "'FormSummaryMapper:getTopSummaryList:' + #dbFormId", reflexClass = FormSummaryEntity.class,nullIsSave = true)
    default List<FormSummaryEntity>  getTopSummaryList(Long dbFormId){
        LambdaQueryWrapper<FormSummaryEntity> topWrapper = new LambdaQueryWrapper<>();
        topWrapper.eq(FormSummaryEntity::getDbformId, dbFormId);
        topWrapper.eq(FormSummaryEntity::getSummaryType, "top");//顶部
        return this.selectList(topWrapper);
    }

    @JeelowCodeCache(cacheNames = "'FormSummaryMapper:getBottomSummaryList:' + #dbFormId", reflexClass = FormSummaryEntity.class,nullIsSave = true)
    default List<FormSummaryEntity>  getBottomSummaryList(Long dbFormId){
        LambdaQueryWrapper<FormSummaryEntity> bottomWrapper = new LambdaQueryWrapper<>();
        bottomWrapper.eq(FormSummaryEntity::getDbformId, dbFormId);
        bottomWrapper.eq(FormSummaryEntity::getSummaryType, "bottom");//顶部
        return this.selectList(bottomWrapper);
    }
    default List<FormSummaryEntity>  getSummaryList(Long dbFormId,String summaryType){
        LambdaQueryWrapper<FormSummaryEntity> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(FormSummaryEntity::getDbformId, dbFormId);
        wrapper.eq(FormSummaryEntity::getSummaryShow, YNEnum.Y.getCode());
        wrapper.eq(Func.isNotEmpty(summaryType), FormSummaryEntity::getSummaryType, summaryType);
        return this.selectList(wrapper);
    }


}
