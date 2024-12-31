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
package com.jeelowcode.module.biz.service.impl;

import com.jeelowcode.core.framework.service.IFrameSqlService;
import com.jeelowcode.framework.plus.SqlHelper;
import com.jeelowcode.framework.plus.build.buildmodel.wrapper.SqlInfoQueryWrapper;
import com.jeelowcode.module.biz.mapper.DemoMapper;
import com.jeelowcode.module.biz.service.IDemoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;


/**
 * demo
 */
@Slf4j
@Service
public class DemoServiceImpl implements IDemoService {

    @Autowired
    private DemoMapper demoMapper;

    @Autowired
    private IFrameSqlService sqlService;


    @Override
    public List<Map<String, Object>> getDemoData(){
        SqlInfoQueryWrapper.Wrapper wrapper = SqlHelper.getQueryWrapper();
        wrapper.setTableName("tbl_lin_class");
        List<Map<String, Object>> dataMapList = sqlService.getDataListByPlus(wrapper);
        return dataMapList;
    }

    @Async("asyncPoolTaskExecutor")
    @Override
    public void testAsync(){
        log.info("哈哈哈");
    }
}
