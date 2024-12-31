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
import com.jeelowcode.core.framework.config.aspect.enhance.JeeLowCodeAnnoaionAspectjSQL;
import com.jeelowcode.core.framework.entity.EnhanceSqlEntity;
import com.jeelowcode.core.framework.service.IEnhanceSqlService;
import com.jeelowcode.core.framework.utils.Func;
import com.jeelowcode.core.framework.utils.FuncWeb;
import com.jeelowcode.framework.utils.model.global.BaseWebResult;
import com.jeelowcode.core.framework.params.vo.PageVo;
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


@Tag(name = "低代码框架-SQL增强接口")
@RestController
@AllArgsConstructor
@RequestMapping(JeeLowCodeBaseConstant.REQUEST_URL_START+"/enhance/sql")
public class EnhanceSqlController extends BaseController {

    private final IEnhanceSqlService sqlService;

    @PreAuthorize("@ss.hasPermission('jeelowcode:sql')")
    @GetMapping("/detail")
    @ApiOperationSupport(order = 1)
    @Operation(tags = "Sql增强",summary = "详情")
    public BaseWebResult<EnhanceSqlEntity> jsDetail(@RequestParam Long id){
        return BaseWebResult.success(sqlService.getById(id));
    }

    @PreAuthorize("@ss.hasPermission('jeelowcode:sql')")
    @PostMapping("/save")
    @ApiOperationSupport(order = 2)
    @Operation(tags = "Sql增强",summary = "新增")
    public BaseWebResult save(@RequestBody EnhanceSqlEntity model) {
        sqlService.saveEnhanceSql(model);
        return BaseWebResult.success("成功");
    }

    @PreAuthorize("@ss.hasPermission('jeelowcode:sql')")
    @PutMapping("/update")
    @ApiOperationSupport(order = 3)
    @Operation(tags = "Sql增强",summary = "修改")
    public BaseWebResult update(@RequestBody EnhanceSqlEntity model) {
        sqlService.updateEnhanceSql(model);
        return BaseWebResult.success("成功");
    }

    @PreAuthorize("@ss.hasPermission('jeelowcode:sql')")
    @DeleteMapping("/delete")
    @ApiOperationSupport(order = 3)
    @Operation(tags = "Sql增强",summary = "删除(逻辑删除)")
    public BaseWebResult del(@RequestBody List<Long> ids) {
        List<EnhanceSqlEntity> enhanceSqlEntities = sqlService.listByIds(ids);
        enhanceSqlEntities.stream().forEach(
                e ->{
                    JeeLowCodeAnnoaionAspectjSQL.removePlugin(e);
                }
        );
        sqlService.removeBatchByIds(ids);
        return BaseWebResult.success("成功");
    }

    @PreAuthorize("@ss.hasPermission('jeelowcode:sql')")
    @GetMapping("/list")
    @ApiOperationSupport(order = 3)
    @Operation(tags = "Sql增强",summary = "获取列表")
    public BaseWebResult list(EnhanceSqlEntity entity) {
        LambdaQueryWrapper<EnhanceSqlEntity> wrapper=new LambdaQueryWrapper<>();
        wrapper.setEntity(entity);
        wrapper.orderByDesc(EnhanceSqlEntity::getId);
        List<EnhanceSqlEntity> dataList = sqlService.list(wrapper);
        return BaseWebResult.success(dataList);
    }

    @PreAuthorize("@ss.hasPermission('jeelowcode:sql')")
    @GetMapping("/page")
    @ApiOperationSupport(order = 3)
    @Operation(tags = "Sql增强",summary = "获取列表(分页)")
    public BaseWebResult page(EnhanceSqlEntity entity, PageVo pageVo,String column,String order) {
        Page page = FuncWeb.getPage(pageVo.getPageNo(), pageVo.getPageSize());

        LambdaQueryWrapper<EnhanceSqlEntity> wrapper=new LambdaQueryWrapper<>();
        wrapper.setEntity(entity);
        if(Func.isNotEmpty(column)){
            if(Func.equals(order.toUpperCase(),"ASC")){
                wrapper.orderByAsc(EnhanceSqlEntity::getButtonCode);
            }else{
                wrapper.orderByDesc(EnhanceSqlEntity::getButtonCode);
            }
        }else{//默认按id排序
            wrapper.orderByDesc(EnhanceSqlEntity::getId);
        }
        IPage<EnhanceSqlEntity> pages = sqlService.page(page, wrapper);
        return BaseWebResult.success(pages);
    }

}
