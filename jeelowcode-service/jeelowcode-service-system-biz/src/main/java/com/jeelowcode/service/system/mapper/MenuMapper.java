package com.jeelowcode.service.system.mapper;

import cn.hutool.core.util.ObjectUtil;
import com.jeelowcode.service.system.controller.vo.permission.menu.MenuListReqVO;
import com.jeelowcode.service.system.entity.MenuDO;
import com.jeelowcode.tool.framework.mybatis.core.mapper.BaseMapperX;
import com.jeelowcode.tool.framework.mybatis.core.query.LambdaQueryWrapperX;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface MenuMapper extends BaseMapperX<MenuDO> {

    default MenuDO selectByParentIdAndName(Long parentId, String name) {
        return selectOne(MenuDO::getParentId, parentId, MenuDO::getName, name);
    }

    default Long selectCountByParentId(Long parentId) {
        return selectCount(MenuDO::getParentId, parentId);
    }


    default List<MenuDO> selectList(MenuListReqVO reqVO) {
        if(reqVO.getParentId()!=null || ObjectUtil.isEmpty(reqVO.getBtnDisplay())){
            return selectList(new LambdaQueryWrapperX<MenuDO>()
                    .eqIfPresent(MenuDO::getParentId,reqVO.getParentId())
                    .likeIfPresent(MenuDO::getName, reqVO.getName())
                    .eqIfPresent(MenuDO::getStatus, reqVO.getStatus()));
        }

        return selectList(new LambdaQueryWrapperX<MenuDO>()
                .neIfPresent(MenuDO::getType,3)
                .likeIfPresent(MenuDO::getName, reqVO.getName())
                .eqIfPresent(MenuDO::getStatus, reqVO.getStatus()));
    }

    default List<MenuDO> selectListByPermission(String permission) {
        return selectList(MenuDO::getPermission, permission);
    }
}
