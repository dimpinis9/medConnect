

package com.medConnect.medConnect_app;

import com.medConnect.medConnect_app.Entities.Appointment;
import com.medConnect.medConnect_app.Entities.Doctor;
import com.medConnect.medConnect_app.Repositories.AppointmentRepository;
import com.medConnect.medConnect_app.Repositories.DoctorRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class DoctorRepositoryTest {

    @Autowired
    private DoctorRepository doctorRepository;

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Test
    public void testCreateAndFindDoctor() {
        // Δημιουργία ενός νέου γιατρού
        Doctor doctor = new Doctor();
        doctor.setName("John Smith");
        doctor.setSpecialization("Cardiologist");
        doctor.setEmail("john.smith@example.com");

        // Αποθήκευση του doctor στη βάση δεδομένων
        doctor = doctorRepository.save(doctor);

        // Έλεγχος αν ο doctor αποθηκεύτηκε σωστά
        assertThat(doctor.getId()).isNotNull();
        assertThat(doctor.getName()).isEqualTo("John Smith");
        assertThat(doctor.getSpecialization()).isEqualTo("Cardiologist");
        assertThat(doctor.getEmail()).isEqualTo("john.smith@example.com");

        // Εύρεση του doctor από τη βάση δεδομένων
        Doctor foundDoctor = doctorRepository.findById(doctor.getId()).orElse(null);
        assertThat(foundDoctor).isNotNull();
        assertThat(foundDoctor.getName()).isEqualTo("John Smith");
    }

    @Test
    public void testUpdateDoctor() {
        // Δημιουργία και αποθήκευση ενός νέου doctor
        Doctor doctor = new Doctor();
        doctor.setName("Jahn smath");
        doctor.setSpecialization("Cardiologist");
        doctor.setEmail("jsmith@email.com");
        doctor = doctorRepository.save(doctor);

        // Ενημέρωση του doctor
        doctor.setSpecialization("Neurologist");
        doctor = doctorRepository.save(doctor);

        // Έλεγχος αν ο doctor ενημερώθηκε σωστά
        Doctor updatedDoctor = doctorRepository.findById(doctor.getId()).orElse(null);
        assertThat(updatedDoctor).isNotNull();
        assertThat(updatedDoctor.getSpecialization()).isEqualTo("Neurologist");
    }


    @Test
    public void testDeleteDoctor() {
        // Δημιουργία και αποθήκευση ενός νέου doctor
        Doctor doctor = new Doctor();
        doctor.setName("John Smith");
        doctor.setSpecialization("Cardiologist");
        doctor.setEmail("john.smith@example.com");
        doctor = doctorRepository.save(doctor);

        // Διαγραφή του doctor
        doctorRepository.delete(doctor);

        // Έλεγχος αν ο doctor διαγράφηκε σωστά
        Doctor deletedDoctor = doctorRepository.findById(doctor.getId()).orElse(null);
        assertThat(deletedDoctor).isNull();
    }

    @Test
    public void testDoctorAppointments() {
        // Δημιουργία ενός νέου γιατρού
        Doctor doctor = new Doctor();
        doctor.setName("DIM DEV");
        doctor.setSpecialization("Pathologist");
        doctor.setEmail("devdim@gmail.com");
        doctor = doctorRepository.save(doctor);

        // Δημιουργία ενός νέου ραντεβού
        Appointment appointment = new Appointment();
        appointment.setDoctor(doctor);
        appointment.setAppointmentDate(LocalDate.now());
        appointment.setReason("Routine checkup");
        appointment = appointmentRepository.save(appointment);

        // Έλεγχος αν το ραντεβού συνδέθηκε με τον doctor
        Doctor foundDoctor = doctorRepository.findById(doctor.getId()).orElse(null);
        assertThat(foundDoctor.getAppointments().size()).isEqualTo(1);
    }


}
