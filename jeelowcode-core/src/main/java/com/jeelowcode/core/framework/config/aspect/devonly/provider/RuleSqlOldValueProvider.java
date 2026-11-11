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
package com.jeelowcode.core.framework.config.aspect.devonly.provider;

import com.jeelowcode.core.framework.config.aspect.devonly.support.DevOnlyArgUtils;
import com.jeelowcode.core.framework.config.aspect.devonly.support.SqlFieldNames;

import com.jeelowcode.core.framework.entity.FormRoleDataRuleEntity;
import com.jeelowcode.core.framework.mapper.FormRoleDataRuleMapper;
import com.jeelowcode.core.framework.params.vo.role.DbFormRoleDataRuleVo;
import com.jeelowcode.framework.utils.component.devonly.OldSqlValueProvider;
import com.jeelowcode.framework.utils.component.devonly.SqlFieldSnapshot;
import com.jeelowcode.framework.utils.utils.SqlFieldDetector;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 数据权限规则旧值：ruleSql。
 */
@Component
@RequiredArgsConstructor
public class RuleSqlOldValueProvider implements OldSqlValueProvider {

    private final FormRoleDataRuleMapper roleDataRuleMapper;

    @Override
    public SqlFieldSnapshot load(Object[] args) {
        DbFormRoleDataRuleVo request = DevOnlyArgUtils.find(args, DbFormRoleDataRuleVo.class);
        if (request == null || request.getId() == null) {
            return SqlFieldSnapshot.empty();
        }
        FormRoleDataRuleEntity old = roleDataRuleMapper.selectById(request.getId());
        if (old == null) {
            return SqlFieldSnapshot.empty();
        }
        Map<String, Object> snapshot = new LinkedHashMap<>();
        snapshot.put("ruleSql", old.getRuleSql());
        return SqlFieldDetector.snapshot(new Object[]{snapshot}, SqlFieldNames.ALL);
    }
}
