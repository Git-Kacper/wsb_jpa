package com.jpacourse.persistance.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.*;

@Entity
@Table(name = "VISIT")
public class VisitEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "visit_seq")
	@SequenceGenerator(name = "visit_seq", sequenceName = "visit_sequence", allocationSize = 1, initialValue = 10)
	private Long id;

	private String description;

	@Column(nullable = false)
	private LocalDateTime time;

	// Relacja dwustronna od strony dziecka do pacjenta (Many-to-One)
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "patient_id", nullable = false)
	private PatientEntity patient;

	// Relacja jednostronna od strony wizyty do lekarza (Many-to-One)
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "doctor_id", nullable = false)
	private DoctorEntity doctor;

	// Relacja dwustronna do zabiegów medycznych (Many-to-Many)
	@ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.LAZY)
	@JoinTable(
		name = "VISIT_TREATMENT",
		joinColumns = @JoinColumn(name = "visit_id"),
		inverseJoinColumns = @JoinColumn(name = "treatment_id")
	)
	private List<MedicalTreatmentEntity> medicalTreatments = new ArrayList<>();

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public LocalDateTime getTime() {
		return time;
	}

	public void setTime(LocalDateTime time) {
		this.time = time;
	}

	public PatientEntity getPatient() {
		return patient;
	}

	public void setPatient(PatientEntity patient) {
		this.patient = patient;
	}

	public DoctorEntity getDoctor() {
		return doctor;
	}

	public void setDoctor(DoctorEntity doctor) {
		this.doctor = doctor;
	}

	public List<MedicalTreatmentEntity> getMedicalTreatments() {
		return medicalTreatments;
	}

	public void setMedicalTreatments(List<MedicalTreatmentEntity> medicalTreatments) {
		this.medicalTreatments = medicalTreatments;
	}

	// Metoda pomocnicza do dodawania zabiegów
	public void addMedicalTreatment(MedicalTreatmentEntity treatment) {
		medicalTreatments.add(treatment);
		treatment.getVisits().add(this);
	}

	// Metoda pomocnicza do usuwania zabiegów
	public void removeMedicalTreatment(MedicalTreatmentEntity treatment) {
		medicalTreatments.remove(treatment);
		treatment.getVisits().remove(this);
	}

}
