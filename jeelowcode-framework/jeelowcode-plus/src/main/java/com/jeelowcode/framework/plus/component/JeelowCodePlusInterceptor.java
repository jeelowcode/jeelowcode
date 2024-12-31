
/*
Apache License
Version 2.0, January 2004
http://www.apache.org/licenses/
本软件受适用的国家软件著作权法（包括国际条约）和开源协议 双重保护许可。

开源协议中文释意如下：
1.JeeLowCode开源版本无任何限制，在遵循本开源协议（Apache2.0）条款下，允许商用使用，不会造成侵权行为。
2.允许基于本平台软件开展业务系统开发。
3.在任何情况下，您不得使用本软件开发可能被认为与本软件竞争的软件。

最终解释权归：http://www.jeelowcode.com
*/
package com.jeelowcode.framework.plus.component;



import com.jeelowcode.framework.global.JeeLowCodeBaseConstant;
import com.jeelowcode.framework.plus.SqlHelper;
import com.jeelowcode.framework.utils.utils.FuncBase;
import org.apache.ibatis.builder.StaticSqlSource;
import org.apache.ibatis.cache.CacheKey;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.*;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import static com.jeelowcode.framework.global.JeeLowCodeBaseConstant.BASE_PACKAGES_CODE;


@Component
@Intercepts({@Signature(
        type = Executor.class,
        method = "update",
        args = {MappedStatement.class, Object.class}
), @Signature(
        type = Executor.class,
        method = "query",
        args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class}
), @Signature(
        type = Executor.class,
        method = "query",
        args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class, CacheKey.class, BoundSql.class}
)})
public class JeelowCodePlusInterceptor implements Interceptor {

    private List<String> getSkipMapperList(){//todo  后面需要优化
        String SKIP_MAPPER_CODE = BASE_PACKAGES_CODE+".framework.mapper";//不做租户拦截的mapper


        List<String> skipMapperList=new ArrayList<>();
        skipMapperList.add(SKIP_MAPPER_CODE);
        return skipMapperList;
    }

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        Object target = invocation.getTarget();
        Object[] args = invocation.getArgs();
        if (!(target instanceof Executor)) {
            return invocation.proceed();
        }


        Executor executor = (Executor) target;
        Object parameter = args[1];
        boolean isUpdate = args.length == 2;
        MappedStatement ms = (MappedStatement) args[0];
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

        if (!isUpdate && ms.getSqlCommandType() == SqlCommandType.SELECT) {//查询相关
            RowBounds rowBounds = (RowBounds) args[2];
            ResultHandler resultHandler = (ResultHandler) args[3];
            BoundSql boundSql;
            if (args.length == 4) {
                boundSql = ms.getBoundSql(parameter);
            } else {
                boundSql = (BoundSql) args[5];
            }

            String publicSql = SqlHelper.getPublicSql(boundSql.getSql());
            Object additionalParametersObj = boundSql.getAdditionalParameters();
            boundSql = new BoundSql(ms.getConfiguration(), publicSql, boundSql.getParameterMappings(), boundSql.getParameterObject());
            if (FuncBase.isNotEmpty(additionalParametersObj)) {
                Map<String,Object> additionalParameters = (Map<String,Object>) additionalParametersObj;

                Iterator<Map.Entry<String, Object>> iterator = additionalParameters.entrySet().iterator();
                while (iterator.hasNext()) {
                    Map.Entry<String, Object> next = iterator.next();
                    String key = next.getKey();
                    Object value = next.getValue();
                    boundSql.setAdditionalParameter(key, value);
                }
            }

            CacheKey cacheKey = executor.createCacheKey(ms, parameter, rowBounds, boundSql);
            List<Object> query = null;
            try {
                query = executor.query(ms, parameter, rowBounds, resultHandler, cacheKey, boundSql);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return query;

        }
        if (isUpdate) {
            SqlSource sqlSource = ms.getSqlSource();
            try {
                BoundSql boundSql = sqlSource.getBoundSql(parameter);
                String publicSql = SqlHelper.getPublicSql(boundSql.getSql());
                List<ParameterMapping> parameterMappings = boundSql.getParameterMappings();
                SqlSource newSqlSource = new StaticSqlSource(ms.getConfiguration(), publicSql, parameterMappings);
                MappedStatement.Builder builder = new MappedStatement.Builder(ms.getConfiguration(), ms.getId(), newSqlSource, ms.getSqlCommandType());
                this.copyBuilder(builder, ms);
                MappedStatement build = builder.build();

                return executor.update(build, parameter);
            } catch (Exception e) {
                //e.printStackTrace();
            }
        }
        return invocation.proceed();

    }

    //复制属性
    private void copyBuilder(MappedStatement.Builder builder, MappedStatement ms) {
        builder.resource(ms.getResource());
        builder.fetchSize(ms.getFetchSize());
        builder.timeout(ms.getTimeout());
        builder.statementType(ms.getStatementType());
        builder.resultSetType(ms.getResultSetType());
        builder.cache(ms.getCache());
        builder.parameterMap(ms.getParameterMap());
        builder.resultMaps(ms.getResultMaps());
        builder.flushCacheRequired(ms.isFlushCacheRequired());
        builder.useCache(ms.isUseCache());
        builder.resultOrdered(ms.isResultOrdered());
        builder.keyGenerator(ms.getKeyGenerator());
        if (FuncBase.isNotEmpty(ms.getKeyProperties())) {
            builder.keyProperty(ms.getKeyProperties()[0]);
        }
        if (FuncBase.isNotEmpty(ms.getKeyColumns())) {
            builder.keyColumn(ms.getKeyColumns()[0]);
        }
        builder.databaseId(ms.getDatabaseId());
        builder.lang(ms.getLang());
        if (FuncBase.isNotEmpty(ms.getResultSets())) {
            builder.resultSets(ms.getResultSets()[0]);
        }

        builder.dirtySelect(ms.isDirtySelect());
    }
}