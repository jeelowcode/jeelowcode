package com.jeelowcode.service.system.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.extra.spring.SpringUtil;
import com.jeelowcode.service.system.controller.vo.permission.menu.MenuListReqVO;
import com.jeelowcode.service.system.controller.vo.permission.menu.MenuSaveVO;
import com.jeelowcode.service.system.constant.ErrorCodeConstants;
import com.jeelowcode.service.system.service.IMenuService;
import com.jeelowcode.service.system.service.IPermissionService;
import com.jeelowcode.tool.framework.common.exception.util.ServiceExceptionUtil;
import com.jeelowcode.tool.framework.common.util.object.BeanUtils;
import com.jeelowcode.service.system.entity.MenuDO;
import com.jeelowcode.service.system.mapper.MenuMapper;
import com.jeelowcode.service.system.config.redis.RedisKeyConstants;
import com.jeelowcode.service.system.enums.MenuTypeEnum;
import com.jeelowcode.service.system.service.ITenantService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.google.common.annotations.VisibleForTesting;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

import static com.jeelowcode.tool.framework.common.exception.util.ServiceExceptionUtil.exception;
import static com.jeelowcode.tool.framework.common.util.collection.CollectionUtils.convertList;
import static com.jeelowcode.service.system.entity.MenuDO.ID_ROOT;

/**
 * 菜单 Service 实现
 *
 * @author 芋道源码
 */
@Service
@Slf4j
public class MenuServiceImpl implements IMenuService {

    @Resource
    private MenuMapper menuMapper;
    @Resource
    private IPermissionService permissionService;
    @Resource
    @Lazy // 延迟，避免循环依赖报错
    private ITenantService tenantService;

    @Override
    @CacheEvict(value = RedisKeyConstants.PERMISSION_MENU_ID_LIST, key = "#createReqVO.permission",
            condition = "#createReqVO.permission != null")
    public Long createMenu(MenuSaveVO createReqVO) {
        // 校验父菜单存在
        validateParentMenu(createReqVO.getParentId(), null);
        // 校验菜单（自己）
        validateMenu(createReqVO.getParentId(), createReqVO.getName(), null);

        // 插入数据库
        MenuDO menu = BeanUtils.toBean(createReqVO, MenuDO.class);
        initMenuProperty(menu);
        menuMapper.insert(menu);
        // 返回
        return menu.getId();
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public List<Long> createBatchMenu(List<MenuSaveVO> volist) {
        MenuServiceImpl proxyService = SpringUtil.getBean(MenuServiceImpl.class);

        List<Long> idList=new ArrayList<>();
        for(MenuSaveVO createReqVO:volist){
            Long id = proxyService.createMenu(createReqVO);
            idList.add(id);
        }

        // 返回
        return idList;
    }

    @Override
    @CacheEvict(value = RedisKeyConstants.PERMISSION_MENU_ID_LIST,
            allEntries = true) // allEntries 清空所有缓存，因为 permission 如果变更，涉及到新老两个 permission。直接清理，简单有效
    public void updateMenu(MenuSaveVO updateReqVO) {
        // 校验更新的菜单是否存在
        if (menuMapper.selectById(updateReqVO.getId()) == null) {
            throw ServiceExceptionUtil.exception(ErrorCodeConstants.MENU_NOT_EXISTS);
        }
        // 校验父菜单存在
        validateParentMenu(updateReqVO.getParentId(), updateReqVO.getId());
        // 校验菜单（自己）
        validateMenu(updateReqVO.getParentId(), updateReqVO.getName(), updateReqVO.getId());

        // 更新到数据库
        MenuDO updateObj = BeanUtils.toBean(updateReqVO, MenuDO.class);
        initMenuProperty(updateObj);
        menuMapper.updateById(updateObj);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @CacheEvict(value = RedisKeyConstants.PERMISSION_MENU_ID_LIST,
            allEntries = true) // allEntries 清空所有缓存，因为此时不知道 id 对应的 permission 是多少。直接清理，简单有效
    public void deleteMenu(Long id,String type) {
        if(ObjectUtil.isNotEmpty(type) && ObjectUtil.equal(type,"2")){
            // 标记删除
            menuMapper.deleteById(id);
            // 删除授予给角色的权限
            permissionService.processMenuDeleted(id);

            LambdaQueryWrapper<MenuDO> wrapper=new LambdaQueryWrapper<>();
            wrapper.eq(MenuDO::getParentId,id);
            List<MenuDO> menuDOList = menuMapper.selectList(wrapper);
            if(ObjectUtil.isNotEmpty(menuDOList)){//有下级
                for(MenuDO menuDO:menuDOList){
                    LambdaQueryWrapper<MenuDO> subWrapper=new LambdaQueryWrapper<>();
                    subWrapper.eq(MenuDO::getParentId,menuDO.getId());
                    List<MenuDO> subMenuDOList = menuMapper.selectList(subWrapper);
                    if(ObjectUtil.isNotEmpty(subMenuDOList)){//有下级
                        for(MenuDO subMenuDO:subMenuDOList){
                            // 标记删除
                            menuMapper.deleteById(subMenuDO.getId());
                            // 删除授予给角色的权限
                            permissionService.processMenuDeleted(subMenuDO.getId());
                        }
                    }


                    // 标记删除
                    menuMapper.deleteById(menuDO.getId());
                    // 删除授予给角色的权限
                    permissionService.processMenuDeleted(menuDO.getId());
                }
            }
            return;
        }


        // 校验是否还有子菜单
        if (menuMapper.selectCountByParentId(id) > 0) {
            throw ServiceExceptionUtil.exception(ErrorCodeConstants.MENU_EXISTS_CHILDREN);
        }
        // 校验删除的菜单是否存在
        if (menuMapper.selectById(id) == null) {
            throw ServiceExceptionUtil.exception(ErrorCodeConstants.MENU_NOT_EXISTS);
        }
        // 标记删除
        menuMapper.deleteById(id);
        // 删除授予给角色的权限
        permissionService.processMenuDeleted(id);
    }

    @Override
    public List<MenuDO> getMenuList() {
        return menuMapper.selectList();
    }

    @Override
    public List<MenuDO> getMenuListByTenant(MenuListReqVO reqVO) {
        List<MenuDO> menus = getMenuList(reqVO);
        // 开启多租户的情况下，需要过滤掉未开通的菜单
        tenantService.handleTenantMenu(menuIds -> menus.removeIf(menu -> !CollUtil.contains(menuIds, menu.getId())));
        return menus;
    }

    @Override
    public List<MenuDO> getMenuList(MenuListReqVO reqVO) {
        return menuMapper.selectList(reqVO);
    }

    @Override
    @Cacheable(value = RedisKeyConstants.PERMISSION_MENU_ID_LIST, key = "#permission")
    public List<Long> getMenuIdListByPermissionFromCache(String permission) {
        List<MenuDO> menus = menuMapper.selectListByPermission(permission);
        return convertList(menus, MenuDO::getId);
    }

    @Override
    public MenuDO getMenu(Long id) {
        return menuMapper.selectById(id);
    }

    @Override
    public List<MenuDO> getMenuList(Collection<Long> ids) {
        return menuMapper.selectBatchIds(ids);
    }

    //获取所有按钮权限
    @Override
    public Set<String> getAllButtonPermissionSets(){
        LambdaQueryWrapper<MenuDO> menuWrapper=new LambdaQueryWrapper<>();
        menuWrapper.eq(MenuDO::getType,3);//按钮
        menuWrapper.isNotNull(MenuDO::getPermission);//权限不为空

        return menuMapper.selectList(menuWrapper).stream()
                .map(MenuDO::getPermission)
                .collect(Collectors.toSet());
    }

    /**
     * 校验父菜单是否合法
     * <p>
     * 1. 不能设置自己为父菜单
     * 2. 父菜单不存在
     * 3. 父菜单必须是 {@link MenuTypeEnum#MENU} 菜单类型
     *
     * @param parentId 父菜单编号
     * @param childId  当前菜单编号
     */
    @VisibleForTesting
    void validateParentMenu(Long parentId, Long childId) {
        if (parentId == null || ID_ROOT.equals(parentId)) {
            return;
        }
        // 不能设置自己为父菜单
        if (parentId.equals(childId)) {
            throw ServiceExceptionUtil.exception(ErrorCodeConstants.MENU_PARENT_ERROR);
        }
        MenuDO menu = menuMapper.selectById(parentId);
        // 父菜单不存在
        if (menu == null) {
            throw ServiceExceptionUtil.exception(ErrorCodeConstants.MENU_PARENT_NOT_EXISTS);
        }
        // 父菜单必须是目录或者菜单类型
        if (!MenuTypeEnum.DIR.getType().equals(menu.getType())
                && !MenuTypeEnum.MENU.getType().equals(menu.getType())) {
            throw ServiceExceptionUtil.exception(ErrorCodeConstants.MENU_PARENT_NOT_DIR_OR_MENU);
        }
    }

    /**
     * 校验菜单是否合法
     * <p>
     * 1. 校验相同父菜单编号下，是否存在相同的菜单名
     *
     * @param name     菜单名字
     * @param parentId 父菜单编号
     * @param id       菜单编号
     */
    @VisibleForTesting
    void validateMenu(Long parentId, String name, Long id) {
        MenuDO menu = menuMapper.selectByParentIdAndName(parentId, name);
        if (menu == null) {
            return;
        }
        // 如果 id 为空，说明不用比较是否为相同 id 的菜单
        if (id == null) {
            throw ServiceExceptionUtil.exception(ErrorCodeConstants.MENU_NAME_DUPLICATE);
        }
        if (!menu.getId().equals(id)) {
            throw ServiceExceptionUtil.exception(ErrorCodeConstants.MENU_NAME_DUPLICATE);
        }
    }

    /**
     * 初始化菜单的通用属性。
     * <p>
     * 例如说，只有目录或者菜单类型的菜单，才设置 icon
     *
     * @param menu 菜单
     */
    private void initMenuProperty(MenuDO menu) {
        // 菜单为按钮类型时，无需 component、icon、path 属性，进行置空
        if (MenuTypeEnum.BUTTON.getType().equals(menu.getType())) {
            menu.setComponent("");
            menu.setComponentName("");
            menu.setIcon("");
            menu.setPath("");
        }
    }

}
