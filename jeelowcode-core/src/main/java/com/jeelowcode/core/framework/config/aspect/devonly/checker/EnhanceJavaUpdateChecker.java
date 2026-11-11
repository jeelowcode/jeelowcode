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
import com.jeelowcode.core.framework.entity.EnhanceJavaEntity;
import com.jeelowcode.core.framework.service.IEnhanceJavaService;
import com.jeelowcode.framework.utils.component.devonly.DevOnlyChecker;
import com.jeelowcode.framework.utils.constant.EnhanceConstant;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * 修改在线 Java 增强：实现原 EnhanceScriptEnvGuard.prepareUpdate 的业务校验规则，
 * 生产环境仅允许停用；不允许转为在线增强、写入脚本、修改绑定配置或重新启用。
 * Guard 现在只保留稀疏更新合并，环境判定由切面统一处理。
 */
@Component
@RequiredArgsConstructor
public class EnhanceJavaUpdateChecker implements DevOnlyChecker {

    private final IEnhanceJavaService javaService;

    @Override
    public boolean isRestricted(Object[] args) {
        EnhanceJavaEntity request = DevOnlyArgUtils.find(args, EnhanceJavaEntity.class);
        if (request == null || request.getId() == null) {
            return true;
        }
        EnhanceJavaEntity old = javaService.getById(request.getId());
        if (old == null) {
            return true;
        }
        EnhanceJavaEntity merged = BeanUtil.copyProperties(old, EnhanceJavaEntity.class);
        BeanUtil.copyProperties(request, merged, CopyOptions.create().setIgnoreNullValue(true));

        boolean oldOnline = EnhanceConstant.ENHANCE_TYPE_ONLINE_EDIT.equals(old.getJavaType());
        boolean mergedOnline = EnhanceConstant.ENHANCE_TYPE_ONLINE_EDIT.equals(merged.getJavaType());
        if (!oldOnline) {
            return mergedOnline
                    || (DevOnlyArgUtils.isNotEmpty(merged.getOnlineScript())
                    && !Objects.equals(old.getOnlineScript(), merged.getOnlineScript()));
        }

        return UpdateRestrictUtils.isRestricted(old, merged,
                old.getActiveStatus(), merged.getActiveStatus());
    }
}
