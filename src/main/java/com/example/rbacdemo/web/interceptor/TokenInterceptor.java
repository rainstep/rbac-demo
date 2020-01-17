package com.example.rbacdemo.web.interceptor;

import com.example.rbacdemo.common.Result;
import com.example.rbacdemo.common.annotion.RequirePermission;
import com.example.rbacdemo.common.util.HttpUtils;
import com.example.rbacdemo.common.util.JacksonUtils;
import com.example.rbacdemo.service.PermissionService;
import com.example.rbacdemo.service.UserTokenService;
import com.example.rbacdemo.service.model.TokenUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class TokenInterceptor implements HandlerInterceptor {
    @Autowired
    private UserTokenService userTokenService;
    @Autowired
    private PermissionService permissionService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (!(request instanceof HttpServletRequest)) return false;
        String methodType = request.getMethod().toUpperCase();
        if ("POST".equals(methodType)) {
            String token = request.getHeader("token");
            Result<TokenUser> result = userTokenService.check(token);
            if (result.isSuccess()) {
                RequirePermission requirePermission = ((HandlerMethod) handler).getMethodAnnotation(RequirePermission.class);
                if (requirePermission == null) return true;
                String requirePermissionCode = requirePermission.value();
                Integer userId = result.getData().getUserId();
//                boolean hasPermission = permissionService.check(userId, requirePermissionCode);
//                if (!hasPermission) {
//                    return false;
//                }
                userTokenService.refresh(token);
                return true;
            } else {
                HttpUtils.write(response, JacksonUtils.stringify(result));
                return false;
            }
        } else if ("OPTIONS".equals(methodType)) {
            return false;
        }
        return false;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }

}
