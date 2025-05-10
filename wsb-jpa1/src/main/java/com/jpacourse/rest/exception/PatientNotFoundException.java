package com.jpacourse.rest.exception;

public class PatientNotFoundException extends RuntimeException {
    public PatientNotFoundException(Long id) {
        super(String.format("Could not find patient of ID: %s", id));
    }
}