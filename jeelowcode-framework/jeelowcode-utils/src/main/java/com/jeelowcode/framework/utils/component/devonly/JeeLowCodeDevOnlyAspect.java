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
package com.jeelowcode.framework.utils.component.devonly;

import com.jeelowcode.framework.exception.JeeLowCodeException;
import com.jeelowcode.framework.utils.annotation.JeeLowCodeDevOnly;
import com.jeelowcode.framework.utils.component.properties.JeeLowCodeProperties;
import com.jeelowcode.framework.utils.enums.DevOnlyPolicy;
import com.jeelowcode.framework.utils.enums.WriteMode;
import com.jeelowcode.framework.utils.tool.spring.SpringUtils;
import com.jeelowcode.framework.utils.utils.DevProfileUtils;
import com.jeelowcode.framework.utils.utils.SqlFieldDetector;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;

import java.util.Set;

/**
 * 「仅 local/dev 允许编写 SQL / 在线脚本」的环境隔离切面。
 * dev/local 直接放行；其余环境按「新快照 -> 旧快照 -> diff -> checker」判定，
 * 只有受限写入被拒绝，运行时执行与已保存 SQL 的执行不受影响。
 */
@Aspect
@Order(Ordered.HIGHEST_PRECEDENCE + 20)
public class JeeLowCodeDevOnlyAspect {

    private static final Logger log = LoggerFactory.getLogger(JeeLowCodeDevOnlyAspect.class);

    @Around("@annotation(jeeLowCodeDevOnly)")
    public Object around(ProceedingJoinPoint joinPoint, JeeLowCodeDevOnly jeeLowCodeDevOnly) throws Throwable {
        //开关关闭且为 local/dev 时才完全放行；开关开启（默认）时所有环境都做校验。
        //正式环境即使关闭开关，isDevOrLocal() 为 false，仍会走校验。
        if (!JeeLowCodeProperties.getSqlWriteCheckEnabled() && DevProfileUtils.isDevOrLocal()) {
            return joinPoint.proceed();
        }

        Object[] args = joinPoint.getArgs() == null ? new Object[0] : joinPoint.getArgs();
        Decision decision;
        try {
            decision = decide(jeeLowCodeDevOnly, args);
        } catch (JeeLowCodeException e) {
            throw e;
        } catch (Exception e) {
            // 失败关闭：解析/查询/检查异常一律按拒绝处理，并统一包装为业务异常
            log.warn("[DevOnly] 环境隔离校验异常，按拒绝处理: {}", e.toString());
            throw new JeeLowCodeException("环境隔离校验失败，当前操作已拒绝：" + e.getMessage());
        }

        switch (decision.action) {
            case ALLOW:
                return joinPoint.proceed();
            case SANITIZE:
                return joinPoint.proceed(decision.args);
            case DENY:
            default:
                throw deny(jeeLowCodeDevOnly);
        }
    }

    /**
     * 判定逻辑单独成方法，保证 joinPoint.proceed() 在 try 之外执行，
     * 业务方法自身抛出的异常不会被误包装成「环境隔离拒绝」。
     */
    private Decision decide(JeeLowCodeDevOnly annotation, Object[] args) {
        boolean hasChecker = !DevOnlyChecker.Noop.class.equals(annotation.checker());
        boolean hasProvider = !OldSqlValueProvider.Noop.class.equals(annotation.oldValue());
        DevOnlyChecker checker = hasChecker ? SpringUtils.getBean(annotation.checker()) : null;

        // 1. 新值快照；fields 为空表示整接口受限
        SqlFieldSnapshot newSnapshot = annotation.fields().length == 0
                ? SqlFieldSnapshot.restricted()
                : SqlFieldDetector.snapshot(args, annotation.fields());

        // 2. 没有任何受限字段且未配 checker -> 放行
        if (newSnapshot.isEmpty() && !hasChecker) {
            return Decision.allow();
        }

        // 3. 旧值快照。CREATE 强制为空，避免「携带已存在 id 把新增伪装成修改」
        SqlFieldSnapshot oldSnapshot = SqlFieldSnapshot.empty();
        if (annotation.mode() == WriteMode.CREATE) {
            oldSnapshot = SqlFieldSnapshot.empty();
        } else if (hasProvider && !newSnapshot.isRestricted()) {
            oldSnapshot = SpringUtils.getBean(annotation.oldValue()).load(args);
            if (oldSnapshot == null) {
                throw new JeeLowCodeException("旧值读取失败，无法确认 SQL 是否发生变更");
            }
        }

        // 4. 仅保留「新值非空且与旧值不同」的变更
        Set<String> changed = newSnapshot.diff(oldSnapshot);
        if (changed.isEmpty() && !hasChecker) {
            return Decision.allow();
        }

        // 5. 配了 checker 时以其结果为准，不受字段比对是否命中影响
        if (hasChecker && !checker.isRestricted(args)) {
            return Decision.allow();
        }

        // 6. 命中受限
        if (annotation.policy() == DevOnlyPolicy.SANITIZE && hasChecker) {
            Object[] sanitized = args.clone();
            checker.sanitize(sanitized);
            return Decision.sanitize(sanitized);
        }
        return Decision.deny();
    }

    private JeeLowCodeException deny(JeeLowCodeDevOnly annotation) {
        String message = annotation.message();
        if (message == null || message.trim().isEmpty()) {
            if (DevProfileUtils.isDevOrLocal()) {
                message = "当前已开启 SQL 编写环境隔离校验（jeelowcode.sql-write-check-enabled=true），"
                        + "开发环境默认也不允许修改 SQL/在线脚本；如需修改请临时关闭该配置";
            } else {
                message = "当前环境" + DevProfileUtils.activeProfiles()
                        + "不允许编写 SQL/在线脚本，仅 local/dev 可用";
            }
        }
        return new JeeLowCodeException(message);
    }

    private enum Action {
        ALLOW, DENY, SANITIZE
    }

    private static final class Decision {

        private static final Decision ALLOW = new Decision(Action.ALLOW, null);
        private static final Decision DENY = new Decision(Action.DENY, null);

        private final Action action;
        private final Object[] args;

        private Decision(Action action, Object[] args) {
            this.action = action;
            this.args = args;
        }

        private static Decision allow() {
            return ALLOW;
        }

        private static Decision deny() {
            return DENY;
        }

        private static Decision sanitize(Object[] args) {
            return new Decision(Action.SANITIZE, args);
        }
    }
}
