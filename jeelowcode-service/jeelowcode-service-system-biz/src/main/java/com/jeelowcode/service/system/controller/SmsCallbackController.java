package com.jeelowcode.service.system.controller;

import com.jeelowcode.tool.framework.common.pojo.CommonResult;
import com.jeelowcode.tool.framework.common.util.servlet.ServletUtils;
import com.jeelowcode.tool.framework.operatelog.core.annotations.OperateLog;
import com.jeelowcode.service.system.config.framework.sms.core.enums.SmsChannelEnum;
import com.jeelowcode.service.system.service.ISmsSendService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.annotation.security.PermitAll;
import javax.servlet.http.HttpServletRequest;

import static com.jeelowcode.tool.framework.common.pojo.CommonResult.success;

@Tag(name = "管理后台 - 短信回调")
@RestController
@RequestMapping("/system/sms/callback")
public class SmsCallbackController {

    @Resource
    private ISmsSendService smsSendService;

    @PostMapping("/aliyun")
    @PermitAll
    @Operation(tags = "短信管理",summary = "阿里云短信的回调", description = "参见 https://help.aliyun.com/document_detail/120998.html 文档")
    @OperateLog(enable = false)
    public CommonResult<Boolean> receiveAliyunSmsStatus(HttpServletRequest request) throws Throwable {
        String text = ServletUtils.getBody(request);
        smsSendService.receiveSmsStatus(SmsChannelEnum.ALIYUN.getCode(), text);
        return success(true);
    }

    @PostMapping("/tencent")
    @PermitAll
    @Operation(tags = "短信管理",summary = "腾讯云短信的回调", description = "参见 https://cloud.tencent.com/document/product/382/52077 文档")
    @OperateLog(enable = false)
    public CommonResult<Boolean> receiveTencentSmsStatus(HttpServletRequest request) throws Throwable {
        String text = ServletUtils.getBody(request);
        smsSendService.receiveSmsStatus(SmsChannelEnum.TENCENT.getCode(), text);
        return success(true);
    }

}
