package com.example.pib2.controllers;

import com.example.pib2.models.entities.Service;
import com.example.pib2.repositories.ServiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/services")
@CrossOrigin(origins = "*") // 🔹 Permite llamadas desde React u otro frontend
public class ServiceController {

    @Autowired
    private ServiceRepository serviceRepository;

    // 🔹 GET → Listar todos los servicios
    @GetMapping
    public List<Service> getAllServices() {
        return serviceRepository.findAll();
    }

    // 🔹 GET → Obtener un servicio por ID
    @GetMapping("/{id}")
    public ResponseEntity<Service> getServiceById(@PathVariable Long id) {
        Optional<Service> service = serviceRepository.findById(id);
        return service.map(ResponseEntity::ok)
                      .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // 🔹 POST → Crear un nuevo servicio
    @PostMapping
    public Service createService(@RequestBody Service service) {
        return serviceRepository.save(service);
    }

    // 🔹 PUT → Actualizar un servicio existente
    @PutMapping("/{id}")
    public ResponseEntity<Service> updateService(@PathVariable Long id, @RequestBody Service serviceDetails) {
        Optional<Service> service = serviceRepository.findById(id);

        if (service.isPresent()) {
            Service updatedService = service.get();
            updatedService.setTitulo(serviceDetails.getTitulo());
            updatedService.setImagen(serviceDetails.getImagen());
            updatedService.setAlt(serviceDetails.getAlt());
            updatedService.setDescripcion(serviceDetails.getDescripcion());
            return ResponseEntity.ok(serviceRepository.save(updatedService));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // 🔹 DELETE → Eliminar un servicio
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteService(@PathVariable Long id) {
        if (serviceRepository.existsById(id)) {
            serviceRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}

