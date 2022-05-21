package org.example.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.example.enums.ScSecurityOrganizationTypeEnum;

import java.io.IOException;


public class SeSecurityOrganizationTypeEnumSerializer extends JsonSerializer<ScSecurityOrganizationTypeEnum> {
    @Override
    public void serialize(ScSecurityOrganizationTypeEnum value,
                          JsonGenerator gen, SerializerProvider serializers) throws IOException {
        gen.writeNumber(value.getCode());
    }


}
