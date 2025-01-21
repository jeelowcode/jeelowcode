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

import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUnit;
import cn.hutool.core.date.DateUtil;
import com.jeelowcode.core.framework.config.aspect.enhancereport.model.EnhanceReportContext;
import com.jeelowcode.core.framework.config.aspect.enhancereport.plugin.ReportAfterAdvicePlugin;
import com.jeelowcode.core.framework.mapper.example.ExampleReportMapper;
import com.jeelowcode.core.framework.utils.Func;
import com.jeelowcode.core.framework.utils.FuncWeb;
import com.jeelowcode.framework.utils.utils.JeeLowCodeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * 系统首页-使用统计-登录使用人数分析
 */
@Component("exampleTrafficStatisticsReportEnhance")
public class ExampleTrafficStatisticsReportEnhance implements ReportAfterAdvicePlugin {

    @Autowired
    private ExampleReportMapper exampleReportMapper;

    @Override
    public void execute(EnhanceReportContext enhanceContext) {
        Map<String, Object> params = enhanceContext.getParam().getParams();
        String date = JeeLowCodeUtils.getMap2Str(params, "sj");
        // 开始结束日期相同则按小时，不同则按天
        String startDateStr;
        String endDateStr;
        if (Func.isNotEmpty(date) && date.contains(",")) {
            List<String> dateList = Arrays.asList(date.split(","));
            startDateStr = dateList.get(0);
            endDateStr = dateList.get(1);
        } else {
            // 默认为当天
            startDateStr = DateUtil.today();
            endDateStr = startDateStr;
        }
        List<Map<String, Object>> list = getData(startDateStr, endDateStr);
        FuncWeb.setReportResult(enhanceContext, list);
    }

    private List<Map<String, Object>> getData(String startDateStr, String endDateStr) {
        boolean hourFlag = true;// 默认按小时
        DateTime dateNow = DateUtil.date();
        Date dateStart = DateUtil.beginOfDay(DateUtil.parse(startDateStr, DatePattern.NORM_DATE_PATTERN));// yyyy-MM-dd 00:00:00
        Date dateEnd = DateUtil.endOfDay(DateUtil.parse(endDateStr, DatePattern.NORM_DATE_PATTERN));// yyyy-MM-dd 23:59:59
        if (!Func.equals(startDateStr, endDateStr)) {
            hourFlag = false;
        } else if (Func.equals(startDateStr, DateUtil.today())) {
            dateEnd = dateNow;// 当前时间 yyyy-MM-dd HH:mm:ss
        }
        long between;
        Date start;
        Date end;
        if (hourFlag) {
            // 按小时
            between = DateUtil.between(dateStart, dateEnd, DateUnit.HOUR);
            start = DateUtil.offsetHour(dateStart, -1);
            end = DateUtil.endOfHour(start);
        } else {
            // 按天
            between = DateUtil.between(dateStart, dateEnd, DateUnit.DAY);
            start = DateUtil.offsetDay(dateStart, -1);
            end = DateUtil.endOfDay(start);
        }
        List<Map<String, Object>> list = new ArrayList<>();
        for (int i = 0; i <= between; i++) {
            Map<String, Object> dataMap = new HashMap<>();
            if (hourFlag) {
                start = DateUtil.offsetHour(start, 1);
                end = DateUtil.offsetHour(end, 1);
                dataMap.put("sj", DateUtil.format(start, "HH"));
            } else {
                start = DateUtil.offsetDay(start, 1);
                end = DateUtil.offsetDay(end, 1);
                dataMap.put("sj", DateUtil.format(start, DatePattern.NORM_DATE_PATTERN));
            }
            if (start.getTime() <= dateNow.getTime()) {
                dataMap.putAll(exampleReportMapper.getIpAndVisitsNum(start, end));
            }
            list.add(dataMap);
        }
        return list;
    }
}
