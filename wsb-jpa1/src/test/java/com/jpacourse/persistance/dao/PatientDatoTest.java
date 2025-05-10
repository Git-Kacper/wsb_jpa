package com.jpacourse.persistance.dao;

import com.jpacourse.persistance.entity.AddressEntity;
import com.jpacourse.persistance.entity.PatientEntity;
import com.jpacourse.persistance.entity.VisitEntity;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import static com.jpacourse.persistance.helpers.AddressHelpers.createAddress;
import static com.jpacourse.persistance.helpers.PatientHelpers.createPatient;
import static org.assertj.core.api.Assertions.assertThat;

//public class PatientDatoTest {
//}


import com.jpacourse.persistance.entity.AddressEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
@ActiveProfiles("test")
public class PatientDatoTest {
    @Autowired
    private AddressDao addressDao;

    @Autowired
    protected EntityManager entityManager;

    @Autowired
    private PatientDao patientDao;

    @Transactional
    @Test
    public void shouldSavePatient() {
        PatientEntity patientEntity = createPatient();

        PatientEntity saved = patientDao.save(patientEntity);

        assertThat(saved).isNotNull();
    }

//    @Test
//    public void shouldAddPatientWithVisit() {
//        Long patientId = 1L;
//        Long doctorId = 2L;
//        LocalDateTime visitDate = LocalDateTime.now();
//        String visitDescription = "visitDescription";
//
//        PatientEntity patientEntity = patientDao.findOne(patientId);
//
//        patientDao.addVisitForPatient(patientId, doctorId, visitDate, visitDescription);
//
//        Assertions.assertEquals(1, patientEntity.getVisits().size());
//        VisitEntity visitEntity = patientEntity.getVisits().get(0);
//        Assertions.assertNotNull(visitEntity);
//    }
}
