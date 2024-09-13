package com.js.bookforum.controller;

import java.security.Principal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.js.bookforum.dto.ReviewDTO;
import com.js.bookforum.entity.Book;
import com.js.bookforum.entity.Review;
import com.js.bookforum.entity.User;
import com.js.bookforum.service.BookService;
import com.js.bookforum.service.ReviewService;
import com.js.bookforum.service.UserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/reviews")
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;
    private final UserService userService;
    private final BookService bookService;

    // 특정 사용자가 작성한 리뷰를 조회하는 API
    @GetMapping("/user/{email}")
    public ResponseEntity<List<ReviewDTO>> getReviewsByUser(@PathVariable String email) {
        User user = userService.findByEmail(email);
        if (user == null) {
            return ResponseEntity.badRequest().build();
        }
        List<Review> reviews = reviewService.findByUserId(user.getUserId());
        List<ReviewDTO> reviewDTOs = reviews.stream().map(review -> 
            ReviewDTO.builder()
                .reviewId(review.getReviewId())
                .name(review.getUser().getName()) // 작성자 이름 설정
                .creationDate(formatDate(review.getCreationDate())) // 날짜 형식 변환
                .rating(review.getRating())
                .content(review.getContent())
                .bookId(review.getBook().getBookId()) // 책 ID 설정
                .build()
        ).collect(Collectors.toList());
        return ResponseEntity.ok(reviewDTOs);
    }

    // 리뷰 생성 API
    @PostMapping
    public ResponseEntity<ReviewDTO> createReview(@RequestBody ReviewDTO reviewDTO, Principal principal) {
        User user = userService.findByEmail(principal.getName());
        if (user == null) {
            return ResponseEntity.badRequest().build();
        }

        Book book = bookService.findById(reviewDTO.getBookId());
        if (book == null) {
            return ResponseEntity.badRequest().body(null);
        }

        Review review = Review.builder()
            .user(user)
            .book(book)
            .rating(reviewDTO.getRating())
            .content(reviewDTO.getContent())
            .creationDate(new Date()) // 현재 시간으로 설정
            .build();

        Review savedReview = reviewService.saveReview(review);

        ReviewDTO responseDTO = ReviewDTO.builder()
            .reviewId(savedReview.getReviewId())
            .name(savedReview.getUser().getName()) // 작성자 이름 설정
            .creationDate(formatDate(savedReview.getCreationDate())) // 작성일자 형식 변환
            .rating(savedReview.getRating())
            .content(savedReview.getContent())
            .bookId(savedReview.getBook().getBookId())
            .build();

        return ResponseEntity.ok(responseDTO);
    }

    // 책에 대한 리뷰 조회 API
    @GetMapping("/book/{bookId}")
    public ResponseEntity<List<ReviewDTO>> getReviewsByBook(@PathVariable Long bookId) {
        List<Review> reviews = reviewService.findByBookId(bookId);
        List<ReviewDTO> reviewDTOs = reviews.stream().map(review -> 
            ReviewDTO.builder()
                .reviewId(review.getReviewId())
                .name(review.getUser().getName()) // 작성자 이름 설정
                .creationDate(formatDate(review.getCreationDate())) // 작성일자 형식 변환
                .rating(review.getRating())
                .content(review.getContent())
                .bookId(review.getBook().getBookId())
                .build()
        ).collect(Collectors.toList());
        return ResponseEntity.ok(reviewDTOs);
    }

    // 날짜를 "yyyy-MM-dd" 형식으로 포맷팅하는 메소드
    private String formatDate(Date date) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        return formatter.format(date);
    }
}