package com.jpacourse.dto;

import com.jpacourse.persistance.entity.AddressEntity;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
public class PatientTo implements Serializable {
    private Long id;
    private String firstName;
    private String lastName;
    private String telephoneNumber;
    private String email;
    private String patientNumber;
    private LocalDate dateOfBirth;
    private AddressEntity address;
    private List<VisitTo> visits;
}
