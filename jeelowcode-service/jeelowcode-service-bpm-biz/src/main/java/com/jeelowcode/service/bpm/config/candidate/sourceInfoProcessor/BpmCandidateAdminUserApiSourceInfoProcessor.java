package com.jeelowcode.service.bpm.config.candidate.sourceInfoProcessor;

import com.jeelowcode.service.bpm.controller.vo.candidate.BpmTaskCandidateRuleVO;
import com.jeelowcode.service.bpm.enums.definition.BpmTaskAssignRuleTypeEnum;
import com.jeelowcode.service.bpm.config.candidate.BpmCandidateSourceInfo;
import com.jeelowcode.service.bpm.config.candidate.BpmCandidateSourceInfoProcessor;
import com.jeelowcode.service.system.api.AdminUserApi;
import com.jeelowcode.tool.framework.common.util.collection.SetUtils;
import org.flowable.engine.delegate.DelegateExecution;

import javax.annotation.Resource;
import java.util.Set;

public class BpmCandidateAdminUserApiSourceInfoProcessor implements BpmCandidateSourceInfoProcessor {
    @Resource
    private AdminUserApi api;

    @Override
    public Set<Integer> getSupportedTypes() {
        return SetUtils.asSet(BpmTaskAssignRuleTypeEnum.USER.getType());
    }

    @Override
    public void validRuleOptions(Integer type, Set<Long> options) {
        api.validateUserList(options);
    }

    @Override
    public Set<Long> doProcess(BpmCandidateSourceInfo request, BpmTaskCandidateRuleVO rule, DelegateExecution delegateExecution) {
        return rule.getOptions();
    }
}