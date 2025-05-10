package com.jpacourse.service.impl;

import com.jpacourse.dto.AddressTO;
import com.jpacourse.mapper.AddressMapper;
import com.jpacourse.persistance.dao.AddressDao;
import com.jpacourse.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;


@Service
@Transactional
public class AddressServiceImpl implements AddressService
{
    private final AddressDao addressDao;

    @Autowired
    public AddressServiceImpl(AddressDao pAddressDao)
    {
        addressDao = pAddressDao;
    }

    @Override
    public Optional<AddressTO> findById(Long id) {
        return Optional.ofNullable(AddressMapper.mapToTo(addressDao.findOne(id)));
    }
}
