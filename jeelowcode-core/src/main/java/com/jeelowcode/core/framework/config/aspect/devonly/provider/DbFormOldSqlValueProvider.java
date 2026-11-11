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

import com.jeelowcode.core.framework.entity.FormEntity;
import com.jeelowcode.core.framework.entity.FormFieldWebEntity;
import com.jeelowcode.core.framework.entity.FormSummaryEntity;
import com.jeelowcode.core.framework.mapper.FormFieldWebMapper;
import com.jeelowcode.core.framework.mapper.FormSummaryMapper;
import com.jeelowcode.core.framework.params.DbFormAddOrUpdateParam;
import com.jeelowcode.core.framework.service.IFormService;
import com.jeelowcode.framework.utils.component.devonly.OldSqlValueProvider;
import com.jeelowcode.framework.utils.component.devonly.SqlFieldSnapshot;
import com.jeelowcode.framework.utils.utils.SqlFieldDetector;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * 表单旧值：主表 dataSourcesConfig / whereConfig、统计 summarySql、字段格式化 formatConfig。
 * 构造与 DbFormAddOrUpdateParam 同构的 Map 后复用 SqlFieldDetector，
 * 保证旧值路径与新值路径逐字符一致（含列表 id / fieldCode 对齐键）。
 */
@Component
@RequiredArgsConstructor
public class DbFormOldSqlValueProvider implements OldSqlValueProvider {

    private final IFormService formService;
    private final FormSummaryMapper summaryMapper;
    private final FormFieldWebMapper fieldWebMapper;

    @Override
    public SqlFieldSnapshot load(Object[] args) {
        DbFormAddOrUpdateParam param = DevOnlyArgUtils.find(args, DbFormAddOrUpdateParam.class);
        if (param == null || param.getDbForm() == null || param.getDbForm().getId() == null) {
            return SqlFieldSnapshot.empty();
        }
        Long formId = param.getDbForm().getId();
        FormEntity old = formService.getById(formId);
        if (old == null) {
            return SqlFieldSnapshot.empty();
        }

        Map<String, Object> root = new LinkedHashMap<>();
        Map<String, Object> dbForm = new LinkedHashMap<>();
        dbForm.put("dataSourcesConfig", old.getDataSourcesConfig());
        dbForm.put("whereConfig", old.getWhereConfig());
        root.put("dbForm", dbForm);

        List<Map<String, Object>> summaryList = new ArrayList<>();
        for (FormSummaryEntity entity : summaryMapper.getByFormId(formId)) {
            Map<String, Object> row = new LinkedHashMap<>();
            row.put("id", entity.getId());
            row.put("fieldCode", entity.getFieldCode());
            row.put("summarySql", entity.getSummarySql());
            summaryList.add(row);
        }
        root.put("summaryList", summaryList);

        List<Map<String, Object>> webList = new ArrayList<>();
        for (FormFieldWebEntity entity : fieldWebMapper.getByFormId(formId)) {
            Map<String, Object> row = new LinkedHashMap<>();
            row.put("id", entity.getId());
            row.put("fieldCode", entity.getFieldCode());
            row.put("formatConfig", entity.getFormatConfig());
            webList.add(row);
        }
        root.put("webList", webList);

        return SqlFieldDetector.snapshot(new Object[]{root}, SqlFieldNames.ALL);
    }
}
