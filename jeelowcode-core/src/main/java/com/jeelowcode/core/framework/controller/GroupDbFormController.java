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
import com.jeelowcode.core.framework.entity.GroupDbFormEntity;
import com.jeelowcode.core.framework.service.IGroupDbFormService;
import com.jeelowcode.framework.utils.model.global.BaseWebResult;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Tag(name = "低代码框架-表单开发分组")
@RestController
@AllArgsConstructor
@RequestMapping(JeeLowCodeBaseConstant.REQUEST_URL_START+"/group/dbform")
public class GroupDbFormController extends BaseController {

    private final IGroupDbFormService groupDbFormService;

    @GetMapping("/detail")
    @ApiOperationSupport(order = 1)
    @Operation(tags = "表单开发分组",summary = "详情")
    public BaseWebResult<GroupDbFormEntity> detail(@RequestParam Long id){
        return BaseWebResult.success(groupDbFormService.getById(id));
    }

    @PreAuthorize("@ss.hasPermission('jeelowcode:dbform:create')")
    @PostMapping("/save")
    @ApiOperationSupport(order = 2)
    @Operation(tags = "表单开发分组",summary = "新增")
    public BaseWebResult save(@RequestBody GroupDbFormEntity model) {
        model.setId(IdWorker.getId());
        groupDbFormService.save(model);
        return BaseWebResult.success(model.getId());
    }

    @PreAuthorize("@ss.hasPermission('jeelowcode:dbform:update')")
    @PutMapping("/update")
    @ApiOperationSupport(order = 3)
    @Operation(tags = "表单开发分组",summary = "修改")
    public BaseWebResult update(@RequestBody GroupDbFormEntity model) {
        groupDbFormService.updateById(model);
        return BaseWebResult.success("成功");
    }

    @PreAuthorize("@ss.hasPermission('jeelowcode:dbform:delete')")
    @DeleteMapping("/delete")
    @ApiOperationSupport(order = 3)
    @Operation(tags = "表单开发分组",summary = "删除(逻辑删除)")
    public BaseWebResult del(@RequestBody List<Long> ids) {
        groupDbFormService.removeBatchByIds(ids);
        return BaseWebResult.success("成功");
    }

    @GetMapping("/list")
    @ApiOperationSupport(order = 3)
    @Operation(tags = "表单开发分组",summary = "获取列表")
    public BaseWebResult list(GroupDbFormEntity entity) {
        LambdaQueryWrapper<GroupDbFormEntity> wrapper=new LambdaQueryWrapper<>();
        wrapper.setEntity(entity);
        wrapper.orderByAsc(GroupDbFormEntity::getId);
        List<GroupDbFormEntity> dataList = groupDbFormService.list(wrapper);
        return BaseWebResult.success(dataList);
    }



}