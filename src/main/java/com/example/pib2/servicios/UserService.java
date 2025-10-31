package com.example.pib2.servicios;

import com.example.pib2.models.entities.User;
import com.example.pib2.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public User findById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    public User save(User user) {
        return userRepository.save(user);
    }

    public User update(User user) {
        if (user.getId() == null || !userRepository.existsById(user.getId())) {
            throw new IllegalArgumentException("No se puede actualizar: el usuario no existe.");
        }
        return userRepository.save(user);
    }

    public void deleteById(Long id) {
        if (!userRepository.existsById(id)) {
            throw new IllegalArgumentException("No se puede eliminar: el usuario no existe.");
        }
        userRepository.deleteById(id);
    }
}
