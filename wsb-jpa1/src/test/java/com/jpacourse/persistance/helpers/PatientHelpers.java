package com.jpacourse.persistance.helpers;

import com.jpacourse.persistance.entity.PatientEntity;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static com.jpacourse.persistance.helpers.AddressHelpers.createAddress;

public class PatientHelpers {

    public static PatientEntity createPatient() {
        PatientEntity patientEntity = new PatientEntity();

        patientEntity.setFirstName("FOo");
        patientEntity.setLastName("Bar");
        patientEntity.setTelephoneNumber("0721");
        patientEntity.setEmail("bar@example.com");
        patientEntity.setPatientNumber("69");
        patientEntity.setDateOfBirth(LocalDate.from(LocalDateTime.now()));
        patientEntity.setAddress(createAddress());

        return patientEntity;
    }
}
