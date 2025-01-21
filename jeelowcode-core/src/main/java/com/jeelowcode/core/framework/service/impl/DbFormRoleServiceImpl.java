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
import com.jeelowcode.core.framework.mapper.FormRoleButtonMapper;
import com.jeelowcode.core.framework.mapper.FormRoleDataRuleMapper;
import com.jeelowcode.core.framework.mapper.FormRoleDataTenantMapper;
import com.jeelowcode.core.framework.mapper.FormRoleFieldMapper;
import com.jeelowcode.core.framework.params.vo.role.DbFormRoleButtonVo;
import com.jeelowcode.core.framework.params.vo.role.DbFormRoleDataRuleVo;
import com.jeelowcode.core.framework.params.vo.role.DbFormRoleDataTenantVo;
import com.jeelowcode.core.framework.params.vo.role.DbFormRoleFieldVo;
import com.jeelowcode.core.framework.service.IDbFormRoleService;
import com.jeelowcode.core.framework.service.IFormButtonService;
import com.jeelowcode.core.framework.service.IFormService;
import com.jeelowcode.core.framework.utils.Func;
import com.jeelowcode.framework.utils.annotation.JeelowCodeCache;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


/**
 * 表单开发-权限相关
 */
@Service
public class DbFormRoleServiceImpl implements IDbFormRoleService {

    @Autowired
    private FormRoleFieldMapper roleFieldMapper;

    @Autowired
    private FormRoleButtonMapper roleButtonMapper;

    @Autowired
    private FormRoleDataRuleMapper roleDataRuleMapper;

    @Autowired
    private FormRoleDataTenantMapper roleDataTenantMapper;

    @Autowired
    private IFormService formService;

    @Autowired
    private IFormButtonService buttonService;

    //保存字段权限
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void saveOrUpdateRoleField(List<DbFormRoleFieldVo> voList) {
        voList.stream().forEach(vo -> {
            Long tenantId = Func.toLong(vo.getTenantId());
            LambdaQueryWrapper<FormRoleFieldEntity> wrapper = new LambdaQueryWrapper<>();
            wrapper.eq(FormRoleFieldEntity::getTenantId, tenantId);
            wrapper.eq(FormRoleFieldEntity::getDbformId, vo.getDbformId());
            wrapper.eq(FormRoleFieldEntity::getFieldCode, vo.getFieldCode());
            FormRoleFieldEntity selectEntity = roleFieldMapper.selectOne(wrapper);

            //默认是开启的，所以只对关闭操作
            FormRoleFieldEntity entity = new FormRoleFieldEntity();
            entity.setTenantId(tenantId);
            entity.setDbformId(vo.getDbformId());
            entity.setFieldCode(vo.getFieldCode());

            entity.setListIsView(vo.getListIsView());
            entity.setFormIsEdit(vo.getFormIsEdit());
            entity.setFormIsView(vo.getFormIsView());
            entity.setEnableState(vo.getEnableState());
            if (Func.isNotEmpty(selectEntity)) {//更新
                entity.setId(selectEntity.getId());
                roleFieldMapper.updateById(entity);
            } else {//新增
                roleFieldMapper.insert(entity);
            }

            boolean publicExist = false;
            if (!Func.equals(tenantId, 1L)) {
                LambdaQueryWrapper<FormRoleFieldEntity> pulbicwrapper = new LambdaQueryWrapper<>();
                pulbicwrapper.eq(FormRoleFieldEntity::getTenantId, 1L);
                pulbicwrapper.eq(FormRoleFieldEntity::getDbformId, vo.getDbformId());
                pulbicwrapper.eq(FormRoleFieldEntity::getFieldCode, vo.getFieldCode());
                FormRoleFieldEntity publicSelectEntity = roleFieldMapper.selectOne(pulbicwrapper);
                if (Func.isNotEmpty(publicSelectEntity)) {
                    publicExist = true;
                }
            }


            FormRoleFieldEntity afterEntity = roleFieldMapper.selectById(entity.getId());
            //如果是全开的情况下，直接删掉。如果通用的存在的情况下是不能删的
            if (!publicExist &&
                    Func.isEmpty(afterEntity.getEnableState()) &&
                    Func.isEmpty(afterEntity.getListIsView()) &&
                    Func.isEmpty(afterEntity.getFormIsView()) &&
                    Func.isEmpty(afterEntity.getFormIsEdit())) {
                roleFieldMapper.deleteById(entity.getId());
            }
        });

    }

    //字段权限列表
    @Override
    public List<DbFormRoleFieldVo> listRoleField(Long tenantId, Long dbFormId) {
        return this.listRoleField(tenantId,dbFormId,true);
    }

    @JeelowCodeCache(cacheNames = "'DbFormRoleServiceImpl:listRoleField:' + #tenantId+'_'+#dbFormId+'_'+#enableFlag", reflexClass = DbFormRoleFieldVo.class,nullIsSave = true)
    @Override
    public List<DbFormRoleFieldVo> listRoleField(Long tenantId, Long dbFormId,Boolean enableFlag) {
        if(Func.isEmpty(tenantId) || Func.equals(tenantId, -1L)){
            return null;
        }
        List<FormFieldEntity> fieldEntityList = formService.getFieldList(dbFormId);
        //获取公共
        LambdaQueryWrapper<FormRoleFieldEntity> publicWrapper = new LambdaQueryWrapper<>();
        publicWrapper.eq(FormRoleFieldEntity::getTenantId, 1L);
        publicWrapper.eq(FormRoleFieldEntity::getDbformId, dbFormId);
        List<FormRoleFieldEntity> publicEntityList = roleFieldMapper.selectList(publicWrapper);
        //转为map
        Map<String, FormRoleFieldEntity> publicMap = publicEntityList.stream()
                .collect(Collectors.toMap(FormRoleFieldEntity::getFieldCode, entity -> entity));

        //获取租户私有
        LambdaQueryWrapper<FormRoleFieldEntity> tenantWrapper = new LambdaQueryWrapper<>();
        tenantWrapper.eq(FormRoleFieldEntity::getTenantId, tenantId);
        tenantWrapper.eq(FormRoleFieldEntity::getDbformId, dbFormId);
        List<FormRoleFieldEntity> tenantEntityList = roleFieldMapper.selectList(tenantWrapper);
        Map<String, FormRoleFieldEntity> tenantMap = tenantEntityList.stream()
                .collect(Collectors.toMap(FormRoleFieldEntity::getFieldCode, entity -> entity));

        //转为vo
        List<DbFormRoleFieldVo> resultList = new ArrayList<>();
        for (FormFieldEntity fieldEntity : fieldEntityList) {
            String listIsView = null;
            String formIsView = null;
            String formIsEdit = null;
            String enableState = null;
            //判断私有是否存在
            FormRoleFieldEntity entity = tenantMap.get(fieldEntity.getFieldCode());
            if (Func.isEmpty(entity)) {//私有不存在，判断公共是否存在
                entity = publicMap.get(fieldEntity.getFieldCode());
            }
            if (Func.isNotEmpty(entity)) {//存在，则用自己的
                listIsView = entity.getListIsView();
                formIsView = entity.getFormIsView();
                formIsEdit = entity.getFormIsEdit();
                enableState = entity.getEnableState();//关闭
            }
            //获取关闭的
            if(Func.isNotEmpty(enableFlag) && !enableFlag){
                //所有都为空的话，说明是不做任何操作
                if(Func.isEmpty(listIsView) && Func.isEmpty(formIsEdit) && Func.isEmpty(formIsView) && Func.isEmpty(enableState)){
                    continue;
                }
            }

            DbFormRoleFieldVo vo = new DbFormRoleFieldVo();
            vo.setFieldCode(fieldEntity.getFieldCode());
            vo.setFieldName(fieldEntity.getFieldName());
            vo.setListIsView(listIsView);
            vo.setFormIsView(formIsView);
            vo.setFormIsEdit(formIsEdit);
            vo.setEnableState(enableState);
            resultList.add(vo);
        }

        return resultList;
    }


    //保存按钮权限
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void saveOrUpdateRoleButton(List<DbFormRoleButtonVo> voList) {
        voList.stream().forEach(vo -> {
            Long tenantId = Func.toLong(vo.getTenantId());
            LambdaQueryWrapper<FormRoleButtonEntity> wrapper = new LambdaQueryWrapper<>();
            wrapper.eq(FormRoleButtonEntity::getTenantId, tenantId);
            wrapper.eq(FormRoleButtonEntity::getDbformId, vo.getDbformId());
            wrapper.eq(FormRoleButtonEntity::getButtonCode, vo.getButtonCode());
            FormRoleButtonEntity selectEntity = roleButtonMapper.selectOne(wrapper);

            //默认是开启的，所以只对关闭操作
            FormRoleButtonEntity entity = new FormRoleButtonEntity();
            entity.setTenantId(tenantId);
            entity.setDbformId(vo.getDbformId());
            entity.setButtonCode(vo.getButtonCode());
            entity.setEnableState(vo.getEnableState());
            if (Func.isNotEmpty(selectEntity)) {//更新
                entity.setId(selectEntity.getId());
                roleButtonMapper.updateById(entity);
            } else {//新增
                roleButtonMapper.insert(entity);
            }
            boolean publicExist = false;
            if (!Func.equals(tenantId, 1L)) {
                LambdaQueryWrapper<FormRoleButtonEntity> publicwrapper = new LambdaQueryWrapper<>();
                publicwrapper.eq(FormRoleButtonEntity::getTenantId, tenantId);
                publicwrapper.eq(FormRoleButtonEntity::getDbformId, vo.getDbformId());
                publicwrapper.eq(FormRoleButtonEntity::getButtonCode, vo.getButtonCode());
                FormRoleButtonEntity publicselectEntity = roleButtonMapper.selectOne(publicwrapper);
                if (Func.isNotEmpty(publicselectEntity)) {
                    publicExist = true;
                }
            }


            FormRoleButtonEntity afterEntity = roleButtonMapper.selectById(entity.getId());
            //如果是全开的情况下，直接删掉
            if (!publicExist &&
                    Func.isEmpty(afterEntity.getEnableState()) &&
                    Func.isEmpty(afterEntity.getButtonCode())) {
                roleButtonMapper.deleteById(entity.getId());
            }
        });
    }

    //字段权限列表
    @Override
    public List<DbFormRoleButtonVo> listRoleButton(Long tenantId, Long dbFormId) {
        return listRoleButton(tenantId,dbFormId,true);
    }

    @JeelowCodeCache(cacheNames = "'DbFormRoleServiceImpl:listRoleButton:' + #tenantId+'_'+#dbFormId+'_'+#enableFlag", reflexClass = DbFormRoleButtonVo.class,nullIsSave = true)
    @Override
    public List<DbFormRoleButtonVo> listRoleButton(Long tenantId, Long dbFormId,Boolean enableFlag) {
        List<FormButtonEntity> buttonList = new ArrayList<>();

        FormEntity formEntity = formService.getFormEntityById(dbFormId);
        String basicFunctionStr = formEntity.getBasicFunction();//默认按钮
        if (Func.isNotEmpty(basicFunctionStr)) {
            List<String> defaultButtonList = Func.toStrList(basicFunctionStr);
            defaultButtonList.stream().forEach(buttonCode -> {
                String buttonName = Func.getButtonName(buttonCode);
                FormButtonEntity buttonEntity = new FormButtonEntity();
                buttonEntity.setButtonCode(buttonCode);
                buttonEntity.setButtonName(buttonName);
                buttonList.add(buttonEntity);
            });
        }


        //获取自定义按钮
        LambdaQueryWrapper<FormButtonEntity> buttonWrapper = new LambdaQueryWrapper<>();
        buttonWrapper.eq(FormButtonEntity::getDbformId, dbFormId);
        List<FormButtonEntity> selfButtonList = buttonService.list(buttonWrapper);
        if (Func.isNotEmpty(buttonList)) {
            buttonList.addAll(selfButtonList);
        }
        if (Func.isEmpty(buttonList)) {
            return null;
        }

        //获取公共
        LambdaQueryWrapper<FormRoleButtonEntity> publicWrapper = new LambdaQueryWrapper<>();
        publicWrapper.eq(FormRoleButtonEntity::getTenantId, 1L);
        publicWrapper.eq(FormRoleButtonEntity::getDbformId, dbFormId);
        List<FormRoleButtonEntity> publicEntityList = roleButtonMapper.selectList(publicWrapper);
        //转为map
        Map<String, FormRoleButtonEntity> publicMap = publicEntityList.stream()
                .collect(Collectors.toMap(FormRoleButtonEntity::getButtonCode, entity -> entity));

        //获取租户私有
        LambdaQueryWrapper<FormRoleButtonEntity> tenantWrapper = new LambdaQueryWrapper<>();
        tenantWrapper.eq(FormRoleButtonEntity::getTenantId, tenantId);
        tenantWrapper.eq(FormRoleButtonEntity::getDbformId, dbFormId);
        List<FormRoleButtonEntity> tenantEntityList = roleButtonMapper.selectList(tenantWrapper);
        //转为map
        Map<String, FormRoleButtonEntity> tenantMap = tenantEntityList.stream()
                .collect(Collectors.toMap(FormRoleButtonEntity::getButtonCode, entity -> entity));

        //转为vo
        List<DbFormRoleButtonVo> resultList = new ArrayList<>();
        for (FormButtonEntity buttonEntity : buttonList) {

            String enableState = null;
            //判断私有是否存在
            FormRoleButtonEntity entity = tenantMap.get(buttonEntity.getButtonCode());
            if (Func.isEmpty(entity)) {//私有不存在，判断公共是否存在
                entity = publicMap.get(buttonEntity.getButtonCode());
            }
            if (Func.isNotEmpty(entity)) {//存在，则用自己的
                enableState = entity.getEnableState();//关闭
            }
            if(Func.isNotEmpty(enableFlag) && !enableFlag){
                if(Func.isEmpty(enableState)){
                    continue;
                }
            }

            DbFormRoleButtonVo vo = new DbFormRoleButtonVo();
            vo.setButtonCode(buttonEntity.getButtonCode());
            vo.setButtonName(buttonEntity.getButtonName());
            vo.setEnableState(enableState);
            resultList.add(vo);
        }

        return resultList;
    }


    //保存规则
    @Override
    public void saveOrUpdateRoleDataRule(DbFormRoleDataRuleVo vo) {
        //保存规则
        FormRoleDataRuleEntity entity = new FormRoleDataRuleEntity();
        entity.setDbformId(vo.getDbformId());
        entity.setRuleName(vo.getRuleName());
        entity.setRuleType(vo.getRuleType());
        entity.setRuleField(vo.getRuleField());
        entity.setRuleCondition(vo.getRuleCondition());
        entity.setRuleValue(vo.getRuleValue());
        entity.setRuleSql(vo.getRuleSql());
        if (Func.isNotEmpty(vo.getId())) {//更新
            entity.setId(vo.getId());
            roleDataRuleMapper.updateById(entity);
        } else {//新增
            roleDataRuleMapper.insert(entity);
        }
    }

    //删除数据圈规则
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delRoleDataRule(Long ruleId) {
        roleDataRuleMapper.deleteById(ruleId);

        LambdaQueryWrapper<FormRoleDataTenantEntity> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(FormRoleDataTenantEntity::getDbformRoleDataRuleId, ruleId);
        roleDataTenantMapper.delete(wrapper);
    }


    @Transactional(rollbackFor = Exception.class)
    @Override
    public void saveOrUpdateRoleDataTenant(List<DbFormRoleDataTenantVo> voList) {
        voList.stream().forEach(vo -> {
            Long tenantId = Func.toLong(vo.getTenantId());
            LambdaQueryWrapper<FormRoleDataTenantEntity> wrapper = new LambdaQueryWrapper<>();
            wrapper.eq(FormRoleDataTenantEntity::getTenantId, tenantId);
            wrapper.eq(FormRoleDataTenantEntity::getDbformRoleDataRuleId, vo.getRuleId());
            FormRoleDataTenantEntity selectEntity = roleDataTenantMapper.selectOne(wrapper);

            //默认是开启的，所以只对关闭操作
            FormRoleDataTenantEntity entity = new FormRoleDataTenantEntity();
            entity.setTenantId(tenantId);
            entity.setDbformRoleDataRuleId(vo.getRuleId());
            entity.setEnableState(vo.getEnableState());
            if (Func.isNotEmpty(selectEntity)) {//更新
                entity.setId(selectEntity.getId());
                roleDataTenantMapper.updateById(entity);
            } else {//新增
                roleDataTenantMapper.insert(entity);
            }
            boolean publicExist = false;
            if (!Func.equals(tenantId, 1L)) {
                LambdaQueryWrapper<FormRoleDataTenantEntity> publicwrapper = new LambdaQueryWrapper<>();
                publicwrapper.eq(FormRoleDataTenantEntity::getTenantId, tenantId);
                publicwrapper.eq(FormRoleDataTenantEntity::getDbformRoleDataRuleId, vo.getRuleId());
                FormRoleDataTenantEntity publicselectEntity = roleDataTenantMapper.selectOne(publicwrapper);
                if (Func.isNotEmpty(publicselectEntity)) {
                    publicExist = true;
                }
            }


            FormRoleDataTenantEntity afterEntity = roleDataTenantMapper.selectById(entity.getId());

            //如果是全开的情况下，直接删掉
            if (!publicExist && Func.isEmpty(afterEntity.getEnableState())) {
                roleDataTenantMapper.deleteById(entity.getId());
            }
        });
    }


    //字段权限列表
    @JeelowCodeCache(cacheNames = "'DbFormRoleServiceImpl:listRoleData' + #tenantId+'_'+#dbFormId", reflexClass = DbFormRoleDataRuleVo.class,nullIsSave = true)
    @Override
    public List<DbFormRoleDataRuleVo> listRoleData(Long tenantId, Long dbFormId) {
        List<DbFormRoleDataRuleVo> resultList = new ArrayList<>();

        //获取所有规则
        LambdaQueryWrapper<FormRoleDataRuleEntity> ruleWrapper = new LambdaQueryWrapper<>();
        ruleWrapper.eq(FormRoleDataRuleEntity::getDbformId, dbFormId);
        List<FormRoleDataRuleEntity> ruleList = roleDataRuleMapper.selectList(ruleWrapper);
        if (Func.isEmpty(ruleList)) {
            return resultList;
        }
        List<Long> ruleIdList = ruleList.stream()
                .map(FormRoleDataRuleEntity::getId)
                .collect(Collectors.toList());

        //获取公共
        LambdaQueryWrapper<FormRoleDataTenantEntity> publicWrapper = new LambdaQueryWrapper<>();
        publicWrapper.eq(FormRoleDataTenantEntity::getTenantId, 1L);
        publicWrapper.in(FormRoleDataTenantEntity::getDbformRoleDataRuleId, ruleIdList);
        List<FormRoleDataTenantEntity> publicEntityList = roleDataTenantMapper.selectList(publicWrapper);

        //转为map
        Map<Long, FormRoleDataTenantEntity> publicMap = publicEntityList.stream()
                .collect(Collectors.toMap(FormRoleDataTenantEntity::getDbformRoleDataRuleId, entity -> entity));

        //获取租户私有
        LambdaQueryWrapper<FormRoleDataTenantEntity> tenantWrapper = new LambdaQueryWrapper<>();
        tenantWrapper.eq(FormRoleDataTenantEntity::getTenantId, tenantId);
        tenantWrapper.in(FormRoleDataTenantEntity::getDbformRoleDataRuleId, ruleIdList);
        List<FormRoleDataTenantEntity> tenantEntityList = roleDataTenantMapper.selectList(tenantWrapper);
        //转为map
        Map<Long, FormRoleDataTenantEntity> tenantMap = tenantEntityList.stream()
                .collect(Collectors.toMap(FormRoleDataTenantEntity::getDbformRoleDataRuleId, entity -> entity));

        //转为vo

        for (FormRoleDataRuleEntity ruleEntity : ruleList) {


            String enableState = null;
            //判断私有是否存在
            FormRoleDataTenantEntity entity = tenantMap.get(ruleEntity.getId());
            if (Func.isEmpty(entity)) {//私有不存在，判断公共是否存在
                entity = publicMap.get(ruleEntity.getId());
            }
            if (Func.isNotEmpty(entity)) {//存在，则用自己的
                enableState = entity.getEnableState();//关闭
            }


            DbFormRoleDataRuleVo vo = new DbFormRoleDataRuleVo();
            vo.setId(ruleEntity.getId());
            vo.setDbformId(ruleEntity.getDbformId());
            vo.setRuleName(ruleEntity.getRuleName());
            vo.setRuleType(ruleEntity.getRuleType());
            vo.setRuleField(ruleEntity.getRuleField());
            vo.setRuleCondition(ruleEntity.getRuleCondition());
            vo.setRuleValue(ruleEntity.getRuleValue());
            vo.setRuleSql(ruleEntity.getRuleSql());
            vo.setEnableState(enableState);
            resultList.add(vo);
        }

        return resultList;
    }

}
