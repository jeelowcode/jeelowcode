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
package com.jeelowcode.core.framework.entity;

import com.jeelowcode.framework.utils.model.global.BaseTenantEntity;
import com.baomidou.mybatisplus.annotation.FieldStrategy;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 表单开发
 */
@TableName("lowcode_dbform") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode
public class FormEntity extends BaseTenantEntity {

    /**
     * 表名
     */
    private String tableName;

    /**
     * 表描述
     */
    private String tableDescribe;

    /**
     * 表类型;1单表、2树表、3主表、4附表
     */
    private Integer tableType;

    /**
     * 表分类;
     */
    private Integer tableClassify;

    /**
     * 主键类型;
     */
    private String tableIdType;

    /**
     * 表格选择类型;multiple   radio
     */
    private String tableSelect;

    /**
     * 同步数据库状态;N=未同步  Y=已同步
     */
    private String isDbSync;

    /**
     * 是否用设计器表单;N=不 启用  Y=已启用
     */
    private String isDesForm;

    /**
     * 附表-映射关系
     */
    private String subTableMapping;

    /**
     * 附表-排序序号
     */
    private Integer subTableSort;

    /**
     * 附表-标题
     */
    private String subTableTitle;

    /**
     * 主题模板
     */
    private String themeTemplate;

    /**
     * WEB表单设计ID
     */
    @TableField(updateStrategy = FieldStrategy.ALWAYS)
    private Long desformWebId;

    /**
     * 树表样式
     */
    private String treeStyle;

    /**
     * 树表模式
     */
    private String treeMode;

    /**
     * 树表展开字段
     */
    private String treeLabelField;

    /**
     * 操作栏样式
     */
    private String operateMenuStyle;

    /**
     * 表单风格
     */
    private Integer formStyle;


    /**
     * 表单开发分组id
     */
    @TableField(updateStrategy = FieldStrategy.ALWAYS)
    private Long groupDbformId;

    /**
     * 默认排序字段
     */
    private String orderbyConfig;

    /**
     * 默认条件配置
     */
    private String whereConfig;

    /**
     * 子表列表
     */
    private String subTableListStr;

    /**
     * 是否显示系统字段;N=不 启用  Y=已启用
     */
    private String viewDefaultField;

    /**
     * 数据配置
     */
    private String dataConfig;

    /**
     * 基础功能
     */
    private String basicFunction;

    /**
     * 基础配置
     */
    private String basicConfig;

    /**
     * 表格配置
     */
    private String tableConfig;

    /**
     * 表视图数据来源配置
     */
    private String dataSourcesConfig;

    /**
     * 单表样式
     */
    private String tableStyle;
}

