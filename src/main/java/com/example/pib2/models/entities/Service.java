package com.example.pib2.models.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "services")
public class Service {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String titulo;

    private String imagen;

    private String alt;

    @Column(length = 1000) // Para descripciones largas
    private String descripcion;

    //  Relación con Clinic (Muchos servicios pertenecen a una clínica)
    @ManyToOne
    @JoinColumn(name = "clinic_id", nullable = false)
    private Clinic clinic;

    // 🔹 Constructores
    public Service() {}

    public Service(String titulo, String imagen, String alt, String descripcion, Clinic clinic) {
        this.titulo = titulo;
        this.imagen = imagen;
        this.alt = alt;
        this.descripcion = descripcion;
        this.clinic = clinic;
    }

    // 🔹 Getters y Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public String getAlt() {
        return alt;
    }

    public void setAlt(String alt) {
        this.alt = alt;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Clinic getClinic() {
        return clinic;
    }

    public void setClinic(Clinic clinic) {
        this.clinic = clinic;
    }
}
