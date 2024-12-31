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
package com.jeelowcode.core.framework.config.btncommand.receiver;

import com.jeelowcode.framework.utils.model.ResultDataModel;
import com.jeelowcode.framework.utils.tool.spring.SpringUtils;
import com.jeelowcode.core.framework.config.btncommand.param.ButtonParamSummary;
import com.jeelowcode.core.framework.service.IFrameService;
import com.jeelowcode.core.framework.utils.Func;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 统计按钮执行者
 */
public class ButtonReceiverSummary implements IButtonCommandReceiver<Map<String, Object>> {


    private String pluginKey;

    private ButtonParamSummary param;

    public ButtonReceiverSummary(ButtonParamSummary param) {
        this.param = param;
    }

    /**
     * 执行命令
     *
     * @return
     */
    @Override
    public Map<String, Object> receiver() {
        IFrameService frameService = SpringUtils.getBean(IFrameService.class);
        Long dbformId = param.getDbFormId();
        Map<String, Object> params = param.getParams();
        ResultDataModel resultDataModel = frameService.getDataSummaryList(dbformId, params);

        Map<String, Object> resultMap = new HashMap<>();
        if(Func.isEmpty(resultDataModel)){
            return resultMap;
        }
        List<Map<String, Object>> dataMapList = resultDataModel.getRecords();
        if (Func.isEmpty(dataMapList)) {//数据为空
            return resultMap;
        }
        return dataMapList.get(0);
    }



}
