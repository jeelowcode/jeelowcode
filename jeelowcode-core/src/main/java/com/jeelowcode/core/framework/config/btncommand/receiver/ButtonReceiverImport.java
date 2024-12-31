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

import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.jeelowcode.core.framework.config.btncommand.param.ButtonParamImport;
import com.jeelowcode.core.framework.params.model.ExcelImportResultModel;
import com.jeelowcode.core.framework.service.IExcelFileService;
import com.jeelowcode.core.framework.service.IExcelService;
import com.jeelowcode.core.framework.utils.Func;
import com.jeelowcode.framework.utils.tool.spring.SpringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * 新增按钮执行者
 */
public class ButtonReceiverImport implements IButtonCommandReceiver<ExcelImportResultModel> {


    private String pluginKey;

    private ButtonParamImport param;

    public ButtonReceiverImport(ButtonParamImport param) {
        this.param = param;
    }

    /**
     * 执行命令
     * @return
     */

    @Override
    public ExcelImportResultModel receiver() {
        IExcelService excelService = SpringUtils.getBean(IExcelService.class);
        IExcelFileService excelFileService = SpringUtils.getBean(IExcelFileService.class);

        // 将request设置为子线程共享
        ServletRequestAttributes sra = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();

        ExcelImportResultModel resultModel=null;
        //保存Excel数据
        String serviceType = param.getServiceType();
        switch (serviceType){
            case "IMPORT_TEMPLATE"://导入excel到临时库
                long fileId = IdWorker.getId();
                excelFileService.saveExcelSync(fileId,param);

                resultModel = new ExcelImportResultModel();
                resultModel.setBatchCode(Func.toStr(fileId));
                resultModel.setDbFormId(param.getDbFormId());
                resultModel.setTotalCou(param.getDataMapList().size());
                resultModel.setFileId(fileId);
                break;
            case "HANBLE_TEMPLATE"://处理临时库数据
                excelService.handleTempTable(sra,param.getDbFormId(),param.getBatchCode());
                break;

        }

        return resultModel;
    }




}
