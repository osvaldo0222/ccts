package com.project.ccts.config;

import com.project.ccts.exception.ExceptionAuthenticationEntryPointHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ExceptionHandlerConfig {

    @Bean
    public ExceptionAuthenticationEntryPointHandler authenticationEntryPoint() {
        return new ExceptionAuthenticationEntryPointHandler();
    }
}
