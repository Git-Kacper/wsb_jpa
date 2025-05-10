package com.jpacourse.service.impl;

import com.jpacourse.dto.PatientTo;
import com.jpacourse.mapper.PatientMapper;
import com.jpacourse.rest.exception.EntityNotFoundException;
import com.jpacourse.rest.exception.PatientNotFoundException;
import com.jpacourse.service.PatientService;
import com.jpacourse.persistance.dao.PatientDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;


@Service
@Transactional
public class PatientServiceImpl implements PatientService {
    private final PatientDao patientDao;

    @Autowired
    public PatientServiceImpl(PatientDao patientDao) {
        this.patientDao = patientDao;
    }

    @Override
    public Optional<PatientTo> findById(Long id) {
        return Optional.ofNullable(PatientMapper.mapToTo(patientDao.findOne(id)));
    }

    @Override
    public void deleteById(Long id) {
        try {
            patientDao.delete(id);
        } catch (EntityNotFoundException e) {
            throw new PatientNotFoundException(id);
        }
    }
}
