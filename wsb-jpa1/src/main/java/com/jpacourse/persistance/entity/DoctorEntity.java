package com.jpacourse.persistance.entity;

import com.jpacourse.persistance.enums.Specialization;
import jakarta.persistence.*;
import java.util.Set;

@Entity
@Table(name = "DOCTOR")
public class DoctorEntity {

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
	private String doctorNumber;

	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	private Specialization specialization;

	@OneToMany(mappedBy = "doctor")
	private Set<VisitEntity> visits;

	@ManyToMany
	@JoinTable(
			name = "DOCTOR_ADDRESSES",
			joinColumns = @JoinColumn(name = "DOCTOR_ID"),
			inverseJoinColumns = @JoinColumn(name = "ADDRESS_ID")
	)
	private Set<AddressEntity> addresses;

	public Long getId() { return id; }
	public String getFirstName() { return firstName; }
	public String getLastName() { return lastName; }
	public String getTelephoneNumber() { return telephoneNumber; }
	public String getEmail() { return email; }
	public String getDoctorNumber() { return doctorNumber; }
	public Specialization getSpecialization() { return specialization; }
	public Set<VisitEntity> getVisits() { return visits; }
}
