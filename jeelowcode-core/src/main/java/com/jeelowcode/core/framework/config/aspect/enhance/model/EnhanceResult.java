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
package com.jeelowcode.core.framework.config.aspect.enhance.model;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * @author JX
 * @create 2024-08-15 10:02
 * @dedescription:
 */
public class EnhanceResult implements Serializable {
    private boolean exitFlag = false;//是否退出

    //表单
    private String id;

    //列表
    private Long total;
    private List<Map<String, Object>> records;

    public boolean isExitFlag() {
        return exitFlag;
    }

    public void setExitFlag(boolean exitFlag) {
        this.exitFlag = exitFlag;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }

    public List<Map<String, Object>> getRecords() {
        return records;
    }

    public void setRecords(List<Map<String, Object>> records) {
        this.records = records;
    }

    public EnhanceResult() {
    }

    public EnhanceResult(boolean exitFlag, String id, Long total, List<Map<String, Object>> records) {
        this.exitFlag = exitFlag;
        this.id = id;
        this.total = total;
        this.records = records;
    }
}
