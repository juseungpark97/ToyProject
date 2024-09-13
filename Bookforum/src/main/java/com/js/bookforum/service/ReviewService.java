package com.js.bookforum.service;

import com.js.bookforum.entity.Review;
import java.util.List;

public interface ReviewService {
    List<Review> findByUserId(Long userId);
    List<Review> findByBookId(Long bookId);
    Review saveReview(Review review);
}