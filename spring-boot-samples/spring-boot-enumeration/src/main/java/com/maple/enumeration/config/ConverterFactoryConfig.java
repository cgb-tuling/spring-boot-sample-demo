package com.maple.enumeration.config;

import com.maple.enumeration.StringToEnumConverterFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 转换器工厂配置
 *
 * @author maple
 * @version 1.0
 * @since 2020-06-21 23:02
 */
@Configuration
public class ConverterFactoryConfig implements WebMvcConfigurer {
    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverterFactory(new StringToEnumConverterFactory());
    }
}
