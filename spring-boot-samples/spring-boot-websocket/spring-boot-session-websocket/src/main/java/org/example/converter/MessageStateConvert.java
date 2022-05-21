package org.example.converter;


import org.example.enums.WebMessageState;
import javax.persistence.AttributeConverter;

/**
 * @author admin
 * @date 2021-05-25
 * @description
 */
public class MessageStateConvert implements AttributeConverter<WebMessageState,Integer> {

    @Override
    public Integer convertToDatabaseColumn(WebMessageState attribute) {
        return attribute.getCode();
    }

    @Override
    public WebMessageState convertToEntityAttribute(Integer dbData) {
        return WebMessageState.getInstance(dbData);
    }
}
