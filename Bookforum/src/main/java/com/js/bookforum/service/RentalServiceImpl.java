package com.js.bookforum.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.js.bookforum.entity.Rental;
import com.js.bookforum.repository.RentalRepository;

@Service
public class RentalServiceImpl implements RentalService {

    @Autowired
    private RentalRepository rentalRepository;

    @Override
    public Rental saveRental(Rental rental) {
        return rentalRepository.save(rental);
    }
}