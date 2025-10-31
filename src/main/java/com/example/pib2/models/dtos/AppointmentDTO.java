package com.example.pib2.models.dtos;

import lombok.*;
import com.example.pib2.models.entities.Appointment;
import com.example.pib2.models.entities.Clinic;
import com.example.pib2.models.entities.User;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AppointmentDTO {

    private Long id;
    private String nombre;
    private String correo;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate fecha;

    @JsonFormat(pattern = "HH:mm")
    private LocalTime hora;

    private String medico;

    private Long clinicId;
    private String clinicName;

    private Long userId;    // <-- agregado para recibir el usuario
    private String userName; // opcional, para mostrar en frontend

    public static AppointmentDTO fromEntity(Appointment appointment) {
        if (appointment == null) return null;

        return AppointmentDTO.builder()
                .id(appointment.getId())
                .nombre(appointment.getNombre())
                .correo(appointment.getCorreo())
                .fecha(appointment.getFecha())
                .hora(appointment.getHora())
                .medico(appointment.getMedico())
                .clinicId(appointment.getClinic() != null ? appointment.getClinic().getId() : null)
                .clinicName(appointment.getClinic() != null ? appointment.getClinic().getName() : null)
                .userId(appointment.getUser() != null ? appointment.getUser().getId() : null)
                .userName(appointment.getUser() != null ? appointment.getUser().getNombre() : null)
                .build();
    }

    public Appointment toEntity(Clinic clinic, User user) {
        Appointment appointment = new Appointment();
        appointment.setNombre(this.nombre);
        appointment.setCorreo(this.correo);
        appointment.setFecha(this.fecha);
        appointment.setHora(this.hora);
        appointment.setMedico(this.medico);
        appointment.setClinic(clinic);
        appointment.setUser(user); // <-- asignamos el usuario si viene
        return appointment;
    }
}
