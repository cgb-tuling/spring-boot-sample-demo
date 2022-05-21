package org.example.json;

import com.fasterxml.jackson.annotation.JacksonAnnotationsInside;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.example.json.v2.ValuePrefixJsonDeserializerFactory;
import org.example.json.v2.ValuePrefixJsonSerializerFactory;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author galaxy
 */

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
@JacksonAnnotationsInside
//@JsonSerialize(using = ValuePrefixJsonSerializer.class)
//@JsonDeserialize(using = ValuePrefixJsonDeserializer.class)
@JsonSerialize(using = ValuePrefixJsonSerializerFactory.class)
@JsonDeserialize(using = ValuePrefixJsonDeserializerFactory.class)
public @interface ValuePrefix {

    String prefix() default "";

}
