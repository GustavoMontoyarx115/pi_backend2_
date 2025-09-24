package com.example.pib2.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@Profile("dev") // Solo se aplica en desarrollo
public class WebSecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            // Permitir la consola de H2
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/h2-console/**").permitAll()
                .anyRequest().authenticated()
            )
            // Deshabilitar CSRF solo para H2
            .csrf(csrf -> csrf
                .ignoringRequestMatchers("/h2-console/**")
            )
            // Permitir iframes para H2 console
            .headers(headers -> headers
                .frameOptions(frame -> frame.sameOrigin())
            )
            // Deshabilitar login por formulario
            .formLogin(Customizer.withDefaults());

        return http.build();
    }
}
