package com.jeelowcode.service.bpm.config.convert.definition;

import com.jeelowcode.service.bpm.controller.vo.rule.BpmTaskAssignRuleCreateReqVO;
import com.jeelowcode.service.bpm.controller.vo.rule.BpmTaskAssignRuleRespVO;
import com.jeelowcode.service.bpm.controller.vo.rule.BpmTaskAssignRuleUpdateReqVO;
import com.jeelowcode.service.bpm.entity.BpmTaskAssignRuleDO;
import com.jeelowcode.tool.framework.common.util.collection.CollectionUtils;
import org.flowable.bpmn.model.UserTask;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;
import java.util.Map;

@Mapper
public interface BpmTaskAssignRuleConvert {
    BpmTaskAssignRuleConvert INSTANCE = Mappers.getMapper(BpmTaskAssignRuleConvert.class);

    default List<BpmTaskAssignRuleRespVO> convertList(List<UserTask> tasks, List<BpmTaskAssignRuleDO> rules) {
        Map<String, BpmTaskAssignRuleDO> ruleMap = CollectionUtils.convertMap(rules, BpmTaskAssignRuleDO::getTaskDefinitionKey);
        // 以 UserTask 为主维度，原因是：流程图编辑后，一些规则实际就没用了。
        return CollectionUtils.convertList(tasks, task -> {
            BpmTaskAssignRuleRespVO respVO = convert(ruleMap.get(task.getId()));
            if (respVO == null) {
                respVO = new BpmTaskAssignRuleRespVO();
                respVO.setTaskDefinitionKey(task.getId());
            }
            respVO.setTaskDefinitionName(task.getName());
            return respVO;
        });
    }

    BpmTaskAssignRuleRespVO convert(BpmTaskAssignRuleDO bean);

    BpmTaskAssignRuleDO convert(BpmTaskAssignRuleCreateReqVO bean);

    BpmTaskAssignRuleDO convert(BpmTaskAssignRuleUpdateReqVO bean);

    List<BpmTaskAssignRuleDO> convertList2(List<BpmTaskAssignRuleRespVO> list);
}
