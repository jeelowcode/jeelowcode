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
package com.jeelowcode.core.framework.config.aspect.devonly.checker;

import com.jeelowcode.core.framework.config.aspect.devonly.support.DevOnlyArgUtils;
import com.jeelowcode.core.framework.config.aspect.devonly.support.UpdateRestrictUtils;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import com.jeelowcode.core.framework.entity.EnhanceSqlEntity;
import com.jeelowcode.core.framework.service.IEnhanceSqlService;
import com.jeelowcode.framework.utils.component.devonly.DevOnlyChecker;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * 修改 SQL 增强：生产环境仅允许停用（Y 改为 N）。
 * 按评审决定，不允许启用、不允许修改 executeSql 或其它配置。
 * 采用全字段比较，避免「只传 id + activeStatus 而不带 executeSql」绕过字段级比对后
 * 重新注册并执行已存在的 SQL 增强（EnhanceSqlServiceImpl.updateEnhanceSql:72-76）。
 */
@Component
@RequiredArgsConstructor
public class EnhanceSqlUpdateChecker implements DevOnlyChecker {

    private final IEnhanceSqlService sqlService;

    @Override
    public boolean isRestricted(Object[] args) {
        EnhanceSqlEntity request = DevOnlyArgUtils.find(args, EnhanceSqlEntity.class);
        if (request == null || request.getId() == null) {
            return true;
        }
        EnhanceSqlEntity old = sqlService.getById(request.getId());
        if (old == null) {
            return true;
        }
        EnhanceSqlEntity merged = BeanUtil.copyProperties(old, EnhanceSqlEntity.class);
        BeanUtil.copyProperties(request, merged, CopyOptions.create().setIgnoreNullValue(true));

        return UpdateRestrictUtils.isRestricted(old, merged,
                old.getActiveStatus(), merged.getActiveStatus());
    }
}
