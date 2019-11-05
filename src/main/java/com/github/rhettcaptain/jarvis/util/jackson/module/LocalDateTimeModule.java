package com.github.rhettcaptain.jarvis.util.jackson.module;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class LocalDateTimeModule extends SimpleModule {
    private static final String DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";

    public LocalDateTimeModule(){
        this.addSerializer(new LocalDateTimeSerializer(LocalDateTime.class));
        this.addDeserializer(LocalDateTime.class, new LocalDateTimeDeserializer(String.class));
    }

    private class LocalDateTimeSerializer extends StdSerializer<LocalDateTime> {
        protected LocalDateTimeSerializer(Class<LocalDateTime> t) {
            super(t);
        }
        @Override
        public void serialize(LocalDateTime value, JsonGenerator gen, SerializerProvider provider) throws IOException {
            gen.writeString(value.format(DateTimeFormatter.ofPattern(DATE_TIME_FORMAT)));
        }
    }
    private class LocalDateTimeDeserializer extends StdDeserializer<LocalDateTime> {

        protected LocalDateTimeDeserializer(Class<String> vc) {
            super(vc);
        }

        @Override
        public LocalDateTime deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {
            return LocalDateTime.parse(p.getValueAsString(), DateTimeFormatter.ofPattern(DATE_TIME_FORMAT));
        }
    }
}

