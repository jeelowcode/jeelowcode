package com.jeelowcode.core.framework.enhance.example.dbform.zmh;

import com.jeelowcode.core.framework.config.aspect.enhance.model.EnhanceContext;
import com.jeelowcode.core.framework.config.aspect.enhance.plugin.AfterAdvicePlugin;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @Author: MR
 * @Date: 2025/12/25 15:45
 * @Version: v1.0.0
 * @Description: TODO
 **/
@Slf4j
@Component("testAfterEnhance")
public class TestAfterEnhance implements AfterAdvicePlugin {

    @Override
    public void execute(EnhanceContext enhanceContext) {
        enhanceContext.getResult().getRecords().forEach(item -> {
          item.put("name", item.get("name")+ "-after");
        });
    }
}

