package com.js.bookforum.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.js.bookforum.entity.Review;

@Repository 
public interface ReviewRepository extends JpaRepository<Review, Long> {

}