package com.jpacourse.service;

import com.jpacourse.dto.PatientTo;
import com.jpacourse.persistance.dao.AddressDao;
import com.jpacourse.persistance.dao.PatientDao;
import com.jpacourse.persistance.entity.*;
import com.jpacourse.rest.exception.PatientNotFoundException;
import com.jpacourse.service.impl.PatientServiceImpl;
import com.jpacourse.helpers.AddressHelper;
import com.jpacourse.helpers.PatientHelper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static com.jpacourse.helpers.PatientHelper.createPatient;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@Transactional
@ExtendWith(MockitoExtension.class)
public class PatientServiceTest {

    @Mock
    AddressDao addressDao;

    @Mock
    PatientDao patientDao;

    @InjectMocks
    PatientServiceImpl patientService;

    @Test
    public void shouldFindPatientById() {
        long patientId = 23L;
        PatientEntity mockPatient = createPatient();

        when(patientDao.findOne(patientId)).thenReturn(mockPatient);
        Optional<PatientTo> foundPatient = patientService.findById(patientId);

        assertThat(foundPatient).isPresent();
        assertThat(foundPatient.get().getFirstName()).isEqualTo("Jaroslaw");
    }

    @Test
    public void shouldRemovePatient() {
        // GIVEN
        long patientId = 23L;

        patientService.deleteById(patientId);

        verify(patientDao).delete(patientId);
    }

    @Test
    public void shouldThrowAnError_whenRemovePatient_and_patientNotFound() {
        long patientId = 991L;
        doThrow(new PatientNotFoundException(patientId))
                .when(patientDao).delete(patientId);

        assertThatThrownBy(() -> patientService.deleteById(patientId))
                .isInstanceOf(PatientNotFoundException.class);
    }
}
