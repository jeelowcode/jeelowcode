
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
import com.jeelowcode.core.framework.mapper.example.ExampleDbFormMapper;
import com.jeelowcode.framework.exception.JeeLowCodeException;
import com.jeelowcode.framework.utils.utils.FuncBase;
import com.jeelowcode.framework.utils.utils.JeeLowCodeUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * 学生-导入-前置增强
 */
@Slf4j
 @Component ( value  =  "studentBeforeEnhanceImport" )
public class ExampleStudentBeforeEnhanceImport implements BeforeAdvicePlugin {

    @Autowired
    private ExampleDbFormMapper exampleDbFormMapper;

    @Override
    public void execute(EnhanceContext enhanceContext) {
        log.info("进入=======>studentBeforeEnhanceImport=======>execute");
        Map<String, Object> params = enhanceContext.getParam().getParams();
        this.checkData(params);
    }

    /**
     * 校验数据合法性
     *
     * @param map
     */
    private void checkData(Map<String, Object> map) {
        Long sno = JeeLowCodeUtils.getMap2Long(map, "sno");
        Long id = exampleDbFormMapper.getStudentBySon(sno);
        if (FuncBase.isNotEmpty(id)) {
            throw new JeeLowCodeException("学号[" + sno + "]已存在！");
        }
    }
}
