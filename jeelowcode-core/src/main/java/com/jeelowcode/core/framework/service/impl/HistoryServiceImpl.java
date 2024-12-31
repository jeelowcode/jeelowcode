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


import com.jeelowcode.core.framework.entity.*;
import com.jeelowcode.core.framework.mapper.HistoryDbFormMapper;
import com.jeelowcode.core.framework.mapper.HistoryDesFormMapper;
import com.jeelowcode.core.framework.params.vo.history.HistoryJavaVo;
import com.jeelowcode.core.framework.params.vo.history.HistoryJsVo;
import com.jeelowcode.core.framework.params.vo.history.HistorySqlVo;
import com.jeelowcode.core.framework.service.IHistoryService;
import com.jeelowcode.core.framework.utils.Func;
import com.jeelowcode.framework.utils.utils.FuncBase;
import com.jeelowcode.framework.utils.utils.JeeLowCodeUtils;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jeelowcode.core.framework.entity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 历史
 */
@Service
public class HistoryServiceImpl implements IHistoryService {

    @Autowired
    private HistoryDesFormMapper historyDesFormMapper;

    @Autowired
    private HistoryDbFormMapper historyDbFormMapper;

    //获取表单设计历史记录
    @Override
    public IPage<HistoryDesformEntity> getDesFormPages(Long desformId, Page page) {

        //剔除最后一条
        Long maxId = null;
        long current = page.getCurrent();
        if (current == 1) {//第一页，需要剔除第一条
            QueryWrapper<HistoryDesformEntity> queryWrapper = new QueryWrapper<>();
            queryWrapper.select("MAX(id) as maxId");
            queryWrapper.eq("desform_id", desformId);
            Map<String, Object> resultMap = historyDesFormMapper.selectMaps(queryWrapper).get(0);
            if (FuncBase.isNotEmpty(resultMap)) {
                maxId = JeeLowCodeUtils.getMap2Long(resultMap, "maxId");
            }
        }

        LambdaQueryWrapper<HistoryDesformEntity> wrapper = new LambdaQueryWrapper<HistoryDesformEntity>();
        wrapper.eq(HistoryDesformEntity::getDesformId, desformId);
        wrapper.ne(FuncBase.isNotEmpty(maxId), HistoryDesformEntity::getId, maxId);
        wrapper.orderByDesc(HistoryDesformEntity::getId);
        wrapper.select(HistoryDesformEntity::getId,
                HistoryDesformEntity::getDesformName,
                HistoryDesformEntity::getGroupDesformId,
                HistoryDesformEntity::getCreateUserName,
                HistoryDesformEntity::getCreateTime);
        Page pages = historyDesFormMapper.selectPage(page, wrapper);
        return pages;
    }

    //获取表单设计历史记录详情
    @Override
    public HistoryDesformEntity getDesFormInfo(Long historyId) {
        return historyDesFormMapper.selectById(historyId);
    }


    //获取历史记录-JS增强
    @Override
    public IPage<HistoryJsVo> getHistoryJsPages(Long enhanceJsId, Page page) {

        //剔除最后一条
        Long maxId = null;
        long current = page.getCurrent();
        if (current == 1) {//第一页，需要剔除第一条
            QueryWrapper<HistoryDbformEntity> queryWrapper = new QueryWrapper<>();
            queryWrapper.select("MAX(id) as maxId");
            queryWrapper.eq("service_type", "js");
            queryWrapper.eq("service_id", enhanceJsId);
            Map<String, Object> resultMap = historyDbFormMapper.selectMaps(queryWrapper).get(0);
            if (FuncBase.isNotEmpty(resultMap)) {
                maxId = JeeLowCodeUtils.getMap2Long(resultMap, "maxId");
            }
        }

        LambdaQueryWrapper<HistoryDbformEntity> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(HistoryDbformEntity::getServiceId, enhanceJsId);
        wrapper.eq(HistoryDbformEntity::getServiceType, "js");
        wrapper.ne(FuncBase.isNotEmpty(maxId), HistoryDbformEntity::getId, maxId);
        wrapper.orderByDesc(HistoryDbformEntity::getId);
        wrapper.select(HistoryDbformEntity::getId,
                HistoryDbformEntity::getServiceId,
                HistoryDbformEntity::getServiceJson,
                HistoryDbformEntity::getCreateTime,
                HistoryDbformEntity::getCreateUserName);
        Page pages = historyDbFormMapper.selectPage(page, wrapper);

        List<HistoryJsVo> voList = new ArrayList<>();
        List<HistoryDbformEntity> records = pages.getRecords();
        for (HistoryDbformEntity entity : records) {
            String serviceJson = entity.getServiceJson();
            EnhanceJsEntity jsEntity = Func.json2Bean(serviceJson, EnhanceJsEntity.class);

            HistoryJsVo vo = new HistoryJsVo();
            vo.setId(entity.getId());
            vo.setEnhanceJsId(entity.getServiceId());
            vo.setCreateTime(entity.getCreateTime());
            vo.setCreateUserName(entity.getCreateUserName());
            vo.setJsType(jsEntity.getJsType());
            vo.setJsJson(jsEntity.getJsJson());
            voList.add(vo);
        }
        pages.setRecords(voList);

        return pages;
    }

    @Override
    public HistoryJsVo getHistoryJsInfo(Long historyId) {
        HistoryDbformEntity entity = historyDbFormMapper.selectById(historyId);

        String serviceJson = entity.getServiceJson();
        EnhanceJsEntity jsEntity = Func.json2Bean(serviceJson, EnhanceJsEntity.class);

        HistoryJsVo vo = new HistoryJsVo();
        vo.setId(entity.getId());
        vo.setEnhanceJsId(entity.getServiceId());
        vo.setCreateTime(entity.getCreateTime());
        vo.setCreateUserName(entity.getCreateUserName());
        vo.setJsType(jsEntity.getJsType());
        vo.setJsJson(jsEntity.getJsJson());
        return vo;
    }


    //获取历史记录-Sql增强
    @Override
    public IPage<HistorySqlVo> getHistorySqlPages(Long enhanceSqlId, Page page) {
        //剔除最后一条
        Long maxId = null;
        long current = page.getCurrent();
        if (current == 1) {//第一页，需要剔除第一条
            QueryWrapper<HistoryDbformEntity> queryWrapper = new QueryWrapper<>();
            queryWrapper.select("MAX(id) as maxId");
            queryWrapper.eq("service_type", "sql");
            queryWrapper.eq("service_id", enhanceSqlId);
            Map<String, Object> resultMap = historyDbFormMapper.selectMaps(queryWrapper).get(0);
            if (FuncBase.isNotEmpty(resultMap)) {
                maxId = JeeLowCodeUtils.getMap2Long(resultMap, "maxId");
            }
        }

        LambdaQueryWrapper<HistoryDbformEntity> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(HistoryDbformEntity::getServiceId, enhanceSqlId);
        wrapper.eq(HistoryDbformEntity::getServiceType, "sql");
        wrapper.ne(FuncBase.isNotEmpty(maxId), HistoryDbformEntity::getId, maxId);
        wrapper.orderByDesc(HistoryDbformEntity::getId);
        wrapper.select(HistoryDbformEntity::getId,
                HistoryDbformEntity::getServiceId,
                HistoryDbformEntity::getServiceJson,
                HistoryDbformEntity::getCreateTime,
                HistoryDbformEntity::getCreateUserName);
        Page pages = historyDbFormMapper.selectPage(page, wrapper);

        List<HistorySqlVo> voList = new ArrayList<>();
        List<HistoryDbformEntity> records = pages.getRecords();
        for (HistoryDbformEntity entity : records) {
            String serviceJson = entity.getServiceJson();
            EnhanceSqlEntity sqlEntity = Func.json2Bean(serviceJson, EnhanceSqlEntity.class);

            HistorySqlVo vo = new HistorySqlVo();
            vo.setId(entity.getId());
            vo.setServiceId(entity.getServiceId());
            vo.setCreateTime(entity.getCreateTime());
            vo.setCreateUserName(entity.getCreateUserName());
            vo.setSqlEntity(sqlEntity);

            voList.add(vo);
        }
        pages.setRecords(voList);

        return pages;
    }

    //获取记录详情-Sql增强
    @Override
    public HistorySqlVo getHistorySqlInfo(Long historyId) {
        HistoryDbformEntity entity = historyDbFormMapper.selectById(historyId);

        String serviceJson = entity.getServiceJson();
        EnhanceSqlEntity sqlEntity = Func.json2Bean(serviceJson, EnhanceSqlEntity.class);

        HistorySqlVo vo = new HistorySqlVo();
        vo.setId(entity.getId());
        vo.setServiceId(entity.getServiceId());
        vo.setCreateTime(entity.getCreateTime());
        vo.setCreateUserName(entity.getCreateUserName());
        vo.setSqlEntity(sqlEntity);
        return vo;
    }


    //获取历史记录-JAVA增强
    @Override
    public IPage<HistoryJavaVo> getHistoryJavaPages(Long enhanceJavaId, Page page) {
        //剔除最后一条
        Long maxId = null;
        long current = page.getCurrent();
        if (current == 1) {//第一页，需要剔除第一条
            QueryWrapper<HistoryDbformEntity> queryWrapper = new QueryWrapper<>();
            queryWrapper.select("MAX(id) as maxId");
            queryWrapper.eq("service_type", "java");
            queryWrapper.eq("service_id", enhanceJavaId);
            Map<String, Object> resultMap = historyDbFormMapper.selectMaps(queryWrapper).get(0);
            if (FuncBase.isNotEmpty(resultMap)) {
                maxId = JeeLowCodeUtils.getMap2Long(resultMap, "maxId");
            }
        }

        LambdaQueryWrapper<HistoryDbformEntity> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(HistoryDbformEntity::getServiceId, enhanceJavaId);
        wrapper.eq(HistoryDbformEntity::getServiceType, "java");
        wrapper.ne(FuncBase.isNotEmpty(maxId), HistoryDbformEntity::getId, maxId);
        wrapper.orderByDesc(HistoryDbformEntity::getId);
        wrapper.select(HistoryDbformEntity::getId,
                HistoryDbformEntity::getServiceId,
                HistoryDbformEntity::getServiceJson,
                HistoryDbformEntity::getCreateTime,
                HistoryDbformEntity::getCreateUserName);
        Page pages = historyDbFormMapper.selectPage(page, wrapper);

        List<HistoryJavaVo> voList = new ArrayList<>();
        List<HistoryDbformEntity> records = pages.getRecords();
        for (HistoryDbformEntity entity : records) {
            String serviceJson = entity.getServiceJson();
            EnhanceJavaEntity javaEntity = Func.json2Bean(serviceJson, EnhanceJavaEntity.class);

            HistoryJavaVo vo = new HistoryJavaVo();
            vo.setId(entity.getId());
            vo.setServiceId(entity.getServiceId());
            vo.setCreateTime(entity.getCreateTime());
            vo.setCreateUserName(entity.getCreateUserName());
            vo.setJavaEntity(javaEntity);

            voList.add(vo);
        }
        pages.setRecords(voList);

        return pages;
    }

    //获取记录详情-JAVA增强
    @Override
    public HistoryJavaVo getHistoryJavaInfo(Long historyId) {
        HistoryDbformEntity entity = historyDbFormMapper.selectById(historyId);

        String serviceJson = entity.getServiceJson();
        EnhanceJavaEntity javaEntity = Func.json2Bean(serviceJson, EnhanceJavaEntity.class);

        HistoryJavaVo vo = new HistoryJavaVo();
        vo.setId(entity.getId());
        vo.setServiceId(entity.getServiceId());
        vo.setCreateTime(entity.getCreateTime());
        vo.setCreateUserName(entity.getCreateUserName());
        vo.setJavaEntity(javaEntity);
        return vo;
    }
}
