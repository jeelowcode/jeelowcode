/*
Apache License
Version 2.0, January 2004
http://www.apache.org/licenses/LICENSE-2.0
本软件受适用的国家软件著作权法（包括国际条约）和开源协议 双重保护许可。

开源协议中文释意如下：
1.JeeLowCode开源版本无任何限制，在遵循本开源协议（Apache2.0）条款下，【允许商用】使用，不会造成侵权行为。
2.允许基于本平台软件开展业务系统开发。
3.在任何情况下，您不得使用本软件开发可能被认为与【本软件竞争】的软件。

最终解释权归：http://www.jeelowcode.com
*/
package com.jeelowcode.framework.utils.utils;

import com.jeelowcode.framework.utils.constant.JeeLowCodeConstant;
import com.jeelowcode.framework.utils.tool.spring.SpringUtils;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

/**
 * 环境判定：由 EnhanceScriptEnvGuard.onlineScriptEnabled() 整段原样下沉。
 *
 * 语义固定：拿不到 profile 默认拒绝；DENY_PROFILES 优先于 ALLOW_PROFILES；
 * 其余环境默认拒绝。dev,prod 同时激活时按 DENY 优先拒绝。
 */
public final class DevProfileUtils {

    private static final Set<String> ALLOW_PROFILES = Collections.unmodifiableSet(
            new HashSet<>(Arrays.asList(
                    JeeLowCodeConstant.APPLICATION_LOCAL,
                    JeeLowCodeConstant.APPLICATION_DEV
            )));

    private static final Set<String> DENY_PROFILES = Collections.unmodifiableSet(
            new HashSet<>(Arrays.asList(
                    "prod", "production", "pre", "uat", "test", "staging", "release"
            )));

    private DevProfileUtils() {
    }

    /**
     * 当前环境是否允许编写 SQL / 在线脚本。
     */
    public static boolean isDevOrLocal() {
        String[] activeProfiles = SpringUtils.getActiveProfiles();
        if (activeProfiles == null || activeProfiles.length == 0) {
            return false;
        }
        for (String profile : activeProfiles) {
            if (DENY_PROFILES.contains(normalize(profile))) {
                return false;
            }
        }
        for (String profile : activeProfiles) {
            if (ALLOW_PROFILES.contains(normalize(profile))) {
                return true;
            }
        }
        return false;
    }

    /**
     * 当前激活环境，用于日志与异常提示。
     */
    public static String activeProfiles() {
        String[] activeProfiles = SpringUtils.getActiveProfiles();
        if (activeProfiles == null || activeProfiles.length == 0) {
            return "<未配置>";
        }
        return String.join(",", activeProfiles);
    }

    private static String normalize(String profile) {
        return profile == null ? "" : profile.trim().toLowerCase(Locale.ROOT);
    }
}
