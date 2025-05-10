package com.jpacourse.mapper;

import com.jpacourse.dto.VisitTo;
import com.jpacourse.persistance.entity.MedicalTreatmentEntity;
import com.jpacourse.persistance.entity.VisitEntity;


public class VisitMapper {

    public static VisitTo mapToTo(final VisitEntity visitEntity) {
        if (visitEntity == null) {
            return null;
        }

        final VisitTo visitTo = new VisitTo();
        visitTo.setVisitDate(visitEntity.getTime());
        visitTo.setDoctorFirstName(visitEntity.getDoctor().getFirstName());
        visitTo.setDoctorLastName(visitEntity.getDoctor().getLastName());
        visitTo.setTreatmentTypes(visitEntity.getMedicalTreatments().stream().map(MedicalTreatmentEntity::getType).toList());

        return visitTo;
    }
}
