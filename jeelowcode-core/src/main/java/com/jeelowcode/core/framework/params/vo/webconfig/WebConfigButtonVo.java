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
package com.jeelowcode.core.framework.params.vo.webconfig;


import lombok.Data;

@Data
public class WebConfigButtonVo {

    /**
     * 按钮名称
     */
    private String buttonName;

    /**
     * 按钮编码
     */
    private String buttonCode;

    /**
     * 按钮图标
     */
    private String buttonIcon;

    /**
     * 按钮位置
     */
    private String buttonLocation;

    /**
     * 按钮类型
     */
    private String buttonType;

    /**
     * 按钮样式
     */
    private String buttonSort;

    /**
     * 按钮显隐增强
     */
    private String buttonExp;

    /**
     * 按钮显示
     */
    private String buttonShow;

    /**
     * 权限控制
     */
    private String buttonAuth;
}
