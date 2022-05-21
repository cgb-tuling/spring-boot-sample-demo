package org.example.aspect;

import cn.hutool.json.JSONUtil;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.example.http.BaseApiRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

/**
 * 服务日志切面，主要记录接口日志及耗时
 **/
@Aspect
@Component
public class SvcLogAspect {

    @Pointcut("@annotation(org.springframework.web.bind.annotation.RequestMapping)")
    public void requestMapping() {
    }

    @Pointcut("execution(* org.example.controller.*Controller.*(..))")
    public void methodPointCut() {
    }

    @Around("requestMapping() && methodPointCut()")
    public Object around(ProceedingJoinPoint pjd) throws Throwable {

        System.out.println("Spring AOP方式记录服务日志");

        Object response = null;//定义返回信息

        BaseApiRequest baseApiRequest = null;//请求基类

        int index = 0;

        Signature curSignature = pjd.getSignature();

        String className = curSignature.getClass().getName();//类名

        String methodName = curSignature.getName(); //方法名

        Logger logger = LoggerFactory.getLogger(className);//日志

        StopWatch watch = new StopWatch();//用于统计调用耗时

        // 获取方法参数
        Object[] reqParamArr = pjd.getArgs();
        StringBuffer sb = new StringBuffer();
        //获取请求参数集合并进行遍历拼接
        for (Object reqParam : reqParamArr) {
            if (reqParam == null) {
                index++;
                continue;
            }
            try {
                sb.append(JSONUtil.toJsonStr(reqParam));

                //获取继承自BaseApiRequest的请求实体
//                if (baseApiRequest == null && reqParam instanceof BaseApiRequest) {
//                    index++;
//                    baseApiRequest = (BaseApiRequest) reqParam;
//                }

            } catch (Exception e) {
                sb.append(reqParam.toString());
            }
            sb.append(",");
        }

        String strParam = sb.toString();
        if (strParam.length() > 0) {
            strParam = strParam.substring(0, strParam.length() - 1);
        }

        //记录请求
        logger.info(String.format("【%s】类的【%s】方法，请求参数：%s", className, methodName, strParam));

        response = pjd.proceed(); // 执行服务方法

        watch.stop();

        //记录应答
        logger.info(String.format("【%s】类的【%s】方法，应答参数：%s", className, methodName,JSONUtil.toJsonStr(response)));

        // 获取执行完的时间
        logger.info(String.format("接口【%s】总耗时(毫秒)：%s", methodName, watch.getTotalTimeMillis()));

        //标准请求-应答模型

        return response;

    }

}