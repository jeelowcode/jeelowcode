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
package com.jeelowcode.module.biz.demo.test;

import com.jeelowcode.core.framework.config.aspect.enhance.model.EnhanceContext;
import com.jeelowcode.core.framework.config.aspect.enhance.model.EnhanceParam;
import com.jeelowcode.core.framework.config.aspect.enhance.model.EnhanceResult;
import com.jeelowcode.core.framework.config.aspect.enhance.plugin.BeforeAdvicePlugin;
import com.jeelowcode.core.framework.utils.Func;
import com.jeelowcode.framework.exception.JeeLowCodeException;
import com.jeelowcode.framework.utils.utils.FuncBase;


import java.util.Map;

/**
 * @author JX
 * @create 2024-08-15 11:31
 * @dedescription: 前置增强测试
 */
public class DemoBeforeAdvicePlugin implements BeforeAdvicePlugin {


    @Override
    public void execute(EnhanceContext enhanceContext) {
        EnhanceParam param = enhanceContext.getParam();
        Map<String, Object> params = param.getParams();
        String name = FuncBase.getMap2Str(params, "name");
        if (Func.equals(name, "jeelowcode")) {
            throw new JeeLowCodeException("不允许使用jeelowcode");
        }
        params.replace("name",FuncBase.getMap2Str(params,"name")+"before_enhance");

        EnhanceResult result = new EnhanceResult();
        result.setExitFlag(false);
        enhanceContext.setResult(result);
    }
}
