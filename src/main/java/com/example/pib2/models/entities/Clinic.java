package com.example.pib2.models.entities;

import jakarta.persistence.*;
import lombok.*;
import java.util.List;

@Entity
@Table(name = "clinics")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Clinic {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 120)
    private String name;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(nullable = false, length = 200)
    private String address;

    @Column(length = 100)
    private String city;  //  Nuevo campo para que funcione findByCity

    @Column(length = 20)
    private String phone;

    @Column(length = 100)
    private String email;

    @Column(length = 150)
    private String facebook;

    @Column(length = 150)
    private String instagram;

    @Column(length = 150)
    private String whatsapp;

    @Column(length = 150)
    private String tiktok;

    // 🔗 Relación con Appointment (Uno a Muchos)
    @OneToMany(mappedBy = "clinic", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Appointment> appointments;

    // 🔹 Constructor personalizado para inicializar rápido una clínica
    public Clinic(String name, String address, String phone) {
        this.name = name;
        this.address = address;
        this.phone = phone;
    }
}
