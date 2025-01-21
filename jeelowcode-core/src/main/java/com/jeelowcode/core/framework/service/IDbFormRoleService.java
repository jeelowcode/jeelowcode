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
package com.jeelowcode.core.framework.service;

import com.jeelowcode.core.framework.params.vo.role.DbFormRoleButtonVo;
import com.jeelowcode.core.framework.params.vo.role.DbFormRoleDataRuleVo;
import com.jeelowcode.core.framework.params.vo.role.DbFormRoleDataTenantVo;
import com.jeelowcode.core.framework.params.vo.role.DbFormRoleFieldVo;

import java.util.List;

/**
 * 表单开发-权限相关
 */
public interface IDbFormRoleService {

    //保存字段权限
    void saveOrUpdateRoleField(List<DbFormRoleFieldVo> voList);

    //字段权限列表
    List<DbFormRoleFieldVo> listRoleField(Long tenantId, Long dbFormId);
    List<DbFormRoleFieldVo> listRoleField(Long tenantId, Long dbFormId,Boolean enableFlag);

    //保存按钮权限
    void saveOrUpdateRoleButton(List<DbFormRoleButtonVo> voList);
    //按钮权限列表
    List<DbFormRoleButtonVo> listRoleButton(Long tenantId, Long dbFormId);
    List<DbFormRoleButtonVo> listRoleButton(Long tenantId, Long dbFormId,Boolean enableFlag);

    //保存数据权限-规则
    void saveOrUpdateRoleDataRule(DbFormRoleDataRuleVo vo);

    //删除数据圈规则
    void delRoleDataRule(Long ruleId);

    ///保存数据权限-租户
    void saveOrUpdateRoleDataTenant(List<DbFormRoleDataTenantVo> voList);

    //数据权限列表
    List<DbFormRoleDataRuleVo> listRoleData(Long tenantId, Long dbFormId);


}
