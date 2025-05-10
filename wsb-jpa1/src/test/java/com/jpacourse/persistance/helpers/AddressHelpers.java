package com.jpacourse.persistance.helpers;

import com.jpacourse.persistance.entity.AddressEntity;

public class AddressHelpers {

    public static AddressEntity createAddress() {
        AddressEntity address = new AddressEntity();
        address.setAddressLine1("address line 1");
        address.setAddressLine2("address line 2");
        address.setCity("city");
        address.setPostalCode("699");

        return address;
    }
}
