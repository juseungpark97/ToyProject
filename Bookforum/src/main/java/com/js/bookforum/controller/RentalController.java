package com.js.bookforum.controller;

import java.util.Date;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.js.bookforum.entity.Book;
import com.js.bookforum.entity.Rental;
import com.js.bookforum.entity.User;
import com.js.bookforum.security.CustomUserDetails;
import com.js.bookforum.service.BookService;
import com.js.bookforum.service.RentalService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/rentals")
@RequiredArgsConstructor
@Slf4j
public class RentalController {

    private final RentalService rentalService;
    private final BookService bookService;

    @PostMapping("/rent")
    public ResponseEntity<String> rentBook(@RequestBody Map<String, Long> requestBody, 
                                           @AuthenticationPrincipal CustomUserDetails customUserDetails) {
        // CustomUserDetails로부터 User 엔티티를 가져옴
        User user = customUserDetails.getUser();

        Long bookId = requestBody.get("bookId");
        if (bookId == null) {
            return ResponseEntity.badRequest().body("bookId는 필수입니다.");
        }

        Book book = bookService.getBookById(bookId);
        if (book.getStockQuantity() <= 0) {
            return ResponseEntity.badRequest().body("재고가 부족합니다.");
        }

        // 대여 처리
        Rental rental = Rental.builder()
                .book(book)
                .user(user)
                .rentalDate(new Date())
                .build();

        rentalService.saveRental(rental);

        // 재고 감소
        book.setStockQuantity(book.getStockQuantity() - 1);
        bookService.updateBook(book);

        return ResponseEntity.ok("도서가 성공적으로 대여되었습니다.");
    }
}