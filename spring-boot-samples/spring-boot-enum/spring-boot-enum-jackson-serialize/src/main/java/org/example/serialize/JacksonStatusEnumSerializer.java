package org.example.serialize;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.example.enums.StatusEnum;

import java.io.IOException;

public class JacksonStatusEnumSerializer extends JsonSerializer<StatusEnum> {
    @Override
    public void serialize(StatusEnum value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        gen.writeString(value.getDesc());
    }
}
