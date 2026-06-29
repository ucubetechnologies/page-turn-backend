package com.pageturn.controller;

import com.pageturn.dto.BookRequest;
import com.pageturn.entity.Book;
import com.pageturn.service.BookService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/books")
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;

    @GetMapping
    public ResponseEntity<List<Book>> getAllBooks(
            @RequestParam(required = false) String language,
            @RequestParam(required = false) String genre,
            @RequestParam(required = false) Double minRating,
            @RequestParam(required = false) String search) {
        return ResponseEntity.ok(bookService.getAllBooks(language, genre, minRating, search));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Book> getBook(@PathVariable Long id) {
        return ResponseEntity.ok(bookService.getBookById(id));
    }

    @GetMapping("/featured")
    public ResponseEntity<List<Book>> getFeatured() {
        return ResponseEntity.ok(bookService.getFeaturedBooks());
    }

    @GetMapping("/top-rated")
    public ResponseEntity<List<Book>> getTopRated() {
        return ResponseEntity.ok(bookService.getTopRatedBooks());
    }

    @GetMapping("/by-language/{language}")
    public ResponseEntity<List<Book>> getByLanguage(@PathVariable String language) {
        return ResponseEntity.ok(bookService.getBooksByLanguage(language));
    }

    @GetMapping("/related/{bookId}")
    public ResponseEntity<List<Book>> getRelated(@PathVariable Long bookId) {
        return ResponseEntity.ok(bookService.getRelatedBooks(bookId));
    }

    @PostMapping
    public ResponseEntity<Book> createBook(@Valid @RequestBody BookRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(bookService.createBook(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Book> updateBook(@PathVariable Long id, @Valid @RequestBody BookRequest request) {
        return ResponseEntity.ok(bookService.updateBook(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable Long id) {
        bookService.deleteBook(id);
        return ResponseEntity.noContent().build();
    }
}
