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
import com.jeelowcode.core.framework.utils.Func;
import com.jeelowcode.framework.utils.utils.JeeLowCodeUtils;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * 系统首页-数据会员-各品类占比
 */
@Component("exampleMemberProductPercentageReportEnhance")
public class ExampleMemberProductPercentageReportEnhance implements ReportAfterAdvicePlugin {

    @Override
    public void execute(EnhanceReportContext enhanceContext) {
        List<Map<String, Object>> records = enhanceContext.getResult().getRecords();
        if (Func.isEmpty(records)) {
            return;
        }
        records.forEach(item -> item.put("sj", DateUtil.format(JeeLowCodeUtils.getMap2DateTime(item, "sj"), "yyyy-MM")));
    }
}
