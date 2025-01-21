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

import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.jeelowcode.core.framework.entity.LogModuleUsageRecordsEntity;
import com.jeelowcode.core.framework.mapper.JeeLowCodeMapper;
import com.jeelowcode.core.framework.mapper.LogModuleUsageRecordsMapper;
import com.jeelowcode.core.framework.service.IApiLogService;
import com.jeelowcode.core.framework.service.IJeeLowCodeService;
import com.jeelowcode.core.framework.utils.Func;
import com.jeelowcode.framework.utils.utils.JeeLowCodeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 *  低代码平台
 */
@Service
public class JeeLowCodeServiceImpl implements IJeeLowCodeService {

	@Autowired
	private JeeLowCodeMapper jeeLowCodeMapper;

    @Autowired
    private LogModuleUsageRecordsMapper logModuleUsageRecordsMapper;


    @Autowired
    private IApiLogService iApiLogService;

	//清除ExcelData日志
	@Override
	public Integer clearExcelData(Date clearDate){
		return jeeLowCodeMapper.clearExcelData(clearDate);
	}

    // 保存模块使用记录
    @Override
    public Integer saveModelUsageRecords() {
        Date now = DateUtil.date();
        Date begin = DateUtil.beginOfDay(now);
        LocalDate today = LocalDate.now();
        LocalDateTime dateTimeNow = LocalDateTime.now();
        // 从日志表统计模块使用记录
        List<Map<String, Object>> records = iApiLogService.countModelUsageRecords(begin, now);
        // 写入模块使用统计表
        for (Map<String, Object> record : records) {
            String modelTitle = JeeLowCodeUtils.getMap2Str(record, "modeltitle");
            Long usersNum = JeeLowCodeUtils.getMap2Long(record, "usersnum");
            Long useNum = JeeLowCodeUtils.getMap2Long(record, "usenum");
            Long id = getLogModuleUsageRecordsId(modelTitle, today);
            LogModuleUsageRecordsEntity moduleEntity = new LogModuleUsageRecordsEntity();
            moduleEntity.setUsersNum(usersNum);
            moduleEntity.setUseNum(useNum);
            if (Func.isEmpty(id)) {
                moduleEntity.setTenantId(1L);
                moduleEntity.setCreateUser(1L);
                moduleEntity.setCreateTime(dateTimeNow);
                moduleEntity.setCreateDept(101L);
                moduleEntity.setIsDeleted(0);
                moduleEntity.setRecordDate(today);
                moduleEntity.setModelTitle(modelTitle);
            } else {
                moduleEntity.setId(id);
                moduleEntity.setUpdateUser(1L);
                moduleEntity.setUpdateTime(dateTimeNow);
            }
            saveLogModuleUsageRecords(moduleEntity);
        }
        return records.size();
    }

    /**
     * 按模块标题和记录日期获取id
     */
    private Long getLogModuleUsageRecordsId(String modelTitle, LocalDate recordDate) {
        LambdaQueryWrapper<LogModuleUsageRecordsEntity> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(LogModuleUsageRecordsEntity::getModelTitle, modelTitle);
        wrapper.eq(LogModuleUsageRecordsEntity::getRecordDate, recordDate);
        wrapper.select(LogModuleUsageRecordsEntity::getId);
        LogModuleUsageRecordsEntity one = logModuleUsageRecordsMapper.selectOne(wrapper);
        if (Func.isEmpty(one) || Func.isEmpty(one.getId())) {
            return null;
        }
        return one.getId();
    }

    /**
     * 写入模块使用统计表
     */
    private void saveLogModuleUsageRecords(LogModuleUsageRecordsEntity entity) {
        if (Func.isEmpty(entity.getId())) {
            logModuleUsageRecordsMapper.insert(entity);
        } else {
            logModuleUsageRecordsMapper.updateById(entity);
        }
    }

}
