package com.js.bookforum.repository;

import com.js.bookforum.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    List<Review> findByUser_UserId(Long userId);
    List<Review> findByBook_BookId(Long bookId);
}