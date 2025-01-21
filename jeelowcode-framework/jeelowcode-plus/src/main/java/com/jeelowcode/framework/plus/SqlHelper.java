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
*//*
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
package com.jeelowcode.framework.plus;


import com.jeelowcode.framework.plus.build.ISQLExpression;
import com.jeelowcode.framework.plus.build.build.AndExpression;
import com.jeelowcode.framework.plus.build.build.ddl.*;
import com.jeelowcode.framework.plus.build.build.dql.BuildSelect;
import com.jeelowcode.framework.plus.build.buildmodel.SQLInterpretContext;
import com.jeelowcode.framework.plus.build.buildmodel.ddl.*;
import com.jeelowcode.framework.plus.build.buildmodel.dql.SqlInfoPublicModel;
import com.jeelowcode.framework.plus.build.buildmodel.wrapper.SqlInfoDeleteWrapper;
import com.jeelowcode.framework.plus.build.buildmodel.wrapper.SqlInfoInsertWrapper;
import com.jeelowcode.framework.plus.build.buildmodel.wrapper.SqlInfoQueryWrapper;
import com.jeelowcode.framework.plus.build.buildmodel.wrapper.SqlInfoUpdateWrapper;
import com.jeelowcode.framework.plus.chain.entity.TenantIdFilterEntity;
import com.jeelowcode.framework.plus.entity.DbColunmTypesEntity;
import com.jeelowcode.framework.plus.entity.FieldModel;
import com.jeelowcode.framework.plus.entity.SqlInfoEntity;
import com.jeelowcode.framework.plus.enums.DbTypeEnum;
import com.jeelowcode.framework.plus.flyweight.FlyweightDBFactory;
import com.jeelowcode.framework.plus.template.AbstractDbTemplate;
import com.jeelowcode.framework.plus.core.model.SqlFormatModel;
import com.jeelowcode.framework.utils.adapter.IJeeLowCodeAdapter;
import com.jeelowcode.framework.utils.enums.YNEnum;
import com.jeelowcode.framework.utils.tool.spring.SpringUtils;
import com.jeelowcode.framework.utils.utils.FuncBase;
import net.sf.jsqlparser.JSQLParserException;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class SqlHelper {

    //数据库类型
    //private static String dbType="postgresql";
    //private static String dbSchemaName="db_jeelowcode";

    private static String dbType="mysql";
    private static String dbSchemaName="db_jeelowcode";

    //数据库类型
    //private static String dbType="oracle";
    //private static String dbSchemaName="c##jee_test";

    public static void setDbType(String dbType) {
        SqlHelper.dbType = dbType;
    }


    public static void setDbSchemaName(String dbSchemaName) {
        SqlHelper.dbSchemaName = dbSchemaName;
    }

    private static AbstractDbTemplate getAbstractDbTemplate() {
        DbTypeEnum dbTypeEnum = DbTypeEnum.getByDbCode(dbType);
        AbstractDbTemplate template = FlyweightDBFactory.getDbType(dbTypeEnum);
        return template;
    }

    public static DbColunmTypesEntity getDbColunmTypesEntity() {
        DbTypeEnum dbTypeEnum = DbTypeEnum.getByDbCode(dbType);
        AbstractDbTemplate template = FlyweightDBFactory.getDbType(dbTypeEnum);

        SqlInfoEntity sqlInfoEntity=new SqlInfoEntity();
        SQLInterpretContext context =new SQLInterpretContext(dbSchemaName);
        sqlInfoEntity.setContext(context);
        template.operation(sqlInfoEntity);
        return sqlInfoEntity.getContext().getDbColunmTypes();
    }

    //获取租户权限
    private static TenantIdFilterEntity getTenantIdFilterEntity() {
        //处理租户
        IJeeLowCodeAdapter adapter = SpringUtils.getBean(IJeeLowCodeAdapter.class);
        Set<String> tenantIgnoreTable = adapter.getTenantIgnoreTable();//忽略的表
        Set<String> tenantIgnoreUrl = adapter.getTenantIgnoreUrl();//忽略的表
        boolean tenantEnable = adapter.getTenantEnable();

        TenantIdFilterEntity tenantIdFilterEntity = new TenantIdFilterEntity();
        tenantIdFilterEntity.setEnable(tenantEnable);
        tenantIdFilterEntity.setTenantId(adapter.getTenantId());
        tenantIdFilterEntity.setIgnore(adapter.getTenantIsIgnore());
        tenantIdFilterEntity.setIgnoreTables(tenantIgnoreTable);
        tenantIdFilterEntity.setIgnoreUrl(tenantIgnoreUrl);
        return tenantIdFilterEntity;
    }

    //新建表
    public static List<String> createTable(String tableName, String tableAlias, List<FieldModel> fieldModelList) throws JSQLParserException {
        /**
         * 创建表分三步
         * 1.创建简单表  id varchar(10) not null
         * 2.处理备注
         * 3.处理主键
         */
        //1.创建表
        SqlInfoCreateModel<BuildCreateTable> createModel = SqlInfoCreateModel.builder()
                .setTableName(tableName)
                .setTableOptions(fieldModelList)
                .build();

        //2.处理字段备注
        SqlInfoCommentModel<BuildComment> commentmodel = SqlInfoCommentModel.builder()
                .setTableName(tableName)
                .setTableAlias(tableAlias)
                .setfieldModelList(fieldModelList)
                .build();
        //3.处理主键
        List<String> primaryKeyList = fieldModelList.stream()
                .filter(fieldModel -> !FuncBase.isEmpty(fieldModel.getIsPrimaryKey()) && !FuncBase.equals(fieldModel.getIsPrimaryKey(), YNEnum.N.getCode()))
                .map(FieldModel::getFieldCode)
                .collect(Collectors.toList());
        //3.处理主键
        SqlInfoPrimaryKeyModel primaryKeyModel = SqlInfoPrimaryKeyModel.builder()
                .setTableName(tableName)
                .setPrimaryKey(primaryKeyList)
                .build();

        //构建SQL上下文
        SQLInterpretContext context = new SQLInterpretContext(dbSchemaName);
        context.setCreateModel(createModel);
        context.setCommentModel(commentmodel);
        context.setPrimaryKeyModel(primaryKeyModel);

        //多个
        AndExpression expression = new AndExpression(createModel.getSqlExpression(), commentmodel.getSqlExpression(),primaryKeyModel.getSqlExpression());
        List<SqlFormatModel> sqlFormatModelList = ddlExpression(context, expression);

        List<String> ddlList = sqlFormatModelList.stream()
                .map(SqlFormatModel::getSql)
                .collect(Collectors.toList());
        return ddlList;
    }

    //增加列
    public static List<String> addColumn(String tableName, FieldModel fieldModel) {
        /**
         * 增加列分两步
         * 1.创建简单表
         * 2.处理备注
         */

        //1.创建表
        SqlInfoAlterModel<BuildAlter> addColumnModel = SqlInfoAlterModel.builder()
                .setTableName(tableName)
                .buildAddColumn(fieldModel);

        //2.处理主键
        SqlInfoPrimaryKeyModel primaryKeyModel =null;
        if(FuncBase.equals(fieldModel.getFieldCode(),"id")){
            primaryKeyModel = SqlInfoPrimaryKeyModel.builder()
                    .setTableName(tableName)
                    .setPrimaryKey(FuncBase.toStrList("id"))
                    .build();
        }




        List<FieldModel> fieldModelList = new ArrayList<>();
        fieldModelList.add(fieldModel);

        SqlInfoCommentModel<BuildComment> commentmodel = SqlInfoCommentModel.builder()
                .setTableName(tableName)
                .setfieldModelList(fieldModelList)
                .build();

        SQLInterpretContext context = new SQLInterpretContext(dbSchemaName);
        context.setAlterModel(addColumnModel);
        context.setCommentModel(commentmodel);
        context.setPrimaryKeyModel(primaryKeyModel);//主键

        //多个
        AndExpression expression =null;
        if(FuncBase.isNotEmpty(context.getPrimaryKeyModel())){
            expression = new AndExpression(addColumnModel.getSqlExpression(), commentmodel.getSqlExpression(),primaryKeyModel.getSqlExpression());
        }else{
            expression = new AndExpression(addColumnModel.getSqlExpression(), commentmodel.getSqlExpression());
        }

        List<SqlFormatModel> sqlFormatModelList = ddlExpression(context, expression);

        List<String> ddlList = sqlFormatModelList.stream()
                .map(SqlFormatModel::getSql)
                .collect(Collectors.toList());
        return ddlList;
    }

    //修改列
    public static List<String> modifyColumnSql(String tableName, FieldModel fieldModel) {
        /**
         * 增加列分两步
         * 1.创建简单表
         * 2.处理备注
         */
        //修改表
        SqlInfoAlterModel<BuildAlter> modifyColumnModel = SqlInfoAlterModel.builder()
                .setTableName(tableName)
                .buildUpdateColum(fieldModel);

        List<FieldModel> fieldModelList = new ArrayList<>();
        fieldModelList.add(fieldModel);

        SqlInfoCommentModel<BuildComment> commentModel = SqlInfoCommentModel.builder()
                .setTableName(tableName)
                .setfieldModelList(fieldModelList)
                .build();

        SQLInterpretContext context = new SQLInterpretContext(dbSchemaName);
        context.setAlterModel(modifyColumnModel);
        context.setCommentModel(commentModel);

        //多个
        AndExpression expression = new AndExpression(modifyColumnModel.getSqlExpression(), commentModel.getSqlExpression());
        List<SqlFormatModel> sqlFormatModelList = ddlExpression(context, expression);

        List<String> ddlList = sqlFormatModelList.stream()
                .map(SqlFormatModel::getSql)
                .collect(Collectors.toList());
        return ddlList;
    }

    //删除列
    public static String getDropColumnSql(String tableName,String clumn) {
        SqlInfoDropModel<BuildDrop> dropModel = SqlInfoDropModel.builder()
                .buildDropColumn(tableName, clumn);

        SQLInterpretContext context = new SQLInterpretContext(dbSchemaName);
        context.setDropModel(dropModel);

        List<SqlFormatModel> sqlFormatModelList = ddlExpression(context, dropModel.getSqlExpression());
        return sqlFormatModelList.get(0).getSql();
    }

    //删除表
    public static String getDropTableSql(String tableName) {
        SqlInfoDropModel<BuildDrop> dropTableModel = SqlInfoDropModel.builder()
                .buildDropTable(tableName);

        SQLInterpretContext context = new SQLInterpretContext(dbSchemaName);
        context.setDropModel(dropTableModel);

        List<SqlFormatModel> sqlFormatModelList = ddlExpression(context, dropTableModel.getSqlExpression());
        return sqlFormatModelList.get(0).getSql();
    }

    //创建索引
    public static String createIndex(String tableName,String indexName,List<String> fieldList) {

        SqlInfoCreateIndexModel<BuildCreateIndex> createIndexModel = SqlInfoCreateIndexModel.builder()
                .setTableName(tableName)
                .setIndexName(indexName)
                .setIndexFieldCodeList(fieldList)
                .build();

        SQLInterpretContext context = new SQLInterpretContext(dbSchemaName);
        context.setCreateIndexModel(createIndexModel);

        List<SqlFormatModel> sqlFormatModelList = ddlExpression(context, createIndexModel.getSqlExpression());
        return sqlFormatModelList.get(0).getSql();
    }

    //删除索引
    public static String getDropIndex(String tableName,String indexName) {

        SqlInfoDropModel<BuildDrop> dropModel = SqlInfoDropModel.builder()
                .buildDropIndex(tableName, indexName);

        SQLInterpretContext context = new SQLInterpretContext(dbSchemaName);
        context.setDropModel(dropModel);

        List<SqlFormatModel> sqlFormatModelList = ddlExpression(context, dropModel.getSqlExpression());
        return sqlFormatModelList.get(0).getSql();
    }

    //校验索引是否存在
    public static String getIndexExistSql(String tableName,String  indexName){
        SqlInfoDdlModel<BuildDdl> ddlModel = SqlInfoDdlModel.builder().buildIndexExist(tableName, indexName);

        SQLInterpretContext context = new SQLInterpretContext(dbSchemaName);
        context.setDdlModel(ddlModel);
        List<SqlFormatModel> sqlFormatModelList = ddlExpression(context, ddlModel.getSqlExpression());
        return sqlFormatModelList.get(0).getSql();
    }

    //获取某一个表所有索引
    public static String getIndexAllByTableSql(String tableName){
        SqlInfoDdlModel<BuildDdl> ddlModel = SqlInfoDdlModel.builder().buildIndexAllByTable(tableName);

        SQLInterpretContext context = new SQLInterpretContext(dbSchemaName);
        context.setDdlModel(ddlModel);
        List<SqlFormatModel> sqlFormatModelList = ddlExpression(context, ddlModel.getSqlExpression());
        return sqlFormatModelList.get(0).getSql();
    }

    //校验表是否存在
    public static String getTableExistSql(String tableName){
        SqlInfoDdlModel<BuildDdl> ddlModel = SqlInfoDdlModel.builder().buildTableExist(tableName);

        SQLInterpretContext context = new SQLInterpretContext(dbSchemaName);
        context.setDdlModel(ddlModel);
        List<SqlFormatModel> sqlFormatModelList = ddlExpression(context, ddlModel.getSqlExpression());
        return sqlFormatModelList.get(0).getSql();
    }

    //校验表是否存在
    public static String getAllTableNameSql(){
        SqlInfoDdlModel<BuildDdl> ddlModel = SqlInfoDdlModel.builder().buildGetAllTableName();

        SQLInterpretContext context = new SQLInterpretContext(dbSchemaName);
        context.setDdlModel(ddlModel);
        List<SqlFormatModel> sqlFormatModelList = ddlExpression(context, ddlModel.getSqlExpression());
        return sqlFormatModelList.get(0).getSql();
    }

    //获取表字段备注
    public static String getTableFieldComment(String tableName){
        SqlInfoDdlModel<BuildDdl> ddlModel = SqlInfoDdlModel.builder().buildFieldComment(tableName);

        SQLInterpretContext context = new SQLInterpretContext(dbSchemaName);
        context.setDdlModel(ddlModel);
        List<SqlFormatModel> sqlFormatModelList = ddlExpression(context, ddlModel.getSqlExpression());
        return sqlFormatModelList.get(0).getSql();
    }

    //获取表备注
    public static String getTableComment(String tableName){
        SqlInfoDdlModel<BuildDdl> ddlModel = SqlInfoDdlModel.builder().buildTableComment(tableName);

        SQLInterpretContext context = new SQLInterpretContext(dbSchemaName);
        context.setDdlModel(ddlModel);
        List<SqlFormatModel> sqlFormatModelList = ddlExpression(context, ddlModel.getSqlExpression());
        return sqlFormatModelList.get(0).getSql();
    }

    //--------------dql---------------
    //通用sql
    public static String getPublicSql(String sql){
        SqlInfoPublicModel<BuildSelect> publicModel = SqlInfoPublicModel.builder().setSql(sql).build();

        SQLInterpretContext context = new SQLInterpretContext(dbSchemaName);
        context.setPublicModel(publicModel);
        List<SqlFormatModel> sqlFormatModelList = sqlExpression(context, publicModel.getSqlExpression());
        return sqlFormatModelList.get(0).getSql();
    }

    //查询数据
    public static SqlInfoQueryWrapper.Wrapper getQueryWrapper(){
        SqlInfoQueryWrapper.Wrapper wrapper = SqlInfoQueryWrapper.wrapper(dbSchemaName);
        return wrapper;
    }

    //新增数据
    public static SqlInfoInsertWrapper.Wrapper getInsertWrapper(){
        SqlInfoInsertWrapper.Wrapper wrapper = SqlInfoInsertWrapper.wrapper(dbSchemaName);
        return wrapper;
    }

    //修改数据
    public static SqlInfoUpdateWrapper.Wrapper getUpdateWrapper(){
        SqlInfoUpdateWrapper.Wrapper wrapper = SqlInfoUpdateWrapper.wrapper(dbSchemaName);
        return wrapper;
    }

    //删除数据
    public static SqlInfoDeleteWrapper.Wrapper getDeleteWrapper(){
        SqlInfoDeleteWrapper.Wrapper wrapper = SqlInfoDeleteWrapper.wrapper(dbSchemaName);
        return wrapper;
    }



    //自定义 sql
    public static List<SqlFormatModel> sqlExpression(SQLInterpretContext context, ISQLExpression expression) {
        TenantIdFilterEntity tenantIdFilterEntity =getTenantIdFilterEntity();
        SqlInfoEntity sqlInfoEntity = new SqlInfoEntity();
        List<SqlFormatModel> sqlModelList = sqlExpression(sqlInfoEntity,context, expression, tenantIdFilterEntity);
        return sqlModelList;
    }
    //自定义 ddl
    public static List<SqlFormatModel> ddlExpression(SQLInterpretContext context, ISQLExpression expression) {
        SqlInfoEntity sqlInfoEntity = new SqlInfoEntity();
        List<SqlFormatModel> sqlModelList = sqlExpression(sqlInfoEntity,context, expression, null);
        return sqlModelList;
    }
    //自定义 只需要使用sql解析
    public static List<SqlFormatModel> parserExpression(SQLInterpretContext context, ISQLExpression expression) {
        SqlInfoEntity sqlInfoEntity = new SqlInfoEntity();
        sqlInfoEntity.setSqlParserLevel(3);
        List<SqlFormatModel> sqlModelList = sqlExpression(sqlInfoEntity,context, expression, null);
        return sqlModelList;
    }

    private static List<SqlFormatModel> sqlExpression(SqlInfoEntity sqlInfoEntity,SQLInterpretContext context, ISQLExpression expression, TenantIdFilterEntity tenantIdFilterEntity) {
        AbstractDbTemplate template = getAbstractDbTemplate();

        sqlInfoEntity.setContext(context);
        sqlInfoEntity.setSqlExpression(expression);
        if (FuncBase.isNotEmpty(tenantIdFilterEntity)) {
            sqlInfoEntity.setTenantIdFilterEntity(tenantIdFilterEntity);
        }

        template.operation(sqlInfoEntity);
        List<SqlFormatModel> sqlModelList = sqlInfoEntity.getSqlModelList();
        if (FuncBase.isEmpty(sqlModelList)) {
            return sqlModelList;
        }
        sqlModelList.forEach(model->{
            String sql = model.getSql();
            if(sql.endsWith(";")){
                System.out.println(sql);
            }else{
                System.out.println(sql+";");
            }
        });
        return sqlModelList;
    }


}
