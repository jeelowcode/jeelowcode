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

import com.jeelowcode.framework.exception.JeeLowCodeException;
import com.jeelowcode.framework.plus.build.ISQLExpression;
import com.jeelowcode.framework.plus.build.build.ddl.*;
import com.jeelowcode.framework.plus.build.build.dql.*;
import com.jeelowcode.framework.plus.build.buildmodel.SQLInterpretContext;
import com.jeelowcode.framework.plus.core.model.SqlFormatModel;
import com.jeelowcode.framework.utils.utils.FuncBase;
import net.sf.jsqlparser.JSQLParserException;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 多条件
 */
public class AndExpression implements ISQLExpression<List<SqlFormatModel>> {

    private ISQLExpression[] expressions;

    public AndExpression(ISQLExpression... expressions) {
        this.expressions = expressions;
    }
    public AndExpression(List<ISQLExpression> expressionList) {
        expressions =new ISQLExpression[expressionList.size()];
        for (int i = 0; i < expressionList.size(); i++) {
            ISQLExpression expression = expressionList.get(i);
            expressions[i]=expression;
        }
    }

    /**
     * ddl允许多个，dql只允许一个
     * @param context
     * @return
     * @throws JSQLParserException
     */
    @Override
    public List<SqlFormatModel> interpret(SQLInterpretContext context) throws JSQLParserException {
        List<SqlFormatModel> sqlFormatModelList = this.getDDL(context);
        if (FuncBase.isNotEmpty(sqlFormatModelList)) {
            return sqlFormatModelList;
        }
        SqlFormatModel sqlFormatModel = this.getDQL(context);
        return Collections.singletonList(sqlFormatModel);
    }

    //获取ddl
    private List<SqlFormatModel> getDDL(SQLInterpretContext context) throws JSQLParserException  {

        List<ISQLExpression> ddlList = Arrays.stream(expressions)
                .filter(expression -> expression instanceof BuildAlter
                        || expression instanceof BuildCreateIndex
                        || expression instanceof BuildCreateTable
                        || expression instanceof BuildDrop
                        || expression instanceof BuildComment
                        || expression instanceof BuildPrimaryKey
                        || expression instanceof BuildDdl)
                .collect(Collectors.toList());

        if (FuncBase.isEmpty(ddlList)) {
            return null;
        }

        List<SqlFormatModel> resultModelList=new ArrayList<>();
        for (ISQLExpression<SqlFormatModel> expression : ddlList) {
            Object obj= expression.interpret(context);
            if (FuncBase.isEmpty(obj)) {
                continue;
            }
            if(obj instanceof SqlFormatModel){
                resultModelList.add((SqlFormatModel)obj);
            }else if(obj instanceof List){
                resultModelList.addAll((List<SqlFormatModel>)obj);
            }

        }
        return resultModelList;
    }

    private SqlFormatModel getDQL(SQLInterpretContext context) throws JSQLParserException {
        List<ISQLExpression> expressionList = this.getDQLExpression();
        if (FuncBase.isEmpty(expressionList)) {
            throw new JeeLowCodeException("表达式为空");
        }
        String sql = "";
        Map<String, Object> dataMap = new LinkedHashMap<>();
        int step = 0;
        for (ISQLExpression expression : expressionList) {//buildselect buildwhere buidlorderby
            SqlFormatModel sqlFormatModel = (SqlFormatModel) expression.interpret(context);
            String tmpSql = sqlFormatModel.getSql();
            Map<String, Object> tmpMap = sqlFormatModel.getDataMap();
            if (FuncBase.isEmpty(tmpMap)) {
                sql += " " + tmpSql;
                continue;
            }

            // 首先，将entrySet中的元素复制到一个列表中
            List<Map.Entry<String, Object>> entrieList = new ArrayList<>(tmpMap.entrySet());
            Collections.reverse(entrieList);

            for (Map.Entry<String, Object> entry : entrieList) {
                step++;

                String key = entry.getKey(); // 获取键
                Object value = entry.getValue(); // 获取值

                String newKey = "JEELOWCODE_MPGENVAL" + step;

                // 执行你的替换逻辑
                tmpSql = tmpSql.replace("#{ew.paramNameValuePairs." + key+","  , "#{" +context.getPreSymbol()+ newKey+"," );
                tmpSql = tmpSql.replace("#{ew.paramNameValuePairs." + key+"}"  , "#{" +context.getPreSymbol()+ newKey+"}" );

                dataMap.put("JEELOWCODE_MPGENVAL" + step, value);
            }

            sql += " " + tmpSql;
        }

        return new SqlFormatModel(sql, dataMap);

    }

    //获取dql 排序后的表达式
    private List<ISQLExpression> getDQLExpression() {

        List<ISQLExpression> dqllList = Arrays.stream(expressions)
                .filter(expression -> expression instanceof BuildSelect
                        || expression instanceof BuildJoin
                        || expression instanceof BuildWhere
                        || expression instanceof BuildGroupBy
                        || expression instanceof BuildHaving
                        || expression instanceof BuildOrderBy
                        || expression instanceof BuildInsert
                        || expression instanceof BuildUpdate
                        || expression instanceof BuildDelete)
                .collect(Collectors.toList());

        if (FuncBase.isEmpty(dqllList)) {
            return null;
        }
        //只有一个，不用排序
        if(FuncBase.isNotEmpty(dqllList) && dqllList.size()==1){
            return dqllList;
        }

        //排序
        List<ISQLExpression> sortList = new ArrayList<>();

        //下面是dql,需要安装sql标准查询的格式来凭接
        ISQLExpression tmpSelect = null;
        List<ISQLExpression> tmpJoinList = new ArrayList<>();
        ISQLExpression tmpWhere = null;
        ISQLExpression tmpGroupBy = null;
        ISQLExpression tmpOrderBy = null;
        ISQLExpression tmpHaving = null;

        ISQLExpression tmpInsert = null;
        ISQLExpression tmpUpdate = null;
        ISQLExpression tmpDelete = null;
        for (ISQLExpression expression : expressions) {
            if (expression instanceof BuildSelect) {
                tmpSelect = expression;
            } else if (expression instanceof BuildJoin) {
                tmpJoinList.add(expression);//因为会多个
            } else if (expression instanceof BuildWhere) {
                tmpWhere = expression;
            } else if (expression instanceof BuildGroupBy) {
                tmpGroupBy = expression;
            } else if (expression instanceof BuildHaving) {
                tmpHaving = expression;
            } else if (expression instanceof BuildOrderBy) {
                tmpOrderBy = expression;
            } else if (expression instanceof BuildInsert) {
                tmpInsert = expression;
            } else if (expression instanceof BuildUpdate) {
                tmpUpdate = expression;
            } else if (expression instanceof BuildDelete) {
                tmpDelete = expression;
            }
        }

        //如果是insert类的话，则直接返回
        if (FuncBase.isNotEmpty(tmpInsert)) {
            sortList.add(tmpInsert);
            return sortList;
        }

        //如果是update 和 delete  select 都可以带where
        if (FuncBase.isNotEmpty(tmpSelect)) {
            sortList.add(tmpSelect);
        } else if (FuncBase.isNotEmpty(tmpUpdate)) {
            sortList.add(tmpUpdate);
        } else if (FuncBase.isNotEmpty(tmpDelete)) {
            sortList.add(tmpDelete);
        } else {
            throw new JeeLowCodeException("表达式必须有BuildSelect/BuildUpdate/BuildDelete");
        }

        //第一个
        ISQLExpression firstExpression = sortList.get(0);
        if (firstExpression instanceof BuildSelect && FuncBase.isNotEmpty(tmpJoinList)) {//必须是select的情况下，才能inner join
            sortList.addAll(tmpJoinList);
        }

        //后面是带 where
        if (FuncBase.isNotEmpty(tmpWhere)) {
            sortList.add(tmpWhere);
        }

        if (firstExpression instanceof BuildSelect && FuncBase.isNotEmpty(tmpGroupBy)) {//必须是select的情况下，才有group by
            sortList.add(tmpGroupBy);
            if (FuncBase.isNotEmpty(tmpHaving)) {
                sortList.add(tmpHaving);
            }
        }

        if (firstExpression instanceof BuildSelect && FuncBase.isNotEmpty(tmpOrderBy)) {//必须是select的情况下，才有order by
            sortList.add(tmpOrderBy);
        }
        return sortList;
    }
}
