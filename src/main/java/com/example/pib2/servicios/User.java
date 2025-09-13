package com.example.pib2.servicios;

import jakarta.persistence.*;

@Entity
@Table(name = "users")  // Nombre de la tabla en la BD
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Autoincremental
    private Long id;

    @Column(nullable = false, length = 100)
    private String nombre;

    @Column(nullable = false, unique = true, length = 100)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false, length = 20)
    private String tipoDocumento; // CC, TI, etc.

    @Column(nullable = false, unique = true, length = 20)
    private String numeroDocumento;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private Rol rol;  // PACIENTE o MEDICO

    // ====== CONSTRUCTORES ======
    public User() {}

    public User(String nombre, String email, String password,
                String tipoDocumento, String numeroDocumento, Rol rol) {
        this.nombre = nombre;
        this.email = email;
        this.password = password;
        this.tipoDocumento = tipoDocumento;
        this.numeroDocumento = numeroDocumento;
        this.rol = rol;
    }

    // ====== GETTERS & SETTERS ======
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getTipoDocumento() {
        return tipoDocumento;
    }

    public void setTipoDocumento(String tipoDocumento) {
        this.tipoDocumento = tipoDocumento;
    }

    public String getNumeroDocumento() {
        return numeroDocumento;
    }

    public void setNumeroDocumento(String numeroDocumento) {
        this.numeroDocumento = numeroDocumento;
    }

    public Rol getRol() {
        return rol;
    }

    public void setRol(Rol rol) {
        this.rol = rol;
    }

    // ====== ENUM INTERNO ======
    public enum Rol {
        PACIENTE,
        MEDICO,
        ADMIN
    }
}

