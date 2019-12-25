package com.example.rbacdemo.config;

import com.example.rbacdemo.web.converter.StringToDateConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Bean
    public StringToDateConverter converter() {
        return new StringToDateConverter();
    }
}
