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
package com.jeelowcode.core.framework.params.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * @author JX
 * @create 2024-03-06 10:27
 * @dedescription:
 */

@Schema(description = "表单开发-Sql增强")
@Data
public class DbFormEnhanceSqlVo{

    @Schema(description = "id 修改时候用到")
    private Long id;

    @Schema(description = "租户编号")
    private Integer tenantId;

    @Schema(description = "创建者所属部门")
    private String createDept;

    @Schema(description = "表单开发id")
    private Long dbformId;

    @Schema(description = "按钮编码")
    private String buttonCode;

    @Schema(description = "执行SQL")
    private String executeSql;

    @Schema(description = "备注")
    private String remark;


}
