package org.example.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import org.example.serialize.general.JacksonEnumDeserializer;
import org.example.serialize.general.JacksonEnumSerializer;
import org.springframework.boot.jackson.JsonComponent;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

/**
 * 或者直接 在 {@link JacksonEnumDeserializer} 上使用 {@link JsonComponent}
 */
@Configuration
public class JacksonConfig1 {
    @Bean
    public HttpMessageConverter<?> httpMessageConverter(ObjectMapper objectMapper) {
        SimpleModule simpleModule = new SimpleModule();
        simpleModule.addDeserializer(Enum.class, new JacksonEnumDeserializer());
        simpleModule.addSerializer(Enum.class, new JacksonEnumSerializer());
        objectMapper.registerModule(simpleModule);
        return new MappingJackson2HttpMessageConverter(objectMapper);
    }
}
