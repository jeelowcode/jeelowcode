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
package com.jeelowcode.framework.plus.chain.entity;

import java.util.Set;

/**
 * 租户权限相关
 */
public class TenantIdFilterEntity {
    private boolean enable;///是否启动

    private Long tenantId;//租户id

    private Set<String> ignoreTables; //不过滤的表

    private Set<String> ignoreUrl;//不过滤的url

    private boolean ignore;



    public Long getTenantId() {
        return tenantId;
    }

    public void setTenantId(Long tenantId) {
        this.tenantId = tenantId;
    }


    public boolean isEnable() {
        return enable;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }

    public Set<String> getIgnoreTables() {
        return ignoreTables;
    }

    public void setIgnoreTables(Set<String> ignoreTables) {
        this.ignoreTables = ignoreTables;
    }

    public boolean isIgnore() {
        return ignore;
    }

    public void setIgnore(boolean ignore) {
        this.ignore = ignore;
    }

    public Set<String> getIgnoreUrl() {
        return ignoreUrl;
    }

    public void setIgnoreUrl(Set<String> ignoreUrl) {
        this.ignoreUrl = ignoreUrl;
    }
}
