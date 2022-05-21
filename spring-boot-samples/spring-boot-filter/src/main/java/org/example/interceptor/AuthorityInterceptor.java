package org.example.interceptor;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.io.IOUtils;
import org.example.annotations.LoginRequire;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

public class AuthorityInterceptor extends HandlerInterceptorAdapter {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if(!(handler instanceof HandlerMethod)){
            return true;
        }

        //START: 方法注解级拦截器
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        Method method = handlerMethod.getMethod();
        //判断接口是否需要登录
        LoginRequire methodAnnotation = method.getAnnotation(LoginRequire.class);
        //有@LoginRequired注解，需要认证
        if(methodAnnotation != null){
            System.out.println("========================");
            ContentCachingRequestWrapper requestWapper = null;
            if(request instanceof HttpServletRequest){
                requestWapper = (ContentCachingRequestWrapper) request;
            }
            String body = IOUtils.toString(requestWapper.getBody(),request.getCharacterEncoding());
            System.out.println(body);
            JSONObject obj = JSON.parseObject(body);
            System.out.println(obj);
        }
        return true;
    }
}