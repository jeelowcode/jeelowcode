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

import lombok.*;

/**
 * 增强-JAVA增强
 * @author JX
 * @create 2024-03-02 11:21
 * @dedescription:
 */
@TableName("lowcode_dbform_enhance_java")
@Data
@EqualsAndHashCode
public class EnhanceJavaEntity extends BaseTenantEntity {

    /**
     * 表单开发id
     */
    private Long dbformId;

    /**
     * 按钮编码
     */
    private String buttonCode;

    /**
     * java类型;spring   class http
     */
    private String javaType;

    /**
     * java类路径
     */
    private String javaClassUrl;

    /**
     * 在线脚本
     */
    private String onlineScript;

    /**
     * 生效状态;N=不生效 Y=生效
     */
    private String activeStatus;

    /**
     * 备注
     */
    private String remark;

    /**
     * 结果处理类型:只针对列表，导出，列表和导出
     * 默认 =0 也就是把上一个结果带到下一个结果
     * 合集 =1
     * 差集 =2
     * 并集 =3
     * 交集=4
     */
    private String listResultHandleType;

    /**
     * 排序
     */
    private Integer sort;

}
