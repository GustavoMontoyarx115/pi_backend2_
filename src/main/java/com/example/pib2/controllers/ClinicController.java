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
@CrossOrigin(origins = "*") // Permite peticiones desde cualquier frontend
public class ClinicController {

    @Autowired
    private ClinicService clinicService;

    // ✅ GET: Todas las clínicas
    @GetMapping
    public ResponseEntity<List<ClinicDTO>> getAllClinics() {
        List<ClinicDTO> clinics = clinicService.findAll()
                .stream()
                .map(ClinicDTO::fromEntity)
                .collect(Collectors.toList());
        return ResponseEntity.ok(clinics);
    }

    // ✅ GET: Clínica por ID
    @GetMapping("/{id}")
    public ResponseEntity<ClinicDTO> getClinicById(@PathVariable Long id) {
        Clinic clinic = clinicService.findById(id);
        if (clinic == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(ClinicDTO.fromEntity(clinic));
    }

    // ✅ POST: Crear clínica nueva
    @PostMapping
    public ResponseEntity<?> createClinic(@RequestBody ClinicDTO clinicDTO) {
        try {
            if (clinicDTO.getName() == null || clinicDTO.getAddress() == null) {
                return ResponseEntity.badRequest().body("⚠️ Nombre y dirección son obligatorios");
            }

            Clinic clinic = clinicDTO.toEntity();
            Clinic savedClinic = clinicService.save(clinic);
            return ResponseEntity.ok(ClinicDTO.fromEntity(savedClinic));

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().body("❌ Error al crear la clínica: " + e.getMessage());
        }
    }

    // ✅ PUT: Actualizar clínica existente
    @PutMapping("/{id}")
    public ResponseEntity<?> updateClinic(@PathVariable Long id, @RequestBody ClinicDTO clinicDTO) {
        try {
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

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().body("❌ Error al actualizar la clínica: " + e.getMessage());
        }
    }

    // ✅ DELETE: Eliminar clínica
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteClinic(@PathVariable Long id) {
        try {
            Clinic clinic = clinicService.findById(id);
            if (clinic == null) {
                return ResponseEntity.notFound().build();
            }
            clinicService.delete(clinic.getId());
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().body("❌ Error al eliminar la clínica: " + e.getMessage());
        }
    }
}
