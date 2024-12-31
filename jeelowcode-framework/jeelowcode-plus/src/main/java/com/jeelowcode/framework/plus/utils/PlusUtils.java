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
package com.jeelowcode.framework.plus.utils;

import com.jeelowcode.framework.exception.JeeLowCodeException;
import com.jeelowcode.framework.plus.entity.DbColunmTypesEntity;
import com.jeelowcode.framework.plus.core.model.SqlFormatModel;
import com.jeelowcode.framework.utils.enums.JeeLowCodeFieldTypeEnum;
import com.jeelowcode.framework.utils.enums.YNEnum;
import com.jeelowcode.framework.utils.utils.FuncBase;

import java.util.*;
import java.util.stream.Collectors;

public class PlusUtils {

    private static String NOT_NULL = "NOT NULL";//不允许为空
    private static String NULL = "NULL";//允许为空
    private static String DEFAULT = "DEFAULT";//默认值


    /**
     * 通过jeelowcode 类型来获取数据库类型
     * @param fieldTypeEnum
     * @param dbColunmTypes
     * @return
     */
    public static DbColunmTypesEntity.TypeEntity jeelowCodeType2DbType(JeeLowCodeFieldTypeEnum fieldTypeEnum, DbColunmTypesEntity dbColunmTypes){
        Map<JeeLowCodeFieldTypeEnum, DbColunmTypesEntity.TypeEntity> typeMap = new HashMap<>();
        typeMap.put(JeeLowCodeFieldTypeEnum.STRING, dbColunmTypes.getJeeLowCodeString());
        typeMap.put(JeeLowCodeFieldTypeEnum.INTEGER, dbColunmTypes.getJeeLowCodeInteger());
        typeMap.put(JeeLowCodeFieldTypeEnum.DATE, dbColunmTypes.getJeeLowCodeDate());
        typeMap.put(JeeLowCodeFieldTypeEnum.DATETIME, dbColunmTypes.getJeeLowCodeDateTime());
        typeMap.put(JeeLowCodeFieldTypeEnum.TIME, dbColunmTypes.getJeeLowCodeTime());
        typeMap.put(JeeLowCodeFieldTypeEnum.BIGINT, dbColunmTypes.getJeeLowCodeBigInt());
        typeMap.put(JeeLowCodeFieldTypeEnum.BIGDECIMAL, dbColunmTypes.getJeeLowCodeBigDecimal());
        typeMap.put(JeeLowCodeFieldTypeEnum.TEXT, dbColunmTypes.getJeeLowCodeText());
        typeMap.put(JeeLowCodeFieldTypeEnum.LONGTEXT, dbColunmTypes.getJeeLowCodeLongText());
        typeMap.put(JeeLowCodeFieldTypeEnum.BLOB, dbColunmTypes.getJeeLowCodeBlob());
        DbColunmTypesEntity.TypeEntity dbType = typeMap.get(fieldTypeEnum);
        if(FuncBase.isEmpty(dbType)){
            throw new JeeLowCodeException("类型有误");
        }
        return dbType;
    }

    /**
     * 通过数据库 类型来获取 jeelowcode 类型
     * @param dbType 数据库类型 例如oracle对应的是number
     * @param dbColunmTypes
     * @return
     */
    public static List<DbColunmTypesEntity.TypeEntity> dbType2JeelowCodeTypeList(String dbType, DbColunmTypesEntity dbColunmTypes){
        dbType=dbType.toLowerCase();
        if(dbType.contains("(")){
            dbType =dbType.split("\\(")[0];
        }

        List<DbColunmTypesEntity.TypeEntity> resultList=new ArrayList<>();
        if(FuncBase.equals(dbType,dbColunmTypes.getJeeLowCodeString().getDbType())){
            resultList.add(dbColunmTypes.getJeeLowCodeString());
        }
        if(FuncBase.equals(dbType,dbColunmTypes.getJeeLowCodeInteger().getDbType())){
            resultList.add(dbColunmTypes.getJeeLowCodeInteger());
        }
        if(FuncBase.equals(dbType,dbColunmTypes.getJeeLowCodeDate().getDbType())){
            resultList.add(dbColunmTypes.getJeeLowCodeDate());
        }
        if(FuncBase.equals(dbType,dbColunmTypes.getJeeLowCodeDateTime().getDbType())){
            resultList.add(dbColunmTypes.getJeeLowCodeDateTime());
        }
        if(FuncBase.equals(dbType,dbColunmTypes.getJeeLowCodeTime().getDbType())){
            resultList.add(dbColunmTypes.getJeeLowCodeTime());
        }
        if(FuncBase.equals(dbType,dbColunmTypes.getJeeLowCodeBigInt().getDbType())){
            resultList.add(dbColunmTypes.getJeeLowCodeBigInt());
        }
        if(FuncBase.equals(dbType,dbColunmTypes.getJeeLowCodeBigDecimal().getDbType())){
            resultList.add(dbColunmTypes.getJeeLowCodeBigDecimal());
        }
        if(FuncBase.equals(dbType,dbColunmTypes.getJeeLowCodeText().getDbType())){
            resultList.add(dbColunmTypes.getJeeLowCodeText());
        }
        if(FuncBase.equals(dbType,dbColunmTypes.getJeeLowCodeLongText().getDbType())){
            resultList.add(dbColunmTypes.getJeeLowCodeLongText());
        }
        if(FuncBase.equals(dbType,dbColunmTypes.getJeeLowCodeBlob().getDbType())){
            resultList.add(dbColunmTypes.getJeeLowCodeBlob());
        }
        //默认字符串
        if(FuncBase.isEmpty(resultList)){
            resultList.add(dbColunmTypes.getJeeLowCodeString());
        }

        return resultList;
    }


    //获取类型
    public static String getDbType(JeeLowCodeFieldTypeEnum jeeLowCodeFieldTypeEnum, String dbType, Integer len, Integer pointLen,Integer fieldMaxLen) {

        len = FuncBase.isEmpty(len) ? 0 : len;//长度
        pointLen = FuncBase.isEmpty(pointLen) ? 0 : pointLen;//小数

        if(FuncBase.isNotEmpty(fieldMaxLen) && fieldMaxLen>0){
            if(len>fieldMaxLen){
                len=fieldMaxLen;
            }
        }

        if (FuncBase.equals(JeeLowCodeFieldTypeEnum.STRING, jeeLowCodeFieldTypeEnum)) {
            return String.format(" %s (%s)", dbType, len);
        } else if (FuncBase.equals(JeeLowCodeFieldTypeEnum.INTEGER, jeeLowCodeFieldTypeEnum)) {
            return len == 0 ? String.format(" %s ", dbType) : String.format(" %s (%s)", dbType, len);
        } else if (FuncBase.equals(JeeLowCodeFieldTypeEnum.BIGINT, jeeLowCodeFieldTypeEnum)) {
            return len == 0 ? String.format(" %s ", dbType) : String.format(" %s (%s)", dbType, len);
        } else if (FuncBase.equals(JeeLowCodeFieldTypeEnum.BIGDECIMAL, jeeLowCodeFieldTypeEnum)) {
            return String.format(" %s (%s, %s)", dbType, len, pointLen);
        } else{
            return String.format(" %s ", dbType);
        }

    }

    public static String getDefaultValSql(String defaultVal, String isNull,String nowIsNull) {
        String nullStr=NULL;
        String notNullStr=NOT_NULL;
        if(FuncBase.isNotEmpty(nowIsNull) && FuncBase.equals(isNull,nowIsNull)){//如果一样，则不用操作，oracle特殊
            nullStr="";
            notNullStr="";
        }
        if (FuncBase.equals(isNull, YNEnum.Y.getCode())) {//允许为空
            if (FuncBase.isNotEmpty(defaultVal)) {
                String formatStr = DEFAULT + " '%s' ";
                return String.format(formatStr, defaultVal);
            } else {
                return nullStr;
            }
        } else {//不为空
            if (FuncBase.isNotEmpty(defaultVal)) {
                String formatStr = DEFAULT + " '%s'";
                return NOT_NULL + " " + String.format(formatStr, defaultVal);
            } else {
                return notNullStr;
            }
        }

    }

    public static List<SqlFormatModel> ddl2SqlFormatModel(List<String> ddlList){
        return ddlList.stream()
                .map(ddl -> new SqlFormatModel(ddl))
                .collect(Collectors.toList());
    }

    public static List<SqlFormatModel> ddl2SqlFormatModel(String ddl){
        return Collections.singletonList(new SqlFormatModel(ddl));
    }
}
