package cn.iocoder.yudao.module.system.dal.mysql.permission;

import cn.hutool.core.util.ObjectUtil;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.module.system.controller.admin.permission.vo.menu.MenuListReqVO;
import cn.iocoder.yudao.module.system.dal.dataobject.permission.MenuDO;
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