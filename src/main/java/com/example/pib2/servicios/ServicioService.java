package com.example.pib2.servicios;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import com.example.pib2.models.entities.Service;
import com.example.pib2.repositories.ServiceRepository;

@ServiceAnnotation
public class ServicioService {

    @Autowired
    private ServiceRepository serviceRepository;

    // 🔹 Obtener todos los servicios
    public List<Service> getAllServices() {
        return serviceRepository.findAll();
    }

    // 🔹 Obtener un servicio por ID
    public Optional<Service> getServiceById(Long id) {
        return serviceRepository.findById(id);
    }

    // 🔹 Crear un nuevo servicio
    public Service createService(Service service) {
        return serviceRepository.save(service);
    }

    // 🔹 Actualizar un servicio existente
    public Optional<Service> updateService(Long id, Service serviceDetails) {
        return serviceRepository.findById(id).map(service -> {
            service.setTitulo(serviceDetails.getTitulo());
            service.setImagen(serviceDetails.getImagen());
            service.setAlt(serviceDetails.getAlt());
            service.setDescripcion(serviceDetails.getDescripcion());
            return serviceRepository.save(service);
        });
    }

    // 🔹 Eliminar un servicio
    public boolean deleteService(Long id) {
        if (serviceRepository.existsById(id)) {
            serviceRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
