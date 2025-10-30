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

    // ✅ Usuarios de prueba
    @Bean
    public UserDetailsService userDetailsService() {
        UserDetails admin = User.withUsername("admin")
                .password("{noop}admin123")
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

    // ✅ Configuración principal de seguridad
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            // 🔹 Habilitar CORS antes de los filtros de seguridad
            .cors(Customizer.withDefaults())
            // 🔹 Desactivar CSRF (recomendado para APIs REST)
            .csrf(csrf -> csrf.disable())
            // 🔹 Autorizar rutas
            .authorizeHttpRequests(auth -> auth
                .requestMatchers(
                    "/api/**",
                    "/swagger-ui/**",
                    "/v3/api-docs/**",
                    "/h2-console/**"
                ).permitAll()
                .anyRequest().authenticated()
            )
            // 🔹 Permitir iframes (para consola H2)
            .headers(headers -> headers.frameOptions(frame -> frame.disable()))
            // 🔹 Autenticación básica HTTP
            .httpBasic(Customizer.withDefaults());

        return http.build();
    }

    // ✅ Configuración global de CORS
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();

        // 🔹 Permitir orígenes (frontend local y Render)
        config.setAllowedOrigins(List.of(
                "http://localhost:3000",
                "https://pi-web2.onrender.com"
        ));

        // 🔹 Métodos permitidos
        config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));

        // 🔹 Cabeceras permitidas
        config.setAllowedHeaders(List.of("Authorization", "Content-Type"));

        // 🔹 Cabeceras expuestas (para respuesta)
        config.setExposedHeaders(List.of("Authorization", "Content-Type"));

        // 🔹 Permitir credenciales (cookies, tokens)
        config.setAllowCredentials(true);

        // Registrar configuración para todas las rutas
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);

        return source;
    }
}
