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
package com.jeelowcode.core.framework.config.job;

import cn.hutool.core.date.DateUtil;
import cn.iocoder.yudao.framework.quartz.core.handler.JobHandler;
import com.jeelowcode.core.framework.service.IJeeLowCodeConfigService;
import com.jeelowcode.core.framework.service.IJeeLowCodeService;
import com.jeelowcode.framework.tenant.annotation.JeeLowCodeTenantIgnore;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * 删除excel临时表数据
 */
@Component
@Slf4j
public class ClearExcelDataJob implements JobHandler {

    @Autowired
    private IJeeLowCodeService jeeLowCodeService;

    @Autowired
    private IJeeLowCodeConfigService jeeLowCodeConfigService;


    @Override
    @JeeLowCodeTenantIgnore
    public String execute(String param) {
        log.info("*********** 开始清理Excel Data日志 ************");
        Date now = DateUtil.date();
        Integer day = jeeLowCodeConfigService.getExcelFileDataDay();

        //x天之前
        Date clearDate = DateUtil.offsetDay(now, -day);

        Integer clearCou = jeeLowCodeService.clearExcelData(clearDate);

        String resultStr="*********** Excel Data日志清理完成，共清理了"+clearCou+"条 ************";
        log.info(resultStr);
        return resultStr;
    }

}
