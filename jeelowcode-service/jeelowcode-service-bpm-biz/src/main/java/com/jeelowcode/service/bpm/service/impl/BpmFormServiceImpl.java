package com.jeelowcode.service.bpm.service.impl;

import cn.hutool.core.lang.Assert;
import com.jeelowcode.service.bpm.controller.vo.form.BpmFormCreateReqVO;
import com.jeelowcode.service.bpm.controller.vo.form.BpmFormPageReqVO;
import com.jeelowcode.service.bpm.controller.vo.form.BpmFormUpdateReqVO;
import com.jeelowcode.service.bpm.service.IBpmFormService;
import com.jeelowcode.tool.framework.common.pojo.PageResult;
import com.jeelowcode.service.bpm.config.convert.definition.BpmFormConvert;
import com.jeelowcode.service.bpm.entity.BpmFormDO;
import com.jeelowcode.service.bpm.mapper.BpmFormMapper;
import com.jeelowcode.service.bpm.enums.ErrorCodeConstants;
import com.jeelowcode.service.bpm.enums.definition.BpmModelFormTypeEnum;
import com.jeelowcode.service.bpm.dto.BpmFormFieldRespDTO;
import com.jeelowcode.service.bpm.dto.BpmModelMetaInfoRespDTO;
import com.jeelowcode.tool.framework.common.util.json.JsonUtils;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;
import java.util.*;

import static com.jeelowcode.service.bpm.enums.ErrorCodeConstants.FORM_NOT_EXISTS;
import static com.jeelowcode.service.bpm.enums.ErrorCodeConstants.MODEL_DEPLOY_FAIL_FORM_NOT_CONFIG;
import static com.jeelowcode.tool.framework.common.exception.util.ServiceExceptionUtil.exception;
/**
 * 动态表单 Service 实现类
 *
 * @author 风里雾里
 */
@Service
@Validated
public class BpmFormServiceImpl implements IBpmFormService {

    @Resource
    private BpmFormMapper bpmFormMapper;

    @Override
    public Long createForm(BpmFormCreateReqVO createReqVO) {
        this.checkFields(createReqVO.getFields());
        // 插入
        BpmFormDO form = BpmFormConvert.INSTANCE.convert(createReqVO);
        bpmFormMapper.insert(form);
        // 返回
        return form.getId();
    }

    @Override
    public void updateForm(BpmFormUpdateReqVO updateReqVO) {
        this.checkFields(updateReqVO.getFields());
        // 校验存在
        this.validateFormExists(updateReqVO.getId());
        // 更新
        BpmFormDO updateObj = BpmFormConvert.INSTANCE.convert(updateReqVO);
        bpmFormMapper.updateById(updateObj);
    }

    @Override
    public void deleteForm(Long id) {
        // 校验存在
        this.validateFormExists(id);
        // 删除
        bpmFormMapper.deleteById(id);
    }

    private void validateFormExists(Long id) {
        if (bpmFormMapper.selectById(id) == null) {
            throw exception(FORM_NOT_EXISTS);
        }
    }

    @Override
    public BpmFormDO getForm(Long id) {
        return bpmFormMapper.selectById(id);
    }

    @Override
    public List<BpmFormDO> getFormList() {
        return bpmFormMapper.selectList();
    }

    @Override
    public List<BpmFormDO> getFormList(Collection<Long> ids) {
        return bpmFormMapper.selectBatchIds(ids);
    }

    @Override
    public PageResult<BpmFormDO> getFormPage(BpmFormPageReqVO pageReqVO) {
        return bpmFormMapper.selectPage(pageReqVO);
    }


    @Override
    public BpmFormDO checkFormConfig(String configStr) {
        BpmModelMetaInfoRespDTO metaInfo = JsonUtils.parseObject(configStr, BpmModelMetaInfoRespDTO.class);
        if (metaInfo == null || metaInfo.getFormType() == null) {
            throw exception(MODEL_DEPLOY_FAIL_FORM_NOT_CONFIG);
        }
        // 校验表单存在
        if (Objects.equals(metaInfo.getFormType(), BpmModelFormTypeEnum.NORMAL.getType())) {
            BpmFormDO form = getForm(metaInfo.getFormId());
            if (form == null) {
                throw exception(FORM_NOT_EXISTS);
            }
            return form;
        }
        return null;
    }

    /**
     * 校验 Field，避免 field 重复
     *
     * @param fields field 数组
     */
    private void checkFields(List<String> fields) {
        if (true) { // TODO 芋艿：兼容 Vue3 工作流：因为采用了新的表单设计器，所以暂时不校验
            return;
        }
        Map<String, String> fieldMap = new HashMap<>(); // key 是 vModel，value 是 label
        for (String field : fields) {
            BpmFormFieldRespDTO fieldDTO = JsonUtils.parseObject(field, BpmFormFieldRespDTO.class);
            Assert.notNull(fieldDTO);
            String oldLabel = fieldMap.put(fieldDTO.getVModel(), fieldDTO.getLabel());
            // 如果不存在，则直接返回
            if (oldLabel == null) {
                continue;
            }
            // 如果存在，则报错
            throw exception(ErrorCodeConstants.FORM_FIELD_REPEAT, oldLabel, fieldDTO.getLabel(), fieldDTO.getVModel());
        }
    }

}
