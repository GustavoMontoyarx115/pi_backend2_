package com.example.pib2.models.dtos;

import lombok.*;
import com.example.pib2.models.entities.Clinic;
import java.util.List;
import java.util.stream.Collectors;
import com.example.pib2.models.entities.Appointment;

/**
 * DTO (Data Transfer Object) para la entidad Clinic.
 * Transfiere los datos entre el backend y el frontend,
 * evitando exponer directamente la entidad JPA.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ClinicDTO {

    private Long id;
    private String name;
    private String description;
    private String address;
    private String city;
    private String phone;
    private String email;
    private String facebook;
    private String instagram;
    private String whatsapp;
    private String tiktok;

    // Lista de citas asociadas (si deseas mostrar información básica de las citas)
    private List<AppointmentDTO> appointments;

    /**
     * Convierte una entidad Clinic a un objeto ClinicDTO.
     */
    public static ClinicDTO fromEntity(Clinic clinic) {
        if (clinic == null) {
            return null;
        }
        return ClinicDTO.builder()
                .id(clinic.getId())
                .name(clinic.getName())
                .description(clinic.getDescription())
                .address(clinic.getAddress())
                .city(clinic.getCity())
                .phone(clinic.getPhone())
                .email(clinic.getEmail())
                .facebook(clinic.getFacebook())
                .instagram(clinic.getInstagram())
                .whatsapp(clinic.getWhatsapp())
                .tiktok(clinic.getTiktok())
                .appointments(clinic.getAppointments() != null
                ? clinic.getAppointments().stream()
                    .map(AppointmentDTO::fromEntityBasic)
                        .collect(Collectors.toList())
                        : null)
                .build();
    }

    /**
     * Convierte este DTO en una entidad Clinic.
     */
    public Clinic toEntity() {
        Clinic clinic = Clinic.builder()
                .id(this.id)
                .name(this.name)
                .description(this.description)
                .address(this.address)
                .city(this.city)
                .phone(this.phone)
                .email(this.email)
                .facebook(this.facebook)
                .instagram(this.instagram)
                .whatsapp(this.whatsapp)
                .tiktok(this.tiktok)
                .build();

        // Evita recursión infinita al asignar las citas
        if (this.appointments != null) {
            clinic.setAppointments(
                this.appointments.stream()
                    .map(dto -> dto.toEntityBasic(clinic))
                    .collect(Collectors.toList())
            );
        }

        return clinic;
    }
}
