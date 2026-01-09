package com.jeelowcode.service.bpm.config.convert.task;

import com.jeelowcode.service.bpm.controller.vo.activity.BpmActivityRespVO;
import com.jeelowcode.tool.framework.common.util.date.DateUtils;
import org.flowable.engine.history.HistoricActivityInstance;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * BPM 活动 Convert
 *
 * @author 芋道源码
 */
@Mapper(uses = DateUtils.class)
public interface BpmActivityConvert {

    BpmActivityConvert INSTANCE = Mappers.getMapper(BpmActivityConvert.class);

    List<BpmActivityRespVO> convertList(List<HistoricActivityInstance> list);

    @Mappings({
            @Mapping(source = "activityId", target = "key"),
            @Mapping(source = "activityType", target = "type")
    })
    BpmActivityRespVO convert(HistoricActivityInstance bean);
}
