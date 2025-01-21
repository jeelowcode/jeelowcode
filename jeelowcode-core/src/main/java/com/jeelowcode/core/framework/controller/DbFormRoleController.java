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
package com.jeelowcode.core.framework.controller;

import com.jeelowcode.framework.global.JeeLowCodeBaseConstant;
import com.jeelowcode.core.framework.params.vo.role.DbFormRoleButtonVo;
import com.jeelowcode.core.framework.params.vo.role.DbFormRoleDataRuleVo;
import com.jeelowcode.core.framework.params.vo.role.DbFormRoleDataTenantVo;
import com.jeelowcode.core.framework.params.vo.role.DbFormRoleFieldVo;
import com.jeelowcode.core.framework.service.IDbFormRoleService;
import com.jeelowcode.framework.utils.adapter.IJeeLowCodeAdapter;
import com.jeelowcode.framework.utils.model.global.BaseWebResult;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "低代码框架-表单开发-权限接口")
@RestController
@AllArgsConstructor
@RequestMapping(JeeLowCodeBaseConstant.REQUEST_URL_START + "/dbform-role")
public class DbFormRoleController extends BaseController {

    private final IDbFormRoleService dbFormRoleService;

    private final IJeeLowCodeAdapter jeeLowCodeAdapter;


    @PreAuthorize("@ss.hasPermission('jeelowcode:dbform:role')")
    @PostMapping("/save-field")
    @ApiOperationSupport(order = 1)
    @Operation(tags = "表单开发",summary = "新增/修改 字段权限")
    public BaseWebResult saveRoleField(@RequestBody List<DbFormRoleFieldVo> voList) {
        dbFormRoleService.saveOrUpdateRoleField(voList);
        return BaseWebResult.success("成功");
    }

    //@PreAuthorize("@ss.hasPermission('jeelowcode:dbform:role')")
    @PostMapping("/list-field")
    @ApiOperationSupport(order = 2)
    @Operation(tags = "表单开发",summary = "列表 字段权限")
    public BaseWebResult listRoleField(Long tenantId,Long dbFormId) {
        List<DbFormRoleFieldVo> list = dbFormRoleService.listRoleField(tenantId, dbFormId);
        return BaseWebResult.success(list);
    }

    @PreAuthorize("@ss.hasPermission('jeelowcode:dbform:role')")
    @PostMapping("/save-button")
    @ApiOperationSupport(order = 3)
    @Operation(tags = "表单开发",summary = "新增/修改 按钮权限")
    public BaseWebResult saveRoleButton(@RequestBody List<DbFormRoleButtonVo> voList) {
        dbFormRoleService.saveOrUpdateRoleButton(voList);
        return BaseWebResult.success("成功");
    }

    //@PreAuthorize("@ss.hasPermission('jeelowcode:dbform:role')")
    @PostMapping("/list-button")
    @ApiOperationSupport(order = 4)
    @Operation(tags = "表单开发",summary = "列表 字段权限")
    public BaseWebResult listRoleButton(Long tenantId,Long dbFormId) {
        List<DbFormRoleButtonVo> list = dbFormRoleService.listRoleButton(tenantId, dbFormId);
        return BaseWebResult.success(list);
    }

    @PreAuthorize("@ss.hasPermission('jeelowcode:dbform:role')")
    @PostMapping("/save-data-rule")
    @ApiOperationSupport(order = 3)
    @Operation(tags = "表单开发",summary = "新增/修改 数据权限-规则")
    public BaseWebResult saveRoleDataRule(@RequestBody DbFormRoleDataRuleVo vo) {
        dbFormRoleService.saveOrUpdateRoleDataRule(vo);
        return BaseWebResult.success("成功");
    }

    @PreAuthorize("@ss.hasPermission('jeelowcode:dbform:role')")
    @DeleteMapping("/del-data-rule")
    @ApiOperationSupport(order = 3)
    @Operation(tags = "表单开发",summary = "删除 数据权限-规则")
    public BaseWebResult delRoleDataRule(Long ruleId) {
        dbFormRoleService.delRoleDataRule(ruleId);
        return BaseWebResult.success("成功");
    }

    @PreAuthorize("@ss.hasPermission('jeelowcode:dbform:role')")
    @PostMapping("/save-data-tenant")
    @ApiOperationSupport(order = 3)
    @Operation(tags = "表单开发",summary = "新增/修改 数据权限-租户数据")
    public BaseWebResult saveRoleDataTenant(@RequestBody List<DbFormRoleDataTenantVo> voList) {
        dbFormRoleService.saveOrUpdateRoleDataTenant(voList);
        return BaseWebResult.success("成功");
    }


    //@PreAuthorize("@ss.hasPermission('jeelowcode:dbform:role')")
    @PostMapping("/list-data")
    @ApiOperationSupport(order = 4)
    @Operation(tags = "表单开发",summary = "列表 数据权限")
    public BaseWebResult listRoleData(Long tenantId,Long dbFormId) {
        List<DbFormRoleDataRuleVo> list = dbFormRoleService.listRoleData(tenantId, dbFormId);
        return BaseWebResult.success(list);
    }
}
