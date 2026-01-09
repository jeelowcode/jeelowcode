package com.jeelowcode.service.system.service;

import com.jeelowcode.service.system.controller.vo.sms.channel.SmsChannelPageReqVO;
import com.jeelowcode.service.system.controller.vo.sms.channel.SmsChannelSaveReqVO;
import com.jeelowcode.service.system.config.framework.sms.core.client.SmsClient;
import com.jeelowcode.tool.framework.common.pojo.PageResult;
import com.jeelowcode.service.system.entity.SmsChannelDO;

import javax.validation.Valid;
import java.util.List;

/**
 * 短信渠道 Service 接口
 *
 * @author zzf
 * @since 2021/1/25 9:24
 */
public interface ISmsChannelService {

    /**
     * 创建短信渠道
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createSmsChannel(@Valid SmsChannelSaveReqVO createReqVO);

    /**
     * 更新短信渠道
     *
     * @param updateReqVO 更新信息
     */
    void updateSmsChannel(@Valid SmsChannelSaveReqVO updateReqVO);

    /**
     * 删除短信渠道
     *
     * @param id 编号
     */
    void deleteSmsChannel(Long id);

    /**
     * 获得短信渠道
     *
     * @param id 编号
     * @return 短信渠道
     */
    SmsChannelDO getSmsChannel(Long id);

    /**
     * 获得所有短信渠道列表
     *
     * @return 短信渠道列表
     */
    List<SmsChannelDO> getSmsChannelList();

    /**
     * 获得短信渠道分页
     *
     * @param pageReqVO 分页查询
     * @return 短信渠道分页
     */
    PageResult<SmsChannelDO> getSmsChannelPage(SmsChannelPageReqVO pageReqVO);

    /**
     * 获得短信客户端
     *
     * @param id 编号
     * @return 短信客户端
     */
    SmsClient getSmsClient(Long id);

    /**
     * 获得短信客户端
     *
     * @param code 编码
     * @return 短信客户端
     */
    SmsClient getSmsClient(String code);

}
