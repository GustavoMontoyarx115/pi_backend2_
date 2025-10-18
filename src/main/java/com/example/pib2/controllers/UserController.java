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
@CrossOrigin(origins = "*") // 🔓 Permite peticiones desde tu frontend (Next.js)
public class UserController {

    @Autowired
    private UserRepository userRepository;

    // ✅ LOGIN (autenticación segura)
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserDTO userDTO) {
        Optional<User> optionalUser = userRepository.findByEmail(userDTO.getEmail());

        if (optionalUser.isEmpty()) {
            return ResponseEntity.status(404).body("❌ Usuario no encontrado");
        }

        User user = optionalUser.get();

        if (!user.getPassword().equals(userDTO.getPassword())) {
            return ResponseEntity.status(401).body("❌ Contraseña incorrecta");
        }

        // 🔐 Aquí podrías generar un token JWT si luego implementas autenticación
        return ResponseEntity.ok(new UserDTO(user));
    }

    // ✅ Listar todos los usuarios (usa DTO)
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

    // ✅ Crear un nuevo usuario
    @PostMapping
    public ResponseEntity<UserDTO> createUser(@RequestBody UserDTO userDTO) {
        User user = new User(
                userDTO.getNombre(),
                userDTO.getEmail(),
                userDTO.getPassword(),
                userDTO.getTipoDocumento(),
                userDTO.getNumeroDocumento(),
                userDTO.getRol()
        );
        User savedUser = userRepository.save(user);
        return ResponseEntity.ok(new UserDTO(savedUser));
    }

    // ✅ Actualizar usuario
    @PutMapping("/{id}")
    public ResponseEntity<UserDTO> updateUser(@PathVariable Long id, @RequestBody UserDTO userDTO) {
        return userRepository.findById(id)
                .map(user -> {
                    user.setNombre(userDTO.getNombre());
                    user.setEmail(userDTO.getEmail());
                    user.setPassword(userDTO.getPassword());
                    user.setTipoDocumento(userDTO.getTipoDocumento());
                    user.setNumeroDocumento(userDTO.getNumeroDocumento());
                    user.setRol(userDTO.getRol());
                    User updatedUser = userRepository.save(user);
                    return ResponseEntity.ok(new UserDTO(updatedUser));
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
