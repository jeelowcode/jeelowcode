package com.jeelowcode.service.bpm.config.listener;

import com.jeelowcode.service.bpm.config.framework.bpm.core.event.BpmProcessInstanceResultEvent;
import com.jeelowcode.service.bpm.config.framework.bpm.core.event.BpmProcessInstanceResultEventListener;
import com.jeelowcode.service.bpm.service.IBpmOALeaveService;
import com.jeelowcode.service.bpm.service.impl.BpmOALeaveServiceImpl;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * OA 请假单的结果的监听器实现类
 *
 * @author 芋道源码
 */
@Component
public class BpmOALeaveResultListener extends BpmProcessInstanceResultEventListener {

    @Resource
    private IBpmOALeaveService leaveService;

    @Override
    protected String getProcessDefinitionKey() {
        return BpmOALeaveServiceImpl.PROCESS_KEY;
    }

    @Override
    protected void onEvent(BpmProcessInstanceResultEvent event) {
        leaveService.updateLeaveResult(Long.parseLong(event.getBusinessKey()), event.getResult());
    }

}
