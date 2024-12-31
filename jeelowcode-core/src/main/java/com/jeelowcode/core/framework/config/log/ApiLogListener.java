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
package com.jeelowcode.core.framework.config.log;

import cn.hutool.core.bean.BeanUtil;
import com.jeelowcode.core.framework.entity.LogRequestApiEntity;
import com.jeelowcode.core.framework.entity.LogRequestErrorApiEntity;
import com.jeelowcode.core.framework.mapper.LogApiErrorMapper;
import com.jeelowcode.core.framework.mapper.LogApiMapper;
import com.jeelowcode.core.framework.utils.Func;
import com.jeelowcode.framework.utils.tool.spring.SpringUtils;
import com.jeelowcode.core.framework.params.model.LogRequestApiModel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.Order;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

/**
 * 异步监听日志事件
 *
 * @author Chill
 */
@Component
@Slf4j
@AllArgsConstructor
public class ApiLogListener {


    @Autowired
    private LogApiMapper logApiMapper;

    @Autowired
    private LogApiErrorMapper logApiErrorMapper;

    @Async("asyncPoolTaskExecutor")
    @Order
    @EventListener(ApiLogEvent.class)
    public void saveApiLog(ApiLogEvent event) {
        LogRequestApiModel apiModel =(LogRequestApiModel)event.getSource();
        String error = apiModel.getError();
        if(Func.isEmpty(error)){//正常日志
            LogRequestApiEntity infoEntity = BeanUtil.copyProperties(apiModel, LogRequestApiEntity.class);
            logApiMapper.insert(infoEntity);
        }else{//错误日志
            LogRequestErrorApiEntity errorEntity = BeanUtil.copyProperties(apiModel, LogRequestErrorApiEntity.class);
            logApiErrorMapper.insert(errorEntity);
        }

    }

}
