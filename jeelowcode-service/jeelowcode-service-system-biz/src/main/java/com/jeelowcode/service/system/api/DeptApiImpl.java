package com.jeelowcode.service.system.api;

import com.jeelowcode.service.system.dto.DeptRespDTO;
import com.jeelowcode.service.system.entity.DeptDO;
import com.jeelowcode.service.system.service.IDeptService;
import com.jeelowcode.tool.framework.common.util.object.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.List;

/**
 * 部门 API 实现类
 *
 * @author 芋道源码
 */
@Service
public class DeptApiImpl implements DeptApi {

    @Resource
    private IDeptService DeptService;

    @Override
    public DeptRespDTO getDept(Long id) {
        DeptDO dept = DeptService.getDept(id);
        return BeanUtils.toBean(dept, DeptRespDTO.class);
    }

    @Override
    public List<DeptRespDTO> getDeptList(Collection<Long> ids) {
        List<DeptDO> depts = DeptService.getDeptList(ids);
        return BeanUtils.toBean(depts, DeptRespDTO.class);
    }

    @Override
    public void validateDeptList(Collection<Long> ids) {
        DeptService.validateDeptList(ids);
    }

    @Override
    public List<DeptRespDTO> getChildDeptList(Long id) {
        List<DeptDO> childDeptList = DeptService.getChildDeptList(id);
        return BeanUtils.toBean(childDeptList, DeptRespDTO.class);
    }

}
