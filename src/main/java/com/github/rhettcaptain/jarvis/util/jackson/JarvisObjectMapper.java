package com.github.rhettcaptain.jarvis.util.jackson;

import com.github.rhettcaptain.jarvis.util.jackson.module.LocalDateTimeModule;
import com.github.rhettcaptain.jarvis.util.jackson.module.LocalTimeModule;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.time.LocalDateTime;

@Component
public class JarvisObjectMapper extends ObjectMapper{
    public JarvisObjectMapper(){
        this.registerModule(new LocalDateTimeModule());
        this.registerModule(new LocalTimeModule());
    }
}
