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

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.jeelowcode.framework.utils.model.global.BaseTenantEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Excel 导入文件
 */
@TableName("lowcode_log_excel_file")
@Data
@EqualsAndHashCode(callSuper = true)
public class ExcelFileEntity extends BaseTenantEntity {

    /**
     * 表单开发id
     */
    private Long dbformId;

    /**
     * 文件名
     */
    private String fileName;

    /**
     * 导入状态 -2=已取消 -1=已撤回 0=未导入  1=已导入
     */
    private String importState;

    /**
     * 总条数
     */
    private Integer totalNum;

    /**
     * 成功条数
     */
    private Integer successNum;


    /**
     * 失败条数
     */
    private Integer errorNum;


    /**
     * 预览状态
     */
    @TableField(exist = false)
    private Boolean viewFlag;

}
