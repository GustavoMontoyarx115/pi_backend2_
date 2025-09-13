package com.example.pib2.config;


import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.example.pib2.models.entities.User;
import com.example.pib2.models.entities.User.Rol;
import com.example.pib2.repositories.UserRepository;

@Configuration
public class UserConfig {

    /**
     * Este bean se ejecuta cuando arranca la aplicación.
     * Sirve para inicializar datos en la base de datos.
     */
    @Bean
    CommandLineRunner initUsers(UserRepository userRepository) {
        return args -> {
            if (userRepository.count() == 0) {
                User admin = new User(
                        "Administrador",
                        "admin@clinica.com",
                        "admin123",   // ⚠️ En la vida real usa BCryptPasswordEncoder
                        "CC",
                        "1000000000",
                        Rol.ADMIN
                );

                User medico = new User(
                        "Dr. Juan Pérez",
                        "jperez@clinica.com",
                        "medico123",
                        "CC",
                        "2000000000",
                        Rol.MEDICO
                );

                User paciente = new User(
                        "María López",
                        "maria@correo.com",
                        "paciente123",
                        "TI",
                        "3000000000",
                        Rol.PACIENTE
                );

                userRepository.save(admin);
                userRepository.save(medico);
                userRepository.save(paciente);

                System.out.println("Usuarios iniciales cargados en la BD ✅");
            }
        };
    }
}
