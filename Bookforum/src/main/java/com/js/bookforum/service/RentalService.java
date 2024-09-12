package com.js.bookforum.service;

import com.js.bookforum.entity.Rental;
import com.js.bookforum.entity.User;

public interface RentalService {
    Rental saveRental(Rental rental);
    Rental findByUserAndBook(User user, Long bookId);
    void returnBook(User user, Long bookId);
}