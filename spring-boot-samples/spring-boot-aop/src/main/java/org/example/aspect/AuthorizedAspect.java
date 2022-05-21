package org.example.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.example.annotation.Authorized;
import org.example.contst.AppConst;
import org.example.service.AuthTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.annotation.Annotation;

/**
 * 请求认证切面，验证自定义请求header的authtoken是否合法
 **/
@Slf4j
@Aspect
@Component
public class AuthorizedAspect {

    @Autowired
    private AuthTokenService authTokenService;

    @Pointcut("@annotation(org.springframework.web.bind.annotation.RequestMapping)")
    public void requestMapping() {
    }

    @Pointcut("execution(* org.example.controller.*Controller.*(..))")
    public void methodPointCut() {
    }

    /**
     * 某个方法执行前进行请求合法性认证 注入Authorized注解 （先）
     */
    @Before("requestMapping() && methodPointCut()&&@annotation(authorized)")
    public void doBefore(JoinPoint joinPoint, Authorized authorized) throws Exception {

        log.info("方法认证开始...");

        Class type = joinPoint.getSignature().getDeclaringType();

        Annotation[] annotations = type.getAnnotationsByType(Authorized.class);

        if (annotations != null && annotations.length > 0) {
            log.info("直接类认证");
            return;
        }

        //获取当前http请求
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();

        String token = request.getHeader(AppConst.AUTH_TOKEN);

        Object result = authTokenService.powerCheck(token);

        if (result != null) {
            log.info("方法认证通过");
        } else {
            throw new Exception("认证失败");
        }
    }

    /**
     * 类下面的所有方法执行前进行请求合法性认证 （后）
     */
    @Before("requestMapping() && methodPointCut()")
    public void doBefore(JoinPoint joinPoint) throws Exception {

        log.info("类认证开始...");

        Annotation[] annotations = joinPoint.getSignature().getDeclaringType().getAnnotationsByType(Authorized.class);

        if (annotations == null || annotations.length == 0) {
            log.info("类不需要认证");
            return;
        }

        //获取当前http请求
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();

        String token = request.getHeader(AppConst.AUTH_TOKEN);

        Object bizResult = authTokenService.powerCheck(token);

        if (bizResult != null) {
            log.info("类认证通过");
        } else {
            throw new Exception("认证失败");
        }
    }

}