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
package com.jeelowcode.framework.utils.component;

import com.baomidou.mybatisplus.autoconfigure.ConfigurationCustomizer;
import com.jeelowcode.framework.utils.adapter.IJeeLowCodeAdapter;
import com.jeelowcode.framework.utils.component.cache.JeelowCodeCacheAspect;
import com.jeelowcode.framework.utils.component.crypto.ApiDecryptRequestBodyAdvice;
import com.jeelowcode.framework.utils.component.crypto.ApiDecryptResponseBodyAdvice;
import com.jeelowcode.framework.utils.component.defaultval.DefaultValAspect;
import com.jeelowcode.framework.utils.component.mybatis.MapWrapperFactory;
import com.jeelowcode.framework.utils.component.properties.JeeLowCodeProperties;
import com.jeelowcode.framework.utils.component.redis.JeeLowCodeRedisUtils;
import com.jeelowcode.framework.utils.component.validate.JeelowCodeValidateAspect;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@EnableConfigurationProperties(JeeLowCodeProperties.class)
@Component
public class JeeLowCodeAutoConfiguration {

    // ========== AOP ==========

    @Bean
    public ApiDecryptRequestBodyAdvice apiDecryptRequestBodyAdvice() {
        return new ApiDecryptRequestBodyAdvice();
    }

    @Bean
    public ApiDecryptResponseBodyAdvice apiDecryptResponseBodyAdvice() {
        return new ApiDecryptResponseBodyAdvice();
    }

    @Bean
    public JeelowCodeValidateAspect jeelowCodeValidateAspect() {
        return new JeelowCodeValidateAspect();
    }

    //缓存相关
    @Bean
    public JeelowCodeCacheAspect jeelowCodeCacheAspect(JeeLowCodeRedisUtils jeeLowCodeRedisUtils) {
        return new JeelowCodeCacheAspect(jeeLowCodeRedisUtils);
    }

    //默认值处理相关
    @Bean
    public DefaultValAspect defaultValAspect(IJeeLowCodeAdapter proxyAdapter) {
        return new DefaultValAspect(proxyAdapter);
    }

    //mybatis 返回值大写转小写
    @Bean
    public ConfigurationCustomizer configurationCustomizer() {
        return configuration -> configuration.setObjectWrapperFactory(new MapWrapperFactory());
    }

}
