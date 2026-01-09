package com.jeelowcode.service.system.service.impl;

import cn.hutool.core.util.IdUtil;
import com.jeelowcode.service.system.constant.ErrorCodeConstants;
import com.jeelowcode.service.system.service.IOAuth2CodeService;
import com.jeelowcode.tool.framework.common.exception.util.ServiceExceptionUtil;
import com.jeelowcode.tool.framework.common.util.date.DateUtils;
import com.jeelowcode.service.system.entity.OAuth2CodeDO;
import com.jeelowcode.service.system.mapper.OAuth2CodeMapper;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;

import static com.jeelowcode.tool.framework.common.exception.util.ServiceExceptionUtil.exception;

/**
 * OAuth2.0 授权码 Service 实现类
 *
 * @author 芋道源码
 */
@Service
@Validated
public class OAuth2CodeServiceImpl implements IOAuth2CodeService {

    /**
     * 授权码的过期时间，默认 5 分钟
     */
    private static final Integer TIMEOUT = 5 * 60;

    @Resource
    private OAuth2CodeMapper oauth2CodeMapper;

    @Override
    public OAuth2CodeDO createAuthorizationCode(Long userId, Integer userType, String clientId,
                                                List<String> scopes, String redirectUri, String state) {
        OAuth2CodeDO codeDO = new OAuth2CodeDO().setCode(generateCode())
                .setUserId(userId).setUserType(userType)
                .setClientId(clientId).setScopes(scopes)
                .setExpiresTime(LocalDateTime.now().plusSeconds(TIMEOUT))
                .setRedirectUri(redirectUri).setState(state);
        oauth2CodeMapper.insert(codeDO);
        return codeDO;
    }

    @Override
    public OAuth2CodeDO consumeAuthorizationCode(String code) {
        OAuth2CodeDO codeDO = oauth2CodeMapper.selectByCode(code);
        if (codeDO == null) {
            throw ServiceExceptionUtil.exception(ErrorCodeConstants.OAUTH2_CODE_NOT_EXISTS);
        }
        if (DateUtils.isExpired(codeDO.getExpiresTime())) {
            throw ServiceExceptionUtil.exception(ErrorCodeConstants.OAUTH2_CODE_EXPIRE);
        }
        oauth2CodeMapper.deleteById(codeDO.getId());
        return codeDO;
    }

    private static String generateCode() {
        return IdUtil.fastSimpleUUID();
    }

}
