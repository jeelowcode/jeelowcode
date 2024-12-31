/*
Apache License
Version 2.0, January 2004
http://www.apache.org/licenses/
本软件受适用的国家软件著作权法（包括国际条约）和开源协议 双重保护许可。

开源协议中文释意如下：
1.JeeLowCode开源版本无任何限制，在遵循本开源协议（Apache2.0）条款下，【允许商用】使用，不会造成侵权行为。
2.允许基于本平台软件开展业务系统开发。
3.在任何情况下，您不得使用本软件开发可能被认为与【本软件竞争】的软件。

最终解释权归：http://www.jeelowcode.com
*/
package com.jeelowcode.core.framework.config.listener;

import com.jeelowcode.core.framework.service.IFormService;
import com.jeelowcode.core.framework.service.IReportService;
import com.jeelowcode.framework.utils.component.redis.JeeLowCodeRedisUtils;
import groovy.lang.GroovyClassLoader;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;


@Slf4j
@Order(value = 999)
@Component
public class JeeLowCodeListener implements ApplicationListener<ContextRefreshedEvent> {

    @Autowired
    JeeLowCodeRedisUtils redisUtil;

    @Autowired
    private IFormService dbFormService;

    @Autowired
    private IReportService reportService;

    public GroovyClassLoader groovyClassLoader = new GroovyClassLoader();

    public GroovyClassLoader getGroovyClassLoader() {
        return groovyClassLoader;
    }
    
    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {

        //初始化自定义按钮
        dbFormService.initConfigBtnCommand();
        log.info("1.初始化自定义按钮--->完成");
        // 容器启动完成后清除框架缓存
        dbFormService.cleanCache();
        log.info("2.清除框架缓存--->完成");
        //初始化在线编辑脚本
        dbFormService.initOnlineScript(groovyClassLoader);
        log.info("3.初始化在线脚本--->完成");
        //初始化增强插件
        dbFormService.initEnhancePluginManager();
        log.info("4.初始化增强--->完成");
        reportService.initEnhancePluginManager();
        log.info("5.初始化报表增强--->完成");
    }


}