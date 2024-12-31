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
package com.jeelowcode.framework.plus.component;


import com.jeelowcode.framework.exception.JeeLowCodeException;
import com.jeelowcode.framework.plus.SqlHelper;
import com.jeelowcode.framework.plus.entity.DbColunmTypesEntity;
import com.jeelowcode.framework.plus.entity.FieldModel;
import com.jeelowcode.framework.plus.utils.PlusUtils;
import com.jeelowcode.framework.utils.enums.JeeLowCodeFieldTypeEnum;
import com.jeelowcode.framework.utils.enums.YNEnum;
import com.jeelowcode.framework.utils.utils.FuncBase;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.DependsOn;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import java.util.stream.Collectors;

@Component
@Order(2) // 指定DbManager的初始化顺序，确保它在SqlSessionFactory之后初始化
@DependsOn("sqlSessionFactory") // 指定DbManager依赖于SqlSessionFactory
public class DbManager {

    @Autowired
    private SqlSessionFactory sqlSessionFactory;

    @Bean
    public void initDbManager(){
        //初始化type
        initDbType();
        //初始化schema
        initDbSchema();
    }


    /**
     * 获取数据库的元数据
     * @param primaryKeyResultSet
     * @param set
     * @return
     */
    public Map<String, FieldModel> formatMetaData(ResultSet primaryKeyResultSet, ResultSet set,Map<String,String> remarkMap) {

        Map<String,FieldModel> resultMap=new LinkedHashMap<>();
        try{
            //获取主键
            Set<String> primaryKeySet=new HashSet<>();
            while (primaryKeyResultSet.next()) {
                String columnName = primaryKeyResultSet.getString("COLUMN_NAME");
                if(FuncBase.isEmpty(columnName)){
                    continue;
                }
                primaryKeySet.add(columnName.toLowerCase());
            }

            DbColunmTypesEntity dbColunmTypesEntity = SqlHelper.getDbColunmTypesEntity();

            while (set.next()) {
                FieldModel fieldModel=new FieldModel();

                String fieldCode = set.getString("COLUMN_NAME").toLowerCase();
                fieldModel.setFieldCode(fieldCode);//字段

                if(FuncBase.isNotEmpty(remarkMap)){
                    fieldModel.setFieldName(remarkMap.get(fieldCode));//字段名称
                }else{
                    String fieldName = set.getString("REMARKS");
                    fieldModel.setFieldName(fieldName);//字段名称
                }


                Integer fieldLen = set.getInt("COLUMN_SIZE");//大小
                fieldModel.setFieldLen(fieldLen);//长度

                Integer fieldPointLen = set.getInt("DECIMAL_DIGITS");//小数
                fieldModel.setFieldPointLen(FuncBase.isEmpty(fieldPointLen)?0:fieldPointLen);

                String fieldDefaultVal = set.getString("COLUMN_DEF");
                fieldModel.setFieldDefaultVal(fieldDefaultVal);//字段默认值

                String dbType = set.getString("TYPE_NAME");
                List<DbColunmTypesEntity.TypeEntity> fieldTypeList = PlusUtils.dbType2JeelowCodeTypeList(dbType, dbColunmTypesEntity);

                List<JeeLowCodeFieldTypeEnum> fieldTypeEnumList=new ArrayList<>();
                for(DbColunmTypesEntity.TypeEntity entityType:fieldTypeList){
                    JeeLowCodeFieldTypeEnum fieldType = JeeLowCodeFieldTypeEnum.getByFieldType(entityType.getJeeLowCodeType());
                    fieldTypeEnumList.add(fieldType);
                }

                fieldModel.setJavaFieldTypeList(fieldTypeEnumList);

                fieldModel.setMaxFieldLen(fieldTypeList.get(0).getDbMaxLen());//数据库类型一样，长度也是一样
                fieldModel.setMaxPointLen(fieldTypeList.get(0).getDbMaxPointLen());//小数位

                fieldModel.setIsPrimaryKey(primaryKeySet.contains(fieldCode)? YNEnum.Y.getCode():YNEnum.N.getCode());//是否是主键

                String isNull = set.getInt("NULLABLE") == 1 ? YNEnum.Y.getCode() : YNEnum.N.getCode();//是
                fieldModel.setIsNull(isNull);

                resultMap.put(fieldCode,fieldModel);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return resultMap;
    }

    /**
     * 获取现有数据库的元数据
     * @param tableName
     * @return
     */
    public Map<String, FieldModel> getDbColumnMetaMap(String tableName,Map<String,String> remarkMap) {
        DbColunmTypesEntity dbColunmTypesEntity = SqlHelper.getDbColunmTypesEntity();
        Boolean upperFlag = dbColunmTypesEntity.getUpperFlag();
        if(FuncBase.isNotEmpty(upperFlag) && upperFlag){
            tableName=tableName.toUpperCase();
        }
        try (SqlSession sqlSession = sqlSessionFactory.openSession();
             Connection connection = sqlSession.getConnection()) {
            DatabaseMetaData metaData = connection.getMetaData();
            //获取主键
            ResultSet primaryKeyResultSet = metaData.getPrimaryKeys(connection.getCatalog(), connection.getSchema(), tableName);
            ResultSet set = metaData.getColumns(connection.getCatalog(), connection.getSchema(), tableName, "%");
            Map<String, FieldModel> metaDataMap = formatMetaData(primaryKeyResultSet, set,remarkMap);
            return metaDataMap;
        } catch (SQLException e) {
            throw new JeeLowCodeException(e.getMessage());
        }
    }


    /**
     * 获取新增列
     * @param tableName
     * @param fieldModelList
     * @param columnMetaMap
     * @return
     */
    public List<String> getAddColumnDdl(String tableName, List<FieldModel> fieldModelList, Map<String, FieldModel> columnMetaMap) {
        List<String> ddlList = new ArrayList<>();
        for (FieldModel fieldModel : fieldModelList) {
            String fieldCode = fieldModel.getFieldCode();
            if (columnMetaMap.containsKey(fieldCode)) {//已存在，不走新增
                continue;
            }
            List<String> tmpDdlList = SqlHelper.addColumn(tableName, fieldModel);
            ddlList.addAll(tmpDdlList);
        }

        return ddlList;
    }


    /**
     * 获取修改ddl列表
     * @param tableName
     * @param fieldModelList
     * @param columnMetaMap
     * @return
     */
    public List<String> getUpdateColumnDdl(String tableName, List<FieldModel> fieldModelList, Map<String, FieldModel> columnMetaMap) {
        List<String> ddlList = new ArrayList<>();
        for (FieldModel fieldModel : fieldModelList) {
            String fieldCode = fieldModel.getFieldCode();
            Integer fieldLen = fieldModel.getFieldLen();
            Integer fieldPointLen = fieldModel.getFieldPointLen();
            if (!columnMetaMap.containsKey(fieldCode)) {//不存在
                continue;
            }
            //数据库的
            FieldModel dbFieldModel = columnMetaMap.get(fieldCode);
            Integer maxFieldLen = dbFieldModel.getMaxFieldLen();//有最大长度
            Integer maxPointLen = dbFieldModel.getMaxPointLen();//小数位

            boolean lenFlag=false;//整型位长度
            boolean pointLenFlag=false;//小数位长度
            if(FuncBase.isNotEmpty(maxFieldLen) &&  maxFieldLen>0){
                if(fieldLen>maxFieldLen){//保证和当前输入的一样长度
                    dbFieldModel.setFieldLen(fieldLen);
                }
                if(FuncBase.equals(dbFieldModel.getFieldPointLen(), fieldLen)){
                    lenFlag=true;
                }
                //校验小数位长度
                if(FuncBase.isNotEmpty(maxPointLen)){//需要对长度进行校验
                    if(fieldPointLen>maxPointLen){//保证和当前输入的一样长度，下面则是true
                        dbFieldModel.setMaxPointLen(maxPointLen);
                    }
                    if(FuncBase.equals(dbFieldModel.getFieldPointLen(), fieldPointLen)){
                        pointLenFlag=true;
                    }
                }else{
                    pointLenFlag=true;
                }
            }else{//0说明不要对长度进行校验，设置和输入的一样就可以了
                lenFlag=true;//不用对长度进行校验
                pointLenFlag=true;
            }

            fieldModel.setDbNowIsNull(dbFieldModel.getIsNull());//当前状态的

            boolean typeFlag=false;
            List<JeeLowCodeFieldTypeEnum> fieldTypeList = dbFieldModel.getJavaFieldTypeList();
            for(JeeLowCodeFieldTypeEnum fieldTypeEnum:fieldTypeList){
                if(FuncBase.equals(fieldTypeEnum, fieldModel.getFieldType())){
                    typeFlag=true;//一样
                    break;
                }
            }


            //判断是否一致
            if (!FuncBase.equals(dbFieldModel.getFieldName(), fieldModel.getFieldName()) ||
                    !lenFlag ||
                    !pointLenFlag ||
                    !FuncBase.equals(dbFieldModel.getFieldName(), fieldModel.getFieldName()) ||
                    !typeFlag ||
                    !FuncBase.equals(dbFieldModel.getIsPrimaryKey(), fieldModel.getIsPrimaryKey()) ||
                    !FuncBase.equals(dbFieldModel.getIsNull(), fieldModel.getIsNull())) {
                List<String> tmpDdlList = SqlHelper.modifyColumnSql(tableName, fieldModel);
                ddlList.addAll(tmpDdlList);
            }

        }

        return ddlList;
    }

    /**
     * 获取删除ddl列表
     * @param tableName
     * @param fieldModelList
     * @param columnMetaMap
     * @return
     */
    public List<String> getDropColumnDdl(String tableName,  List<FieldModel> fieldModelList, Map<String, FieldModel> columnMetaMap) {



        Set<String> fieldCodeSet = fieldModelList.stream()
                .map(FieldModel::getFieldCode)
                .collect(Collectors.toCollection(LinkedHashSet::new));


        List<String> ddlList = columnMetaMap.entrySet().stream()
                .filter(entry -> !fieldCodeSet.contains(entry.getKey()))
                .map(entry ->SqlHelper.getDropColumnSql(tableName,entry.getKey()))
                .collect(Collectors.toList());

        return ddlList;
    }



    private void initDbType(){
        String dbType="mysql";
        try (SqlSession sqlSession = sqlSessionFactory.openSession();
             Connection connection = sqlSession.getConnection()) {
            DatabaseMetaData metaData = connection.getMetaData();
            String databaseProductName = metaData.getDatabaseProductName();
            dbType = databaseProductName.toLowerCase();
        }catch (Exception e){
            throw new JeeLowCodeException("获取数据库类型失败");
        }
        SqlHelper.setDbType(dbType);
    }

    private void initDbSchema(){
        try (SqlSession sqlSession = sqlSessionFactory.openSession();
             Connection connection = sqlSession.getConnection()) {
            String dbSchemaName = connection.getCatalog();
            if(FuncBase.isEmpty(dbSchemaName)){
                dbSchemaName = connection.getSchema();
            }
            if(FuncBase.isEmpty(dbSchemaName)){
                throw new JeeLowCodeException("获取数据库 schema 失败");
            }
            SqlHelper.setDbSchemaName(dbSchemaName);
        }catch (Exception e){
            throw new JeeLowCodeException("获取数据库 schema 失败");
        }
    }
}
