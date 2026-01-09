package com.jeelowcode.service.system.mapper;

import com.jeelowcode.service.system.controller.vo.dept.post.PostPageReqVO;
import com.jeelowcode.service.system.entity.PostDO;
import com.jeelowcode.tool.framework.common.pojo.PageResult;
import com.jeelowcode.tool.framework.mybatis.core.mapper.BaseMapperX;
import com.jeelowcode.tool.framework.mybatis.core.query.LambdaQueryWrapperX;
import org.apache.ibatis.annotations.Mapper;

import java.util.Collection;
import java.util.List;

@Mapper
public interface PostMapper extends BaseMapperX<PostDO> {

    default List<PostDO> selectList(Collection<Long> ids, Collection<Integer> statuses) {
        return selectList(new LambdaQueryWrapperX<PostDO>()
                .inIfPresent(PostDO::getId, ids)
                .inIfPresent(PostDO::getStatus, statuses));
    }

    default PageResult<PostDO> selectPage(PostPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<PostDO>()
                .likeIfPresent(PostDO::getCode, reqVO.getCode())
                .likeIfPresent(PostDO::getName, reqVO.getName())
                .eqIfPresent(PostDO::getStatus, reqVO.getStatus())
                .orderByDesc(PostDO::getId));
    }

    default PostDO selectByName(String name) {
        return selectOne(PostDO::getName, name);
    }

    default PostDO selectByCode(String code) {
        return selectOne(PostDO::getCode, code);
    }

}
