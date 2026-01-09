package com.jeelowcode.service.infra.config.job.logger;

import com.jeelowcode.service.infra.service.IApiAccessLogService;
import com.jeelowcode.tool.framework.quartz.core.handler.JobHandler;
import com.jeelowcode.tool.framework.tenant.core.aop.TenantIgnore;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * 物理删除 N 天前的访问日志的 Job
 *
 * @author j-sentinel
 */
@Component
@Slf4j
public class AccessLogCleanJob implements JobHandler {

    @Resource
    private IApiAccessLogService apiAccessLogService;

    /**
     * 清理超过（14）天的日志
     */
    private static final Integer JOB_CLEAN_RETAIN_DAY = 14;

    /**
     * 每次删除间隔的条数，如果值太高可能会造成数据库的压力过大
     */
    private static final Integer DELETE_LIMIT = 100;

    @Override
    @TenantIgnore
    public String execute(String param) {
        Integer count = apiAccessLogService.cleanAccessLog(JOB_CLEAN_RETAIN_DAY, DELETE_LIMIT);
        log.info("[execute][定时执行清理访问日志数量 ({}) 个]", count);
        return String.format("定时执行清理错误日志数量 %s 个", count);
    }

}
