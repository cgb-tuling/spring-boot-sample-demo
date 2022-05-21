package org.example.advice;

import lombok.extern.slf4j.Slf4j;
import org.example.annonation.ResponseResult;
import org.example.exception.BizException;
import org.example.result.Result;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import java.lang.reflect.Method;

@Slf4j
//@ControllerAdvice
public class ResponseResultHandle implements ResponseBodyAdvice<Object> {
    public static final String RESPONSE_RESULT_ANN ="RESPONSE-RESULT-ANN";
    @Override
    public boolean supports(MethodParameter methodParameter, Class<? extends HttpMessageConverter<?>> aClass) {
        //ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        //HttpServletRequest request = requestAttributes.getRequest();
        //ResponseResult responseResult = (ResponseResult) request.getAttribute(RESPONSE_RESULT_ANN);
        //return responseResult != null;
        Class<?> declaringClass = methodParameter.getDeclaringClass();
        Class<?> containingClass = methodParameter.getContainingClass();
        Method method = methodParameter.getMethod();
        if (containingClass.isAnnotationPresent(ResponseResult.class)){
            return true;
        }
        return method.isAnnotationPresent(ResponseResult.class);
    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter methodParameter, MediaType mediaType, Class<? extends HttpMessageConverter<?>> aClass, ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse) {
        log.info("进入返回体 重写格式");
        /**
         if (body instanceof ErrorResult){
         log.info("返回值 异常处理");
         ErrorResult errorResult = (ErrorResult) body;
         return Result.failure(errorResult.getCode(),errorResult.getMessage(),errorResult.getData());
         }
         **/
        return Result.success(body);
    }

    @ExceptionHandler(BizException.class)
    @ResponseBody
    public <T> Result<T> exceptionHandler(BizException e) {
        log.error(e.getMessage(), e);
        Result<T> responseVo = new Result<>();
        responseVo.setCode(-1);
        responseVo.setMessage(e.getMessage());
        responseVo.setData(null);
        return responseVo;
    }

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public <T> Result<T> exceptionHandler(Exception e) {
        log.error(e.getMessage(), e);
        Result<T> responseVo = new Result<>();
        responseVo.setCode(-2);
        responseVo.setMessage(e.getMessage());
        responseVo.setData(null);
        return responseVo;
    }
}