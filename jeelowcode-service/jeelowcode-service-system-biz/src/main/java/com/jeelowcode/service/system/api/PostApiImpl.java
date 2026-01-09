package com.jeelowcode.service.system.api;

import com.jeelowcode.service.system.dto.PostRespDTO;
import com.jeelowcode.service.system.entity.PostDO;
import com.jeelowcode.service.system.service.IPostService;
import com.jeelowcode.tool.framework.common.util.object.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.List;

/**
 * 岗位 API 实现类
 *
 * @author 芋道源码
 */
@Service
public class PostApiImpl implements PostApi {

    @Resource
    private IPostService postService;

    @Override
    public void validPostList(Collection<Long> ids) {
        postService.validatePostList(ids);
    }

    @Override
    public List<PostRespDTO> getPostList(Collection<Long> ids) {
        List<PostDO> list = postService.getPostList(ids);
        return BeanUtils.toBean(list, PostRespDTO.class);
    }

}
