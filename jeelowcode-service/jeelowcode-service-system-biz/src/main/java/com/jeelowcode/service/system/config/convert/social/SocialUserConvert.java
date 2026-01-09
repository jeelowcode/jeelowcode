package com.jeelowcode.service.system.config.convert.social;
import com.jeelowcode.service.system.dto.SocialUserBindReqDTO;
import com.jeelowcode.service.system.controller.vo.socail.user.SocialUserBindReqVO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface SocialUserConvert {

    SocialUserConvert INSTANCE = Mappers.getMapper(SocialUserConvert.class);

    @Mapping(source = "reqVO.type", target = "socialType")
    SocialUserBindReqDTO convert(Long userId, Integer userType, SocialUserBindReqVO reqVO);

}
