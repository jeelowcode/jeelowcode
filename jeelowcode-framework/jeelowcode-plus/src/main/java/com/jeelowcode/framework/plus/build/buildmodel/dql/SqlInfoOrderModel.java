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
import com.jeelowcode.framework.plus.build.build.dql.BuildOrderBy;
import com.jeelowcode.framework.plus.build.buildmodel.SqlInfoBaseModel;
import com.jeelowcode.framework.plus.core.model.SqlFormatModel;

import java.util.ArrayList;
import java.util.List;

/**
 * @author JX
 * @create 2024-07-04 9:14
 * @dedescription:
 */

public class SqlInfoOrderModel<T> extends SqlInfoBaseModel {

    private List<OrderByModel> orderByList=new ArrayList<>();

    public static Builder builder(){
        return new Builder();
    }

    public static class OrderByModel{

        public String[] columns;//列

        public boolean asc;//是否是asc 升序

        public void setColumns(String ... columns){
            this.columns = columns;
        }

        public boolean isAsc() {
            return asc;
        }

        public void setAsc(boolean asc) {
            this.asc = asc;
        }

        public String[] getColumns() {
            return columns;
        }
    }


    public static class Builder{
        private SqlInfoOrderModel infoOrderModel = new SqlInfoOrderModel();

        //升序
        public Builder setOrderByAsc(String ... columns){
            OrderByModel model=new OrderByModel();
            model.setAsc(true);
            model.setColumns(columns);
            infoOrderModel.orderByList.add(model);
            return this;
        }

        //降序
        public Builder setOrderByDesc(String ... columns ){
            OrderByModel model=new OrderByModel();
            model.setAsc(false);
            model.setColumns(columns);
            infoOrderModel.orderByList.add(model);
            return this;
        }

        public SqlInfoOrderModel<BuildOrderBy> build(){
            ISQLExpression<SqlFormatModel> expression = new BuildOrderBy();
            infoOrderModel.setSqlExpression(expression);
            return infoOrderModel;
        }
    }

    public List<OrderByModel> getOrderByList() {
        return orderByList;
    }

    public void setOrderByList(List<OrderByModel> orderByList) {
        this.orderByList = orderByList;
    }
}
