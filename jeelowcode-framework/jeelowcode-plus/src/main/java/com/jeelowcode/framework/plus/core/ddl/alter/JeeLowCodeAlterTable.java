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
package com.jeelowcode.framework.plus.core.ddl.alter;

import net.sf.jsqlparser.schema.Table;
import net.sf.jsqlparser.statement.alter.Alter;
import net.sf.jsqlparser.statement.alter.AlterExpression;
import net.sf.jsqlparser.statement.alter.AlterOperation;
import net.sf.jsqlparser.statement.create.table.ColDataType;
import net.sf.jsqlparser.statement.create.table.ColumnDefinition;
import net.sf.jsqlparser.statement.create.table.Index;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author JX
 * @create 2024-03-27 13:38
 * @dedescription:
 */
public class JeeLowCodeAlterTable {

    private String tableName;

    private AlterOperation alterOperation;

    private List<Index> indices = new ArrayList<>();

    private List<ColumnDefinition> columnDefinitions = new ArrayList<>();

    private List<AlterExpression> expressions = new ArrayList<>();

    private Alter alter ;


    public void setTableName(String tableName){
        this.tableName = tableName;
        alter = new Alter();
        alter.setTable(new Table(tableName));
    }

    public void setAlterOperation(AlterOperation alterOperation){
        this.alterOperation = alterOperation;
    }

    /**
     * 新增字段
     * @param columnName
     * @param dataType
     * @param specs
     */
    public void addCoulumn(String columnName, String dataType,String... specs){
        AlterExpression alterExpression = new AlterExpression();
        alterExpression.setOperation(AlterOperation.ADD);
        AlterExpression.ColumnDataType columnDataType = new AlterExpression.ColumnDataType(columnName,false,new ColDataType(dataType),Arrays.asList(specs));
        alterExpression.addColDataType(columnDataType);
        expressions.add(alterExpression);
    }

    public void addIndex(String indexName, String... columns){
        Index index = new Index();
        index.setName(indexName);
        index.setColumnsNames(Arrays.asList(columns));
        index.setType("index");
        AlterExpression alterExpression = new AlterExpression();
        alterExpression.setOperation(AlterOperation.ADD);
        alterExpression.setIndex(index);
        expressions.add(alterExpression);
    }

    public void dropColumn(String columnName){
        AlterExpression alterExpression = new AlterExpression();
        alterExpression.setOperation(AlterOperation.DROP);
        alterExpression.setColumnName(columnName);
        expressions.add(alterExpression);
    }

    public void dropIndex(String indexName){
        Index index = new Index();
        index.setName(indexName);
        index.setType("index");
        AlterExpression alterExpression = new AlterExpression();
        alterExpression.setIndex(index);
        alterExpression.setOperation(AlterOperation.DROP);
        expressions.add(alterExpression);
    }

    public void alterColumn(String columnName, String dataType, AlterOperation alterOperation,String... specs){
        AlterExpression alterExpression = new AlterExpression();
        alterExpression.setColumnName(columnName);
        AlterExpression.ColumnDataType columnDataType = new AlterExpression.ColumnDataType(false);
        columnDataType.setColumnName(columnName);
        columnDataType.setColDataType(new ColDataType(dataType));
        columnDataType.setColumnSpecs(Arrays.asList(specs));
        alterExpression.addColDataType(columnDataType);
        alterExpression.setOperation(alterOperation);
        expressions.add(alterExpression);
    }


    //字段属性
    public void modifyColumn(String columnName, String dateType , String... specs){
        AlterExpression.ColumnDataType columnDataType = new AlterExpression.ColumnDataType(false);
        columnDataType.setColumnName(columnName);
        columnDataType.setColDataType(new ColDataType(dateType));
        columnDataType.setColumnSpecs(Arrays.asList(specs));
        AlterExpression alterExpression = new AlterExpression();
        alterExpression.addColDataType(columnDataType);
        alterExpression.setOperation(AlterOperation.MODIFY);
        expressions.add(alterExpression);
    }

    //字段改变
    public void change(String oldName, String newName, String dataType, String... specs){
        AlterExpression.ColumnDataType columnDataType = new AlterExpression.ColumnDataType(false);
        columnDataType.setColumnName(newName);
        columnDataType.setColumnSpecs(Arrays.asList(specs));
        columnDataType.setColDataType(new ColDataType(dataType));
        AlterExpression alterExpression = new AlterExpression();
        alterExpression.setColumnOldName(oldName);
        alterExpression.setOperation(AlterOperation.CHANGE);
        alterExpression.addColDataType(columnDataType);
        expressions.add(alterExpression);
    }


    public String getFullSQL(){
        alter.setAlterExpressions(expressions);
        return alter.toString();
    }

    @Test
    public void dropTable(){
        JeeLowCodeAlterTable alterTable=new JeeLowCodeAlterTable();
        alterTable.setTableName("tbl_lin2");
        alterTable.setAlterOperation(AlterOperation.DROP);
        System.out.println(alterTable.getFullSQL());
    }

    @Test
    public void addCoulumn(){
        JeeLowCodeAlterTable alterTable=new JeeLowCodeAlterTable();
        alterTable.setTableName("tbl_lin2");
        alterTable.setAlterOperation(AlterOperation.ALTER);
        alterTable.addCoulumn("name","varchar(20)","not null","COMMENT '姓名'");
        System.out.println(alterTable.getFullSQL());
    }
    @Test
    public void test_modifyCoulumn(){
        JeeLowCodeAlterTable alterTable=new JeeLowCodeAlterTable();
        alterTable.setTableName("tbl_lin2");
        alterTable.modifyColumn("name","varchar(20)","not null","COMMENT '姓名'");
        System.out.println(alterTable.getFullSQL());
    }

    @Test
    public void test_dropColumn(){
        JeeLowCodeAlterTable alterTable=new JeeLowCodeAlterTable();
        alterTable.setTableName("tbl_lin2");
        alterTable.dropColumn("name");
        System.out.println(alterTable.getFullSQL());
    }

    @Test
    public void test_addIndex(){
        JeeLowCodeAlterTable alterTable=new JeeLowCodeAlterTable();
        alterTable.setTableName("tbl_lin2");
        alterTable.addIndex("ind_name1","id","name");
        System.out.println(alterTable.getFullSQL());
    }

    @Test
    public void test_dropIndex(){
        JeeLowCodeAlterTable alterTable=new JeeLowCodeAlterTable();
        alterTable.setTableName("tbl_lin2");
        alterTable.dropIndex("ind_name1");
        System.out.println(alterTable.getFullSQL());
    }
}
