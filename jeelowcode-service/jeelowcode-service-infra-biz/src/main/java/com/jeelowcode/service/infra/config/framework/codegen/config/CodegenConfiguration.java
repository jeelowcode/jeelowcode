package com.jeelowcode.service.infra.config.framework.codegen.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration(proxyBeanMethods = false)
@EnableConfigurationProperties(CodegenProperties.class)
public class CodegenConfiguration {
}
