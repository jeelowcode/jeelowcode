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
package com.jeelowcode.framework.plus.entity;


import com.jeelowcode.framework.plus.build.ISQLExpression;
import com.jeelowcode.framework.plus.build.buildmodel.SQLInterpretContext;
import com.jeelowcode.framework.plus.chain.entity.DataPermissionFilterEntity;
import com.jeelowcode.framework.plus.chain.entity.TenantIdFilterEntity;
import com.jeelowcode.framework.plus.core.model.SqlFormatModel;


public class SqlInfoEntity extends SqlResultEntity {

    //表达式
    private ISQLExpression<SqlFormatModel> sqlExpression;
    //sql参数上下文
    private SQLInterpretContext context;

    //责任链模式参数
    private String sql;
    //sql解析等级 设置了的话，就单独使用某一个等级
    private Integer sqlParserLevel;

    //租户相关条件过滤
    private TenantIdFilterEntity tenantIdFilterEntity;
    //数据权限相关
    private DataPermissionFilterEntity dataPermissionFilterEntity;


    public ISQLExpression<SqlFormatModel> getSqlExpression() {
        return sqlExpression;
    }

    public void setSqlExpression(ISQLExpression<SqlFormatModel> sqlExpression) {
        this.sqlExpression = sqlExpression;
    }

    public SQLInterpretContext getContext() {
        return context;
    }

    public void setContext(SQLInterpretContext context) {
        this.context = context;
    }

    public String getSql() {
        return sql;
    }

    public void setSql(String sql) {
        this.sql = sql;
    }

    public TenantIdFilterEntity getTenantIdFilterEntity() {
        return tenantIdFilterEntity;
    }

    public void setTenantIdFilterEntity(TenantIdFilterEntity tenantIdFilterEntity) {
        this.tenantIdFilterEntity = tenantIdFilterEntity;
    }

    public DataPermissionFilterEntity getDataPermissionFilterEntity() {
        return dataPermissionFilterEntity;
    }

    public void setDataPermissionFilterEntity(DataPermissionFilterEntity dataPermissionFilterEntity) {
        this.dataPermissionFilterEntity = dataPermissionFilterEntity;
    }

    public Integer getSqlParserLevel() {
        return sqlParserLevel;
    }

    public void setSqlParserLevel(Integer sqlParserLevel) {
        this.sqlParserLevel = sqlParserLevel;
    }
}
