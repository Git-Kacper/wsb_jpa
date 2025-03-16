package com.jpacourse.persistance.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Table(name = "VISIT")
public class VisitEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	private LocalDateTime visitDateTime;

	@ManyToOne
	@JoinColumn(name = "PATIENT_ID")
	private PatientEntity patient;

	@ManyToOne
	@JoinColumn(name = "DOCTOR_ID")
	private DoctorEntity doctor;

	@ManyToMany
	@JoinTable(
			name = "VISIT_TREATMENTS",
			joinColumns = @JoinColumn(name = "VISIT_ID"),
			inverseJoinColumns = @JoinColumn(name = "TREATMENT_ID")
	)
	private Set<MedicalTreatmentEntity> medicalTreatments;

	public Long getId() { return id; }
	public LocalDateTime getVisitDateTime() { return visitDateTime; }
	public PatientEntity getPatient() { return patient; }
	public DoctorEntity getDoctor() { return doctor; }
	public Set<MedicalTreatmentEntity> getMedicalTreatments() { return medicalTreatments; }
}
