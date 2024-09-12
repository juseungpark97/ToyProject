package com.js.bookforum.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.js.bookforum.entity.Book;
import com.js.bookforum.entity.Rental;
import com.js.bookforum.entity.User;

@Repository
public interface RentalRepository extends JpaRepository<Rental, Long> {
	Optional<Rental> findByUserAndBookAndReturnDateIsNull(User user, Book book);
    
 // 사용자 ID로 대여 기록 페이징 조회
    Page<Rental> findByUserUserId(Long userId, Pageable pageable); // 메서드 이름 수정
	
}