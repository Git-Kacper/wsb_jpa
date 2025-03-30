package com.jpacourse.persistance.entity;

import jakarta.persistence.*;
import java.util.Set;

@Entity
@Table(name = "ADDRESS")
public class AddressEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String city;

	private String addressLine1;

	private String addressLine2;

	private String postalCode;

	@ManyToMany(mappedBy = "addresses")
	private Set<PatientEntity> patients;

	public Long getId() { return id; }
	public String getCity() { return city; }
	public String getAddressLine1() { return addressLine1; }
	public String getAddressLine2() { return addressLine2; }
	public String getPostalCode() { return postalCode; }
	public Set<PatientEntity> getPatients() { return patients; }

	public void setId(Long id) {
	}

	public void setAddressLine1(String addressLine1) {
	}

	public void setAddressLine2(String addressLine2) {
	}

	public void setCity(String city) {
	}

	public void setPostalCode(String postalCode) {
	}
}
