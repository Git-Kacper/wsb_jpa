package com.jpacourse.persistance.dao;

import com.jpacourse.persistance.entity.PatientEntity;
import jakarta.transaction.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface PatientDao extends Dao<PatientEntity, Long> {

    @Transactional
    void addVisitForPatient(Long patientId, Long doctorId, LocalDateTime visitDate, String visitDescription) throws IllegalArgumentException;

    @Transactional
    void removeVisitFromPatient(Long patientId, Long visitId) throws IllegalArgumentException;

    List<PatientEntity> findPatientsByLastName(String lastName);

    List<PatientEntity> findPatientsByVisitCountGreaterThan(int visitsCount);

    List<PatientEntity> findPatientsByInsuranceStatusAfterDate(Boolean insured, LocalDate date);
}
