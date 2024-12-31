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
package com.jeelowcode.core.framework.controller;


import cn.hutool.core.date.DateUtil;
import com.jeelowcode.framework.global.JeeLowCodeBaseConstant;
import com.jeelowcode.core.framework.entity.EnhanceJsEntity;
import com.jeelowcode.core.framework.service.IEnhanceJsService;
import com.jeelowcode.core.framework.utils.Func;
import com.jeelowcode.framework.constants.FrameErrorCodeConstants;
import com.jeelowcode.framework.utils.adapter.IJeeLowCodeAdapter;
import com.jeelowcode.framework.utils.annotation.ApiDecryptAes;
import com.jeelowcode.framework.utils.annotation.ApiEncryptAes;
import com.jeelowcode.framework.utils.constant.JeeRedisConstants;
import com.jeelowcode.framework.utils.component.redis.JeeLowCodeRedisUtils;
import com.jeelowcode.framework.utils.utils.FuncBase;
import com.jeelowcode.framework.utils.model.global.BaseWebResult;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;


@Tag(name = "低代码框架-JS增强接口")
@RestController
@AllArgsConstructor
@RequestMapping(JeeLowCodeBaseConstant.REQUEST_URL_START+"/enhance/js")
public class EnhanceJsController extends BaseController {

    private final JeeLowCodeRedisUtils jeeLowCodeRedisUtils;

    private final IEnhanceJsService enhanceJsService;

    private final IJeeLowCodeAdapter adapter;

    @PreAuthorize("@ss.hasPermission('jeelowcode:web')")
    @PostMapping("/unlock/{dbformId}")
    @ApiOperationSupport(order = 1)
    @Operation(tags = "Js增强",summary = "Js增强-解锁")
    public BaseWebResult unlock(@PathVariable("dbformId") Long dbformId, @RequestParam String type) {

        String redisKey = String.format(JeeRedisConstants.ENHANCE_JS_LOCK,dbformId+":"+type);
        if (jeeLowCodeRedisUtils.hasKey(redisKey)) {
            jeeLowCodeRedisUtils.del(redisKey);
        }
        return BaseWebResult.success("成功");
    }


    //返回数据加密
    @ApiEncryptAes
    @PreAuthorize("@ss.hasPermission('jeelowcode:web')")
    @GetMapping("/detail")
    @ApiOperationSupport(order = 1)
    @Operation(tags = "Js增强",summary = "Js增强-详情")
    public BaseWebResult jsDetail(@RequestParam Long dbformId,
                                                   @RequestParam String type,
                                                   @RequestParam String lock){
        if (FuncBase.isNotEmpty(lock) && FuncBase.equals(lock, "true")) {
            String redisKey = String.format(JeeRedisConstants.ENHANCE_JS_LOCK,dbformId+":"+type);
            if (jeeLowCodeRedisUtils.hasKey(redisKey)) {
                String lockMapStr = (String)jeeLowCodeRedisUtils.get(redisKey);
                Map lockMap = Func.json2Bean(lockMapStr, Map.class);
                //返回锁定信息
                return BaseWebResult.error(FrameErrorCodeConstants.FRAME_ENHANCE_ERROR,lockMap);
            }

            String userName = adapter.getOnlineUserName();

            String timeStr = DateUtil.now();
            //存储锁定信息
            Map<String,Object> lockMap=new HashMap<>();
            lockMap.put("userName",userName);
            lockMap.put("timeStr",timeStr);
            jeeLowCodeRedisUtils.set(redisKey, Func.json2Str(lockMap));//上锁
        }
        return BaseWebResult.success(enhanceJsService.getDbFormEnhanceJsDetail(dbformId,type));
    }

    //解密body数据
    @ApiDecryptAes
    @PreAuthorize("@ss.hasPermission('jeelowcode:web')")
    @PostMapping("/save")
    @ApiOperationSupport(order = 2)
    @Operation(tags = "Js增强",summary = "js增强-新增")
    public BaseWebResult save(@RequestBody EnhanceJsEntity model) {
        enhanceJsService.saveEnhanceJs(model);
        return BaseWebResult.success(model.getId());
    }
    
    //解密body数据
    @ApiDecryptAes
    @PreAuthorize("@ss.hasPermission('jeelowcode:web')")
    @PutMapping("/update")
    @ApiOperationSupport(order = 3)
    @Operation(tags = "Js增强",summary = "js增强-修改")
    public BaseWebResult update(@RequestBody EnhanceJsEntity model) {
        enhanceJsService.updateEnhanceJs(model);
        return BaseWebResult.success(model.getId());
    }


}
