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

import com.jeelowcode.core.framework.entity.LogRequestApiEntity;
import com.jeelowcode.core.framework.entity.LogRequestErrorApiEntity;
import com.jeelowcode.core.framework.params.LogRequestApiParam;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 日志相关
 */
public interface IApiLogService {
    //清除某一个日期前的api请求日志
    Integer clearApiLog(Date clearDate);

    //清除某一个日期前的api错误请求日志
    Integer clearApiErrorLog(Date clearDate);

    //获取info日志列表
    IPage<LogRequestApiEntity> getInfoApiLogPage(LogRequestApiParam param, Page page);
    //获取error日志列表
    IPage<LogRequestErrorApiEntity> getErrorApiLogPage(LogRequestApiParam param, Page page);

    //获取info日志详情
    LogRequestApiEntity getInfoApiLogDetail(Long id);
    //获取error日志详情
    LogRequestErrorApiEntity getErrorApiLogDetail(Long id);

    // 统计模块使用记录
    List<Map<String, Object>> countModelUsageRecords(Date begin, Date end);
}
