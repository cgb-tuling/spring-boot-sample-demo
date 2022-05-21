package com.maple.enumeration.config;

import org.springframework.context.annotation.Configuration;

/**
 * jackson配置
 *
 * @author maple
 * @version 1.0
 * @since 2020-06-22 00:15
 */
@Configuration
public class JacksonConfig {
    private static final String ENUM_BASE_PKG = "com.maple.enumeration.enums";

//    @Bean
//    public HttpMessageConverter<?> httpMessageConverter(ObjectMapper objectMapper) {
//        //        SimpleModule simpleModule = new SimpleModule();
//        //                simpleModule.addDeserializer(Enum.class, new JacksonEnumDeserializer());
//        //                simpleModule.addSerializer(Enum.class, new JacksonEnumSerializer());
//        //                objectMapper.registerModule(simpleModule);
//        ClassUtil.scanPackage(ENUM_BASE_PKG).forEach(item -> objectMapper.configOverride(item).setFormat
//                (JsonFormat.Value.forShape(JsonFormat.Shape.OBJECT)));
//        return new MappingJackson2HttpMessageConverter(objectMapper);
//    }

    //    @Bean
    //    public Jackson2ObjectMapperBuilderCustomizer customizer() {
    //        return builder -> builder.featuresToEnable(SerializationFeature.WRITE_ENUMS_USING_TO_STRING);
    //    }
}
