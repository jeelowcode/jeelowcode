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
import com.jeelowcode.framework.plus.build.buildmodel.dql.SqlInfoUpdateModel;
import com.jeelowcode.framework.plus.core.dql.update.JeeLowCodeUpdateWrapper;
import com.jeelowcode.framework.plus.core.model.SqlFormatModel;
import com.jeelowcode.framework.utils.utils.FuncBase;

import java.util.*;

/**
 * 构建 update 语句
 */
public class BuildUpdate implements ISQLExpression<SqlFormatModel> {



    @Override
    public SqlFormatModel interpret(SQLInterpretContext context){
        SqlInfoUpdateModel sqlInfo = context.getUpdateModel();
        String tableName = sqlInfo.getTableName();
        JeeLowCodeUpdateWrapper updateWrapper = new JeeLowCodeUpdateWrapper(tableName);


        Map<String, Object> updateMap = sqlInfo.getUpdateMap();
        updateMap.forEach((k,v)->{
            updateWrapper.set(k,v);
        });

        String sqlSet = updateWrapper.getSqlSet();
        Map<String,Object> paramNameValuePairs = updateWrapper.getParamNameValuePairs();

        String newSqlSet="";


        List<String> setList = FuncBase.toStrList(sqlSet);
        for(String set:setList){
            String[] split = set.split("=");
            String field=split[0];
            String value=split[1];
            if(!updateMap.containsKey(field)){//不存在,指定jdbc类型，不然oracle报错
                value=value.substring(0,value.length()-1)+",jdbcType=VARCHAR}";
            }else if(FuncBase.isEmpty(updateMap.get(field))){//为空,指定jdbc类型，不然oracle报错
                value=value.substring(0,value.length()-1)+",jdbcType=VARCHAR}";
            }
            if(FuncBase.isEmpty(newSqlSet)){
                newSqlSet=field+"="+value;
            }else{
                newSqlSet+=","+field+"="+value;
            }
        }

        //处理null值
        for (Map.Entry<String,Object> entry : paramNameValuePairs.entrySet()) {
            Object value = entry.getValue();
            if(FuncBase.isEmpty(value)){
                entry.setValue(null);
            }
        }

        String sql = "update " + tableName + " set " + newSqlSet ;
        SqlFormatModel sqlFormatModel = new SqlFormatModel(sql, paramNameValuePairs);

        return sqlFormatModel;
    }


}
