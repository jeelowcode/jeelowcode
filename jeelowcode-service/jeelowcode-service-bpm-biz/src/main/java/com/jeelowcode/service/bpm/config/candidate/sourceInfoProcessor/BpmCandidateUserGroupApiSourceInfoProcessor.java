package com.jeelowcode.service.bpm.config.candidate.sourceInfoProcessor;


import com.jeelowcode.service.bpm.controller.vo.candidate.BpmTaskCandidateRuleVO;
import com.jeelowcode.service.bpm.entity.BpmUserGroupDO;
import com.jeelowcode.service.bpm.enums.definition.BpmTaskAssignRuleTypeEnum;
import com.jeelowcode.service.bpm.config.candidate.BpmCandidateSourceInfo;
import com.jeelowcode.service.bpm.config.candidate.BpmCandidateSourceInfoProcessor;
import com.jeelowcode.service.bpm.service.IBpmUserGroupService;
import com.jeelowcode.tool.framework.common.util.collection.SetUtils;
import org.flowable.engine.delegate.DelegateExecution;

import javax.annotation.Resource;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class BpmCandidateUserGroupApiSourceInfoProcessor implements BpmCandidateSourceInfoProcessor {
    @Resource
    private IBpmUserGroupService api;
    @Resource
    private IBpmUserGroupService userGroupService;

    @Override
    public Set<Integer> getSupportedTypes() {
        return SetUtils.asSet(BpmTaskAssignRuleTypeEnum.USER_GROUP.getType());
    }

    @Override
    public void validRuleOptions(Integer type, Set<Long> options) {
        api.validUserGroups(options);
    }

    @Override
    public Set<Long> doProcess(BpmCandidateSourceInfo request, BpmTaskCandidateRuleVO rule, DelegateExecution delegateExecution) {
        List<BpmUserGroupDO> userGroups = userGroupService.getUserGroupList(rule.getOptions());
        Set<Long> userIds = new HashSet<>();
        userGroups.forEach(group -> userIds.addAll(group.getMemberUserIds()));
        return userIds;
    }

}