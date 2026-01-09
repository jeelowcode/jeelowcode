package com.jeelowcode.service.infra.api.impl;
import com.jeelowcode.service.infra.api.WebSocketSenderApi;
import com.jeelowcode.tool.framework.websocket.core.sender.WebSocketMessageSender;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * WebSocket 发送器的 API 实现类
 *
 * @author 芋道源码
 */
@Component
public class WebSocketSenderApiImpl implements WebSocketSenderApi {

    @Lazy
    @Resource
    private WebSocketMessageSender webSocketMessageSender;

    @Override
    public void send(Integer userType, Long userId, String messageType, String messageContent) {
        webSocketMessageSender.send(userType, userId, messageType, messageContent);
    }

    @Override
    public void send(Integer userType, String messageType, String messageContent) {
        webSocketMessageSender.send(userType, messageType, messageContent);
    }

    @Override
    public void send(String sessionId, String messageType, String messageContent) {
        webSocketMessageSender.send(sessionId, messageType, messageContent);
    }

}
