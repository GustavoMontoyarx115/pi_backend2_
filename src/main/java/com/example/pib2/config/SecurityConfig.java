package com.example.pib2.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Profile("prod") // Solo se aplica en producción
@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable()) // Desactivar CSRF para APIs REST
            .authorizeHttpRequests(auth -> auth
                // Rutas públicas
                .requestMatchers("/api/users/**").permitAll()
                .requestMatchers("/api/appointments/**").permitAll()

                // Swagger / OpenAPI
                .requestMatchers(
                    "/swagger-ui.html",
                    "/swagger-ui/**",
                    "/v3/api-docs/**",
                    "/v3/api-docs.yaml"
                ).permitAll()

                // Todo lo demás requiere autenticación
                .anyRequest().authenticated()
            )
            .formLogin(login -> login.disable()) // Deshabilitar login por formulario
            .httpBasic(basic -> basic.disable()); // Deshabilitar autenticación básica

        return http.build();
    }
}
