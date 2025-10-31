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
            // 🔹 Habilitamos CORS y desactivamos CSRF para APIs REST
            .cors(Customizer.withDefaults())
            .csrf(csrf -> csrf.disable())
            
            // 🔹 Permitimos todas las rutas públicas de la API
            .authorizeHttpRequests(auth -> auth
                .requestMatchers(
                    "/api/**",          // API pública
                    "/swagger-ui/**",   // Swagger UI
                    "/v3/api-docs/**",  // OpenAPI docs
                    "/h2-console/**"    // H2-console
                ).permitAll()
                .anyRequest().permitAll()   // Permite todo lo demás
            )
            
            // 🔹 Desactivamos login, form y basic auth
            .httpBasic(httpBasic -> httpBasic.disable())
            .formLogin(form -> form.disable())
            
            // 🔹 Permite acceso al H2-console en iframe
            .headers(headers -> headers.frameOptions(frame -> frame.disable()));

        return http.build();
    }

    // ✅ Configuración de CORS para frontend en Vercel y localhost
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();

        // 🔹 Orígenes permitidos
        config.setAllowedOriginPatterns(List.of(
            "https://pi-web2-brown.vercel.app", // frontend producción
            "http://localhost:3000"              // frontend local
        ));

        // 🔹 Métodos HTTP permitidos
        config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));

        // 🔹 Headers permitidos y expuestos
        config.setAllowedHeaders(List.of("*"));
        config.setExposedHeaders(List.of("Authorization", "Content-Type"));

        // 🔹 Permite enviar cookies o headers de autenticación
        config.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return source;
    }
}
