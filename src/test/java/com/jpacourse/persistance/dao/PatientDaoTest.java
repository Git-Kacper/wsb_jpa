package com.jpacourse.persistance.dao;

import com.jpacourse.persistance.entity.PatientEntity;
import com.jpacourse.persistance.entity.VisitEntity;
import com.jpacourse.persistance.entity.DoctorEntity;
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
public class PatientDaoTest {

    @Autowired
    private PatientDao patientDao;

    @Autowired
    private DoctorDao doctorDao;

    @Test
    @Transactional
    public void testShouldFindPatientsByLastName() {
        // given
        String lastName = "Kowalczyk";

        // when
        List<PatientEntity> patients = patientDao.findByLastName(lastName);

        // then
        assertThat(patients).isNotEmpty();
        assertThat(patients).allSatisfy(patient -> 
            assertThat(patient.getLastName()).isEqualTo(lastName)
        );
    }

    @Test
    @Transactional
    public void testShouldFindPatientsWithMoreThanXVisits() {
        // given
        int minVisitCount = 1;

        // when
        List<PatientEntity> patients = patientDao.findPatientsWithMoreThanXVisits(minVisitCount);

        // then
        assertThat(patients).isNotEmpty();
        // Pacjent o ID 1 ma 3 wizyty zgodnie z data.sql
        PatientEntity patientWithManyVisits = patients.stream()
            .filter(p -> p.getId().equals(1L))
            .findFirst()
            .orElse(null);
        assertThat(patientWithManyVisits).isNotNull();
    }

    @Test
    @Transactional
    public void testShouldFindPatientsOlderThan() {
        // given
        int minAge = 30;

        // when
        List<PatientEntity> patients = patientDao.findPatientsOlderThan(minAge);

        // then
        assertThat(patients).isNotEmpty();
        assertThat(patients).allSatisfy(patient -> 
            assertThat(patient.getAge()).isGreaterThan(minAge)
        );
    }

    @Test
    @Transactional
    public void testShouldAddVisitToPatient() {
        // given
        Long patientId = 1L;
        Long doctorId = 1L;
        LocalDateTime visitTime = LocalDateTime.now().plusDays(1);
        String description = "Nowa wizyta testowa";

        PatientEntity patientBefore = patientDao.findOne(patientId);
        int visitCountBefore = patientBefore.getVisits().size();

        // when
        VisitEntity newVisit = patientDao.addVisitToPatient(patientId, doctorId, visitTime, description);

        // then
        assertThat(newVisit).isNotNull();
        assertThat(newVisit.getId()).isNotNull();
        assertThat(newVisit.getDescription()).isEqualTo(description);
        assertThat(newVisit.getTime()).isEqualTo(visitTime);
        assertThat(newVisit.getPatient().getId()).isEqualTo(patientId);
        assertThat(newVisit.getDoctor().getId()).isEqualTo(doctorId);

        // Sprawdź czy wizyta została dodana do pacjenta
        PatientEntity patientAfter = patientDao.findOne(patientId);
        assertThat(patientAfter.getVisits()).hasSize(visitCountBefore + 1);
    }
} 