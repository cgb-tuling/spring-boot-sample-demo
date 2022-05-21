package org.example.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.example.util.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collection;

@Aspect
@Component
public class SetFeildValueAspect {

    @Autowired
    BeanUtils beanUtils;

    @Around("@annotation(org.example.annotation.NeedSetValueFeild)")
    public Object doSetFeildValue(ProceedingJoinPoint joinPoint) throws Throwable {
        //执行原方法,获取结果
        Object obj = joinPoint.proceed();
        //List [{id:1,customerId:1,customerName:xxx @NeedSetValue()}]
        //操作结果集，将值设置进去
        beanUtils.setFeildValue((Collection) obj);
        return obj;
    }
}