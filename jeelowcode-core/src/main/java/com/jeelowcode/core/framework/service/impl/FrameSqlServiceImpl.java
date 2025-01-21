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
package com.jeelowcode.core.framework.service.impl;

import com.jeelowcode.core.framework.mapper.JeeLowCodeSqlMapper;
import com.jeelowcode.core.framework.service.IFrameSqlService;
import com.jeelowcode.framework.plus.SqlHelper;
import com.jeelowcode.framework.plus.build.buildmodel.wrapper.SqlInfoDeleteWrapper;
import com.jeelowcode.framework.plus.build.buildmodel.wrapper.SqlInfoQueryWrapper;
import com.jeelowcode.framework.plus.build.buildmodel.wrapper.SqlInfoUpdateWrapper;
import com.jeelowcode.framework.plus.core.model.SqlFormatModel;
import com.jeelowcode.framework.utils.utils.FuncBase;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *  低代码平台
 *  该接口下所有业务不用租户过滤
 */

@Service
public class FrameSqlServiceImpl implements IFrameSqlService {

	@Autowired
	private JeeLowCodeSqlMapper sqlMapper;


	@Override
	public void executeDDL(String ddlSql){
		sqlMapper.executeDDL(ddlSql);
	}

	//获取数据，不做租户等其他处理
	@Override
	public List<Map<String, Object>> getSimpleData(String sql){

		Map<String,Object> dataMap=new HashMap<>();
		return sqlMapper.selectData(sql,dataMap);
	}

	//转为ew.
	private String formatSql2EW(String sql){
		String replacedSql = sql.replaceAll("(?<!#\\{ew\\.)\\#\\{(\\w+)}", "#{ew.$1}");
		return replacedSql;
	}

	//查询数据相关
	//单个
	@Override
	public Map<String, Object> getDataOneByPlus(SqlInfoQueryWrapper.Wrapper wrapper){
		SqlFormatModel sqlFormatModel = wrapper.buildSql();
		List<Map<String, Object>> dataMapList = sqlMapper.selectData(formatSql2EW(sqlFormatModel.getSql()),sqlFormatModel.getDataMap());
		if(FuncBase.isEmpty(dataMapList)){
			return new HashMap<>();
		}
		return dataMapList.get(0);
	}
	@Override
	public Map<String, Object> getDataOneByPlus(SqlInfoQueryWrapper.Wrapper wrapper,Map<String,Object> params){
		SqlFormatModel sqlFormatModel = wrapper.buildSql();
		Map<String, Object> dataMap = sqlFormatModel.getDataMap();
		if(FuncBase.isNotEmpty(params)){
			for (Map.Entry<String, Object> entry : params.entrySet()) {
				String key = entry.getKey();
				Object value = entry.getValue();
				// 如果map1中不存在该键，则添加
				if (!dataMap.containsKey(key)) {
					dataMap.put(key, value);
				}
			};
		}
		List<Map<String, Object>> dataMapList = sqlMapper.selectData(formatSql2EW(sqlFormatModel.getSql()),dataMap);
		if(FuncBase.isEmpty(dataMapList)){
			return new HashMap<>();
		}
		return dataMapList.get(0);
	}
	//多个
	@Override
	public List<Map<String, Object>> getDataListByPlus(SqlInfoQueryWrapper.Wrapper wrapper){
		SqlFormatModel sqlFormatModel = wrapper.buildSql();
		List<Map<String, Object>> dataMapList = sqlMapper.selectData(formatSql2EW(sqlFormatModel.getSql()),sqlFormatModel.getDataMap());
		if(FuncBase.isEmpty(dataMapList)){
			return dataMapList;
		}
		return dataMapList;
	}
	@Override
	public List<Map<String, Object>> getDataListByPlus(SqlInfoQueryWrapper.Wrapper wrapper,Map<String,Object> params){
		SqlFormatModel sqlFormatModel = wrapper.buildSql();
		Map<String, Object> dataMap = sqlFormatModel.getDataMap();
		if(FuncBase.isNotEmpty(params)){
			for (Map.Entry<String, Object> entry : params.entrySet()) {
				String key = entry.getKey();
				Object value = entry.getValue();
				// 如果map1中不存在该键，则添加
				if (!dataMap.containsKey(key)) {
					dataMap.put(key, value);
				}
			}
		}
		List<Map<String, Object>> dataMapList = sqlMapper.selectData(formatSql2EW(sqlFormatModel.getSql()),dataMap);
		if(FuncBase.isEmpty(dataMapList)){
			return dataMapList;
		}
		return dataMapList;
	}

	@Override
	public List<Map<String, Object>> getDataListByPlus(String sql,Map<String,Object> params){
		String publicSql = SqlHelper.getPublicSql(sql);

		List<Map<String, Object>> dataMapList = sqlMapper.selectData(formatSql2EW(publicSql),params);
		return dataMapList;
	}

	//分页
	@Override
	public IPage<Map<String, Object>> getDataIPageByPlus(IPage page,SqlInfoQueryWrapper.Wrapper wrapper,Map<String,Object> params){
		SqlFormatModel sqlFormatModel = wrapper.buildSql();
		Map<String, Object> dataMap = sqlFormatModel.getDataMap();
		if(FuncBase.isNotEmpty(params)){
			for (Map.Entry<String, Object> entry : params.entrySet()) {
				String key = entry.getKey();
				Object value = entry.getValue();
				// 如果map1中不存在该键，则添加
				if (!dataMap.containsKey(key)) {
					dataMap.put(key, value);
				}
			};
		}
		IPage<Map<String, Object>> pages = sqlMapper.selectPageData(page, formatSql2EW(sqlFormatModel.getSql()),dataMap);
		return pages;
	}

	@Override
	public IPage<Map<String, Object>> getDataIPageByPlus(IPage page,SqlInfoQueryWrapper.Wrapper wrapper){
		SqlFormatModel sqlFormatModel = wrapper.buildSql();
		IPage<Map<String, Object>> pages = sqlMapper.selectPageData(page, formatSql2EW(sqlFormatModel.getSql()),sqlFormatModel.getDataMap());
		return pages;
	}

	//修改数据相关
	@Override
	public void updateDataByPlus(SqlInfoUpdateWrapper.Wrapper wrapper){
		SqlFormatModel sqlFormatModel = wrapper.buildSql();
		sqlMapper.updateData(formatSql2EW(sqlFormatModel.getSql()),sqlFormatModel.getDataMap());
	}

	//新增数据相关
	@Override
	public void insertDataByPlus(String tableName, Map<String,Object> addDataMap){
		SqlFormatModel sqlFormatModel = SqlHelper.getInsertWrapper()
				.setTableName(tableName)
				.setMap(addDataMap)
				.buildSql();
		sqlMapper.insertData(formatSql2EW(sqlFormatModel.getSql()),sqlFormatModel.getDataMap());
	}



	//真实-删除数据
	@Override
	public void delRealDataByPlus(SqlInfoDeleteWrapper.Wrapper wrapper){
		SqlFormatModel sqlFormatModel = wrapper.buildSql();
		sqlMapper.deleteData(formatSql2EW(sqlFormatModel.getSql()),sqlFormatModel.getDataMap());
	}
	//逻辑-删除数据
	@Override
	public void delDataByPlus(SqlInfoUpdateWrapper.Wrapper wrapper){
		wrapper.setColumn("is_deleted",-1);
		SqlFormatModel sqlFormatModel = wrapper.buildSql();
		sqlMapper.updateData(formatSql2EW(sqlFormatModel.getSql()),sqlFormatModel.getDataMap());
	}

	//----------------------------------------------------------
	//获取表数据
	@Override
	public List<Map<String,Object>> getTableDataList(String tableName){
		SqlInfoQueryWrapper.Wrapper wrapper = SqlHelper.getQueryWrapper()
				.setTableName(tableName)
				.build();
		List<Map<String, Object>> resultList = this.getDataListByPlus(wrapper);
		return resultList;
	}
	@Override
	public List<Map<String,Object>> getTableDataList(String tableName,List<String> fieldList){
		SqlInfoQueryWrapper.Wrapper wrapper = SqlHelper.getQueryWrapper()
				.setTableName(tableName)
				.select(fieldList,true)
				.build();
		return this.getDataListByPlus(wrapper);
	}

	//根据Id获取数据
	@Override
	public Map<String,Object> getTableDataById(String tableName,Long id){
		SqlInfoQueryWrapper.Wrapper wrapper = SqlHelper.getQueryWrapper()
				.setTableName(tableName)
				.setWhere(where -> {
					where.eq("id", id);
				})
				.build();
		return this.getDataOneByPlus(wrapper);
	}
	@Override
	public Map<String,Object> getTableDataById(String tableName,Long id,List<String> fieldList){
		SqlInfoQueryWrapper.Wrapper wrapper = SqlHelper.getQueryWrapper()
				.setTableName(tableName)
				.select(fieldList,true)
				.setWhere(where -> {
					where.eq("id", id);
				})
				.build();
		return this.getDataOneByPlus(wrapper);
	}

	//自定义根据某一个字段查询--单条
	@Override
	public Map<String, Object> getDataOneByField(String tableName, String whereFieldName, Object whereFieldValue){
		SqlInfoQueryWrapper.Wrapper wrapper = SqlHelper.getQueryWrapper()
				.setTableName(tableName)
				.setWhere(where -> {
					where.eq(whereFieldName, whereFieldValue);
				})
				.build();

		return this.getDataOneByPlus(wrapper);
	}
	@Override
	public Map<String, Object> getDataOneByField(String tableName, String whereFieldName, Object whereFieldValue,List<String> fieldList){
		SqlInfoQueryWrapper.Wrapper wrapper = SqlHelper.getQueryWrapper()
				.setTableName(tableName)
				.select(fieldList,true)
				.setWhere(where -> {
					where.eq(whereFieldName, whereFieldValue);
				})
				.build();
		return this.getDataOneByPlus(wrapper);
	}

	//自定义根据某一个字段查询--多条
	@Override
	public List<Map<String, Object>> getDataListByField(String tableName, String whereFieldName, Object whereFieldValue){
		SqlInfoQueryWrapper.Wrapper wrapper = SqlHelper.getQueryWrapper()
				.setTableName(tableName)
				.setWhere(where -> {
					where.eq(whereFieldName, whereFieldValue);
				})
				.build();
		return this.getDataListByPlus(wrapper);
	}
	@Override
	public List<Map<String, Object>> getDataListByField(String tableName, String whereFieldName, Object whereFieldValue,List<String> fieldList){
		SqlInfoQueryWrapper.Wrapper wrapper = SqlHelper.getQueryWrapper()
				.setTableName(tableName)
				.select(fieldList,true)
				.setWhere(where -> {
					where.eq(whereFieldName, whereFieldValue);
				})
				.build();

		return this.getDataListByPlus(wrapper);
	}

	//公共修改
	@Override
	public void baseUpdateDataById(String tableName,Map<String,Object> dataMap,Long id){
		SqlInfoUpdateWrapper.Wrapper wrapper = SqlHelper.getUpdateWrapper()
				.setTableName(tableName)
				.setMap(dataMap)
				.setWhere(where -> {
					where.eq("id", id);
				}).build();
		this.updateDataByPlus(wrapper);
	}
	public void baseUpdateDataByField(String tableName,Map<String,Object> dataMap,String whereFieldName, Object whereFieldValue){
		SqlInfoUpdateWrapper.Wrapper wrapper = SqlHelper.getUpdateWrapper()
				.setTableName(tableName)
				.setMap(dataMap)
				.setWhere(where -> {
					where.eq(whereFieldName, whereFieldValue);
				}).build();
		this.updateDataByPlus(wrapper);
	}

	//公共删除-逻辑
	@Override
	public void baseDelDataById(String tableName,Long id){
		SqlInfoUpdateWrapper.Wrapper wrapper = SqlHelper.getUpdateWrapper()
				.setTableName(tableName)
				.setWhere(where -> {
					where.eq("id",id);
				});
		this.delDataByPlus(wrapper);
	}
	@Override
	public void baseDelDataByField(String tableName,String whereFieldName, Object whereFieldValue){
		SqlInfoUpdateWrapper.Wrapper wrapper = SqlHelper.getUpdateWrapper()
				.setTableName(tableName)
				.setWhere(where -> {
					where.eq(whereFieldName, whereFieldValue);
				});
		this.delDataByPlus(wrapper);
	}

	//公共删除-真实
	@Override
	public void baseDelRealData(String tableName,Long id){
		SqlInfoDeleteWrapper.Wrapper wrapper = SqlHelper.getDeleteWrapper()
				.setTableName(tableName)
				.setWhere(where -> {
					where.eq("id", id);
				}).build();
		this.delRealDataByPlus(wrapper);
	}
	@Override
	public void baseDelRealDataByField(String tableName,String whereFieldName, Object whereFieldValue){
		SqlInfoDeleteWrapper.Wrapper wrapper = SqlHelper.getDeleteWrapper()
				.setTableName(tableName)
				.setWhere(where -> {
					where.eq(whereFieldName, whereFieldValue);
				}).build();
		this.delRealDataByPlus(wrapper);
	}

}
