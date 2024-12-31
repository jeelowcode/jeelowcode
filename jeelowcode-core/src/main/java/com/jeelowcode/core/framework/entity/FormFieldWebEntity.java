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
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

/**
 * 表单开发-页面字段表
 */
@TableName("lowcode_dbform_field_web")
@Data
@EqualsAndHashCode(callSuper = true)
public class FormFieldWebEntity extends BaseTenantEntity {

    /**
     * 表单开发id
     */
    private Long dbformId;

    /**
     * 字段
     */
    private String fieldCode;

    /**
     * 数据库是否查询;N=不显示 Y=显示
     */
    private String isDbSelect;

    /**
     * 列表是否显示;N=不显示 Y=显示
     */
    private String isShowList;

    /**
     * 表单是否显示;N=不显示 Y=显示
     */
    private String isShowForm;

    /**
     * 是否可控;N=不显示 Y=显示
     */
    private String isShowColumn;

    /**
     * 是否排序;N=不显示 Y=显示
     */
    private String isShowSort;

    /**
     * 是否必填;N=否 Y=是
     */
    private String isRequired;

    /**
     * 控件类型
     */
    private String controlType;

    /**
     * 控件配置
     */
    private String controlsConfig;

    /**
     * 列宽
     */
    private String cellWidth;

    /**
     * 列宽类型
     */
    private String cellWidthType;

    /**
     * 校验配置
     */
    private String verifyConfig;

    /**
     * 格式化配置
     */
    private String formatConfig;
}

