package com.js.bookforum.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.js.bookforum.entity.Review;
import com.js.bookforum.repository.ReviewRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {

    private final ReviewRepository reviewRepository;

    @Override
    public List<Review> findByUserId(Long userId) {
        return reviewRepository.findByUser_UserId(userId);
    }

    @Override
    public List<Review> findByBookId(Long bookId) {
        return reviewRepository.findByBook_BookId(bookId);
    }

    @Override
    public Review saveReview(Review review) {
        return reviewRepository.save(review);
    }
}