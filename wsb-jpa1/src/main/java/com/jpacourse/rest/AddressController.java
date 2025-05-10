package com.jpacourse.rest;

import com.jpacourse.dto.AddressTO;
import com.jpacourse.rest.exception.AddressNotFoundException;
import com.jpacourse.service.AddressService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AddressController {

    private final AddressService addressService;


    public AddressController(AddressService addressService) {
        this.addressService = addressService;
    }

    @GetMapping("/address/{id}")
    AddressTO findById(@PathVariable final Long id) {
        try {
            return addressService.findById(id).orElseThrow(() -> new AddressNotFoundException(id));
        } catch (Exception e) {
            System.err.println("Error occurred: " + e.getMessage());
            throw e;
        }
    }
}
