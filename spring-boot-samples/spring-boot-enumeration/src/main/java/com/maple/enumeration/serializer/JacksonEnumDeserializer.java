package com.maple.enumeration.serializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.deser.ContextualDeserializer;
import com.maple.enumeration.StringToEnumConverterFactory;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.jackson.JsonComponent;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Objects;

/**
 * jackson 枚举转换器
 *
 * @author maple
 * @version 1.0
 * @since 2020-06-22 00:25
 */
@JsonComponent
public class JacksonEnumDeserializer extends JsonDeserializer<Enum<?>> implements ContextualDeserializer {
    Logger log = LoggerFactory.getLogger(this.getClass().getName());
    private Class<?> clazz;

    public void setClazz(Class<?> clazz) {
        this.clazz = clazz;
    }

    @Override
    public Enum<?> deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
        Class<?> enumType = clazz;
        if (Objects.isNull(enumType) || !enumType.isEnum()) {
            return null;
        }
        String text = jsonParser.getText();
        Method method = StringToEnumConverterFactory.getMethod(clazz);
        Enum<?>[] enumConstants = (Enum<?>[]) enumType.getEnumConstants();

        for (Enum<?> e : enumConstants) {
            try {
                if (Objects.equals(method.invoke(e).toString(), text)) {
                    return e;
                }
            } catch (IllegalAccessException | InvocationTargetException ex) {
                log.error("获取枚举值错误!!! ", ex);
            }
        }
        return null;
    }

    /**
     * 为不同的枚举获取合适的解析器
     *
     * @param ctx      ctx
     * @param property property
     */
    @Override
    public JsonDeserializer<Enum<?>> createContextual(DeserializationContext ctx, BeanProperty property) throws JsonMappingException {
        Class<?> rawCls = ctx.getContextualType().getRawClass();
        JacksonEnumDeserializer converter = new JacksonEnumDeserializer();
        converter.setClazz(rawCls);
        return converter;
    }
}
