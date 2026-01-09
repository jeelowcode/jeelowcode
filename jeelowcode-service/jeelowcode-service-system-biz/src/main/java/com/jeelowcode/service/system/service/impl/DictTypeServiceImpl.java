package com.jeelowcode.service.system.service.impl;

import cn.hutool.core.util.StrUtil;
import com.jeelowcode.service.system.controller.vo.dict.type.DictTypePageReqVO;
import com.jeelowcode.service.system.controller.vo.dict.type.DictTypeSaveReqVO;
import com.jeelowcode.service.system.constant.ErrorCodeConstants;
import com.jeelowcode.service.system.service.IDictDataService;
import com.jeelowcode.service.system.service.IDictTypeService;
import com.jeelowcode.tool.framework.common.exception.util.ServiceExceptionUtil;
import com.jeelowcode.tool.framework.common.pojo.PageResult;
import com.jeelowcode.tool.framework.common.util.date.LocalDateTimeUtils;
import com.jeelowcode.tool.framework.common.util.object.BeanUtils;
import com.jeelowcode.service.system.entity.DictDataDO;
import com.jeelowcode.service.system.entity.DictTypeDO;
import com.jeelowcode.service.system.mapper.DictTypeMapper;
import com.google.common.annotations.VisibleForTesting;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;

import static com.jeelowcode.tool.framework.common.exception.util.ServiceExceptionUtil.exception;

/**
 * 字典类型 Service 实现类
 *
 * @author 芋道源码
 */
@Service
public class DictTypeServiceImpl implements IDictTypeService {

    @Resource
    private IDictDataService IDictDataService;

    @Resource
    private DictTypeMapper dictTypeMapper;

    @Override
    public PageResult<DictTypeDO> getDictTypePage(DictTypePageReqVO pageReqVO) {
        return dictTypeMapper.selectPage(pageReqVO);
    }

    @Override
    public DictTypeDO getDictType(Long id) {
        return dictTypeMapper.selectById(id);
    }

    @Override
    public DictTypeDO getDictType(String type) {
        return dictTypeMapper.selectByType(type);
    }

    @Override
    public Long createDictType(DictTypeSaveReqVO createReqVO) {
        // 校验字典类型的名字的唯一性
        validateDictTypeNameUnique(null, createReqVO.getName());
        // 校验字典类型的类型的唯一性
        validateDictTypeUnique(null, createReqVO.getType());

        // 插入字典类型
        DictTypeDO dictType = BeanUtils.toBean(createReqVO, DictTypeDO.class);
        dictType.setDeletedTime(LocalDateTimeUtils.EMPTY); // 唯一索引，避免 null 值
        dictTypeMapper.insert(dictType);
        return dictType.getId();
    }

    @Override
    public void updateDictType(DictTypeSaveReqVO updateReqVO) {
        // 校验自己存在
        validateDictTypeExists(updateReqVO.getId());
        // 校验字典类型的名字的唯一性
        validateDictTypeNameUnique(updateReqVO.getId(), updateReqVO.getName());
        // 校验字典类型的类型的唯一性
        validateDictTypeUnique(updateReqVO.getId(), updateReqVO.getType());

        // 更新字典类型
        DictTypeDO updateObj = BeanUtils.toBean(updateReqVO, DictTypeDO.class);
        dictTypeMapper.updateById(updateObj);
    }

    @Override
    public void deleteDictType(Long id) {
        // 校验是否存在
        DictTypeDO dictType = validateDictTypeExists(id);

        List<DictDataDO> dictDataList = IDictDataService.getDictDataList(null, dictType.getType());
        //把子集删除
        dictDataList.stream().forEach(model->{
            IDictDataService.deleteDictData(model.getId());
        });



        // 校验是否有字典数据
        /*if (dictDataService.getDictDataCountByDictType(dictType.getType()) > 0) {
            throw exception(DICT_TYPE_HAS_CHILDREN);
        }*/
        // 删除字典类型
        dictTypeMapper.updateToDelete(id, LocalDateTime.now());
    }

    @Override
    public List<DictTypeDO> getDictTypeList() {
        return dictTypeMapper.selectList();
    }

    @VisibleForTesting
    void validateDictTypeNameUnique(Long id, String name) {
        DictTypeDO dictType = dictTypeMapper.selectByName(name);
        if (dictType == null) {
            return;
        }
        // 如果 id 为空，说明不用比较是否为相同 id 的字典类型
        if (id == null) {
            throw ServiceExceptionUtil.exception(ErrorCodeConstants.DICT_TYPE_NAME_DUPLICATE);
        }
        if (!dictType.getId().equals(id)) {
            throw ServiceExceptionUtil.exception(ErrorCodeConstants.DICT_TYPE_NAME_DUPLICATE);
        }
    }

    @VisibleForTesting
    void validateDictTypeUnique(Long id, String type) {
        if (StrUtil.isEmpty(type)) {
            return;
        }
        DictTypeDO dictType = dictTypeMapper.selectByType(type);
        if (dictType == null) {
            return;
        }
        // 如果 id 为空，说明不用比较是否为相同 id 的字典类型
        if (id == null) {
            throw ServiceExceptionUtil.exception(ErrorCodeConstants.DICT_TYPE_TYPE_DUPLICATE);
        }
        if (!dictType.getId().equals(id)) {
            throw ServiceExceptionUtil.exception(ErrorCodeConstants.DICT_TYPE_TYPE_DUPLICATE);
        }
    }

    @VisibleForTesting
    DictTypeDO validateDictTypeExists(Long id) {
        if (id == null) {
            return null;
        }
        DictTypeDO dictType = dictTypeMapper.selectById(id);
        if (dictType == null) {
            throw ServiceExceptionUtil.exception(ErrorCodeConstants.DICT_TYPE_NOT_EXISTS);
        }
        return dictType;
    }

}
