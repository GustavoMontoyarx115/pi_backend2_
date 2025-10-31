package com.example.pib2.models.dtos;

import java.util.List;
import java.util.stream.Collectors;

import com.example.pib2.models.entities.Clinic;
import com.example.pib2.models.entities.Appointment;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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

    private List<AppointmentDTO> appointments;

    /**
     * Convierte una entidad Clinic a DTO
     * Incluye la lista de citas con datos básicos
     */
    public static ClinicDTO fromEntity(Clinic clinic) {
        if (clinic == null) return null;

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
                .appointments(
                        clinic.getAppointments() != null
                                ? clinic.getAppointments().stream()
                                        .map(appointment -> {
                                            // Convertimos a AppointmentDTO usando fromEntity para incluir userId
                                            return AppointmentDTO.fromEntity(appointment);
                                        })
                                        .collect(Collectors.toList())
                                : null
                )
                .build();
    }

    /**
     * Convierte este DTO en una entidad Clinic
     * Evita referencias circulares al convertir citas
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

        // Convertir citas sin causar recursión infinita
        if (this.appointments != null) {
            clinic.setAppointments(
                    this.appointments.stream()
                            .map(dto -> {
                                Appointment appointment = dto.toEntity(clinic, null); // User se asigna aparte si es necesario
                                return appointment;
                            })
                            .collect(Collectors.toList())
            );
        }

        return clinic;
    }
}
