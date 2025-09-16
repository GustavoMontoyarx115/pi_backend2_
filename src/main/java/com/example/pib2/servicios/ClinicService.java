package com.example.pib2.servicios;

import com.example.pib2.models.entities.Clinic;
import com.example.pib2.repositories.ClinicRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClinicService {

    private final ClinicRepository clinicRepository;

    // Constructor para inyección de dependencias
    public ClinicService(ClinicRepository clinicRepository) {
        this.clinicRepository = clinicRepository;
    }

    // 🔹 Obtener todas las clínicas
    public List<Clinic> findAll() {
        return clinicRepository.findAll();
    }

    // 🔹 Guardar o actualizar una clínica
    public Clinic save(Clinic clinic) {
        return clinicRepository.save(clinic);
    }

    // 🔹 Buscar una clínica por ID
    public Optional<Clinic> findById(Long id) {
        return clinicRepository.findById(id);
    }

    // 🔹 Eliminar una clínica por ID
    public void deleteById(Long id) {
        clinicRepository.deleteById(id);
    }
}

