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

import java.util.Date;

/**
 * Excel导入临时库
 * @author JX
 * @create 2024-03-02 11:56
 * @dedescription:
 */
@TableName("lowcode_log_excel_file_data")
@Data
@EqualsAndHashCode(callSuper = true)
public class ExcelFileDataEntity extends BaseTenantEntity {

    /**
     * excel文件表id
     */
    private Long excelFileId;

    /**
     * 序号
     */
    private Integer sort;

    /**
     * Excel数据
     */
    private String dataJson;

    /**
     * 处理状态 ：Y=已处理  N=未处理
     */
    private String handleState;

    /**
     * 处理结果：SUCCESS=成功 FAIL=失败
     */
    private String handleResult;

    /**
     * 处理时间
     */
    private Date handleTime;

    /**
     * 错误原因
     */
    private String errorReason;

    /**
     * 目标数据id
     */
    private Long dataId;
}
