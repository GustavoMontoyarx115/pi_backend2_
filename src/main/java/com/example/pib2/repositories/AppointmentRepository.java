package com.example.pib2.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.pib2.models.entities.Appointment;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
    // Aquí puedes definir consultas personalizadas si las necesitas
}
