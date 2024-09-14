package com.js.bookforum.service;

import java.util.List;

import com.js.bookforum.entity.Book;

public interface BookService {
    Book addBook(Book book);
    Book updateBook(Long bookId, Book book);
    Book updateBook(Book book);
    void deleteBook(Long bookId);
    Book getBookById(Long bookId);
    List<Book> getAllBooks();
    Book findById(Long BookId);
    List<Book> findBooksByTitleContaining(String title); // 제목에 검색어가 포함된 책 목록을 찾는 메서드
    
}