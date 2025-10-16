package com.example.pib2.controllers;


import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.example.pib2.models.dtos.AppointmentDTO;
import com.example.pib2.models.entities.Appointment;
import com.example.pib2.models.entities.Clinic;
import com.example.pib2.servicios.AppointmentService;
import com.example.pib2.servicios.ClinicService;

@RestController
@RequestMapping("/api/appointments")
@CrossOrigin(origins = "http://localhost:3000") // Permite llamadas desde el frontend
public class AppointmentControllers {

    @Autowired
    private AppointmentService appointmentService;

    @Autowired
    private ClinicService clinicService;

    // ✅ Obtener todas las citas como DTOs
    @GetMapping
    public List<AppointmentDTO> getAllAppointments() {
        return appointmentService.findAll()
                .stream()
                .map(AppointmentDTO::fromEntity) // convierte entidad -> DTO
                .collect(Collectors.toList());
    }

    // ✅ Obtener una cita por ID
    @GetMapping("/{id}")
    public AppointmentDTO getAppointmentById(@PathVariable Long id) {
        Appointment appointment = appointmentService.findById(id);
        return AppointmentDTO.fromEntity(appointment);
    }

    // ✅ Crear una nueva cita
    @PostMapping
    public AppointmentDTO createAppointment(@RequestBody AppointmentDTO dto) {
        Clinic clinic = clinicService.findById(dto.getClinicId());
        Appointment appointment = dto.toEntity(clinic);
        Appointment saved = appointmentService.save(appointment);
        return AppointmentDTO.fromEntity(saved);
    }

    // ✅ Actualizar una cita existente
    @PutMapping("/{id}")
    public AppointmentDTO updateAppointment(@PathVariable Long id, @RequestBody AppointmentDTO dto) {
        Clinic clinic = clinicService.findById(dto.getClinicId());
        Appointment updatedAppointment = dto.toEntity(clinic);
        updatedAppointment.setId(id);
        Appointment updated = appointmentService.update(updatedAppointment);
        return AppointmentDTO.fromEntity(updated);
    }

    // ✅ Eliminar una cita por ID
    @DeleteMapping("/{id}")
    public void deleteAppointment(@PathVariable Long id) {
        appointmentService.delete(id);
    }
}
