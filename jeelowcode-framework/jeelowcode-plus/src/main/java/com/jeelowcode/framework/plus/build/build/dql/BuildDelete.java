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
package com.jeelowcode.framework.plus.build.build.dql;

import com.jeelowcode.framework.plus.build.ISQLExpression;
import com.jeelowcode.framework.plus.build.buildmodel.SQLInterpretContext;
import com.jeelowcode.framework.plus.build.buildmodel.dql.SqlInfoDeleteModel;
import com.jeelowcode.framework.plus.core.dql.update.JeeLowCodeUpdateWrapper;
import com.jeelowcode.framework.plus.core.model.SqlFormatModel;
import com.jeelowcode.framework.utils.utils.FuncBase;

import java.util.Map;

/**
 * 构建 删除 语句
 */
public class BuildDelete implements ISQLExpression<SqlFormatModel> {


    public SqlFormatModel interpret(SQLInterpretContext context){
        SqlInfoDeleteModel sqlInfo = context.getDeleteModel();
        String tableName = sqlInfo.getTableName();
        Boolean isReal = sqlInfo.getReal();


        SqlFormatModel sqlFormatModel;
        if (isReal){//真实删除
            String sql = "Delete from " + tableName;
            sqlFormatModel = new SqlFormatModel(sql);
        }else {//逻辑删除
            JeeLowCodeUpdateWrapper updateWrapper = new JeeLowCodeUpdateWrapper<>();
            updateWrapper.set("is_deleted",-1);

            String sqlSet = updateWrapper.getSqlSet();
            Map<String,Object> paramNameValuePairs = updateWrapper.getParamNameValuePairs();

            //处理null值
            for (Map.Entry<String,Object> entry : paramNameValuePairs.entrySet()) {
                Object value = entry.getValue();
                if(FuncBase.isEmpty(value)){
                    entry.setValue(null);
                }
            }

            String sql = "update " + tableName + " set " + sqlSet ;
            sqlFormatModel = new SqlFormatModel(sql, paramNameValuePairs);
        }
        return  sqlFormatModel;

    }


}
