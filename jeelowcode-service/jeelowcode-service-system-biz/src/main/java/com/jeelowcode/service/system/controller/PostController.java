package com.jeelowcode.service.system.controller;

import com.jeelowcode.service.system.controller.vo.dept.post.PostPageReqVO;
import com.jeelowcode.service.system.controller.vo.dept.post.PostRespVO;
import com.jeelowcode.tool.framework.common.enums.CommonStatusEnum;
import com.jeelowcode.tool.framework.common.pojo.CommonResult;
import com.jeelowcode.tool.framework.common.pojo.PageParam;
import com.jeelowcode.tool.framework.common.pojo.PageResult;
import com.jeelowcode.tool.framework.common.util.object.BeanUtils;
import com.jeelowcode.tool.framework.excel.core.util.ExcelUtils;
import com.jeelowcode.tool.framework.operatelog.core.annotations.OperateLog;
import com.jeelowcode.service.system.controller.vo.dept.post.PostSaveReqVO;
import com.jeelowcode.service.system.controller.vo.dept.post.PostSimpleRespVO;
import com.jeelowcode.service.system.entity.PostDO;
import com.jeelowcode.service.system.service.IPostService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import static com.jeelowcode.tool.framework.common.pojo.CommonResult.success;
import static com.jeelowcode.tool.framework.operatelog.core.enums.OperateTypeEnum.EXPORT;

@Tag(name = "管理后台 - 岗位")
@RestController
@RequestMapping("/system/post")
@Validated
public class PostController {

    @Resource
    private IPostService postService;

    @PostMapping("/create")
    @Operation(tags = "岗位管理",summary = "创建岗位")
    @PreAuthorize("@ss.hasPermission('system:post:create')")
    public CommonResult<Long> createPost(@Valid @RequestBody PostSaveReqVO createReqVO) {
        Long postId = postService.createPost(createReqVO);
        return success(postId);
    }

    @PutMapping("/update")
    @Operation(tags = "岗位管理",summary = "修改岗位")
    @PreAuthorize("@ss.hasPermission('system:post:update')")
    public CommonResult<Boolean> updatePost(@Valid @RequestBody PostSaveReqVO updateReqVO) {
        postService.updatePost(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(tags = "岗位管理",summary = "删除岗位")
    @PreAuthorize("@ss.hasPermission('system:post:delete')")
    public CommonResult<Boolean> deletePost(@RequestParam("id") Long id) {
        postService.deletePost(id);
        return success(true);
    }

    @GetMapping(value = "/get")
    @Operation(tags = "岗位管理",summary = "获得岗位信息")
    @Parameter(name = "id", description = "岗位编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('system:post:query')")
    public CommonResult<PostRespVO> getPost(@RequestParam("id") Long id) {
        PostDO post = postService.getPost(id);
        return success(BeanUtils.toBean(post, PostRespVO.class));
    }

    @GetMapping(value = {"/list-all-simple", "simple-list"})
    @Operation(tags = "岗位管理",summary = "获取岗位全列表", description = "只包含被开启的岗位，主要用于前端的下拉选项")
    public CommonResult<List<PostSimpleRespVO>> getSimplePostList() {
        // 获得岗位列表，只要开启状态的
        List<PostDO> list = postService.getPostList(null, Collections.singleton(CommonStatusEnum.ENABLE.getStatus()));
        // 排序后，返回给前端
        list.sort(Comparator.comparing(PostDO::getSort));
        return success(BeanUtils.toBean(list, PostSimpleRespVO.class));
    }

    @GetMapping("/page")
    @Operation(tags = "岗位管理",summary = "获得岗位分页列表")
    @PreAuthorize("@ss.hasPermission('system:post:query')")
    public CommonResult<PageResult<PostRespVO>> getPostPage(@Validated PostPageReqVO pageReqVO) {
        PageResult<PostDO> pageResult = postService.getPostPage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, PostRespVO.class));
    }

    @GetMapping("/export")
    @Operation(tags = "岗位管理",summary = "岗位管理")
    @PreAuthorize("@ss.hasPermission('system:post:export')")
    @OperateLog(type = EXPORT)
    public void export(HttpServletResponse response, @Validated PostPageReqVO reqVO) throws IOException {
        reqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<PostDO> list = postService.getPostPage(reqVO).getList();
        // 输出
        ExcelUtils.write(response, "岗位数据.xls", "岗位列表", PostRespVO.class,
                BeanUtils.toBean(list, PostRespVO.class));
    }

}
