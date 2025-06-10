package com.jpacourse.persistance.dao;

import com.jpacourse.persistance.entity.PatientEntity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.OptimisticLockException;
import jakarta.persistence.PersistenceContext;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
@TestPropertySource(locations = "classpath:application-test.properties")
public class PatientOptimisticLockTest {

    @Autowired
    private PatientDao patientDao;
    
    @PersistenceContext
    private EntityManager entityManager;

    @Test
    @Transactional
    public void testOptimisticLockException() {
        // given
        Long patientId = 1L;
        
        // Pobierz pacjenta i sprawdź wersję
        PatientEntity patient = patientDao.findOne(patientId);
        assertThat(patient).isNotNull();
        Long originalVersion = patient.getVersion();
        
        // Zaktualizuj pacjenta
        patient.setFirstName("Zmienione Imię 1");
        PatientEntity updated = patientDao.update(patient);
        entityManager.flush();
        assertThat(updated.getVersion()).isGreaterThan(originalVersion);

        // Symulacja konfliktu - utwórz nową instancję z nieaktualną wersją
        PatientEntity conflictingPatient = new PatientEntity();
        conflictingPatient.setId(patientId);
        conflictingPatient.setFirstName("Konfliktujące Imię");
        conflictingPatient.setLastName(patient.getLastName());
        conflictingPatient.setTelephoneNumber(patient.getTelephoneNumber());
        conflictingPatient.setPatientNumber(patient.getPatientNumber());
        conflictingPatient.setDateOfBirth(patient.getDateOfBirth());
        conflictingPatient.setAge(patient.getAge());
        conflictingPatient.setVersion(originalVersion); // Stara wersja!

        // when & then
        // To powinno rzucić OptimisticLockException z powodu nieaktualnej wersji
        // Spring opakowuje to w ObjectOptimisticLockingFailureException
        assertThatThrownBy(() -> {
            patientDao.update(conflictingPatient);
            entityManager.flush();
        }).isInstanceOf(ObjectOptimisticLockingFailureException.class);
    }

    @Test
    @Transactional
    public void testVersionIncrementsOnUpdate() {
        // given
        Long patientId = 1L;
        PatientEntity patient = patientDao.findOne(patientId);
        Long initialVersion = patient.getVersion();
        assertThat(initialVersion).isNotNull();

        // when
        patient.setFirstName("Nowe Imię");
        PatientEntity updatedPatient = patientDao.update(patient);
        entityManager.flush(); // Wymuszamy aktualizację wersji

        // then
        // Wersja powinna zostać zwiększona po aktualizacji
        assertThat(updatedPatient.getVersion()).isEqualTo(initialVersion + 1);
    }
} 