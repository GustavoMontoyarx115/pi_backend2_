package com.example.pib2.controllers;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.example.pib2.models.dtos.ClinicDTO;
import com.example.pib2.models.entities.Clinic;
import com.example.pib2.servicios.ClinicService;

@RestController
@RequestMapping("/api/clinics")
@CrossOrigin(origins = "http://localhost:3000") // Permite peticiones desde tu frontend React
public class ClinicController {

    @Autowired
    private ClinicService clinicService;

    /**
     * 🔹 GET: Obtiene todas las clínicas.
     * Convierte las entidades a DTOs antes de enviarlas al frontend.
     */
    @GetMapping
    public List<ClinicDTO> getAllClinics() {
        List<Clinic> clinics = clinicService.findAll();
        return clinics.stream()
                .map(ClinicDTO::fromEntity)
                .collect(Collectors.toList());
    }

    /**
     * 🔹 GET: Obtiene una clínica por su ID.
     */
    @GetMapping("/{id}")
    public ClinicDTO getClinicById(@PathVariable Long id) {
        Clinic clinic = clinicService.findById(id);
        return ClinicDTO.fromEntity(clinic);
    }

    /**
     * 🔹 POST: Crea una nueva clínica.
     */
    @PostMapping
    public ClinicDTO createClinic(@RequestBody ClinicDTO clinicDTO) {
        Clinic clinic = clinicDTO.toEntity();
        Clinic savedClinic = clinicService.save(clinic);
        return ClinicDTO.fromEntity(savedClinic);
    }

    /**
     * 🔹 PUT: Actualiza una clínica existente.
     */
    @PutMapping("/{id}")
    public ClinicDTO updateClinic(@PathVariable Long id, @RequestBody ClinicDTO clinicDTO) {
        Clinic existingClinic = clinicService.findById(id);

        // Actualiza los campos de la entidad
        existingClinic.setName(clinicDTO.getName());
        existingClinic.setDescription(clinicDTO.getDescription());
        existingClinic.setAddress(clinicDTO.getAddress());
        existingClinic.setCity(clinicDTO.getCity());
        existingClinic.setPhone(clinicDTO.getPhone());
        existingClinic.setEmail(clinicDTO.getEmail());
        existingClinic.setFacebook(clinicDTO.getFacebook());
        existingClinic.setInstagram(clinicDTO.getInstagram());
        existingClinic.setWhatsapp(clinicDTO.getWhatsapp());
        existingClinic.setTiktok(clinicDTO.getTiktok());

        Clinic updatedClinic = clinicService.save(existingClinic);
        return ClinicDTO.fromEntity(updatedClinic);
    }

    /**
     * 🔹 DELETE: Elimina una clínica por ID.
     */
    @DeleteMapping("/{id}")
    public void deleteClinic(@PathVariable Long id) {
        clinicService.deleteById(id);
    }
}
