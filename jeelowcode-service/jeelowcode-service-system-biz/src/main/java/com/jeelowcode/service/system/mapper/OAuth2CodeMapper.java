package com.jeelowcode.service.system.mapper;

import com.jeelowcode.service.system.entity.OAuth2CodeDO;
import com.jeelowcode.tool.framework.mybatis.core.mapper.BaseMapperX;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface OAuth2CodeMapper extends BaseMapperX<OAuth2CodeDO> {

    default OAuth2CodeDO selectByCode(String code) {
        return selectOne(OAuth2CodeDO::getCode, code);
    }

}
