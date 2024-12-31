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
package com.jeelowcode.core.framework.config.btncommand.definable;

import cn.hutool.cache.Cache;
import cn.hutool.cache.CacheUtil;
import com.jeelowcode.framework.exception.JeeLowCodeException;
import com.jeelowcode.core.framework.utils.Func;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;

@Slf4j
public class DefinablePluginManager<T> {

    //从数据库读取，放入到map
     public static Map<String,String> allDefinableMap=new HashMap<>();


    //只保留最近10个常用的
    Cache<String, DefinableButtonPlugin> cache = CacheUtil.newLRUCache(10);

    //初始化实例
    public static DefinableButtonPlugin initClazz(String clazzStr) {
        try {
            Class<?> clazz = Class.forName(clazzStr);
            DefinableButtonPlugin definableButtonPlugin = (DefinableButtonPlugin)clazz.newInstance();//实例化
            return definableButtonPlugin;
        } catch (Exception e) {
            throw new JeeLowCodeException(e.getMessage());
        }
    }


    //添加插件
    public void addPlugin(String key, DefinableButtonPlugin plugin) {
        cache.put(key, plugin);
    }

    //移除
    public void remove(String key) {
        if (cache.containsKey(key)) {
            cache.remove(key);
        }
    }

    public DefinableButtonPlugin getPlugin(String key) {
        if (cache.containsKey(key)) {
            return cache.get(key);
        }
        if(!allDefinableMap.containsKey(key)){
            log.error("自定义按钮控件不存在==》"+key);
            return null;
        }

        //没有在队列里面，则重新实例化
        String clazzUrl = allDefinableMap.get(key);

        addPlugin(key, initClazz(clazzUrl));
        return cache.get(key);
    }

    //运行插件
    public Object executePlugin(String key,T param) {
        DefinableButtonPlugin plugin = this.getPlugin(key);
        if(Func.isEmpty(plugin)){
            return null;
        }
        return plugin.execute(param);
    }


}
