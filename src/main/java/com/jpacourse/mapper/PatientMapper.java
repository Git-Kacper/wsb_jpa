package com.jpacourse.mapper;

import com.jpacourse.dto.PatientTO;
import com.jpacourse.persistance.entity.PatientEntity;
import com.jpacourse.persistance.entity.VisitEntity;
import com.jpacourse.persistance.entity.MedicalTreatmentEntity;

import java.util.List;
import java.util.stream.Collectors;

public final class PatientMapper {

    public static PatientTO mapToTO(final PatientEntity patientEntity) {
        if (patientEntity == null) {
            return null;
        }

        final PatientTO patientTO = new PatientTO();
        patientTO.setId(patientEntity.getId());
        patientTO.setFirstName(patientEntity.getFirstName());
        patientTO.setLastName(patientEntity.getLastName());
        patientTO.setTelephoneNumber(patientEntity.getTelephoneNumber());
        patientTO.setEmail(patientEntity.getEmail());
        patientTO.setPatientNumber(patientEntity.getPatientNumber());
        patientTO.setDateOfBirth(patientEntity.getDateOfBirth());
        patientTO.setAge(patientEntity.getAge());
        
        // Mapowanie adresu
        if (patientEntity.getAddress() != null) {
            patientTO.setAddress(AddressMapper.mapToTO(patientEntity.getAddress()));
        }

        // Mapowanie wizyt
        if (patientEntity.getVisits() != null) {
            List<PatientTO.VisitTO> visitTOs = patientEntity.getVisits().stream()
                .map(PatientMapper::mapVisitToTO)
                .collect(Collectors.toList());
            patientTO.setVisits(visitTOs);
        }

        return patientTO;
    }

    public static PatientEntity mapToEntity(final PatientTO patientTO) {
        if (patientTO == null) {
            return null;
        }

        PatientEntity patientEntity = new PatientEntity();
        patientEntity.setId(patientTO.getId());
        patientEntity.setFirstName(patientTO.getFirstName());
        patientEntity.setLastName(patientTO.getLastName());
        patientEntity.setTelephoneNumber(patientTO.getTelephoneNumber());
        patientEntity.setEmail(patientTO.getEmail());
        patientEntity.setPatientNumber(patientTO.getPatientNumber());
        patientEntity.setDateOfBirth(patientTO.getDateOfBirth());
        patientEntity.setAge(patientTO.getAge());

        // Mapowanie adresu
        if (patientTO.getAddress() != null) {
            patientEntity.setAddress(AddressMapper.mapToEntity(patientTO.getAddress()));
        }

        return patientEntity;
    }

    private static PatientTO.VisitTO mapVisitToTO(final VisitEntity visitEntity) {
        if (visitEntity == null) {
            return null;
        }

        PatientTO.VisitTO visitTO = new PatientTO.VisitTO();
        visitTO.setId(visitEntity.getId());
        visitTO.setDescription(visitEntity.getDescription());
        visitTO.setTime(visitEntity.getTime());

        // Mapowanie informacji o lekarzu
        if (visitEntity.getDoctor() != null) {
            visitTO.setDoctorFirstName(visitEntity.getDoctor().getFirstName());
            visitTO.setDoctorLastName(visitEntity.getDoctor().getLastName());
        }

        // Mapowanie typów zabiegów
        if (visitEntity.getMedicalTreatments() != null) {
            List<String> treatmentTypes = visitEntity.getMedicalTreatments().stream()
                .map(MedicalTreatmentEntity::getType)
                .map(type -> type != null ? type.toString() : null)
                .collect(Collectors.toList());
            visitTO.setTreatmentTypes(treatmentTypes);
        }

        return visitTO;
    }
} 