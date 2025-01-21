
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
package com.jeelowcode.core.framework.enhance.example.dbform.student;

import com.jeelowcode.core.framework.config.aspect.enhance.model.EnhanceContext;
import com.jeelowcode.core.framework.config.aspect.enhance.plugin.AroundAdvicePlugin;
import com.jeelowcode.core.framework.mapper.example.ExampleDbFormMapper;
import com.jeelowcode.core.framework.utils.FuncWeb;
import com.jeelowcode.framework.utils.utils.JeeLowCodeUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * 学生-详情-环绕增强
 */
@Slf4j
@Component("studentAroundEnhanceDetail")
public class ExampleStudentAroundEnhanceDetail implements AroundAdvicePlugin {

    @Autowired
    private ExampleDbFormMapper exampleDbFormMapper;

    @Override
    public void beforeExecute(EnhanceContext enhanceContext) {
        log.info("进入=======>studentAroundEnhanceDetail=======>beforeExecute");
        Map<String, Object> params = enhanceContext.getParam().getParams();
        Long id = JeeLowCodeUtils.getMap2Long(params, "id");
        // 手动编写sql语句
        Map<String, Object> map = exampleDbFormMapper.getStudentById(id);
        FuncWeb.setDetailResult(enhanceContext, map);
    }

    @Override
    public void afterExecute(EnhanceContext enhanceContext) {
    }
}
