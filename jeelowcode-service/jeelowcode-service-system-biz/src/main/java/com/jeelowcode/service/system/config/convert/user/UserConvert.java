package com.jeelowcode.service.system.config.convert.user;

import com.jeelowcode.service.system.controller.vo.dept.dept.DeptSimpleRespVO;
import com.jeelowcode.service.system.controller.vo.dept.post.PostSimpleRespVO;
import com.jeelowcode.service.system.controller.vo.permission.role.RoleSimpleRespVO;
import com.jeelowcode.service.system.controller.vo.user.profile.UserProfileRespVO;
import com.jeelowcode.service.system.controller.vo.user.user.UserRespVO;
import com.jeelowcode.service.system.controller.vo.user.user.UserSimpleRespVO;
import com.jeelowcode.tool.framework.common.util.collection.CollectionUtils;
import com.jeelowcode.tool.framework.common.util.collection.MapUtils;
import com.jeelowcode.tool.framework.common.util.object.BeanUtils;
import com.jeelowcode.service.system.entity.DeptDO;
import com.jeelowcode.service.system.entity.PostDO;
import com.jeelowcode.service.system.entity.RoleDO;
import com.jeelowcode.service.system.entity.SocialUserDO;
import com.jeelowcode.service.system.entity.AdminUserDO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;
import java.util.Map;

@Mapper
public interface UserConvert {

    UserConvert INSTANCE = Mappers.getMapper(UserConvert.class);

    default List<UserRespVO> convertList(List<AdminUserDO> list, Map<Long, DeptDO> deptMap) {
        return CollectionUtils.convertList(list, user -> convert(user, deptMap.get(user.getDeptId())));
    }

    default UserRespVO convert(AdminUserDO user, DeptDO dept) {
        UserRespVO userVO = BeanUtils.toBean(user, UserRespVO.class);
        if (dept != null) {
            userVO.setDeptName(dept.getName());
        }
        return userVO;
    }

    default List<UserSimpleRespVO> convertSimpleList(List<AdminUserDO> list, Map<Long, DeptDO> deptMap) {
        return CollectionUtils.convertList(list, user -> {
            UserSimpleRespVO userVO = BeanUtils.toBean(user, UserSimpleRespVO.class);
            MapUtils.findAndThen(deptMap, user.getDeptId(), dept -> userVO.setDeptName(dept.getName()));
            return userVO;
        });
    }

    default UserProfileRespVO convert(AdminUserDO user, List<RoleDO> userRoles,
                                      DeptDO dept, List<PostDO> posts, List<SocialUserDO> socialUsers) {
        UserProfileRespVO userVO = BeanUtils.toBean(user, UserProfileRespVO.class);
        userVO.setRoles(BeanUtils.toBean(userRoles, RoleSimpleRespVO.class));
        userVO.setDept(BeanUtils.toBean(dept, DeptSimpleRespVO.class));
        userVO.setPosts(BeanUtils.toBean(posts, PostSimpleRespVO.class));
        userVO.setSocialUsers(BeanUtils.toBean(socialUsers, UserProfileRespVO.SocialUser.class));
        return userVO;
    }

}
