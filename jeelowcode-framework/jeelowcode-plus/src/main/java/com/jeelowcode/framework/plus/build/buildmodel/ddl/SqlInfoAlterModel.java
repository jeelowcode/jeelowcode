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
import com.jeelowcode.framework.plus.build.build.ddl.BuildAlter;
import com.jeelowcode.framework.plus.entity.FieldModel;
import com.jeelowcode.framework.plus.core.model.SqlFormatModel;

import java.util.List;

/**
 * @author JX
 * @create 2024-07-06 11:45
 * @dedescription:
 */
public class SqlInfoAlterModel<T> extends SqlInfoBaseDdlModel {

    private AlterType alterType;//操作类型

    private String tableName;//表名称
    //addColumn
    private FieldModel fieldModel;//字段属性


    private SqlInfoAlterModel(){}


    public enum AlterType{
        ADD_COLUMN,   //增加字段
        UPDATE_COLUMN    //修改字段
    }

    public static Builder builder(){
        return new Builder();
    }
    public static class Builder{

        private SqlInfoAlterModel alterModel = new SqlInfoAlterModel();
        ISQLExpression<List<SqlFormatModel>> alterExpression = new BuildAlter();

        public Builder setTableName(String tableName){
            alterModel.tableName = tableName;
            return this;
        }
        //增加字段
        public SqlInfoAlterModel<BuildAlter> buildAddColumn(FieldModel fieldModel){
            alterModel.alterType = AlterType.ADD_COLUMN;
            alterModel.fieldModel =fieldModel;
            alterModel.setSqlExpression(alterExpression);
            return alterModel;
        }

        //更新字段
        public SqlInfoAlterModel<BuildAlter> buildUpdateColum(FieldModel fieldModel){
            alterModel.alterType = AlterType.UPDATE_COLUMN;
            alterModel.fieldModel =fieldModel;
            alterModel.setSqlExpression(alterExpression);
            return alterModel;
        }

    }


    public AlterType getAlterType() {
        return alterType;
    }

    public void setAlterType(AlterType alterType) {
        this.alterType = alterType;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public FieldModel getFieldModel() {
        return fieldModel;
    }

    public void setFieldModel(FieldModel fieldModel) {
        this.fieldModel = fieldModel;
    }


}
