package com.jpacourse.persistance.dao;

import com.jpacourse.persistance.entity.PatientEntity;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.orm.jpa.JpaObjectRetrievalFailureException;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;

import static com.jpacourse.persistance.helpers.PatientHelpers.createPatient;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;


@SpringBootTest
@Transactional
@ActiveProfiles("test")
public class PatientDaoTest {
    @Autowired
    private AddressDao addressDao;

    @Autowired
    protected EntityManager entityManager;

    @Autowired
    private PatientDao patientDao;

    @Test
    public void shouldSavePatient() {
        PatientEntity patientEntity = createPatient();

        PatientEntity saved = patientDao.save(patientEntity);

        assertThat(saved).isNotNull();
    }

    @Test
    @Sql(scripts = {
            "/data/address.sql",
            "/data/patients.sql"
    })
    public void shouldFindPatientById() {
        Long kowalski_id = 69L;

        PatientEntity patientEntity = patientDao.findOne(kowalski_id);
        assertThat(patientEntity).isNotNull();
        assertThat(patientEntity.getFirstName()).isEqualTo("Adam");
    }

    @Test
    @Sql(scripts = {
            "/data/address.sql",
            "/data/patients.sql"
    })
    public void shouldRemovePatientById() {
        Long kowalski_id = 69L;
        long entries = patientDao.count();

        patientDao.delete(kowalski_id);

        assertThat(patientDao.count()).isEqualTo(entries - 1);
        assertThat(patientDao.findOne(kowalski_id)).isNull();
    }

    @Test
    @Sql(scripts = {
            "/data/address.sql",
            "/data/patients.sql"
    })
    public void testShouldNotRemovePatientIfIdNotExist() {

        Long notExistId = 999L;

        assertThrows(JpaObjectRetrievalFailureException.class, () -> {
            patientDao.delete(notExistId);
        });
    }

    @Transactional
    @Test
    public void testShouldSaveAndRemovePatient() {
        // given
        PatientEntity patientEntity = createPatient();
        // when
        final PatientEntity saved = patientDao.save(patientEntity);
        assertThat(saved.getId()).isNotNull();
        final PatientEntity newSaved = patientDao.findOne(saved.getId());
        assertThat(newSaved).isNotNull();

        patientDao.delete(saved.getId());

        // then
        final PatientEntity removed = patientDao.findOne(saved.getId());
        assertThat(removed).isNull();
    }
}
