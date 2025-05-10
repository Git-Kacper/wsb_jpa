package com.jpacourse.persistance.entity;

import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "VISIT")
public class VisitEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String description;

    @Column(nullable = false)
    private LocalDateTime time;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, name = "DOCTOR_ID")
    private DoctorEntity doctor;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, name = "PATIENT_ID")
    private PatientEntity patient;

    @OneToMany(mappedBy = "visit", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<MedicalTreatmentEntity> medicalTreatments;

    public void addMedicalTreatment(MedicalTreatmentEntity medicalTreatment) {
        this.medicalTreatments.add(medicalTreatment);
        medicalTreatment.setVisit(this);
    }

    public void removeMedicalTreatment(MedicalTreatmentEntity visit) {
        this.medicalTreatments.remove(visit);
        visit.setVisit(null);
    }
}
