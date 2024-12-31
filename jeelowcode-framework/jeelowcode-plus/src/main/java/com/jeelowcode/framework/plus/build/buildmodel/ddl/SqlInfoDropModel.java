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
import com.jeelowcode.framework.plus.build.build.ddl.BuildDrop;
import com.jeelowcode.framework.plus.core.model.SqlFormatModel;

import java.util.List;

/**
 * @author JX
 * @create 2024-07-06 11:45
 * @dedescription:
 */
public class SqlInfoDropModel<T> extends SqlInfoBaseDdlModel {

    private DropType dropType;//操作类型

    String tableName;//表

    private String cloumnCode;//字段
    private String cloumnFormat;//格式化

    private String indexName;//索引名称

    private SqlInfoDropModel(){}

    public static Builder builder(){
        return new Builder();
    }


    public static class Builder{

        private SqlInfoDropModel dropModel = new SqlInfoDropModel();

        ISQLExpression<List<SqlFormatModel>> expression = new BuildDrop();

        public SqlInfoDropModel<BuildDrop> buildDropTable(String tableName){
            dropModel.tableName = tableName;
            dropModel.dropType = DropType.DROP_TABLE;
            dropModel.setSqlExpression(expression);
            return dropModel;
        }
        public SqlInfoDropModel<BuildDrop> buildDropColumn(String tableName,String column){
            dropModel.tableName = tableName;
            dropModel.cloumnCode=column;
            dropModel.dropType = DropType.DROP_COLUMN;
            dropModel.setSqlExpression(expression);
            return dropModel;
        }
        public SqlInfoDropModel<BuildDrop> buildDropIndex(String tableName,String indexName){
            dropModel.tableName = tableName;
            dropModel.dropType = DropType.DROP_INDEX;
            dropModel.indexName=indexName;
            dropModel.setSqlExpression(expression);
            return dropModel;
        }

    }


    public enum DropType{
        DROP_TABLE,   //删除表
        DROP_COLUMN, //删除字段
        DROP_INDEX  //删除索引
    }

    public DropType getDropType() {
        return dropType;
    }

    public void setDropType(DropType dropType) {
        this.dropType = dropType;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getCloumnCode() {
        return cloumnCode;
    }

    public void setCloumnCode(String cloumnCode) {
        this.cloumnCode = cloumnCode;
    }

    public String getIndexName() {
        return indexName;
    }

    public void setIndexName(String indexName) {
        this.indexName = indexName;
    }

    public String getCloumnFormat() {
        return cloumnFormat;
    }

    public void setCloumnFormat(String cloumnFormat) {
        this.cloumnFormat = cloumnFormat;
    }
}
