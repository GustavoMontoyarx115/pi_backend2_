package com.example.pib2.models.dtos;

import com.example.pib2.models.entities.User;

public class UserDTO {

    private Long id;
    private String nombre;
    private String email;
    private String tipoDocumento;
    private String numeroDocumento;
    private User.Rol rol;

    // 🔹 Constructor vacío (necesario para serialización)
    public UserDTO() {}

    // 🔹 Constructor completo
    public UserDTO(Long id, String nombre, String email, String tipoDocumento, String numeroDocumento, User.Rol rol) {
        this.id = id;
        this.nombre = nombre;
        this.email = email;
        this.tipoDocumento = tipoDocumento;
        this.numeroDocumento = numeroDocumento;
        this.rol = rol;
    }

    // 🔹 Constructor que convierte una entidad User a DTO directamente
    public UserDTO(User user) {
        this.id = user.getId();
        this.nombre = user.getNombre();
        this.email = user.getEmail();
        this.tipoDocumento = user.getTipoDocumento();
        this.numeroDocumento = user.getNumeroDocumento();
        this.rol = user.getRol();
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

    public User.Rol getRol() {
        return rol;
    }
    public void setRol(User.Rol rol) {
        this.rol = rol;
    }
}
