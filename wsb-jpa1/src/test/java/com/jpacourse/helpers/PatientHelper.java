package com.jpacourse.helpers;

import com.jpacourse.persistance.entity.AddressEntity;
import com.jpacourse.persistance.entity.PatientEntity;

import java.time.LocalDate;

public class PatientHelper {

    public static PatientEntity createPatient() {
        AddressEntity address = AddressHelper.createAddress();

        PatientEntity patient = new PatientEntity();
        patient.setFirstName("Jaroslaw");
        patient.setLastName("Roszyk");
        patient.setTelephoneNumber("69512313");
        patient.setEmail("jaros.r@example.com");
        patient.setPatientNumber("692137");
        patient.setDateOfBirth(LocalDate.of(2001, 7, 28));
        patient.setInsured(true);
        patient.setAddress(address);

        return patient;
    }
}
