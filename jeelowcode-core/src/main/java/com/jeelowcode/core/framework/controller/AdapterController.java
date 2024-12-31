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
import com.jeelowcode.framework.utils.adapter.IJeeLowCodeAdapter;
import com.jeelowcode.framework.utils.model.JeeLowCodeDept;
import com.jeelowcode.framework.utils.model.JeeLowCodeRole;
import com.jeelowcode.framework.utils.model.JeeLowCodeUser;
import com.jeelowcode.framework.utils.model.JeeLowTenant;
import com.jeelowcode.framework.utils.params.JeeLowCodeDeptParam;
import com.jeelowcode.framework.utils.params.JeeLowCodeUserParam;
import com.jeelowcode.framework.utils.model.global.BaseWebResult;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "低代码框架-芋道框架接口")
@RestController
@AllArgsConstructor
@RequestMapping(JeeLowCodeBaseConstant.REQUEST_URL_START + "/adapter")
public class AdapterController extends BaseController {

    private final IJeeLowCodeAdapter jeeLowCodeAdapter;

    @GetMapping("/tenant/list")
    @ApiOperationSupport(order = 1)
    @Operation(tags = "芋道适配器",summary = "获取租户列表")
    public BaseWebResult tenantList() {
        List<JeeLowTenant> tenantList = jeeLowCodeAdapter.getTenantList();
        return BaseWebResult.success(tenantList);
    }

    @GetMapping("/dept/list")
    @ApiOperationSupport(order = 1)
    @Operation(tags = "芋道适配器",summary = "获取部门列表")
    public BaseWebResult deptList(JeeLowCodeDeptParam param) {
        List<JeeLowCodeDept> deptList = jeeLowCodeAdapter.getDeptList(param);
        return BaseWebResult.success(deptList);
    }

    @GetMapping("/role/list")
    @ApiOperationSupport(order = 2)
    @Operation(tags = "芋道适配器",summary = "获取角色列表")
    public BaseWebResult roleList() {
        List<JeeLowCodeRole> roleList = jeeLowCodeAdapter.getRoleList();
        return BaseWebResult.success(roleList);
    }

    @PostMapping("/user/list")
    @ApiOperationSupport(order = 3)
    @Operation(tags = "芋道适配器",summary = "获取用户列表")
    public BaseWebResult userList(@RequestBody JeeLowCodeUserParam param) {
        IPage<JeeLowCodeUser> userPage =(IPage<JeeLowCodeUser>) jeeLowCodeAdapter.getUserPage(param.getPageNo(), param.getPageSize(), param);
        return BaseWebResult.success(userPage);
    }
}
