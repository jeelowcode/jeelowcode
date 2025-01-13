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
package com.jeelowcode.core.framework.config.aspect.enhance;

import cn.hutool.core.date.DateUtil;
import com.jeelowcode.core.framework.config.aspect.enhance.model.BuildSqlEnhanceContext;
import com.jeelowcode.core.framework.config.aspect.enhance.model.EnhanceContext;
import com.jeelowcode.core.framework.config.aspect.enhance.model.EnhanceResult;
import com.jeelowcode.core.framework.entity.EnhanceSqlEntity;
import com.jeelowcode.core.framework.mapper.JeeLowCodeMapper;
import com.jeelowcode.core.framework.mapper.JeeLowCodeSqlMapper;
import com.jeelowcode.core.framework.utils.Func;
import com.jeelowcode.core.framework.utils.FuncWeb;
import com.jeelowcode.framework.plus.SqlHelper;
import com.jeelowcode.framework.plus.build.buildmodel.wrapper.SqlInfoQueryWrapper;
import com.jeelowcode.framework.plus.core.model.SqlFormatModel;
import com.jeelowcode.framework.utils.adapter.IJeeLowCodeAdapter;
import com.jeelowcode.framework.utils.constant.JeeLowCodeConstant;
import com.jeelowcode.framework.utils.enums.JeeLowCodeFieldTypeEnum;
import com.jeelowcode.framework.utils.enums.ParamEnum;
import com.jeelowcode.framework.utils.enums.QueryModelEnum;
import com.jeelowcode.framework.utils.model.ExecuteEnhanceModel;
import com.jeelowcode.framework.utils.model.ResultDataModel;
import com.jeelowcode.framework.utils.utils.FuncBase;
import com.jeelowcode.framework.utils.utils.JeeLowCodeUtils;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.commons.collections4.CollectionUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.LocalVariableTableParameterNameDiscoverer;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

import static com.jeelowcode.core.framework.config.aspect.enhance.JeeLowCodeAnnotationAspectjJAVA.EXPRESSION;
import static com.jeelowcode.core.framework.config.aspect.enhance.JeeLowCodeAnnotationAspectjJAVA.aspectMethodNameMap;
import static com.jeelowcode.framework.utils.constant.EnhanceConstant.*;

/**
 * @author JX
 * @create 2024-08-19 13:46
 * @dedescription:
 */
@Aspect
@Component
public class JeeLowCodeAnnoaionAspectjSQL {


    @Autowired
    private IJeeLowCodeAdapter jeeLowCodeAdapter;

    @Autowired
    private JeeLowCodeSqlMapper sqlMapper;

    @Autowired
    private JeeLowCodeMapper jeeLowCodeMapper;

    private static Map<String, List<EnhanceSqlEntity>> sqlPlugins = new HashMap<>();


    @AfterReturning(value = EXPRESSION, returning = "returnVal")
    public Object afterReturingAdvice(JoinPoint joinPoint, Object returnVal){
        BuildSqlEnhanceContext contextAndPlugins = getContextAndPlugins(joinPoint, returnVal);
        if (FuncBase.isEmpty(contextAndPlugins)){
            return returnVal;
        }
        EnhanceContext context = contextAndPlugins.getContext();
        //判断结果类型
        ExecuteEnhanceModel formModel =null; //表单类
        ResultDataModel listModel =null; //查询类

        Boolean resultFlag = false;
        if(Func.isNotEmpty(returnVal) && returnVal instanceof ExecuteEnhanceModel){//表单类
            formModel = (ExecuteEnhanceModel) returnVal;
            resultFlag = true;
        }
        if(Func.isNotEmpty(returnVal) && returnVal instanceof ResultDataModel){//列表类
            listModel = (ResultDataModel) returnVal;
        }

        //把结果放到上下文中再执行插件
        if(resultFlag){
            String id = formModel.getId();
            context.getResult().setId(id);
            context.getResult().setExitFlag(formModel.isExitFlag());
        }else {
            context.getResult().setRecords(listModel.getRecords());
            context.getResult().setTotal(listModel.getTotal());
            context.getResult().setExitFlag(listModel.isExitFlag());
        }

        //集合操作的插件
        List<EnhanceSqlEntity> setOperations = contextAndPlugins.getSetOperations();
        if(CollectionUtils.isNotEmpty(setOperations)){
            //执行
            executeEnhanceSetOperation(context,setOperations);
            listModel.setTotal(context.getResult().getTotal());
            listModel.setRecords(context.getResult().getRecords());
        }

        //所有sql增强
        List<EnhanceSqlEntity> enhanceSqlEntityList = contextAndPlugins.getEntitys();

        for (EnhanceSqlEntity enhanceSqlEntity : enhanceSqlEntityList) {
            if (setOperations.contains(enhanceSqlEntity)){
                continue;
            }
            this.executSQLPlugin(context,enhanceSqlEntity);
            if (FuncBase.isNotEmpty(context.getResult()) && context.getResult().isExitFlag()){
                listModel.setTotal(context.getResult().getTotal());
                listModel.setRecords(context.getResult().getRecords());
                return listModel;
            }

        }

        if(resultFlag){//结果类型
            ExecuteEnhanceModel executeEnhanceModel=new ExecuteEnhanceModel();
            executeEnhanceModel.setId(context.getResult().getId());
            return executeEnhanceModel;
        }else {
            listModel.setTotal(context.getResult().getTotal());
            listModel.setRecords(context.getResult().getRecords());
        }
        return listModel;
    }

    public BuildSqlEnhanceContext getContextAndPlugins(JoinPoint joinPoint,Object returnVal){
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        // 获取方法名称
        String methodName = methodSignature.getName();
        if (!aspectMethodNameMap.containsKey(methodName)) {//不在拦截范围内 add
            return null;
        }
        Object[] args = joinPoint.getArgs();
        LocalVariableTableParameterNameDiscoverer discoverer = new LocalVariableTableParameterNameDiscoverer();
        String[] parameterNames = discoverer.getParameterNames(methodSignature.getMethod());
        Map<String, Object> paramMap = new HashMap<>();
        for (int i = 0; i < parameterNames.length; i++) {
            paramMap.put(parameterNames[i], args[i]);
        }

        Long dbFormId = (Long) paramMap.getOrDefault("dbFormId", null);
        Long dataId = (Long)paramMap.getOrDefault("id",null);
        Page page = (Page)paramMap.getOrDefault("page", null);
        List<Long> dataIdList =(List)paramMap.getOrDefault("dataIdList",null);
        String buttonCode = aspectMethodNameMap.get(methodName);
        String key = dbFormId + "_" + buttonCode;
        List<EnhanceSqlEntity> allPlugins = sqlPlugins.getOrDefault(key, null);
        if (Func.isEmpty(allPlugins)) {
            return null;
        }
        List<EnhanceSqlEntity> setOperation = new ArrayList<>();
        if (FuncBase.isNotEmpty(allPlugins)) {
            setOperation = allPlugins.stream()
                    .filter(enhanceSqlEntity -> (FuncBase.equals(enhanceSqlEntity.getButtonCode(), ENHANCE_LIST) ||
                            FuncBase.equals(enhanceSqlEntity.getButtonCode(), ENHANCE_EXPORT)))
                    .collect(Collectors.toList());
            if (setOperation.size() < 2) {
                //如果只有一条，则直接执行，不用走集合操作
                setOperation.clear();
            }
        }
        List<Map<String,Object>> dataList = new ArrayList<>();
        if(returnVal instanceof ResultDataModel){
            dataList = ((ResultDataModel) returnVal).getRecords();
        }
        EnhanceContext context = new EnhanceContext();
        Map<String, Object> params = JeeLowCodeUtils.getMap2Map(paramMap, "params");
        context.setParam(dbFormId, params, dataList, dataId, page, dataIdList);
        context.setResult(new EnhanceResult());
        return new BuildSqlEnhanceContext(context, allPlugins,setOperation);
    }

    public static void initSqlPlugins(Map<String, List<EnhanceSqlEntity>> initSQLPlugins) {
        sqlPlugins = initSQLPlugins;
    }

    public void executSQLPlugin(EnhanceContext context, EnhanceSqlEntity enhanceSqlEntity) {

        switch (enhanceSqlEntity.getButtonCode()){
            case ENHANCE_ADD:
                //新增
                this.executeEnhanceAfterAdd(context, enhanceSqlEntity);
                break;
            case ENHANCE_DELETE:
                //删除
                this.executeEnhanceAfterDelete(context, enhanceSqlEntity);
                break;
            case ENHANCE_EDIT:
                //编辑
                this.executeEnhanceAfterEdit(context, enhanceSqlEntity);
                break;
            case ENHANCE_DETAIL:
                //详情
                this.executeEnhanceAfterDetail(context, enhanceSqlEntity);
                break;
            case ENHANCE_IMPORT:
                //导入
                this.executeEnhanceAfterImport(context, enhanceSqlEntity);
                break;
            case ENHANCE_LIST:
                //列表 前端只保留了一个
                Map<String, Object> params = context.getParam().getParams();
                Integer pageSize = JeeLowCodeUtils.getMap2Integer(params, "pageSize");
                if(Func.isEmpty(pageSize) || pageSize==-1 || Func.equals(pageSize, JeeLowCodeConstant.NOT_PAGE)){
                    this.executeEnhanceAfterList(context, enhanceSqlEntity);
                }else{
                    this.executeEnhanceAfterPage(context, enhanceSqlEntity);
                }
                break;
            case ENHANCE_PAGE:
                //分页
                this.executeEnhanceAfterPage(context, enhanceSqlEntity);
                break;
            case ENHANCE_EXPORT:
                //导出
                this.executeEnhanceAfterExport(context, enhanceSqlEntity);
                break;
        }
    }
    //新增
    public void executeEnhanceAfterAdd(EnhanceContext context,EnhanceSqlEntity sqlEntity) {
        Long dbFormId = context.getParam().getDbFormId();
        Map<String,Object> paramMap = context.getParam().getParams();
        if(FuncBase.isNotEmpty(context.getParam().getDataId())){//把id放入到参数
            paramMap.put("id",context.getParam().getDataId());
        }

        this.executeSql(sqlEntity.getExecuteSql(), paramMap);

    }
    //删除
    public void executeEnhanceAfterDelete(EnhanceContext context,EnhanceSqlEntity sqlEntity){
        List<Long> dataIdList = context.getParam().getDataIdList();


            String executeSql = sqlEntity.getExecuteSql();
            if(FuncBase.isEmpty(executeSql)){
                return ;
            }
            //因为是批量删除的，需要拆分出来
            for(Long dataId:dataIdList){
                Map<String,Object> dataMap =new HashMap<>();
                dataMap.put("id",dataId);

                //执行sql
                this.executeSql(executeSql,dataMap);
            }


    }

    //编辑
    public void executeEnhanceAfterEdit(EnhanceContext context,EnhanceSqlEntity sqlEntity){
        Long dataId = context.getParam().getDataId();
        Map<String,Object> paramMap = context.getParam().getParams();

        paramMap.put("id", dataId);//把id放到json里面传递

        this.executeSql(sqlEntity.getExecuteSql(),paramMap);

    }

    //详情
    public void executeEnhanceAfterDetail(EnhanceContext context,EnhanceSqlEntity sqlEntity){

        Long dbFormId = context.getParam().getDbFormId();
        Long id = (Long) context.getParam().getDataId();
        Map<String, Object> params = context.getParam().getParams();
        params.put("id",id);//把id放入参数集
        
        String autoWhereSql = this.getAutoWhereSql(dbFormId, params);

        String executeSql = sqlEntity.getExecuteSql();
        List<Map<String, Object>> dataMapList = this.executeSelectListSql(executeSql, params, autoWhereSql);
        context.setResult( ResultDataModel.fomatList(dataMapList));

    }

    //导入
    public void executeEnhanceAfterImport(EnhanceContext context,EnhanceSqlEntity sqlEntity){

        Map<String,Object> paramMap = context.getParam().getParams();

        this.executeSql(sqlEntity.getExecuteSql(),paramMap);
    }

    //列表
    public void executeEnhanceAfterList(EnhanceContext context,EnhanceSqlEntity sqlEntity){
        Long dbFormId = context.getParam().getDbFormId();
        Map<String, Object> params = context.getParam().getParams();
        String executeSql = sqlEntity.getExecuteSql();

        String autoWhereSql = this.getAutoWhereSql(dbFormId, params);
        List<Map<String, Object>> dataMapList = this.executeSelectListSql(executeSql, params, autoWhereSql);
        context.getResult().setRecords(dataMapList);
        context.getResult().setExitFlag(true);
    }

    // 分页
    public void executeEnhanceAfterPage(EnhanceContext context,EnhanceSqlEntity sqlEntity){
        Long dbFormId = context.getParam().getDbFormId();
        Map<String, Object> params = context.getParam().getParams();
        Integer pageNo = JeeLowCodeUtils.getMap2Integer(params, "pageNo");
        Integer pageSize = JeeLowCodeUtils.getMap2Integer(params, "pageSize");
        Page page = FuncWeb.getPage(pageNo, pageSize);
        String executeSql = sqlEntity.getExecuteSql();
        String autoWhereSql = this.getAutoWhereSql(dbFormId, params);
        IPage<Map<String, Object>> pages = this.executeSelectPageSql(executeSql, params, autoWhereSql, page);
        FuncWeb.setPageResult(context, pages);
    }

    //导出
    public void executeEnhanceAfterExport(EnhanceContext context, EnhanceSqlEntity sqlEntity){
        Long dbFormId = context.getParam().getDbFormId();
        Map<String, Object> params = context.getParam().getParams();
        String executeSql = sqlEntity.getExecuteSql();

        String autoWhereSql = this.getAutoWhereSql(dbFormId, params);
        List<Map<String, Object>> dataMapList = this.executeSelectListSql(executeSql, params, autoWhereSql);
        context.getResult().setRecords(dataMapList);
        context.getResult().setExitFlag(true);
    }

    //执行集合操作
    public void executeEnhanceSetOperation(EnhanceContext context,List<EnhanceSqlEntity> sqlEntitys){
        sqlEntitys.sort(Comparator.comparing(EnhanceSqlEntity::getSort,Comparator.nullsLast(Integer::compareTo)));
        for (int i = 0; i < sqlEntitys.size()-1; i++) {
            EnhanceSqlEntity leftEntity = null;
            EnhanceSqlEntity rightEntity = null;
            if (i == 0) {
                leftEntity = sqlEntitys.get(i);
                rightEntity = sqlEntitys.get(i + 1);
            } else {
                rightEntity = sqlEntitys.get(i + 1);
            }
            switch (rightEntity.getListResultHandleType()) {
                case "1":
                    //执行合集操作
                    executSetOperationOr(leftEntity, rightEntity, context);
                    break;
                case "2":
                    //执行差集操作
                    executSetOperationDiffer(leftEntity, rightEntity, context);
                    break;
                case "3":
                    //执行并集操作
                    executSetOperationUnion(leftEntity, rightEntity, context);
                    break;
                case "4":
                    //执行交集操作
                    executSetOperationInterSerction(leftEntity, rightEntity, context);
                    break;
                default:
                case "0":
                    //执行串行操作
                    executSetOperationAnd(leftEntity, rightEntity, context);
                    break;
            }

        }

    }


    //执行合集操作
    private void executSetOperationOr(EnhanceSqlEntity leftEntity, EnhanceSqlEntity rightEntity, EnhanceContext context) {
        List<Map<String, Object>> leftRecords ;
      if(FuncBase.isNotEmpty(leftEntity)){
          EnhanceContext leftContext = context.clone();
          executSQLPlugin(leftContext, leftEntity);
          leftRecords = leftContext.getResult().getRecords();
      }else {
          leftRecords = context.getResult().getRecords();
      }
        EnhanceContext rightContext = context.clone();
        executSQLPlugin(rightContext, rightEntity);
        List<Map<String, Object>> rightRecords = rightContext.getResult().getRecords();
        CollectionUtils.addAll(leftRecords,rightRecords);
        context.getResult().setRecords(leftRecords);

    }

    private void executSetOperationDiffer(EnhanceSqlEntity leftEntity, EnhanceSqlEntity rightEntity, EnhanceContext context){
        List<Map<String,Object>> differ;
        List<Map<String, Object>> leftRecords ;
        if(FuncBase.isNotEmpty(leftEntity)){
            EnhanceContext leftContext = context.clone();
            executSQLPlugin(leftContext, leftEntity);
            leftRecords = leftContext.getResult().getRecords();
        }else {
            leftRecords = context.getResult().getRecords();
        }
        EnhanceContext rightContext = context.clone();
        executSQLPlugin(rightContext, rightEntity);
        List<Map<String, Object>> rightRecords = rightContext.getResult().getRecords();
        differ = (List<Map<String, Object>>) CollectionUtils.subtract(leftRecords, rightRecords);
        context.getResult().setRecords(differ);



    };
    //并集
    public void executSetOperationUnion(EnhanceSqlEntity leftEntity, EnhanceSqlEntity rightEntity, EnhanceContext context){
        List<Map<String,Object>> union;
        List<Map<String, Object>> leftRecords ;
        if(FuncBase.isNotEmpty(leftEntity)){
            EnhanceContext leftContext = context.clone();
            executSQLPlugin(leftContext, leftEntity);
            leftRecords = leftContext.getResult().getRecords();
        }else {
            leftRecords = context.getResult().getRecords();
        }
        EnhanceContext rightContext = context.clone();
        executSQLPlugin(rightContext, rightEntity);
        List<Map<String, Object>> rightRecords = rightContext.getResult().getRecords();
        union = (List<Map<String, Object>>) CollectionUtils.union(leftRecords, rightRecords);
        context.getResult().setRecords(union);
    }

    //交集
    public void executSetOperationInterSerction(EnhanceSqlEntity leftEntity, EnhanceSqlEntity rightEntity, EnhanceContext context){
        List<Map<String,Object>> intersection;
        List<Map<String, Object>> leftRecords ;
        if(FuncBase.isNotEmpty(leftEntity)){
            EnhanceContext leftContext = context.clone();
            executSQLPlugin(leftContext, leftEntity);
            leftRecords = leftContext.getResult().getRecords();
        }else {
            leftRecords = context.getResult().getRecords();
        }
        EnhanceContext rightContext = context.clone();
        executSQLPlugin(rightContext, rightEntity);
        List<Map<String, Object>> rightRecords = rightContext.getResult().getRecords();
        intersection = (List<Map<String, Object>>) CollectionUtils.intersection(leftRecords, rightRecords);
        context.getResult().setRecords(intersection);
    }

    //串行
    public void executSetOperationAnd(EnhanceSqlEntity leftEntity, EnhanceSqlEntity rightEntity, EnhanceContext context){
        executSQLPlugin(context, leftEntity);
        executSQLPlugin(context, rightEntity);
    }

    /**
     * 获取自动构建where的语句
     * @param dbFormId
     * @return
     */
    public String getAutoWhereSql(Long dbFormId, Map<String, Object> params){
        SqlInfoQueryWrapper.Wrapper queryWrapper = SqlHelper.getQueryWrapper();

        List<Map<String, String>> whereFieldMapList = jeeLowCodeMapper.getDbWhereFieldList(dbFormId);

        //没有搜索条件
        if (FuncBase.isEmpty(whereFieldMapList)) {
            SqlFormatModel sqlFormatModel = queryWrapper.buildSql();
            String whereSql = sqlFormatModel.getSql();
            Map<String, Object> paramMap = sqlFormatModel.getDataMap();
            if(Func.isNotEmpty(paramMap)){
                params.putAll(paramMap);
            }
            whereSql=whereSql.substring(6);//因为是where开头要去掉
            return whereSql;
        }

        //如果是多选的时候，要做特殊处理，因为多选的时候 ，数据库存储的是 11,22,33
        String moreSelectFieldListStr = (String)params.get(ParamEnum.MORE_SELECT_FIELD.getCode());
        List<String> moreSelectFieldList=null;
        if(FuncBase.isNotEmpty(moreSelectFieldListStr)){
            moreSelectFieldList = FuncBase.toStrList(moreSelectFieldListStr);
        }

        for (Map<String, String> whereFieldMap : whereFieldMapList) {
            String field_code = whereFieldMap.get("field_code");//字段
            String field_type = whereFieldMap.get("field_type");//类型
            String query_mode = whereFieldMap.get("query_mode");//EQ LIKE
            Object obj = params.get(field_code);
            if (FuncBase.isEmpty(obj)) {
                continue;
            }
            JeeLowCodeFieldTypeEnum fieldTypeEnum = JeeLowCodeFieldTypeEnum.getByFieldType(field_type);
            switch (fieldTypeEnum) {
                case DATE:
                    obj = DateUtil.parse(Func.toStr(obj), "yyyy-MM-dd");
                    break;
                case DATETIME:
                    obj = DateUtil.parse(Func.toStr(obj), "yyyy-MM-dd HH:mm:ss");
                    break;
                case TIME:
                    obj = DateUtil.parse(Func.toStr(obj), "HH:mm:ss");
                    break;
            }
            Object finalObj = obj;
            //类型转换 111,22,33
            if (FuncBase.equals(query_mode, QueryModelEnum.EQ.getCode())) {//精确
                if(FuncBase.isNotEmpty(moreSelectFieldList) && moreSelectFieldList.contains(field_code)){//在多选里面

                    queryWrapper.setWhere(where->{
                        where.and(w->w.eq(field_code, finalObj)
                                .or().likeLeft(field_code,","+ finalObj)
                                .or().likeRight(field_code, finalObj +",")
                                .or().like(field_code,","+ finalObj +","));
                    });
                }else{
                    queryWrapper.setWhere(where->{
                        where.eq(field_code, finalObj);
                    });
                }

            } else if (FuncBase.equals(query_mode, QueryModelEnum.RANGE.getCode())) {//范围
                String listStr = FuncBase.toStr(obj);
                List<String> paramList = FuncBase.toStrList(listStr);// 1<=x<2
                Object leftVal = paramList.get(0);
                Object rightVal = paramList.get(1);
                if (FuncBase.equals(field_type, JeeLowCodeFieldTypeEnum.BIGINT)) {//防止大整型出现精度问题
                    leftVal = FuncBase.toLong(leftVal);
                    rightVal = FuncBase.toLong(rightVal);
                }
                if (FuncBase.isNotEmpty(leftVal) && FuncBase.isNotEmpty(rightVal)) {
                    Object finalLeftVal = leftVal;
                    Object finalRightVal = rightVal;
                    queryWrapper.setWhere(where->{
                        where.between(field_code, finalLeftVal, finalRightVal);//区间
                    });
                } else if (FuncBase.isNotEmpty(leftVal)) {//>=
                    Object finalLeftVal1 = leftVal;
                    queryWrapper.setWhere(where->{
                        where.ge(field_code, finalLeftVal1);
                    });
                } else if (FuncBase.isNotEmpty(rightVal)) {//<=
                    Object finalRightVal1 = rightVal;
                    queryWrapper.setWhere(where->{
                        where.le(field_code, finalRightVal1);
                    });
                }
            } else {
                queryWrapper.setWhere(where->{
                    where.like(field_code, finalObj);
                });
            }
        }
        SqlFormatModel sqlFormatModel = queryWrapper.buildSql();

        String whereSql = sqlFormatModel.getSql();
        if (FuncBase.isEmpty(whereSql)) {
            return whereSql;
        }
        Map<String, Object> paramMap = sqlFormatModel.getDataMap();
        if(Func.isNotEmpty(paramMap)){
            params.putAll(paramMap);
        }
        whereSql=whereSql.substring(6);//因为是where开头要去掉
        return whereSql;
    }

    /**
     * 运行增删改
     * @param executeSql
     * @param paramMap
     * @throws Throwable
     */
    public void executeSql(String executeSql, Map<String, Object> paramMap)  {
        String sql = this.getSql(executeSql, paramMap);
        this.execute(sql,paramMap);
    }

    /**
     * 执行查询
     *
     * @param executeSql
     * @param paramMap
     * @return
     * @throws Throwable
     */
    public List<Map<String, Object>> executeSelectListSql(String executeSql, Map<String, Object> paramMap, String autoWhereSql) {
        String sql = this.getSql(executeSql, paramMap, autoWhereSql);
        Object obj = this.execute(sql,paramMap);
        return (List<Map<String, Object>>)obj;
    }

    //分页
    public IPage<Map<String, Object>> executeSelectPageSql(String executeSql, Map<String, Object> paramMap, String autoWhereSql, Page page) {
        String sql = this.getSql(executeSql, paramMap, autoWhereSql);
        Object obj = this.execute(sql, paramMap,page);
        return (IPage<Map<String, Object>>)obj;
    }

    /**
     * 获取执行sql
     * @param executeSql
     * @param paramMap
     * @return
     */
    private String getSql(String executeSql, Map<String, Object> paramMap){
        return this.getSql(executeSql,paramMap,null);
    }
    private String getSql(String executeSql, Map<String, Object> paramMap, String autoWhereSql){

        //替换参数
        executeSql = Func.replaceParam(executeSql,paramMap,autoWhereSql,jeeLowCodeAdapter);

        return executeSql.trim();
    }

    /**
     * 执行sql
     * @param sql
     * @return
     */
    private Object execute(String sql,Map<String,Object> dataMap){
        return execute(sql,dataMap,null);
    }

    private Object execute(String sql,Map<String,Object> dataMap, Page page){
        sql=sql.trim();
        //执行sql
        if (sql.startsWith("insert") || sql.startsWith("INSERT")) {
            sqlMapper.insertData(sql,dataMap);
        } else if (sql.startsWith("update") || sql.startsWith("UPDATE")) {
            sqlMapper.updateData(sql,dataMap);
        } else if (sql.startsWith("delete") || sql.startsWith("DELETE")) {
            sqlMapper.deleteData(sql,dataMap);
        } else if (sql.startsWith("select") || sql.startsWith("SELECT")) {
            if(FuncBase.isNotEmpty(page)){
                return sqlMapper.selectPageData(page, sql,dataMap);
            }
            return sqlMapper.selectData(sql,dataMap);
        }
        return null;
    }

    // 新增增强
    public static void addPlugin(EnhanceSqlEntity sqlEntity){
        String key = sqlEntity.getDbformId()+"_"+sqlEntity.getButtonCode();
        sqlPlugins.merge(key, new ArrayList<>(Collections.singletonList(sqlEntity)), (oldValue, newValue) -> {
            oldValue.addAll(newValue);
            return oldValue;
        });
    }

    // 修改增强
    public static void updatePlugin(EnhanceSqlEntity sqlEntity) {
        String key = sqlEntity.getDbformId() + "_" + sqlEntity.getButtonCode();
        sqlPlugins.merge(key, new ArrayList<>(Collections.singletonList(sqlEntity)), (oldValue, newValue) -> {
            // 先删除再新增
            oldValue.removeIf(enhanceSql -> FuncBase.equals(enhanceSql.getId(), sqlEntity.getId()));
            oldValue.add(sqlEntity);
            return oldValue;
        });
    }

    // 删除增强
    public static void removePlugin(EnhanceSqlEntity sqlEntity){
        String key = sqlEntity.getDbformId() + "_" + sqlEntity.getButtonCode();
        List<EnhanceSqlEntity> sqlEntityList = sqlPlugins.getOrDefault(key, new ArrayList<>());
        // id相同则删除
        sqlEntityList.removeIf(enhanceSqlEntity -> sqlEntity.getId().equals(enhanceSqlEntity.getId()));
        if (FuncBase.isEmpty(sqlEntityList)) {
            sqlPlugins.remove(key);
        }
    }
}
