package com.example.pib2.controllers;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.pib2.models.dtos.UserDTO;
import com.example.pib2.models.entities.User;
import com.example.pib2.repositories.UserRepository;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "*")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    // ✅ LOGIN
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        try {
            Optional<User> optionalUser = userRepository.findByEmail(request.getEmail());
            if (optionalUser.isEmpty()) {
                return ResponseEntity.status(404).body("❌ Usuario no encontrado");
            }

            User user = optionalUser.get();
            if (!user.getPassword().equals(request.getPassword())) {
                return ResponseEntity.status(401).body("❌ Contraseña incorrecta");
            }

            return ResponseEntity.ok(new UserDTO(user));

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().body("❌ Error en login: " + e.getMessage());
        }
    }

    // ✅ REGISTRO
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest request) {
        try {
            if (request.getEmail() == null || request.getPassword() == null) {
                return ResponseEntity.badRequest().body("⚠️ Email y contraseña son obligatorios");
            }

            if (userRepository.findByEmail(request.getEmail()).isPresent()) {
                return ResponseEntity.badRequest().body("⚠️ Ya existe un usuario con ese email");
            }

            User newUser = new User(
                    request.getNombre(),
                    request.getEmail(),
                    request.getPassword(),
                    request.getTipoDocumento(),
                    request.getNumeroDocumento(),
                    request.getRol()
            );

            userRepository.save(newUser);
            return ResponseEntity.ok(new UserDTO(newUser));

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().body("❌ Error al registrar usuario: " + e.getMessage());
        }
    }

    // ✅ LISTAR
    @GetMapping
    public List<UserDTO> getAllUsers() {
        return userRepository.findAll().stream().map(UserDTO::new).collect(Collectors.toList());
    }

    // ✅ BUSCAR POR ID
    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable Long id) {
        Optional<User> user = userRepository.findById(id);
        return user.map(value -> ResponseEntity.ok(new UserDTO(value)))
                .orElse(ResponseEntity.notFound().build());
    }

    // ✅ ACTUALIZAR
    @PutMapping("/{id}")
    public ResponseEntity<?> updateUser(@PathVariable Long id, @RequestBody RegisterRequest request) {
        try {
            return userRepository.findById(id).map(user -> {
                user.setNombre(request.getNombre());
                user.setEmail(request.getEmail());
                user.setPassword(request.getPassword());
                user.setTipoDocumento(request.getTipoDocumento());
                user.setNumeroDocumento(request.getNumeroDocumento());
                user.setRol(request.getRol());
                userRepository.save(user);
                return ResponseEntity.ok(new UserDTO(user));
            }).orElse(ResponseEntity.notFound().build());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().body("❌ Error al actualizar usuario: " + e.getMessage());
        }
    }

    // ✅ ELIMINAR
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id) {
        try {
            if (!userRepository.existsById(id)) {
                return ResponseEntity.notFound().build();
            }
            userRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().body("❌ Error al eliminar usuario: " + e.getMessage());
        }
    }

    // Clases auxiliares
    static class LoginRequest {
        private String email;
        private String password;
        public String getEmail() { return email; }
        public void setEmail(String email) { this.email = email; }
        public String getPassword() { return password; }
        public void setPassword(String password) { this.password = password; }
    }

    static class RegisterRequest {
        private String nombre;
        private String email;
        private String password;
        private String tipoDocumento;
        private String numeroDocumento;
        private User.Rol rol;
        public String getNombre() { return nombre; }
        public void setNombre(String nombre) { this.nombre = nombre; }
        public String getEmail() { return email; }
        public void setEmail(String email) { this.email = email; }
        public String getPassword() { return password; }
        public void setPassword(String password) { this.password = password; }
        public String getTipoDocumento() { return tipoDocumento; }
        public void setTipoDocumento(String tipoDocumento) { this.tipoDocumento = tipoDocumento; }
        public String getNumeroDocumento() { return numeroDocumento; }
        public void setNumeroDocumento(String numeroDocumento) { this.numeroDocumento = numeroDocumento; }
        public User.Rol getRol() { return rol; }
        public void setRol(User.Rol rol) { this.rol = rol; }
    }
}
