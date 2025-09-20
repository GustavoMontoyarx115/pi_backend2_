package com.example.pib2.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable()) // 🔹 desactivar CSRF para APIs REST
            .authorizeHttpRequests(auth -> auth
                // Rutas públicas (sin autenticación)
                .requestMatchers("/api/users/**").permitAll()
                .requestMatchers("/api/appointments/**").permitAll()

                // Swagger OpenAPI (se deja público)
                .requestMatchers(
                    "/swagger-ui.html",
                    "/swagger-ui/**",
                    "/v3/api-docs/**",
                    "/v3/api-docs.yaml"
                ).permitAll()

                // Todo lo demás requiere autenticación
                .anyRequest().authenticated()
            )
            .formLogin(login -> login.disable()) // deshabilita login por formulario
            .httpBasic(basic -> basic.disable()); // deshabilita autenticación básica

        return http.build();
    }
}
