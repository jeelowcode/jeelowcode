package cn.iocoder.yudao.module.bpm.api.listener;

import cn.iocoder.yudao.module.bpm.api.listener.dto.BpmResultListenerRespDTO;
import org.springframework.stereotype.Service;

/**
 * @author JX
 * @create 2024-07-09 11:22
 * @dedescription:
 */

@Service
public class BpmResultListenerApiImpl implements BpmResultListenerApi{
    @Override
    public String getProcessDefinitionKey() {
        return null;
    }

    @Override
    public void onEvent(BpmResultListenerRespDTO event) {

    }
}
