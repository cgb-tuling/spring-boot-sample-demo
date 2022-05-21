package com.maple.enumeration.config;

import cn.hutool.core.util.ClassUtil;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import com.maple.enumeration.enums.FastJsonStatusEnum;
import com.maple.enumeration.serializer.FastJsonEnumSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;

/**
 * fast json配置
 *
 * @author maple
 * @version 1.0
 * @since 2020-06-22 21:43
 */
@Configuration
public class FastJsonConfig {
    private static final String ENUM_BASE_PKG = "com.maple.enumeration.enums";

    /**
     * 全局配置示例
     */
    //    @Bean
    //    public HttpMessageConverter<?> httpMessageConverter() {
    //        FastJsonHttpMessageConverter fastConverter = new FastJsonHttpMessageConverter();
    //        ParserConfig parserConfig = fastConverter.getFastJsonConfig().getParserConfig();
    //        // 此方式不会生效
    //        // parserConfig.putDeserializer(Enum.class, new FastJsonEnumDeserializer());
    //        ClassUtil.scanPackage(ENUM_BASE_PKG).stream().filter(Class::isEnum).forEach(item ->
    //                parserConfig.putDeserializer(item, new FastJsonEnumDeserializer()));
    //        return fastConverter;
    //    }

    /**
     * SerializerFeature配置示例
     */
    //    @Bean
    //    public HttpMessageConverter<?> httpMessageConverter() {
    //        FastJsonHttpMessageConverter fastConverter = new FastJsonHttpMessageConverter();
    ////        fastConverter.getFastJsonConfig().setSerializerFeatures(SerializerFeature.WriteEnumUsingToString);
    //        return fastConverter;
    //    }

    /**
     * 全局注册序列化器示例
     */
    @Bean
    public HttpMessageConverter<?> httpMessageConverter() {
        FastJsonHttpMessageConverter fastConverter = new FastJsonHttpMessageConverter();
        ClassUtil.scanPackage(ENUM_BASE_PKG).stream().filter(Class::isEnum).forEach(item ->
                fastConverter.getFastJsonConfig().getSerializeConfig().put(FastJsonStatusEnum.class,
                        new FastJsonEnumSerializer()));
        return fastConverter;
    }

}
