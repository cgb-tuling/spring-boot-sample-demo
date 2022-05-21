package org.example.serialize.general;

import cn.hutool.core.util.EnumUtil;
import cn.hutool.core.util.NumberUtil;
import cn.hutool.core.util.ReflectUtil;
import cn.hutool.core.util.StrUtil;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.deser.ContextualDeserializer;
import com.fasterxml.jackson.databind.ser.ContextualSerializer;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.example.convert.StringToEnumConverterFactory;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Objects;

@Slf4j
@Setter
//@JsonComponent
public class  JacksonEnumSerializer <T extends Enum<?>>  extends JsonSerializer<T>{
    private Class<?> clazz;

    @Override
    public void serialize(T value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        Method[] methods = ReflectUtil.getMethods(value.getClass(), item -> StrUtil.startWith(item.getName(), "get"));
        gen.writeStartObject();
        for (Method method : methods) {
            String name = StrUtil.subAfter(method.getName(), "get", false);
            // 首字母小写
            name = name.substring(0, 1).toLowerCase() + name.substring(1);
            String invokeStr = Objects.toString(ReflectUtil.invoke(value, method));
            // 非数值类型写入字符串
            if (!NumberUtil.isNumber(invokeStr)) {
                gen.writeStringField(name, invokeStr);
                continue;
            }
            // 是否小数
            if (invokeStr.contains(".")) {
                gen.writeNumberField(name, Double.parseDouble(invokeStr));
                continue;
            }

            gen.writeNumberField(name, Long.parseLong(invokeStr));
        }
        gen.writeEndObject();
    }
}
