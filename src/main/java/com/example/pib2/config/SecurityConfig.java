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

    // 🔹 Usuarios en memoria (para pruebas o entorno simple)
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

    // 🔹 Configuración HTTP básica
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable()) // Desactiva CSRF para facilitar pruebas con Postman/Frontend
            .authorizeHttpRequests(auth -> auth
                // Permitir Swagger y la documentación sin autenticación
                .requestMatchers(
                    "/swagger-ui/**",
                    "/v3/api-docs/**",
                    "/api-docs/**"
                ).permitAll()
                // Todo lo demás requiere autenticación
                .anyRequest().authenticated()
            )
            // Autenticación básica (usuario/contraseña en encabezado)
            .httpBasic(Customizer.withDefaults());

        return http.build();
    }
}
