package com.jeelowcode.service.system.config.convert.tenant;

import com.jeelowcode.service.system.controller.vo.tenant.tenant.TenantSaveReqVO;
import com.jeelowcode.service.system.controller.vo.user.user.UserSaveReqVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * 租户 Convert
 *
 * @author 芋道源码
 */
@Mapper
public interface TenantConvert {

    TenantConvert INSTANCE = Mappers.getMapper(TenantConvert.class);

    default UserSaveReqVO convert02(TenantSaveReqVO bean) {
        UserSaveReqVO reqVO = new UserSaveReqVO();
        reqVO.setUsername(bean.getUsername());
        reqVO.setPassword(bean.getPassword());
        reqVO.setNickname(bean.getContactName()).setMobile(bean.getContactMobile());
        return reqVO;
    }

}
