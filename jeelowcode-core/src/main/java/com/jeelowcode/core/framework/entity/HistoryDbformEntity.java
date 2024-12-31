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
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 表单开发历史数据
 */
@TableName("lowcode_log_history_dbform")
@Data
@EqualsAndHashCode
public class HistoryDbformEntity extends BaseTenantEntity {


    /**
     * 表单开发id
     */
    private Long dbformId;

    /**
     * 业务类型：java  js  sql button
     */
    private String serviceType;

    /**
     * 业务id
     */
    private Long serviceId;

    /**
     * 业务数据
     */
    private String serviceJson;

    //创建人名称
    private String createUserName;


}
