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

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * @author JX
 * @create 2024-08-15 10:02
 * @dedescription:
 */
public class EnhanceParam implements Serializable {

    private Long dbFormId;

    private Map<String, Object> params;

    private List<Map<String, Object>> list;

    private Long dataId;

    private Page page;

    private List<Long> dataIdList;
    public Long getDbFormId() {
        return dbFormId;
    }

    public void setDbFormId(Long dbFormId) {
        this.dbFormId = dbFormId;
    }

    public Map<String, Object> getParams() {
        return params;
    }

    public void setParams(Map<String, Object> params) {
        this.params = params;
    }

    public List<Map<String, Object>> getList() {
        return list;
    }

    public void setList(List<Map<String, Object>> list) {
        this.list = list;
    }

    public EnhanceParam() {
    }

    public Long getDataId() {
        return dataId;
    }

    public void setDataId(Long dataId) {
        this.dataId = dataId;
    }

    public Page getPage() {
        return page;
    }

    public void setPage(Page page) {
        this.page = page;
    }

    public List<Long> getDataIdList() {
        return dataIdList;
    }

    public void setDataIdList(List<Long> dataIdList) {
        this.dataIdList = dataIdList;
    }

    public EnhanceParam(Long dbFormId, Map<String, Object> params, List<Map<String, Object>> list) {
        this.dbFormId = dbFormId;
        this.params = params;
        this.list = list;
    }

    public EnhanceParam(Long dbFormId, Map<String, Object> params, List<Map<String, Object>> list, Long dataId, Page page, List<Long> dataIdList) {
        this.dbFormId = dbFormId;
        this.params = params;
        this.list = list;
        this.dataId = dataId;
        this.page = page;
        this.dataIdList = dataIdList;
    }
}
