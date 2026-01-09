package com.jeelowcode.tool.framework.websocket.config;

import com.jeelowcode.tool.framework.websocket.core.handler.JsonWebSocketMessageHandler;
import com.jeelowcode.tool.framework.websocket.core.listener.WebSocketMessageListener;
import com.jeelowcode.tool.framework.websocket.core.security.LoginUserHandshakeInterceptor;
import com.jeelowcode.tool.framework.websocket.core.sender.local.LocalWebSocketMessageSender;
import com.jeelowcode.tool.framework.websocket.core.session.WebSocketSessionHandlerDecorator;
import com.jeelowcode.tool.framework.websocket.core.session.WebSocketSessionManager;
import com.jeelowcode.tool.framework.websocket.core.session.WebSocketSessionManagerImpl;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.server.HandshakeInterceptor;

import java.util.List;

/**
 * WebSocket 自动配置
 *
 * @author xingyu4j
 */

@EnableWebSocket // 开启 websocket
@ConditionalOnProperty(prefix = "jeelowcode.websocket", value = "enable", matchIfMissing = true) // 允许使用 jeelowcode.websocket.enable=false 禁用 websocket
@EnableConfigurationProperties(WebSocketProperties.class)
public class WebSocketAutoConfiguration {

    @Bean
    public WebSocketConfigurer webSocketConfigurer(HandshakeInterceptor[] handshakeInterceptors,
                                                   WebSocketHandler webSocketHandler,
                                                   WebSocketProperties webSocketProperties) {
        return registry -> registry
                // 添加 WebSocketHandler
                .addHandler(webSocketHandler, webSocketProperties.getPath())
                .addInterceptors(handshakeInterceptors)
                // 允许跨域，否则前端连接会直接断开
                .setAllowedOriginPatterns("*");
    }

    @Bean
    public HandshakeInterceptor handshakeInterceptor() {
        return new LoginUserHandshakeInterceptor();
    }

    @Bean
    public WebSocketHandler webSocketHandler(WebSocketSessionManager sessionManager,
                                             List<? extends WebSocketMessageListener<?>> messageListeners) {
        // 1. 创建 JsonWebSocketMessageHandler 对象，处理消息
        JsonWebSocketMessageHandler messageHandler = new JsonWebSocketMessageHandler(messageListeners);
        // 2. 创建 WebSocketSessionHandlerDecorator 对象，处理连接
        return new WebSocketSessionHandlerDecorator(messageHandler, sessionManager);
    }

    @Bean
    public WebSocketSessionManager webSocketSessionManager() {
        return new WebSocketSessionManagerImpl();
    }

    // ==================== Sender 相关 ====================

    @Configuration
    @ConditionalOnProperty(prefix = "jeelowcode.websocket", name = "sender-type", havingValue = "local", matchIfMissing = true)
    public class LocalWebSocketMessageSenderConfiguration {
        @Bean
        public LocalWebSocketMessageSender localWebSocketMessageSender(WebSocketSessionManager sessionManager) {
            return new LocalWebSocketMessageSender(sessionManager);
        }

    }


}