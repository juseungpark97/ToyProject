package com.js.bookforum.service;

import com.js.bookforum.entity.Book;

import java.util.List;

public interface BookService {
    Book addBook(Book book);
    Book updateBook(Long bookId, Book book);
    Book updateBook(Book book);
    void deleteBook(Long bookId);
    Book getBookById(Long bookId);
    List<Book> getAllBooks();
    Book findById(Long BookId);
}