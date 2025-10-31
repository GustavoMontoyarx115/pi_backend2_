package com.example.pib2.models.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import com.example.pib2.models.entities.Appointment;
import com.example.pib2.models.entities.Clinic;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDate;
import java.time.LocalTime;

/**
 * DTO (Data Transfer Object) para la entidad Appointment.
 * Permite transferir los datos entre el backend y el frontend
 * sin exponer directamente la entidad JPA.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AppointmentDTO {

    private Long id;
    private String nombre;
    private String correo;

    /**
     * 🔹 Formato de fecha esperado: "yyyy-MM-dd"
     * (Ejemplo: "2025-10-31")
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate fecha;

    /**
     * 🔹 Formato de hora esperado: "HH:mm"
     * (Ejemplo: "14:30")
     */
    @JsonFormat(pattern = "HH:mm")
    private LocalTime hora;

    private String medico;

    private Long clinicId;      // ID de la clínica asociada
    private String clinicName;  // Nombre de la clínica (opcional para mostrar)

    /**
     * Convierte una entidad Appointment a un DTO.
     */
    public static AppointmentDTO fromEntity(Appointment appointment) {
        if (appointment == null) {
            return null;
        }

        return AppointmentDTO.builder()
                .id(appointment.getId())
                .nombre(appointment.getNombre())
                .correo(appointment.getCorreo())
                .fecha(appointment.getFecha())
                .hora(appointment.getHora())
                .medico(appointment.getMedico())
                .clinicId(
                        appointment.getClinic() != null
                                ? appointment.getClinic().getId()
                                : null
                )
                .clinicName(
                        appointment.getClinic() != null
                                ? appointment.getClinic().getName()
                                : null
                )
                .build();
    }

    /**
     * Convierte este DTO en una entidad Appointment.
     */
    public Appointment toEntity(Clinic clinic) {
        Appointment appointment = new Appointment();
        appointment.setId(this.id);
        appointment.setNombre(this.nombre);
        appointment.setCorreo(this.correo);
        appointment.setFecha(this.fecha);
        appointment.setHora(this.hora);
        appointment.setMedico(this.medico);
        appointment.setClinic(clinic);
        return appointment;
    }
}
