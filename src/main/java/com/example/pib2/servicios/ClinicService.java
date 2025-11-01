package com.example.pib2.servicios;

import com.example.pib2.models.entities.Clinic;
import com.example.pib2.repositories.ClinicRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ClinicService {

    private final ClinicRepository clinicRepository;

    public ClinicService(ClinicRepository clinicRepository) {
        this.clinicRepository = clinicRepository;
    }

    public List<Clinic> findAll() {
        return clinicRepository.findAll();
    }

    public Clinic findById(Long id) {
        if (id == null) return null;
        return clinicRepository.findById(id).orElse(null);
    }

    public Clinic save(Clinic clinic) {
        if (clinic == null) throw new IllegalArgumentException("La clínica no puede ser nula");
        return clinicRepository.save(clinic);
    }

    public Clinic update(Clinic clinic) {
        if (clinic.getId() == null || !clinicRepository.existsById(clinic.getId())) {
            throw new IllegalArgumentException("No se puede actualizar: la clínica no existe.");
        }
        return clinicRepository.save(clinic);
    }

    public void delete(Long id) {
        if (id == null) throw new IllegalArgumentException("Id null al eliminar clínica");
        if (!clinicRepository.existsById(id)) {
            throw new IllegalArgumentException("No se puede eliminar: la clínica no existe.");
        }
        clinicRepository.deleteById(id);
    }

    public Optional<Clinic> findByName(String name) {
        return clinicRepository.findByName(name);
    }
}
