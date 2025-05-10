package com.jpacourse.service;

import com.jpacourse.dto.AddressTO;

import java.util.Optional;

public interface AddressService
{
    Optional<AddressTO> findById(final Long id);
}
