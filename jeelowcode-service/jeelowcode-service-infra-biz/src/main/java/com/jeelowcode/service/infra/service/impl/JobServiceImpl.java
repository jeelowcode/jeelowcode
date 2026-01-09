package com.jeelowcode.service.infra.service.impl;

import com.jeelowcode.service.infra.controller.vo.job.JobPageReqVO;
import com.jeelowcode.service.infra.controller.vo.job.JobSaveReqVO;
import com.jeelowcode.service.infra.mapper.JobMapper;
import com.jeelowcode.service.infra.service.IJobService;
import com.jeelowcode.tool.framework.common.pojo.PageResult;
import com.jeelowcode.tool.framework.common.util.object.BeanUtils;
import com.jeelowcode.tool.framework.quartz.core.scheduler.SchedulerManager;
import com.jeelowcode.tool.framework.quartz.core.util.CronUtils;
import com.jeelowcode.service.infra.entity.JobDO;
import com.jeelowcode.service.infra.config.enums.job.JobStatusEnum;
import org.quartz.SchedulerException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;

import static com.jeelowcode.tool.framework.common.exception.util.ServiceExceptionUtil.exception;
import static com.jeelowcode.tool.framework.common.util.collection.CollectionUtils.containsAny;
import static com.jeelowcode.service.infra.enums.ErrorCodeConstants.*;

/**
 * 定时任务 Service 实现类
 *
 * @author 芋道源码
 */
@Service
@Validated
public class JobServiceImpl implements IJobService {

    @Resource
    private JobMapper jobMapper;

    @Resource
    private SchedulerManager schedulerManager;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long createJob(JobSaveReqVO createReqVO) throws SchedulerException {
        validateCronExpression(createReqVO.getCronExpression());
        // 校验唯一性
        if (jobMapper.selectByHandlerName(createReqVO.getHandlerName()) != null) {
            throw exception(JOB_HANDLER_EXISTS);
        }
        // 插入
        JobDO job = BeanUtils.toBean(createReqVO, JobDO.class);
        job.setStatus(JobStatusEnum.INIT.getStatus());
        fillJobMonitorTimeoutEmpty(job);
        jobMapper.insert(job);

        // 添加 Job 到 Quartz 中
        schedulerManager.addJob(job.getId(), job.getHandlerName(), job.getHandlerParam(), job.getCronExpression(),
                createReqVO.getRetryCount(), createReqVO.getRetryInterval());
        // 更新
        JobDO updateObj = JobDO.builder().id(job.getId()).status(JobStatusEnum.NORMAL.getStatus()).build();
        jobMapper.updateById(updateObj);

        // 返回
        return job.getId();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateJob(JobSaveReqVO updateReqVO) throws SchedulerException {
        validateCronExpression(updateReqVO.getCronExpression());
        // 校验存在
        JobDO job = validateJobExists(updateReqVO.getId());
        // 只有开启状态，才可以修改.原因是，如果出暂停状态，修改 Quartz Job 时，会导致任务又开始执行
        if (!job.getStatus().equals(JobStatusEnum.NORMAL.getStatus())) {
            throw exception(JOB_UPDATE_ONLY_NORMAL_STATUS);
        }
        // 更新
        JobDO updateObj = BeanUtils.toBean(updateReqVO, JobDO.class);
        fillJobMonitorTimeoutEmpty(updateObj);
        jobMapper.updateById(updateObj);

        // 更新 Job 到 Quartz 中
        schedulerManager.updateJob(job.getHandlerName(), updateReqVO.getHandlerParam(), updateReqVO.getCronExpression(),
                updateReqVO.getRetryCount(), updateReqVO.getRetryInterval());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateJobStatus(Long id, Integer status) throws SchedulerException {
        // 校验 status
        if (!containsAny(status, JobStatusEnum.NORMAL.getStatus(), JobStatusEnum.STOP.getStatus())) {
            throw exception(JOB_CHANGE_STATUS_INVALID);
        }
        // 校验存在
        JobDO job = validateJobExists(id);
        // 校验是否已经为当前状态
        if (job.getStatus().equals(status)) {
            throw exception(JOB_CHANGE_STATUS_EQUALS);
        }
        // 更新 Job 状态
        JobDO updateObj = JobDO.builder().id(id).status(status).build();
        jobMapper.updateById(updateObj);

        // 更新状态 Job 到 Quartz 中
        if (JobStatusEnum.NORMAL.getStatus().equals(status)) { // 开启
            schedulerManager.resumeJob(job.getHandlerName());
        } else { // 暂停
            schedulerManager.pauseJob(job.getHandlerName());
        }
    }

    @Override
    public void triggerJob(Long id) throws SchedulerException {
        // 校验存在
        JobDO job = validateJobExists(id);

        // 触发 Quartz 中的 Job
        schedulerManager.triggerJob(job.getId(), job.getHandlerName(), job.getHandlerParam());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteJob(Long id) throws SchedulerException {
        // 校验存在
        JobDO job = validateJobExists(id);
        // 更新
        jobMapper.deleteById(id);

        // 删除 Job 到 Quartz 中
        schedulerManager.deleteJob(job.getHandlerName());
    }

    private JobDO validateJobExists(Long id) {
        JobDO job = jobMapper.selectById(id);
        if (job == null) {
            throw exception(JOB_NOT_EXISTS);
        }
        return job;
    }

    private void validateCronExpression(String cronExpression) {
        if (!CronUtils.isValid(cronExpression)) {
            throw exception(JOB_CRON_EXPRESSION_VALID);
        }
    }

    @Override
    public JobDO getJob(Long id) {
        return jobMapper.selectById(id);
    }

    @Override
    public PageResult<JobDO> getJobPage(JobPageReqVO pageReqVO) {
		return jobMapper.selectPage(pageReqVO);
    }

    private static void fillJobMonitorTimeoutEmpty(JobDO job) {
        if (job.getMonitorTimeout() == null) {
            job.setMonitorTimeout(0);
        }
    }

}
