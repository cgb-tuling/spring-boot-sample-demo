package org.example.converter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.entity.ScMsaWebMessage;

import javax.persistence.AttributeConverter;
import java.io.IOException;

/**
 * @author admin
 * @date 2021-05-31
 * @description
 */
public class WebMessageConvert implements AttributeConverter<ScMsaWebMessage,String> {

    public static final ObjectMapper mapper = new ObjectMapper();

    @Override
    public String convertToDatabaseColumn(ScMsaWebMessage attribute) {
        try {
            return mapper.writeValueAsString(attribute);
        } catch (JsonProcessingException e) {

        }
        return null;
    }

    @Override
    public ScMsaWebMessage convertToEntityAttribute(String dbData) {
        try {
            return mapper.readValue(dbData, ScMsaWebMessage.class);
        } catch (IOException e) {

        }
        return null;
    }

}
