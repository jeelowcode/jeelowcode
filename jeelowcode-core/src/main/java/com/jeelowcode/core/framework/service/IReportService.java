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


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.jeelowcode.core.framework.entity.ReportEntity;
import com.jeelowcode.core.framework.params.PageReportParam;
import com.jeelowcode.core.framework.params.ReportAddOrUpdateParam;
import com.jeelowcode.core.framework.params.vo.ReportConfigVo;
import com.jeelowcode.core.framework.params.vo.webconfigreport.WebConfigVo;
import com.jeelowcode.framework.utils.enums.AuthTypeEnum;

import java.util.List;
import java.util.Map;

/**
 * 数据报表相关
 */
public interface IReportService extends IService<ReportEntity> {

    //获取认证类型
    AuthTypeEnum getAuthType(String reportCode);

    //根据id获取
    ReportEntity getReportEntityById(Long reportId);

    //根据code获取
    ReportEntity getReportEntityByCode(String reportCode);

    //新增
    Long saveReportConfig(ReportAddOrUpdateParam param);

    //修改
    void updateReportConfig(ReportAddOrUpdateParam param);

    //删除
    void deleteReportConfig(List<Long> reportIdList);

    //详情
    ReportConfigVo getReportConfig(Long reportId);

    //分页
    IPage<ReportEntity> getPageReportConfig(PageReportParam param, Page page);

    //前端配置
    WebConfigVo getWebConfig(String reportCode);

    //回显字典
    void dictView(Long reportId,List<Map<String, Object>> records);

    //初始化增强插件
    void initEnhancePluginManager();

    //复制
    void copy(String reportCode, String newReportCode);
}
