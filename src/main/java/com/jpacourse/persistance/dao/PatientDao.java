package com.jpacourse.persistance.dao;

import com.jpacourse.persistance.entity.PatientEntity;
import com.jpacourse.persistance.entity.VisitEntity;

import java.time.LocalDateTime;
import java.util.List;

public interface PatientDao extends Dao<PatientEntity, Long> {

    /**
     * Metoda do tworzenia nowej wizyty i dodania jej do pacjenta w jednym wywołaniu
     * @param patientId ID pacjenta
     * @param doctorId ID doktora
     * @param visitTime data wizyty
     * @param description opis wizyty
     * @return utworzona wizyta
     */
    VisitEntity addVisitToPatient(Long patientId, Long doctorId, LocalDateTime visitTime, String description);

    /**
     * Znajdź pacjentów po nazwisku
     * @param lastName nazwisko
     * @return lista pacjentów
     */
    List<PatientEntity> findByLastName(String lastName);

    /**
     * Znajdź wszystkie wizyty pacjenta po jego ID
     * @param patientId ID pacjenta
     * @return lista wizyt
     */
    List<VisitEntity> findVisitsByPatientId(Long patientId);

    /**
     * Znajdź pacjentów którzy mieli więcej niż X wizyt
     * @param minVisitCount minimalna liczba wizyt
     * @return lista pacjentów
     */
    List<PatientEntity> findPatientsWithMoreThanXVisits(int minVisitCount);

    /**
     * Znajdź pacjentów po wieku (większym niż podany)
     * @param minAge minimalny wiek
     * @return lista pacjentów
     */
    List<PatientEntity> findPatientsOlderThan(int minAge);
} 