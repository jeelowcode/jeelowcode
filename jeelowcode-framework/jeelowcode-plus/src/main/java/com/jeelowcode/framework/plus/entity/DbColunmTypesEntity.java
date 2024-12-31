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
package com.jeelowcode.framework.plus.entity;


public class DbColunmTypesEntity {

    private Boolean upperFlag;//是否转大写
    private String symbol;//标识符 例如mysql->`  oracle ->"
    private TypeEntity jeeLowCodeString;//字符串
    private TypeEntity jeeLowCodeInteger;//整数
    private TypeEntity jeeLowCodeDate;//日期
    private TypeEntity jeeLowCodeDateTime;//日期时间
    private TypeEntity jeeLowCodeTime;//时间
    private TypeEntity jeeLowCodeBigInt;//大整数
    private TypeEntity jeeLowCodeBigDecimal;//小数
    private TypeEntity jeeLowCodeText;//文本
    private TypeEntity jeeLowCodeLongText;//大文本
    private TypeEntity jeeLowCodeBlob;//二进制



    public static class TypeEntity {
        private String jeeLowCodeType;
        private String dbType;//类型
        private Integer dbMaxLen;//整数位数
        private Integer dbMaxPointLen;//小数位

        public TypeEntity(String jeeLowCodeType, String dbType, Integer dbMaxLen) {
            this.jeeLowCodeType = jeeLowCodeType;
            this.dbType = dbType;
            this.dbMaxLen = dbMaxLen;
        }
        public TypeEntity(String jeeLowCodeType, String dbType, Integer dbMaxLen,Integer dbMaxPointLen) {
            this.jeeLowCodeType = jeeLowCodeType;
            this.dbType = dbType;
            this.dbMaxLen = dbMaxLen;
            this.dbMaxPointLen = dbMaxPointLen;
        }

        public String getJeeLowCodeType() {
            return jeeLowCodeType;
        }

        public String getDbType() {
            return dbType;
        }

        public Integer getDbMaxLen() {
            return dbMaxLen;
        }

        public Integer getDbMaxPointLen() {
            return dbMaxPointLen;
        }
    }

    public TypeEntity getJeeLowCodeString() {
        return jeeLowCodeString;
    }

    public TypeEntity getJeeLowCodeInteger() {
        return jeeLowCodeInteger;
    }

    public TypeEntity getJeeLowCodeDate() {
        return jeeLowCodeDate;
    }

    public TypeEntity getJeeLowCodeDateTime() {
        return jeeLowCodeDateTime;
    }

    public TypeEntity getJeeLowCodeTime() {
        return jeeLowCodeTime;
    }

    public TypeEntity getJeeLowCodeBigInt() {
        return jeeLowCodeBigInt;
    }

    public TypeEntity getJeeLowCodeBigDecimal() {
        return jeeLowCodeBigDecimal;
    }

    public TypeEntity getJeeLowCodeText() {
        return jeeLowCodeText;
    }

    public TypeEntity getJeeLowCodeLongText() {
        return jeeLowCodeLongText;
    }

    public TypeEntity getJeeLowCodeBlob() {
        return jeeLowCodeBlob;
    }


    public void setJeeLowCodeString(String dbType, Integer maxLen) {
        TypeEntity typeEntity = new TypeEntity("String", dbType, maxLen);
        this.jeeLowCodeString = typeEntity;
    }

    public void setJeeLowCodeInteger(String dbType, Integer maxLen) {
        TypeEntity typeEntity = new TypeEntity("Integer", dbType, maxLen);
        this.jeeLowCodeInteger = typeEntity;
    }

    public void setJeeLowCodeDate(String dbType, Integer maxLen) {
        TypeEntity typeEntity = new TypeEntity("Date", dbType, maxLen);
        this.jeeLowCodeDate = typeEntity;
    }

    public void setJeeLowCodeDateTime(String dbType, Integer maxLen) {
        TypeEntity typeEntity = new TypeEntity("DateTime", dbType, maxLen);
        this.jeeLowCodeDateTime = typeEntity;
    }

    public void setJeeLowCodeTime(String dbType, Integer maxLen) {
        TypeEntity typeEntity = new TypeEntity("Time", dbType, maxLen);
        this.jeeLowCodeTime = typeEntity;
    }

    public void setJeeLowCodeBigInt(String dbType, Integer maxLen) {
        TypeEntity typeEntity = new TypeEntity("BigInt", dbType, maxLen);
        this.jeeLowCodeBigInt = typeEntity;
    }

    public void setJeeLowCodeBigDecimal(String dbType, Integer maxLen,Integer maxPointLen) {
        TypeEntity typeEntity = new TypeEntity("BigDecimal", dbType, maxLen,maxPointLen);
        this.jeeLowCodeBigDecimal = typeEntity;
    }

    public void setJeeLowCodeText(String dbType, Integer maxLen) {
        TypeEntity typeEntity = new TypeEntity("Text", dbType, maxLen);
        this.jeeLowCodeText = typeEntity;
    }

    public void setJeeLowCodeLongText(String dbType, Integer maxLen) {
        TypeEntity typeEntity = new TypeEntity("LongText", dbType, maxLen);
        this.jeeLowCodeLongText = typeEntity;
    }

    public void setJeeLowCodeBlob(String dbType, Integer maxLen) {
        TypeEntity typeEntity = new TypeEntity("Blob", dbType, maxLen);
        this.jeeLowCodeBlob = typeEntity;
    }


    public Boolean getUpperFlag() {
        return upperFlag;
    }

    public void setUpperFlag(Boolean upperFlag) {
        this.upperFlag = upperFlag;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }
}
