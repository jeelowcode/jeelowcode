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
package com.jeelowcode.framework.utils.component.properties;

import com.jeelowcode.framework.utils.tool.AesUtil;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 低代码参数
 */
@ConfigurationProperties(prefix = "jeelowcode")
public class JeeLowCodeProperties {

    //排除表名
    private static List<String> excludeTableNames;


    private static boolean debug;

    //aes加解密key
    private static String aesKey;

    //JAVA增强生成文件地址
    private static String enhancePath;

    //---------------------------------------------------------------------------


    public static List<String> getExcludeTableNames() {
        return excludeTableNames;
    }

    public void setExcludeTableNames(List<String> excludeTableNames) {
        JeeLowCodeProperties.excludeTableNames = excludeTableNames;
    }

    public static boolean getDebug() {
        return debug;
    }

    public void setDebug(boolean debug) {
        JeeLowCodeProperties.debug = debug;
    }

    public static String getAesKey() {
        return aesKey;
    }

    public void setAesKey(String aesKey) {
        AesUtil.secretKey=aesKey;//赋值
        JeeLowCodeProperties.aesKey = aesKey;
    }

    public static String getEnhancePath() {
        return enhancePath;
    }

    public void setEnhancePath(String enhancePath) {
        JeeLowCodeProperties.enhancePath = enhancePath;
    }
}
