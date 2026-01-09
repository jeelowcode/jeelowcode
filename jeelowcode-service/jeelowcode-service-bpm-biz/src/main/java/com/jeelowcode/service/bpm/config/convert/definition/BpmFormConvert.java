package com.jeelowcode.service.bpm.config.convert.definition;


import com.jeelowcode.service.bpm.controller.vo.form.BpmFormCreateReqVO;
import com.jeelowcode.service.bpm.controller.vo.form.BpmFormRespVO;
import com.jeelowcode.service.bpm.controller.vo.form.BpmFormSimpleRespVO;
import com.jeelowcode.service.bpm.controller.vo.form.BpmFormUpdateReqVO;
import com.jeelowcode.service.bpm.entity.BpmFormDO;

import com.jeelowcode.tool.framework.common.pojo.PageResult;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * 动态表单 Convert
 *
 * @author 芋艿
 */
@Mapper
public interface BpmFormConvert {

    BpmFormConvert INSTANCE = Mappers.getMapper(BpmFormConvert.class);

    BpmFormDO convert(BpmFormCreateReqVO bean);

    BpmFormDO convert(BpmFormUpdateReqVO bean);

    BpmFormRespVO convert(BpmFormDO bean);

    List<BpmFormSimpleRespVO> convertList2(List<BpmFormDO> list);

    PageResult<BpmFormRespVO> convertPage(PageResult<BpmFormDO> page);

}
