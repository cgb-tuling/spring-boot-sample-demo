package org.example.serialize;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import lombok.extern.slf4j.Slf4j;
import org.example.enums.StatusEnum;

import java.io.IOException;
import java.util.Objects;

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
