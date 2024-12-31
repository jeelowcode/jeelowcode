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
package com.jeelowcode.core.framework.service;

import cn.hutool.json.JSONObject;
import com.jeelowcode.core.framework.entity.ExcelFileDataEntity;
import com.jeelowcode.core.framework.params.SaveImportDataParam;
import com.jeelowcode.framework.utils.model.ResultDataModel;
import com.jeelowcode.framework.utils.model.ExecuteEnhanceModel;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import net.sf.jsqlparser.JSQLParserException;

import java.util.List;
import java.util.Map;

/**
 * 框架业务相关
 */
public interface IFrameService {



    /**
     * 校验表是否存在
     * @param tableName
     * @return true=存在  false = 不存在
     */
    boolean checkTable(String tableName);

    //校验数据库是否存在表
    boolean checkDbTable(String tableName);

    /**
     * 同步数据到数据库
     * @param dbFormId
     * @param syncModel
     */
    void syncDb(Long dbFormId,String syncModel) throws JSQLParserException;

    /**
     * 删除表
     * @param tableName
     */
    boolean dropTable(String tableName);


    /*------------------表相关----------------------*/

    //获取表数据 -不分页
    ResultDataModel getDataList(Long dbFormId,Map<String, Object> params);

    ResultDataModel getDataList(Long dbFormId, Page page, Map<String, Object> params);

    //获取统计数据
    ResultDataModel getDataSummaryList(Long dbFormId, Map<String, Object> params);

    //获取表详情
    ResultDataModel getDataDetail(Long dbFormId,Long id,Map<String, Object> params);

    //保存数据
    ExecuteEnhanceModel saveData(Long dbformId, Map<String,Object> params);
    //保存数据-批量新增
    List<String> saveBatchData(Long dbformId, List<JSONObject> dataMapList);

    //编辑数据
    ExecuteEnhanceModel editData(Long dbformId, Long dataId, Map<String,Object> params);

    //编辑数据-批量
    void editBatchData(Long dbformId,List<JSONObject> jsonObjectList);

    //删除数据
    ExecuteEnhanceModel delData(Long dbFormId, Map<String,Object> params);

    /*------------------Excel相关----------------------*/
    //获取导出数据
    ResultDataModel getExportDataList(Long dbFormId, Map<String, Object> params);
    //保存导入数据
    void saveImportData(Long dbformId, SaveImportDataParam param);
    /*------------------报表相关----------------------*/

    //获取数据表数据 -分页
    ResultDataModel getReportDataList(String reportCode, Page page, Map<String, Object> params);
}
