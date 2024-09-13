package com.js.bookforum.controller;

import java.util.Date;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.js.bookforum.dto.RentalDTO;
import com.js.bookforum.entity.Book;
import com.js.bookforum.entity.Rental;
import com.js.bookforum.entity.User;
import com.js.bookforum.security.CustomUserDetails;
import com.js.bookforum.service.BookService;
import com.js.bookforum.service.RentalService;
import com.js.bookforum.service.UserService;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/rentals")
@RequiredArgsConstructor
@Slf4j
public class RentalController {

    private final RentalService rentalService;
    private final BookService bookService;
    private final UserService userService;

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
    
    @PostMapping("/check")
    public ResponseEntity<Boolean> checkRentalStatus(@RequestBody Map<String, Long> requestBody, @AuthenticationPrincipal CustomUserDetails customUserDetails) {
        Long bookId = requestBody.get("bookId");
        User user = customUserDetails.getUser();
        if (bookId == null) {
            return ResponseEntity.badRequest().body(false);
        }
       
        
        Rental rental = rentalService.findByUserAndBook(user, bookId);
        boolean isRented = rental != null && rental.getReturnDate() == null;

        return ResponseEntity.ok(isRented);
    }
    
    @PostMapping("/return")
    public ResponseEntity<String> returnBook(@RequestBody Map<String, Long> requestBody, 
                                             @AuthenticationPrincipal CustomUserDetails customUserDetails) {
        Long bookId = requestBody.get("bookId");
        if (bookId == null) {
            return ResponseEntity.badRequest().body("bookId는 필수입니다.");
        }

        User user = customUserDetails.getUser();
        
        try {
            rentalService.returnBook(user, bookId);
            return ResponseEntity.ok("도서가 성공적으로 반납되었습니다.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @Operation(summary = "로그인한 사용자의 대여 기록 조회", description = "로그인한 사용자의 대여 기록을 페이징하여 조회합니다.")
    @GetMapping("/my")
    public Page<RentalDTO> getMyRentals(
        @AuthenticationPrincipal UserDetails userDetails,
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "5") int size) {

        // 현재 인증된 사용자 정보 가져오기
        User user = userService.findByEmail(userDetails.getUsername());
        PageRequest pageRequest = PageRequest.of(page, size);

        // 사용자 ID로 대여 기록 페이징 조회
        Page<Rental> rentals = rentalService.findRentalsByUserId(user.getUserId(), pageRequest);
        return rentals.map(rental -> new RentalDTO(
            rental.getRentalId(),
            rental.getBook().getBookId(),
            rental.getBook().getTitle(),
            rental.getBook().getAuthor(),
            rental.getRentalDate(),
            rental.getReturnDate(),
            rental.getUser().getName()
        ));
    }
    
 
    
    
}