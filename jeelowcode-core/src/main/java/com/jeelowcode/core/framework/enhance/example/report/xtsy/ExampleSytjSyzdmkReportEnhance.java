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
package com.jeelowcode.core.framework.enhance.example.report.xtsy;

import cn.hutool.core.date.DateUtil;
import com.jeelowcode.core.framework.config.aspect.enhancereport.model.EnhanceReportContext;
import com.jeelowcode.core.framework.config.aspect.enhancereport.plugin.ReportAfterAdvicePlugin;
import com.jeelowcode.core.framework.mapper.example.ExampleReportMapper;
import com.jeelowcode.core.framework.utils.Func;
import com.jeelowcode.core.framework.utils.FuncWeb;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * 系统首页-使用统计-使用最多模块
 */
@Component("exampleSytjSyzdmkReportEnhance")
public class ExampleSytjSyzdmkReportEnhance implements ReportAfterAdvicePlugin {

    @Autowired
    private ExampleReportMapper exampleReportMapper;

    @Override
    public void execute(EnhanceReportContext enhanceContext) {
        List<Map<String, Object>> resultList = new ArrayList<>();
        Date end = DateUtil.date();
        Date begin = DateUtil.beginOfDay(end);
        List<Map<String, Object>> list = exampleReportMapper.getMaxUsedModel(begin, end);
        if (Func.isNotEmpty(list)) {
            int total = Math.min(list.size(), 5);
            resultList = list.subList(0, total);
            for (int i = 0; i < resultList.size(); i++) {
                resultList.get(i).put("pm", i + 1);
            }
        }
        FuncWeb.setReportResult(enhanceContext, resultList);
    }
}
