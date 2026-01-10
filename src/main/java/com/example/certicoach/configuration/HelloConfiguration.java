package com.example.certicoach.configuration;

import com.example.certicoach.provider.HelloProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class HelloConfiguration {

    @Bean
    HelloProvider messageProvider() {
        return new HelloProvider();
    }
}