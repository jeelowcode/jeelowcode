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
package com.jeelowcode.framework.utils.global.exeption;


import com.jeelowcode.framework.code.JeeLowCodeErrorCode;
import com.jeelowcode.framework.constants.FrameErrorCodeConstants;
import com.jeelowcode.framework.exception.JeeLowCodeException;
import com.jeelowcode.framework.exception.JeeLowCodeMoreException;
import com.jeelowcode.framework.global.JeeLowCodeBaseConstant;
import com.jeelowcode.framework.utils.model.global.BaseWebResult;
import com.jeelowcode.framework.utils.utils.FuncBase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;


/**
 * 全局异常处理器
 */
@Order(Ordered.HIGHEST_PRECEDENCE)
@RestControllerAdvice(basePackages = JeeLowCodeBaseConstant.BASE_PACKAGES)//扫码我们的包
public class JeeLowCodeGlobalExceptionHandler {
    private static final Logger log = LoggerFactory.getLogger(JeeLowCodeGlobalExceptionHandler.class);


    /**
     * 拦截未知的运行时异常
     */
    @ExceptionHandler(RuntimeException.class)
    public BaseWebResult handleRuntimeException(RuntimeException e, HttpServletRequest request) {
        e.printStackTrace();
        return BaseWebResult.error(FrameErrorCodeConstants.FRAME_PARAM_ERROR);
    }

    /**
     * 系统异常
     */
    @ExceptionHandler(Exception.class)
    public BaseWebResult handleException(Exception e) {
        e.printStackTrace();
        return BaseWebResult.error(FrameErrorCodeConstants.FRAME_PARAM_ERROR);
    }


    /**
     * 自定义异常
     *
     * @param e
     * @return
     */
    @ExceptionHandler(JeeLowCodeException.class)
    public BaseWebResult handleJeeLowCodeExceptionException(RuntimeException e) {
        String message = e.getMessage();
        if(FuncBase.isNotEmpty(message) && FuncBase.jsonIsJson(message)){
            //判断是否是 BaseWebResult，如果是BaseWebResult 则直接弹出
            if(message.contains("code") && message.contains("msg")){
                try{
                    BaseWebResult baseWebResult = FuncBase.json2Bean(message, BaseWebResult.class);
                    if(FuncBase.isNotEmpty(baseWebResult)){
                        return baseWebResult;
                    }
                }catch (Exception e1){

                }
            }
        }

        return BaseWebResult.error(FrameErrorCodeConstants.FRAM_SELF_ERROR.getCode(), e.getMessage());
    }


    /**
     * 自定义异常-更多详情
     * @param e
     * @return
     */
    @ExceptionHandler(JeeLowCodeMoreException.class)
    public BaseWebResult handleJeeLowCodeMoreExceptionException(RuntimeException e) {

        String messageJsonStr = e.getMessage();
        Map<String, String> map = FuncBase.json2Bean(messageJsonStr, Map.class);
        String title = map.get("title");
        String e1 = map.get("e");

        //封装map
        JeeLowCodeErrorCode jeeLowCodeErrorCode = new JeeLowCodeErrorCode(FrameErrorCodeConstants.FRAME_OP_ERROR.getCode(), title);

        return BaseWebResult.error(jeeLowCodeErrorCode, e1);

    }




}