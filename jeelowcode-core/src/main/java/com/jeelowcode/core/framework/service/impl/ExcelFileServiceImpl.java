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
package com.jeelowcode.core.framework.service.impl;

import cn.hutool.extra.spring.SpringUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jeelowcode.core.framework.config.btncommand.param.ButtonParamImport;
import com.jeelowcode.core.framework.entity.ExcelFileDataEntity;
import com.jeelowcode.core.framework.entity.ExcelFileEntity;
import com.jeelowcode.core.framework.mapper.ExcelFileMapper;
import com.jeelowcode.core.framework.params.model.ExcelImportResultModel;
import com.jeelowcode.core.framework.service.*;
import com.jeelowcode.core.framework.utils.Func;
import com.jeelowcode.framework.plus.SqlHelper;
import com.jeelowcode.framework.plus.build.buildmodel.wrapper.SqlInfoDeleteWrapper;
import com.jeelowcode.framework.utils.adapter.IJeeLowCodeAdapter;
import com.jeelowcode.framework.utils.constant.JeeLowCodeConstant;
import com.jeelowcode.framework.utils.enums.YNEnum;
import com.jeelowcode.framework.utils.tool.spring.SpringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


/**
 * Excel文件相关
 */
@Service
public class ExcelFileServiceImpl extends ServiceImpl<ExcelFileMapper, ExcelFileEntity> implements IExcelFileService {

    @Autowired
    private IExcelFileDataService excelFileDataService;

    @Autowired
    private IFormService formService;

    @Autowired
    private IFrameSqlService sqlService;

    @Autowired
    private IJeeLowCodeAdapter jeeLowCodeAdapter;

    //保存Excel数据
    @Async("asyncPoolTaskExecutor")
    @Override
    public void saveExcelSync( long fileId,ButtonParamImport param) {
        ServletRequestAttributes sra = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        IExcelFileService proxyService = SpringUtil.getBean(IExcelFileService.class);
        proxyService.saveExcel(sra,fileId, param);
    }

    @Transactional(rollbackFor = Exception.class)
    public ExcelImportResultModel saveExcel(ServletRequestAttributes sra,long fileId, ButtonParamImport param) {
        RequestContextHolder.setRequestAttributes(sra, true);

        Long tenantId = jeeLowCodeAdapter.getTenantId();
        Long onlineUserId = jeeLowCodeAdapter.getOnlineUserId();
        Long onlineUserDeptId = jeeLowCodeAdapter.getOnlineUserDeptId();
        LocalDateTime now = LocalDateTime.now();

        Long dbFormId = param.getDbFormId();
        List<Map<String, Object>> dataMapList = param.getDataMapList();
        String fileName = param.getFileName();


        //1.存入标题
        ExcelFileEntity fileEntity = new ExcelFileEntity();
        fileEntity.setId(fileId);
        fileEntity.setDbformId(dbFormId);
        fileEntity.setFileName(fileName);
        fileEntity.setImportState("0");//未导入
        fileEntity.setTotalNum(dataMapList.size());//总条数
        this.save(fileEntity);
        List<ExcelFileDataEntity> entityList = dataMapList.stream()
                .map(dataMap -> {
                    Integer stepSort = Func.getMap2Int(dataMap, JeeLowCodeConstant.EXCEL_IMPORT_STEP);
                    Long id = Func.getMap2Long(dataMap, JeeLowCodeConstant.EXCEL_IMPORT_ID);

                    //移除
                    dataMap.remove(JeeLowCodeConstant.EXCEL_IMPORT_STEP);
                    dataMap.remove(JeeLowCodeConstant.EXCEL_IMPORT_ID);

                    ExcelFileDataEntity entity = new ExcelFileDataEntity();
                    dataMap.put("id", id);//设置id

                    entity.setId(id);
                    entity.setExcelFileId(fileId);
                    entity.setSort(stepSort);
                    entity.setDataJson(Func.json2Str(dataMap));
                    entity.setHandleState(YNEnum.N.getCode()); // 未处理
                    entity.setDataId(id);//数据id
                    //赋予默认值
                    entity.setTenantId(tenantId);
                    entity.setCreateUser(onlineUserId);
                    entity.setCreateDept(onlineUserDeptId);
                    entity.setCreateTime(now);
                    return entity;
                })
                .collect(Collectors.toList());

        excelFileDataService.saveBatch(entityList);

        ExcelImportResultModel resultModel = new ExcelImportResultModel();
        resultModel.setBatchCode(Func.toStr(fileId));
        resultModel.setDbFormId(dbFormId);
        resultModel.setTotalCou(dataMapList.size());
        resultModel.setFileId(fileId);
        return resultModel;
    }


    //撤回
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void rollback(Long fileId) {
        ExcelFileEntity excelFileEntity = this.getById(fileId);
        Long dbformId = excelFileEntity.getDbformId();
        String tableName = formService.getTableName(dbformId);

        ExcelFileEntity updateFileEntity = new ExcelFileEntity();
        updateFileEntity.setImportState("-1");//已撤销
        updateFileEntity.setId(excelFileEntity.getId());
        this.updateById(updateFileEntity);

        LambdaQueryWrapper<ExcelFileDataEntity> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(ExcelFileDataEntity::getExcelFileId, fileId);
        List<ExcelFileDataEntity> fileDataList = excelFileDataService.list(wrapper);
        if (Func.isEmpty(fileDataList)) {
            return;
        }
        //删除对应的业务表
        fileDataList.stream().forEach(excelFileDataEntity -> {
            Long dataId = excelFileDataEntity.getDataId();
            sqlService.baseDelRealData(tableName, dataId);
        });

    }
}
