package com.jeelowcode.tool.framework.operatelog.config;

import com.jeelowcode.tool.framework.operatelog.core.aop.OperateLogAspect;
import com.jeelowcode.tool.framework.operatelog.core.service.OperateLogFrameworkService;
import com.jeelowcode.tool.framework.operatelog.core.service.OperateLogFrameworkServiceImpl;
import com.jeelowcode.service.system.api.OperateLogApi;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.Bean;

@AutoConfiguration
public class OperateLogAutoConfiguration {

    @Bean
    public OperateLogAspect operateLogAspect() {
        return new OperateLogAspect();
    }

    @Bean
    public OperateLogFrameworkService operateLogFrameworkService(OperateLogApi operateLogApi) {
        return new OperateLogFrameworkServiceImpl(operateLogApi);
    }

}
