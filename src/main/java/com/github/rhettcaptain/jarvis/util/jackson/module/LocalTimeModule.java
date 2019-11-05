package com.github.rhettcaptain.jarvis.util.jackson.module;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class LocalTimeModule extends SimpleModule {
    private static final String TIME_FORMAT = "HH:mm:ss";

    public LocalTimeModule(){
        this.addSerializer(new LocalTimeSerializer(LocalTime.class));
        this.addDeserializer(LocalTime.class, new LocalTimeDeserializer(String.class));
    }

    private class LocalTimeSerializer extends StdSerializer<LocalTime> {
        protected LocalTimeSerializer(Class<LocalTime> t) {
            super(t);
        }
        @Override
        public void serialize(LocalTime value, JsonGenerator gen, SerializerProvider provider) throws IOException {
            gen.writeString(value.format(DateTimeFormatter.ofPattern(TIME_FORMAT)));
        }
    }
    private class LocalTimeDeserializer extends StdDeserializer<LocalTime> {

        protected LocalTimeDeserializer(Class<String> vc) {
            super(vc);
        }

        @Override
        public LocalTime deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {
            return LocalTime.parse(p.getValueAsString(), DateTimeFormatter.ofPattern(TIME_FORMAT));
        }
    }
}

