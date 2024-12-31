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
package com.jeelowcode.framework.plus.build.buildmodel;

import com.jeelowcode.framework.plus.build.buildmodel.dql.*;
import com.jeelowcode.framework.plus.build.buildmodel.ddl.*;
import com.jeelowcode.framework.plus.build.buildmodel.ddl.*;
import com.jeelowcode.framework.plus.entity.DbColunmTypesEntity;
import com.jeelowcode.framework.plus.build.buildmodel.dql.*;

/**
 * @author JX
 * @create 2024-07-23 9:07
 * @dedescription:
 */
public class SQLInterpretContext {

    private String preSymbol="ew.";

    private String schemaName;

    private SqlInfoInsertModel insertModel;

    private SqlInfoDeleteModel deleteModel;

    private SqlInfoAlterModel alterModel;

    private SqlInfoSelectModel selectModel;

    private SqlInfoCreateModel createModel;

    private SqlInfoCreateIndexModel createIndexModel;

    private SqlInfoUpdateModel updateModel;

    private SqlInfoDropModel dropModel;

    private SqlInfoWhereModel whereModel;

    private SqlInfoOrderModel orderModel;

    private SqlInfoGroupModel groupModel;

    private SqlInfoHavingModel havingModel;

    private SqlInfoJoinModel joinModel;

    private ExpressionModel expressionModel;

    private DbColunmTypesEntity dbColunmTypes;

    private SqlInfoCommentModel commentModel;

    private SqlInfoPrimaryKeyModel primaryKeyModel;

    private SqlInfoDdlModel ddlModel;

    private SqlInfoPublicModel publicModel;


    public SQLInterpretContext(String schemaName) {
        this.schemaName = schemaName;
    }


    ///===========================================================================


    public String getSchemaName() {
        return schemaName;
    }

    public void setSchemaName(String schemaName) {
        this.schemaName = schemaName;
    }

    public SqlInfoInsertModel getInsertModel() {
        return insertModel;
    }

    public void setInsertModel(SqlInfoInsertModel insertModel) {
        this.insertModel = insertModel;
    }

    public SqlInfoDeleteModel getDeleteModel() {
        return deleteModel;
    }

    public void setDeleteModel(SqlInfoDeleteModel deleteModel) {
        this.deleteModel = deleteModel;
    }

    public SqlInfoAlterModel getAlterModel() {
        return alterModel;
    }

    public void setAlterModel(SqlInfoAlterModel alterModel) {
        this.alterModel = alterModel;
    }

    public SqlInfoSelectModel getSelectModel() {
        return selectModel;
    }

    public void setSelectModel(SqlInfoSelectModel selectModel) {
        this.selectModel = selectModel;
    }

    public SqlInfoCreateModel getCreateModel() {
        return createModel;
    }

    public void setCreateModel(SqlInfoCreateModel createModel) {
        this.createModel = createModel;
    }

    public SqlInfoCreateIndexModel getCreateIndexModel() {
        return createIndexModel;
    }

    public void setCreateIndexModel(SqlInfoCreateIndexModel createIndexModel) {
        this.createIndexModel = createIndexModel;
    }

    public SqlInfoUpdateModel getUpdateModel() {
        return updateModel;
    }

    public void setUpdateModel(SqlInfoUpdateModel updateModel) {
        this.updateModel = updateModel;
    }

    public SqlInfoDropModel getDropModel() {
        return dropModel;
    }

    public void setDropModel(SqlInfoDropModel dropModel) {
        this.dropModel = dropModel;
    }

    public SqlInfoWhereModel getWhereModel() {
        return whereModel;
    }

    public void setWhereModel(SqlInfoWhereModel whereModel) {
        this.whereModel = whereModel;
    }

    public SqlInfoOrderModel getOrderModel() {
        return orderModel;
    }

    public void setOrderModel(SqlInfoOrderModel orderModel) {
        this.orderModel = orderModel;
    }

    public SqlInfoGroupModel getGroupModel() {
        return groupModel;
    }

    public void setGroupModel(SqlInfoGroupModel groupModel) {
        this.groupModel = groupModel;
    }

    public SqlInfoHavingModel getHavingModel() {
        return havingModel;
    }

    public void setHavingModel(SqlInfoHavingModel havingModel) {
        this.havingModel = havingModel;
    }

    public SqlInfoJoinModel getJoinModel() {
        return joinModel;
    }

    public void setJoinModel(SqlInfoJoinModel joinModel) {
        this.joinModel = joinModel;
    }

    public ExpressionModel getExpressionModel() {
        return expressionModel;
    }

    public void setExpressionModel(ExpressionModel expressionModel) {
        this.expressionModel = expressionModel;
    }

    public DbColunmTypesEntity getDbColunmTypes() {
        return dbColunmTypes;
    }

    public void setDbColunmTypes(DbColunmTypesEntity dbColunmTypes) {
        this.dbColunmTypes = dbColunmTypes;
    }

    public SqlInfoCommentModel getCommentModel() {
        return commentModel;
    }

    public void setCommentModel(SqlInfoCommentModel commentModel) {
        this.commentModel = commentModel;
    }

    public SqlInfoPrimaryKeyModel getPrimaryKeyModel() {
        return primaryKeyModel;
    }

    public void setPrimaryKeyModel(SqlInfoPrimaryKeyModel primaryKeyModel) {
        this.primaryKeyModel = primaryKeyModel;
    }

    public SqlInfoDdlModel getDdlModel() {
        return ddlModel;
    }

    public void setDdlModel(SqlInfoDdlModel ddlModel) {
        this.ddlModel = ddlModel;
    }

    public String getPreSymbol() {
        return preSymbol;
    }

    public void setPreSymbol(String preSymbol) {
        this.preSymbol = preSymbol;
    }

    public SqlInfoPublicModel getPublicModel() {
        return publicModel;
    }

    public void setPublicModel(SqlInfoPublicModel publicModel) {
        this.publicModel = publicModel;
    }
}
