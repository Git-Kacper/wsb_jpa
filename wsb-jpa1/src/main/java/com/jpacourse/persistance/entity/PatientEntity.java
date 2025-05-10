package com.jpacourse.persistance.entity;

import java.time.LocalDate;
import java.util.List;
import java.util.ArrayList;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

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

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, name = "ADDRESS_ID", referencedColumnName = "id")
    private AddressEntity address;

    @OneToMany(mappedBy = "patient", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @Fetch(FetchMode.JOIN)
    private List<VisitEntity> visits = new ArrayList<>();

    private Boolean insured;

    @Column(nullable = true)
    private LocalDate insuranceStartDate;

    public void addVisit(VisitEntity visit) {
        this.visits.add(visit);
        visit.setPatient(this);
    }

    public void removeVisit(VisitEntity visit) {
        this.visits.remove(visit);
        visit.setPatient(null);
    }
}
