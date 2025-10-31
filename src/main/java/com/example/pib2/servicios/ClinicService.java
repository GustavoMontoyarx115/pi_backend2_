package com.example.pib2.servicios;

import com.example.pib2.models.entities.Clinic;
import com.example.pib2.repositories.ClinicRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClinicService {

    private final ClinicRepository clinicRepository;

    public ClinicService(ClinicRepository clinicRepository) {
        this.clinicRepository = clinicRepository;
    }

    public List<Clinic> findAll() {
        return clinicRepository.findAll();
    }

    public Clinic findById(Long id) {
        return clinicRepository.findById(id).orElse(null);
    }

    public Clinic save(Clinic clinic) {
        return clinicRepository.save(clinic);
    }

    public Clinic update(Clinic clinic) {
        if (clinic.getId() == null || !clinicRepository.existsById(clinic.getId())) {
            throw new IllegalArgumentException("No se puede actualizar: la clínica no existe.");
        }
        return clinicRepository.save(clinic);
    }

    public void deleteById(Long id) {
        if (!clinicRepository.existsById(id)) {
            throw new IllegalArgumentException("No se puede eliminar: la clínica no existe.");
        }
        clinicRepository.deleteById(id);
    }
}
