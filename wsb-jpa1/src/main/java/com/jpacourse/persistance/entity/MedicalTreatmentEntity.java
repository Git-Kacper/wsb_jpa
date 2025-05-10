package com.jpacourse.persistance.entity;

import com.jpacourse.persistance.enums.TreatmentType;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "MEDICAL_TREATMENT")
public class MedicalTreatmentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String description;

    @Enumerated(EnumType.STRING)
    private TreatmentType type;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "VISIT_ID")
    private VisitEntity visit;
}
