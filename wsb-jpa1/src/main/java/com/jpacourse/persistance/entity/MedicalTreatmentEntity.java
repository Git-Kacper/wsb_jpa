package com.jpacourse.persistance.entity;

import com.jpacourse.persistance.enums.TreatmentType;
import jakarta.persistence.*;
import java.util.Set;

@Entity
@Table(name = "MEDICAL_TREATMENT")
public class MedicalTreatmentEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	private String description;

	@Enumerated(EnumType.STRING)
	private TreatmentType type;

	@ManyToMany(mappedBy = "medicalTreatments")
	private Set<VisitEntity> visits;

	public Long getId() { return id; }
	public String getDescription() { return description; }
	public TreatmentType getType() { return type; }
	public Set<VisitEntity> getVisits() { return visits; }
}
