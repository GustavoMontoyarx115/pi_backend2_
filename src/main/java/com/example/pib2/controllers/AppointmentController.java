package com.example.pib2.controllers;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.pib2.models.dtos.AppointmentDTO;
import com.example.pib2.models.entities.Appointment;
import com.example.pib2.models.entities.Clinic;
import com.example.pib2.servicios.AppointmentService;
import com.example.pib2.servicios.ClinicService;

@RestController
@RequestMapping("/api/appointments")
@CrossOrigin(origins = {
    "http://localhost:3000",          // Permite peticiones desde entorno local
    "https://pi-web2.onrender.com"    // Permite peticiones desde el frontend en Render
})
public class AppointmentController {

    @Autowired
    private AppointmentService appointmentService;

    @Autowired
    private ClinicService clinicService;

    /**
     * 🔹 GET: Obtiene todas las citas.
     */
    @GetMapping
    public ResponseEntity<List<AppointmentDTO>> getAllAppointments() {
        List<AppointmentDTO> appointments = appointmentService.findAll()
                .stream()
                .map(AppointmentDTO::fromEntity)
                .collect(Collectors.toList());
        return ResponseEntity.ok(appointments);
    }

    /**
     * 🔹 GET: Obtiene una cita por su ID.
     */
    @GetMapping("/{id}")
    public ResponseEntity<AppointmentDTO> getAppointmentById(@PathVariable Long id) {
        Appointment appointment = appointmentService.findById(id);
        if (appointment == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(AppointmentDTO.fromEntity(appointment));
    }

    /**
     * 🔹 POST: Crea una nueva cita.
     */
    @PostMapping
    public ResponseEntity<AppointmentDTO> createAppointment(@RequestBody AppointmentDTO dto) {
        Clinic clinic = clinicService.findById(dto.getClinicId());
        if (clinic == null) {
            return ResponseEntity.badRequest().build();
        }

        Appointment appointment = dto.toEntity(clinic);
        Appointment saved = appointmentService.save(appointment);
        return ResponseEntity.ok(AppointmentDTO.fromEntity(saved));
    }

    /**
     * 🔹 PUT: Actualiza una cita existente.
     */
    @PutMapping("/{id}")
    public ResponseEntity<AppointmentDTO> updateAppointment(
            @PathVariable Long id,
            @RequestBody AppointmentDTO dto) {

        Appointment existing = appointmentService.findById(id);
        if (existing == null) {
            return ResponseEntity.notFound().build();
        }

        Clinic clinic = clinicService.findById(dto.getClinicId());
        if (clinic == null) {
            return ResponseEntity.badRequest().build();
        }

        existing.setNombre(dto.getNombre());
        existing.setCorreo(dto.getCorreo());
        existing.setFecha(dto.getFecha());
        existing.setHora(dto.getHora());
        existing.setMedico(dto.getMedico());
        existing.setClinic(clinic);

        Appointment updated = appointmentService.update(existing);
        return ResponseEntity.ok(AppointmentDTO.fromEntity(updated));
    }

    /**
     * 🔹 DELETE: Elimina una cita por ID.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAppointment(@PathVariable Long id) {
        Appointment appointment = appointmentService.findById(id);
        if (appointment == null) {
            return ResponseEntity.notFound().build();
        }
        appointmentService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
