package com.jeelowcode.service.bpm.config.candidate.sourceInfoProcessor;


import com.jeelowcode.service.bpm.controller.vo.candidate.BpmTaskCandidateRuleVO;
import com.jeelowcode.service.bpm.enums.definition.BpmTaskAssignRuleTypeEnum;
import com.jeelowcode.service.bpm.config.candidate.BpmCandidateSourceInfo;
import com.jeelowcode.service.bpm.config.candidate.BpmCandidateSourceInfoProcessor;
import com.jeelowcode.service.system.api.DeptApi;
import com.jeelowcode.service.system.dto.DeptRespDTO;
import com.jeelowcode.service.system.api.AdminUserApi;
import com.jeelowcode.service.system.dto.AdminUserRespDTO;
import com.jeelowcode.tool.framework.common.util.collection.SetUtils;
import org.flowable.engine.delegate.DelegateExecution;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import static com.jeelowcode.tool.framework.common.util.collection.CollectionUtils.convertSet;

public class BpmCandidateDeptApiSourceInfoProcessor implements BpmCandidateSourceInfoProcessor {
    @Resource
    private DeptApi api;
    @Resource
    private AdminUserApi adminUserApi;

    @Override
    public Set<Integer> getSupportedTypes() {
        return SetUtils.asSet(BpmTaskAssignRuleTypeEnum.DEPT_MEMBER.getType(),
                BpmTaskAssignRuleTypeEnum.DEPT_LEADER.getType());
    }

    @Override
    public void validRuleOptions(Integer type, Set<Long> options) {
        api.validateDeptList(options);
    }

    @Override
    public Set<Long> doProcess(BpmCandidateSourceInfo request, BpmTaskCandidateRuleVO rule, DelegateExecution delegateExecution) {
        if (Objects.equals(BpmTaskAssignRuleTypeEnum.DEPT_MEMBER.getType(), rule.getType())) {
            List<AdminUserRespDTO> users = adminUserApi.getUserListByDeptIds(rule.getOptions());
            return convertSet(users, AdminUserRespDTO::getId);
        } else if (Objects.equals(BpmTaskAssignRuleTypeEnum.DEPT_LEADER.getType(), rule.getType())) {
            List<DeptRespDTO> depts = api.getDeptList(rule.getOptions());
            return convertSet(depts, DeptRespDTO::getLeaderUserId);
        }
        return Collections.emptySet();
    }
}