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


@Schema(description = "开单开发-基本属性")
@Data
public class DbFormVo {

    @Schema(description = "id 修改时候用到")
    private Long id;

    @Schema(description = "表名")
    private String tableName;

    @Schema(description = "表描述")
    private String tableDescribe;

    @Schema(description = "表类型;1单表、2树表、3主表、4附表")
    private Integer tableType;

    @Schema(description = "表分类")
    private Integer tableClassify;

    @Schema(description = "主键类型")
    private String tableIdType;

    @Schema(description = "表格选择类型;multiple   radio")
    private String tableSelect;

    @Schema(description = "同步数据库状态;N=未同步  Y=已同步")
    private String isDbSync;

    @Schema(description = "是否用设计器表单;N=不 启用  Y=已启用")
    private String isDesForm;

    @Schema(description = "附表-映射关系 many:一对多  one:一对一")
    private String subTableMapping;

    @Schema(description = "附表-排序序号")
    private Integer subTableSort;

    @Schema(description = "附表-标题")
    private String subTableTitle;

    @Schema(description = "主题模板")
    private String themeTemplate;

    @Schema(description = "WEB表单设计ID")
    private Long desformWebId;

    @Schema(description = "树表样式")
    private String treeStyle;

    @Schema(description = "树表模式")
    private String treeMode;

    @Schema(description = "树表展开字段")
    private String treeLabelField;

    @Schema(description = "操作栏样式")
    private String operateMenuStyle;

    @Schema(description = "表单风格")
    private String formStyle;

    @Schema(description = "子表列表")
    private String subTableListStr;

    @Schema(description = "是否显示系统字段;N=不 启用  Y=已启用")
    private String viewDefaultField;

    @Schema(description = "表单开发分组id")
    private Long groupDbformId;

    @Schema(description = "默认排序字段")
    private String orderbyConfig;

    @Schema(description = "默认条件配置")
    private String whereConfig;

    @Schema(description = "数据配置")
    private String dataConfig;

    @Schema(description = "基础功能")
    private String basicFunction;

    @Schema(description = "基础配置")
    private String basicConfig;

    @Schema(description = "基础配置")
    private String tableConfig;

    @Schema(description = "表视图数据来源配置")
    private String dataSourcesConfig;

    @Schema(description = "单表样式")
    private String tableStyle;
}

