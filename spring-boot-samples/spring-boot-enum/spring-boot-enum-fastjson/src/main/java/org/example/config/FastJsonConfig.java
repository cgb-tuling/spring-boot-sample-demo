package org.example.config;

import cn.hutool.core.util.ClassUtil;
import com.alibaba.fastjson.parser.ParserConfig;
import com.alibaba.fastjson.serializer.SerializeConfig;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import org.example.serialize.FastJsonEnumDeserializer;
import org.example.serialize.FastJsonEnumSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;

@Configuration
public class FastJsonConfig {
    private static final String ENUM_BASE_PKG = "com.maple.enumeration.enums";

    @Bean
    public HttpMessageConverter<?> httpMessageConverter() {
        FastJsonHttpMessageConverter fastConverter = new FastJsonHttpMessageConverter();
        // 此方式不会生效
        // parserConfig.putDeserializer(Enum.class, new FastJsonEnumDeserializer());
        ClassUtil.scanPackage(ENUM_BASE_PKG).stream().filter(Class::isEnum).forEach(item -> {
            fastConverter.getFastJsonConfig().getParserConfig().putDeserializer(item, new FastJsonEnumDeserializer());
            fastConverter.getFastJsonConfig().getSerializeConfig().put(Enum.class, new FastJsonEnumSerializer());
            fastConverter.getFastJsonConfig().setSerializerFeatures(SerializerFeature.WriteEnumUsingToString);
        });

        return fastConverter;
    }

}
