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

import com.jeelowcode.framework.excel.model.ExcelTitleModel;
import com.jeelowcode.framework.utils.adapter.IJeeLowCodeAdapter;
import com.jeelowcode.framework.utils.tool.spring.SpringUtils;
import com.jeelowcode.core.framework.config.btncommand.param.ButtonParamExportTemplate;
import com.jeelowcode.core.framework.params.model.ExcelTemplateModel;
import com.jeelowcode.core.framework.params.vo.role.DbFormRoleFieldVo;
import com.jeelowcode.core.framework.service.IDbFormRoleService;
import com.jeelowcode.core.framework.service.IExcelService;
import org.apache.commons.collections4.map.LinkedMap;

import java.util.List;

/**
 * 导出模板按钮执行者
 */
public class ButtonReceiverExportTemplate extends ButtonReceiverBase implements IButtonCommandReceiver<ExcelTemplateModel> {


    private String pluginKey;

    private ButtonParamExportTemplate param;

    public ButtonReceiverExportTemplate(ButtonParamExportTemplate param) {
        this.param = param;
    }

    /**
     * 执行命令
     * @return
     */
    @Override
    public ExcelTemplateModel receiver() {
        IExcelService excelService = SpringUtils.getBean(IExcelService.class);
        IDbFormRoleService dbFormRoleService = SpringUtils.getBean(IDbFormRoleService.class);
        IJeeLowCodeAdapter jeeLowCodeAdapter = SpringUtils.getBean(IJeeLowCodeAdapter.class);
        Long tenantId = jeeLowCodeAdapter.getTenantId();

        Long dbFormId = param.getDbFormId();
        ExcelTemplateModel excelTemplateModel = excelService.getExportExcelTemplate(dbFormId);
        LinkedMap<String, ExcelTitleModel> headTitleMap = excelTemplateModel.getHeadTitleMap();

        List<DbFormRoleFieldVo> roleFieldVoList = dbFormRoleService.listRoleField(tenantId, dbFormId,false);
        super.removeNotExport(headTitleMap,roleFieldVoList);
        return excelTemplateModel;
    }


}
