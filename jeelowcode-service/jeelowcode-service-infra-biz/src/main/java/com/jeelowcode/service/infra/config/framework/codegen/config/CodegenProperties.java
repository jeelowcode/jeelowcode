package com.jeelowcode.service.infra.config.framework.codegen.config;

import com.jeelowcode.service.infra.config.enums.codegen.CodegenFrontTypeEnum;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Collection;

@ConfigurationProperties(prefix = "jeelowcode.codegen")
@Validated
@Data
public class CodegenProperties {

    /**
     * 生成的 Java 代码的基础包
     */
    @NotNull(message = "Java 代码的基础包不能为空")
    private String basePackage="com.jeelowcode";

    /**
     * 数据库名数组
     */
    //@NotEmpty(message = "数据库不能为空")
    private Collection<String> dbSchemas=new ArrayList<>();

    /**
     * 代码生成的前端类型（默认）
     *
     * 枚举 {@link CodegenFrontTypeEnum#getType()}
     */
    @NotNull(message = "代码生成的前端类型不能为空")
    private Integer frontType=10;

}
