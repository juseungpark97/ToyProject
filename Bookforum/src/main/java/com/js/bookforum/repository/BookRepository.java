package com.js.bookforum.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.js.bookforum.entity.Book;

public interface BookRepository extends JpaRepository<Book, Long> {
    
}