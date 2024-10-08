package com.js.bookforum.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.js.bookforum.entity.Book;
import com.js.bookforum.repository.BookRepository;

@Service
public class BookServiceImpl implements BookService {

    @Autowired
    private BookRepository bookRepository;

    @Override
    public Book addBook(Book book) {
        return bookRepository.save(book);
    }

    @Override
    public Book updateBook(Long bookId, Book book) {
        Optional<Book> existingBook = bookRepository.findById(bookId);
        if (existingBook.isPresent()) {
            Book updatedBook = existingBook.get();
            updatedBook.setTitle(book.getTitle());
            updatedBook.setAuthor(book.getAuthor());
            updatedBook.setPublicationDate(book.getPublicationDate());
            updatedBook.setStockQuantity(book.getStockQuantity());
            updatedBook.setCategory(book.getCategory());
            return bookRepository.save(updatedBook);
        } else {
            throw new RuntimeException("Book not found");
        }
    }
    
    @Override
    public Book updateBook(Book book) {
        // 새로운 메서드 내용
        return bookRepository.save(book);
    }

    @Override
    public void deleteBook(Long bookId) {
        bookRepository.deleteById(bookId);
    }

    @Override
    public Book getBookById(Long bookId) {
        return bookRepository.findById(bookId).orElseThrow(() -> new RuntimeException("Book not found"));
    }

    @Override
    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }
    
    public Book findById(Long bookId) {
    	return bookRepository.findById(bookId)
    			.orElseThrow();
    }
    @Override
    public List<Book> findBooksByTitleContaining(String title) {
        return bookRepository.findByTitleContainingIgnoreCase(title); // 대소문자 무시하여 제목에 검색어가 포함된 책 찾기
    }
}