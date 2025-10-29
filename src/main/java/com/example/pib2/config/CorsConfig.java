package com.example.pib2.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.List;

@Configuration
public class CorsConfig {

    @Bean
    public CorsFilter corsFilter() {
        CorsConfiguration config = new CorsConfiguration();

        // 🔹 Permitir tu frontend local y Render (si lo despliegas)
        config.setAllowedOrigins(List.of(
                "http://localhost:3000",
                "https://pi-web2.onrender.com" // Cambia si el frontend está desplegado en otro dominio
        ));

        // 🔹 Métodos permitidos
        config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));

        // 🔹 Cabeceras permitidas
        config.setAllowedHeaders(List.of("Authorization", "Content-Type"));

        // 🔹 Permitir credenciales (opcional)
        config.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return new CorsFilter(source);
    }
}
