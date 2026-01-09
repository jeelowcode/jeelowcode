package com.jeelowcode.service.infra.mapper;

import com.jeelowcode.tool.framework.mybatis.core.mapper.BaseMapperX;
import com.jeelowcode.service.infra.entity.DataSourceConfigDO;
import org.apache.ibatis.annotations.Mapper;

/**
 * 数据源配置 Mapper
 *
 * @author 芋道源码
 */
@Mapper
public interface DataSourceConfigMapper extends BaseMapperX<DataSourceConfigDO> {
}
