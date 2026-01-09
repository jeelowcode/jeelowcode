package com.jeelowcode.service.bpm.config.candidate.sourceInfoProcessor;

import com.jeelowcode.service.bpm.controller.vo.candidate.BpmTaskCandidateRuleVO;
import com.jeelowcode.service.bpm.enums.definition.BpmTaskAssignRuleTypeEnum;
import com.jeelowcode.service.bpm.config.candidate.BpmCandidateSourceInfo;
import com.jeelowcode.service.bpm.config.candidate.BpmCandidateSourceInfoProcessor;
import com.jeelowcode.service.system.api.PostApi;
import com.jeelowcode.service.system.api.AdminUserApi;
import com.jeelowcode.service.system.dto.AdminUserRespDTO;
import com.jeelowcode.tool.framework.common.util.collection.SetUtils;
import org.flowable.engine.delegate.DelegateExecution;

import javax.annotation.Resource;
import java.util.List;
import java.util.Set;
import static com.jeelowcode.tool.framework.common.util.collection.CollectionUtils.convertSet;


public class BpmCandidatePostApiSourceInfoProcessor implements BpmCandidateSourceInfoProcessor {
    @Resource
    private PostApi api;
    @Resource
    private AdminUserApi adminUserApi;

    @Override
    public Set<Integer> getSupportedTypes() {
        return SetUtils.asSet(BpmTaskAssignRuleTypeEnum.POST.getType());
    }

    @Override
    public void validRuleOptions(Integer type, Set<Long> options) {
        api.validPostList(options);
    }

    @Override
    public Set<Long> doProcess(BpmCandidateSourceInfo request, BpmTaskCandidateRuleVO rule, DelegateExecution delegateExecution) {
        List<AdminUserRespDTO> users = adminUserApi.getUserListByPostIds(rule.getOptions());
        return convertSet(users, AdminUserRespDTO::getId);
    }
}