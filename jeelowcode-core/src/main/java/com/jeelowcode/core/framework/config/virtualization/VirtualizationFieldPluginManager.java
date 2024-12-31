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
package com.jeelowcode.core.framework.config.virtualization;

import cn.hutool.cache.Cache;
import cn.hutool.cache.CacheUtil;
import com.jeelowcode.core.framework.utils.Func;
import com.jeelowcode.framework.exception.JeeLowCodeException;
import com.jeelowcode.framework.utils.tool.spring.SpringUtils;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;

/**
 * 虚拟化字段 插件管理器
 */
@Slf4j
public class VirtualizationFieldPluginManager {

    //只保留最近10个常用的
    static Cache<String, VirtualizationFieldPlugin> cache = CacheUtil.newLRUCache(10);

    //初始化实例
    public static VirtualizationFieldPlugin initClazz(String clazzStr) {
        try {
            if(clazzStr.contains(".")){//全路径
                Class<?> clazz = Class.forName(clazzStr);
                VirtualizationFieldPlugin plugin = (VirtualizationFieldPlugin)clazz.newInstance();//实例化
                return plugin;
            }else{
                VirtualizationFieldPlugin plugin = (VirtualizationFieldPlugin)SpringUtils.getBean(clazzStr);
                return plugin;
            }

        } catch (Exception e) {
            throw new JeeLowCodeException(e.getMessage());
        }
    }


    //添加插件
    private static void addPlugin(String key, VirtualizationFieldPlugin plugin) {
        cache.put(key, plugin);
    }


    private static VirtualizationFieldPlugin getPlugin(String key) {
        if (cache.containsKey(key)) {
            return cache.get(key);
        }

        //没有在队列里面，则重新实例化
        addPlugin(key, initClazz(key));
        return cache.get(key);
    }

    //运行插件
    public static String executePlugin(String key,Map<String,Object> dataMap) {
        VirtualizationFieldPlugin plugin = getPlugin(key);
        if(Func.isEmpty(plugin)){
            return null;
        }
        return plugin.execute(dataMap);
    }


}
