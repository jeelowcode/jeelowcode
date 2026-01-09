package com.jeelowcode.service.system.service.impl;

import cn.hutool.core.util.StrUtil;
import com.jeelowcode.service.system.controller.vo.sms.channel.SmsChannelPageReqVO;
import com.jeelowcode.service.system.controller.vo.sms.channel.SmsChannelSaveReqVO;
import com.jeelowcode.service.system.constant.ErrorCodeConstants;
import com.jeelowcode.service.system.config.framework.sms.core.client.SmsClient;
import com.jeelowcode.service.system.config.framework.sms.core.client.SmsClientFactory;
import com.jeelowcode.service.system.config.framework.sms.core.property.SmsChannelProperties;
import com.jeelowcode.service.system.service.ISmsChannelService;
import com.jeelowcode.service.system.service.ISmsTemplateService;
import com.jeelowcode.tool.framework.common.exception.util.ServiceExceptionUtil;
import com.jeelowcode.tool.framework.common.pojo.PageResult;
import com.jeelowcode.tool.framework.common.util.object.BeanUtils;
import com.jeelowcode.service.system.entity.SmsChannelDO;
import com.jeelowcode.service.system.mapper.SmsChannelMapper;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.Duration;
import java.util.List;

import static com.jeelowcode.tool.framework.common.exception.util.ServiceExceptionUtil.exception;
import static com.jeelowcode.tool.framework.common.util.cache.CacheUtils.buildAsyncReloadingCache;

/**
 * 短信渠道 Service 实现类
 *
 * @author zzf
 */
@Service
@Slf4j
public class SmsChannelServiceImpl implements ISmsChannelService {

    /**
     * {@link SmsClient} 缓存，通过它异步刷新 smsClientFactory
     */
    @Getter
    private final LoadingCache<Long, SmsClient> idClientCache = buildAsyncReloadingCache(Duration.ofSeconds(10L),
            new CacheLoader<Long, SmsClient>() {

                @Override
                public SmsClient load(Long id) {
                    // 查询，然后尝试刷新
                    SmsChannelDO channel = smsChannelMapper.selectById(id);
                    if (channel != null) {
                        SmsChannelProperties properties = BeanUtils.toBean(channel, SmsChannelProperties.class);
                        smsClientFactory.createOrUpdateSmsClient(properties);
                    }
                    return smsClientFactory.getSmsClient(id);
                }

            });

    /**
     * {@link SmsClient} 缓存，通过它异步刷新 smsClientFactory
     */
    @Getter
    private final LoadingCache<String, SmsClient> codeClientCache = buildAsyncReloadingCache(Duration.ofSeconds(60L),
            new CacheLoader<String, SmsClient>() {

                @Override
                public SmsClient load(String code) {
                    // 查询，然后尝试刷新
                    SmsChannelDO channel = smsChannelMapper.selectByCode(code);
                    if (channel != null) {
                        SmsChannelProperties properties = BeanUtils.toBean(channel, SmsChannelProperties.class);
                        smsClientFactory.createOrUpdateSmsClient(properties);
                    }
                    return smsClientFactory.getSmsClient(code);
                }

            });

    @Resource
    private SmsClientFactory smsClientFactory;

    @Resource
    private SmsChannelMapper smsChannelMapper;

    @Resource
    private ISmsTemplateService smsTemplateService;

    @Override
    public Long createSmsChannel(SmsChannelSaveReqVO createReqVO) {
        SmsChannelDO channel = BeanUtils.toBean(createReqVO, SmsChannelDO.class);
        smsChannelMapper.insert(channel);
        return channel.getId();
    }

    @Override
    public void updateSmsChannel(SmsChannelSaveReqVO updateReqVO) {
        // 校验存在
        SmsChannelDO channel = validateSmsChannelExists(updateReqVO.getId());
        // 更新
        SmsChannelDO updateObj = BeanUtils.toBean(updateReqVO, SmsChannelDO.class);
        smsChannelMapper.updateById(updateObj);

        // 清空缓存
        clearCache(updateReqVO.getId(), channel.getCode());
    }

    @Override
    public void deleteSmsChannel(Long id) {
        // 校验存在
        SmsChannelDO channel = validateSmsChannelExists(id);
        // 校验是否有在使用该账号的模版
        if (smsTemplateService.getSmsTemplateCountByChannelId(id) > 0) {
            throw ServiceExceptionUtil.exception(ErrorCodeConstants.SMS_CHANNEL_HAS_CHILDREN);
        }
        // 删除
        smsChannelMapper.deleteById(id);

        // 清空缓存
        clearCache(id, channel.getCode());
    }

    /**
     * 清空指定渠道编号的缓存
     *
     * @param id 渠道编号
     * @param code 渠道编码
     */
    private void clearCache(Long id, String code) {
        idClientCache.invalidate(id);
        if (StrUtil.isNotEmpty(code)) {
            codeClientCache.invalidate(code);
        }
    }

    private SmsChannelDO validateSmsChannelExists(Long id) {
        SmsChannelDO channel = smsChannelMapper.selectById(id);
        if (channel == null) {
            throw ServiceExceptionUtil.exception(ErrorCodeConstants.SMS_CHANNEL_NOT_EXISTS);
        }
        return channel;
    }

    @Override
    public SmsChannelDO getSmsChannel(Long id) {
        return smsChannelMapper.selectById(id);
    }

    @Override
    public List<SmsChannelDO> getSmsChannelList() {
        return smsChannelMapper.selectList();
    }

    @Override
    public PageResult<SmsChannelDO> getSmsChannelPage(SmsChannelPageReqVO pageReqVO) {
        return smsChannelMapper.selectPage(pageReqVO);
    }

    @Override
    public SmsClient getSmsClient(Long id) {
        return idClientCache.getUnchecked(id);
    }

    @Override
    public SmsClient getSmsClient(String code) {
        return codeClientCache.getUnchecked(code);
    }

}
