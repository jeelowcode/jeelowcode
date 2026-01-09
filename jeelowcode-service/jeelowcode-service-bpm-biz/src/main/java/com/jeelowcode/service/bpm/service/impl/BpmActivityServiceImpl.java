package com.jeelowcode.service.bpm.service.impl;


import com.jeelowcode.service.bpm.controller.vo.activity.BpmActivityRespVO;
import com.jeelowcode.service.bpm.config.convert.task.BpmActivityConvert;
import com.jeelowcode.service.bpm.service.IBpmActivityService;
import lombok.extern.slf4j.Slf4j;
import org.flowable.engine.HistoryService;
import org.flowable.engine.history.HistoricActivityInstance;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;
import java.util.List;


/**
 * BPM 活动实例 Service 实现类
 *
 * @author 芋道源码
 */
@Service
@Slf4j
@Validated
public class BpmActivityServiceImpl implements IBpmActivityService {

    @Resource
    private HistoryService historyService;

    @Override
    public List<BpmActivityRespVO> getActivityListByProcessInstanceId(String processInstanceId) {
        List<HistoricActivityInstance> activityList = historyService.createHistoricActivityInstanceQuery()
                .processInstanceId(processInstanceId).list();
        return BpmActivityConvert.INSTANCE.convertList(activityList);
    }

    @Override
    public List<HistoricActivityInstance> getHistoricActivityListByExecutionId(String executionId) {
        return historyService.createHistoricActivityInstanceQuery().executionId(executionId).list();
    }

}
