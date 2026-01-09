package com.jeelowcode.service.system.service;

import java.util.List;
import java.util.Set;

/**
 * 低代码 权限过滤
 */
public interface IJeeLowCodePermissionService {
    /**
     * 校验是否有权限
     * @param userId
     * @param roleIds
     * @param permission
     * @return true=有权限  false =无权限
     */
    boolean hasAnyPermission(Long userId, Set<Long> roleIds, String permission);

    /**
     * 转为list
     * @param permission
     * @return
     */
    List<String> permission2List(String permission);
}
