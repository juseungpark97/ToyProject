package com.js.bookforum.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.js.bookforum.entity.Rental;
import com.js.bookforum.entity.Review;
import com.js.bookforum.entity.User;

public interface RentalService {
    Rental saveRental(Rental rental);
    Rental findByUserAndBook(User user, Long bookId);
    void returnBook(User user, Long bookId);
    Page<Rental> findRentalsByUserId(Long userId, Pageable pageable);
   
}