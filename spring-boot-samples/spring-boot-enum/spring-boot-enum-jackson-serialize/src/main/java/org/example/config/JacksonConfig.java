package org.example.config;

import cn.hutool.core.util.ClassUtil;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

//@Configuration
public class JacksonConfig {
    private static final String ENUM_BASE_PKG = "com.maple.enumeration.enums";

    @Bean
    public HttpMessageConverter<?> httpMessageConverter(ObjectMapper objectMapper) {
        ClassUtil.scanPackage(ENUM_BASE_PKG).forEach(item -> objectMapper.configOverride(item)
                .setFormat(JsonFormat.Value.forShape(JsonFormat.Shape.OBJECT)));
        return new MappingJackson2HttpMessageConverter(objectMapper);
    }
}
