package com.example.pib2.controllers;


import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.pib2.models.dtos.ClinicDTO;
import com.example.pib2.models.entities.Clinic;
import com.example.pib2.servicios.ClinicService;

@RestController
@RequestMapping("/api/clinics")
@CrossOrigin(origins = "http://localhost:3000") // Permite peticiones desde el frontend (React)
public class ClinicController {

    @Autowired
    private ClinicService clinicService;

    /**
     * 🔹 GET: Obtiene todas las clínicas.
     */
    @GetMapping
    public ResponseEntity<List<ClinicDTO>> getAllClinics() {
        List<ClinicDTO> clinics = clinicService.findAll()
                .stream()
                .map(ClinicDTO::fromEntity)
                .collect(Collectors.toList());
        return ResponseEntity.ok(clinics);
    }

    /**
     * 🔹 GET: Obtiene una clínica por su ID.
     */
    @GetMapping("/{id}")
    public ResponseEntity<ClinicDTO> getClinicById(@PathVariable Long id) {
        Clinic clinic = clinicService.findById(id);
        if (clinic == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(ClinicDTO.fromEntity(clinic));
    }

    /**
     * 🔹 POST: Crea una nueva clínica.
     */
    @PostMapping
    public ResponseEntity<ClinicDTO> createClinic(@RequestBody ClinicDTO clinicDTO) {
        Clinic clinic = clinicDTO.toEntity();
        Clinic savedClinic = clinicService.save(clinic);
        return ResponseEntity.ok(ClinicDTO.fromEntity(savedClinic));
    }

    /**
     * 🔹 PUT: Actualiza una clínica existente.
     */
    @PutMapping("/{id}")
    public ResponseEntity<ClinicDTO> updateClinic(@PathVariable Long id, @RequestBody ClinicDTO clinicDTO) {
        Clinic existingClinic = clinicService.findById(id);
        if (existingClinic == null) {
            return ResponseEntity.notFound().build();
        }

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
        return ResponseEntity.ok(ClinicDTO.fromEntity(updatedClinic));
    }

    /**
     * 🔹 DELETE: Elimina una clínica por ID.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteClinic(@PathVariable Long id) {
        Clinic clinic = clinicService.findById(id);
        if (clinic == null) {
            return ResponseEntity.notFound().build();
        }
        clinicService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
