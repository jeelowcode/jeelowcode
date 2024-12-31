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
package com.jeelowcode.core.framework.controller;

import cn.hutool.core.date.DateUtil;
import com.jeelowcode.framework.global.JeeLowCodeBaseConstant;
import com.jeelowcode.core.framework.entity.DesformEntity;
import com.jeelowcode.core.framework.params.DesFormAddOrUpdateParam;
import com.jeelowcode.core.framework.params.PageDesFormParam;
import com.jeelowcode.core.framework.service.IDesFormService;
import com.jeelowcode.core.framework.utils.Func;
import com.jeelowcode.core.framework.utils.FuncWeb;
import com.jeelowcode.framework.constants.FrameErrorCodeConstants;
import com.jeelowcode.framework.utils.adapter.IJeeLowCodeAdapter;
import com.jeelowcode.framework.utils.annotation.ApiDecryptAes;
import com.jeelowcode.framework.utils.annotation.ApiEncryptAes;
import com.jeelowcode.framework.utils.constant.JeeLowCodeConstant;
import com.jeelowcode.framework.utils.constant.JeeRedisConstants;
import com.jeelowcode.framework.utils.component.redis.JeeLowCodeRedisUtils;
import com.jeelowcode.framework.utils.enums.YNEnum;
import com.jeelowcode.framework.utils.utils.FuncBase;
import com.jeelowcode.framework.utils.model.global.BaseWebResult;
import com.jeelowcode.core.framework.params.vo.DesformEntityVo;
import com.jeelowcode.core.framework.params.vo.PageVo;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Tag(name = "低代码框架-表单设计器接口")
@RestController
@AllArgsConstructor
@RequestMapping( JeeLowCodeBaseConstant.REQUEST_URL_START+"/desform")
public class DesFormController extends BaseController {

    private final IDesFormService desFormService;

    private final JeeLowCodeRedisUtils jeeLowCodeRedisUtils;

    private final IJeeLowCodeAdapter adapter;


    @PostMapping("/unlock/{desFormId}")
    @ApiOperationSupport(order = 1)
    @Operation(tags = "自定义表单",summary = "表单设计增强-解锁")
    public BaseWebResult unlock(@PathVariable("desFormId") Long desFormId) {
        String redisKey = String.format(JeeRedisConstants.ENHANCE_DESFORM_LOCK,String.valueOf(desFormId));
        if (jeeLowCodeRedisUtils.hasKey(redisKey)) {
            jeeLowCodeRedisUtils.del(redisKey);
        }
        return BaseWebResult.success("成功");
    }

    @PreAuthorize("@ss.hasPermission('jeelowcode:desform:create')")
    @ApiDecryptAes//解密
    @PostMapping("/save")
    @ApiOperationSupport(order = 1)
    @Operation(tags = "自定义表单",summary = "表单设计器 - 保存")
    public BaseWebResult saveDesForm(@RequestBody DesFormAddOrUpdateParam param) {
        Long id = desFormService.saveDesForm(param);
        return BaseWebResult.success(id);
    }

    @PreAuthorize("@ss.hasPermission('jeelowcode:desform:update')")
    @ApiDecryptAes//解密
    @PutMapping("/update")
    @ApiOperationSupport(order = 2)
    @Operation(tags = "自定义表单",summary = "表单设计器 - 修改")
    public BaseWebResult updateDesForm(@RequestBody DesFormAddOrUpdateParam param) {
        desFormService.updateDesForm(param);
        return BaseWebResult.success("成功");
    }

    @PreAuthorize("@ss.hasPermission('jeelowcode:desform:delete')")
    @DeleteMapping("/delete")
    @ApiOperationSupport(order = 3)
    @Operation(tags = "自定义表单",summary = "表单设计器 - 删除")
    public BaseWebResult delDesForm(@RequestBody List<Long> ids) {
        desFormService.removeBatchByIds(ids);
        return BaseWebResult.success("成功");
    }

    //返回数据加密
    @ApiEncryptAes
    @GetMapping("/detail")
    @ApiOperationSupport(order = 4)
    @Operation(tags = "自定义表单",summary = "表单设计器 - 获取详情")
    public BaseWebResult getDetailDesForm(Long desFormId,String lock) {
        if (FuncBase.isNotEmpty(lock) && FuncBase.equals(lock, "true")) {
            String redisKey = String.format(JeeRedisConstants.ENHANCE_DESFORM_LOCK,String.valueOf(desFormId));
            if (jeeLowCodeRedisUtils.hasKey(redisKey)) {
                String lockMapStr = (String)jeeLowCodeRedisUtils.get(redisKey);
                Map lockMap = Func.json2Bean(lockMapStr, Map.class);
                //返回锁定信息
                return BaseWebResult.error(FrameErrorCodeConstants.FRAME_ENHANCE_ERROR,lockMap);
            }

            String userName = adapter.getOnlineUserName();
            String timeStr = DateUtil.now();
            //存储锁定信息
            Map<String,Object> lockMap=new HashMap<>();
            lockMap.put("userName",userName);
            lockMap.put("timeStr",timeStr);
            jeeLowCodeRedisUtils.set(redisKey, Func.json2Str(lockMap));//上锁
        }

        DesformEntity desformEntity = desFormService.getById(desFormId);
        return BaseWebResult.success(desformEntity);
    }

    @PostMapping("/page")
    @ApiOperationSupport(order = 6)
    @Operation(tags = "自定义表单",summary = "获取列表")
    public BaseWebResult page(PageDesFormParam param, PageVo pageVo) {

        LambdaQueryWrapper<DesformEntity> wrapper=new LambdaQueryWrapper<>();
        wrapper.eq(FuncBase.isNotEmpty(param.getId()),DesformEntity::getId,param.getId());
        wrapper.eq(FuncBase.isNotEmpty(param.getGroupDesformId()),DesformEntity::getGroupDesformId,param.getGroupDesformId());
        wrapper.eq(FuncBase.isNotEmpty(param.getIsOpen()), DesformEntity::getIsOpen, param.getIsOpen());
        wrapper.eq(FuncBase.isNotEmpty(param.getIsTemplate()), DesformEntity::getIsTemplate, param.getIsTemplate());
        wrapper.like(FuncBase.isNotEmpty(param.getDesformName()),DesformEntity::getDesformName,param.getDesformName());
        wrapper.orderByDesc(DesformEntity::getId);

        IPage<DesformEntityVo> resultList=new Page<>();
        if(Func.isEmpty(pageVo) || Func.isEmpty(pageVo.getPageSize()) || Func.equals(JeeLowCodeConstant.NOT_PAGE,pageVo.getPageSize())){
            List<DesformEntity> dataList = desFormService.list(wrapper);
            resultList.setRecords(toVoBean(dataList, DesformEntityVo.class));
            resultList.setTotal(dataList.size());
            resultList.setCurrent(1L);
            resultList.setPages(1L);
            resultList.setSize(JeeLowCodeConstant.NOT_PAGE);
        }else{
            Page page = FuncWeb.getPage(pageVo.getPageNo(), pageVo.getPageSize());
            IPage<DesformEntity> pages = desFormService.page(page, wrapper);
            resultList = toVoBean(pages, DesformEntityVo.class);
        }

        return BaseWebResult.success(resultList);
    }



    @GetMapping("/get/template")
    @ApiOperationSupport(order = 6)
    @Operation(tags = "自定义表单",summary = "获取模板")
    public BaseWebResult getTemplate() {
        LambdaQueryWrapper<DesformEntity> wrapper=new LambdaQueryWrapper<>();
        wrapper.eq(DesformEntity::getIsTemplate, YNEnum.Y.getCode());
        List<DesformEntity> templateList = desFormService.list(wrapper);
        return BaseWebResult.success(templateList);
    }

}
