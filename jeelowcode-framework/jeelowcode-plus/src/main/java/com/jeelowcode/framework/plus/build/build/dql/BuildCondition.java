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
package com.jeelowcode.framework.plus.build.build.dql;

import com.jeelowcode.framework.plus.build.buildmodel.ExpressionModel;
import com.jeelowcode.framework.plus.build.buildmodel.SQLInterpretContext;
import com.jeelowcode.framework.plus.build.buildmodel.dql.SqlInfoConditionModel;
import com.jeelowcode.framework.plus.build.buildmodel.dql.SqlInfoHavingModel;
import com.jeelowcode.framework.plus.core.dql.select.JeeLowCodeQueryWrapper;
import com.jeelowcode.framework.plus.core.model.SqlFormatModel;
import com.jeelowcode.framework.utils.utils.FuncBase;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

/**
 * @author JX
 * @create 2024-07-29 16:00
 * @dedescription:
 */

public class BuildCondition {

    public JeeLowCodeQueryWrapper<Object> wrapper = new JeeLowCodeQueryWrapper();

    public SQLInterpretContext context;


    public  SqlFormatModel context2Condition(SqlInfoConditionModel conditionModel){
        List<ExpressionModel> expressions =conditionModel.getExpressions();
        expressions.forEach(expressionModel -> {
            SqlInfoConditionModel.WhereType whereType = expressionModel.getWhereType();
            String column = expressionModel.getColumn();
            Object[] value = expressionModel.getValue();
            switch (whereType) {
                case GT://>
                    gt(column, value);
                    break;
                case GE://>=
                    ge(column, value);
                    break;
                case NE:// !=
                    ne(column, value);
                    break;
                case EQ:// =
                    eq(column, value);
                    break;
                case LT://<
                    lt(column, value);
                    break;
                case LE://<=
                    le(column, value);
                    break;
                case BETWEEN:
                    between(column, value);
                    break;
                case NOT_BETWEEN:
                    notbetween(column, value);
                    break;
                case IN:
                    in(column, value);
                    break;
                case NOT_IN:
                    notIn(column, value);
                    break;
                case LIKE:
                    like(column, value);
                    break;
                case NOT_LIKE:
                    notLike(column, value);
                    break;
                case LIKE_LEFT:
                    likeLeft(column, value);
                    break;
                case LIKE_RIGHT:
                    likeRight(column, value);
                    break;
                case IS_NULL:
                    isNull(column);
                    break;
                case IS_NOT_NULL:
                    isNotNull(column);
                    break;
                case OR:
                    or();
                    break;
                case OR_BUILD:
                    orBuild((Consumer<SqlInfoConditionModel.Builder>) value[0]);
                    break;
                case AND_BUILD:
                    andBuild((Consumer<SqlInfoConditionModel.Builder>) value[0]);
                    break;
                case APPLY:
                    apply(column);
                    break;
            }
        });
        String sqlStr = "";

        if(conditionModel instanceof SqlInfoHavingModel){
            sqlStr=wrapper.getHavingCustomSqlSegment();
        }else{
            sqlStr=wrapper.getCustomSqlSegment();
        }
        Map<String, Object> paramNameValuePairs = wrapper.getParamNameValuePairs();

        SqlFormatModel sqlFormatModel = new SqlFormatModel(sqlStr, paramNameValuePairs);
        return sqlFormatModel;
    }

    public void gt(String column, Object[] value) {
        wrapper.gt(column, value[0]);
        return;
    }

    public void ge(String column, Object[] value) {
        wrapper.ge(column, value[0]);
        return;
    }

    public void ne(String column, Object[] value) {
        wrapper.ne(column, value[0]);
        return;
    }

    public void eq(String column, Object[] value) {
        wrapper.eq(column, value[0]);
        return;
    }

    public void between(String column, Object[] value) {
        Object value1=value[0];
        Object value2=value[1];
        wrapper.between(column, value1, value2);
        return;
    }

    public void notbetween(String column,Object[] value) {
        Object value1=value[0];
        Object value2=value[1];
        wrapper.notBetween(column, value1, value2);
        return;
    }

    public void in(String column, Object[] value) {
        if(value.length>1){
            wrapper.in(column, value);
            return;
        }
        Object obj = value[0];
        if(obj instanceof List){
            List<Object> objList=(List)value[0];
            wrapper.in(column, objList);
        }else{
            wrapper.in(column, obj);
        }
        return;
    }

    public void notIn(String column, Object[] value) {
        if(value.length>1){
            wrapper.notIn(column, value);
            return;
        }
        Object obj = value[0];
        if(obj instanceof List){
            List<Object> objList=(List)value[0];
            wrapper.notIn(column, objList);
        }else{
            wrapper.notIn(column, obj);
        }
        return;
    }

    public void like(String column, Object[] value) {
        wrapper.like(column, value[0]);
        return;
    }

    public void notLike(String column, Object[] value) {
        wrapper.notLike(column, value[0]);
        return;
    }

    public void likeLeft(String column, Object[] value) {
        wrapper.likeLeft(column, value[0]);
        return;
    }

    public void likeRight(String column, Object[] value) {
        wrapper.likeRight(column, value[0]);
        return;
    }

    public void lt(String column, Object[] value) {
        wrapper.lt(column, value[0]);
        return;
    }

    public void le(String column, Object[] value) {
        wrapper.le(column, value[0]);
        return;
    }

    public void isNull(String column) {
        wrapper.isNull(column);
        return;
    }

    public void isNotNull(String column) {
        wrapper.isNotNull(column);
        return;
    }
    public void apply(String column) {
        wrapper.apply(column);
        return;
    }
    public void or() {
        wrapper.or();
        return;
    }


    public void orBuild(Consumer<SqlInfoConditionModel.Builder> condition) {
        try{
            SqlInfoConditionModel.Builder builder = new SqlInfoConditionModel.Builder();
            condition.accept(builder);
            SqlInfoConditionModel<BuildCondition> build = builder.build();
            BuildCondition buildCondition =  new BuildCondition();
            SqlFormatModel sqlFormatModel = buildCondition.context2Condition(build);

            String tmpSql = sqlFormatModel.getSql();
            tmpSql=tmpSql.substring(6);
            Map<String, Object> tmpMap = sqlFormatModel.getDataMap();
            if (FuncBase.isEmpty(tmpMap)) {//没有参数的情况下
                String newSql=String.format("( 1!=1 OR %s) ",tmpSql);
                wrapper.apply(newSql);
                return;
            }

            //存在参数的情况下
            List<Map.Entry<String, Object>> entrieList = new ArrayList<>(tmpMap.entrySet());
            Collections.reverse(entrieList);

            int step=0;
            Object[] objects=new Object[entrieList.size()];

            // 然后，从列表的末尾开始遍历
            for (Map.Entry<String, Object> entry : entrieList) {
                String key = entry.getKey(); // 获取键
                Object value = entry.getValue(); // 获取值

                // 执行你的替换逻辑
                tmpSql = tmpSql.replace("#{"+ FuncBase.PARAM_NAME_VALUE_PAIRS+"." + key + "}", "{"+step+"}");
                objects[step]=value;

                step++;
            }
            String newSql=String.format(" ( 1!=1 OR %s) ",tmpSql);//1!=1 兼容语法
            wrapper.apply(newSql,objects);
        }catch (Exception e){

        }

        return;
    }

    public void andBuild(Consumer<SqlInfoConditionModel.Builder> condition) {
        try{

            SqlInfoConditionModel.Builder builder = new SqlInfoConditionModel.Builder();
            condition.accept(builder);
            SqlInfoConditionModel<BuildCondition> build = builder.build();
            BuildCondition buildCondition =  new BuildCondition();
            SqlFormatModel sqlFormatModel = buildCondition.context2Condition(build);

            String tmpSql = sqlFormatModel.getSql();
            tmpSql=tmpSql.substring(6);
            Map<String, Object> tmpMap = sqlFormatModel.getDataMap();
            if (FuncBase.isEmpty(tmpMap)) {//没有参数的情况下
                String newSql=String.format("(%s) ",tmpSql);
                wrapper.apply(newSql);
                return;
            }

            //存在参数的情况下
            List<Map.Entry<String, Object>> entrieList = new ArrayList<>(tmpMap.entrySet());
            Collections.reverse(entrieList);

            int step=0;
            Object[] objects=new Object[entrieList.size()];

            // 然后，从列表的末尾开始遍历
            for (Map.Entry<String, Object> entry : entrieList) {
                String key = entry.getKey(); // 获取键
                Object value = entry.getValue(); // 获取值

                // 执行你的替换逻辑
                tmpSql = tmpSql.replace("#{"+ FuncBase.PARAM_NAME_VALUE_PAIRS+"." + key + "}", "{"+step+"}");

                objects[step]=value;

                step++;
            }
            String newSql=String.format(" (%s) ",tmpSql);
            wrapper.apply(newSql,objects);
        }catch (Exception e){

        }

        return;
    }

    public JeeLowCodeQueryWrapper<Object> getWrapper() {
        return wrapper;
    }

    public void setWrapper(JeeLowCodeQueryWrapper<Object> wrapper) {
        this.wrapper = wrapper;
    }

    public SQLInterpretContext getContext() {
        return this.context;
    }

    public void setContext(SQLInterpretContext context) {
        this.context = context;
    }
}
