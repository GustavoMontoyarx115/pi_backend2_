package com.example.pib2.servicios;

import com.example.pib2.models.entities.User;
import com.example.pib2.repositories.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.List;

@Service
@Transactional
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public User findById(Long id) {
        if (id == null) {
            System.out.println("UserService.findById: id recibido es null");
            return null;
        }
        Optional<User> opt = userRepository.findById(id);
        return opt.orElse(null);
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

    public void delete(Long id) {
        if (id == null) throw new IllegalArgumentException("Id null al eliminar usuario");
        if (!userRepository.existsById(id)) {
            throw new IllegalArgumentException("No se puede eliminar: el usuario no existe.");
        }
        userRepository.deleteById(id);
    }
}
