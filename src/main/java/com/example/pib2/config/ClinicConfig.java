package com.example.pib2.config;

import com.example.pib2.models.entities.Clinic;
import com.example.pib2.repositories.ClinicRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ClinicConfig {

    @Bean
    CommandLineRunner initClinics(ClinicRepository clinicRepository) {
        return args -> {
            if (clinicRepository.count() == 0) {
                Clinic defaultClinic = new Clinic(
                        "Clínica Dermatológica Central",
                        "Calle 123",
                        "123456789"
                );
                defaultClinic.setCity("Medellín");
                defaultClinic.setEmail("contacto@clinicacentral.com");
                defaultClinic.setDescription("Clínica especializada en dermatología con servicios de alta calidad.");
                defaultClinic.setFacebook("https://facebook.com/clinicacentral");
                defaultClinic.setInstagram("https://instagram.com/clinicacentral");
                defaultClinic.setWhatsapp("+573001112233");
                defaultClinic.setTiktok("https://tiktok.com/@clinicacentral");

                clinicRepository.save(defaultClinic);
                System.out.println("✅ Clínica por defecto creada: " + defaultClinic.getName());
            } else {
                System.out.println("ℹ Ya existen clínicas registradas, no se creó ninguna por defecto.");
            }
        };
    }
}
