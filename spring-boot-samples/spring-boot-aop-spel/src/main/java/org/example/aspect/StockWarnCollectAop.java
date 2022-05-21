package org.example.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.example.annotation.StockWarnCollect;
import org.example.domain.User;
import org.springframework.core.LocalVariableTableParameterNameDiscoverer;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.lang.reflect.Method;
 
/**
 * 下单失败、库存监控
 * Created by xdc on 2019/4/16 15:45
 */
@Aspect
@Component
@Slf4j
public class StockWarnCollectAop {
 
    @Pointcut(value = "@annotation(org.example.annotation.StockWarnCollect)")
    public void collectStockWarn(){}
 
    @Around(value = "collectStockWarn()")
    public Object around(ProceedingJoinPoint pjp) throws Throwable {
 
        Method targetMethod = this.getTargetMethod(pjp);
        StockWarnCollect stockWarnCollect = targetMethod.getAnnotation(StockWarnCollect.class);
 
        // spel信息
        String customerIdSpel = stockWarnCollect.customerId();
        String sourceSpel = stockWarnCollect.source();
        String username = stockWarnCollect.username();
        Integer pageType = null;  // 操作类型，纯字符串
        if (StringUtils.hasText(stockWarnCollect.pageType())) {
            pageType = Integer.valueOf(stockWarnCollect.pageType());
        }
 
        // 客户id、来源解析
        ExpressionParser parser = new SpelExpressionParser();
        LocalVariableTableParameterNameDiscoverer discoverer = new LocalVariableTableParameterNameDiscoverer();
        String[] params = discoverer.getParameterNames(targetMethod);
 
        Object[] args = pjp.getArgs();
 
        EvaluationContext context = new StandardEvaluationContext();
        for (int len = 0; len < params.length; len++) {
            context.setVariable(params[len], args[len]);
        }
        Expression expression = parser.parseExpression(customerIdSpel);
        Long customerId = expression.getValue(context, Long.class);
 
        expression = parser.parseExpression(sourceSpel);

        String value = parser.parseExpression(username).getValue(context, String.class);
        User source = expression.getValue(context, User.class);
        log.info("collectStockWarn customerId:{},username {}, source:{}", customerId,value, source);
 
        // 业务逻辑处理
        Object result = null;
        try {
            result = pjp.proceed();
        } catch (Throwable e) {
            log.info("collectStockWarn watchs creating order errorMsg:{}", e);
            throw e;
        }
 
        try {
            if (result != null) {
            
            }
        } catch (Exception e) {
            log.error("collectStockWarn process error, errorMsg:{}",e);
        }
 
        return result;
 
    }
 
    /**
     * 获取目标方法
     */
    private Method getTargetMethod(ProceedingJoinPoint pjp) throws NoSuchMethodException {
        Signature signature = pjp.getSignature();
        MethodSignature methodSignature = (MethodSignature)signature;
        Method agentMethod = methodSignature.getMethod();
        return pjp.getTarget().getClass().getMethod(agentMethod.getName(),agentMethod.getParameterTypes());
    }
}