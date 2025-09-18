package com.example.pib2.servicios;

import com.example.pib2.models.entities.Appointment;
import com.example.pib2.models.entities.User;
import com.example.pib2.models.entities.Clinic;
import com.example.pib2.repositories.AppointmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class AppointmentService {

    @Autowired
    private AppointmentRepository appointmentRepository;

    // 🔹 Obtener todas las citas
    public List<Appointment> findAll() {
        return appointmentRepository.findAll();
    }

    // 🔹 Buscar cita por ID
    public Optional<Appointment> findById(Long id) {
        return appointmentRepository.findById(id);
    }

    // 🔹 Guardar o actualizar cita
    public Appointment save(Appointment appointment) {
        return appointmentRepository.save(appointment);
    }

    // 🔹 Eliminar cita
    public void deleteById(Long id) {
        appointmentRepository.deleteById(id);
    }

    // 🔹 Buscar todas las citas de un usuario
    public List<Appointment> findByUser(User user) {
        return appointmentRepository.findByUser(user);
    }

    // 🔹 Buscar todas las citas de una clínica
    public List<Appointment> findByClinic(Clinic clinic) {
        return appointmentRepository.findByClinic(clinic);
    }

    // 🔹 Buscar todas las citas en una fecha específica
    public List<Appointment> findByFecha(LocalDate fecha) {
        return appointmentRepository.findByFecha(fecha);
    }

    // 🔹 Buscar todas las citas de un médico específico
    public List<Appointment> findByMedico(String medico) {
        return appointmentRepository.findByMedico(medico);
    }
}
