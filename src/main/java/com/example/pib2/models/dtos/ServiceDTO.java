package com.example.pib2.models.dtos;

import com.example.pib2.models.entities.Service;
import com.example.pib2.models.entities.Clinic;
import lombok.*;

/**
 * DTO (Data Transfer Object) para la entidad Service.
 * Permite transferir datos entre el backend y el frontend
 * sin exponer directamente la entidad JPA.
 */

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ServiceDTO {

    private Long id;
    private String titulo;
    private String imagen;
    private String alt;
    private String descripcion;

    // 🔹 Datos de la clínica relacionada
    private Long clinicId;      // ID de la clínica (clave foránea)
    private String clinicName;  // Nombre de la clínica (para mostrar en frontend)

    /**
     * Convierte una entidad Service en un objeto ServiceDTO.
     */
    public static ServiceDTO fromEntity(Service service) {
        if (service == null) {
            return null;
        }
        return ServiceDTO.builder()
                .id(service.getId())
                .titulo(service.getTitulo())
                .imagen(service.getImagen())
                .alt(service.getAlt())
                .descripcion(service.getDescripcion())
                .clinicId(service.getClinic() != null ? service.getClinic().getId() : null)
                .clinicName(service.getClinic() != null ? service.getClinic().getName() : null)
                .build();
    }

    /**
     * Convierte este DTO en una entidad Service.
     * Se pasa el objeto Clinic ya cargado para evitar referencias nulas.
     */
    public Service toEntity(Clinic clinic) {
        Service service = new Service();
        service.setId(this.id);
        service.setTitulo(this.titulo);
        service.setImagen(this.imagen);
        service.setAlt(this.alt);
        service.setDescripcion(this.descripcion);
        service.setClinic(clinic);
        return service;
    }

    /**
     * Conversión básica (sin detalles de clínica).
     * Útil cuando se quiere evitar ciclos o referencias innecesarias.
     */
    public static ServiceDTO fromEntityBasic(Service service) {
        if (service == null) {
            return null;
        }
        return ServiceDTO.builder()
                .id(service.getId())
                .titulo(service.getTitulo())
                .imagen(service.getImagen())
                .alt(service.getAlt())
                .descripcion(service.getDescripcion())
                .build();
    }
}

