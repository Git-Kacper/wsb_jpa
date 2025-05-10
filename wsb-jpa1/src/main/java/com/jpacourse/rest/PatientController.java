package com.jpacourse.rest;

import com.jpacourse.dto.PatientTo;
import com.jpacourse.rest.exception.EntityNotFoundException;
import com.jpacourse.service.PatientService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PatientController {
    private final PatientService patientService;

    public PatientController(PatientService patientService) {
        this.patientService = patientService;
    }

    @GetMapping("/patients/{id}")
    PatientTo findById(@PathVariable Long id) {
        try {
            return patientService.findById(id).orElseThrow(() -> new EntityNotFoundException(id));
        } catch (Exception e) {
            System.err.println("Error occurred: " + e.getMessage());
            throw e;
        }
    }
}
