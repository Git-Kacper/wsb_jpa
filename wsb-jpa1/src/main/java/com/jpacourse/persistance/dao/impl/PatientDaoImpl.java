package com.jpacourse.persistance.dao.impl;

import com.jpacourse.persistance.dao.PatientDao;
import com.jpacourse.persistance.entity.DoctorEntity;
import com.jpacourse.persistance.entity.PatientEntity;
import com.jpacourse.persistance.entity.VisitEntity;
import org.springframework.stereotype.Repository;

import javax.print.Doc;
import java.time.LocalDateTime;

@Repository
public class PatientDaoImpl extends  AbstractDao<PatientEntity, Long> implements PatientDao {

    @Override
    // todo: maybe return VisitEntity?
    public void addVisitForPatient(Long patientId, Long doctorId, LocalDateTime visitDate, String visitDescription) throws IllegalArgumentException{
        PatientEntity patientEntity = findOne(patientId);
        DoctorEntity doctorEntity =  entityManager.find(DoctorEntity.class, doctorId);

        if (patientEntity == null || doctorEntity == null) {
            throw new IllegalArgumentException("Patient/Doctor not found");
        }

        VisitEntity visitEntity = new VisitEntity();
        visitEntity.setPatient(patientEntity);
        visitEntity.setDoctor(doctorEntity);
        visitEntity.setTime(visitDate);
        visitEntity.setDescription(visitDescription);

        patientEntity.getVisits().add(visitEntity);
        doctorEntity.getVisits().add(visitEntity);

        entityManager.persist(visitEntity);

    }

}
