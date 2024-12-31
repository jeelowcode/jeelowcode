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
package com.jeelowcode.module.api;

import com.jeelowcode.core.framework.service.IFormService;
import com.jeelowcode.core.framework.service.IReportService;
import com.jeelowcode.core.framework.utils.Func;
import com.jeelowcode.framework.utils.enums.AuthTypeEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 低代码 API 接口
 */
@Service
public class JeeLowCodeApiImpl implements JeeLowCodeApi {

    @Autowired
    private IFormService formService;

    @Autowired
    private IReportService reportService;

    //获取表单设置的授权类型
    @Override
    public String getDbformAuthType(Long dbFormid) {
        AuthTypeEnum authType = formService.getAuthType(dbFormid);
        return authType.getType();

    }

    //获取表单设置的授权类型
    @Override
    public String getReportAuthType(String reportCode) {
        AuthTypeEnum authType = reportService.getAuthType(reportCode);
        return authType.getType();

    }

}
