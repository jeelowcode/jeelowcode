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

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.apache.ibatis.type.JdbcType;

import java.time.LocalDateTime;

/**
 * 请求日志-错误日志
 *
 */
@TableName("lowcode_log_api_error")
@Data
@EqualsAndHashCode
public class LogRequestErrorApiEntity {
    @JsonSerialize(
            using = ToStringSerializer.class
    )
    @TableId(
            value = "id",
            type = IdType.ASSIGN_ID
    )
    private Long id;


    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT, jdbcType = JdbcType.DATE)
    private LocalDateTime createTime;

    /**
     * 创建者
     *
     */
    @TableField(fill = FieldFill.INSERT, jdbcType = JdbcType.BIGINT)
    private Long createUser;

    private String createUserName;

    /**
     * 创建部门
     *
     */
    @TableField(fill = FieldFill.INSERT, jdbcType = JdbcType.BIGINT)
    private Long createDept;


    /**
     * 更新者
     *
     */
    @TableField(fill = FieldFill.INSERT_UPDATE, jdbcType = JdbcType.BIGINT)
    private Long updateUser;

    /**
     * 最后更新时间
     */
    @TableField(fill = FieldFill.INSERT_UPDATE, jdbcType = JdbcType.DATE)
    private LocalDateTime updateTime;

    /**
     * 是否删除
     */
    private Integer isDeleted;

    /**
     * 租户id
     */
    private String tenantId;

    //ip
    private String ip;

    //标题
    private String title;

    //模块名称
    private String modelTitle;

    //方法名称
    private String methodName;

    //类名称
    private String methodClass;

    //耗时
    private Long time;

    //请求地址
    private String requestUri;

    //请求方式
    private String requestMethod;

    //请求参数
    private String requestParams;

    //错误原因
    private String error;

}

