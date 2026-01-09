package com.jeelowcode.service.system.api;

import com.jeelowcode.service.system.dto.SocialUserBindReqDTO;
import com.jeelowcode.service.system.dto.SocialUserRespDTO;
import com.jeelowcode.service.system.dto.SocialUserUnbindReqDTO;
import com.jeelowcode.service.system.service.ISocialUserService;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;

/**
 * 社交用户的 API 实现类
 *
 * @author 芋道源码
 */
@Service
@Validated
public class SocialUserApiImpl implements SocialUserApi {

    @Resource
    private ISocialUserService socialUserService;

    @Override
    public String bindSocialUser(SocialUserBindReqDTO reqDTO) {
        return socialUserService.bindSocialUser(reqDTO);
    }

    @Override
    public void unbindSocialUser(SocialUserUnbindReqDTO reqDTO) {
        socialUserService.unbindSocialUser(reqDTO.getUserId(), reqDTO.getUserType(),
                reqDTO.getSocialType(), reqDTO.getOpenid());
    }

    @Override
    public SocialUserRespDTO getSocialUserByUserId(Integer userType, Long userId, Integer socialType) {
        return socialUserService.getSocialUserByUserId(userType, userId, socialType);
    }

    @Override
    public SocialUserRespDTO getSocialUserByCode(Integer userType, Integer socialType, String code, String state) {
       return socialUserService.getSocialUserByCode(userType, socialType, code, state);
    }

}
