package org.example.serialize.general;

import cn.hutool.core.util.EnumUtil;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.deser.ContextualDeserializer;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.example.convert.StringToEnumConverterFactory;
import org.springframework.boot.jackson.JsonComponent;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Objects;

@Slf4j
@Setter
//@JsonComponent
public class JacksonEnumDeserializer extends JsonDeserializer<Enum<?>> implements ContextualDeserializer {
    private Class clazz;
	// ctx.getContextualType() 获取不到类信息
    @Override
    public Enum<?> deserialize(JsonParser jsonParser, DeserializationContext ctx) throws IOException, JsonProcessingException {
        Class<?> enumType = clazz;
        if (Objects.isNull(enumType) || !enumType.isEnum()) {
            return null;
        }
        String text = jsonParser.getText();
        List<String> fieldNames = EnumUtil.getFieldNames(clazz);
        Enum anEnum = EnumUtil.fromString(clazz, text);
        Enum e1 = EnumUtil.likeValueOf(clazz, text);
        Enum<?>[] enumConstants = (Enum<?>[]) enumType.getEnumConstants();
        Method method = StringToEnumConverterFactory.getMethod(clazz);

        // 将值与枚举对象对应并缓存
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
