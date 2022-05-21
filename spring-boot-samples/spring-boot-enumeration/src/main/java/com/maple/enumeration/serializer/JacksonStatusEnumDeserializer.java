package com.maple.enumeration.serializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.maple.enumeration.enums.StatusEnum;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.Objects;

/**
 * jackson 枚举转换器
 *
 * @author maple
 * @version 1.0
 * @since 2020-06-22 00:25
 */
@Slf4j
public class JacksonStatusEnumDeserializer extends JsonDeserializer<StatusEnum> {
    @Override
    public StatusEnum deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        String text = jsonParser.getText();
        for (StatusEnum value : StatusEnum.values()) {
            if (Objects.equals(text, value.getValue().toString())) {
                return value;
            }
        }
        return null;
    }
}
