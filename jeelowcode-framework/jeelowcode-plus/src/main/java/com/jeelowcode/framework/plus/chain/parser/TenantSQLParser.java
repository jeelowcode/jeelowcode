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
package com.jeelowcode.framework.plus.chain.parser;


import com.jeelowcode.framework.plus.chain.AbstractSQLParser;
import com.jeelowcode.framework.plus.chain.entity.TenantIdFilterEntity;
import com.jeelowcode.framework.plus.entity.SqlInfoEntity;
import com.jeelowcode.framework.plus.core.model.SqlFormatModel;
import com.jeelowcode.framework.tenant.utils.JeeLowCodeTenantUtils;
import com.jeelowcode.framework.utils.utils.FuncBase;

import java.util.Set;

/**
 * 多租户
 */
public class TenantSQLParser extends AbstractSQLParser {

    public TenantSQLParser(int level) {
        this.level=level;
    }

    @Override
    protected void sqlParse(SqlInfoEntity sqlInfoEntity) {
       try{
           TenantIdFilterEntity tenantIdFilterEntity = sqlInfoEntity.getTenantIdFilterEntity();
           if(FuncBase.isEmpty(tenantIdFilterEntity)){
               return;
           }

           SqlFormatModel sqlFormatModel = sqlInfoEntity.getSqlModelList().get(0);
           if(FuncBase.isEmpty(sqlFormatModel)){
               return;
           }
           String sql = sqlFormatModel.getSql();


           Long tenantId = tenantIdFilterEntity.getTenantId();
           if(FuncBase.isEmpty(tenantId)){
               return;
           }
           boolean enable = tenantIdFilterEntity.isEnable();
           if(!enable){//没有启动
               return;
           }
           boolean ignore = tenantIdFilterEntity.isIgnore();

           Set<String> ignoreTables = tenantIdFilterEntity.getIgnoreTables();
           Set<String> ignoreUrl = tenantIdFilterEntity.getIgnoreUrl();

           JeeLowCodeTenantUtils.initIgnoreTables(ignoreTables);//初始化
           JeeLowCodeTenantUtils.initIgnoreUrl(ignoreUrl);
           boolean threadIgnore = JeeLowCodeTenantUtils.isIgnore();
           if(!threadIgnore){
               JeeLowCodeTenantUtils.setIgnore(ignore);//本次
           }


           JeeLowCodeTenantUtils.setTenantId(tenantId);
           String newSql = JeeLowCodeTenantUtils.parseTenantSql(sql);
           sqlFormatModel.setSql(newSql);

       }catch (Exception e){
            e.printStackTrace();
       }

    }
}
