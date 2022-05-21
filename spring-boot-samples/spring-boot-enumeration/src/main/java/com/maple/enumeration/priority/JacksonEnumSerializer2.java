package com.maple.enumeration.priority;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.jackson.JsonComponent;

import java.io.IOException;

/**
 * jackson 优先级测试序列化器
 *
 * @author maple
 * @version 1.0
 * @since 2020-06-22 00:25
 */
@Slf4j
public class JacksonEnumSerializer2 extends JsonSerializer<Enum<?>> {
    @Override
    public void serialize(Enum<?> value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        gen.writeString("优先2");
    }
}
