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


@Schema(description = "数据统计-字段属性")
@Data
public class ReportFieldVo extends ReportFieldIdVo {

    @Schema(description = "表字段")
    private String fieldCode;

    @Schema(description = "表字段名称")
    private String fieldName;

    @Schema(description = "字段类型")
    private String fieldType;

    @Schema(description = "是否开启数据库查询;N=不开启 Y=开启")
    private String queryIsDb;

    @Schema(description = "是否显示查询控件;N=不显示 Y=显示")
    private String queryIsWeb;

    @Schema(description = "查询模式;EQ=精确  LIKE=模糊")
    private String queryMode;

    @Schema(description = "字典code")
    private String dictCode;

    @Schema(description = "是否支持导出")
    private String isExport;

    @Schema(description = "是否排序;N=不显示 Y=显示")
    private String isShowSort;

    @Schema(description = "字段是否有修改;如果是N并且有id的情况下，不修改")
    private String isModify;
}

