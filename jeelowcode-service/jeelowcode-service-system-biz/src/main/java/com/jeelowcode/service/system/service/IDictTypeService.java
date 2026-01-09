package com.jeelowcode.service.system.service;

import com.jeelowcode.service.system.controller.vo.dict.type.DictTypePageReqVO;
import com.jeelowcode.service.system.controller.vo.dict.type.DictTypeSaveReqVO;
import com.jeelowcode.tool.framework.common.pojo.PageResult;
import com.jeelowcode.service.system.entity.DictTypeDO;

import java.util.List;

/**
 * 字典类型 Service 接口
 *
 * @author 芋道源码
 */
public interface IDictTypeService {

    /**
     * 创建字典类型
     *
     * @param createReqVO 字典类型信息
     * @return 字典类型编号
     */
    Long createDictType(DictTypeSaveReqVO createReqVO);

    /**
     * 更新字典类型
     *
     * @param updateReqVO 字典类型信息
     */
    void updateDictType(DictTypeSaveReqVO updateReqVO);

    /**
     * 删除字典类型
     *
     * @param id 字典类型编号
     */
    void deleteDictType(Long id);

    /**
     * 获得字典类型分页列表
     *
     * @param pageReqVO 分页请求
     * @return 字典类型分页列表
     */
    PageResult<DictTypeDO> getDictTypePage(DictTypePageReqVO pageReqVO);

    /**
     * 获得字典类型详情
     *
     * @param id 字典类型编号
     * @return 字典类型
     */
    DictTypeDO getDictType(Long id);

    /**
     * 获得字典类型详情
     *
     * @param type 字典类型
     * @return 字典类型详情
     */
    DictTypeDO getDictType(String type);

    /**
     * 获得全部字典类型列表
     *
     * @return 字典类型列表
     */
    List<DictTypeDO> getDictTypeList();

}
