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

import com.jeelowcode.core.framework.entity.ExcelFileDataEntity;
import com.jeelowcode.core.framework.entity.ExcelFileEntity;
import com.jeelowcode.core.framework.mapper.ExcelFileDataMapper;
import com.jeelowcode.core.framework.service.IExcelFileDataService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jeelowcode.core.framework.service.IExcelFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


/**
 * ExcelData相关
 */
@Service
public class ExcelFileDataServiceImpl extends ServiceImpl<ExcelFileDataMapper, ExcelFileDataEntity> implements IExcelFileDataService {

    @Autowired
    private IExcelFileService fileService;

    //删除
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delById(Long fileDataId){
        ExcelFileDataEntity fileDataEntity = baseMapper.selectById(fileDataId);
        ExcelFileEntity excelFileEntity = fileService.getById(fileDataEntity.getExcelFileId());

        //总数-1
        Integer totalNum = excelFileEntity.getTotalNum();
        ExcelFileEntity updateTotal=new ExcelFileEntity();
        updateTotal.setTotalNum(--totalNum);
        updateTotal.setId(excelFileEntity.getId());
        fileService.updateById(updateTotal);


        //删除本条记录
        baseMapper.deleteById(fileDataId);

    }


}
