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

import com.jeelowcode.core.framework.utils.Func;
import com.jeelowcode.framework.utils.adapter.IJeeLowCodeAdapter;
import com.jeelowcode.framework.utils.enums.ParamEnum;
import com.jeelowcode.framework.utils.model.ResultDataModel;
import com.jeelowcode.framework.utils.tool.spring.SpringUtils;
import com.jeelowcode.core.framework.config.btncommand.param.ButtonParamList;
import com.jeelowcode.core.framework.params.vo.role.DbFormRoleFieldVo;
import com.jeelowcode.core.framework.service.IDbFormRoleService;
import com.jeelowcode.core.framework.service.IFrameService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import java.util.List;
import java.util.Map;

/**
 * 编辑按钮执行者
 */
public class ButtonReceiverList extends ButtonReceiverBase implements IButtonCommandReceiver<ResultDataModel> {


    private String pluginKey;

    private ButtonParamList param;

    public ButtonReceiverList(ButtonParamList param) {
        this.param = param;
    }

    /**
     * 执行命令
     * @return
     */
    @Override
    public ResultDataModel receiver() {
        IFrameService frameService = SpringUtils.getBean(IFrameService.class);
        IDbFormRoleService dbFormRoleService = SpringUtils.getBean(IDbFormRoleService.class);
        IJeeLowCodeAdapter jeeLowCodeAdapter = SpringUtils.getBean(IJeeLowCodeAdapter.class);
        Long tenantId = jeeLowCodeAdapter.getTenantId();


        Long dbformId = param.getDbFormId();
        Map<String, Object> params = param.getParams();
        if(Func.isNotEmpty(params)){
            params.remove(ParamEnum.ALL_QUERY_FIELD.getCode());//特殊字段不允许用户带过来
        }
        Page page = param.getPage();
        ResultDataModel resultDataModel = frameService.getDataList(dbformId, page, params);

        List<DbFormRoleFieldVo> roleFieldVoList = dbFormRoleService.listRoleField(tenantId, dbformId,false);
        super.webViewAppend(dbformId,resultDataModel.getRecords());//格式化
        super.removeNotWebView(dbformId,resultDataModel.getRecords(),roleFieldVoList);
        return resultDataModel;
    }
}
