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

import com.baomidou.mybatisplus.annotation.FieldStrategy;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.jeelowcode.framework.utils.model.global.BaseTenantEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 数据报表
 */
@TableName("lowcode_report") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode
public class ReportEntity extends BaseTenantEntity {

    /**
     * 报表名称
     */
    private String reportName;

    /**
     * 报表编号
     */
    private String reportCode;

    /**
     * 分组id
     */
    @TableField(updateStrategy = FieldStrategy.ALWAYS)
    private Long groupReportId;

    /**
     * JAVA配置
     */
    private String javaConfig;

    /**
     * 数据配置
     */
    private String dataConfig;

    /**
     * 表格配置
     */
    private String tableConfig;

    /**
     * 表视图数据来源配置
     */
    private String dataSourcesConfig;


}

