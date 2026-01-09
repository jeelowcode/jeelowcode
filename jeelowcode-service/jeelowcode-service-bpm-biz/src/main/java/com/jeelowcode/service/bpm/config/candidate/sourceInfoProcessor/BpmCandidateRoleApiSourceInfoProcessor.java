package com.jeelowcode.service.bpm.config.candidate.sourceInfoProcessor;
import com.jeelowcode.service.bpm.controller.vo.candidate.BpmTaskCandidateRuleVO;
import com.jeelowcode.service.bpm.enums.definition.BpmTaskAssignRuleTypeEnum;
import com.jeelowcode.service.bpm.config.candidate.BpmCandidateSourceInfo;
import com.jeelowcode.service.bpm.config.candidate.BpmCandidateSourceInfoProcessor;
import com.jeelowcode.service.system.api.PermissionApi;
import com.jeelowcode.service.system.api.RoleApi;
import com.jeelowcode.tool.framework.common.util.collection.SetUtils;
import org.flowable.engine.delegate.DelegateExecution;

import javax.annotation.Resource;
import java.util.Set;

public class BpmCandidateRoleApiSourceInfoProcessor implements BpmCandidateSourceInfoProcessor {
    @Resource
    private RoleApi api;

    @Resource
    private PermissionApi permissionApi;

    @Override
    public Set<Integer> getSupportedTypes() {
        return SetUtils.asSet(BpmTaskAssignRuleTypeEnum.ROLE.getType());
    }

    @Override
    public void validRuleOptions(Integer type, Set<Long> options) {
        api.validRoleList(options);
    }

    @Override
    public Set<Long> doProcess(BpmCandidateSourceInfo request, BpmTaskCandidateRuleVO rule, DelegateExecution delegateExecution) {
        return permissionApi.getUserRoleIdListByRoleIds(rule.getOptions());
    }

}