package com.js.bookforum.service;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.js.bookforum.entity.Book;
import com.js.bookforum.entity.Rental;
import com.js.bookforum.entity.User;
import com.js.bookforum.repository.BookRepository;
import com.js.bookforum.repository.RentalRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class RentalServiceImpl implements RentalService {


	final private RentalRepository rentalRepository;
	final private BookRepository bookRepository;

    @Override
    public Rental saveRental(Rental rental) {
        return rentalRepository.save(rental);
    }

	@Override
	public Rental findByUserAndBook(User user, Long bookId) {
		Book book = bookRepository.findById(bookId)
			    .orElseThrow(() -> new IllegalArgumentException("해당 ID의 책을 찾을 수 없습니다: " + bookId));

	    return rentalRepository.findByUserAndBookAndReturnDateIsNull(user, book)
	            .orElse(null);	
	}
	@Override
    public void returnBook(User user, Long bookId) {
		Book book = bookRepository.findById(bookId)
			    .orElseThrow(() -> new IllegalArgumentException("해당 ID의 책을 찾을 수 없습니다: " + bookId));
        Rental rental = findByUserAndBook(user, bookId);
        if (rental == null) {
            throw new IllegalArgumentException("반납할 대여 기록이 없습니다.");
        }
        
        // 현재 시간으로 반납 날짜 설정
        rental.setReturnDate(new Date());
        rentalRepository.save(rental);

        // 책 재고 증가
        
        book.setStockQuantity(book.getStockQuantity() + 1);
        bookRepository.save(book);
    }
	
    
    // 특정 사용자 ID로 대여 기록 페이징 조회
    public Page<Rental> findRentalsByUserId(Long userId, Pageable pageable) {
        return rentalRepository.findByUserUserId(userId, pageable);
    }
    
    
}