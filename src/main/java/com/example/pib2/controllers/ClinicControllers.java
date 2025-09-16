package com.example.pib2.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.pib2.models.entities.Clinic;
import com.example.pib2.servicios.ClinicService;

@RestController
@RequestMapping("/api/clinics")
@CrossOrigin(origins = "http://localhost:3000") // permite el frontend en React
public class ClinicControllers {

    @Autowired
    private ClinicService service;

    @GetMapping
    public List<Clinic> getAllClinics() {
        return service.findAll();
    }
}

