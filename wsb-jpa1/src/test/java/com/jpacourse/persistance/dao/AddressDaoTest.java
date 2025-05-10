package com.jpacourse.persistance.dao;

import com.jpacourse.persistance.entity.AddressEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.orm.jpa.JpaObjectRetrievalFailureException;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@ActiveProfiles("test")
public class AddressDaoTest {

    @Autowired
    private AddressDao addressDao;

    @Transactional
    @Test
    public void shouldSaveAddress() {
        // GIVEN
        AddressEntity address = createAddress();
        long entitiesCountBefore = addressDao.count();

        // WHEN
        AddressEntity saved = addressDao.save(address);

        // THEN
        assertThat(saved).isNotNull();
        assertThat(saved.getId()).isNotNull();
        assertThat(addressDao.count()).isEqualTo(entitiesCountBefore + 1);
    }

    @Transactional
    @Test
    @Sql("/data/Address.sql")
    public void shouldFindAddressById() {
        long addressId = 901L;

        AddressEntity found = addressDao.findOne(addressId);

        assertThat(found).isNotNull();
        assertThat(found.getPostalCode()).isEqualTo("00-001");
    }

    @Transactional
    @Test
    @Sql("/data/Address.sql")
    public void shouldFindAllAddress() {
        int entitiesCount = 7;

        List<AddressEntity> Address = addressDao.findAll();

        assertThat(Address).isNotNull();
        assertThat(Address).hasSize(entitiesCount);
    }

    @Transactional
    @Test
    @Sql("/data/Address.sql")
    public void testShouldFindAddressById() {
        Long warsaw_id = 901L;

        AddressEntity addressEntity = addressDao.findOne(warsaw_id);

        assertThat(addressEntity).isNotNull();
        assertThat(addressEntity.getCity()).isEqualTo("Nowa Warszawa");
    }

    @Test
    @Sql("/data/Address.sql")
    public void testShouldNotRemoveAddressIfIdNotExist() {
        Long notExistId = 999L;

        assertThrows(JpaObjectRetrievalFailureException.class, () -> {
            addressDao.delete(notExistId);
        });
    }

    @Transactional
    @Test
    @Sql("/data/Address.sql")
    public void shouldRemoveAddressById() {
        long addressId = 902L;
        long entitiesCountBefore = addressDao.count();

        addressDao.delete(addressId);

        assertThat(addressDao.count()).isEqualTo(entitiesCountBefore - 1);
        assertThat(addressDao.findOne(addressId)).isNull();
    }

    private AddressEntity createAddress() {
        AddressEntity addressEntity = new AddressEntity();
        addressEntity.setAddressLine1("250 Hospital Drive");
        addressEntity.setAddressLine2("Building A");
        addressEntity.setCity("Wroclaw");
        addressEntity.setPostalCode("10001");

        return addressEntity;
    }
}
