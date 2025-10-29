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
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
public class SecurityConfig {

    // ✅ Configuración de usuarios en memoria (solo para pruebas)
    @Bean
    public UserDetailsService userDetailsService() {
        UserDetails admin = User.withUsername("admin")
                .password("{noop}admin123") // {noop} = sin encriptar (solo para desarrollo)
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

    // ✅ Configuración principal de seguridad HTTP
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .cors(Customizer.withDefaults()) // habilita CORS con la configuración del bean de abajo
            .csrf(csrf -> csrf.disable())    // desactiva CSRF (útil para APIs REST)
            .authorizeHttpRequests(auth -> auth
                // Endpoints públicos
                .requestMatchers(
                    "/h2-console/**",
                    "/swagger-ui/**",
                    "/v3/api-docs/**",
                    "/api-docs/**",
                    "/api/**" // 🔹 Permitir temporalmente todos los endpoints API (útil para pruebas)
                ).permitAll()
                // Todo lo demás requiere autenticación
                .anyRequest().authenticated()
            )
            // permite iframes (para H2 Console)
            .headers(headers -> headers.frameOptions(frame -> frame.disable()))
            // autenticación básica HTTP
            .httpBasic(Customizer.withDefaults());

        return http.build();
    }

    // ✅ Configuración global de CORS (Frontend + Backend)
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();

        // 🔹 URLs que pueden acceder a tu API
        configuration.setAllowedOrigins(List.of(
                "http://localhost:3000",             // Frontend local
                "https://pi-frontend2.onrender.com"  // Frontend en Render (ajusta si tu URL cambia)
        ));

        // 🔹 Métodos HTTP permitidos
        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));

        // 🔹 Headers permitidos
        configuration.setAllowedHeaders(List.of("Authorization", "Content-Type"));

        // 🔹 Permitir credenciales (si usas cookies o auth básica)
        configuration.setAllowCredentials(true);

        // 🔹 Aplica esta configuración a todas las rutas
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);

        return source;
    }
}
