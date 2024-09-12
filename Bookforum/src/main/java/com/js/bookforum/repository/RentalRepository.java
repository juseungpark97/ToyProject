package com.js.bookforum.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.js.bookforum.entity.Book;
import com.js.bookforum.entity.Rental;
import com.js.bookforum.entity.User;

@Repository
public interface RentalRepository extends JpaRepository<Rental, Long> {
	Optional<Rental> findByUserAndBookAndReturnDateIsNull(User user, Book book);
	
}