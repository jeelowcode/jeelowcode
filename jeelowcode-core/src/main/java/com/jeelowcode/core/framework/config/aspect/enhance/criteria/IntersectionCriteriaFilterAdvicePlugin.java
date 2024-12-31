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
package com.jeelowcode.core.framework.config.aspect.enhance.criteria;

import com.jeelowcode.core.framework.config.aspect.enhance.model.EnhanceContext;
import com.jeelowcode.core.framework.config.aspect.enhance.model.EnhanceResult;
import com.jeelowcode.core.framework.config.aspect.enhance.plugin.AfterAdvicePlugin;
import org.apache.commons.collections4.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author JX
 * @create 2024-08-19 14:46
 * @dedescription: 交集
 */
public class IntersectionCriteriaFilterAdvicePlugin extends BaseCriterFilterAdvicePlugin implements AfterAdvicePlugin {

    public IntersectionCriteriaFilterAdvicePlugin(AfterAdvicePlugin leftCriterFilter, AfterAdvicePlugin rightCriterFilter) {
        super(leftCriterFilter,rightCriterFilter);
    }

    @Override
    public void execute(EnhanceContext enhanceContext) {
        List<Map<String,Object>> intersection = new ArrayList<>();
        EnhanceContext leftEnhanceContext = enhanceContext.clone();
        EnhanceContext rightEnhanceContext = enhanceContext.clone();
        //执行增强
        leftCriterFilter.execute(leftEnhanceContext);
        rightCriterFilter.execute(rightEnhanceContext);
        //合并结果
        EnhanceResult leftResult = leftEnhanceContext.getResult();
        EnhanceResult rightResult = rightEnhanceContext.getResult();
        List<Map<String, Object>> leftRecords = leftResult.getRecords();
        List<Map<String, Object>> rightRecords = rightResult.getRecords();
        intersection = (List<Map<String, Object>>) CollectionUtils.intersection(leftRecords, rightRecords);

        enhanceContext.getResult().setRecords(intersection);
    }
}
