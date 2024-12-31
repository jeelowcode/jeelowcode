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



import com.jeelowcode.core.framework.config.btncommand.definable.DefinableButtonPlugin;
import com.jeelowcode.core.framework.entity.JeeLowCodeConfigEntity;
import com.jeelowcode.core.framework.mapper.JeeLowCodeConfigMapper;
import com.jeelowcode.core.framework.service.IJeeLowCodeConfigService;
import com.jeelowcode.core.framework.utils.Func;
import com.jeelowcode.framework.global.JeeLowCodeBaseConstant;
import com.jeelowcode.framework.utils.utils.FuncBase;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.core.type.filter.AssignableTypeFilter;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 低代码平台-框架配置
 */
@Service
public class JeeLowCodeConfigServiceImpl extends ServiceImpl<JeeLowCodeConfigMapper, JeeLowCodeConfigEntity> implements IJeeLowCodeConfigService {

    /**
     * 获取自定义按钮控件
     *
     * @return
     */
    @Override
    public Map<String, String> getBtnCommandConfig() {

        LambdaQueryWrapper<JeeLowCodeConfigEntity> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(JeeLowCodeConfigEntity::getConfigType, "btn_command");//按钮
        wrapper.select(JeeLowCodeConfigEntity::getConfigKey, JeeLowCodeConfigEntity::getConfigVal);
        List<JeeLowCodeConfigEntity> dataList = baseMapper.selectList(wrapper);
        if (Func.isEmpty(dataList)) {
            return null;
        }

        ClassPathScanningCandidateComponentProvider scanner = new ClassPathScanningCandidateComponentProvider(false);
        scanner.addIncludeFilter(new AssignableTypeFilter(DefinableButtonPlugin.class));
        Set<BeanDefinition> candidates = scanner.findCandidateComponents(JeeLowCodeBaseConstant.BASE_PACKAGES);

        // 创建类名映射
        Map<String, String> classNameMap = candidates.stream()
                .map(candidate -> candidate.getBeanClassName())
                .collect(Collectors.toMap(
                        fullClassName -> fullClassName.substring(fullClassName.lastIndexOf(".") + 1),
                        fullClassName -> fullClassName
                ));

// 使用流构建结果映射
        Map<String, String> resultMap = dataList.stream()
                .filter(entity -> classNameMap.containsKey(entity.getConfigVal()))
                .collect(Collectors.toMap(
                        entity -> entity.getConfigKey(),
                        entity -> classNameMap.get(entity.getConfigVal())
                ));
        return resultMap;
    }

    @Override
    public Map<String,String> getEnhanceCodeConfig() {
        LambdaQueryWrapper<JeeLowCodeConfigEntity> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(JeeLowCodeConfigEntity::getConfigType, "enhance_code");
        wrapper.select(JeeLowCodeConfigEntity::getConfigKey, JeeLowCodeConfigEntity::getConfigVal);
        JeeLowCodeConfigEntity jeeLowCodeConfigEntity = baseMapper.selectOne(wrapper);
        //json字符串
        String configVal = jeeLowCodeConfigEntity.getConfigVal();
        Map<String,String> enhanceCodes = FuncBase.json2Bean(configVal, HashMap.class);
        return enhanceCodes;
    }

    //获取Excel保留天数
    @Override
    public Integer getExcelFileDataDay(){
        LambdaQueryWrapper<JeeLowCodeConfigEntity> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(JeeLowCodeConfigEntity::getConfigType, "excel_file_data_day");
        wrapper.select(JeeLowCodeConfigEntity::getConfigKey, JeeLowCodeConfigEntity::getConfigVal);
        JeeLowCodeConfigEntity jeeLowCodeConfigEntity = baseMapper.selectOne(wrapper);
        String configVal = jeeLowCodeConfigEntity.getConfigVal();
        return Func.toInt(configVal);
    }

}
