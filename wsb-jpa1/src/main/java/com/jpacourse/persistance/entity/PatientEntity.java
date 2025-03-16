package com.jpacourse.persistance.entity;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.Set;

@Entity
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

	@OneToMany(mappedBy = "patient")
	private Set<VisitEntity> visits;

	@ManyToMany
	@JoinTable(
			name = "PATIENTS_ADDRESSES",
			joinColumns = @JoinColumn(name = "PATIENT_ID"),
			inverseJoinColumns = @JoinColumn(name = "ADDRESS_ID")
	)
	private Set<AddressEntity> addresses;

	public Long getId() { return id; }
	public String getFirstName() { return firstName; }
	public String getLastName() { return lastName; }
	public String getTelephoneNumber() { return telephoneNumber; }
	public String getEmail() { return email; }
	public String getPatientNumber() { return patientNumber; }
	public LocalDate getDateOfBirth() { return dateOfBirth; }
	public Set<VisitEntity> getVisits() { return visits; }
	public Set<AddressEntity> getAddresses() { return addresses; }
}
