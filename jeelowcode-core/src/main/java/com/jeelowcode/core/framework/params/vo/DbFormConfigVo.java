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


import com.jeelowcode.core.framework.entity.*;
import com.jeelowcode.core.framework.entity.*;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class DbFormConfigVo {
    @Schema(description = "字典配置列表")
    List<FormFieldDictEntity> dictList=new ArrayList<>();

    @Schema(description = "导出配置列表")
    List<FormFieldExportEntity> exportList=new ArrayList<>();

    @Schema(description = "外键配置列表")
    List<FormFieldForeignkeyEntity> foreignkeyList=new ArrayList<>();

    @Schema(description = "界面配置列表")
    List<FormFieldWebEntity> webList=new ArrayList<>();

    @Schema(description = "查询配置列表")
    List<FormFieldQueryEntity> queryList=new ArrayList<>();

    @Schema(description = "表单字典列表")
    List<FormFieldEntity> fieldList=new ArrayList<>();

    @Schema(description = "索引列表")
    List<FormIndexEntity> indexList=new ArrayList<>();

    @Schema(description = "统计顶部配置列表")
    List<FormSummaryEntity> summaryTopList=new ArrayList<>();

    @Schema(description = "统计底部配置列表")
    List<FormSummaryEntity> summaryBottomList=new ArrayList<>();

    @Schema(description = "表单属性列表")
    FormEntity dbForm;

}
