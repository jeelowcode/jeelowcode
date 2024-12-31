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
package com.jeelowcode.module.biz.demo.test;

import com.jeelowcode.core.framework.config.aspect.enhance.criteria.DifferenceCriteriaFilterAdvicePlugin;
import com.jeelowcode.core.framework.config.aspect.enhance.criteria.IntersectionCriteriaFilterAdvicePlugin;
import com.jeelowcode.core.framework.config.aspect.enhance.criteria.OrCriteriaFilterAdvicePlugin;
import com.jeelowcode.core.framework.config.aspect.enhance.criteria.UnionCriteriaFilterAdvicePlugin;
import com.jeelowcode.core.framework.config.aspect.enhance.model.EnhanceContext;
import com.jeelowcode.framework.utils.utils.FuncBase;

import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author JX
 * @create 2024-08-19 15:03
 * @dedescription:
 */
public class TestCriterFilter {




    //合集
    @Test
    public void testCriterFilter(){
        EnhanceContext context = new EnhanceContext();
        context.setParam(01L,new HashMap<>(),new ArrayList<>());
        context.setResult(false, "01",10L,initRecords());
        TestAfterAdvicePlugin01 plugin01 = new TestAfterAdvicePlugin01();
        TestAfterAdvicePlugin02 plugin02 = new TestAfterAdvicePlugin02();
        //合集
        OrCriteriaFilterAdvicePlugin orPlugin = new OrCriteriaFilterAdvicePlugin(plugin01, plugin02);
        orPlugin.execute(context);
        System.out.println("合集:"+FuncBase.json2Str(context.getResult().getRecords()));



    }

    //差集
    @Test
    public void testCriterFilter02(){
        EnhanceContext context = new EnhanceContext();
        context.setParam(01L,new HashMap<>(),new ArrayList<>());
        context.setResult(false, "01",10L,initRecords());
        TestAfterAdvicePlugin01 plugin01 = new TestAfterAdvicePlugin01();
        TestAfterAdvicePlugin02 plugin02 = new TestAfterAdvicePlugin02();
        //差集
        DifferenceCriteriaFilterAdvicePlugin differPlugin = new DifferenceCriteriaFilterAdvicePlugin(plugin01, plugin02);
        differPlugin.execute(context);
        System.out.println("差集:"+FuncBase.json2Str(context.getResult().getRecords()));
    }

    //并集
    @Test
    public void testCriterFilter03(){
        EnhanceContext context = new EnhanceContext();
        context.setParam(01L,new HashMap<>(),new ArrayList<>());
        context.setResult(false, "01",10L,initRecords());
        TestAfterAdvicePlugin01 plugin01 = new TestAfterAdvicePlugin01();
        TestAfterAdvicePlugin02 plugin02 = new TestAfterAdvicePlugin02();
        //并集
        UnionCriteriaFilterAdvicePlugin unionPlugin = new UnionCriteriaFilterAdvicePlugin(plugin01, plugin02);
        unionPlugin.execute(context);
        System.out.println("并集:"+FuncBase.json2Str(context.getResult().getRecords()));
    }

    //交集
    @Test
    public void testCriterFilter04(){
        EnhanceContext context = new EnhanceContext();
        context.setParam(01L,new HashMap<>(),new ArrayList<>());
        context.setResult(false, "01",10L,initRecords());
        TestAfterAdvicePlugin01 plugin01 = new TestAfterAdvicePlugin01();
        TestAfterAdvicePlugin02 plugin02 = new TestAfterAdvicePlugin02();
        //交集
        IntersectionCriteriaFilterAdvicePlugin intersection = new IntersectionCriteriaFilterAdvicePlugin(plugin01, plugin02);
        intersection.execute(context);
        System.out.println("交集:"+FuncBase.json2Str(context.getResult().getRecords()));
    }


    public static List<Map<String,Object>> initRecords(){
        List<Map<String,Object>> records = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            Map<String,Object> record = new HashMap<>();
            record.put("id",i);
            record.put("name","name--"+i);
            records.add(record);
        }
        return records;
    }
}
