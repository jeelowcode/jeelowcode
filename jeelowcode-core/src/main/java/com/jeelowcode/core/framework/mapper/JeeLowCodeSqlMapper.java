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
package com.jeelowcode.core.framework.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;

/**
 * 低代码平台 sql执行类
 */
public interface JeeLowCodeSqlMapper {

    //运行ddl语句
    @Update("${ddlSql}")
    void executeDDL(@Param("ddlSql") String ddlSql);

    //获取数据 - 多个
    @Select("${jeeLowCodeSelectSql}")
    List<Map<String, Object>> selectData(@Param("jeeLowCodeSelectSql") String jeeLowCodeSelectSql,@Param("ew") Map<String,Object> ew);

    //分页
    @Select("${jeeLowCodeSelectSql}")
    IPage<Map<String, Object>> selectPageData(IPage page,@Param("jeeLowCodeSelectSql") String jeeLowCodeSelectSql,@Param("ew") Map<String,Object> ew);

    //新增数据
    @Insert("${jeeLowCodeInsertSql}")
    void insertData(@Param("jeeLowCodeInsertSql")  String jeeLowCodeInsertSql,@Param("ew") Map<String,Object> ew);

    //修改数据
    @Update("${jeeLowCodeUpdateSql}")
    void updateData(@Param("jeeLowCodeUpdateSql") String jeeLowCodeUpdateSql,@Param("ew") Map<String,Object> ew);

    //删除数据
    @Delete("${jeeLowCodeDeleteSql}")
    void deleteData(@Param("jeeLowCodeDeleteSql") String jeeLowCodeDeleteSql,@Param("ew") Map<String,Object> ew);
}
