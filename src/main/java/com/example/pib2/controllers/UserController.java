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

// ✅ DTOs para login y registro
class LoginRequest {
    private String email;
    private String password;

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
}

class RegisterRequest {
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

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "*") // 🔓 Permite peticiones desde el frontend
public class UserController {

    @Autowired
    private UserRepository userRepository;

    // ✅ LOGIN
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        Optional<User> optionalUser = userRepository.findByEmail(request.getEmail());

        if (optionalUser.isEmpty()) {
            return ResponseEntity.status(404).body("❌ Usuario no encontrado");
        }

        User user = optionalUser.get();

        if (!user.getPassword().equals(request.getPassword())) {
            return ResponseEntity.status(401).body("❌ Contraseña incorrecta");
        }

        // 🔐 Devuelve la información segura del usuario (sin contraseña)
        return ResponseEntity.ok(new UserDTO(user));
    }

    // ✅ REGISTER (crear nuevo usuario)
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest request) {
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
    }

    // ✅ Listar todos los usuarios
    @GetMapping
    public List<UserDTO> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(UserDTO::new)
                .collect(Collectors.toList());
    }

    // ✅ Buscar usuario por ID
    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable Long id) {
        Optional<User> user = userRepository.findById(id);
        return user.map(value -> ResponseEntity.ok(new UserDTO(value)))
                   .orElse(ResponseEntity.notFound().build());
    }

    // ✅ Actualizar usuario
    @PutMapping("/{id}")
    public ResponseEntity<UserDTO> updateUser(@PathVariable Long id, @RequestBody RegisterRequest request) {
        return userRepository.findById(id)
                .map(user -> {
                    user.setNombre(request.getNombre());
                    user.setEmail(request.getEmail());
                    user.setPassword(request.getPassword());
                    user.setTipoDocumento(request.getTipoDocumento());
                    user.setNumeroDocumento(request.getNumeroDocumento());
                    user.setRol(request.getRol());
                    userRepository.save(user);
                    return ResponseEntity.ok(new UserDTO(user));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    // ✅ Eliminar usuario
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        return userRepository.findById(id)
                .map(user -> {
                    userRepository.delete(user);
                    return ResponseEntity.noContent().<Void>build();
                })
                .orElse(ResponseEntity.notFound().build());
    }
}
