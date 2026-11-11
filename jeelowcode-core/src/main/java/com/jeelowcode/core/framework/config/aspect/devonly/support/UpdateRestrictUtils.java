/*
Apache License
Version 2.0, January 2004
http://www.apache.org/licenses/LICENSE-2.0
本软件受适用的国家软件著作权法（包括国际条约）和开源协议 双重保护许可。

开源协议中文释意如下：
1.JeeLowCode开源版本无任何限制，在遵循本开源协议（Apache2.0）条款下，【允许商用】使用，不会造成侵权行为。
2.允许基于本平台软件开展业务系统开发。
3.在任何情况下，您不得使用本软件开发可能被认为与【本软件竞争】的软件。

最终解释权归：http://www.jeelowcode.com
*/
package com.jeelowcode.core.framework.config.aspect.devonly.support;

import cn.hutool.core.bean.BeanUtil;
import com.jeelowcode.framework.utils.enums.YNEnum;

import java.util.Arrays;
import java.util.Map;
import java.util.Objects;

/**
 * 「生产仅允许停用」的公共判定，供 SQL 增强与在线 Java 增强共用，
 * 避免两处规则分别维护导致漏改。
 */
public final class UpdateRestrictUtils {

    private UpdateRestrictUtils() {
    }

    /**
     * @param old           库中旧对象
     * @param merged        按非空字段合并后的完整对象
     * @param oldStatus     旧 activeStatus
     * @param mergedStatus  合并后的 activeStatus
     * @return true = 受限（应拒绝）；false = 仅停用或无变化（放行）
     */
    public static boolean isRestricted(Object old, Object merged, String oldStatus, String mergedStatus) {
        Map<String, Object> oldValues = BeanUtil.beanToMap(old);
        Map<String, Object> newValues = BeanUtil.beanToMap(merged);
        for (String field : Arrays.asList("activeStatus", "updateTime", "updateUser")) {
            oldValues.remove(field);
            newValues.remove(field);
        }
        if (!oldValues.equals(newValues)) {
            return true;
        }
        boolean statusChanged = !Objects.equals(oldStatus, mergedStatus);
        return statusChanged && !YNEnum.N.getCode().equals(mergedStatus);
    }
}
