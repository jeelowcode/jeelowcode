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


import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import com.jeelowcode.core.framework.entity.LogRequestApiEntity;
import com.jeelowcode.core.framework.entity.LogRequestErrorApiEntity;
import com.jeelowcode.core.framework.mapper.LogApiErrorMapper;
import com.jeelowcode.core.framework.mapper.LogApiMapper;
import com.jeelowcode.core.framework.params.LogRequestApiParam;
import com.jeelowcode.core.framework.service.IApiLogService;
import com.jeelowcode.core.framework.utils.Func;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 *  日志相关日志相关日志相关
 */
@Service
public class ApiLogServiceImpl implements IApiLogService {

	@Autowired
	private LogApiMapper logApiMapper;

	@Autowired
	private LogApiErrorMapper logApiErrorMapper;

	//请求某一个日期前的api请求日志
	@Override
	public Integer clearApiLog(Date clearDate){
		return logApiMapper.clearApiLog(clearDate);
	}

	//清除某一个日期前的api错误请求日志
	@Override
	public Integer clearApiErrorLog(Date clearDate){
		return logApiErrorMapper.clearApiLog(clearDate);
	}

	//获取info日志列表
	@Override
	public IPage<LogRequestApiEntity> getInfoApiLogPage(LogRequestApiParam param, Page page){
		LambdaQueryWrapper<LogRequestApiEntity> wrapper=new LambdaQueryWrapper<>();
		wrapper.eq(Func.isNotEmpty(param.getTenantId()),LogRequestApiEntity::getTenantId,param.getTenantId());
		wrapper.eq(Func.isNotEmpty(param.getCreateUserName()),LogRequestApiEntity::getCreateUserName,param.getCreateUserName());
		wrapper.eq(Func.isNotEmpty(param.getRequestUri()),LogRequestApiEntity::getRequestUri,param.getRequestUri());
		//处理时间
		if(Func.isNotEmpty(param.getCreateTime())){
			String createTimeStr = param.getCreateTime();
			String[] timeStrs = createTimeStr.split(",");
			DateTime startTime = DateUtil.parse(timeStrs[0],"yyyy-MM-dd HH:mm:ss");
			DateTime endTime = DateUtil.parse(timeStrs[1],"yyyy-MM-dd HH:mm:ss");
			wrapper.between(LogRequestApiEntity::getCreateTime,startTime,endTime);
		}
		wrapper.gt(Func.isNotEmpty(param.getTime()),LogRequestApiEntity::getTime,param.getTime());

		wrapper.select(LogRequestApiEntity::getId,LogRequestApiEntity::getTenantId,LogRequestApiEntity::getCreateUserName,LogRequestApiEntity::getCreateTime,LogRequestApiEntity::getRequestUri,LogRequestApiEntity::getIp,LogRequestApiEntity::getTime,LogRequestApiEntity::getTitle,LogRequestApiEntity::getModelTitle);
		wrapper.orderByDesc(LogRequestApiEntity::getId);
		Page pages = logApiMapper.selectPage(page, wrapper);
		return pages;
	}

	//获取error日志列表
	@Override
	public IPage<LogRequestErrorApiEntity> getErrorApiLogPage(LogRequestApiParam param, Page page){
		LambdaQueryWrapper<LogRequestErrorApiEntity> wrapper=new LambdaQueryWrapper<>();
		wrapper.eq(Func.isNotEmpty(param.getTenantId()),LogRequestErrorApiEntity::getTenantId,param.getTenantId());
		wrapper.eq(Func.isNotEmpty(param.getCreateUserName()),LogRequestErrorApiEntity::getCreateUserName,param.getCreateUserName());
		wrapper.eq(Func.isNotEmpty(param.getRequestUri()),LogRequestErrorApiEntity::getRequestUri,param.getRequestUri());
		//处理时间
		if(Func.isNotEmpty(param.getCreateTime())){
			String createTimeStr = param.getCreateTime();
			String[] timeStrs = createTimeStr.split(",");
			DateTime startTime = DateUtil.parse(timeStrs[0],"yyyy-MM-dd HH:mm:ss");
			DateTime endTime = DateUtil.parse(timeStrs[1],"yyyy-MM-dd HH:mm:ss");
			wrapper.between(LogRequestErrorApiEntity::getCreateTime,startTime,endTime);
		}
		wrapper.gt(Func.isNotEmpty(param.getTime()),LogRequestErrorApiEntity::getTime,param.getTime());


		wrapper.select(LogRequestErrorApiEntity::getId,LogRequestErrorApiEntity::getTenantId,LogRequestErrorApiEntity::getCreateUserName,LogRequestErrorApiEntity::getCreateTime,LogRequestErrorApiEntity::getRequestUri,LogRequestErrorApiEntity::getIp,LogRequestErrorApiEntity::getTime,LogRequestErrorApiEntity::getTitle,LogRequestErrorApiEntity::getModelTitle);
		wrapper.orderByDesc(LogRequestErrorApiEntity::getId);
		Page pages = logApiErrorMapper.selectPage(page, wrapper);
		return pages;
	}

	//获取info日志详情
	@Override
	public LogRequestApiEntity getInfoApiLogDetail(Long id){
		return logApiMapper.selectById(id);
	}
	//获取error日志详情
	@Override
	public LogRequestErrorApiEntity getErrorApiLogDetail(Long id){
		return logApiErrorMapper.selectById(id);
	}

	// 统计模块使用记录
	@Override
	public List<Map<String, Object>> countModelUsageRecords(Date begin, Date end) {
		return logApiMapper.countModelUsageRecords(begin, end);
	}

}
