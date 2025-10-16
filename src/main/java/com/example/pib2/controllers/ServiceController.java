package com.example.pib2.controllers;

import com.example.pib2.models.dtos.ServiceDTO;
import com.example.pib2.models.entities.Clinic;
import com.example.pib2.models.entities.Service;
import com.example.pib2.repositories.ClinicRepository;
import com.example.pib2.repositories.ServiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/services")
@CrossOrigin(origins = "*")
public class ServiceController {

    @Autowired
    private ServiceRepository serviceRepository;

    @Autowired
    private ClinicRepository clinicRepository;

    // 🔹 GET → Listar todos los servicios (en formato DTO)
    @GetMapping
    public List<ServiceDTO> getAllServices() {
        return serviceRepository.findAll()
                .stream()
                .map(ServiceDTO::fromEntity)
                .collect(Collectors.toList());
    }

    // 🔹 GET → Obtener un servicio por ID
    @GetMapping("/{id}")
    public ResponseEntity<ServiceDTO> getServiceById(@PathVariable Long id) {
        return serviceRepository.findById(id)
                .map(ServiceDTO::fromEntity)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // 🔹 POST → Crear un nuevo servicio
    @PostMapping
    public ResponseEntity<?> createService(@RequestBody ServiceDTO serviceDTO) {
        // Buscar la clínica relacionada
        Optional<Clinic> clinicOpt = clinicRepository.findById(serviceDTO.getClinicId());
        if (clinicOpt.isEmpty()) {
            return ResponseEntity.badRequest().body("❌ Clínica no encontrada con ID: " + serviceDTO.getClinicId());
        }

        // Convertir DTO a entidad y guardar
        Service service = serviceDTO.toEntity(clinicOpt.get());
        Service savedService = serviceRepository.save(service);

        return ResponseEntity.ok(ServiceDTO.fromEntity(savedService));
    }

    // 🔹 PUT → Actualizar un servicio existente
    @PutMapping("/{id}")
    public ResponseEntity<?> updateService(@PathVariable Long id, @RequestBody ServiceDTO serviceDTO) {
        Optional<Service> serviceOpt = serviceRepository.findById(id);
        if (serviceOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Service existingService = serviceOpt.get();

        // Si se incluye un ID de clínica, verificarla
        Clinic clinic = existingService.getClinic();
        if (serviceDTO.getClinicId() != null) {
            clinic = clinicRepository.findById(serviceDTO.getClinicId())
                    .orElse(null);
            if (clinic == null) {
                return ResponseEntity.badRequest().body("❌ Clínica no encontrada con ID: " + serviceDTO.getClinicId());
            }
        }

        // Actualizar campos
        existingService.setTitulo(serviceDTO.getTitulo());
        existingService.setImagen(serviceDTO.getImagen());
        existingService.setAlt(serviceDTO.getAlt());
        existingService.setDescripcion(serviceDTO.getDescripcion());
        existingService.setClinic(clinic);

        Service updatedService = serviceRepository.save(existingService);
        return ResponseEntity.ok(ServiceDTO.fromEntity(updatedService));
    }

    // 🔹 DELETE → Eliminar un servicio
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteService(@PathVariable Long id) {
        if (!serviceRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        serviceRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
