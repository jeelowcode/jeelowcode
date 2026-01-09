package com.jeelowcode.service.bpm.controller.vo.form;


import com.jeelowcode.tool.framework.common.pojo.PageParam;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Schema(description = "管理后台 - 动态表单分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class BpmFormPageReqVO extends PageParam {

    @Schema(description = "表单名称", example = "芋道")
    private String name;

}
