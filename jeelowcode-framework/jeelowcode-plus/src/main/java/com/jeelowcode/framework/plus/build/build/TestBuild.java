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
package com.jeelowcode.framework.plus.build.build;



import com.jeelowcode.framework.plus.build.build.dql.*;
import com.jeelowcode.framework.plus.build.buildmodel.dql.*;


/**
 * @author JX
 * @create 2024-07-03 10:16
 * @dedescription:
 */
public class TestBuild {

  /*  @Test
    public void testBuildSelect() throws JSQLParserException {
        SqlInfoSelectModel selectModel = SqlInfoSelectModel.builder()
                *//*   .setColumns("id", "name")*//*
                .setTableName("tbl_student")
                .build();

        SQLInterpretContext context = new SQLInterpretContext();
        context.setSelectModel(selectModel);
        BuildSelect buildSelect = new BuildSelect();
        SqlFormatModel interpret = buildSelect.interpret(context);
        System.out.println(FuncBase.json2Str(interpret));
    }

    @Test
    public void testBuildOrderBy() throws JSQLParserException {

        SqlInfoOrderModel build = SqlInfoOrderModel.builder()
                .setOrderByAsc("id","name")
                .setOrderByDesc("age")
                .setOrderByAsc("sex")
                .build();

        SQLInterpretContext context = new SQLInterpretContext();
        context.setOrderModel(build);
        BuildOrderBy buildOrderBy=new BuildOrderBy();

        SqlFormatModel interpret = buildOrderBy.interpret(context);
        System.out.println(FuncBase.json2Str(interpret));
    }


    @Test
    public void testBuildGroupBy() throws JSQLParserException {

        SqlInfoGroupModel build = SqlInfoGroupModel.builder()
                .setColumns("name", "age")
                .build();


        SQLInterpretContext context = new SQLInterpretContext();
        context.setGroupModel(build);
        BuildGroupBy buildGroupBy=new BuildGroupBy();

        SqlFormatModel interpret = buildGroupBy.interpret(context);
        System.out.println(FuncBase.json2Str(interpret));
    }

    @Test
    public void testBuildWhere() throws JSQLParserException {

        SqlInfoWhereModel build = SqlInfoWhereModel.builder()
                .gt("id", 12)
                .ge("age", 18)
                .ne("name", "张三")
                .between("height",18,30)
                .in("a.id",1,2,3,4,5)
                .like("name","张三")
                .buildWhere();



        SQLInterpretContext context = new SQLInterpretContext();
        context.setWhereModel(build);
        BuildWhere buildWhere=new BuildWhere();

        SqlFormatModel interpret = buildWhere.interpret(context);
        System.out.println(FuncBase.json2Str(interpret));
    }


    @Test
    public void testBuildDelete() throws JSQLParserException {

        SqlInfoDeleteModel build = SqlInfoDeleteModel.builder()
                .setTableName("tbl_student")
                .build();
        SQLInterpretContext context = new SQLInterpretContext();
        context.setDeleteModel(build);
        BuildDelete buildDelete=new BuildDelete();

        SqlFormatModel interpret = buildDelete.interpret(context);
        System.out.println(FuncBase.json2Str(interpret));
    }


    @Test
    public void testBuildRealDelete() throws JSQLParserException {

        SqlInfoDeleteModel build = SqlInfoDeleteModel.builder()
                .setTableName("tbl_student",true)
                .build();
        SQLInterpretContext context = new SQLInterpretContext();
        BuildDelete buildDelete=new BuildDelete();

        context.setDeleteModel(build);
        SqlFormatModel interpret = buildDelete.interpret(context);
        System.out.println(FuncBase.json2Str(interpret));
    }

    @Test
    public void testBuildInsert() throws JSQLParserException {

        Map<String,Object> addMap=new HashMap<>();
        addMap.put("age",18);
        addMap.put("name","张三");

        SqlInfoInsertModel build = SqlInfoInsertModel.builder()
                .setTableName("tbl_student")
                .addColumn("id", 123)
                .addMap(addMap)
                .build();

        SQLInterpretContext context = new SQLInterpretContext();
        context.setInsertModel(build);
        BuildInsert buildInsert=new BuildInsert();

        SqlFormatModel interpret = buildInsert.interpret(context);
        System.out.println(FuncBase.json2Str(interpret));
    }

    @Test
    public void testBuildUpdate() throws JSQLParserException {

        Map<String,Object> updateMap=new HashMap<>();
        updateMap.put("age",18);
        updateMap.put("name","张三");

        SqlInfoUpdateModel build = SqlInfoUpdateModel.builder()
                .setTableName("tbl_student")
                .addColumn("id", 123)
                .addMap(updateMap)
                .build();

        SQLInterpretContext context = new SQLInterpretContext();
        context.setUpdateModel(build);
        BuildUpdate buildUpdate=new BuildUpdate();

        SqlFormatModel interpret = buildUpdate.interpret(context);
        System.out.println(FuncBase.json2Str(interpret));
    }

    @Test
    public void testBuildAlter_AddColumn() throws JSQLParserException {
*//*
        TableFieldModel fieldModel = new TableFieldModel();
        fieldModel.setFieldCode("name");
        fieldModel.setFieldType("varchar(128)");
        fieldModel.setCommentSql("年龄");

        SqlInfoAlterModel model = SqlInfoAlterModel.builder()
                .setTableName("tbl_student")
                .buildAddColumn(fieldModel);

        SQLInterpretContext context = new SQLInterpretContext();
        context.setAlterModel(model);

        BuildAlter build=new BuildAlter();
        SqlFormatModel interpret = build.interpret(context);
        System.out.println(Func.json2Str(interpret));*//*
    }

    @Test
    public void testBuildAlter_updaateColumn() throws JSQLParserException {

        *//*TableFieldModel fieldModel = new TableFieldModel();
        fieldModel.setFieldCode("name");
        fieldModel.setFieldType("varchar(128)");
        fieldModel.setCommentSql("年龄");

        SqlInfoAlterModel model = SqlInfoAlterModel.builder()
                .setTableName("tbl_student")
                .buildUpdateColum(fieldModel);

        SQLInterpretContext context = new SQLInterpretContext();
        context.setAlterModel(model);

        BuildAlter build=new BuildAlter();

        SqlFormatModel interpret = build.interpret(context);
        System.out.println(Func.json2Str(interpret));*//*
    }

    @Test
    public void testBuildDrop_dropTable() throws JSQLParserException {

        SqlInfoDropModel model = SqlInfoDropModel.builder()
               .buildDropTable("","tbl_student");

        SQLInterpretContext context = new SQLInterpretContext();
        context.setDropModel(model);

        BuildDrop build=new BuildDrop();
        List<SqlFormatModel> sqlFormatModelList = build.interpret(context);
        System.out.println(FuncBase.json2Str(sqlFormatModelList));
    }

    @Test
    public void testBuildDrop_dropColumn() throws JSQLParserException {

        SqlInfoDropModel model = SqlInfoDropModel.builder()
                .buildDropColumn("","tbl_student","name");

        SQLInterpretContext context = new SQLInterpretContext();
        context.setDropModel(model);

        BuildDrop build=new BuildDrop();

        List<SqlFormatModel> sqlFormatModelList = build.interpret(context);
        System.out.println(FuncBase.json2Str(sqlFormatModelList));
    }

    @Test
    public void testBuildDrop_dropIndex() throws JSQLParserException {

        SqlInfoDropModel model = SqlInfoDropModel.builder()
                .buildDropIndex("","tbl_student","ind_name");

        SQLInterpretContext context = new SQLInterpretContext();
        context.setDropModel(model);

        BuildDrop build=new BuildDrop();
        List<SqlFormatModel> sqlFormatModelList = build.interpret(context);
        System.out.println(FuncBase.json2Str(sqlFormatModelList));
    }

    @Test
    public void testBuildCreate() throws JSQLParserException {

        List<FieldModel> buildTableFieldModelList=new ArrayList<>();

        FieldModel idModel = new FieldModel();
        idModel.setFieldCode("id");
        idModel.setFieldName("id");
        idModel.setFieldLen(12);
        idModel.setFieldPointLen(0);
        idModel.setFieldType(JeeLowCodeFieldTypeEnum.BIGINT);
        idModel.setIsPrimaryKey("Y");
        idModel.setIsNull("N");


        FieldModel nameModel = new FieldModel();
        nameModel.setFieldCode("name");
        nameModel.setFieldName("姓名");
        nameModel.setFieldLen(12);
        nameModel.setFieldPointLen(0);
        nameModel.setFieldType(JeeLowCodeFieldTypeEnum.STRING);
        nameModel.setIsPrimaryKey("N");
        nameModel.setIsNull("Y");


        buildTableFieldModelList.add(idModel);
        buildTableFieldModelList.add(nameModel);


        SqlInfoCreateModel model = SqlInfoCreateModel.builder()
                .setTableName("tbl_student")
                .setTableOptions(buildTableFieldModelList)
                .build();

        SQLInterpretContext context = new SQLInterpretContext();
        context.setCreateModel(model);
        BuildCreateTable build=new BuildCreateTable();

        List<SqlFormatModel> sqlFormatModelList = build.interpret(context);
        System.out.println(FuncBase.json2Str(sqlFormatModelList));
    }

    @Test
    public void testBuildCreateIndex() throws JSQLParserException {

        SqlInfoCreateIndexModel model = SqlInfoCreateIndexModel.builder()
                .setTableName("tbl_student")
                .setIndexName("ind_aa")
                .setIndexFieldCodeList(FuncBase.toStrList("name,age"))
                .build();

        SQLInterpretContext context = new SQLInterpretContext();
        context.setCreateIndexModel(model);

        BuildCreateIndex build=new BuildCreateIndex();

        List<SqlFormatModel> sqlFormatModelList = build.interpret(context);
        System.out.println(FuncBase.json2Str(sqlFormatModelList));
    }

    @Test
    public void testBuildAndUpdateExpression() throws JSQLParserException {

        //构建update语句
        Map<String,Object> updateMap=new HashMap<>();
        updateMap.put("age",18);
        updateMap.put("name","张三");

        SqlInfoUpdateModel build = SqlInfoUpdateModel.builder()
                .setTableName("tbl_student")
                .addColumn("id", 123)
                .addMap(updateMap)
                .build();

        SQLInterpretContext context = new SQLInterpretContext();
        BuildUpdate buildUpdate=new BuildUpdate();
        context.setUpdateModel(build);

        //构建where语句
        SqlInfoWhereModel whereModel = SqlInfoWhereModel.builder()
                .ge("age",18)
                .like("name","张三")
                .buildWhere();
        context.setWhereModel(whereModel);
        BuildWhere buildWhere=new BuildWhere();


        //update语句+where语句
        AndExpression expression=new AndExpression(buildUpdate,buildWhere);
        List<SqlFormatModel> sqlFormatModelList = expression.interpret(context);
        System.out.println(FuncBase.json2Str(sqlFormatModelList));

    }

    @Test
    public void testBuildSelectExpression() throws JSQLParserException {

        SqlInfoSelectModel selectModel = SqlInfoSelectModel.builder()
                .setColumns("name", "age")
                .setTableName("tbl_student")
                .build();

        SQLInterpretContext contextSelect = new SQLInterpretContext();
        contextSelect.setSelectModel(selectModel);
        BuildSelect buildSelect = new BuildSelect();


*//*        SqlInfoWhereModel whereOrModel = SqlInfoWhereModel.builder()
                .ge("age", 18)
                .or()
                .le("age",10)
                .build();
        SQLInterpretContext<SqlInfoWhereModel> contextWhereOr = new SQLInterpretContext<>(whereOrModel);
        BuildWhere buildWhereOr=new BuildWhere(contextWhereOr);*//*

  *//*      SqlInfoWhereModel whereOrModel2 = SqlInfoWhereModel.builder()
                .le("age", 182)
                .notLike("name", "张三2")
                .or(buildWhereOr)
                .build();
        SQLInterpretContext<SqlInfoWhereModel> contextWhereOr2 = new SQLInterpretContext<>(whereOrModel2);
        BuildWhere buildWhereOr2=new BuildWhere(contextWhereOr2);*//*


        SqlInfoWhereModel whereAndModel = SqlInfoWhereModel.builder()// 18<x<65
                .ge("age", 18)
                .le("age",65)
                .buildWhere();
        SQLInterpretContext contextWhereAnd = new SQLInterpretContext();
        contextWhereAnd.setWhereModel(whereAndModel);
        BuildWhere buildWhereAnd=new BuildWhere();
        buildWhereAnd.setContext(contextWhereAnd);

        SqlInfoWhereModel whereModel = SqlInfoWhereModel.builder()
                .gt("id", 12)
                //.or(buildWhereOr)
                //.or(buildWhereOr2)
                .eq("na","历史")
                .buildWhere();
        SQLInterpretContext contextWhere = new SQLInterpretContext();
        contextWhere.setWhereModel(whereModel);
        BuildWhere buildWhere=new BuildWhere();
        buildWhere.setContext(contextWhere);


        SqlInfoGroupModel groupByModel = SqlInfoGroupModel.builder()
                .setColumns("name", "age")
                .build();
        SQLInterpretContext contextGroupBy = new SQLInterpretContext();
        BuildGroupBy buildGroupBy=new BuildGroupBy();

        SqlInfoOrderModel orderByModel = SqlInfoOrderModel.builder()
                .setOrderByAsc("age")
                .build();
        SQLInterpretContext contextOrderBy = new SQLInterpretContext();
        BuildOrderBy buildOrderBy=new BuildOrderBy();

        SQLInterpretContext context = new SQLInterpretContext();
        context.setWhereModel(whereModel);
        context.setSelectModel(selectModel);
        context.setOrderModel(orderByModel);
        context.setGroupModel(groupByModel);


        //update语句+where语句
        AndExpression expression=new AndExpression(buildWhere,buildSelect,buildOrderBy,buildGroupBy);
        List<SqlFormatModel> sqlFormatModelList = expression.interpret(context);
        System.out.println(FuncBase.json2Str(sqlFormatModelList));

    }

    @Test
    public void testBuildSelectAllExpression() throws JSQLParserException {

        SqlInfoSelectModel selectModel = SqlInfoSelectModel.builder()
                .setColumns("name", "age")
                .setTableName("tbl_student")
                .build();

        SQLInterpretContext contextSelect = new SQLInterpretContext();
        BuildSelect buildSelect = new BuildSelect();
        contextSelect.setSelectModel(selectModel);
        //
        AndExpression expression=new AndExpression(buildSelect);
        List<SqlFormatModel> sqlFormatModelList = expression.interpret(contextSelect);
        System.out.println(FuncBase.json2Str(sqlFormatModelList));

    }

    @Test
    public void testBuildHavingExpression() throws JSQLParserException {
        SqlInfoSelectModel selectModel = SqlInfoSelectModel.builder()
                .setColumns("name", "count( id ) AS cou")
                .setTableName("system_dept")
                .build();

        BuildSelect buildSelect = new BuildSelect();

       SqlInfoHavingModel havingModel = SqlInfoConditionModel.builder()
                .gt("cou", 1)
                .likeLeft("name", "财务部门")
                .buildHaving();
        SQLInterpretContext contextHaving = new SQLInterpretContext();
        contextHaving.setHavingModel(havingModel);
        BuildHaving buildHaving=new BuildHaving();
        buildHaving.setContext(contextHaving);


        SqlInfoGroupModel groupByModel = SqlInfoGroupModel.builder()
                .setColumns("name")
                .build();
        SQLInterpretContext contextGroupBy = new SQLInterpretContext();
        BuildGroupBy buildGroupBy=new BuildGroupBy();

        SqlInfoOrderModel orderByModel = SqlInfoOrderModel.builder()
                .setOrderByAsc("cou")
                .build();
        SQLInterpretContext contextOrderBy = new SQLInterpretContext();
        BuildOrderBy buildOrderBy=new BuildOrderBy();

        SQLInterpretContext context = new SQLInterpretContext();
        context.setSelectModel(selectModel);
        context.setGroupModel(groupByModel);
        context.setOrderModel(orderByModel);
        context.setHavingModel(havingModel);

        //update语句+where语句
        AndExpression expression=new AndExpression(buildGroupBy,buildSelect,buildOrderBy,buildHaving);
        List<SqlFormatModel> sqlFormatModelList = expression.interpret(context);
        System.out.println(FuncBase.json2Str(sqlFormatModelList));

    }

    @Test
    public void testBuildJoinExpression() throws JSQLParserException {
        ///主表
        SqlInfoSelectModel selectModel = SqlInfoSelectModel.builder()
                .setColumns("a.*","b.*","c.*")
                .setTableName("tbl_a","a")
                .build();
        SQLInterpretContext contextSelect = new SQLInterpretContext();
        BuildSelect buildSelect = new BuildSelect();

        //B表
        SqlInfoJoinModel joinModelB = SqlInfoJoinModel.builder()
                .innerJoin()
                .joinTable("tbl_b", "b")
                .on("a.id", "b.a_id")
                .build();

        SQLInterpretContext contextJoinB = new SQLInterpretContext();
        BuildJoin buildJsonB = new BuildJoin();

        //C表
        SqlInfoJoinModel joinModelC = SqlInfoJoinModel.builder()
                .innerJoin()
                .joinTable("tbl_c", "c")
                .on("b.id", "c.b_id")
                .build();

        SQLInterpretContext contextJoinC = new SQLInterpretContext();
        BuildJoin buildJsonC = new BuildJoin();

        SQLInterpretContext context = new SQLInterpretContext();
        context.setSelectModel(selectModel);
        context.setJoinModel(joinModelB);
        //update语句+where语句
        AndExpression expression=new AndExpression(buildSelect,buildJsonB,buildJsonC);
        List<SqlFormatModel> sqlFormatModelList = expression.interpret(context);
        System.out.println(FuncBase.json2Str(sqlFormatModelList));

    }

    @Test
    public void testHavingAndOrExpression() throws JSQLParserException {
        SqlInfoSelectModel selectModel = SqlInfoSelectModel.builder()
                .setColumns("name", "count( id ) AS cou")
                .setTableName("system_dept")
                .build();

        BuildSelect buildSelect = new BuildSelect();



        SqlInfoHavingModel havingModel = SqlInfoConditionModel.builder()
                .gt("cou", 1)
                .likeLeft("name", "财务部门")
                .and( andTest ->{
                    andTest.eq("status", 1)
                            .or( orTest ->{
                               orTest.ne("id", 1);
                            });
                })
                .buildHaving();
        SQLInterpretContext contextHaving = new SQLInterpretContext();
        contextHaving.setHavingModel(havingModel);
        BuildHaving buildHaving=new BuildHaving();
        buildHaving.setContext(contextHaving);


        SqlInfoGroupModel groupByModel = SqlInfoGroupModel.builder()
                .setColumns("name")
                .build();
        SQLInterpretContext contextGroupBy = new SQLInterpretContext();
        BuildGroupBy buildGroupBy=new BuildGroupBy();


        SQLInterpretContext context = new SQLInterpretContext();
        context.setSelectModel(selectModel);
        context.setGroupModel(groupByModel);
        context.setHavingModel(havingModel);

        //update语句+where语句
        AndExpression expression=new AndExpression(buildGroupBy,buildSelect,buildHaving);
        List<SqlFormatModel> sqlFormatModelList = expression.interpret(context);
        System.out.println(FuncBase.json2Str(sqlFormatModelList));
    }

    @Test
    public void testWhereAndOrExpression() throws JSQLParserException {
        SqlInfoSelectModel selectModel = SqlInfoSelectModel.builder()
                .setColumns("name", "age")
                .setTableName("tbl_student")
                .build();

        SQLInterpretContext contextSelect = new SQLInterpretContext();
        contextSelect.setSelectModel(selectModel);
        BuildSelect buildSelect = new BuildSelect();


        SqlInfoWhereModel whereModel = SqlInfoWhereModel.builder()
                .gt("id", 12)
                .and(andTest ->{
                    andTest.eq("name","李四")
                            .or(orTest ->{
                                orTest.eq("name", "张三");
                            });
                })
                .eq("na","历史")
                .buildWhere();
        SQLInterpretContext contextWhere = new SQLInterpretContext();
        contextWhere.setWhereModel(whereModel);
        BuildWhere buildWhere=new BuildWhere();
        buildWhere.setContext(contextWhere);


        SqlInfoGroupModel groupByModel = SqlInfoGroupModel.builder()
                .setColumns("name", "age")
                .build();
        SQLInterpretContext contextGroupBy = new SQLInterpretContext();
        BuildGroupBy buildGroupBy=new BuildGroupBy();

        SqlInfoOrderModel orderByModel = SqlInfoOrderModel.builder()
                .setOrderByAsc("age")
                .build();
        SQLInterpretContext contextOrderBy = new SQLInterpretContext();
        BuildOrderBy buildOrderBy=new BuildOrderBy();

        SQLInterpretContext context = new SQLInterpretContext();
        context.setWhereModel(whereModel);
        context.setSelectModel(selectModel);
        context.setOrderModel(orderByModel);
        context.setGroupModel(groupByModel);

        
        AndExpression expression=new AndExpression(buildWhere,buildSelect,buildOrderBy,buildGroupBy);
        List<SqlFormatModel> sqlFormatModelList = expression.interpret(context);
        System.out.println(FuncBase.json2Str(sqlFormatModelList));
    }*/
}
