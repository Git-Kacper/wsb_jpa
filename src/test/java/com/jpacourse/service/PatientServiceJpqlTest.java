package com.jpacourse.service;

import com.jpacourse.persistance.dao.PatientDao;
import com.jpacourse.persistance.entity.VisitEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@TestPropertySource(locations = "classpath:application-test.properties")
public class PatientServiceJpqlTest {

    @Autowired
    private PatientService patientService;

    @Autowired
    private PatientDao patientDao;

    @Test
    @Transactional
    public void testShouldFindVisitsByPatientId_ServiceTest() {
        // given
        Long patientId = 1L;

        // when
        List<VisitEntity> visits = patientService.findVisitsByPatientId(patientId);

        // then
        assertThat(visits).isNotEmpty();
        assertThat(visits).allSatisfy(visit -> 
            assertThat(visit.getPatient().getId()).isEqualTo(patientId)
        );
        
        // Sprawdź kolejność sortowania (ORDER BY time DESC)
        if (visits.size() > 1) {
            for (int i = 0; i < visits.size() - 1; i++) {
                assertThat(visits.get(i).getTime())
                    .isAfterOrEqualTo(visits.get(i + 1).getTime());
            }
        }
    }
} 