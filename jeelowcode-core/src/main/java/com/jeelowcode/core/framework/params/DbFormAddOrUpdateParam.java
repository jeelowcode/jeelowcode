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
package com.jeelowcode.core.framework.params;


import com.jeelowcode.core.framework.params.vo.*;
import com.jeelowcode.core.framework.params.vo.*;
import lombok.Data;

import java.util.List;

/**
 * 表单开发新增/修改
 */
@Data
public class DbFormAddOrUpdateParam {

    /**
     * 表单开发基本信息
     */
    private DbFormVo dbForm;

    /**
     * 字段属性
     */
    private List<DbFormFieldVo> fieldList;

    /**
     * 字典属性
     */
    private List<DbFormFieldDictVo> dictList;

    /**
     * Excel属性
     */
    private List<DbFormFieldExportVo> exportList;

    /**
     * 外键属性
     */
    private List<DbFormFieldForeignkeyVo> foreignkeyList;

    /**
     * 界面属性
     */
    private List<DbFormFieldWebVo> webList;

    /**
     * 查询配置
     */
    private List<DbFormFieldQueryVo> queryList;

    /**
     * 索引列表
     */
    private List<DbFormIndexVo> indexList;

    /**
     * 统计配置列表
     */
    private List<DbFormSummaryVo> summaryList;

    //删除的id列表
    private DbFormDeletedVo delIdVo;
}
