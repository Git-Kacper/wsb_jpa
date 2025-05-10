package com.jpacourse.persistance.entity;

import java.time.LocalDate;
import java.util.List;
import java.util.ArrayList;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "PATIENT")
public class PatientEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	private String firstName;

	@Column(nullable = false)
	private String lastName;

	@Column(nullable = false)
	private String telephoneNumber;

	private String email;

	@Column(nullable = false)
	private String patientNumber;

	@Column(nullable = false)
	private LocalDate dateOfBirth;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(nullable = false, name = "ADDRESS_ID")
	private AddressEntity address;

	@OneToMany(mappedBy = "patient", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<VisitEntity> visits = new ArrayList<>();

	public void addVisit(VisitEntity visit) {
		this.visits.add(visit);
		visit.setPatient(this);
	}

	public void removeVisit(VisitEntity visit) {
		this.visits.remove(visit);
		visit.setPatient(null);
	}
}
