package com.jpacourse.persistance.dao;

import com.jpacourse.persistance.entity.AddressEntity;
import com.jpacourse.persistance.entity.PatientEntity;
import com.jpacourse.persistance.entity.VisitEntity;
import com.jpacourse.persistance.enums.TreatmentType;
import com.jpacourse.helpers.PatientHelper;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
public class PatientDaoTest {

    @Autowired
    protected EntityManager entityManager;

    @Autowired
    private AddressDao addressDao;

    @Autowired
    private PatientDao patientDao;

    @DisplayName("Should correctly persist a new patient with address")
    @Test
    void shouldPersistPatientWithAddress() {
        // given
        var patient = PatientHelper.createPatient();
        long patientCountBefore = patientDao.count();
        long addressCountBefore = addressDao.count();

        // when
        var savedPatient = patientDao.save(patient);

        // then
        assertThat(savedPatient)
                .isNotNull()
                .extracting(PatientEntity::getFirstName)
                .isEqualTo(patient.getFirstName());

        assertThat(patientDao.count()).isEqualTo(patientCountBefore + 1);
        assertThat(addressDao.count()).isEqualTo(addressCountBefore + 1);
    }

    @DisplayName("Should load a patient with single visit and treatment")
    @Test
    @Sql(scripts = {
            "/data/Address.sql",
            "/data/Patient.sql",
            "/data/Doctor.sql",
            "/data/Visit.sql",
            "/data/medical-treatments.sql"
    })
    void shouldLoadPatientWithVisitAndTreatment() {
        // given
        long patientId = 24L;

        // when
        var patient = patientDao.findOne(patientId);

        // then
        assertThat(patient)
                .isNotNull()
                .extracting(PatientEntity::getFirstName, PatientEntity::getLastName, PatientEntity::getPatientNumber)
                .containsExactly("Robert", "Mars", "420");

        assertThat(patient.getAddress())
                .extracting(AddressEntity::getAddressLine1, AddressEntity::getAddressLine2, AddressEntity::getCity)
                .containsExactly("Sosnowa 456", "Lokal 300", "Warszawa");

        var visit = patient.getVisits().get(0);
        assertThat(visit)
                .isNotNull()
                .satisfies(v -> {
                    assertThat(v.getDoctor())
                            .extracting(d -> d.getFirstName(), d -> d.getLastName())
                            .containsExactly("Michael", "Rodriguez");

                    assertThat(v.getTime()).isEqualTo(LocalDateTime.of(2025, 3, 18, 14, 0));
                    assertThat(v.getMedicalTreatments())
                            .hasSize(1)
                            .first()
                            .extracting(t -> t.getType())
                            .isEqualTo(TreatmentType.RTG);
                });
    }


    @DisplayName("Should return patients with more than 1 visit")
    @Test
    @Sql(scripts = {
            "/data/Address.sql",
            "/data/Patient.sql",
            "/data/Doctor.sql",
            "/data/Visit.sql"
    })
    public void shouldFindAllPatientsWithMoreThan1Visit() {
        // when
        List<PatientEntity> patients = patientDao.findPatientsByVisitCountGreaterThan(1);

        // then
        assertThat(patients).isNotNull().hasSize(2);
    }

    @DisplayName("Should find patients by insurance status and insurance start date")
    @Test
    @Sql(scripts = {
            "/data/Address.sql",
            "/data/Patient.sql"
    })
    void shouldFindPatientsByInsuranceStatusAfterDate() {
        // given
        Boolean insured = true;
        LocalDate afterDate = LocalDate.of(2021, 12, 31);

        // when
        List<PatientEntity> result = patientDao.findPatientsByInsuranceStatusAfterDate(insured, afterDate);

        // then
        assertThat(result)
                .isNotNull()
                .hasSize(3)
                .allSatisfy(patient -> {
                    assertThat(patient.getInsured()).isTrue();
                    assertThat(patient.getInsuranceStartDate()).isAfter(afterDate);
                });

        var patientNames = result.stream().map(PatientEntity::getFirstName).toList();
        assertThat(patientNames).containsExactlyInAnyOrder("Sarah", "Julia", "William");
    }

    @Test
    @Sql(scripts = {
            "/data/Address.sql",
            "/data/Patient.sql",
            "/data/Doctor.sql",
            "/data/Visit.sql",
            "/data/medical-treatments.sql"
    })
    public void shouldFindPatientByIdWithMultipleVisits() {
        // GIVEN
        long patientId = 22L;

        // WHEN
        PatientEntity found = patientDao.findOne(patientId);

        assertThat(found).isNotNull();
        assertThat(found.getFirstName()).isEqualTo("Connor");
        assertThat(found.getLastName()).isEqualTo("McGregor");
        assertThat(found.getPatientNumber()).isEqualTo("6921");
        assertThat(found.getInsured()).isEqualTo(false);

        AddressEntity address = found.getAddress();
        assertThat(address).isNotNull();
        assertThat(address.getId()).isNotNull();
        assertThat(address.getAddressLine1()).isEqualTo("Parkowa 87");
        assertThat(address.getCity()).isEqualTo("Łeba");

        assertThat(found.getVisits()).isNotNull();
        assertThat(found.getVisits()).hasSize(5);
    }

    @Test
    @Sql(scripts = {
            "/data/Address.sql",
            "/data/Patient.sql"
    })
    public void shouldFindAllPatients() {
        int patientsCount = 5;

        List<PatientEntity> found = patientDao.findAll();

        assertThat(found).isNotNull();
        assertThat(found).hasSize(patientsCount);
    }

    @Test
    @Sql(scripts = {
            "/data/Address.sql",
            "/data/Patient.sql"
    })
    public void shouldFindAllPatientsByLastName() {
        int patientsWithSameLastNameCount = 3;

        List<PatientEntity> found = patientDao.findPatientsByLastName("Mars");

        assertThat(found).isNotNull();
        assertThat(found).hasSize(patientsWithSameLastNameCount);
        assertThat(found).filteredOn("lastName", "Mars").isNotNull();
    }

    @Test
    @Sql(scripts = {
            "/data/Address.sql",
            "/data/Patient.sql",
            "/data/Doctor.sql",
            "/data/Visit.sql",
            "/data/medical-treatments.sql"
    })
    public void shouldRemovePatientById() {
        long patientsCountBefore = patientDao.count();
        long AddressCountBefore = addressDao.count();
        long doctorsCountBefore = countAllDoctorTableEntities();
        long visitsCountBefore = countAllVisitTableEntities();
        long medicalTreatmentsCountBefore = countAllMedicalTreatmentTableEntities();

        long patientId = 23L;
        long patientAddressId = patientDao.findOne(patientId).getAddress().getId();
        long patientVisitsCount = 2;
        long patientVisitsMedicalTreatments = 3;

        patientDao.delete(patientId);

        assertThat(patientDao.findOne(patientId)).isNull();
        assertThat(addressDao.findOne(patientAddressId)).isNull();
        assertThat(patientDao.count()).isEqualTo(patientsCountBefore - 1);
        assertThat(addressDao.count()).isEqualTo(AddressCountBefore - 1);
        assertThat(countAllDoctorTableEntities()).isEqualTo(doctorsCountBefore);
        assertThat(countAllVisitTableEntities()).isEqualTo(visitsCountBefore - patientVisitsCount);
        assertThat(countAllMedicalTreatmentTableEntities())
                .isEqualTo(medicalTreatmentsCountBefore - patientVisitsMedicalTreatments);
    }

    @Test
    @Sql(scripts = {
            "/data/Address.sql",
            "/data/Patient.sql",
            "/data/Doctor.sql"
    })
    public void shouldAddPatientVisit() {
        long patientId = 24L;
        long doctorId = 123L;
        LocalDateTime visitDate = LocalDateTime.of(2025, 4, 20, 14, 30);
        String visitDescription = "follow-up visit";

        assertThatCode(() -> patientDao.addVisitForPatient(patientId, doctorId, visitDate, visitDescription)).doesNotThrowAnyException();

        PatientEntity patient = patientDao.getOne(patientId);
        assertThat(patient).isNotNull();
        assertThat(patient.getVisits()).hasSize(1);

        VisitEntity visit = patient.getVisits().get(0);
        assertThat(visit).isNotNull();
        assertThat(visit.getPatient().getId()).isEqualTo(patientId);
        assertThat(visit.getDoctor().getId()).isEqualTo(doctorId);
        assertThat(visit.getTime()).isEqualTo(visitDate);
        assertThat(visit.getDescription()).isEqualTo(visitDescription);
    }

    @Test
    @Sql(scripts = {
            "/data/Address.sql",
            "/data/Patient.sql",
            "/data/Doctor.sql",
            "/data/Visit.sql"
    })
    public void shouldRemovePatientVisit() {
        PatientEntity patient = patientDao.getOne(24L);
        assertThat(patient.getVisits()).hasSize(1);

        VisitEntity visit = patient.getVisits().get(0);
        patientDao.removeVisitFromPatient(patient.getId(), visit.getId());
        entityManager.flush();
        entityManager.clear();

        PatientEntity updatedPatient = patientDao.getOne(24L);
        assertThat(updatedPatient.getVisits()).isEmpty();

        VisitEntity deletedVisit = entityManager.find(VisitEntity.class, visit.getId());
        assertThat(deletedVisit).isNull(); // poprawiona asercja
    }


    private long countAllDoctorTableEntities() {
        return countAllTableEntities("DoctorEntity");
    }

    private long countAllVisitTableEntities() {
        return countAllTableEntities("VisitEntity");
    }

    private long countAllMedicalTreatmentTableEntities() {
        return countAllTableEntities("MedicalTreatmentEntity");
    }

    private long countAllTableEntities(String tableName) {
        String jpql = "SELECT COUNT(e) FROM " + tableName + " e";
        return entityManager.createQuery(jpql, Long.class).getSingleResult();
    }
}

// Porównanie metod ładowania danych w Hibernate

// Scenariusz 1 — FetchMode.SELECT
// W pierwszym przypadku użyto @Fetch(FetchMode.SELECT) dla kolekcji wizyt.
// Skutkuje to trzema osobnymi zapytaniami:
// - Pobranie danych pacjenta
// - Pobranie wizyt pacjenta
// - Pobranie adresu pacjenta
// Powoduje to problem N+1 zapytań dla każdej wizyty.
//
// Przykładowe zapytania:
// select ... from patient where id = ?
// select ... from visit where patient_id = ?
// select ... from address where id = ?

// Scenariusz 2 — FetchMode.JOIN
// W drugim przypadku zastosowano @Fetch(FetchMode.JOIN) dla wizyt.
// Efekt:
// - Wykonanie dwóch zapytań:
//   - Jedno pobierające pacjenta wraz z wizytami (LEFT JOIN)
//   - Drugie pobierające adres pacjenta
// Rozwiązanie eliminuje problem N+1 zapytań.
//
// Przykładowe zapytania:
// select ... from patient left join visit on patient.id = visit.patient_id where patient.id = ?
// select ... from address where id = ?

// Podsumowanie:

// Wydajność:
// - FetchMode.JOIN jest wydajniejszy przy pobieraniu pojedynczego pacjenta.
// - Przy ładowaniu wielu pacjentów JOIN może powodować duplikację danych i większy rozmiar przesyłanych danych.

// Zużycie pamięci:
// - FetchMode.JOIN powiela dane pacjenta dla każdej wizyty, co może zwiększać użycie pamięci.

// Lazy loading adresu:
// - W obu przypadkach adres ładowany jest osobno (FetchType.LAZY), co jest korzystne, gdy nie zawsze potrzebujemy adresu.

// Optymalizacja:
// - Przy dużej liczbie wizyt warto rozważyć FetchMode.SUBSELECT, aby pobrać wszystkie wizyty jednym zbiorczym zapytaniem.

// Zastosowanie:
// - FetchMode.JOIN — gdy zazwyczaj potrzebne są dane pacjenta i wizyt razem.
// - FetchMode.SELECT — gdy często wystarczą same dane pacjenta bez wizyt.