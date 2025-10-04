package com.example.pib2.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("📚 Clínica Dermatológica API")
                        .version("v1.0.0")
                        .description("🔧🔧Documentación oficial del backend para la Clínica Dermatológica. Incluye endpoints de usuarios, clínicas, citas y servicios.")
                        .contact(new Contact()
                                .name("Equipo de Desarrollo")
                                .url("https://github.com/GustavoMontoyarx115/pi_backend2_.git")
                                .email("ClinicaDerma@gmail.com"))
                        .license(new License()
                                .name("Apache 2.0")
                                .url("http://springdoc.org"))
                )
                .servers(List.of(
                        new Server().url("http://localhost:8080").description("Servidor local"),
                        new Server().url("https://api.tuservicio.com").description("Servidor de producción")
                ));
    }
}