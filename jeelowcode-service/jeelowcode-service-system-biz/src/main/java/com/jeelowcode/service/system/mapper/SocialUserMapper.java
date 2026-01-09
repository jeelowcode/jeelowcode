package com.jeelowcode.service.system.mapper;

import com.jeelowcode.service.system.controller.vo.socail.user.SocialUserPageReqVO;
import com.jeelowcode.service.system.entity.SocialUserDO;
import com.jeelowcode.tool.framework.common.pojo.PageResult;
import com.jeelowcode.tool.framework.mybatis.core.mapper.BaseMapperX;
import com.jeelowcode.tool.framework.mybatis.core.query.LambdaQueryWrapperX;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface SocialUserMapper extends BaseMapperX<SocialUserDO> {

    default SocialUserDO selectByTypeAndCodeAnState(Integer type, String code, String state) {
        return selectOne(new LambdaQueryWrapper<SocialUserDO>()
                .eq(SocialUserDO::getType, type)
                .eq(SocialUserDO::getCode, code)
                .eq(SocialUserDO::getState, state));
    }

    default SocialUserDO selectByTypeAndOpenid(Integer type, String openid) {
        return selectOne(new LambdaQueryWrapper<SocialUserDO>()
                .eq(SocialUserDO::getType, type)
                .eq(SocialUserDO::getOpenid, openid));
    }

    default PageResult<SocialUserDO> selectPage(SocialUserPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<SocialUserDO>()
                .eqIfPresent(SocialUserDO::getType, reqVO.getType())
                .likeIfPresent(SocialUserDO::getNickname, reqVO.getNickname())
                .likeIfPresent(SocialUserDO::getOpenid, reqVO.getOpenid())
                .betweenIfPresent(SocialUserDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(SocialUserDO::getId));
    }

}
