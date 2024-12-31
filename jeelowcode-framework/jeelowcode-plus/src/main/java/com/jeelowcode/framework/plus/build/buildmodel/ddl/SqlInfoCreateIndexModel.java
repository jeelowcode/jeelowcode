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
package com.jeelowcode.framework.plus.build.buildmodel.ddl;


import com.jeelowcode.framework.plus.build.ISQLExpression;
import com.jeelowcode.framework.plus.build.build.ddl.BuildCreateIndex;
import com.jeelowcode.framework.plus.core.model.SqlFormatModel;

import java.util.List;

/**
 * @author JX
 * @create 2024-07-05 17:09
 * @dedescription:
 */
public class SqlInfoCreateIndexModel<T> extends SqlInfoBaseDdlModel {

    String tableName;//表
    //index
    private String indexName;//索引名称

    private List<String> indexFieldCodeList;//索引值


    public static Builder builder(){
        return new Builder();
    }

    public static class Builder{
        private SqlInfoCreateIndexModel sqlInfoCreateModel = new SqlInfoCreateIndexModel();
        ISQLExpression<List<SqlFormatModel>> expression = new BuildCreateIndex();

        public Builder setTableName(String tableName) {
            sqlInfoCreateModel.tableName = tableName;
            return this;
        }

        public Builder setIndexName(String indexName) {
            sqlInfoCreateModel.indexName = indexName;
            return this;
        }

        public Builder setIndexFieldCodeList(List<String> indexFieldCodeList) {
            sqlInfoCreateModel.indexFieldCodeList = indexFieldCodeList;
            return this;
        }

        public SqlInfoCreateIndexModel<BuildCreateIndex> build() {
            ISQLExpression<List<SqlFormatModel>> expression = new BuildCreateIndex();
            sqlInfoCreateModel.setSqlExpression(expression);
            return sqlInfoCreateModel;
        }
    }


    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getIndexName() {
        return indexName;
    }

    public void setIndexName(String indexName) {
        this.indexName = indexName;
    }

    public List<String> getIndexFieldCodeList() {
        return indexFieldCodeList;
    }

    public void setIndexFieldCodeList(List<String> indexFieldCodeList) {
        this.indexFieldCodeList = indexFieldCodeList;
    }

}
