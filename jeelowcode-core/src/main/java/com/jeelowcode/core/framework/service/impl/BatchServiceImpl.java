/*
 *  Redistribution and use in source and binary forms, with or without
 *  modification, are permitted provided that the following conditions are met:
 *
 *  Redistributions of source code must retain the above copyright notice,
 *  this list of conditions and the following disclaimer.
 *  Redistributions in binary form must reproduce the above copyright
 *  notice, this list of conditions and the following disclaimer in the
 *  documentation and/or other materials provided with the distribution.
 *  Neither the name of the dreamlu.net developer nor the names of its
 *  contributors may be used to endorse or promote products derived from
 *  this software without specific prior written permission.
 *  Author：广州魔晶智能科技股份有限公司
 *  Copyright ：广州魔晶智能科技股份有限公司
 *  Website：www.mj.ink
 */
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
package com.jeelowcode.core.framework.service.impl;


import com.baomidou.mybatisplus.core.enums.SqlMethod;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.override.MybatisMapperProxy;
import com.baomidou.mybatisplus.core.toolkit.GlobalConfigUtils;
import com.baomidou.mybatisplus.extension.toolkit.AopUtils;
import com.baomidou.mybatisplus.extension.toolkit.SqlHelper;
import com.jeelowcode.core.framework.service.IBatchService;
import com.jeelowcode.core.framework.utils.Func;
import org.apache.ibatis.binding.MapperMethod;
import org.apache.ibatis.logging.Log;
import org.apache.ibatis.logging.LogFactory;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.aop.framework.AopProxyUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Proxy;
import java.util.Collection;
import java.util.function.BiConsumer;


/**
 * 通用新增
 */
@Service
public class BatchServiceImpl implements IBatchService {

    protected final Log log = LogFactory.getLog(this.getClass());
    private volatile SqlSessionFactory sqlSessionFactory;

    @Transactional(
            rollbackFor = {Exception.class}
    )
    public boolean saveBatch(Collection entityList, int batchSize, BaseMapper baseMapper,Class mapperClass,Class entityClazz) {
        if(Func.isEmpty(entityList)){
            return false;
        }
        String sqlStatement = this.getSqlStatement(SqlMethod.INSERT_ONE,mapperClass);
        return this.executeBatch(entityList, batchSize,baseMapper,entityClazz, (sqlSession, entity) -> {
            sqlSession.insert(sqlStatement, entity);
        });
    }

    @Transactional(
            rollbackFor = {Exception.class}
    )
    public boolean updateBatchById(Collection entityList, int batchSize,BaseMapper baseMapper,Class mapperClass,Class entityClazz) {
        if(Func.isEmpty(entityList)){
            return false;
        }
        String sqlStatement = this.getSqlStatement(SqlMethod.UPDATE_BY_ID,mapperClass);
        return this.executeBatch(entityList, batchSize,baseMapper,entityClazz, (sqlSession, entity) -> {
            MapperMethod.ParamMap param = new MapperMethod.ParamMap();
            param.put("et", entity);
            sqlSession.update(sqlStatement, param);
        });
    }



    protected String getSqlStatement(SqlMethod sqlMethod,Class mapperClass) {
        return SqlHelper.getSqlStatement(mapperClass, sqlMethod);
    }

    protected <E> boolean executeBatch(Collection list, int batchSize,BaseMapper baseMapper,Class clazz, BiConsumer<SqlSession, E> consumer) {
        return SqlHelper.executeBatch(this.getSqlSessionFactory(baseMapper,clazz), this.log, list, batchSize, consumer);
    }
    protected SqlSessionFactory getSqlSessionFactory(BaseMapper baseMapper,Class clazz) {
        if (this.sqlSessionFactory == null) {
            synchronized(this) {
                if (this.sqlSessionFactory == null) {
                    Object target = baseMapper;
                    if (AopUtils.isLoadSpringAop() && org.springframework.aop.support.AopUtils.isAopProxy(baseMapper)) {
                        target = AopProxyUtils.getSingletonTarget(baseMapper);
                    }

                    if (target != null) {
                        MybatisMapperProxy mybatisMapperProxy = (MybatisMapperProxy) Proxy.getInvocationHandler(target);
                        SqlSessionTemplate sqlSessionTemplate = (SqlSessionTemplate)mybatisMapperProxy.getSqlSession();
                        this.sqlSessionFactory = sqlSessionTemplate.getSqlSessionFactory();
                    } else {
                        this.sqlSessionFactory = GlobalConfigUtils.currentSessionFactory(clazz);
                    }
                }
            }
        }

        return this.sqlSessionFactory;
    }

}
