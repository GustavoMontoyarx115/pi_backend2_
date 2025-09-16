package com.example.pib2.repositories;

import com.example.pib2.models.entities.Appointment;
import com.example.pib2.models.entities.User;
import com.example.pib2.models.entities.Clinic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {

    // 🔍 Buscar todas las citas de un usuario
    List<Appointment> findByUser(User user);

    // 🔍 Buscar todas las citas de una clínica
    List<Appointment> findByClinic(Clinic clinic);

    // 🔍 Buscar todas las citas en una fecha específica
    List<Appointment> findByFecha(LocalDate fecha);

    // 🔍 Buscar todas las citas de un médico específico
    List<Appointment> findByMedico(String medico);
}

