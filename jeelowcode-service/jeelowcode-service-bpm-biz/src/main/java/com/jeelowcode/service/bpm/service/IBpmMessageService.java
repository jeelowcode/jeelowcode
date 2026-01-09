package com.jeelowcode.service.bpm.service;

import com.jeelowcode.service.bpm.dto.BpmMessageSendWhenProcessInstanceApproveReqDTO;
import com.jeelowcode.service.bpm.dto.BpmMessageSendWhenProcessInstanceRejectReqDTO;
import com.jeelowcode.service.bpm.dto.BpmMessageSendWhenTaskCreatedReqDTO;

import javax.validation.Valid;

/**
 * BPM 消息 Service 接口
 *
 * TODO 芋艿：未来支持消息的可配置；不同的流程，在什么场景下，需要发送什么消息，消息的内容是什么；
 *
 * @author 芋道源码
 */
public interface IBpmMessageService {

    /**
     * 发送流程实例被通过的消息
     *
     * @param reqDTO 发送信息
     */
    void sendMessageWhenProcessInstanceApprove(@Valid BpmMessageSendWhenProcessInstanceApproveReqDTO reqDTO);

    /**
     * 发送流程实例被不通过的消息
     *
     * @param reqDTO 发送信息
     */
    void sendMessageWhenProcessInstanceReject(@Valid BpmMessageSendWhenProcessInstanceRejectReqDTO reqDTO);

    /**
     * 发送任务被分配的消息
     *
     * @param reqDTO 发送信息
     */
    void sendMessageWhenTaskAssigned(@Valid BpmMessageSendWhenTaskCreatedReqDTO reqDTO);

}
