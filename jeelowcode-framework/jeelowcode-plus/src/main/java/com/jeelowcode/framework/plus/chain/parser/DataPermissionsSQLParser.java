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
import com.jeelowcode.framework.plus.chain.entity.DataPermissionFilterEntity;
import com.jeelowcode.framework.plus.entity.SqlInfoEntity;
import com.jeelowcode.framework.plus.core.model.SqlFormatModel;
import com.jeelowcode.framework.utils.utils.FuncBase;

/**
 * 数据权限
 */
public class DataPermissionsSQLParser extends AbstractSQLParser {

    public DataPermissionsSQLParser(int level) {
        this.level = level;
    }

    @Override
    protected void sqlParse(SqlInfoEntity sqlInfoEntity) {
        try {
            DataPermissionFilterEntity dataPermissionFilterEntity = sqlInfoEntity.getDataPermissionFilterEntity();
            SqlFormatModel sqlFormatModel = sqlInfoEntity.getSqlModelList().get(0);
            if(FuncBase.isEmpty(sqlFormatModel)){
                return;
            }
            if (FuncBase.isEmpty(dataPermissionFilterEntity)) {
                return;
            }
            boolean enable = dataPermissionFilterEntity.isEnable();
            if (!enable) {//没有启动
                return;
            }

            String sql = sqlFormatModel.getSql();

            String newSql = sql+" and deptId=1";

            sqlFormatModel.setSql(newSql);
            return;
        } catch (Exception e) {

        }
    }
}
