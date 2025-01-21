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
package com.jeelowcode.core.framework.mapper;

import com.jeelowcode.framework.utils.annotation.JeelowCodeCache;
import com.jeelowcode.framework.utils.model.JeeLowCodeUser;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 *  适配器相关
 */
public interface AdapterMapper {


    @JeelowCodeCache(cacheNames = "'AdapterMapper:getUserName:' + #userId", reflexClass = String.class)
    String getUserName(Long userId);

    @JeelowCodeCache(cacheNames = "'AdapterMapper:getNickname:' + #userId", reflexClass = String.class)
    String getNickname(Long userId);

    @JeelowCodeCache(cacheNames = "'AdapterMapper:getDeptId:' + #userId", reflexClass = Long.class)
    Long getDeptId(Long userId);


    /**
     * 根据部门获取用户列表
     * @param page
     * @param deptIdList
     * @param params
     * @return
     */
    IPage<JeeLowCodeUser> getUserPageByDeptId(Long tenantId,IPage page, List<Long> deptIdList, @Param("params") Map<String, Object> params);

    /**
     * 根据角色获取用户列表
     * @param page
     * @param roleId
     * @param deptIdList
     * @param params
     * @return
     */
    IPage<JeeLowCodeUser> getUserPageByRoleId(Long tenantId,IPage page, Long roleId, List<Long> deptIdList, @Param("params") Map<String, Object> params);

    /**
     * 根据表单id获取表单的字段对应的字典
     * @param dbFormId
     * @return
     */
    @JeelowCodeCache(cacheNames = "'AdapterMapper:getDbFormFieldDict:' + #dbFormId", reflexClass = Map.class,nullIsSave = true)
    List<Map<String, Object>> getDbFormFieldDict(Long dbFormId);
}
