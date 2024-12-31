package cn.iocoder.yudao.framework.web.core.filter;

import cn.hutool.core.util.StrUtil;

import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.framework.common.util.servlet.ServletUtils;
import cn.iocoder.yudao.framework.web.core.util.WebFrameworkUtils;
import com.jeelowcode.framework.global.JeeLowCodeBaseConstant;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingRequestWrapper;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.ArrayList;
import java.util.List;

import static cn.iocoder.yudao.framework.common.exception.enums.GlobalErrorCodeConstants.DEMO_DENY;

/**
 * 演示 Filter，禁止用户发起写操作，避免影响测试数据
 *
 * @author 芋道源码
 */;
public class DemoFilter extends OncePerRequestFilter {

    static List<String> noFilterList=new ArrayList<>();
    static {
        noFilterList.add("/admin-api/system/area/view-parent-list");
        noFilterList.add("/admin-api/system/auth/logout");
        noFilterList.add("/admin-api/system/auth/logout");
        noFilterList.add("/admin-api/infra/job/create");
        noFilterList.add("/admin-api/infra/job/update");
        noFilterList.add("/admin-api/infra/job/update-status");
        noFilterList.add("/admin-api/infra/job/trigger");
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        boolean jeeLowCodeFlag = isJeeLowCode(request);
        if(jeeLowCodeFlag){
            return jeeLowCodeFlag;
        }

        String method = request.getMethod();
        return !StrUtil.equalsAnyIgnoreCase(method, "POST", "PUT", "DELETE")  // 写操作时，不进行过滤率
                || WebFrameworkUtils.getLoginUserId(request) == null; // 非登录用户时，不进行过滤
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) {
        // 直接返回 DEMO_DENY 的结果。即，请求不继续
        ServletUtils.writeJSON(response, CommonResult.error(DEMO_DENY));
    }

    //是否的jeelowcode
    private boolean  isJeeLowCode(HttpServletRequest request){
        ContentCachingRequestWrapper requestWrapper = new ContentCachingRequestWrapper(request);
        String requestURI = requestWrapper.getRequestURI();
        if(requestURI!=null && requestURI.startsWith(JeeLowCodeBaseConstant.REQUEST_URL_START)){//jeelowcode的全部功能放开
            return true;
        }
        for(String url:noFilterList){
            if(requestURI.startsWith(url)){
                return true;
            }
        }


        return false;
    }

}
