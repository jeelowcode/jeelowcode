
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
import com.jeelowcode.framework.utils.utils.FuncBase;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * 学生-分页-后置增强
 */
@Slf4j
@Component("studentAfterEnhancePage")
public class ExampleStudentAfterEnhancePage implements AfterAdvicePlugin {

    @Override
    public void execute(EnhanceContext enhanceContext) {
        log.info("进入=======>studentAfterEnhancePage=======>execute");
        List<Map<String, Object>> records = enhanceContext.getResult().getRecords();
        if (FuncBase.isEmpty(records)) {
            return;
        }
        // 处理返回的数据
        records.forEach(item -> {
            // 注：如果要在这里添加虚拟字段，需先在 [表单开发-数据库属性-同步数据库] 的对应字段处取消打勾，并确认该字段处在 [表单开发-虚拟化字段格式]
            item.put("classe", "1");
        });
    }
}
