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
package com.jeelowcode.framework.utils.model.global;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.apache.ibatis.type.JdbcType;

import java.io.Serializable;
import java.sql.Date;
import java.time.LocalDateTime;

/**
 * 公共实体对象
 */
@Data
@EqualsAndHashCode
public class BaseEntity implements Serializable {

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
    @TableField(fill = FieldFill.INSERT,jdbcType = JdbcType.DATE)
    private LocalDateTime createTime;

    /**
     * 创建者
     *
     */
    @TableField(fill = FieldFill.INSERT, jdbcType = JdbcType.BIGINT)
    private Long createUser;

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
    @TableField(fill = FieldFill.INSERT_UPDATE,jdbcType = JdbcType.DATE)
    private LocalDateTime updateTime;

    /**
     * 是否删除
     */
    @TableLogic
    private Integer isDeleted;

}
