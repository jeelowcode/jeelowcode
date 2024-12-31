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
package com.jeelowcode.framework.plus.core.ddl.drop;

import net.sf.jsqlparser.schema.Table;
import net.sf.jsqlparser.statement.drop.Drop;
import org.junit.Test;

/**
 * 删除表
 * @author JX
 * @create 2024-03-27 13:38
 * @dedescription:
 */
public class JeeLowCodeDropTable {

    private String tableName;

    private Drop drop ;

    public void setTableName(String tableName){
        this.tableName = tableName;
        drop = new Drop();
        drop.setName(new Table(tableName));
        drop.setType("TABLE");
    }


    public String getFullSQL(){
        return drop.toString();
    }

    @Test
    public void dropTable(){
        JeeLowCodeDropTable dropTable=new JeeLowCodeDropTable();
        dropTable.setTableName("tbl_lin2");
       // alterTable.setAlterOperation(AlterOperation.);
        System.out.println(dropTable.getFullSQL());
    }


}
