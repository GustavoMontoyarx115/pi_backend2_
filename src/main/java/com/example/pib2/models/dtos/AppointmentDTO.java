package com.example.pib2.models.dtos;


import lombok.*;
import com.example.pib2.models.entities.Appointment;
import com.example.pib2.models.entities.Clinic;
import java.time.LocalDateTime;

/**
 * DTO (Data Transfer Object) para la entidad Appointment.
 * Permite la transferencia de información sin exponer directamente la entidad JPA.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AppointmentDTO {

    private Long id;
    private String patientName;
    private String doctorName;
    private LocalDateTime appointmentDate;
    private String status;
    private Long clinicId;      // Relación con Clinic
    private String clinicName;  // Información opcional para mostrar

    /**
     * Convierte una entidad Appointment a un objeto AppointmentDTO.
     */
    public static AppointmentDTO fromEntity(Appointment appointment) {
        if (appointment == null) {
            return null;
        }
        return AppointmentDTO.builder()
                .id(appointment.getId())
                .patientName(appointment.getPatientName())
                .doctorName(appointment.getDoctorName())
                .appointmentDate(appointment.getAppointmentDate())
                .status(appointment.getStatus())
                .clinicId(appointment.getClinic() != null ? appointment.getClinic().getId() : null)
                .clinicName(appointment.getClinic() != null ? appointment.getClinic().getName() : null)
                .build();
    }

    /**
     * Versión básica para evitar ciclos al convertir desde ClinicDTO.
     */
    public static AppointmentDTO fromEntityBasic(Appointment appointment) {
        if (appointment == null) {
            return null;
        }
        return AppointmentDTO.builder()
                .id(appointment.getId())
                .patientName(appointment.getPatientName())
                .appointmentDate(appointment.getAppointmentDate())
                .status(appointment.getStatus())
                .build();
    }

    /**
     * Convierte este DTO en una entidad Appointment.
     */
    public Appointment toEntity(Clinic clinic) {
        return Appointment.builder()
                .id(this.id)
                .patientName(this.patientName)
                .doctorName(this.doctorName)
                .appointmentDate(this.appointmentDate)
                .status(this.status)
                .clinic(clinic)
                .build();
    }

    /**
     * Conversión básica usada cuando se crea desde ClinicDTO.
     */
    public Appointment toEntityBasic(Clinic clinic) {
        return Appointment.builder()
                .id(this.id)
                .patientName(this.patientName)
                .appointmentDate(this.appointmentDate)
                .status(this.status)
                .clinic(clinic)
                .build();
    }
}
