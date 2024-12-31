
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
import com.jeelowcode.core.framework.config.aspect.enhance.plugin.BeforeAdvicePlugin;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * 学生-导出-前置增强
 */
@Slf4j
@Component("studentBeforeEnhanceExport")
public class ExampleStudentBeforeEnhanceExport implements BeforeAdvicePlugin {

    @Override
    public void execute(EnhanceContext enhanceContext) {
        log.info("进入=======>studentBeforeEnhanceExport=======>execute");
        Map<String, Object> params = enhanceContext.getParam().getParams();
        // 注：如果要在这里添加过滤条件需先在 [表单开发-查询属性-接口查询] 的对应字段处打勾
        params.put("flag", "1");
    }
}
