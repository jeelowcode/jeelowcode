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
package com.jeelowcode.core.framework.utils;


import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpRequest;
import com.jeelowcode.framework.global.JeeLowCodeBaseConstant;
import com.jeelowcode.core.framework.params.CopyEnhanceParam;
import com.jeelowcode.core.framework.params.vo.ReportFieldVo;
import com.jeelowcode.framework.exception.JeeLowCodeException;
import com.jeelowcode.framework.utils.adapter.IJeeLowCodeAdapter;
import com.jeelowcode.framework.utils.constant.JeeLowCodeConstant;
import com.jeelowcode.framework.utils.enums.JeeLowCodeFieldTypeEnum;
import com.jeelowcode.framework.utils.enums.SqlParamEnum;
import com.jeelowcode.framework.utils.enums.YNEnum;
import com.jeelowcode.framework.utils.model.JeeLowCodeDept;
import com.jeelowcode.framework.utils.params.JeeLowCodeDeptParam;
import com.jeelowcode.framework.utils.tool.spring.SpringUtils;
import com.jeelowcode.framework.utils.utils.FuncBase;
import com.jeelowcode.framework.utils.utils.JeeLowCodeUtils;
import com.jeelowcode.framework.utils.component.properties.JeeLowCodeProperties;
import com.jeelowcode.core.framework.entity.FormFieldEntity;
import com.jeelowcode.core.framework.entity.FormFieldWebEntity;
import com.jeelowcode.core.framework.params.model.WebFormatConfigModel;
import com.jeelowcode.core.framework.params.vo.DbFormFieldIdVo;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.ql.util.express.DefaultContext;
import com.ql.util.express.ExpressRunner;
import oracle.sql.TIMESTAMP;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.util.AntPathMatcher;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.CodeSource;
import java.sql.Blob;
import java.sql.Clob;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinWorkerThread;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * 工具类
 */
public class Func extends FuncBase {


    //校验数据类型
    public static Object fomatDbValue(String fieldType, Object value) {
        if (FuncBase.isEmpty(value)) {
            return null;
        }
        if (FuncBase.equals(fieldType, JeeLowCodeFieldTypeEnum.STRING.getFieldType())
                || FuncBase.equals(fieldType, JeeLowCodeFieldTypeEnum.TEXT.getFieldType())
                || FuncBase.equals(fieldType, JeeLowCodeFieldTypeEnum.LONGTEXT.getFieldType())) {//字符串
            return FuncBase.toStr(value);
        } else if (FuncBase.equals(fieldType, JeeLowCodeFieldTypeEnum.INTEGER.getFieldType())) {
            return FuncBase.toInt(value);
        } else if (FuncBase.equals(fieldType, JeeLowCodeFieldTypeEnum.BIGINT.getFieldType())) {
            return FuncBase.toLong(value);
        } else if (FuncBase.equals(fieldType, JeeLowCodeFieldTypeEnum.BIGDECIMAL.getFieldType())) {
            return new BigDecimal(FuncBase.toStr(value));
        } else if (FuncBase.equals(fieldType, JeeLowCodeFieldTypeEnum.DATE.getFieldType())) {
            //yyyy-MM-dd
            String str = FuncBase.toStr(value);
            return DateUtil.parseDate(str.substring(0, 10));
        } else if (FuncBase.equals(fieldType, JeeLowCodeFieldTypeEnum.DATETIME.getFieldType())) {
            //yyyy-MM-dd HH:mm:ss
            String str = FuncBase.toStr(value);
            return DateUtil.parseDateTime(str.substring(0, 19));
        } else if (FuncBase.equals(fieldType, JeeLowCodeFieldTypeEnum.TIME.getFieldType())) {
            //HH:mm:ss
            String str = FuncBase.toStr(value);
            return DateUtil.parseTime(str.substring(0, 8));
        }
        return FuncBase.toStr(value);
    }

    /**
     * 校验表名称是否可用
     *
     * @param tableName
     * @return true=可用 false=不可用
     */
    public static Boolean checkTableName(String tableName) {
        List<String> excludeTableNamesList = JeeLowCodeProperties.getExcludeTableNames();
        if (FuncBase.isEmpty(excludeTableNamesList)) {
            return true;
        }

        boolean flag = excludeTableNamesList.stream()
                .anyMatch(fieldName -> tableName.startsWith(fieldName));
        return !flag;

    }

    public static boolean isJeeLowCodeUrl(HttpServletRequest request) {
        List<String> jeeLowCodeUrlList = new ArrayList<>();
        jeeLowCodeUrlList.add(JeeLowCodeBaseConstant.REQUEST_URL_START+"/** ");
        if(isEmpty(request)){
            return false;
        }
        String requestURI = request.getRequestURI();
        if(isEmpty(requestURI)){
            return false;
        }

        // 快速匹配，保证性能
        if (CollUtil.contains(jeeLowCodeUrlList, requestURI)) {
            return true;
        }
        AntPathMatcher pathMatcher = new AntPathMatcher();
        // 逐个 Ant 路径匹配
        for (String url : jeeLowCodeUrlList) {
            if (pathMatcher.match(url, request.getRequestURI())) {
                return true;
            }
        }
        return false;
    }


    /**
     * 将list转为map  key=id  value=id
     *
     * @param dataList
     * @return
     */
    public static Set<String> list2SetId(List<Map<String, Object>> dataList) {
        Set<String> idSet = dataList.stream()
                .filter(subParamMap -> !Func.isEmpty(Func.getMap2Str(subParamMap, "id")))
                .map(subParamMap -> Func.getMap2Str(subParamMap, "id"))
                .collect(Collectors.toSet());

        return idSet;
    }

    /**
     * 判断新字段和旧字段是否一致
     *
     * @return false=不一致  true=一致
     */
    public static boolean dbFormFieldEquals(List<FormFieldEntity> oldFieldList, List<FormFieldEntity> newFieldList) {
        if (FuncBase.isEmpty(oldFieldList) && FuncBase.isNotEmpty(newFieldList)) {
            return false;
        }
        Map<String, FormFieldEntity> oldMap = new HashMap<>();
        for (FormFieldEntity dbFormFieldEntity : oldFieldList) {
            String fieldCode = dbFormFieldEntity.getFieldCode();
            oldMap.put(fieldCode, dbFormFieldEntity);
        }

        for (FormFieldEntity newField : newFieldList) {
            String fieldCode = newField.getFieldCode();
            String isDb = newField.getIsDb();
            if (Func.equals(isDb, YNEnum.N.getCode())) {//不用同步到数据库
                continue;
            }

            FormFieldEntity oldfield = oldMap.get(fieldCode);
            if (FuncBase.isEmpty(oldfield)) {//新-存在，旧不存在
                return false;
            }
            //不一样
            if (!FuncBase.equals(oldfield.getFieldCode(), newField.getFieldCode())
                    || !FuncBase.equals(oldfield.getFieldName(), newField.getFieldName())
                    || !FuncBase.equals(oldfield.getFieldLen(), newField.getFieldLen())
                    || !FuncBase.equals(oldfield.getFieldPointLen(), newField.getFieldPointLen())
                    || !FuncBase.equals(oldfield.getFieldDefaultVal(), newField.getFieldDefaultVal())
                    || !FuncBase.equals(oldfield.getFieldType(), newField.getFieldType())
                    || !FuncBase.equals(oldfield.getIsPrimaryKey(), newField.getIsPrimaryKey())
                    || !FuncBase.equals(oldfield.getIsNull(), newField.getIsNull())
                    || !FuncBase.equals(oldfield.getIsDb(), newField.getIsDb())) {
                return false;
            }

        }
        //判断旧的存在，新的不存在情况
        Map<String, FormFieldEntity> newMap = new HashMap<>();
        for (FormFieldEntity dbFormFieldEntity : newFieldList) {
            String fieldCode = dbFormFieldEntity.getFieldCode();
            newMap.put(fieldCode, dbFormFieldEntity);
        }
        for (FormFieldEntity oldField : oldFieldList) {
            String fieldCode = oldField.getFieldCode();
            String isDb = oldField.getIsDb();
            if (Func.equals(isDb, YNEnum.N.getCode())) {//不用同步到数据库
                continue;
            }
            FormFieldEntity newfield = newMap.get(fieldCode);
            if (FuncBase.isEmpty(newfield)) {//旧的存在新的不存在
                return false;
            }

        }
        return true;

    }


    public static List<String> getSummaryFieldList(String fieldCode, String summarySql) {
        List<String> selfFieldList = new ArrayList<>();
        if (Func.equals(summarySql, "sum")) {
            selfFieldList.add(String.format("sum(%s) as \"%s\"", fieldCode, fieldCode));
        } else if (Func.equals(summarySql, "avg")) {
            selfFieldList.add(String.format("avg(%s) as \"%s\"", fieldCode, fieldCode));
        } else if (Func.equals(summarySql, "max")) {
            selfFieldList.add(String.format("max(%s) as \"%s\"", fieldCode, fieldCode));
        } else if (Func.equals(summarySql, "min")) {
            selfFieldList.add(String.format("min(%s) as \"%s\"", fieldCode, fieldCode));
        } else {
            selfFieldList.add(String.format("("+summarySql+")" + " as \"%s\"", fieldCode));
        }
        return selfFieldList;
    }

    public static String getFormatExecuteSql(WebFormatConfigModel.SqlModel sqlModel) {
        String valueType = sqlModel.getValueType();
        String sql = "";
        if (Func.equals(valueType, "group")) {
            sql = sqlModel.getGroup();
        } else if (Func.equals(valueType, "custom")) {
            sql = sqlModel.getCustom();
        }
        return sql;
    }

    /**
     * 执行某一个类下的方法，
     *
     * @param classPath  类路径
     * @param methodName 方法名称
     * @param dataMap    参数
     * @return
     */
    public static Object runByClass(String classPath, String methodName, Map<String, Object> dataMap) {
        try {
            // 1. 获取Class对象
            Class<?> clazz = Class.forName(classPath);

            // 2. 创建DemoClass的一个实例
            Object demoClassInstance = clazz.getDeclaredConstructor().newInstance();

            // 3. 获取getBjName方法的对象
            Method method = clazz.getMethod(methodName, Map.class);

            // 4. 调用getBjName方法
            return method.invoke(demoClassInstance, dataMap);
        } catch (Exception e) {
            e.printStackTrace();
            throw new JeeLowCodeException(e.getMessage());
        }

    }

    //计算
    public static String executeJavaExpress(String express, Map<String, Object> dataMap) {
        try {
            String expressTmp = express.replaceAll("\\#\\{([^}]+)\\}", "$1");
            ExpressRunner runner = new ExpressRunner();
            DefaultContext<String, Object> context = new DefaultContext<String, Object>();
            dataMap.forEach((key, val) -> {
                // 处理键值对
                context.put(key, val);
            });
            Object result = runner.execute(expressTmp, context, null, true, false);
            return Func.toStr(result);
        } catch (Exception e) {
            e.printStackTrace();
            throw new JeeLowCodeException(e.getMessage());
        }
    }

    public static Object getfieldValObj(JeeLowCodeFieldTypeEnum fieldTypeEnum, Object fieldVal) {
        Object fieldValObj = fieldVal;
        if (Func.equals(JeeLowCodeFieldTypeEnum.BIGINT, fieldTypeEnum)) {
            fieldValObj = Func.toLong(fieldVal);
        } else {
            fieldValObj = Func.toStr(fieldVal);
        }
        return fieldValObj;
    }

    public static String sendPost(String url, Object body) throws Exception {
        String accept = HttpRequest.post(url)
                .body(Func.json2Str(body))
                .header("Content-Type", "application/json")
                .header("Accept", "application/json")
                .timeout(5000)
                .execute().body();
        if (FuncBase.isEmpty(accept)) {
            Thread.sleep(2000);//休息2秒
        }
        return accept;

    }

    /**
     * 处理虚拟化字段
     *
     * @param selectList
     * @param asList
     */
    public static void handleFieldFormatConfig(List<String> selectList, List<String> asList, List<FormFieldWebEntity> webEntityList) {
        if (isEmpty(webEntityList)) {
            return;
        }

        for (int i = 0; i < webEntityList.size(); i++) {
            FormFieldWebEntity web = webEntityList.get(i);
            String fieldCode = web.getFieldCode();
            String formatConfig = web.getFormatConfig();
            if (Func.isEmpty(formatConfig)) {
                continue;
            }
            WebFormatConfigModel webFormatConfigModel = Func.json2Bean(formatConfig, WebFormatConfigModel.class);
            if (Func.isEmpty(webFormatConfigModel)) {
                continue;
            }
            if (Func.isNotEmpty(webFormatConfigModel.getClass()) && Func.equals(webFormatConfigModel.getFormatType(), "sql")) {//子查询处理
                WebFormatConfigModel.Json formatJson = webFormatConfigModel.getFormatJson();
                WebFormatConfigModel.SqlModel sqlModel = formatJson.getSql();
                String sql = Func.getFormatExecuteSql(sqlModel);
                if (Func.isEmpty(sql)) {
                    continue;
                }

                selectList.add("(" + sql + ")" + " as \"" + fieldCode + "\"");
                asList.add(fieldCode);
            } else if (Func.isNotEmpty(webFormatConfigModel.getClass()) && Func.equals(webFormatConfigModel.getFormatType(), "fun")) {//sql函数处理
                String sql = webFormatConfigModel.getFormatJson().getFun();
                if (Func.isEmpty(sql)) {
                    continue;
                }

                selectList.add(sql + " as \"" + fieldCode + "\"");
                asList.add(fieldCode);
            }
        }
    }

    //复制表属性
    public static <T> List<T> copyDbFormFieldList(List list, Class<T> clazz) {
        List<T> fieldList = BeanUtil.copyToList(list, clazz);
        fieldList.stream().forEach(vo -> {
            DbFormFieldIdVo v = (DbFormFieldIdVo) vo;
            v.setId(null);//剔除id
        });
        return fieldList;
    }
    public static <T> List<T> copyReportFieldList(List list, Class<T> clazz) {
        List<T> fieldList = BeanUtil.copyToList(list, clazz);
        fieldList.stream().forEach(vo -> {
            ReportFieldVo v = (ReportFieldVo) vo;
            v.setId(null);//剔除id
        });
        return fieldList;
    }

    //将datamaplist 转为  ResultSet
    public static Map<String, String> dataMapList2ResultSet(List<Map<String, Object>> dataMapList) {
        // 使用Java 8的Stream API进行转换
        return dataMapList.stream()
                .filter(Objects::nonNull) // 过滤掉null值
                .collect(Collectors.toMap(
                        map -> Func.getMap2Str(map, "fieldCode").toLowerCase(), // 作为键
                        map -> Func.getMap2StrDefault(map, "fieldName", "")  // 作为值
                ));
    }

    public static Map<String, Object> getSaveOrUpdateMap(List<FormFieldEntity> fieldList, Map<String, Object> params) {
        // 使用 Stream API 优化代码
        Map<String, Object> dataMap = fieldList.stream()
                .filter(entity -> params.containsKey(entity.getFieldCode())) // 只处理 params 中存在的字段
                .collect(Collectors.toMap(
                        entity -> entity.getFieldCode(),
                        entity -> {
                            Object o = params.get(entity.getFieldCode());

                            if (Func.isEmpty(o)) {
                                return o;
                            }
                            JeeLowCodeFieldTypeEnum fieldTypeEnum = JeeLowCodeFieldTypeEnum.getByFieldType(entity.getFieldType());
                            switch (fieldTypeEnum) {
                                case DATE:
                                    return DateUtil.parse(Func.toStr(o), "yyyy-MM-dd");
                                case DATETIME:
                                    return DateUtil.parse(Func.toStr(o), "yyyy-MM-dd HH:mm:ss");
                                case TIME:
                                    return DateUtil.parse(Func.toStr(o), "HH:mm:ss");
                                case BLOB:
                                    // 假设 BLOB 处理方式是将字符串转换为字节数组
                                    return Func.toStr(o).getBytes();
                                default:
                                    return o;
                            }

                        }
                ));
        return dataMap;
    }

    /**
     * 参数转换
     * @param obj
     * @param fieldTypeEnum
     * @return
     */
    public static Object paramParse2Object(Object obj, JeeLowCodeFieldTypeEnum fieldTypeEnum) {
        switch (fieldTypeEnum) {
            case BIGINT:
                return Func.toLong(obj);
            case DATE:
                return DateUtil.parse(Func.toStr(obj), "yyyy-MM-dd");
            case DATETIME:
                return DateUtil.parse(Func.toStr(obj), "yyyy-MM-dd HH:mm:ss");
            case TIME:
                return DateUtil.parse(Func.toStr(obj), "HH:mm:ss");
            case BLOB:
                // 假设 BLOB 处理方式是将字符串转换为字节数组
                return Func.toStr(obj).getBytes();
            default:
                return obj;
        }

    }


    public static void handlePlusDataList(List<Map<String, Object>> records, Map<String, JeeLowCodeFieldTypeEnum> fieldTypeEnumMap) {
        try {
            Func.jeelowcodeForkJoinPool().submit(() -> records.parallelStream().forEach(recordMap -> {
                handlePlusDataMap(recordMap, fieldTypeEnumMap);
            })).get();
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e.getMessage());
        }
    }


    public static void handlePlusDataMap(Map<String, Object> recordMap, Map<String, JeeLowCodeFieldTypeEnum> fieldTypeEnumMap) {
        recordMap.forEach((key, value) -> {
            JeeLowCodeFieldTypeEnum fieldTypeEnum = fieldTypeEnumMap.get(key);
            if (value instanceof Clob) {//二进制文件转为string
                convertClob2String(recordMap, key, (Clob) value);
            } else if (value instanceof oracle.sql.TIMESTAMP) {//Oracle特有
                convertTIMESTAMP(recordMap, key, (TIMESTAMP) value, fieldTypeEnum);
            } else if (value instanceof Blob) {
                convertBlob2String(recordMap, key, (Blob) value);
            }
        });
    }

    private static void convertClob2String(Map<String, Object> recordMap, String key, Clob clob) {
        if (Func.isEmpty(clob)) {
            return;
        }
        try (Reader reader = clob.getCharacterStream();
             StringWriter writer = new StringWriter()) {
            int c;
            while ((c = reader.read()) != -1) {
                writer.write(c);
            }
            recordMap.put(key, writer.toString());
        } catch (Exception e) {
            // 处理可能发生的异常，例如IOException
            e.printStackTrace();
        }
    }

    private static void convertBlob2String(Map<String, Object> recordMap, String key, Blob blob) {
        if (Func.isEmpty(blob)) {
            return;
        }

        try (InputStream inputStream = blob.getBinaryStream();
             InputStreamReader reader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
             StringWriter writer = new StringWriter()) {

            int c;
            while ((c = reader.read()) != -1) {
                writer.write(c);
            }
            recordMap.put(key, writer.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    //oracle
    private static void convertTIMESTAMP(Map<String, Object> recordMap, String key, TIMESTAMP val, JeeLowCodeFieldTypeEnum fieldTypeEnum) {
        if (Func.isEmpty(val)) {
            return;
        }
        try {
            switch (fieldTypeEnum) {
                case DATE:
                    recordMap.put(key, val.dateValue());
                    break;
                case TIME:
                    recordMap.put(key, val.timeValue());
                    break;
                case DATETIME:
                    recordMap.put(key, val.toLocalDateTime());
            }
        } catch (Exception e) {
            // 处理可能发生的异常，例如IOException
            e.printStackTrace();
        }
    }

    public static String getButtonName(String buttonCode){
        String buttonName="";
        switch (buttonCode){
            case "addBtn":
                buttonName="新增";
                break;
            case "editBtn":
                buttonName="编辑";
                break;
            case "viewBtn":
                buttonName="查看";
                break;
            case "delBtn":
                buttonName="删除";
                break;
            case "batchDelBtn":
                buttonName="批量删除";
                break;
            case "importBtn":
                buttonName="导入";
                break;
            case "exportBtn":
                buttonName="导出";
                break;
        }
        return buttonName;
    }

    //替换参数
    public static String replaceParam(String executeSql, Map<String,Object> paramMap, String autoWhereSql, IJeeLowCodeAdapter jeeLowCodeAdapter){

        //处理默认值
        if(SqlParamEnumIsExist(executeSql, SqlParamEnum.JEELOWCODE_ID)){
            paramMap.put(SqlParamEnum.JEELOWCODE_ID.getCode(),IdWorker.getId());
        }
        if(SqlParamEnumIsExist(executeSql,SqlParamEnum.JEELOWCODE_DATE_TIME)){
            paramMap.put(SqlParamEnum.JEELOWCODE_DATE_TIME.getCode(), LocalDateTime.now().withNano(0));
        }
        if(SqlParamEnumIsExist(executeSql,SqlParamEnum.JEELOWCODE_DATE)){
            paramMap.put(SqlParamEnum.JEELOWCODE_DATE.getCode(), LocalDate.now());
        }
        if(SqlParamEnumIsExist(executeSql,SqlParamEnum.JEELOWCODE_TIME)){
            paramMap.put(SqlParamEnum.JEELOWCODE_TIME.getCode(), LocalTime.now().withNano(0));
        }
        if(SqlParamEnumIsExist(executeSql,SqlParamEnum.JEELOWCODE_IS_DELETED)){
            paramMap.put(SqlParamEnum.JEELOWCODE_IS_DELETED.getCode(),0);
        }
        if(SqlParamEnumIsExist(executeSql,SqlParamEnum.JEELOWCODE_TENANT_ID)){
            Long tenantId = jeeLowCodeAdapter.getTenantId();
            paramMap.put(SqlParamEnum.JEELOWCODE_TENANT_ID.getCode(),tenantId);
        }
        if(SqlParamEnumIsExist(executeSql,SqlParamEnum.JEELOWCODE_USER_ID)){
            Long onlineUserId = jeeLowCodeAdapter.getOnlineUserId();
            paramMap.put(SqlParamEnum.JEELOWCODE_USER_ID.getCode(),onlineUserId);
        }
        if(SqlParamEnumIsExist(executeSql,SqlParamEnum.JEELOWCODE_USER_NAME)){
            String onlineUserName = jeeLowCodeAdapter.getOnlineUserName();
            paramMap.put(SqlParamEnum.JEELOWCODE_USER_NAME.getCode(),onlineUserName);
        }
        if(SqlParamEnumIsExist(executeSql,SqlParamEnum.JEELOWCODE_USER_NICKNAME)){
            String onlineUserNickName = jeeLowCodeAdapter.getOnlineUserNickName();
            paramMap.put(SqlParamEnum.JEELOWCODE_USER_NICKNAME.getCode(),onlineUserNickName);
        }
        if(SqlParamEnumIsExist(executeSql,SqlParamEnum.JEELOWCODE_USER_DEPT)){
            Long onlineUserDeptId = jeeLowCodeAdapter.getOnlineUserDeptId();
            paramMap.put(SqlParamEnum.JEELOWCODE_USER_DEPT.getCode(),onlineUserDeptId);
        }
        if(SqlParamEnumIsExist(executeSql,SqlParamEnum.JEELOWCODE_USER_ALL_DEPT)){
            JeeLowCodeDeptParam param=new JeeLowCodeDeptParam();
            param.setType("sub");
            List<JeeLowCodeDept> deptList = jeeLowCodeAdapter.getDeptList(param);
            String allDeptIds = deptList.stream()
                    .map(JeeLowCodeDept::getDeptId)
                    .collect(Collectors.joining(","));
            paramMap.put(SqlParamEnum.JEELOWCODE_USER_ALL_DEPT.getCode(),allDeptIds);//当前登录人所有部
        }
        if(SqlParamEnumIsExist(executeSql,SqlParamEnum.JEELOWCODE_MAIN_ID)){
            Long mainId = JeeLowCodeUtils.getMap2Long(paramMap, "id");
            paramMap.put(SqlParamEnum.JEELOWCODE_MAIN_ID.getCode(),mainId);
        }
        if(SqlParamEnumIsExist(executeSql,SqlParamEnum.JEELOWCODE_AUTO_WHERE)){
            executeSql=replacePlaceholder(executeSql,SqlParamEnum.JEELOWCODE_AUTO_WHERE, autoWhereSql);//动态where
        }

        return formatSql2EW(executeSql);
    }

    public static String formatSql2EW(String sql){
        String replacedSql = sql.replaceAll("(?<!#\\{ew\\.)\\#\\{(\\w+)}", "#{ew.$1}");
        return replacedSql;
    }

    public static String fomatEnum(SqlParamEnum sqlParamEnum){
        String FORMAT="\\#\\{%s}";
        return String.format(FORMAT, sqlParamEnum.getCode());
    }

    public static String fomatSqlStr(String str){
        String FORMAT="\"%s\"";
        return String.format(FORMAT, str);
    }

    public static boolean SqlParamEnumIsExist(String executeSql,SqlParamEnum sqlParamEnum){
        String FORMAT="#{%s}";
        return executeSql.contains(String.format(FORMAT,sqlParamEnum.getCode()));
    }
    //替换占位符
    public static String replacePlaceholder(String executeSql,SqlParamEnum sqlParamEnum,String value){
        if (FuncBase.isEmpty(value)) {
            return executeSql.replaceAll("#\\{"+SqlParamEnum.JEELOWCODE_AUTO_WHERE.getCode()+"}", "(1=1)");
        }
        return  executeSql.replaceAll(fomatEnum(sqlParamEnum), value);//动态where
    }

    /**
     * 复制spring类型增强
     *
     * @param type 表类型 参考 TableType2Enum
     * @param oldEnhanceName 旧增强
     * @param newTableName 新表名
     */
    public static String copySpringEnhance(String type, String oldEnhanceName, String newTableName){
        // 获取当前环境配置，只在本地开发环境进行复制
        String activeProfile = SpringUtils.getActiveProfile();
        if (Func.isNotEmpty(activeProfile) && !Func.equals(activeProfile, JeeLowCodeConstant.APPLICATION_LOCAL)) {
            return null;
        }
        // 获取原增强
        Object bean = null;
        try {
            bean = SpringUtils.getBean(oldEnhanceName);
        } catch (NoSuchBeanDefinitionException ignored) {}
        if (bean == null) {
            return null;
        }
        URL url = bean.getClass().getProtectionDomain().getCodeSource().getLocation();
        URI uri = null;
        try {
            uri = url.toURI();
        } catch (URISyntaxException ignored) {}
        if (uri == null) {
            return null;
        }
        String oldClassesPath = new File(uri).getPath();
        String oldClazzName = bean.getClass().getName();
        CopyEnhanceParam copyEnhanceParam = copyEnhanceFile(type, oldClassesPath, oldClazzName, newTableName);
        if (FuncBase.isEmpty(copyEnhanceParam)) {
            return null;
        }
        return copyEnhanceParam.getBeanName();
    }

    /**
     * 复制class类型增强
     */
    public static String copyClassEnhance(String type, String oldClazzName, String newTableName) {
        // 获取当前环境配置，只在本地开发环境进行复制
        String activeProfile = SpringUtils.getActiveProfile();
        if (Func.isNotEmpty(activeProfile) && !Func.equals(activeProfile, JeeLowCodeConstant.APPLICATION_LOCAL)) {
            return null;
        }
        Class<?> aClass = null;
        try {
            aClass = Class.forName(oldClazzName);
        } catch (ClassNotFoundException ignored) {}
        if (aClass == null) {
            return null;
        }
        CodeSource codeSource = aClass.getProtectionDomain().getCodeSource();
        URI uri = null;
        try {
            uri = codeSource.getLocation().toURI();
        } catch (URISyntaxException ignored) {}
        if (uri == null) {
            return null;
        }
        String oldClassesPath = new File(uri).getPath();
        CopyEnhanceParam copyEnhanceParam = copyEnhanceFile(type, oldClassesPath, oldClazzName, newTableName);
        if (FuncBase.isEmpty(copyEnhanceParam)) {
            return null;
        }
        return copyEnhanceParam.getReference();
    }

    /**
     * 复制文件
     */
    private static CopyEnhanceParam copyEnhanceFile(String type, String oldClassesPath, String oldClazzName, String newTableName) {
        // 要复制的增强的文件绝对地址
        String oldFilePath = new StringBuilder().append(StringUtils.substringBefore(oldClassesPath, JeeLowCodeConstant.CLASSES_DIR))
                .append(JeeLowCodeConstant.PROGRAM_DIR)
                .append(oldClazzName.replace(JeeLowCodeConstant.DOT, JeeLowCodeConstant.BACK_SLASH))
                .append(JeeLowCodeConstant.JAVA_SUFFIX).toString();
        // 要复制的增强的类名
        String oldClassName = StringUtils.substringAfterLast(oldClazzName, JeeLowCodeConstant.DOT);
        // 读取原增强
        List<String> readList = null;
        try {
            readList = Files.readAllLines(Paths.get(oldFilePath));
        } catch (IOException ignored) {}
        if (readList == null) {
            return null;
        }
        // 生成新增强java文件
        // 处理新增强bean名称、类名、绝对地址等
        String humpTable = StrUtil.toCamelCase(newTableName);
        String lowerCaseTable = humpTable.toLowerCase();
        // 新增强文件绝对地址
        String baseNewEnhancePath = new StringBuilder().append(JeeLowCodeProperties.getEnhancePath())
                .append(JeeLowCodeConstant.BACK_SLASH)
                .append(type).toString();
        String newFilePathStr = new StringBuilder().append(JeeLowCodeConstant.USER_DIR)
                .append(JeeLowCodeConstant.BACK_SLASH)
                .append(baseNewEnhancePath)
                .append(JeeLowCodeConstant.BACK_SLASH)
                .append(lowerCaseTable)
                .append(JeeLowCodeConstant.BACK_SLASH)
                .append(humpTable)
                .append(StringUtils.substringAfterLast(oldFilePath, JeeLowCodeConstant.BACK_SLASH)).toString();
        Path newFilePath = Paths.get(newFilePathStr);
        // 判断文件名是否已经存在，存在则累加1
        int counter = 1;
        String basePath = StringUtils.substringBeforeLast(newFilePathStr, JeeLowCodeConstant.DOT);
        while (Files.exists(newFilePath)) {
            newFilePathStr = new StringBuilder().append(basePath).append(counter).append(JeeLowCodeConstant.JAVA_SUFFIX).toString();
            newFilePath = Paths.get(newFilePathStr);
            counter++;
        }
        // 新增强类名
        String newClassName = StringUtils.substringBeforeLast(StringUtils.substringAfterLast(newFilePathStr, JeeLowCodeConstant.BACK_SLASH), JeeLowCodeConstant.DOT);
        // 新增强bean名称
        String newEnhanceName = StrUtil.lowerFirst(newClassName);
        // 替换指定内容
        String newPackageReference = new StringBuilder().append(StringUtils.substringAfter(baseNewEnhancePath, JeeLowCodeConstant.PROGRAM_DIR))
                .append(JeeLowCodeConstant.BACK_SLASH)
                .append(lowerCaseTable)
                .toString().replace(JeeLowCodeConstant.BACK_SLASH, JeeLowCodeConstant.DOT);
        String pattern1 = "(package)(" + StringUtils.substringBeforeLast(oldClazzName, JeeLowCodeConstant.DOT) + ")(;)";
        String pattern2 = "(\\s*@Component\\s*\\()((\\s*value\\s*=\\s*)|(\\s*))(\")(.+?)(\"\\s*\\))";
        String pattern3 = "(\\s*public\\s+class\\s+)" + oldClassName + "(\\s+implements)";
        String replace1 = "$1" + JeeLowCodeConstant.SPACE + newPackageReference + "$3";
        String replace2 = "$1$2$5" + newEnhanceName + "$7";
        String replace3 = "$1" + newClassName + "$2";
        AtomicBoolean flag1 = new AtomicBoolean(false);
        AtomicBoolean flag3 = new AtomicBoolean(false);
        List<String> writeList = readList.stream().map(item -> {
            String trim = item.replaceAll(JeeLowCodeConstant.SPACE, JeeLowCodeConstant.EMPTY);
            if (Pattern.compile(pattern1).matcher(trim).find()) {
                flag1.set(true);
                return Pattern.compile(pattern1).matcher(trim).replaceAll(replace1);
            }
            else if (Pattern.compile(pattern2).matcher(item).find()) {
                return Pattern.compile(pattern2).matcher(item).replaceAll(replace2);
            }
            else if (Pattern.compile(pattern3).matcher(item).find()) {
                flag3.set(true);
                return Pattern.compile(pattern3).matcher(item).replaceAll(replace3);
            }
            return item;
        }).collect(Collectors.toList());
        if (flag1.get() && flag3.get()) {
            try {
                // 创建文件
                Files.createDirectories(Paths.get(StringUtils.substringBeforeLast(newFilePathStr, JeeLowCodeConstant.BACK_SLASH)));
                Files.createFile(newFilePath);
                Files.write(newFilePath, writeList);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }else {
            // 未找到替换目标
            return null;
        }
        CopyEnhanceParam copyEnhanceParam = new CopyEnhanceParam();
        copyEnhanceParam.setBeanName(newEnhanceName);
        copyEnhanceParam.setReference(StringUtils.substringBeforeLast(StringUtils.substringAfter(newFilePathStr, JeeLowCodeConstant.PROGRAM_DIR), JeeLowCodeConstant.JAVA_SUFFIX)
                .replace(JeeLowCodeConstant.BACK_SLASH, JeeLowCodeConstant.DOT));
        return copyEnhanceParam;
    }

    /**
     * 自定义ForkJoinPool
     */
    public static ForkJoinPool jeelowcodeForkJoinPool(){
       return jeelowcodeForkJoinPool(20);
    }

    /**
     * 自定义ForkJoinPool
     *
     * @param poolSize
     */
    public static ForkJoinPool jeelowcodeForkJoinPool(Integer poolSize){
        ForkJoinPool.ForkJoinWorkerThreadFactory factory = pool -> {
            ForkJoinWorkerThread thread = ForkJoinPool.defaultForkJoinWorkerThreadFactory.newThread(pool);
            thread.setName("jeelowcodeForkJoinPool-worker-"+IdWorker.getIdStr());
            return thread;
        };
        return new ForkJoinPool(poolSize, factory, null, false);
    }
}

