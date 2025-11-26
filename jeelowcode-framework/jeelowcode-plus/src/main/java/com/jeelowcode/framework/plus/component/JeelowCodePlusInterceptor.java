
package com.jeelowcode.framework.plus.component;


import com.jeelowcode.framework.plus.SqlHelper;
import com.jeelowcode.framework.utils.utils.FuncBase;
import org.apache.ibatis.executor.statement.RoutingStatementHandler;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.SystemMetaObject;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import static com.jeelowcode.framework.global.JeeLowCodeBaseConstant.BASE_PACKAGES_CODE;


@Component
@Intercepts({
        @Signature(type = StatementHandler.class,
                method = "prepare",
                args = {Connection.class, Integer.class})
})
public class JeelowCodePlusInterceptor implements Interceptor {

    private List<String> getSkipMapperList() {
        List<String> skipMapperList = new ArrayList<>();
        // 不做租户拦截的mapper包路径
        skipMapperList.add(BASE_PACKAGES_CODE + ".framework.mapper");
        // 可以添加更多需要跳过的mapper路径
        return skipMapperList;
    }

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        try {
            StatementHandler statementHandler = (StatementHandler) invocation.getTarget();

            // 获取MappedStatement
            MappedStatement ms = getMappedStatement(statementHandler);
            if(FuncBase.isEmpty(ms)){
                return invocation.proceed();
            }
            String id = ms.getId();
            List<String> skipMapperList = getSkipMapperList();
            for(String skipMapper:skipMapperList){
                if(id.startsWith(skipMapper)){
                    return invocation.proceed();
                }
            }

            if (!id.startsWith(BASE_PACKAGES_CODE)) {//直接跳过,如果不是低代码平台，则直接跳过
                return invocation.proceed();
            }
            // 获取SQL并处理
            BoundSql boundSql = statementHandler.getBoundSql();
            String originalSql = boundSql.getSql();

            // 进行SQL替换处理
            String publicSql = SqlHelper.getPublicSql(originalSql);

            // 使用反射修改SQL
            Field sqlField = BoundSql.class.getDeclaredField("sql");
            sqlField.setAccessible(true);
            sqlField.set(boundSql, publicSql);
        } catch (Exception e) {
            // 记录错误但不中断流程
            System.err.println("拦截器处理异常: " + e.getMessage());
            e.printStackTrace();
        }

        return invocation.proceed();
    }


    /**
     * 通过反射获取MappedStatement
     */
    /**
     * 通过 MetaObject 获取 MappedStatement
     */
    private MappedStatement getMappedStatement(StatementHandler handler) {
        try {
            // 处理代理对象
            if (Proxy.isProxyClass(handler.getClass())) {
                InvocationHandler invocationHandler = Proxy.getInvocationHandler(handler);
                if (invocationHandler instanceof Plugin) {
                    Field targetField = invocationHandler.getClass().getDeclaredField("target");
                    targetField.setAccessible(true);
                    handler = (StatementHandler) targetField.get(invocationHandler);
                }
            }

            // 如果是 RoutingStatementHandler，获取其 delegate
            if (handler instanceof RoutingStatementHandler) {
                Field delegateField = RoutingStatementHandler.class.getDeclaredField("delegate");
                delegateField.setAccessible(true);
                Object delegate = delegateField.get(handler);

                // 从 delegate 中获取 mappedStatement
                MetaObject delegateMeta = SystemMetaObject.forObject(delegate);
                if (delegateMeta.hasGetter("mappedStatement")) {
                    return (MappedStatement) delegateMeta.getValue("mappedStatement");
                }
            }

            return null;
        } catch (Exception e) {
            System.err.println("获取 MappedStatement 失败: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Object plugin(Object target) {
        return Interceptor.super.plugin(target);
    }

    @Override
    public void setProperties(Properties properties) {
        Interceptor.super.setProperties(properties);
    }
}