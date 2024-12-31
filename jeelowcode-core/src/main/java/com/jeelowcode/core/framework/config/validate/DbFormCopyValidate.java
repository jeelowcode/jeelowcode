/*
Apache License
Version 2.0, January 2004
http://www.apache.org/licenses/
本软件受适用的国家软件著作权法（包括国际条约）和开源协议 双重保护许可。

开源协议中文释意如下：
1.JeeLowCode开源版本无任何限制，在遵循本开源协议（Apache2.0）条款下，【允许商用】使用，不会造成侵权行为。
2.允许基于本平台软件开展业务系统开发。
3.在任何情况下，您不得使用本软件开发可能被认为与【本软件竞争】的软件。

最终解释权归：http://www.jeelowcode.com
*/
package com.jeelowcode.core.framework.config.validate;

import com.jeelowcode.core.framework.service.IFrameService;
import com.jeelowcode.core.framework.utils.Func;
import com.jeelowcode.core.framework.utils.FuncWeb;
import com.jeelowcode.framework.exception.JeeLowCodeException;
import com.jeelowcode.framework.utils.adapter.IJeelowCodeValidate;
import com.jeelowcode.framework.utils.utils.FuncBase;
import com.jeelowcode.framework.utils.utils.JeeLowCodeUtils;
import com.jeelowcode.framework.utils.component.properties.JeeLowCodeProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;
import java.util.StringJoiner;

/**
 * 校验表名是否合法
 */
@Component
public class DbFormCopyValidate implements IJeelowCodeValidate {

    @Lazy
    @Autowired
    private IFrameService frameService;

    @Override
    public void validate(HttpServletRequest req) throws JeeLowCodeException {
        Map<String, Object> parameterMap = FuncWeb.getParameterMap(req);
        String tableName = JeeLowCodeUtils.getMap2Str(parameterMap, "tableName");

        if (FuncBase.isEmpty(tableName)) {
            throw new JeeLowCodeException("表名不允许为空");
        }

        if (!Func.checkTableName(tableName)) {
            StringJoiner joiner = new StringJoiner(",");
            JeeLowCodeProperties.getExcludeTableNames().forEach(joiner::add);
            String errorMsg = joiner.toString();
            throw new JeeLowCodeException(String.format("表名称不允许以【%s】开头", errorMsg));
        }
        //校验表名是否重复
        if (frameService.checkTable(tableName)) {
            String errorStr = String.format("数据库表【%s】已存在", tableName);
            throw new JeeLowCodeException(errorStr);
        }

    }
}
