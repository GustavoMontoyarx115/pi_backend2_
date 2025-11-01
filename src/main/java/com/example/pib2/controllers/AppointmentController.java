package com.example.pib2.controllers;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.pib2.models.dtos.AppointmentDTO;
import com.example.pib2.models.entities.Appointment;
import com.example.pib2.models.entities.Clinic;
import com.example.pib2.models.entities.User;
import com.example.pib2.servicios.AppointmentService;
import com.example.pib2.servicios.ClinicService;
import com.example.pib2.servicios.UserService;

@RestController
@RequestMapping("/api/appointments")
@CrossOrigin(origins = {"http://localhost:3000", "https://pi-web2.onrender.com"})
public class AppointmentController {

    private final AppointmentService appointmentService;
    private final ClinicService clinicService;
    private final UserService userService;

    public AppointmentController(AppointmentService appointmentService,
                                 ClinicService clinicService,
                                 UserService userService) {
        this.appointmentService = appointmentService;
        this.clinicService = clinicService;
        this.userService = userService;
    }

    // 🔹 Obtener todas las citas
    @GetMapping
    public ResponseEntity<List<AppointmentDTO>> getAllAppointments() {
        List<AppointmentDTO> appointments = appointmentService.findAll()
                .stream()
                .map(AppointmentDTO::fromEntity)
                .collect(Collectors.toList());
        return ResponseEntity.ok(appointments);
    }

    // 🔹 Obtener cita por ID
    @GetMapping("/{id}")
    public ResponseEntity<AppointmentDTO> getAppointmentById(@PathVariable Long id) {
        Appointment appointment = appointmentService.findById(id);
        if (appointment == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(AppointmentDTO.fromEntity(appointment));
    }

    // 🔹 Crear nueva cita
    @PostMapping
    public ResponseEntity<AppointmentDTO> createAppointment(@RequestBody AppointmentDTO dto) {
        try {
            Clinic clinic;

            // Si no se envía clinicId, asignar la primera clínica disponible
            if (dto.getClinicId() == null) {
                clinic = clinicService.findAll()
                        .stream()
                        .findFirst()
                        .orElseThrow(() -> new RuntimeException("No hay clínicas registradas"));
            } else {
                clinic = clinicService.findById(dto.getClinicId());
                if (clinic == null) return ResponseEntity.badRequest().build();
            }

            User user = null;
            if (dto.getUserId() != null) {
                user = userService.findById(dto.getUserId());
                if (user == null) return ResponseEntity.badRequest().build();
            }

            Appointment appointment = dto.toEntity(clinic, user);
            Appointment saved = appointmentService.save(appointment);

            System.out.println("✅ Nueva cita creada con clínica (id): " + (clinic != null ? String.valueOf(clinic.getId()) : "null"));

            return ResponseEntity.ok(AppointmentDTO.fromEntity(saved));

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }

    // 🔹 Actualizar cita existente
    @PutMapping("/{id}")
    public ResponseEntity<AppointmentDTO> updateAppointment(
            @PathVariable Long id,
            @RequestBody AppointmentDTO dto) {

        Appointment existing = appointmentService.findById(id);
        if (existing == null) return ResponseEntity.notFound().build();

        Clinic clinic;

        // Igual que en POST: asigna una clínica si no viene el ID
        if (dto.getClinicId() == null) {
            clinic = clinicService.findAll()
                    .stream()
                    .findFirst()
                    .orElseThrow(() -> new RuntimeException("No hay clínicas registradas"));
        } else {
            clinic = clinicService.findById(dto.getClinicId());
            if (clinic == null) return ResponseEntity.badRequest().build();
        }

        User user = null;
        if (dto.getUserId() != null) {
            user = userService.findById(dto.getUserId());
            if (user == null) return ResponseEntity.badRequest().build();
        }

        existing.setNombre(dto.getNombre());
        existing.setCorreo(dto.getCorreo());
        existing.setFecha(dto.getFecha());
        existing.setHora(dto.getHora());
        existing.setMedico(dto.getMedico());
        existing.setClinic(clinic);
        existing.setUser(user);

        Appointment updated = appointmentService.update(existing);
        System.out.println("♻️ Cita actualizada correctamente con clínica (id): " + (clinic != null ? String.valueOf(clinic.getId()) : "null"));
        return ResponseEntity.ok(AppointmentDTO.fromEntity(updated));
    }

    // 🔹 Eliminar cita
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAppointment(@PathVariable Long id) {
        Appointment appointment = appointmentService.findById(id);
        if (appointment == null) return ResponseEntity.notFound().build();
        appointmentService.delete(id);
        System.out.println("🗑️ Cita eliminada con ID: " + id);
        return ResponseEntity.noContent().build();
    }
}
