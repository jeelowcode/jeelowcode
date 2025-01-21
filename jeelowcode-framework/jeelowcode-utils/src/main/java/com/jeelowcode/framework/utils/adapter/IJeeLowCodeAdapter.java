/*					Apache License
                           Version 2.0, January 2004
                        http://www.apache.org/licenses/
			本软件受适用的国家软件著作权法（包括国际条约）和开源协议 双重保护许可。

			开源协议中文释意如下：
			1.JeeLowCode开源版本无任何限制，在遵循本开源协议（Apache2.0）条款下，允许商用使用，不会造成侵权行为。
			2.允许基于本平台软件开展业务系统开发。
			3.在任何情况下，您不得使用本软件开发可能被认为与本软件竞争的软件。

			最终解释权归：http://www.jeelowcode.com*/
/*
Apache License
Version 2.0, January 2004
http://www.apache.org/licenses/
本软件受适用的国家软件著作权法（包括国际条约）和开源协议 双重保护许可。

开源协议中文释意如下：
1.JeeLowCode开源版本无任何限制，在遵循本开源协议（Apache2.0）条款下，允许商用使用，不会造成侵权行为。
2.允许基于本平台软件开展业务系统开发。
3.在任何情况下，您不得使用本软件开发可能被认为与本软件竞争的软件。

最终解释权归：http://www.jeelowcode.com
*/
package com.jeelowcode.framework.utils.adapter;

import com.jeelowcode.framework.utils.model.JeeLowCodeDept;
import com.jeelowcode.framework.utils.model.JeeLowCodeDict;
import com.jeelowcode.framework.utils.model.JeeLowCodeRole;
import com.jeelowcode.framework.utils.model.JeeLowTenant;
import com.jeelowcode.framework.utils.params.JeeLowCodeDeptParam;
import com.jeelowcode.framework.utils.params.JeeLowCodeDictParam;
import com.jeelowcode.framework.utils.params.JeeLowCodeUserParam;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 适配器 相关接口
 */
public interface IJeeLowCodeAdapter {

    //获取当前在线人id
    Long getOnlineUserId();

    //获取当前在线人账号
    String getOnlineUserName();

    //获取当前在线人名称
    String getOnlineUserNickName();

    //获取当前在线人部门id
    Long getOnlineUserDeptId();

    //获取当前在线人id
    Long getTenantId();

    //获取部门id
    Long getDeptId(Long userId);

    //是否不用租户
    boolean getTenantIsIgnore();

    //获取所有租户列表
    List<JeeLowTenant> getTenantList();

    //获取字典列表
    List<JeeLowCodeDict> getDictList(JeeLowCodeDictParam param);

    //获取部门列表
    List<JeeLowCodeDept> getDeptList(JeeLowCodeDeptParam param);

    //获取所有角色列表
    List<JeeLowCodeRole> getRoleList();

    //回显用户
    List<Map<String,Object>> getUserViewList(List<Long> userIdList);
    //回显部门
    List<Map<String,Object>> getDeptViewList(List<Long> deptIdList);


    //获取用户分页列表
    Object getUserPage(Integer pageNo, Integer pageSize, JeeLowCodeUserParam param);

    //初始化-新增数据默认项
    void initSaveDefaultData(Map<String, Object> map);

    //初始化-修改数据默认项
    void initUpdateDefaultData(Map<String, Object> map);

    //获取不用租户的表
    Set<String> getTenantIgnoreTable();
    //不用租户的url
    Set<String> getTenantIgnoreUrl();

    //获取是否开启租户
    boolean getTenantEnable();
}
