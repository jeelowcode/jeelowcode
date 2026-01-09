package com.jeelowcode.service.system.service.impl;

import com.jeelowcode.service.system.constant.ErrorCodeConstants;
import com.jeelowcode.service.system.service.INotifyMessageService;
import com.jeelowcode.service.system.service.INotifySendService;
import com.jeelowcode.service.system.service.INotifyTemplateService;
import com.jeelowcode.tool.framework.common.enums.CommonStatusEnum;
import com.jeelowcode.tool.framework.common.enums.UserTypeEnum;
import com.jeelowcode.service.system.entity.NotifyTemplateDO;
import com.google.common.annotations.VisibleForTesting;
import com.jeelowcode.tool.framework.common.exception.util.ServiceExceptionUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;
import java.util.Map;
import java.util.Objects;

import static com.jeelowcode.tool.framework.common.exception.util.ServiceExceptionUtil.exception;

/**
 * 站内信发送 Service 实现类
 *
 * @author xrcoder
 */
@Service
@Validated
@Slf4j
public class NotifySendServiceImpl implements INotifySendService {

    @Resource
    private INotifyTemplateService notifyTemplateService;

    @Resource
    private INotifyMessageService notifyMessageService;

    @Override
    public Long sendSingleNotifyToAdmin(Long userId, String templateCode, Map<String, Object> templateParams) {
        return sendSingleNotify(userId, UserTypeEnum.ADMIN.getValue(), templateCode, templateParams);
    }

    @Override
    public Long sendSingleNotifyToMember(Long userId, String templateCode, Map<String, Object> templateParams) {
        return sendSingleNotify(userId, UserTypeEnum.MEMBER.getValue(), templateCode, templateParams);
    }

    @Override
    public Long sendSingleNotify(Long userId, Integer userType, String templateCode, Map<String, Object> templateParams) {
        // 校验模版
        NotifyTemplateDO template = validateNotifyTemplate(templateCode);
        if (Objects.equals(template.getStatus(), CommonStatusEnum.DISABLE.getStatus())) {
            log.info("[sendSingleNotify][模版({})已经关闭，无法给用户({}/{})发送]", templateCode, userId, userType);
            return null;
        }
        // 校验参数
        validateTemplateParams(template, templateParams);

        // 发送站内信
        String content = notifyTemplateService.formatNotifyTemplateContent(template.getContent(), templateParams);
        return notifyMessageService.createNotifyMessage(userId, userType, template, content, templateParams);
    }

    @VisibleForTesting
    public NotifyTemplateDO validateNotifyTemplate(String templateCode) {
        // 获得站内信模板。考虑到效率，从缓存中获取
        NotifyTemplateDO template = notifyTemplateService.getNotifyTemplateByCodeFromCache(templateCode);
        // 站内信模板不存在
        if (template == null) {
            throw ServiceExceptionUtil.exception(ErrorCodeConstants.NOTICE_NOT_FOUND);
        }
        return template;
    }

    /**
     * 校验站内信模版参数是否确实
     *
     * @param template 邮箱模板
     * @param templateParams 参数列表
     */
    @VisibleForTesting
    public void validateTemplateParams(NotifyTemplateDO template, Map<String, Object> templateParams) {
        template.getParams().forEach(key -> {
            Object value = templateParams.get(key);
            if (value == null) {
                throw exception(ErrorCodeConstants.NOTIFY_SEND_TEMPLATE_PARAM_MISS, key);
            }
        });
    }
}
