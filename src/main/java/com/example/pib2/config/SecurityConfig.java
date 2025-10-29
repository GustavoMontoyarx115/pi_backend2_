package com.example.pib2.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    // ...existing code...
    // Usuarios en memoria (para pruebas o entorno simple)
    @Bean
    public UserDetailsService userDetailsService() {
        UserDetails admin = User.withUsername("admin")
                .password("{noop}admin123") // {noop} → sin encriptar
                .roles("ADMIN")
                .build();

        UserDetails user = User.withUsername("user")
                .password("{noop}user123")
                .roles("USER")
                .build();

        UserDetails gustavo = User.withUsername("gustavo")
                .password("{noop}tavo123")
                .roles("ADMIN")
                .build();

        return new InMemoryUserDetailsManager(admin, user, gustavo);
    }

    // Configuración HTTP básica con CORS habilitado
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .cors(Customizer.withDefaults()) // habilita CORS usando la configuración global de CorsConfig
            .csrf(csrf -> csrf.disable()) // desactiva CSRF (para pruebas y H2)
            .authorizeHttpRequests(auth -> auth
                // acceso libre a H2 Console y Swagger
                .requestMatchers(
                    "/h2-console/**",
                    "/swagger-ui/**",
                    "/v3/api-docs/**",
                    "/api-docs/**"
                ).permitAll()
                // todo lo demás requiere autenticación
                .anyRequest().authenticated()
            )
            // permitir iframes para H2 Console
            .headers(headers -> headers.frameOptions(frame -> frame.disable()))
            // autenticación básica HTTP
            .httpBasic(Customizer.withDefaults());

        return http.build();
    }
    // ...existing code...
}