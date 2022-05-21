package com.maple.enumeration.serializer;

import cn.hutool.core.util.NumberUtil;
import cn.hutool.core.util.ReflectUtil;
import cn.hutool.core.util.StrUtil;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.jackson.JsonComponent;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Objects;

/**
 * jackson 枚举转换器
 *
 * @author maple
 * @version 1.0
 * @since 2020-06-22 00:25
 */
@Slf4j
@JsonComponent
public class JacksonEnumSerializer extends JsonSerializer<Enum<?>> {
    @Override
    public void serialize(Enum<?> value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
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
