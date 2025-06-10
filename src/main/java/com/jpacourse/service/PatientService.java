package com.jpacourse.service;

import com.jpacourse.dto.PatientTO;
import com.jpacourse.persistance.entity.VisitEntity;

import java.time.LocalDateTime;
import java.util.List;

public interface PatientService {
    
    /**
     * Znajdź pacjenta po ID
     * @param id ID pacjenta
     * @return PatientTO z listą wizyt
     */
    PatientTO findById(Long id);
    
    /**
     * Usuń pacjenta po ID
     * @param id ID pacjenta
     */
    void deletePatient(Long id);
    
    /**
     * Znajdź wszystkie wizyty pacjenta po jego ID
     * @param patientId ID pacjenta
     * @return lista wizyt
     */
    List<VisitEntity> findVisitsByPatientId(Long patientId);
    
    /**
     * Dodaj wizytę do pacjenta
     * @param patientId ID pacjenta
     * @param doctorId ID doktora
     * @param visitTime data wizyty
     * @param description opis wizyty
     * @return utworzona wizyta
     */
    VisitEntity addVisitToPatient(Long patientId, Long doctorId, LocalDateTime visitTime, String description);
} 