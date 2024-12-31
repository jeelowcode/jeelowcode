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
package com.jeelowcode.core.framework.params.vo.webconfig;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

@Data
public class WebConfigVo {

    @Schema(description = "属性列表")
    List<WebConfigFieldVo> fieldList;

    @Schema(description = "表单属性列表")
    WebConfigFormVo dbForm;

    @Schema(description = "按钮增强列表")
    List<WebConfigButtonVo> buttonList;

    @Schema(description = "js增强列表")
    List<WebConfigJsVo> jsList;

    @Schema(description = "附表DbFormId信息")
    List<Long> subDbFormIdList;

    @Schema(description = "顶部统计配置")
    boolean summaryTopOpenFlag;

    @Schema(description = "租户权限列表-字段权限")
    List<WebConfigRoleFieldVo> webConfigRoleFieldVoList;

    @Schema(description = "租户权限列表-按钮权限")
    List<String> webConfigRoleButtonVoList;
}
