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
package com.jeelowcode.framework.utils.params;

import java.util.List;
import java.util.Map;

public class JeeLowCodeDictTableParam {

    //字典编码
    List<DictCodeDataParam> paramList;


    public static class DictCodeDataParam{

        private String tableName;//表名称
        private String fieldKey;//字段键
        private String fieldValue;//字段值

        private String whereField;//id
        private String whereValue;//1

        public String getTableName() {
            return tableName;
        }

        public void setTableName(String tableName) {
            this.tableName = tableName;
        }


        public String getFieldKey() {
            return fieldKey;
        }

        public void setFieldKey(String fieldKey) {
            this.fieldKey = fieldKey;
        }

        public String getFieldValue() {
            return fieldValue;
        }

        public void setFieldValue(String fieldValue) {
            this.fieldValue = fieldValue;
        }

        public String getWhereField() {
            return whereField;
        }

        public void setWhereField(String whereField) {
            this.whereField = whereField;
        }

        public String getWhereValue() {
            return whereValue;
        }

        public void setWhereValue(String whereValue) {
            this.whereValue = whereValue;
        }
    }

    public List<DictCodeDataParam> getParamList() {
        return paramList;
    }

    public void setParamList(List<DictCodeDataParam> paramList) {
        this.paramList = paramList;
    }
}
