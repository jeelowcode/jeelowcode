package com.jeelowcode.core.framework.config.aspect.enhance;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import com.jeelowcode.core.framework.entity.EnhanceJavaEntity;
import com.jeelowcode.framework.exception.JeeLowCodeException;
import com.jeelowcode.framework.utils.constant.EnhanceConstant;

import java.util.Objects;

/**
 * 在线编辑(online_edit)类型增强的业务辅助方法。
 *
 * 环境隔离已统一为 DevProfileUtils + @JeeLowCodeDevOnly 切面：
 * - 是否允许编辑由 DevProfileUtils.isDevOrLocal() 判定；
 * - 新增/修改是否受限由 EnhanceJavaCreateChecker / EnhanceJavaUpdateChecker 判定；
 * - 本类只保留参数校验、稀疏更新合并与类型判断，不再持有 profile 判断。
 *
 * @author JX
 */
public final class EnhanceScriptEnvGuard {

    private EnhanceScriptEnvGuard() {
    }

    /**
     * 新增和导入共用此入口；环境校验已上移到 Controller 切面，这里只做参数校验。
     */
    public static void checkCreate(EnhanceJavaEntity entity) {
        if (entity == null) {
            throw new JeeLowCodeException("增强配置不能为空");
        }
    }

    /**
     * 按非空字段更新语义合并；不修改数据库旧对象和请求对象。
     * 环境校验已由 EnhanceJavaUpdateChecker 完成，这里只负责生成完整配置供
     * updateEnhanceJava 落库与刷新插件使用。
     */
    public static EnhanceJavaEntity prepareUpdate(EnhanceJavaEntity oldEntity, EnhanceJavaEntity request) {
        if (oldEntity == null || request == null || request.getId() == null
                || !Objects.equals(oldEntity.getId(), request.getId())) {
            throw new JeeLowCodeException("待修改的增强不存在");
        }
        EnhanceJavaEntity merged = BeanUtil.copyProperties(oldEntity, EnhanceJavaEntity.class);
        BeanUtil.copyProperties(request, merged, CopyOptions.create().setIgnoreNullValue(true));
        return merged;
    }

    public static boolean isOnline(EnhanceJavaEntity entity) {
        return entity != null && EnhanceConstant.ENHANCE_TYPE_ONLINE_EDIT.equals(entity.getJavaType());
    }
}
