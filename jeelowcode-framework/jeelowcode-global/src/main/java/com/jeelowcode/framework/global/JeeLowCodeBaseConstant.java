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
package com.jeelowcode.framework.global;

/**
 * 全局公共
 */
public interface JeeLowCodeBaseConstant {
    String REQUEST_URL_START="/admin-api/jeelowcode";//公共请求
    String BASE_PACKAGES="com.jeelowcode";//公共包名称
    String BASE_PACKAGES_CODE=BASE_PACKAGES+".core";//核心包名
    String BASE_PACKAGES_MODULE = BASE_PACKAGES+".module";//低代码模块
}
