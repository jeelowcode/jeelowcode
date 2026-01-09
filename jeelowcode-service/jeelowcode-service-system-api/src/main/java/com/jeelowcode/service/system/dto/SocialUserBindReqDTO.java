package com.jeelowcode.service.system.dto;

import com.jeelowcode.tool.framework.common.enums.UserTypeEnum;
import com.jeelowcode.tool.framework.common.validation.InEnum;
import com.jeelowcode.service.system.enums.SocialTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * 取消绑定社交用户 Request DTO
 *
 * @author 芋道源码
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SocialUserBindReqDTO {

    /**
     * 用户编号
     */
    @NotNull(message = "用户编号不能为空")
    private Long userId;
    /**
     * 用户类型
     */
    @InEnum(UserTypeEnum.class)
    @NotNull(message = "用户类型不能为空")
    private Integer userType;

    /**
     * 社交平台的类型
     */
    @InEnum(SocialTypeEnum.class)
    @NotNull(message = "社交平台的类型不能为空")
    private Integer socialType;
    /**
     * 授权码
     */
    @NotEmpty(message = "授权码不能为空")
    private String code;
    /**
     * state
     */
    @NotNull(message = "state 不能为空")
    private String state;

}
