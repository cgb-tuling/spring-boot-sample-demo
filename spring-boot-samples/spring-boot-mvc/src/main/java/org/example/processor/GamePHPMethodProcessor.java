package org.example.processor;

import org.example.annotaion.GamePHP;
import org.springframework.core.MethodParameter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import org.springframework.web.servlet.mvc.method.annotation.RequestResponseBodyMethodProcessor;
import org.springframework.web.servlet.mvc.method.annotation.ServletModelAttributeMethodProcessor;

import javax.servlet.ServletRequest;
import java.util.ArrayList;
import java.util.List;

public class GamePHPMethodProcessor implements HandlerMethodArgumentResolver {

    //    private GameFormMethodArgumentResolver formResolver;
//    private GameJsonMethodArgumentResolver jsonResolver;
    private ServletModelAttributeMethodProcessor modelAttributeMethodProcessor;
    private RequestResponseBodyMethodProcessor requestResponseBodyMethodProcessor;

    public GamePHPMethodProcessor() {
        List<HttpMessageConverter<?>> messageConverters = new ArrayList<>();
//        PHPMessageConverter PHPMessageConverter = new PHPMessageConverter();
//        messageConverters.add(PHPMessageConverter);
//
//        jsonResolver = new GameJsonMethodArgumentResolver(messageConverters);
//        formResolver = new GameFormMethodArgumentResolver();
    }

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        GamePHP ann = parameter.getParameterAnnotation(GamePHP.class);
        return (ann != null);
    }

    @Override
    public Object resolveArgument(MethodParameter methodParameter, ModelAndViewContainer modelAndViewContainer, NativeWebRequest nativeWebRequest, WebDataBinderFactory webDataBinderFactory) throws Exception {
        ServletRequest servletRequest = nativeWebRequest.getNativeRequest(ServletRequest.class);
        String contentType = servletRequest.getContentType();
        if (contentType == null) {
            throw new IllegalArgumentException("不支持contentType");
        }

//        if (contentType.contains("application/json")) {
//            return jsonResolver.resolveArgument(methodParameter, modelAndViewContainer, nativeWebRequest, webDataBinderFactory);
//        }
//
//        if (contentType.contains("application/x-www-form-urlencoded")) {
//            return formResolver.resolveArgument(methodParameter, modelAndViewContainer, nativeWebRequest, webDataBinderFactory);
//        }
//
//        if (contentType.contains("multipart")) {
//            return formResolver.resolveArgument(methodParameter, modelAndViewContainer, nativeWebRequest, webDataBinderFactory);
//        }

        throw new IllegalArgumentException("不支持contentType");
    }
}