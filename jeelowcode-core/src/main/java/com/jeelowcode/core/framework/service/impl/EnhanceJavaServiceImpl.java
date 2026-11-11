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

import com.jeelowcode.core.framework.config.aspect.enhance.EnhanceScriptEnvGuard;
import com.jeelowcode.core.framework.config.aspect.enhance.JeeLowCodeAnnotationAspectjJAVA;
import com.jeelowcode.core.framework.entity.EnhanceJavaEntity;
import com.jeelowcode.core.framework.entity.HistoryDbformEntity;
import com.jeelowcode.core.framework.mapper.EnhanceJavaMapper;
import com.jeelowcode.core.framework.mapper.HistoryDbFormMapper;
import com.jeelowcode.core.framework.service.IEnhanceJavaService;
import com.jeelowcode.core.framework.utils.Func;
import com.jeelowcode.framework.exception.JeeLowCodeException;
import com.jeelowcode.framework.utils.adapter.IJeeLowCodeAdapter;
import com.jeelowcode.framework.utils.utils.DevProfileUtils;
import com.jeelowcode.framework.utils.constant.EnhanceConstant;
import com.jeelowcode.framework.utils.enums.TableType2Enum;
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
import java.util.stream.Collectors;


/**
 * @author JX
 * @create 2024-03-06 9:55
 * @dedescription:
 */

@Service
public class EnhanceJavaServiceImpl extends ServiceImpl<EnhanceJavaMapper, EnhanceJavaEntity> implements IEnhanceJavaService {

    @Autowired
    private IJeeLowCodeAdapter jeeLowCodeAdapter;

    @Autowired
    private HistoryDbFormMapper historyDbFormMapper;

    //新增java增强
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void saveEnhanceJava(EnhanceJavaEntity enhanceJavaEntity){
        EnhanceScriptEnvGuard.checkCreate(enhanceJavaEntity);

        long id = IdWorker.getId();
        enhanceJavaEntity.setId(id);
        this.save(enhanceJavaEntity);

        //停用的在线增强不注册，避免在开发环境创建 N 状态脚本时仍执行。
        if (!EnhanceScriptEnvGuard.isOnline(enhanceJavaEntity)
                || YNEnum.Y.getCode().equals(enhanceJavaEntity.getActiveStatus())) {
            JeeLowCodeAnnotationAspectjJAVA.addPlugin(enhanceJavaEntity);
        }
        //加历史记录
        this.saveHistoryLog(id,enhanceJavaEntity);
    }

    //编辑java增强
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void updateEnhanceJava(EnhanceJavaEntity enhanceJavaEntity){
        if (enhanceJavaEntity == null || enhanceJavaEntity.getId() == null) {
            throw new JeeLowCodeException("增强ID不能为空");
        }
        EnhanceJavaEntity oldEntity = this.getById(enhanceJavaEntity.getId());
        EnhanceJavaEntity merged = EnhanceScriptEnvGuard.prepareUpdate(oldEntity, enhanceJavaEntity);
        if (!this.updateById(merged)) {
            throw new JeeLowCodeException("增强更新失败");
        }

        //按旧绑定移除，再用已校验的完整配置注册，兼容稀疏更新及开发环境修改绑定。
        JeeLowCodeAnnotationAspectjJAVA.deletePlugins(oldEntity);
        if (YNEnum.Y.getCode().equals(merged.getActiveStatus())) {
            JeeLowCodeAnnotationAspectjJAVA.updatePlugins(merged);
        }

        this.saveHistoryLog(merged.getId(), merged);
    }



    @Override
    public void copy(Long sourceFormIdId,Long targetFormIdId,String oldTableName,String newTableName){
        LambdaQueryWrapper<EnhanceJavaEntity> javaWrapper=new LambdaQueryWrapper<>();
        javaWrapper.eq(EnhanceJavaEntity::getDbformId,sourceFormIdId);
        javaWrapper.orderByAsc(EnhanceJavaEntity::getId);
        List<EnhanceJavaEntity> javaEntityList = this.list(javaWrapper);
        //清空默认值并修改dbformId值
        javaEntityList = javaEntityList.stream().filter(javaEntity -> {
            javaEntity.setDbformId(targetFormIdId);
            javaEntity.setId(null);
            javaEntity.setTenantId(null);
            javaEntity.setCreateDept(null);
            javaEntity.setCreateTime(null);
            javaEntity.setCreateUser(null);
            javaEntity.setUpdateTime(null);
            javaEntity.setUpdateUser(null);
            javaEntity.setIsDeleted(null);
            if (FuncBase.equals(javaEntity.getJavaType(), EnhanceConstant.ENHANCE_TYPE_SPRING)) {// spring方式
                String newEnhanceName = Func.copySpringEnhance(TableType2Enum.DBFORM.getType(), javaEntity.getJavaClassUrl(), newTableName);
                if (FuncBase.isEmpty(newEnhanceName)) {
                    return false;
                }
                javaEntity.setJavaClassUrl(newEnhanceName);
            } else if (FuncBase.equals(javaEntity.getJavaType(), EnhanceConstant.ENHANCE_TYPE_CLASS)) {// class方式
                String newEnhanceName = Func.copyClassEnhance(TableType2Enum.DBFORM.getType(), javaEntity.getJavaClassUrl(), newTableName);
                if (FuncBase.isEmpty(newEnhanceName)) {
                    return false;
                }
                javaEntity.setJavaClassUrl(newEnhanceName);
            } else if (FuncBase.equals(javaEntity.getJavaType(), EnhanceConstant.ENHANCE_TYPE_ONLINE_EDIT)) {// 在线编辑方式
                //安全策略：非 local/dev 环境，复制过来的在线脚本默认不生效，避免脚本被复制到生产环境
                if (!DevProfileUtils.isDevOrLocal()) {
                    javaEntity.setActiveStatus(YNEnum.N.getCode());
                }
            }
            return true;
        }).collect(Collectors.toList());
        this.saveBatch(javaEntityList);
    }

    //保存历史记录
    private void saveHistoryLog(Long javaId, EnhanceJavaEntity javaEntity){

       try{
           HistoryDbformEntity entity=new HistoryDbformEntity();
           entity.setId(IdWorker.getId());
           entity.setTenantId(jeeLowCodeAdapter.getTenantId());
           entity.setCreateUser(jeeLowCodeAdapter.getOnlineUserId());
           entity.setCreateTime(LocalDateTime.now());
           entity.setCreateUserName(jeeLowCodeAdapter.getOnlineUserName());

           entity.setDbformId(javaEntity.getDbformId());
           entity.setServiceType("java");
           entity.setServiceId(javaId);
           entity.setServiceJson(Func.json2Str(javaEntity));

           historyDbFormMapper.insert(entity);
       }catch (Exception e){
           e.printStackTrace();
       }
    }

    @Override
    public List<EnhanceJavaEntity> getAllJavaEnhance(){
        LambdaQueryWrapper<EnhanceJavaEntity> javaWrapper=new LambdaQueryWrapper<>();
        javaWrapper.eq(EnhanceJavaEntity::getActiveStatus,YNEnum.Y.getCode());
        javaWrapper.isNotNull(EnhanceJavaEntity::getJavaClassUrl);
        javaWrapper.isNotNull(EnhanceJavaEntity::getDbformId);
        List<EnhanceJavaEntity> enhanceJavaEntities = baseMapper.selectList(javaWrapper);
        return enhanceJavaEntities;
    }
}
