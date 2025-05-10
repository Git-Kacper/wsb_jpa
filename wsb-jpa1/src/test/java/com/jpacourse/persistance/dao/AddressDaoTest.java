package com.jpacourse.persistance.dao;

import com.jpacourse.persistance.entity.AddressEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.orm.jpa.JpaObjectRetrievalFailureException;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@Transactional
@ActiveProfiles("test")
public class AddressDaoTest {
    @Autowired
    private AddressDao addressDao;

    @Test
    @Sql("/data/address.sql")
    public void testShouldFindAddressById() {
        Long warsaw_id = 69L;

        AddressEntity addressEntity = addressDao.findOne(warsaw_id);

        assertThat(addressEntity).isNotNull();
        assertThat(addressEntity.getCity()).isEqualTo("Warszawa");
    }

    @Test
    @Sql("/data/address.sql")
    public void testShouldRemoveAddressById() {
        Long warsaw_id = 69L;
        long entries = addressDao.count();

        addressDao.delete(warsaw_id);

        assertThat(addressDao.count()).isEqualTo(entries - 1);
        assertThat(addressDao.findOne(warsaw_id)).isNull();
    }

    @Test
    @Sql("/data/address.sql")
    public void testShouldNotRemoveAddressIfIdNotExist() {

        Long notExistId = 999L;

        assertThrows(JpaObjectRetrievalFailureException.class, () -> {
            addressDao.delete(notExistId);
        });
    }

    @Transactional
    @Test
    public void testShouldSaveAddress() {
        // given
        AddressEntity addressEntity = new AddressEntity();
        addressEntity.setAddressLine1("line1");
        addressEntity.setAddressLine2("line2");
        addressEntity.setCity("City1");
        addressEntity.setPostalCode("27-677");
        long entitiesNumBefore = addressDao.count();

        // when
        final AddressEntity saved = addressDao.save(addressEntity);

        // then
        assertThat(saved).isNotNull();
        assertThat(saved.getId()).isNotNull();
        assertThat(saved.getPostalCode()).isEqualTo("27-677");
        assertThat(addressDao.count()).isEqualTo(entitiesNumBefore + 1);
    }

    @Transactional
    @Test
    public void testShouldSaveAndRemoveAddress() {
        // given
        AddressEntity addressEntity = new AddressEntity();
        addressEntity.setAddressLine1("line1");
        addressEntity.setAddressLine2("line2");
        addressEntity.setCity("City1");
        addressEntity.setPostalCode("66-666");

        // when
        final AddressEntity saved = addressDao.save(addressEntity);
        assertThat(saved.getId()).isNotNull();
        final AddressEntity newSaved = addressDao.findOne(saved.getId());
        assertThat(newSaved).isNotNull();

        addressDao.delete(saved.getId());

        // then
        final AddressEntity removed = addressDao.findOne(saved.getId());
        assertThat(removed).isNull();
    }
}
