package com.jeelowcode.service.system.api;

import com.jeelowcode.service.system.dto.NotifySendSingleToUserReqDTO;
import com.jeelowcode.service.system.service.INotifySendService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 站内信发送 API 实现类
 *
 * @author xrcoder
 */
@Service
public class NotifyMessageSendApiImpl implements NotifyMessageSendApi {

    @Resource
    private INotifySendService notifySendService;

    @Override
    public Long sendSingleMessageToAdmin(NotifySendSingleToUserReqDTO reqDTO) {
        return notifySendService.sendSingleNotifyToAdmin(reqDTO.getUserId(),
                reqDTO.getTemplateCode(), reqDTO.getTemplateParams());
    }

    @Override
    public Long sendSingleMessageToMember(NotifySendSingleToUserReqDTO reqDTO) {
        return notifySendService.sendSingleNotifyToMember(reqDTO.getUserId(),
                reqDTO.getTemplateCode(), reqDTO.getTemplateParams());
    }

}
