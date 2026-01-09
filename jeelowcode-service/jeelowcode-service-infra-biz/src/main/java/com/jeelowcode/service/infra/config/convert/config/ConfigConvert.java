package com.jeelowcode.service.infra.config.convert.config;

import com.jeelowcode.tool.framework.common.pojo.PageResult;
import com.jeelowcode.service.infra.controller.vo.config.ConfigRespVO;
import com.jeelowcode.service.infra.controller.vo.config.ConfigSaveReqVO;
import com.jeelowcode.service.infra.entity.ConfigDO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface ConfigConvert {

    ConfigConvert INSTANCE = Mappers.getMapper(ConfigConvert.class);

    PageResult<ConfigRespVO> convertPage(PageResult<ConfigDO> page);

    List<ConfigRespVO> convertList(List<ConfigDO> list);

    @Mapping(source = "configKey", target = "key")
    ConfigRespVO convert(ConfigDO bean);

    @Mapping(source = "key", target = "configKey")
    ConfigDO convert(ConfigSaveReqVO bean);

}
