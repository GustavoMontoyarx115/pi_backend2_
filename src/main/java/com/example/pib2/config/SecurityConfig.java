package com.example.pib2.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            // 🔹 Habilitamos CORS y desactivamos CSRF (necesario para APIs REST)
            .cors(Customizer.withDefaults())
            .csrf(csrf -> csrf.disable())
            // 🔹 Permitimos todas las rutas públicas en la API
            .authorizeHttpRequests(auth -> auth
                .requestMatchers(
                    "/api/**",
                    "/swagger-ui/**",
                    "/v3/api-docs/**",
                    "/h2-console/**"
                ).permitAll()
                .anyRequest().permitAll() // Permite todo el resto también
            )
            // 🔹 Desactivamos cualquier autenticación (para evitar 401)
            .httpBasic(httpBasic -> httpBasic.disable())
            .formLogin(form -> form.disable())
            // 🔹 Habilitamos acceso al H2-console y a Swagger sin bloqueo
            .headers(headers -> headers.frameOptions(frame -> frame.disable()));

        return http.build();
    }

    // ✅ Configuración de CORS universal para Render y localhost
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();

        // 🔥 Permitir orígenes tanto locales como en producción
        config.addAllowedOriginPattern("*");
        config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        config.setAllowedHeaders(List.of("*"));
        config.setExposedHeaders(List.of("Authorization", "Content-Type"));
        config.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return source;
    }
}
