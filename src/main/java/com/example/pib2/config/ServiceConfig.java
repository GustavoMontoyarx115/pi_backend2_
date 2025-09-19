package com.example.pib2.config;

import com.example.pib2.models.entities.Service;
import com.example.pib2.models.entities.Clinic;
import com.example.pib2.repositories.ServiceRepository;
import com.example.pib2.repositories.ClinicRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ServiceConfig {

    @Bean
    CommandLineRunner initServices(ServiceRepository serviceRepository, ClinicRepository clinicRepository) {
        return args -> {
            if (serviceRepository.count() == 0) {
                // ⚠️ Verificamos que exista al menos una clínica
                Clinic clinic = clinicRepository.findAll().stream().findFirst()
                        .orElseGet(() -> clinicRepository.save(new Clinic("Clínica Dermatológica Central", "Calle 123", "123456789")));

                Service consultaGeneral = new Service(
                        "Consulta Dermatológica General",
                        "consulta.jpg",
                        "Imagen de consulta dermatológica",
                        "Atención integral para diagnóstico y tratamiento de problemas de la piel.",
                        clinic
                );

                Service tratamientoAcne = new Service(
                        "Tratamiento de Acné",
                        "acne.jpg",
                        "Imagen de tratamiento de acné",
                        "Tratamiento especializado para control y mejoría del acné en adolescentes y adultos.",
                        clinic
                );

                Service cirugiaEstetica = new Service(
                        "Cirugía Estética Menor",
                        "cirugia.jpg",
                        "Imagen de cirugía estética",
                        "Procedimientos quirúrgicos menores para mejorar la estética de la piel.",
                        clinic
                );

                serviceRepository.save(consultaGeneral);
                serviceRepository.save(tratamientoAcne);
                serviceRepository.save(cirugiaEstetica);

                System.out.println("✅ Servicios iniciales cargados en la BD.");
            }
        };
    }
}
