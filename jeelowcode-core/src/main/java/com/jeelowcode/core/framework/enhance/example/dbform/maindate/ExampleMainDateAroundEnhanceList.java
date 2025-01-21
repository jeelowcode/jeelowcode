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
package com.jeelowcode.core.framework.enhance.example.dbform.maindate;

import cn.hutool.json.JSONUtil;
import com.jeelowcode.core.framework.config.aspect.enhance.model.EnhanceContext;
import com.jeelowcode.core.framework.config.aspect.enhance.plugin.AroundAdvicePlugin;
import com.jeelowcode.core.framework.mapper.example.ExampleDbFormMapper;
import com.jeelowcode.core.framework.utils.Func;
import com.jeelowcode.framework.utils.utils.JeeLowCodeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

/**
 * 日程管理-环绕
 */
@Component("exampleMainDateAroundEnhanceList")
public class ExampleMainDateAroundEnhanceList implements AroundAdvicePlugin {

    @Autowired
    private ExampleDbFormMapper exampleDbFormMapper;

    @Override
    public void beforeExecute(EnhanceContext enhanceContext) {
        Map<String, Object> params = enhanceContext.getParam().getParams();
        String kssjStr = JeeLowCodeUtils.getMap2Str(params, "kssj");
        if (Func.isNotEmpty(kssjStr)) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            String format = null;
            try {
                format = sdf.format(sdf.parse(kssjStr));
            } catch (ParseException ignored) {
            }
            if (Func.isNotEmpty(format) && Func.equals(format, kssjStr)) {
                // 前端传过来的时间格式为 yyyy-MM-dd ，转为 yyyy-MM-dd 00:00:00,yyyy-MM-dd 23:59:59
                params.put("kssj", kssjStr + " 00:00:00," + kssjStr + " 23:59:59");
            }
        }
    }

    @Override
    public void afterExecute(EnhanceContext enhanceContext) {
        List<Map<String, Object>> records = enhanceContext.getResult().getRecords();
        for (Map<String, Object> record : records) {
            Long id = JeeLowCodeUtils.getMap2Long(record, "id");
            // 获取附表数据转json字符串
            List<Map<String, Object>> list = exampleDbFormMapper.getSubmoredateByMainDateId(id);
            record.put("sub_more_date", JSONUtil.toJsonStr(list));
        }
    }
}
