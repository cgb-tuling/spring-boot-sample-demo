package com.maple.enumeration.serializer;

import com.alibaba.fastjson.parser.DefaultJSONParser;
import com.alibaba.fastjson.parser.JSONLexer;
import com.alibaba.fastjson.parser.JSONToken;
import com.alibaba.fastjson.parser.deserializer.ObjectDeserializer;
import com.maple.enumeration.StringToEnumConverterFactory;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.Objects;

/**
 * fast json反序列化器
 *
 * @author maple
 * @version 1.0
 * @since 2020-06-23 02:07
 */
public class FastJsonEnumDeserializer implements ObjectDeserializer {
    Logger log = LoggerFactory.getLogger(this.getClass().getName());

    @Override
    public <T> T deserialze(DefaultJSONParser parser, Type type, Object o) {
        final JSONLexer lexer = parser.lexer;
        Class<?> cls = (Class<?>) type;
        Object[] enumConstants = cls.getEnumConstants();
        Method method = StringToEnumConverterFactory.getMethod(cls);
        if (!Enum.class.isAssignableFrom(cls)) {
            return null;
        }
        for (Object item : enumConstants) {
            try {
                String value = method.invoke(item).toString();
                if (Objects.equals(value, lexer.stringVal()) || Objects.equals(Integer.valueOf(value), lexer.intValue())) {
                    return (T) item;
                }
            } catch (IllegalAccessException | InvocationTargetException ex) {
                log.error("获取枚举值错误!!! ", ex);
            }
        }
        return null;
    }

    @Override
    public int getFastMatchToken() {
        return JSONToken.LITERAL_INT;
    }
}
