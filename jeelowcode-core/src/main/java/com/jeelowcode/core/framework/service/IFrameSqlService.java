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
package com.jeelowcode.core.framework.service;

import com.jeelowcode.framework.plus.build.buildmodel.wrapper.SqlInfoDeleteWrapper;
import com.jeelowcode.framework.plus.build.buildmodel.wrapper.SqlInfoQueryWrapper;
import com.jeelowcode.framework.plus.build.buildmodel.wrapper.SqlInfoUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;

import java.util.List;
import java.util.Map;

/**
 * 框架SQL相关
 */
public interface IFrameSqlService {

    //运行ddl语句
    void executeDDL(String ddlSql);

    //获取数据，不做租户等其他处理
    List<Map<String, Object>> getSimpleData(String sql);

    //查询数据相关
    //单个
    Map<String, Object> getDataOneByPlus(SqlInfoQueryWrapper.Wrapper wrapper);
    Map<String, Object> getDataOneByPlus(SqlInfoQueryWrapper.Wrapper wrapper,Map<String,Object> params);
    //多个
    List<Map<String, Object>> getDataListByPlus(SqlInfoQueryWrapper.Wrapper wrapper);
    List<Map<String, Object>> getDataListByPlus(SqlInfoQueryWrapper.Wrapper wrapper,Map<String,Object> params);
    List<Map<String, Object>> getDataListByPlus(String sql,Map<String,Object> params);
    //分页
    IPage<Map<String, Object>> getDataIPageByPlus(IPage page, SqlInfoQueryWrapper.Wrapper wrapper);
    IPage<Map<String, Object>> getDataIPageByPlus(IPage page, SqlInfoQueryWrapper.Wrapper wrapper,Map<String,Object> params);

    //修改数据
    void updateDataByPlus(SqlInfoUpdateWrapper.Wrapper wrapper);

    //新增数据
    void insertDataByPlus(String tableName, Map<String,Object> addDataMap);

    //真实-删除数据
    void delRealDataByPlus(SqlInfoDeleteWrapper.Wrapper wrapper);
    //逻辑-删除数据
    void delDataByPlus(SqlInfoUpdateWrapper.Wrapper wrapper);

    //--------------------------抽取快捷方法--------------------------------
    //获取表数据
    List<Map<String,Object>> getTableDataList(String tableName);
    List<Map<String,Object>> getTableDataList(String tableName,List<String> fieldList);

    //根据Id获取数据
    Map<String,Object> getTableDataById(String tableName,Long id);
    Map<String,Object> getTableDataById(String tableName,Long id,List<String> fieldList);

    //自定义根据某一个字段查询-返回单条
    Map<String, Object> getDataOneByField(String tableName, String whereFieldName, Object whereFieldValue);
    Map<String, Object> getDataOneByField(String tableName, String whereFieldName, Object whereFieldValue,List<String> fieldList);
    //自定义根据某一个字段查询-返回多条
    List<Map<String, Object>> getDataListByField(String tableName, String whereFieldName, Object whereFieldValue);
    List<Map<String, Object>> getDataListByField(String tableName, String whereFieldName, Object whereFieldValue,List<String> fieldList);

    //公共修改
    void baseUpdateDataById(String tableName,Map<String,Object> dataMap,Long id);
    void baseUpdateDataByField(String tableName,Map<String,Object> dataMap,String whereFieldName, Object whereFieldValue);

    //公共删除-逻辑
    void baseDelDataById(String tableName,Long id);
    void baseDelDataByField(String tableName,String whereFieldName, Object whereFieldValue);

    //公共删除-真实
    void baseDelRealData(String tableName,Long id);
    void baseDelRealDataByField(String tableName,String whereFieldName, Object whereFieldValue);
}
