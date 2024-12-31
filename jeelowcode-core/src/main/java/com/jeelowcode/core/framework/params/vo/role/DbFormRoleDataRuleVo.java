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
package com.jeelowcode.core.framework.params.vo.role;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;


@Schema(description = "表单增强-数据权限-规则")
@Data
public class DbFormRoleDataRuleVo {

    @Schema(description = "id")
    private Long id;

    @Schema(description = "表单id")
    private Long dbformId;

    @Schema(description = "规则名称")
    private String ruleName;

    @Schema(description = "规则类型")
    private String ruleType;

    @Schema(description = "规则字段")
    private String ruleField;

    @Schema(description = "规则条件")
    private String ruleCondition;

    @Schema(description = "规则值")
    private String ruleValue;

    @Schema(description = " 启用状态 N=关闭 Y=启动,列表回显用")
    private String enableState;

    @Schema(description = "规则运行sql")
    private String ruleSql;
}
