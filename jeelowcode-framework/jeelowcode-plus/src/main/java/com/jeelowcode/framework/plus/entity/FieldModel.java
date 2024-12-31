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


import com.jeelowcode.framework.utils.enums.JeeLowCodeFieldTypeEnum;

import java.util.List;
import java.util.Map;

/**
 * 数据库字段列表
 */
public class FieldModel {
	private String fieldCode;//字段
	private String fieldName;//字段名称
	private Integer fieldLen;//长度
	private Integer fieldPointLen;//小数位
	private String fieldDefaultVal;//字段默认值
	private JeeLowCodeFieldTypeEnum fieldType;//字段类型
	private List<JeeLowCodeFieldTypeEnum> javaFieldTypeList;//字段类型
	private String isPrimaryKey;//是否是主键  Y=是  N=不是
	private String isNull;//是否允许为空 Y=允许  N=不允许

	private Map<String,Object> otherMap;//其他值

	private String dbNowIsNull;//当前数据库是否为空状态

	private Integer maxFieldLen;//最大长度
	private Integer maxPointLen;//最大小数位长度

	public String getFieldCode() {
		return fieldCode;
	}

	public void setFieldCode(String fieldCode) {
		this.fieldCode = fieldCode;
	}

	public String getFieldName() {
		return fieldName;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	public Integer getFieldLen() {
		return fieldLen;
	}

	public void setFieldLen(Integer fieldLen) {
		this.fieldLen = fieldLen;
	}

	public Integer getFieldPointLen() {
		return fieldPointLen;
	}

	public void setFieldPointLen(Integer fieldPointLen) {
		this.fieldPointLen = fieldPointLen;
	}

	public String getFieldDefaultVal() {
		return fieldDefaultVal;
	}

	public void setFieldDefaultVal(String fieldDefaultVal) {
		this.fieldDefaultVal = fieldDefaultVal;
	}

	public JeeLowCodeFieldTypeEnum getFieldType() {
		return fieldType;
	}

	public void setFieldType(JeeLowCodeFieldTypeEnum fieldType) {
		this.fieldType = fieldType;
	}

	public List<JeeLowCodeFieldTypeEnum> getJavaFieldTypeList() {
		return javaFieldTypeList;
	}

	public void setJavaFieldTypeList(List<JeeLowCodeFieldTypeEnum> javaFieldTypeList) {
		this.javaFieldTypeList = javaFieldTypeList;
	}

	public String getIsPrimaryKey() {
		return isPrimaryKey;
	}

	public void setIsPrimaryKey(String isPrimaryKey) {
		this.isPrimaryKey = isPrimaryKey;
	}

	public String getIsNull() {
		return isNull;
	}

	public void setIsNull(String isNull) {
		this.isNull = isNull;
	}

	public Map<String, Object> getOtherMap() {
		return otherMap;
	}

	public void setOtherMap(Map<String, Object> otherMap) {
		this.otherMap = otherMap;
	}

	public Integer getMaxFieldLen() {
		return maxFieldLen;
	}

	public void setMaxFieldLen(Integer maxFieldLen) {
		this.maxFieldLen = maxFieldLen;
	}

	public Integer getMaxPointLen() {
		return maxPointLen;
	}

	public void setMaxPointLen(Integer maxPointLen) {
		this.maxPointLen = maxPointLen;
	}

	public String getDbNowIsNull() {
		return dbNowIsNull;
	}

	public void setDbNowIsNull(String dbNowIsNull) {
		this.dbNowIsNull = dbNowIsNull;
	}
}
