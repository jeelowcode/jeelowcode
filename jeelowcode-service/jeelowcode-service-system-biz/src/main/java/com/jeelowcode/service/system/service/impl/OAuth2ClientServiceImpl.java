package com.jeelowcode.service.system.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.extra.spring.SpringUtil;
import com.jeelowcode.service.system.controller.vo.oauth2.client.OAuth2ClientPageReqVO;
import com.jeelowcode.service.system.controller.vo.oauth2.client.OAuth2ClientSaveReqVO;
import com.jeelowcode.service.system.constant.ErrorCodeConstants;
import com.jeelowcode.service.system.service.IAuth2ClientService;
import com.jeelowcode.tool.framework.common.enums.CommonStatusEnum;
import com.jeelowcode.tool.framework.common.exception.util.ServiceExceptionUtil;
import com.jeelowcode.tool.framework.common.pojo.PageResult;
import com.jeelowcode.tool.framework.common.util.object.BeanUtils;
import com.jeelowcode.tool.framework.common.util.string.StrUtils;
import com.jeelowcode.service.system.entity.OAuth2ClientDO;
import com.jeelowcode.service.system.mapper.OAuth2ClientMapper;
import com.jeelowcode.service.system.config.redis.RedisKeyConstants;
import com.google.common.annotations.VisibleForTesting;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;
import java.util.Collection;

import static com.jeelowcode.tool.framework.common.exception.util.ServiceExceptionUtil.exception;

/**
 * OAuth2.0 Client Service 实现类
 *
 * @author 芋道源码
 */
@Service
@Validated
@Slf4j
public class OAuth2ClientServiceImpl implements IAuth2ClientService {

    @Resource
    private OAuth2ClientMapper oauth2ClientMapper;

    @Override
    public Long createOAuth2Client(OAuth2ClientSaveReqVO createReqVO) {
        validateClientIdExists(null, createReqVO.getClientId());
        // 插入
        OAuth2ClientDO client = BeanUtils.toBean(createReqVO, OAuth2ClientDO.class);
        oauth2ClientMapper.insert(client);
        return client.getId();
    }

    @Override
    @CacheEvict(cacheNames = RedisKeyConstants.OAUTH_CLIENT,
            allEntries = true) // allEntries 清空所有缓存，因为可能修改到 clientId 字段，不好清理
    public void updateOAuth2Client(OAuth2ClientSaveReqVO updateReqVO) {
        // 校验存在
        validateOAuth2ClientExists(updateReqVO.getId());
        // 校验 Client 未被占用
        validateClientIdExists(updateReqVO.getId(), updateReqVO.getClientId());

        // 更新
        OAuth2ClientDO updateObj = BeanUtils.toBean(updateReqVO, OAuth2ClientDO.class);
        oauth2ClientMapper.updateById(updateObj);
    }

    @Override
    @CacheEvict(cacheNames = RedisKeyConstants.OAUTH_CLIENT,
            allEntries = true) // allEntries 清空所有缓存，因为 id 不是直接的缓存 key，不好清理
    public void deleteOAuth2Client(Long id) {
        // 校验存在
        validateOAuth2ClientExists(id);
        // 删除
        oauth2ClientMapper.deleteById(id);
    }

    private void validateOAuth2ClientExists(Long id) {
        if (oauth2ClientMapper.selectById(id) == null) {
            throw ServiceExceptionUtil.exception(ErrorCodeConstants.OAUTH2_CLIENT_NOT_EXISTS);
        }
    }

    @VisibleForTesting
    void validateClientIdExists(Long id, String clientId) {
        OAuth2ClientDO client = oauth2ClientMapper.selectByClientId(clientId);
        if (client == null) {
            return;
        }
        // 如果 id 为空，说明不用比较是否为相同 id 的客户端
        if (id == null) {
            throw ServiceExceptionUtil.exception(ErrorCodeConstants.OAUTH2_CLIENT_EXISTS);
        }
        if (!client.getId().equals(id)) {
            throw ServiceExceptionUtil.exception(ErrorCodeConstants.OAUTH2_CLIENT_EXISTS);
        }
    }

    @Override
    public OAuth2ClientDO getOAuth2Client(Long id) {
        return oauth2ClientMapper.selectById(id);
    }

    @Override
    @Cacheable(cacheNames = RedisKeyConstants.OAUTH_CLIENT, key = "#clientId",
            unless = "#result == null")
    public OAuth2ClientDO getOAuth2ClientFromCache(String clientId) {
        return oauth2ClientMapper.selectByClientId(clientId);
    }

    @Override
    public PageResult<OAuth2ClientDO> getOAuth2ClientPage(OAuth2ClientPageReqVO pageReqVO) {
        return oauth2ClientMapper.selectPage(pageReqVO);
    }

    @Override
    public OAuth2ClientDO validOAuthClientFromCache(String clientId, String clientSecret, String authorizedGrantType,
                                                    Collection<String> scopes, String redirectUri) {
        // 校验客户端存在、且开启
        OAuth2ClientDO client = getSelf().getOAuth2ClientFromCache(clientId);
        if (client == null) {
            throw ServiceExceptionUtil.exception(ErrorCodeConstants.OAUTH2_CLIENT_NOT_EXISTS);
        }
        if (CommonStatusEnum.isDisable(client.getStatus())) {
            throw ServiceExceptionUtil.exception(ErrorCodeConstants.OAUTH2_CLIENT_DISABLE);
        }

        // 校验客户端密钥
        if (StrUtil.isNotEmpty(clientSecret) && ObjectUtil.notEqual(client.getSecret(), clientSecret)) {
            throw ServiceExceptionUtil.exception(ErrorCodeConstants.OAUTH2_CLIENT_CLIENT_SECRET_ERROR);
        }
        // 校验授权方式
        if (StrUtil.isNotEmpty(authorizedGrantType) && !CollUtil.contains(client.getAuthorizedGrantTypes(), authorizedGrantType)) {
            throw ServiceExceptionUtil.exception(ErrorCodeConstants.OAUTH2_CLIENT_AUTHORIZED_GRANT_TYPE_NOT_EXISTS);
        }
        // 校验授权范围
        if (CollUtil.isNotEmpty(scopes) && !CollUtil.containsAll(client.getScopes(), scopes)) {
            throw ServiceExceptionUtil.exception(ErrorCodeConstants.OAUTH2_CLIENT_SCOPE_OVER);
        }
        // 校验回调地址
        if (StrUtil.isNotEmpty(redirectUri) && !StrUtils.startWithAny(redirectUri, client.getRedirectUris())) {
            throw ServiceExceptionUtil.exception(ErrorCodeConstants.OAUTH2_CLIENT_REDIRECT_URI_NOT_MATCH, redirectUri);
        }
        return client;
    }

    /**
     * 获得自身的代理对象，解决 AOP 生效问题
     *
     * @return 自己
     */
    private OAuth2ClientServiceImpl getSelf() {
        return SpringUtil.getBean(getClass());
    }

}
