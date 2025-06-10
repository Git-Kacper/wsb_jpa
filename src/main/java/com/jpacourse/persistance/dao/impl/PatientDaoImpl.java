package com.jpacourse.persistance.dao.impl;

import com.jpacourse.persistance.dao.PatientDao;
import com.jpacourse.persistance.entity.PatientEntity;
import com.jpacourse.persistance.entity.VisitEntity;
import com.jpacourse.persistance.entity.DoctorEntity;
import org.springframework.stereotype.Repository;

import jakarta.persistence.TypedQuery;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public class PatientDaoImpl extends AbstractDao<PatientEntity, Long> implements PatientDao {

    @Override
    public VisitEntity addVisitToPatient(Long patientId, Long doctorId, LocalDateTime visitTime, String description) {
        // Pobierz pacjenta i lekarza
        PatientEntity patient = findOne(patientId);
        DoctorEntity doctor = entityManager.find(DoctorEntity.class, doctorId);
        
        if (patient == null || doctor == null) {
            throw new IllegalArgumentException("Pacjent lub lekarz nie został znaleziony");
        }

        // Utwórz nową wizytę
        VisitEntity visit = new VisitEntity();
        visit.setDescription(description);
        visit.setTime(visitTime);
        visit.setPatient(patient);
        visit.setDoctor(doctor);

        // Zapisz wizytę bezpośrednio
        entityManager.persist(visit);
        
        // Dodaj wizytę do pacjenta
        patient.addVisit(visit);
        
        return visit;
    }

    @Override
    public List<PatientEntity> findByLastName(String lastName) {
        TypedQuery<PatientEntity> query = entityManager.createQuery(
            "SELECT p FROM PatientEntity p WHERE p.lastName = :lastName", 
            PatientEntity.class
        );
        query.setParameter("lastName", lastName);
        return query.getResultList();
    }

    @Override
    public List<VisitEntity> findVisitsByPatientId(Long patientId) {
        TypedQuery<VisitEntity> query = entityManager.createQuery(
            "SELECT v FROM VisitEntity v WHERE v.patient.id = :patientId ORDER BY v.time DESC", 
            VisitEntity.class
        );
        query.setParameter("patientId", patientId);
        return query.getResultList();
    }

    @Override
    public List<PatientEntity> findPatientsWithMoreThanXVisits(int minVisitCount) {
        TypedQuery<PatientEntity> query = entityManager.createQuery(
            "SELECT p FROM PatientEntity p WHERE SIZE(p.visits) > :minVisitCount", 
            PatientEntity.class
        );
        query.setParameter("minVisitCount", minVisitCount);
        return query.getResultList();
    }

    @Override
    public List<PatientEntity> findPatientsOlderThan(int minAge) {
        TypedQuery<PatientEntity> query = entityManager.createQuery(
            "SELECT p FROM PatientEntity p WHERE p.age > :minAge", 
            PatientEntity.class
        );
        query.setParameter("minAge", minAge);
        return query.getResultList();
    }
} 