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
package com.jeelowcode.core.framework.config.aspect.enhancereport.plugin;

import com.jeelowcode.core.framework.config.aspect.enhancereport.model.EnhanceReportContext;

import java.util.HashMap;
import java.util.Map;


public class ReportPluginManager {

    public static Map<String, ReportBaseAdvicePlugin> plugins = new HashMap<>();


    public static ReportBaseAdvicePlugin getPlugin(String key){
        return plugins.get(key);
    }


    public static void addPlugin(String key, ReportBaseAdvicePlugin plugin){
        plugins.put(key,plugin);
    }

    public static void addPlugin(ReportBaseAdvicePlugin plugin){
        plugins.put(plugin.getClass().getName(),  plugin);
    }

    public static boolean removePlugin(String name){
        plugins.remove(name);
        return true;
    }

    public static void executePlugin(String name, EnhanceReportContext enhanceContext){
        ReportBaseAdvicePlugin plugin = plugins.get(name);
        plugin.execute(enhanceContext);
    }

    public static void executePlugin(ReportBaseAdvicePlugin plugin, EnhanceReportContext enhanceContext){
        plugin.execute(enhanceContext);
    }

}