package com.jpacourse.persistance.dao;

import com.jpacourse.dto.PatientTo;
import com.jpacourse.persistance.entity.PatientEntity;
import com.jpacourse.persistance.entity.VisitEntity;

import java.time.LocalDateTime;

public interface  PatientDao extends Dao<PatientEntity, Long> {

    // @Transactional
    void addVisitForPatient(Long patientId, Long doctorId, LocalDateTime visitDate, String visitDescription) throws IllegalArgumentException;
}
