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
package com.jeelowcode.core.framework.mapper;

import com.jeelowcode.framework.utils.annotation.JeelowCodeCache;
import org.apache.ibatis.annotations.Delete;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 低代码平台
 */
public interface JeeLowCodeMapper {

    //获取前端显示列
    @JeelowCodeCache(cacheNames = "'JeeLowCodeMapper:getWebViewFieldList:' + #dbFormId", reflexClass = String.class)
    List<String> getWebViewFieldList(Long dbFormId);

    //获取开启搜索列
    @JeelowCodeCache(cacheNames = "'JeeLowCodeMapper:getDbWhereFieldList:' + #dbFormId", reflexClass = Map.class,nullIsSave = true)
    List<Map<String,String>> getDbWhereFieldList(Long dbFormId);

    @JeelowCodeCache(cacheNames = "'JeeLowCodeMapper:getAllDbWhereFieldList:' + #dbFormId", reflexClass = Map.class,nullIsSave = true)
    List<Map<String,String>> getAllDbWhereFieldList(Long dbFormId);

    //获取开启搜索列-数据报表
    @JeelowCodeCache(cacheNames = "'JeeLowCodeMapper:getReportWhereFieldList:' + #reportId", reflexClass = Map.class,nullIsSave=true)
    List<Map<String,String>> getReportWhereFieldList(Long reportId);

    //获取导出字段
    List<Map<String,Object>> getExcelFieldList(Long dbFormId);

    //获取数量
    Map<String,Object> getFormCou(Long dbFormId);


    //清除ExcelData
    @Delete("delete from lowcode_log_excel_file_data where create_time<#{clearDate}")
    Integer clearExcelData(Date clearDate);

}
