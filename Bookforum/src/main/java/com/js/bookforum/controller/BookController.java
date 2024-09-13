package com.js.bookforum.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.js.bookforum.entity.Book;
import com.js.bookforum.entity.Category;
import com.js.bookforum.repository.CategoryRepository;
import com.js.bookforum.service.BookService;
import com.js.bookforum.service.S3Service;

@RestController
@RequestMapping("/api/books")
public class BookController {

    @Autowired
    private BookService bookService;

    @Autowired
    private S3Service s3Service;
    
    @Autowired
    private CategoryRepository categoryRepository; // CategoryRepository 주입

    @PostMapping("/add")
    public ResponseEntity<Book> addBook(@RequestParam("file") MultipartFile file,
                                        @RequestParam("title") String title,
                                        @RequestParam("author") String author,
                                        @RequestParam("publicationDate") String publicationDate,
                                        @RequestParam("stockQuantity") int stockQuantity,
                                        @RequestParam("categoryId") Long categoryId) {
        try {
            String imageUrl = s3Service.uploadFile(file);

            // 선택된 카테고리 ID로 카테고리를 조회
            Category category = categoryRepository.findById(categoryId)
                    .orElseThrow(() -> new RuntimeException("Category not found"));

            Book book = Book.builder()
                    .title(title)
                    .author(author)
                    .publicationDate(java.sql.Date.valueOf(publicationDate))
                    .stockQuantity(stockQuantity)
                    .imageUrl(imageUrl)
                    .category(category) // 조회된 카테고리 설정
                    .build();

            Book savedBook = bookService.addBook(book);
            return ResponseEntity.ok(savedBook);
        } catch (IOException e) {
            return ResponseEntity.status(500).body(null);
        }
    }

    @PutMapping("/{id}")
    public Book updateBook(@PathVariable Long id, @RequestBody Book book) {
        return bookService.updateBook(id, book);
    }

    @DeleteMapping("/{id}")
    public void deleteBook(@PathVariable Long id) {
        bookService.deleteBook(id);
    }

    @GetMapping("/{id}")
    public Book getBooById(@PathVariable Long id) {
        return bookService.getBookById(id);
    }

    @GetMapping
    public List<Book> getAllBooks() {
        return bookService.getAllBooks();
    }
}