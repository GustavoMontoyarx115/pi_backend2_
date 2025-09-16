package com.example.pib2.repositories;

import com.example.pib2.models.entities.Clinic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClinicRepository extends JpaRepository<Clinic, Long> {

    // Ejemplo de método de búsqueda personalizada
    // Busca una clínica por nombre
    Optional<Clinic> findByName(String name);

    // Si quieres buscar por ciudad
    List<Clinic> findByCity(String city);
}
