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

import com.jeelowcode.core.framework.entity.DesformEntity;
import com.jeelowcode.core.framework.entity.HistoryDesformEntity;
import com.jeelowcode.core.framework.mapper.DesFormMapper;
import com.jeelowcode.core.framework.mapper.HistoryDesFormMapper;
import com.jeelowcode.core.framework.params.DesFormAddOrUpdateParam;
import com.jeelowcode.core.framework.service.IDesFormService;
import com.jeelowcode.framework.utils.adapter.IJeeLowCodeAdapter;
import com.jeelowcode.framework.utils.utils.FuncBase;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;


/**
 * 表单设计器相关
 */
@Service
public class DesFormServiceImpl extends ServiceImpl<DesFormMapper, DesformEntity> implements IDesFormService {

    @Autowired
    private HistoryDesFormMapper historyDesFormMapper;

    @Autowired
    private IJeeLowCodeAdapter jeeLowCodeAdapter;

    //保存
    @Transactional(rollbackFor = Exception.class)
    @Override
    public Long saveDesForm(DesFormAddOrUpdateParam param){
        long id = IdWorker.getId();

        DesformEntity desformEntity=new DesformEntity();
        desformEntity.setId(id);
        desformEntity.setTenantId(jeeLowCodeAdapter.getTenantId());
        desformEntity.setCreateUser(jeeLowCodeAdapter.getOnlineUserId());
        desformEntity.setCreateTime(LocalDateTime.now());
        desformEntity.setDesformName(param.getDesformName());
        desformEntity.setDesformJson(param.getDesformJson());
        desformEntity.setGroupDesformId(param.getGroupDesformId());
        desformEntity.setIsOpen(param.getIsOpen());
        desformEntity.setIsTemplate(param.getIsTemplate());
        baseMapper.insert(desformEntity);

        //保存历史记录
        this.saveHistoryLog(id,param);
        return id;
    }

    //修改
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void updateDesForm(DesFormAddOrUpdateParam param){
        DesformEntity desformEntity=new DesformEntity();
        desformEntity.setId(param.getId());
        desformEntity.setUpdateUser(jeeLowCodeAdapter.getOnlineUserId());
        desformEntity.setUpdateTime(LocalDateTime.now());

        desformEntity.setDesformName(param.getDesformName());
        desformEntity.setDesformJson(param.getDesformJson());
        desformEntity.setGroupDesformId(param.getGroupDesformId());
        desformEntity.setIsOpen(param.getIsOpen());
        desformEntity.setIsTemplate(param.getIsTemplate());
        baseMapper.updateById(desformEntity);

        //保存历史记录
       this.saveHistoryLog(param.getId(),param);
    }

    //保存历史记录
    private void saveHistoryLog(Long id,DesFormAddOrUpdateParam param){
        HistoryDesformEntity historyEntity=new HistoryDesformEntity();
        historyEntity.setId(IdWorker.getId());
        historyEntity.setTenantId(jeeLowCodeAdapter.getTenantId());
        historyEntity.setCreateUser(jeeLowCodeAdapter.getOnlineUserId());
        historyEntity.setCreateTime(LocalDateTime.now());
        historyEntity.setCreateUserName(jeeLowCodeAdapter.getOnlineUserName());

        historyEntity.setDesformId(id);
        historyEntity.setDesformName(param.getDesformName());
        historyEntity.setDesformJson(param.getDesformJson());
        historyEntity.setGroupDesformId(param.getGroupDesformId());
        historyEntity.setIsOpen(param.getIsOpen());
        historyDesFormMapper.insert(historyEntity);
    }
}
