package com.jpacourse.dto;

import com.jpacourse.persistance.enums.TreatmentType;

import java.time.LocalDateTime;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VisitTo {
    private LocalDateTime visitDate;
    private String doctorFirstName; // Todo: maybe create a struct for doctor or record
    private String doctorLastName;
    private List<TreatmentType> treatmentTypes;
}
