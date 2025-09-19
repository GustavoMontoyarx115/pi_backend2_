package com.example.pib2.config;

import com.example.pib2.models.entities.Appointment;
import com.example.pib2.models.entities.Clinic;
import com.example.pib2.models.entities.User;
import com.example.pib2.models.entities.User.Rol;
import com.example.pib2.repositories.AppointmentRepository;
import com.example.pib2.repositories.ClinicRepository;
import com.example.pib2.repositories.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;

@Configuration
public class AppointmentConfig {

    @Bean
    CommandLineRunner initAppointments(
            AppointmentRepository appointmentRepository,
            UserRepository userRepository,
            ClinicRepository clinicRepository
    ) {
        return args -> {
            if (appointmentRepository.count() == 0) {
                // ✅ Buscar si ya existe el usuario por email
                Optional<User> existingUser = Optional.ofNullable(userRepository.findByEmail("juan@example.com"));

                User user = existingUser.orElseGet(() -> {
                    User nuevo = new User(
                            "Juan Pérez",
                            "juan@example.com",
                            "123456",
                            "CC",
                            "123456789",
                            Rol.PACIENTE
                    );
                    return userRepository.save(nuevo);
                });

                // ✅ Crear clínica de prueba
                Clinic clinic = Clinic.builder()
                        .name("Clínica Central")
                        .description("Clínica especializada en dermatología")
                        .address("Calle 123 #45-67")
                        .phone("3201234567")
                        .email("contacto@clinicacentral.com")
                        .facebook("https://facebook.com/clinicacentral")
                        .instagram("https://instagram.com/clinicacentral")
                        .whatsapp("https://wa.me/3201234567")
                        .tiktok("https://tiktok.com/@clinicacentral")
                        .build();
                clinicRepository.save(clinic);

          
 Appointment cita = new Appointment();
                cita.setNombre("Cita inicial");
                cita.setMedico("Dra. Martínez");
                cita.setFecha(LocalDate.of(2025, 9, 20));
                cita.setHora(LocalTime.of(10, 0));
                cita.setUser(user);      // usuario existente
                cita.setClinic(clinic);  // clínica existente
                cita.setCorreo("correo@ejemplo.com"); // ✅ ESTE ES EL NUEVO CAMPO OBLIGATORIO

                    appointmentRepository.save(cita);


                System.out.println("✅ Datos iniciales cargados en Appointment");
            }
        };
    }
}
