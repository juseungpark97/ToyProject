package com.js.bookforum.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.js.bookforum.entity.Review;

public interface ReviewRepository extends JpaRepository<Review, Long> {

}