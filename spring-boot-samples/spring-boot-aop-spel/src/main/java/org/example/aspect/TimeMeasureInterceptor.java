package org.example.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.example.annotation.TimeMeasure;
import org.example.spel.AspectExpressContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.expression.EvaluationException;
import org.springframework.expression.ParseException;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;
import org.springframework.util.StringUtils;

/**
 * @author wurenhai
 * @since 2019/1/11 9:00
 */
@Aspect
@Component
public class TimeMeasureInterceptor {

    private static final Logger logger = LoggerFactory.getLogger(TimeMeasureInterceptor.class);

    @Around("@annotation(org.example.annotation.TimeMeasure)")
    public Object around(ProceedingJoinPoint point) throws Throwable {
        StopWatch sw = new StopWatch();
        sw.start();
        Object result = null;
        try {
            result = point.proceed();
            return result;
        } finally {
            sw.stop();
            output(point, result, sw.getLastTaskTimeMillis());
        }
    }

    private void output(ProceedingJoinPoint point, Object result, long timeInMs) {
        String taskName = point.getSignature().getDeclaringType().getSimpleName()
                + "." + point.getSignature().getName() + "()";
        MethodSignature signature = (MethodSignature) point.getSignature();
        TimeMeasure annotation = signature.getMethod().getAnnotation(TimeMeasure.class);
        String expression = annotation.value();

        String text = expression;
        if (StringUtils.hasLength(expression)) {
            AspectExpressContext context = new AspectExpressContext(point.getTarget(), point.getArgs(), result);
            try {
                text = context.getValue(expression);
            } catch (ParseException e) {
                logger.warn("{} parse[{}] error: {}", taskName, expression, e.getMessage());
            } catch (EvaluationException e) {
                logger.warn("{} eval[{}] error: {}", taskName, expression, e.getMessage());
            }
        }

        logger.info("{} cost {}(ms): {}", taskName, timeInMs, text);
    }

}