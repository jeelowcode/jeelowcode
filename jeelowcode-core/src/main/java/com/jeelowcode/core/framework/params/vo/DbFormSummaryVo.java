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
package com.jeelowcode.core.framework.params.vo;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Schema(description = "开单开发-统计配置")
@Data
public class DbFormSummaryVo extends DbFormFieldIdVo {

    @Schema(description = "表字段")
    private String fieldCode;

    @Schema(description = "是否显示;N=不开启 Y=开启")
    private String summaryShow;

    @Schema(description = "统计类型：底部=bottom，顶部=top")
    private String summaryType;

    @Schema(description = "执行sql")
    private String summarySql;


    @Schema(description = "标签")
    private String summaryLabel;

    @Schema(description = "其他配置")
    private String summaryJson;

    @Schema(description = "字段是否有修改;如果是N并且有id的情况下，不修改")
    private String isModify;

}

