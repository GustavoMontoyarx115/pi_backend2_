package com.example.pib2.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig {

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/api/**")          // aplica a todos los endpoints de tu API
                        .allowedOrigins("*")            // reemplaza "*" por tu frontend en producción
                        .allowedMethods("GET","POST","PUT","DELETE")
                        .allowCredentials(true);
            }
        };
    }
}
