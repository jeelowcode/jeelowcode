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
package com.jeelowcode.framework.plus.core.ddl.create;


import com.jeelowcode.framework.utils.utils.FuncBase;
import net.sf.jsqlparser.schema.Table;
import net.sf.jsqlparser.statement.create.table.ColDataType;
import net.sf.jsqlparser.statement.create.table.ColumnDefinition;
import net.sf.jsqlparser.statement.create.table.CreateTable;
import net.sf.jsqlparser.statement.create.table.Index;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * @author JX
 * @create 2024-03-27 10:40
 * @dedescription:
 */
public class JeeLowCodeCreateTable {

    //表名
    private String tableName;

    //别名
    private List<String> tableOptions;

    //字段
    private List<ColumnDefinition> columnDefinitions=new ArrayList<>();

    //索引
    private List<Index> indices = new ArrayList<>();

    public void setTableName(String tableName){
        this.tableName = tableName;
    }

    public void setTableOptions(List<String> tableOptions) {
        this.tableOptions = tableOptions;
    }



    public void addCoulumn(String columnName, String dataType, String... specs){
        ColumnDefinition columnDefinition = new ColumnDefinition(columnName,new ColDataType(dataType));
        if (!FuncBase.isEmpty(specs)){
            columnDefinition.addColumnSpecs(specs);
        }
        this.columnDefinitions.add(columnDefinition);
    }


    public String getDDL(){
        CreateTable createTable = new CreateTable();
        createTable.setTable(new Table(tableName));
        createTable.setTableOptionsStrings(tableOptions);
        createTable.setColumnDefinitions(columnDefinitions);
        createTable.setIndexes(indices);
        return createTable.toString();
    }



    @Test
    public void testCreate(){
        JeeLowCodeCreateTable createTable=new JeeLowCodeCreateTable();
        createTable.setTableName("tbl_lin2");
        createTable.addCoulumn("id","varchar(12)","PRIMARY KEY","COMMENT 'id'","not null");
        createTable.addCoulumn("name","varchar(20)","not null","COMMENT '姓名'");
        System.out.println(createTable.getDDL());
    }
}
