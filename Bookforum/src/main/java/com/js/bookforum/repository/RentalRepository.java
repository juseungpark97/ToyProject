package com.js.bookforum.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.js.bookforum.entity.Rental;

public interface RentalRepository extends JpaRepository<Rental, Long> {
    // 사용자 정의 쿼리 메서드를 추가할 수 있습니다.
}