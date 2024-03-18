package com.seulah.appdesign.apicontroller.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig implements WebMvcConfigurer {

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedOrigins("http://localhost:3000", "http://localhost:3001", "http://localhost:8085")
                        .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS","PATCH")
                        .allowCredentials(true)
                        .maxAge(3600);
            }
        };
    }
}

