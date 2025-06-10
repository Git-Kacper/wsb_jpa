package com.jpacourse.service;

import com.jpacourse.dto.PatientTO;
import com.jpacourse.persistance.dao.PatientDao;
import com.jpacourse.persistance.dao.DoctorDao;
import com.jpacourse.persistance.entity.PatientEntity;
import com.jpacourse.persistance.entity.DoctorEntity;
import com.jpacourse.persistance.entity.VisitEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@TestPropertySource(locations = "classpath:application-test.properties")
public class PatientServiceTest {

    @Autowired
    private PatientService patientService;

    @Autowired
    private PatientDao patientDao;

    @Autowired
    private DoctorDao doctorDao;

    @Test
    @Transactional
    public void testShouldDeletePatientWithVisitsButNotDoctors() {
        // given
        long initialPatientCount = patientDao.count();
        long initialDoctorCount = doctorDao.count();
        
        PatientEntity patient = patientDao.findOne(1L);
        assertThat(patient).isNotNull();
        
        // Sprawdź czy pacjent ma wizyty
        List<VisitEntity> visits = patientService.findVisitsByPatientId(1L);
        assertThat(visits).isNotEmpty();
        
        // Zapamiętaj ID lekarzy z wizyt
        List<Long> doctorIds = visits.stream()
            .map(visit -> visit.getDoctor().getId())
            .distinct()
            .toList();

        // when
        patientService.deletePatient(1L);

        // then
        PatientEntity deletedPatient = patientDao.findOne(1L);
        assertThat(deletedPatient).isNull();
        
        // Sprawdź czy wszystkie wizyty zostały usunięte (kaskada)
        List<VisitEntity> remainingVisits = patientService.findVisitsByPatientId(1L);
        assertThat(remainingVisits).isEmpty();
        
        // Sprawdź czy lekarze NIE zostali usunięci
        assertThat(patientDao.count()).isEqualTo(initialPatientCount - 1);
        assertThat(doctorDao.count()).isEqualTo(initialDoctorCount);
        
        // Sprawdź czy konkretni lekarze nadal istnieją
        for (Long doctorId : doctorIds) {
            DoctorEntity doctor = doctorDao.findOne(doctorId);
            assertThat(doctor).isNotNull();
        }
    }

    @Test
    @Transactional
    public void testShouldReturnPatientWithVisitsAndCorrectStructure() {
        // given
        Long patientId = 1L;

        // when
        PatientTO patientTO = patientService.findById(patientId);

        // then
        assertThat(patientTO).isNotNull();
        assertThat(patientTO.getId()).isEqualTo(patientId);
        assertThat(patientTO.getFirstName()).isEqualTo("Maria");
        assertThat(patientTO.getLastName()).isEqualTo("Kowalczyk");
        
        // Sprawdź dodatkowe pole age
        assertThat(patientTO.getAge()).isEqualTo(38);
        
        // Sprawdź adres
        assertThat(patientTO.getAddress()).isNotNull();
        assertThat(patientTO.getAddress().getCity()).isEqualTo("Warszawa");
        
        // Sprawdź wizyty
        assertThat(patientTO.getVisits()).isNotNull();
        assertThat(patientTO.getVisits()).isNotEmpty();
        
        // Sprawdź strukturę pierwszej wizyty
        PatientTO.VisitTO firstVisit = patientTO.getVisits().get(0);
        assertThat(firstVisit.getTime()).isNotNull();
        assertThat(firstVisit.getDoctorFirstName()).isNotNull();
        assertThat(firstVisit.getDoctorLastName()).isNotNull();
        assertThat(firstVisit.getTreatmentTypes()).isNotNull();
        
        // Sprawdź czy są informacje o zabiegu
        if (!firstVisit.getTreatmentTypes().isEmpty()) {
            assertThat(firstVisit.getTreatmentTypes().get(0)).isIn("USG", "EKG", "RTG");
        }
    }
} 