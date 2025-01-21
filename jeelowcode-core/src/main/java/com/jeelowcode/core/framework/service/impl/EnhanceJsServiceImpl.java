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

import com.jeelowcode.core.framework.entity.EnhanceJsEntity;
import com.jeelowcode.core.framework.entity.HistoryDbformEntity;
import com.jeelowcode.core.framework.mapper.EnhanceJsMapper;
import com.jeelowcode.core.framework.mapper.HistoryDbFormMapper;
import com.jeelowcode.core.framework.service.IEnhanceJsService;
import com.jeelowcode.core.framework.utils.Func;
import com.jeelowcode.framework.utils.adapter.IJeeLowCodeAdapter;
import com.jeelowcode.framework.utils.annotation.JeelowCodeCache;
import com.jeelowcode.framework.utils.utils.FuncBase;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;


/**
 * @author JX
 * @create 2024-03-06 9:55
 * @dedescription:
 */

@Service
public class EnhanceJsServiceImpl extends ServiceImpl<EnhanceJsMapper, EnhanceJsEntity> implements IEnhanceJsService {

    @Autowired
    private IJeeLowCodeAdapter jeeLowCodeAdapter;

    @Autowired
    private HistoryDbFormMapper historyDbFormMapper;

    @JeelowCodeCache(cacheNames = "'EnhanceJsServiceImpl:getJsList:' + #dbFormId", reflexClass = EnhanceJsEntity.class,nullIsSave = true)
    public List<EnhanceJsEntity> getJsList(Long dbFormId){
        LambdaQueryWrapper<EnhanceJsEntity> jsWrapper = new LambdaQueryWrapper<>();
        jsWrapper.eq(EnhanceJsEntity::getDbformId, dbFormId);
        List<EnhanceJsEntity> jsList = this.list(jsWrapper);
        return jsList;
    }

    /**
     * JS增强-详情
     *
     * @param dbformId
     * @param type
     * @return
     */
    @Override
    public EnhanceJsEntity getDbFormEnhanceJsDetail(Long dbformId, String type) {
        LambdaQueryWrapper<EnhanceJsEntity> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(EnhanceJsEntity::getDbformId, dbformId);
        wrapper.eq(EnhanceJsEntity::getJsType, type);
        wrapper.orderByDesc(EnhanceJsEntity::getId);
        List<EnhanceJsEntity> enhanceJsList = baseMapper.selectList(wrapper);
        if(Func.isEmpty(enhanceJsList)){
            return null;
        }
        return enhanceJsList.get(0);
    }

    //新增js增强
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void saveEnhanceJs(EnhanceJsEntity enhanceJsEntity){
        long id = IdWorker.getId();
        enhanceJsEntity.setId(id);
        this.save(enhanceJsEntity);

        //加历史记录
        this.saveHistoryLog(id,enhanceJsEntity);
    }


    //编辑js增强
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void updateEnhanceJs(EnhanceJsEntity enhanceJsEntity){
        this.updateById(enhanceJsEntity);
        //加历史记录
        this.saveHistoryLog(enhanceJsEntity.getId(),enhanceJsEntity);
    }


    @Override
    public void copy(Long sourceFormIdId,Long targetFormIdId){
        LambdaQueryWrapper<EnhanceJsEntity> jsWrapper=new LambdaQueryWrapper<>();
        jsWrapper.eq(EnhanceJsEntity::getDbformId,sourceFormIdId);
        jsWrapper.orderByAsc(EnhanceJsEntity::getId);
        List<EnhanceJsEntity> jsEntityList = this.list(jsWrapper);
        //清空默认值并修改dbformId值
        jsEntityList.stream().forEach(jsEntity->{
            jsEntity.setDbformId(targetFormIdId);
            jsEntity.setId(null);
            jsEntity.setTenantId(null);
            jsEntity.setCreateDept(null);
            jsEntity.setCreateTime(null);
            jsEntity.setCreateUser(null);
            jsEntity.setUpdateTime(null);
            jsEntity.setUpdateUser(null);
            jsEntity.setIsDeleted(null);
        });
        this.saveBatch(jsEntityList);
    }

    //保存历史记录
    private void saveHistoryLog(Long jsId, EnhanceJsEntity enhanceJsEntity){

        HistoryDbformEntity entity=new HistoryDbformEntity();
        entity.setId(IdWorker.getId());
        entity.setTenantId(jeeLowCodeAdapter.getTenantId());
        entity.setCreateUser(jeeLowCodeAdapter.getOnlineUserId());
        entity.setCreateTime(LocalDateTime.now());
        entity.setCreateUserName(jeeLowCodeAdapter.getOnlineUserName());

        entity.setDbformId(enhanceJsEntity.getDbformId());
        entity.setServiceType("js");
        entity.setServiceId(jsId);
        entity.setServiceJson(Func.json2Str(enhanceJsEntity));

        historyDbFormMapper.insert(entity);
    }
}
