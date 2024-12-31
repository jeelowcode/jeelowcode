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

import com.jeelowcode.framework.constants.FrameErrorCodeConstants;
import com.jeelowcode.framework.global.JeeLowCodeBaseConstant;
import com.jeelowcode.core.framework.entity.FormButtonEntity;
import com.jeelowcode.core.framework.service.IFormButtonService;
import com.jeelowcode.core.framework.utils.FuncWeb;
import com.jeelowcode.core.framework.params.vo.PageVo;
import com.jeelowcode.framework.utils.model.global.BaseWebResult;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Tag(name = "低代码框架-Button增强接口")
@RestController
@AllArgsConstructor
@RequestMapping(JeeLowCodeBaseConstant.REQUEST_URL_START+"/enhance/button")
public class ButtonController extends BaseController {

    private final IFormButtonService buttonService;

    @PreAuthorize("@ss.hasPermission('jeelowcode:button')")
    @GetMapping("/detail")
    @ApiOperationSupport(order = 1)
    @Operation(tags = "Button增强",summary = "Js增强-详情")
    public BaseWebResult<FormButtonEntity> jsDetail(@RequestParam Long id){
        return BaseWebResult.success(buttonService.getById(id));
    }

    @PreAuthorize("@ss.hasPermission('jeelowcode:button')")
    @PostMapping("/save")
    @ApiOperationSupport(order = 2)
    @Operation(tags = "Button增强",summary = "新增")
    public BaseWebResult save(@RequestBody FormButtonEntity model) {
        //编号唯一
        String buttonCode = model.getButtonCode();
        LambdaQueryWrapper<FormButtonEntity> wrapper=new LambdaQueryWrapper<>();
        wrapper.eq(FormButtonEntity::getDbformId,model.getDbformId());
        wrapper.eq(FormButtonEntity::getButtonCode,buttonCode);
        long count = buttonService.count(wrapper);
        if(count>0){
            return BaseWebResult.error(FrameErrorCodeConstants.FRAME_CORE_EXIT);
        }


        buttonService.save(model);
        return BaseWebResult.success("成功");
    }

    @PreAuthorize("@ss.hasPermission('jeelowcode:button')")
    @PutMapping("/update")
    @ApiOperationSupport(order = 3)
    @Operation(tags = "Button增强",summary = "修改")
    public BaseWebResult update(@RequestBody FormButtonEntity model) {
        //编号唯一
        String buttonCode = model.getButtonCode();
        LambdaQueryWrapper<FormButtonEntity> wrapper=new LambdaQueryWrapper<>();
        wrapper.eq(FormButtonEntity::getDbformId,model.getDbformId());
        wrapper.eq(FormButtonEntity::getButtonCode,buttonCode);
        wrapper.ne(FormButtonEntity::getId,model.getId());
        long count = buttonService.count(wrapper);
        if(count>0){
            return BaseWebResult.error(FrameErrorCodeConstants.FRAME_CORE_EXIT);
        }
        
        buttonService.updateById(model);
        return BaseWebResult.success("成功");
    }

    @PreAuthorize("@ss.hasPermission('jeelowcode:button')")
    @DeleteMapping("/delete")
    @ApiOperationSupport(order = 3)
    @Operation(tags = "Button增强",summary = "删除(逻辑删除)")
    public BaseWebResult del(@RequestBody List<Long> ids) {
        buttonService.removeBatchByIds(ids);
        return BaseWebResult.success("成功");
    }

    @PreAuthorize("@ss.hasPermission('jeelowcode:button')")
    @GetMapping("/list")
    @ApiOperationSupport(order = 3)
    @Operation(tags = "Button增强",summary = "获取列表")
    public BaseWebResult list(FormButtonEntity entity) {
        LambdaQueryWrapper<FormButtonEntity> wrapper=new LambdaQueryWrapper<>();
        wrapper.setEntity(entity);
        wrapper.orderByDesc(FormButtonEntity::getId);
        List<FormButtonEntity> dataList = buttonService.list(wrapper);
        return BaseWebResult.success(dataList);
    }

    @PreAuthorize("@ss.hasPermission('jeelowcode:button')")
    @GetMapping("/page")
    @ApiOperationSupport(order = 3)
    @Operation(tags = "Button增强",summary = "获取列表(分页)")
    public BaseWebResult page(FormButtonEntity entity, PageVo pageVo) {
        Page page = FuncWeb.getPage(pageVo.getPageNo(), pageVo.getPageSize());

        LambdaQueryWrapper<FormButtonEntity> wrapper=new LambdaQueryWrapper<>();
        wrapper.setEntity(entity);
        wrapper.orderByDesc(FormButtonEntity::getId);
        IPage<FormButtonEntity> pages = buttonService.page(page, wrapper);
        return BaseWebResult.success(pages);
    }

}
