package com.jeelowcode.service.infra.controller;

import cn.hutool.core.io.IoUtil;
import cn.hutool.core.util.ZipUtil;
import com.jeelowcode.service.infra.controller.vo.codegen.CodegenCreateListReqVO;
import com.jeelowcode.service.infra.controller.vo.codegen.CodegenDetailRespVO;
import com.jeelowcode.service.infra.controller.vo.codegen.CodegenPreviewRespVO;
import com.jeelowcode.service.infra.controller.vo.codegen.CodegenUpdateReqVO;
import com.jeelowcode.service.infra.controller.vo.codegen.table.CodegenTablePageReqVO;
import com.jeelowcode.service.infra.controller.vo.codegen.table.CodegenTableRespVO;
import com.jeelowcode.service.infra.controller.vo.codegen.table.DatabaseTableRespVO;
import com.jeelowcode.service.infra.service.ICodegenService;
import com.jeelowcode.tool.framework.common.pojo.CommonResult;
import com.jeelowcode.tool.framework.common.pojo.PageResult;
import com.jeelowcode.tool.framework.common.util.object.BeanUtils;
import com.jeelowcode.tool.framework.common.util.servlet.ServletUtils;
import com.jeelowcode.service.infra.config.convert.codegen.CodegenConvert;
import com.jeelowcode.service.infra.entity.CodegenColumnDO;
import com.jeelowcode.service.infra.entity.CodegenTableDO;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import static com.jeelowcode.tool.framework.common.pojo.CommonResult.success;
import static com.jeelowcode.tool.framework.security.core.util.SecurityFrameworkUtils.getLoginUserId;

@Tag(name = "管理后台 - 代码生成器")
@RestController
@RequestMapping("/infra/codegen")
@Validated
public class CodegenController {

    @Resource
    private ICodegenService codegenService;

    @GetMapping("/db/table/list")
    @Operation(tags = "代码生成器",summary = "获得数据库自带的表定义列表", description = "会过滤掉已经导入 Codegen 的表")
    @Parameters({
            @Parameter(name = "dataSourceConfigId", description = "数据源配置的编号", required = true, example = "1"),
            @Parameter(name = "name", description = "表名，模糊匹配", example = "jeelowcode"),
            @Parameter(name = "comment", description = "描述，模糊匹配", example = "芋道")
    })
    @PreAuthorize("@ss.hasPermission('infra:codegen:query')")
    public CommonResult<List<DatabaseTableRespVO>> getDatabaseTableList(
            @RequestParam(value = "dataSourceConfigId") Long dataSourceConfigId,
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "comment", required = false) String comment) {
        return success(codegenService.getDatabaseTableList(dataSourceConfigId, name, comment));
    }

    @GetMapping("/table/list")
    @Operation(tags = "代码生成器",summary = "获得表定义列表")
    @Parameter(name = "dataSourceConfigId", description = "数据源配置的编号", required = true, example = "1")
    @PreAuthorize("@ss.hasPermission('infra:codegen:query')")
    public CommonResult<List<CodegenTableRespVO>> getCodegenTableList(@RequestParam(value = "dataSourceConfigId") Long dataSourceConfigId) {
        List<CodegenTableDO> list = codegenService.getCodegenTableList(dataSourceConfigId);
        return success(BeanUtils.toBean(list, CodegenTableRespVO.class));
    }

    @GetMapping("/table/page")
    @Operation(tags = "代码生成器",summary = "获得表定义分页")
    @PreAuthorize("@ss.hasPermission('infra:codegen:query')")
    public CommonResult<PageResult<CodegenTableRespVO>> getCodegenTablePage(@Valid CodegenTablePageReqVO pageReqVO) {
        PageResult<CodegenTableDO> pageResult = codegenService.getCodegenTablePage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, CodegenTableRespVO.class));
    }

    @GetMapping("/detail")
    @Operation(tags = "代码生成器",summary = "获得表和字段的明细")
    @Parameter(name = "tableId", description = "表编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('infra:codegen:query')")
    public CommonResult<CodegenDetailRespVO> getCodegenDetail(@RequestParam("tableId") Long tableId) {
        CodegenTableDO table = codegenService.getCodegenTable(tableId);
        List<CodegenColumnDO> columns = codegenService.getCodegenColumnListByTableId(tableId);
        // 拼装返回
        return success(CodegenConvert.INSTANCE.convert(table, columns));
    }

    @Operation(tags = "代码生成器",summary = "基于数据库的表结构，创建代码生成器的表和字段定义")
    @PostMapping("/create-list")
    @PreAuthorize("@ss.hasPermission('infra:codegen:create')")
    public CommonResult<List<Long>> createCodegenList(@Valid @RequestBody CodegenCreateListReqVO reqVO) {
        return success(codegenService.createCodegenList(getLoginUserId(), reqVO));
    }

    @Operation(tags = "代码生成器",summary = "更新数据库的表和字段定义")
    @PutMapping("/update")
    @PreAuthorize("@ss.hasPermission('infra:codegen:update')")
    public CommonResult<Boolean> updateCodegen(@Valid @RequestBody CodegenUpdateReqVO updateReqVO) {
        codegenService.updateCodegen(updateReqVO);
        return success(true);
    }

    @Operation(tags = "代码生成器",summary = "基于数据库的表结构，同步数据库的表和字段定义")
    @PutMapping("/sync-from-db")
    @Parameter(name = "tableId", description = "表编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('infra:codegen:update')")
    public CommonResult<Boolean> syncCodegenFromDB(@RequestParam("tableId") Long tableId) {
        codegenService.syncCodegenFromDB(tableId);
        return success(true);
    }

    @Operation(tags = "代码生成器",summary = "删除数据库的表和字段定义")
    @DeleteMapping("/delete")
    @Parameter(name = "tableId", description = "表编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('infra:codegen:delete')")
    public CommonResult<Boolean> deleteCodegen(@RequestParam("tableId") Long tableId) {
        codegenService.deleteCodegen(tableId);
        return success(true);
    }

    @Operation(tags = "代码生成器",summary = "预览生成代码")
    @GetMapping("/preview")
    @Parameter(name = "tableId", description = "表编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('infra:codegen:preview')")
    public CommonResult<List<CodegenPreviewRespVO>> previewCodegen(@RequestParam("tableId") Long tableId) {
        Map<String, String> codes = codegenService.generationCodes(tableId);
        return success(CodegenConvert.INSTANCE.convert(codes));
    }

    @Operation(tags = "代码生成器",summary = "下载生成代码")
    @GetMapping("/download")
    @Parameter(name = "tableId", description = "表编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('infra:codegen:download')")
    public void downloadCodegen(@RequestParam("tableId") Long tableId,
                                HttpServletResponse response) throws IOException {
        // 生成代码
        Map<String, String> codes = codegenService.generationCodes(tableId);
        // 构建 zip 包
        String[] paths = codes.keySet().toArray(new String[0]);
        ByteArrayInputStream[] ins = codes.values().stream().map(IoUtil::toUtf8Stream).toArray(ByteArrayInputStream[]::new);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ZipUtil.zip(outputStream, paths, ins);
        // 输出
        ServletUtils.writeAttachment(response, "codegen.zip", outputStream.toByteArray());
    }

}
