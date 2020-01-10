package com.example.rbacdemo.web.interceptor;

import com.example.rbacdemo.common.Result;
import com.example.rbacdemo.common.annotion.IpLimit;
import com.example.rbacdemo.common.util.HttpUtils;
import com.example.rbacdemo.common.util.JacksonUtils;
import com.example.rbacdemo.service.IpLimitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class IpInterceptor implements HandlerInterceptor {
    @Autowired
    private IpLimitService ipLimitService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (handler instanceof HandlerMethod == false) return true;
        IpLimit ipLimit = ((HandlerMethod) handler).getMethodAnnotation(IpLimit.class);
        if (ipLimit == null) return true;
        String ip = request.getRemoteAddr();
        boolean result = ipLimitService.check(ipLimit, ip);
        if (!result) {
            HttpUtils.write(response, JacksonUtils.stringify(Result.error("请求太过频繁，请稍后再试")));
        }
        return result;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object o, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception e) throws Exception {

    }
}
