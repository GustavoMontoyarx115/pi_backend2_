package com.example.pib2.config;


import com.example.pib2.models.entities.Clinic;
import com.example.pib2.repositories.ClinicRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataLoader implements CommandLineRunner {

    private final ClinicRepository clinicRepository;

    // Clínica por defecto accesible desde cualquier parte del backend
    public static Long defaultClinicId;

    public DataLoader(ClinicRepository clinicRepository) {
        this.clinicRepository = clinicRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        if (clinicRepository.count() == 0) {

            Clinic clinic1 = new Clinic();
            clinic1.setName("Clínica Central");
            clinic1.setAddress("Calle 123 #45-67");
            clinic1.setPhone("123456789");
            clinicRepository.save(clinic1);

            Clinic clinic2 = new Clinic();
            clinic2.setName("Clínica Salud Total");
            clinic2.setAddress("Carrera 10 #20-30");
            clinic2.setPhone("987654321");
            clinicRepository.save(clinic2);

            Clinic clinic3 = new Clinic();
            clinic3.setName("Clínica Vida");
            clinic3.setAddress("Avenida 50 #12-34");
            clinic3.setPhone("321654987");
            clinicRepository.save(clinic3);

            // Definimos la clínica por defecto
            defaultClinicId = clinic1.getId();

            System.out.println("✅ Clínicas iniciales insertadas en la base de datos");
            System.out.println("ℹ️ Clínica por defecto: " + clinic1.getName() + " (ID=" + defaultClinicId + ")");
        } else {
            // Si ya existen clínicas, podemos asignar por defecto la primera que encontremos
            defaultClinicId = clinicRepository.findAll().get(0).getId();
            System.out.println("ℹ️ Clínicas ya existen en la base de datos");
            System.out.println("ℹ️ Clínica por defecto asignada automáticamente: ID=" + defaultClinicId);
        }
    }
}
