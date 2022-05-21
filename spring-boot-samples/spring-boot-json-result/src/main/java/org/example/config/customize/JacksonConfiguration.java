package org.example.config.customize;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.ser.std.DateSerializer;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

// import com.fasterxml.jackson.datatype.hibernate5.Hibernate5Module;
// import com.fasterxml.jackson.module.afterburner.AfterburnerModule;

//@Configuration
public class JacksonConfiguration {

    /**
     * Json时间格式, 默认值为: yyyy-MM-dd HH:mm:ss
     */
    @Value("${spring.jackson.date-format:yyyy-MM-dd HH:mm:ss}")
    private String formatValue;

    @Bean(name = "format")
    public DateTimeFormatter format() {
        return DateTimeFormatter.ofPattern(formatValue);
    }

    @Bean(name = "jacksonObjectMapper")
    public ObjectMapper serializingObjectMapper(@Qualifier("format") DateTimeFormatter format) {
        JavaTimeModule javaTimeModule = new JavaTimeModule();
        javaTimeModule.addSerializer(LocalDateTime.class, new LocalDateTimeSerializer(format));
        javaTimeModule.addSerializer(Instant.class, new InstantCustomSerializer(format));
        javaTimeModule.addSerializer(Date.class, new DateSerializer(false, new SimpleDateFormat(formatValue)));
        javaTimeModule.addDeserializer(Instant.class, new InstantCustomDeserializer());
        javaTimeModule.addDeserializer(Date.class, new DateCustomDeserializer());
        ObjectMapper mapper = new ObjectMapper()
                .registerModule(new ParameterNamesModule())
                .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
                .registerModule(javaTimeModule);
        return mapper;
    }

    class InstantCustomSerializer extends JsonSerializer<Instant> {
        private DateTimeFormatter format;

        private InstantCustomSerializer(DateTimeFormatter formatter) {
            this.format = formatter;
        }

        @Override
        public void serialize(Instant instant, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
            if (instant == null) {
                return;
            }
            String jsonValue = format.format(instant.atZone(ZoneId.systemDefault()));
            jsonGenerator.writeString(jsonValue);
        }
    }

    class InstantCustomDeserializer extends JsonDeserializer<Instant> {

        @Override
        public Instant deserialize(JsonParser p, DeserializationContext ctxt)
                throws IOException, JsonProcessingException {
            String dateString = p.getText().trim();
            if (!StringUtils.isEmpty(dateString)) {
                Date pareDate;
                try {
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    pareDate = simpleDateFormat.parse(dateString);
                    if (null != pareDate) {
                        return pareDate.toInstant();
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }

    }

    class DateCustomDeserializer extends JsonDeserializer<Date> {

        @Override
        public Date deserialize(JsonParser p, DeserializationContext ctxt)
                throws IOException, JsonProcessingException {
            String dateString = p.getText().trim();
            if (!StringUtils.isEmpty(dateString)) {
                try {
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    return simpleDateFormat.parse(dateString);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }

    }

    /**
     * Support for Java date and time API.
     *
     * @return the corresponding Jackson module.
     */
    @Bean
    public JavaTimeModule javaTimeModule() {
        return new JavaTimeModule();
    }

    @Bean
    public Jdk8Module jdk8TimeModule() {
        return new Jdk8Module();
    }


    // /*
    //  * Support for Hibernate types in Jackson.
    //  */
    // @Bean
    // public Hibernate5Module hibernate5Module() {
    //     return new Hibernate5Module();
    // }
    //
    // /*
    //  * Jackson Afterburner module to speed up serialization/deserialization.
    //  */
    // @Bean
    // public AfterburnerModule afterburnerModule() {
    //     return new AfterburnerModule();
    // }
    //
    // /*
    //  * Module for serialization/deserialization of RFC7807 Problem.
    //  */
    // @Bean
    // ProblemModule problemModule() {
    //     return new ProblemModule();
    // }
    //
    // /*
    //  * Module for serialization/deserialization of ConstraintViolationProblem.
    //  */
    // @Bean
    // ConstraintViolationProblemModule constraintViolationProblemModule() {
    //     return new ConstraintViolationProblemModule();
    // }

}