
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
import com.jeelowcode.core.framework.config.aspect.enhance.plugin.AfterAdvicePlugin;
import com.jeelowcode.core.framework.mapper.example.ExampleDbFormMapper;
import com.jeelowcode.core.framework.utils.Func;
import com.jeelowcode.framework.utils.utils.JeeLowCodeUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * 学生-删除-后置增强
 */
@Slf4j
@Component("studentAfterEnhanceDelete")
public class ExampleStudentAfterEnhanceDelete implements AfterAdvicePlugin {

    @Autowired
    private ExampleDbFormMapper exampleDbFormMapper;

    @Override
    public void execute(EnhanceContext enhanceContext) {
        log.info("进入=======>studentAfterEnhanceDelete=======>execute");
        // 删除其他表数据
        Map<String, Object> params = enhanceContext.getParam().getParams();
        // 单条删除和批量删除的数据的id都是在dateIdList中
        Object dateIdList = params.get("dataIdList");
        if (Func.isEmpty(dateIdList)) {
            return;
        }
        List<Long> studentIdList = (List<Long>) dateIdList;
        exampleDbFormMapper.delScoreByStudentId(studentIdList);
    }
}
