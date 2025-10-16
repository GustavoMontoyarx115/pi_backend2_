package com.example.pib2.servicios;


import com.example.pib2.models.entities.Clinic;
import com.example.pib2.repositories.ClinicRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClinicService {

    private final ClinicRepository clinicRepository;

    // ✅ Inyección de dependencias por constructor
    public ClinicService(ClinicRepository clinicRepository) {
        this.clinicRepository = clinicRepository;
    }

    /**
     * 🔹 Obtener todas las clínicas
     */
    public List<Clinic> findAll() {
        return clinicRepository.findAll();
    }

    /**
     * 🔹 Buscar clínica por ID
     */
    public Clinic findById(Long id) {
        return clinicRepository.findById(id).orElse(null);
    }

    /**
     * 🔹 Guardar una nueva clínica
     */
    public Clinic save(Clinic clinic) {
        return clinicRepository.save(clinic);
    }

    /**
     * 🔹 Actualizar una clínica existente
     */
    public Clinic update(Clinic clinic) {
        if (clinic.getId() == null || !clinicRepository.existsById(clinic.getId())) {
            throw new IllegalArgumentException("No se puede actualizar: la clínica no existe.");
        }
        return clinicRepository.save(clinic);
    }

    /**
     * 🔹 Eliminar clínica por ID
     */
    public void deleteById(Long id) {
        if (!clinicRepository.existsById(id)) {
            throw new IllegalArgumentException("No se puede eliminar: la clínica no existe.");
        }
        clinicRepository.deleteById(id);
    }
}
