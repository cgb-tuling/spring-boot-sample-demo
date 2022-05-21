package org.example.config;

import com.fasterxml.jackson.databind.SerializationFeature;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 或者在 application.yml 中配置
 * spring:
 *   jackson:
 *     serialization:
 *       WRITE_ENUMS_USING_TO_STRING: true
 *       WRITE_ENUMS_USING_INDEX: true
 *       WRITE_ENUM_KEYS_USING_INDEX: true
 */
//@Configuration
public class JacksonConfig {
    @Bean
    public Jackson2ObjectMapperBuilderCustomizer customizer() {
        return builder -> builder.featuresToEnable(SerializationFeature.WRITE_ENUMS_USING_TO_STRING);
    }
}