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

import com.baomidou.mybatisplus.annotation.TableName;
import com.jeelowcode.framework.utils.model.global.BaseTenantEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 报表统计-字段配置
 */
@TableName("lowcode_report_field")
@Data
@EqualsAndHashCode
public class ReportFieldEntity extends BaseTenantEntity {

    /**
     * 报表id
     */
    private Long reportId;

    /**
     * 字段
     */
    private String fieldCode;

    /**
     * 字段名称
     */
    private String fieldName;

    /**
     * 字段类型
     */
    private String fieldType;

    /**
     * 排序
     */
    private Integer sortNum;

    /**
     * 是否开启数据库查询;N=不开启 Y=开启
     */
    private String queryIsDb;

    /**
     * 是否显示查询控件;N=不显示 Y=显示
     */
    private String queryIsWeb;

    /**
     * 查询模式;EQ=精确  LIKE=模糊
     */
    private String queryMode;

    /**
     * 字典code
     */
    private String dictCode;

    /**
     * 是否支持导出
     */
    private String isExport;

    /**
     * 是否排序;N=不显示 Y=显示
     */
    private String isShowSort;

}

