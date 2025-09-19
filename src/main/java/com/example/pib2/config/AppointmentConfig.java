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
                // Crear usuario de prueba
                User user = new User(
                        "Juan Pérez",
                        "juan@example.com",
                        "123456",
                        "CC",
                        "123456789",
                        Rol.PACIENTE
                );
                userRepository.save(user);

                // Crear clínica de prueba
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

                // Crear cita de prueba
                Appointment cita = new Appointment();
                cita.setNombre("Cita inicial");
                cita.setFecha(LocalDate.now().plusDays(1));
                cita.setHora(LocalTime.of(10, 0));
                cita.setMedico("Dra. Martínez");
                cita.setUser(user);
                cita.setClinic(clinic);

                appointmentRepository.save(cita);

                System.out.println("✅ Datos iniciales cargados en Appointment");
            }
        };
    }
}
