package com.example.rbacdemo.web.interceptor;

import com.example.rbacdemo.common.Result;
import com.example.rbacdemo.common.util.HttpUtils;
import com.example.rbacdemo.common.util.JacksonUtils;
import com.example.rbacdemo.service.UserTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class TokenInterceptor implements HandlerInterceptor {

    @Autowired
    private UserTokenService userTokenService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (request instanceof HttpServletRequest) {
            String methodType = request.getMethod().toUpperCase();
            if ("POST".equals(methodType)) {
                String token = request.getHeader("token");
                Result result = userTokenService.check(token);
                if (result.isSuccess()) {
                    userTokenService.refresh(token);
                    return true;
                } else {
                    HttpUtils.write(response, JacksonUtils.stringify(result));
                    return false;
                }
            } else if("OPTIONS".equals(methodType)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }

}
