package com.rodrigomoreira.myapp.config;

import java.util.concurrent.atomic.AtomicLong;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {
    
    @Bean
    public AtomicLong idCounter(){
        return new AtomicLong(1);
    }
}
