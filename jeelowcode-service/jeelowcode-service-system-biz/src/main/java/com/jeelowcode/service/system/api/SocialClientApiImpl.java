package com.jeelowcode.service.system.api;

import cn.binarywang.wx.miniapp.bean.WxMaPhoneNumberInfo;
import com.jeelowcode.service.system.dto.SocialWxJsapiSignatureRespDTO;
import com.jeelowcode.service.system.dto.SocialWxPhoneNumberInfoRespDTO;
import com.jeelowcode.tool.framework.common.util.object.BeanUtils;
import me.chanjar.weixin.common.bean.WxJsapiSignature;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;

/**
 * 社交应用的 API 实现类
 *
 * @author 芋道源码
 */
@Service
@Validated
public class SocialClientApiImpl implements SocialClientApi {

    @Resource
    private com.jeelowcode.service.system.service.ISocialClientService ISocialClientService;

    @Override
    public String getAuthorizeUrl(Integer socialType, Integer userType, String redirectUri) {
        return ISocialClientService.getAuthorizeUrl(socialType, userType, redirectUri);
    }

    @Override
    public SocialWxJsapiSignatureRespDTO createWxMpJsapiSignature(Integer userType, String url) {
        WxJsapiSignature signature = ISocialClientService.createWxMpJsapiSignature(userType, url);
        return BeanUtils.toBean(signature, SocialWxJsapiSignatureRespDTO.class);
    }

    @Override
    public SocialWxPhoneNumberInfoRespDTO getWxMaPhoneNumberInfo(Integer userType, String phoneCode) {
        WxMaPhoneNumberInfo info = ISocialClientService.getWxMaPhoneNumberInfo(userType, phoneCode);
        return BeanUtils.toBean(info, SocialWxPhoneNumberInfoRespDTO.class);
    }

}
