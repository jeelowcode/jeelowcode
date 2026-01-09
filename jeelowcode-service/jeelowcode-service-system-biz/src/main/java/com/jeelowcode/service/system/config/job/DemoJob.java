package com.jeelowcode.service.system.config.job;

import com.jeelowcode.tool.framework.quartz.core.handler.JobHandler;
import com.jeelowcode.tool.framework.tenant.core.context.TenantContextHolder;
import com.jeelowcode.tool.framework.tenant.core.job.TenantJob;
import com.jeelowcode.service.system.entity.AdminUserDO;
import com.jeelowcode.service.system.mapper.AdminUserMapper;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

@Component
public class DemoJob implements JobHandler {

    @Resource
    private AdminUserMapper adminUserMapper;

    @Override
    @TenantJob // 标记多租户
    public String execute(String param) {
        System.out.println("当前租户：" + TenantContextHolder.getTenantId());
        List<AdminUserDO> users = adminUserMapper.selectList();
        return "用户数量：" + users.size();
    }

}
