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

import com.jeelowcode.core.framework.config.aspect.enhance.JeeLowCodeAnnoaionAspectjSQL;
import com.jeelowcode.core.framework.entity.EnhanceSqlEntity;
import com.jeelowcode.core.framework.entity.HistoryDbformEntity;
import com.jeelowcode.core.framework.mapper.EnhanceSqlMapper;
import com.jeelowcode.core.framework.mapper.HistoryDbFormMapper;
import com.jeelowcode.core.framework.service.IEnhanceSqlService;
import com.jeelowcode.core.framework.utils.Func;
import com.jeelowcode.framework.utils.adapter.IJeeLowCodeAdapter;
import com.jeelowcode.framework.utils.enums.YNEnum;
import com.jeelowcode.framework.utils.utils.FuncBase;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


/**
 * @author JX
 * @create 2024-03-06 9:55
 * @dedescription:
 */

@Service
public class EnhanceSqlServiceImpl extends ServiceImpl<EnhanceSqlMapper, EnhanceSqlEntity> implements IEnhanceSqlService {

    @Autowired
    private HistoryDbFormMapper historyDbFormMapper;

    @Autowired
    private IJeeLowCodeAdapter jeeLowCodeAdapter;

    //新增sql增强
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void saveEnhanceSql(EnhanceSqlEntity enhanceSqlEntity){
        long id = IdWorker.getId();
        enhanceSqlEntity.setId(id);
        this.save(enhanceSqlEntity);
        //加入插件管理
        JeeLowCodeAnnoaionAspectjSQL.addPlugin(enhanceSqlEntity);
        //加历史记录
        this.saveHistoryLog(id,enhanceSqlEntity);
    }

    //编辑sql增强
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void updateEnhanceSql(EnhanceSqlEntity enhanceSqlEntity){
        this.updateById(enhanceSqlEntity);
        if (Func.equals(enhanceSqlEntity.getActiveStatus(), YNEnum.Y.getCode())){
            JeeLowCodeAnnoaionAspectjSQL.updatePlugin(enhanceSqlEntity);
        } else {
            JeeLowCodeAnnoaionAspectjSQL.removePlugin(enhanceSqlEntity);
        }
        //加历史记录
        this.saveHistoryLog(enhanceSqlEntity.getId(),enhanceSqlEntity);
    }

    @Override
    public void copy(Long sourceFormIdId,Long targetFormIdId){
        LambdaQueryWrapper<EnhanceSqlEntity> sqlWrapper=new LambdaQueryWrapper<>();
        sqlWrapper.eq(EnhanceSqlEntity::getDbformId,sourceFormIdId);
        sqlWrapper.orderByAsc(EnhanceSqlEntity::getId);
        List<EnhanceSqlEntity> sqlEntityList = this.list(sqlWrapper);
        //清空默认值并修改dbformId值
        sqlEntityList.stream().forEach(sqlEntity->{
            sqlEntity.setDbformId(targetFormIdId);
            sqlEntity.setId(null);
            sqlEntity.setTenantId(null);
            sqlEntity.setCreateDept(null);
            sqlEntity.setCreateTime(null);
            sqlEntity.setCreateUser(null);
            sqlEntity.setUpdateTime(null);
            sqlEntity.setUpdateUser(null);
            sqlEntity.setIsDeleted(null);
        });
        this.saveBatch(sqlEntityList);
    }


    //保存历史记录
    private void saveHistoryLog(Long jsId, EnhanceSqlEntity enhanceSqlEntity){

        HistoryDbformEntity entity=new HistoryDbformEntity();
        entity.setId(IdWorker.getId());
        entity.setTenantId(jeeLowCodeAdapter.getTenantId());
        entity.setCreateUser(jeeLowCodeAdapter.getOnlineUserId());
        entity.setCreateTime(LocalDateTime.now());
        entity.setCreateUserName(jeeLowCodeAdapter.getOnlineUserName());

        entity.setDbformId(enhanceSqlEntity.getDbformId());
        entity.setServiceType("sql");
        entity.setServiceId(jsId);
        entity.setServiceJson(Func.json2Str(enhanceSqlEntity));

        historyDbFormMapper.insert(entity);
    }

    @Override
    public List<EnhanceSqlEntity> getAllSqlEnhance() {
        LambdaQueryWrapper<EnhanceSqlEntity> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(EnhanceSqlEntity::getActiveStatus, YNEnum.Y.getCode());
        wrapper.isNotNull(EnhanceSqlEntity::getExecuteSql);
        wrapper.isNotNull(EnhanceSqlEntity::getDbformId);
        List<EnhanceSqlEntity> enhanceSqlEntities = baseMapper.selectList(wrapper);
        return enhanceSqlEntities;
    }

}
