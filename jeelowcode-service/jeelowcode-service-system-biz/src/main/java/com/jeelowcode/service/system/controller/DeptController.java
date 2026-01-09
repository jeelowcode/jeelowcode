package com.jeelowcode.service.system.controller;

import com.jeelowcode.tool.framework.common.enums.CommonStatusEnum;
import com.jeelowcode.tool.framework.common.pojo.CommonResult;
import com.jeelowcode.tool.framework.common.util.object.BeanUtils;
import com.jeelowcode.service.system.controller.vo.dept.dept.DeptListReqVO;
import com.jeelowcode.service.system.controller.vo.dept.dept.DeptRespVO;
import com.jeelowcode.service.system.controller.vo.dept.dept.DeptSaveReqVO;
import com.jeelowcode.service.system.controller.vo.dept.dept.DeptSimpleRespVO;
import com.jeelowcode.service.system.entity.DeptDO;
import com.jeelowcode.service.system.service.IDeptService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;

import static com.jeelowcode.tool.framework.common.pojo.CommonResult.success;

@Tag(name = "管理后台 - 部门")
@RestController
@RequestMapping("/system/dept")
@Validated
public class DeptController {

    @Resource
    private IDeptService DeptService;

    @PostMapping("create")
    @Operation(tags = "部门管理",summary = "创建部门")
    @PreAuthorize("@ss.hasPermission('system:dept:create')")
    public CommonResult<Long> createDept(@Valid @RequestBody DeptSaveReqVO createReqVO) {
        Long deptId = DeptService.createDept(createReqVO);
        return success(deptId);
    }

    @PutMapping("update")
    @Operation(tags = "部门管理",summary = "更新部门")
    @PreAuthorize("@ss.hasPermission('system:dept:update')")
    public CommonResult<Boolean> updateDept(@Valid @RequestBody DeptSaveReqVO updateReqVO) {
        DeptService.updateDept(updateReqVO);
        return success(true);
    }

    @DeleteMapping("delete")
    @Operation(tags = "部门管理",summary = "删除部门")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('system:dept:delete')")
    public CommonResult<Boolean> deleteDept(@RequestParam("id") Long id) {
        DeptService.deleteDept(id);
        return success(true);
    }

    @GetMapping("/list")
    @Operation(tags = "部门管理",summary = "获取部门列表")
    @PreAuthorize("@ss.hasPermission('system:dept:query')")
    public CommonResult<List<DeptRespVO>> getDeptList(DeptListReqVO reqVO) {
        List<DeptDO> list = DeptService.getDeptList(reqVO);
        return success(BeanUtils.toBean(list, DeptRespVO.class));
    }

    @GetMapping(value = {"/list-all-simple", "/simple-list"})
    @Operation(tags = "部门管理",summary = "获取部门精简信息列表", description = "只包含被开启的部门，主要用于前端的下拉选项")
    public CommonResult<List<DeptSimpleRespVO>> getSimpleDeptList() {
        List<DeptDO> list = DeptService.getDeptList(
                new DeptListReqVO().setStatus(CommonStatusEnum.ENABLE.getStatus()));
        return success(BeanUtils.toBean(list, DeptSimpleRespVO.class));
    }

    @GetMapping("/get")
    @Operation(tags = "部门管理",summary = "获得部门信息")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('system:dept:query')")
    public CommonResult<DeptRespVO> getDept(@RequestParam("id") Long id) {
        DeptDO dept = DeptService.getDept(id);
        return success(BeanUtils.toBean(dept, DeptRespVO.class));
    }

}
