package com.jeelowcode.service.bpm.config.convert.oa;

import com.jeelowcode.service.bpm.controller.vo.oa.BpmOALeaveCreateReqVO;
import com.jeelowcode.service.bpm.controller.vo.oa.BpmOALeaveRespVO;
import com.jeelowcode.service.bpm.entity.BpmOALeaveDO;
import com.jeelowcode.tool.framework.common.pojo.PageResult;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * 请假申请 Convert
 *
 * @author 芋艿
 */
@Mapper
public interface BpmOALeaveConvert {

    BpmOALeaveConvert INSTANCE = Mappers.getMapper(BpmOALeaveConvert.class);

    BpmOALeaveDO convert(BpmOALeaveCreateReqVO bean);

    BpmOALeaveRespVO convert(BpmOALeaveDO bean);

    List<BpmOALeaveRespVO> convertList(List<BpmOALeaveDO> list);

    PageResult<BpmOALeaveRespVO> convertPage(PageResult<BpmOALeaveDO> page);

}
