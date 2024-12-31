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

import com.jeelowcode.core.framework.entity.EnhanceSqlEntity;

import java.util.List;

/**
 * @author JX
 * @create 2024-08-21 15:52
 * @dedescription:
 */
public class BuildSqlEnhanceContext {


    private EnhanceContext context;

    private List<EnhanceSqlEntity> entitys;

    private List<EnhanceSqlEntity> setOperations;

    public EnhanceContext getContext() {
        return context;
    }

    public void setContext(EnhanceContext context) {
        this.context = context;
    }

    public List<EnhanceSqlEntity> getEntitys() {
        return entitys;
    }

    public void setEntitys(List<EnhanceSqlEntity> entitys) {
        this.entitys = entitys;
    }

    public List<EnhanceSqlEntity> getSetOperations() {
        return setOperations;
    }

    public void setSetOperations(List<EnhanceSqlEntity> setOperations) {
        this.setOperations = setOperations;
    }

    public BuildSqlEnhanceContext(EnhanceContext context, List<EnhanceSqlEntity> entitys, List<EnhanceSqlEntity> setOperations) {
        this.context = context;
        this.entitys = entitys;
        this.setOperations = setOperations;
    }
}
