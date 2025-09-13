package com.example.pib2.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.pib2.models.entities.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    
    // Método extra para buscar usuario por email
    User findByEmail(String email);
}


