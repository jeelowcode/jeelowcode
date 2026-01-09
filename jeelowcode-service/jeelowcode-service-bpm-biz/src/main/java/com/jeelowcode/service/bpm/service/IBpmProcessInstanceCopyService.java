package com.jeelowcode.service.bpm.service;


import com.jeelowcode.service.bpm.controller.vo.instance.BpmProcessInstanceCopyCreateReqVO;
import com.jeelowcode.service.bpm.controller.vo.instance.BpmProcessInstanceCopyMyPageReqVO;
import com.jeelowcode.service.bpm.entity.BpmProcessInstanceCopyDO;
import com.jeelowcode.service.bpm.config.candidate.BpmCandidateSourceInfo;
import com.jeelowcode.tool.framework.common.pojo.PageResult;

/**
 * 流程抄送 Service 接口
 *
 * 现在是在审批的时候进行流程抄送
 */
public interface IBpmProcessInstanceCopyService {

    // TODO 芋艿：这块要 review 下；思考下~~
    /**
     * 抄送
     * @param sourceInfo 抄送源信息，方便抄送处理
     * @return
     */
    boolean makeCopy(BpmCandidateSourceInfo sourceInfo);

    /**
     * 流程实例的抄送
     *
     * @param userId      当前登录用户
     * @param createReqVO 创建的抄送请求
     */
    void createProcessInstanceCopy(Long userId, BpmProcessInstanceCopyCreateReqVO createReqVO);

    /**
     * 抄送的流程的分页
     * @param userId 当前登录用户
     * @param pageReqVO 分页请求
     * @return 抄送的分页结果
     */
    PageResult<BpmProcessInstanceCopyDO> getMyProcessInstanceCopyPage(Long userId,
                                                                      BpmProcessInstanceCopyMyPageReqVO pageReqVO);
}
