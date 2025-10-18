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
            // ⚠️ Solo cargar datos si no existen citas previas
            if (appointmentRepository.count() == 0) {

                System.out.println("⏳ Iniciando carga de datos iniciales...");

                // ✅ Verificar o crear usuario base
                Optional<User> existingUser = Optional.empty();

                User user = existingUser.orElseGet(() -> {
                    User nuevo = new User(
                            "Juan Pérez",
                            "juan@example.com",
                            "123456", // ⚠️ En producción, usa contraseñas encriptadas
                            "CC",
                            "123456789",
                            Rol.PACIENTE
                    );
                    System.out.println("👤 Usuario creado: " + nuevo.getEmail());
                    return userRepository.save(nuevo);
                });

                // ✅ Crear clínica base si no existe
                Clinic clinic = clinicRepository.findAll().stream()
                        .findFirst()
                        .orElseGet(() -> {
                            Clinic nuevaClinica = Clinic.builder()
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
                            System.out.println("🏥 Clínica creada: " + nuevaClinica.getName());
                            return clinicRepository.save(nuevaClinica);
                        });

                // ✅ Crear cita base
                Appointment cita = new Appointment();
                cita.setNombre("Cita inicial de revisión");
                cita.setMedico("Dra. Martínez");
                cita.setFecha(LocalDate.of(2025, 9, 20));
                cita.setHora(LocalTime.of(10, 0));
                cita.setCorreo("correo@ejemplo.com");
                cita.setUser(user);
                cita.setClinic(clinic);

                appointmentRepository.save(cita);

                System.out.println("✅ Datos iniciales cargados correctamente en Appointment");
            } else {
                System.out.println("⚙️ Los datos ya existen. No se cargaron registros iniciales.");
            }
        };
    }
}
