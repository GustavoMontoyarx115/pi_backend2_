package com.example.pib2.models.dtos;

import java.time.LocalDate;
import java.time.LocalTime;

import com.example.pib2.models.entities.Appointment;

/**
 * DTO para la entidad Appointment.
 * Contiene la información que se enviará al frontend
 * y métodos utilitarios para mapear entre entidad y DTO.
 */
public class AppointmentDTO {

    private Long id;
    private String nombre;
    private String correo;
    private LocalDate fecha;
    private LocalTime hora;
    private String medico;

    // Datos simplificados de relaciones
    private Long userId;
    private String userName;

    private Long clinicId;
    private String clinicName;

    // =======================
    // Getters y Setters
    // =======================
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getCorreo() { return correo; }
    public void setCorreo(String correo) { this.correo = correo; }

    public LocalDate getFecha() { return fecha; }
    public void setFecha(LocalDate fecha) { this.fecha = fecha; }

    public LocalTime getHora() { return hora; }
    public void setHora(LocalTime hora) { this.hora = hora; }

    public String getMedico() { return medico; }
    public void setMedico(String medico) { this.medico = medico; }

    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }

    public String getUserName() { return userName; }
    public void setUserName(String userName) { this.userName = userName; }

    public Long getClinicId() { return clinicId; }
    public void setClinicId(Long clinicId) { this.clinicId = clinicId; }

    public String getClinicName() { return clinicName; }
    public void setClinicName(String clinicName) { this.clinicName = clinicName; }

    // =======================
    // Métodos de mapeo (Mapper integrado)
    // =======================

    /** Convierte una entidad Appointment a un DTO */
    public static AppointmentDTO fromEntity(Appointment appointment) {
        if (appointment == null) return null;

        AppointmentDTO dto = new AppointmentDTO();
        dto.setId(appointment.getId());
        dto.setNombre(appointment.getNombre());
        dto.setCorreo(appointment.getCorreo());
        dto.setFecha(appointment.getFecha());
        dto.setHora(appointment.getHora());
        dto.setMedico(appointment.getMedico());

        if (appointment.getUser() != null) {
            dto.setUserId(appointment.getUser().getId());
            dto.setUserName(appointment.getUser().getNombre());
        }

        if (appointment.getClinic() != null) {
            dto.setClinicId(appointment.getClinic().getId());
            dto.setClinicName(appointment.getClinic().getNombre());
        }

        return dto;
    }

    /** Convierte un DTO en una entidad Appointment (sin relaciones asignadas) */
    public static Appointment toEntity(AppointmentDTO dto) {
        if (dto == null) return null;

        Appointment appointment = new Appointment();
        appointment.setId(dto.getId());
        appointment.setNombre(dto.getNombre());
        appointment.setCorreo(dto.getCorreo());
        appointment.setFecha(dto.getFecha());
        appointment.setHora(dto.getHora());
        appointment.setMedico(dto.getMedico());

        // Las relaciones User y Clinic se asignan en el servicio si es necesario
        return appointment;
    }
}
