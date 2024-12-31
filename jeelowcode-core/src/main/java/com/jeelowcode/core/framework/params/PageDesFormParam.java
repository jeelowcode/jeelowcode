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
package com.jeelowcode.core.framework.params;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class PageDesFormParam {
    @Schema(description = "id")
    private Long id;

    @Schema(description = "表单设计名称")
    private String desformName;

    @Schema(description = "功能类型")
    private String functionType;

    @Schema(description = "是否开放")
    private String isOpen;

    @Schema(description = "是否模板")
    private String isTemplate;

    //表单设计分组id
    private Long groupDesformId;
}
