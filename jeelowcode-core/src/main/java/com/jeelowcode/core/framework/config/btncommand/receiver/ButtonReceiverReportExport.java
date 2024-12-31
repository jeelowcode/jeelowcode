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

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jeelowcode.core.framework.config.btncommand.param.ButtonParamReportExport;
import com.jeelowcode.core.framework.params.model.ExcelModel;
import com.jeelowcode.core.framework.service.IExcelService;
import com.jeelowcode.core.framework.service.IFrameService;
import com.jeelowcode.framework.utils.model.ResultDataModel;
import com.jeelowcode.framework.utils.tool.spring.SpringUtils;

import java.util.Map;

/**
 * 新增按钮执行者
 */
public class ButtonReceiverReportExport extends ButtonReceiverBase implements IButtonCommandReceiver<ExcelModel> {


    private String pluginKey;

    private ButtonParamReportExport param;

    public ButtonReceiverReportExport(ButtonParamReportExport param) {
        this.param = param;
    }

    /**
     * 执行命令
     * @return
     */
    @Override
    public ExcelModel receiver() {
        IExcelService excelService = SpringUtils.getBean(IExcelService.class);
        IFrameService frameService = SpringUtils.getBean(IFrameService.class);

        String reportCode = param.getReportCode();
        Map<String, Object> params = param.getParams();

        //数据列表
        Page page=new Page(1,-1);
        ResultDataModel model = frameService.getReportDataList(reportCode, page,params);

        //获取基本信息
        ExcelModel excelModel = excelService.getExcelReportModel(reportCode);
        excelModel.setDataMapList(model.getRecords());

        return excelModel;
    }


}
