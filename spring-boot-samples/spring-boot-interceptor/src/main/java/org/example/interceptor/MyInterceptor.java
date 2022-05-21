package org.example.interceptor;

import org.example.annotation.UnInterception;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Method;

/**
 * 自定义拦截器
 *
 * @author tian
 * @date 2018/08/03
 */
public class MyInterceptor implements HandlerInterceptor {

    private static final Logger logger = LoggerFactory.getLogger(MyInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        if (handler instanceof HandlerMethod) {
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            Method method = handlerMethod.getMethod();
            String methodName = method.getName();
            logger.info("====拦截到了方法：{}，在该方法执行之前执行====", methodName);
            // 通过方法，可以获取该方法上的自定义注解，然后通过注解来判断该方法是否要被拦截
            // @UnInterception 是我们自定义的注解
            UnInterception unInterception = method.getAnnotation(UnInterception.class);
            if (null != unInterception) {
                System.out.println(getBodyData(request));

                return true;
            }
        }


        // 判断用户有没有登陆，一般登陆之后的用户都有一个对应的token
        String token = request.getParameter("token");
        if (null == token || "".equals(token)) {
            logger.info("用户未登录，没有权限执行……请登录");
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            response.setHeader("Content-Type", "application/json;charset=utf-8");
            PrintWriter out = response.getWriter();
            out.write("{\"status\":\"error\",\"msg\":\"权限不足，请联系管理员！\"}");
            out.flush();
            out.close();
            return false;
        }

        // 返回true才会继续执行，返回false则取消当前请求
        return true;
    }

    private String getBodyData(HttpServletRequest request) {
        StringBuffer data = new StringBuffer();
        String line = null;
        BufferedReader reader = null;
        try {
            reader = request.getReader();
            while (null != (line = reader.readLine())) {
                data.append(line);
            }
        } catch (IOException e) {
        } finally {
        }
        return data.toString();
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        logger.info("执行完方法之后进执行(Controller方法调用之后)，但是此时还没进行视图渲染");
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        logger.info("整个请求都处理完咯，DispatcherServlet也渲染了对应的视图咯，此时我可以做一些清理的工作了");
    }
}
