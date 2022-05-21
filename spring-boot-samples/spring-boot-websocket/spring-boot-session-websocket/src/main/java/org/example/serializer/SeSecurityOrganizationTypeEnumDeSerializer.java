package org.example.serializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import org.example.enums.ScSecurityOrganizationTypeEnum;


import java.io.IOException;

public class SeSecurityOrganizationTypeEnumDeSerializer extends JsonDeserializer<ScSecurityOrganizationTypeEnum> {

    @Override
    public ScSecurityOrganizationTypeEnum deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {
        return ScSecurityOrganizationTypeEnum.getInstance(p.getIntValue());
    }
}
