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
package com.jeelowcode.framework.plus.build.buildmodel.dql;

import com.jeelowcode.framework.plus.build.ISQLExpression;
import com.jeelowcode.framework.plus.build.build.dql.BuildJoin;
import com.jeelowcode.framework.plus.build.buildmodel.SqlInfoBaseModel;
import com.jeelowcode.framework.plus.core.model.SqlFormatModel;

/**
 * @author JX
 * @create 2024-07-04 9:14
 * @dedescription:
 */
public class SqlInfoJoinModel<T> extends SqlInfoBaseModel {

    private String tableName;
    private JoinType joinType;
    private String onStr;

    public static Builder builder(){
        return new Builder();
    }

    public static class Builder{
        private SqlInfoJoinModel joinModel = new SqlInfoJoinModel();

        //构建having
        public Builder joinTable(String tableName,String alias){
            joinModel.setTableName(tableName+" "+alias);
            return this;
        }
        public Builder innerJoin(){
            joinModel.joinType= JoinType.INNER_JOIN;
            return this;
        }
        public Builder leftJoin(){
            joinModel.joinType= JoinType.LEFT_JOIN;
            return this;
        }
        public Builder rightJoin(){
            joinModel.joinType= JoinType.RIGHT_JOIN;
            return this;
        }
        public Builder on(String leftStr,String rightStr){
            joinModel.onStr=leftStr+" = "+rightStr;
            return this;
        }
        public SqlInfoJoinModel<SqlFormatModel> build(){
            ISQLExpression<SqlFormatModel> expression = new BuildJoin();
            joinModel.setSqlExpression(expression);
            return joinModel;
        }
    }

    public enum JoinType{
        LEFT_JOIN,
        RIGHT_JOIN,
        INNER_JOIN
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public JoinType getJoinType() {
        return joinType;
    }

    public void setJoinType(JoinType joinType) {
        this.joinType = joinType;
    }

    public String getOnStr() {
        return onStr;
    }

    public void setOnStr(String onStr) {
        this.onStr = onStr;
    }
}
