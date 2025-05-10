package com.jpacourse.helpers;

import com.jpacourse.persistance.entity.AddressEntity;

public class AddressHelper {

    public static AddressEntity createAddress() {
        AddressEntity address = new AddressEntity();
        address.setAddressLine1("87 Parkway Ave");
        address.setAddressLine2("Floor 3");
        address.setCity("Miami");
        address.setPostalCode("33101");

        return address;
    }
}
