package com.jpacourse.mapper;

import com.jpacourse.dto.PatientTo;
import com.jpacourse.persistance.entity.PatientEntity;

public class PatientMapper {

    public static PatientTo mapToTo(final PatientEntity patientEntity) {
        if (patientEntity == null) {
            return null;
        }
        final PatientTo patientTo = new PatientTo();
        patientTo.setId(patientEntity.getId());
        patientTo.setFirstName(patientEntity.getFirstName());
        patientTo.setLastName(patientEntity.getLastName());
        patientTo.setDateOfBirth(patientEntity.getDateOfBirth());
        patientTo.setEmail(patientEntity.getEmail());
        patientTo.setTelephoneNumber(patientEntity.getTelephoneNumber());
        patientTo.setDateOfBirth(patientEntity.getDateOfBirth());
        patientTo.setAddress(patientEntity.getAddress());
        patientTo.setVisits(patientEntity.getVisits().stream().map(VisitMapper::mapToTo).toList());

        return patientTo;
    }

}
