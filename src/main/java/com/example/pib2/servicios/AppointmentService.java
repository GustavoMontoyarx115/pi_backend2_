package com.example.pib2.servicios;


import com.example.pib2.models.entities.Appointment;
import com.example.pib2.models.entities.User;
import com.example.pib2.models.entities.Clinic;
import com.example.pib2.repositories.AppointmentRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class AppointmentService {

    @Autowired
    private AppointmentRepository appointmentRepository;

    /**
     * 🔹 Obtener todas las citas
     */
    public List<Appointment> findAll() {
        return appointmentRepository.findAll();
    }

    /**
     * 🔹 Buscar cita por ID
     */
    public Appointment findById(Long id) {
        return appointmentRepository.findById(id).orElse(null);
    }

    /**
     * 🔹 Guardar nueva cita
     */
    public Appointment save(Appointment appointment) {
        return appointmentRepository.save(appointment);
    }

    /**
     * 🔹 Actualizar cita existente
     */
    public Appointment update(Appointment appointment) {
        if (appointment.getId() == null || !appointmentRepository.existsById(appointment.getId())) {
            throw new IllegalArgumentException("No se puede actualizar: la cita no existe.");
        }
        return appointmentRepository.save(appointment);
    }

    /**
     * 🔹 Eliminar cita por ID
     */
    public void delete(Long id) {
        if (!appointmentRepository.existsById(id)) {
            throw new IllegalArgumentException("No se puede eliminar: la cita no existe.");
        }
        appointmentRepository.deleteById(id);
    }

    /**
     * 🔹 Buscar citas por usuario
     */
    public List<Appointment> findByUser(User user) {
        return appointmentRepository.findByUser(user);
    }

    /**
     * 🔹 Buscar citas por clínica
     */
    public List<Appointment> findByClinic(Clinic clinic) {
        return appointmentRepository.findByClinic(clinic);
    }

    /**
     * 🔹 Buscar citas por fecha
     */
    public List<Appointment> findByFecha(LocalDate fecha) {
        return appointmentRepository.findByFecha(fecha);
    }

    /**
     * 🔹 Buscar citas por médico
     */
    public List<Appointment> findByMedico(String medico) {
        return appointmentRepository.findByMedico(medico);
    }
}
