package com.jpacourse.service;

import com.jpacourse.dto.PatientTo;
import com.jpacourse.rest.exception.PatientNotFoundException;

import java.util.Optional;

public interface PatientService
{
    Optional<PatientTo> findById(final Long id);

    void deleteById(final Long id) throws PatientNotFoundException;
}
