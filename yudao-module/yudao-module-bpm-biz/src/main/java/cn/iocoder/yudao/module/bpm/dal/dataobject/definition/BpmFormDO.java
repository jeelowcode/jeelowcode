package cn.iocoder.yudao.module.bpm.dal.dataobject.definition;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.*;
import org.apache.ibatis.type.JdbcType;

import java.time.LocalDateTime;

/**
 * 工作流的表单定义
 * 用于工作流的申请表单，需要动态配置的场景
 *
 * @author 芋道源码
 */
@TableName(value = "lowcode_desform", autoResultMap = true)
@Data
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BpmFormDO{

    @JsonSerialize(
            using = ToStringSerializer.class
    )
    @TableId(
            value = "id",
            type = IdType.ASSIGN_ID
    )
    private Long id;

    /**
     * 租户id
     */
    private String tenantId;

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


    //名称
    private String desformName;

    //json数据
    private String desformJson;



}
