package com.example.pib2.controllers;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.pib2.models.dtos.UserDTO;
import com.example.pib2.models.entities.User;
import com.example.pib2.repositories.UserRepository;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "*") // 🔓 Permite peticiones desde tu frontend (Next.js)
public class UserController {

    @Autowired
    private UserRepository userRepository;

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
        // Convierte el DTO en entidad
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