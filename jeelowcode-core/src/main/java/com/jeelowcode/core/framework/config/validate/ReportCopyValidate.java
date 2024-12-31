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
package com.jeelowcode.core.framework.config.validate;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.jeelowcode.core.framework.entity.ReportEntity;
import com.jeelowcode.core.framework.service.IReportService;
import com.jeelowcode.core.framework.utils.FuncWeb;
import com.jeelowcode.framework.exception.JeeLowCodeException;
import com.jeelowcode.framework.utils.adapter.IJeelowCodeValidate;
import com.jeelowcode.framework.utils.utils.FuncBase;
import com.jeelowcode.framework.utils.utils.JeeLowCodeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * 校验表名是否合法
 */
@Component
public class ReportCopyValidate implements IJeelowCodeValidate {

    @Lazy
    @Autowired
    private IReportService reportService;

    @Override
    public void validate(HttpServletRequest req) throws JeeLowCodeException {
        Map<String, Object> parameterMap = FuncWeb.getParameterMap(req);
        String reportCode = JeeLowCodeUtils.getMap2Str(parameterMap, "reportCode");

        if (FuncBase.isEmpty(reportCode)) {
            throw new JeeLowCodeException("报表编号不允许为空");
        }

        LambdaQueryWrapper<ReportEntity> wrapper=new LambdaQueryWrapper<>();
        wrapper.eq(ReportEntity::getReportCode,reportCode);
        long count = reportService.count(wrapper);

        //校验表名是否重复
        if (count>0) {
            String errorStr = String.format("报表编号【%s】已存在", reportCode);
            throw new JeeLowCodeException(errorStr);
        }

    }
}
