package cn.iocoder.yudao.module.system.controller.admin.ip;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.lang.Assert;
import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import cn.iocoder.yudao.framework.ip.core.Area;
import cn.iocoder.yudao.framework.ip.core.utils.AreaUtils;
import cn.iocoder.yudao.framework.ip.core.utils.IPUtils;
import cn.iocoder.yudao.module.system.controller.admin.ip.vo.AreaNodeRespVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Pattern;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static cn.iocoder.yudao.framework.common.pojo.CommonResult.success;

@Tag(name = "管理后台 - 地区")
@RestController
@RequestMapping("/system/area")
@Validated
public class AreaController {

    @GetMapping("/tree")
    @Operation(tags = "地区管理",summary = "获得地区树")
    public CommonResult<List<AreaNodeRespVO>> getAreaTree() {
        Area area = AreaUtils.getArea(Area.ID_CHINA);
        Assert.notNull(area, "获取不到中国");
        return success(BeanUtils.toBean(area.getChildren(), AreaNodeRespVO.class));
    }

    @GetMapping("/get-by-ip")
    @Operation(tags = "地区管理",summary = "获得 IP 对应的地区名")
    @Parameter(name = "ip", description = "IP", required = true)
    public CommonResult<String> getAreaByIp(
            @Valid
            @Pattern(regexp = "^(?:(\\\\d{1,2}|1\\\\d{2}|2[0-4]\\\\d|25[0-5])\\\\.){3}(\\\\d{1,2}|1\\\\d{2}|2[0-4]\\\\d|25[0-5])$", message = "ip格式不正确")
            @RequestParam("ip")
            String ip) {
        // 获得城市
        Area area = IPUtils.getArea(ip);
        if (area == null) {
            return success("未知");
        }
        // 格式化返回
        return success(AreaUtils.format(area.getId()));
    }

    //------------------ jeelowcode --------------------
    @GetMapping("/tree-by-id")
    @Operation(tags = "地区管理",summary = "获得地区树")
    public CommonResult<List<AreaNodeRespVO>> getAreaTreeById(Integer pid) {
        Area area = AreaUtils.getArea(pid);
        Assert.notNull(area, "获取不到中国");

        List<AreaNodeRespVO> resultList=new ArrayList<>();
        List<Area> children = area.getChildren();
        for(Area areaTmp:children){
            AreaNodeRespVO areaVo=new AreaNodeRespVO();
            areaVo.setId(areaTmp.getId());
            areaVo.setName(areaTmp.getName());
            areaVo.setLeaf(CollectionUtil.isEmpty(areaTmp.getChildren()));
            resultList.add(areaVo);
        }
        return success(resultList);
    }

    @PostMapping("/view-parent-list")
    @Operation(tags = "地区管理",summary = "获取所有父级")
    public CommonResult<Map<Integer,List<AreaNodeRespVO>>> viewParentList(@RequestBody List<Integer> idList) {
        Map<Integer,List<AreaNodeRespVO>> resultMap=new HashMap<>();
        for(Integer id:idList){
            List<Area> areaList = AreaUtils.getAllParentList(id);

            resultMap.put(id,BeanUtils.toBean(areaList, AreaNodeRespVO.class));
        }
        return success(resultMap);
    }
}
