package com.example.pib2.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.pib2.models.entities.Appointment;
import com.example.pib2.servicios.AppointmentService;

@RestController
@RequestMapping("/api/appointments")
@CrossOrigin(origins = "http://localhost:3000") // permite llamadas desde el frontend
public class AppointmentControllers {

    @Autowired
    private AppointmentService appointmentService;

    // Obtener todas las citas
    @GetMapping
    public List<Appointment> getAllAppointments() {
        return appointmentService.findAll();
    }
}
