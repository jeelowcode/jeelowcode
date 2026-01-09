package com.jeelowcode.service.system.config.mq.consumer.mail;


import com.jeelowcode.service.system.config.mq.message.mail.MailSendMessage;
import com.jeelowcode.service.system.service.IMailSendService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * 针对 {@link MailSendMessage} 的消费者
 *
 * @author 芋道源码
 */
@Component
@Slf4j
public class MailSendConsumer {

    @Resource
    private IMailSendService mailSendService;

    @EventListener
    @Async // Spring Event 默认在 Producer 发送的线程，通过 @Async 实现异步
    public void onMessage(MailSendMessage message) {
        log.info("[onMessage][消息内容({})]", message);
        mailSendService.doSendMail(message);
    }

}
