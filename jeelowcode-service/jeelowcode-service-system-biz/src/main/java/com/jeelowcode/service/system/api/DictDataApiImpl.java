package com.jeelowcode.service.system.api;

import com.jeelowcode.service.system.dto.DictDataRespDTO;
import com.jeelowcode.service.system.entity.DictDataDO;
import com.jeelowcode.service.system.service.IDictDataService;
import com.jeelowcode.tool.framework.common.util.object.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Collection;

/**
 * 字典数据 API 实现类
 *
 * @author 芋道源码
 */
@Service
public class DictDataApiImpl implements DictDataApi {

    @Resource
    private IDictDataService IDictDataService;

    @Override
    public void validateDictDataList(String dictType, Collection<String> values) {
        IDictDataService.validateDictDataList(dictType, values);
    }

    @Override
    public DictDataRespDTO getDictData(String dictType, String value) {
        DictDataDO dictData = IDictDataService.getDictData(dictType, value);
        return BeanUtils.toBean(dictData, DictDataRespDTO.class);
    }

    @Override
    public DictDataRespDTO parseDictData(String dictType, String label) {
        DictDataDO dictData = IDictDataService.parseDictData(dictType, label);
        return BeanUtils.toBean(dictData, DictDataRespDTO.class);
    }

}
