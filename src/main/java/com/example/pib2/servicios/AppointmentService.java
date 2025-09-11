package com.example.pib2.servicios;

import com.example.pib2.models.entities.Appointment;
import com.example.pib2.repositories.AppointmentRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AppointmentService {

    private final AppointmentRepository appointmentRepository;

    // Inyección de dependencias por constructor
    public AppointmentService(AppointmentRepository appointmentRepository) {
        this.appointmentRepository = appointmentRepository;
    }

    // Crear o actualizar una cita
    public Appointment save(Appointment appointment) {
        return appointmentRepository.save(appointment);
    }

    // Listar todas las citas
    public List<Appointment> findAll() {
        return appointmentRepository.findAll();
    }

    // Buscar una cita por ID
    public Optional<Appointment> findById(Long id) {
        return appointmentRepository.findById(id);
    }

    // Eliminar una cita por ID
    public void delete(Long id) {
        appointmentRepository.deleteById(id);
    }
}
