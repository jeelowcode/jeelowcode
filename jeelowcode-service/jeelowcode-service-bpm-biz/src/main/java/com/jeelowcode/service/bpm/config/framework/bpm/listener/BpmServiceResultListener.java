package com.jeelowcode.service.bpm.config.framework.bpm.listener;

import cn.hutool.core.util.StrUtil;

import com.jeelowcode.service.bpm.api.BpmResultListenerApi;
import com.jeelowcode.service.bpm.dto.BpmResultListenerRespDTO;
import com.jeelowcode.service.bpm.config.framework.bpm.core.event.BpmProcessInstanceResultEvent;
import com.jeelowcode.tool.framework.common.util.object.BeanUtils;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

// TODO @芋艿：后续改成支持 RPC
/**
 * 业务流程结果监听器实现类
 *
 * @author HUIHUI
 */
@Component
public class BpmServiceResultListener implements ApplicationListener<BpmProcessInstanceResultEvent> {

    @Resource
    private List<BpmResultListenerApi> bpmResultListenerApis;

    @Override
    public final void onApplicationEvent(BpmProcessInstanceResultEvent event) {
        bpmResultListenerApis.forEach(bpmResultListenerApi -> {
            if (!StrUtil.equals(event.getProcessDefinitionKey(), bpmResultListenerApi.getProcessDefinitionKey())) {
                return;
            }
            bpmResultListenerApi.onEvent(BeanUtils.toBean(event, BpmResultListenerRespDTO.class));
        });
    }

}
