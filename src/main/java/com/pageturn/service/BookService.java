package com.pageturn.service;

import com.pageturn.dto.BookRequest;
import com.pageturn.entity.Book;
import com.pageturn.exception.ResourceNotFoundException;
import com.pageturn.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookService {

    private final BookRepository bookRepository;

    public List<Book> getAllBooks(String language, String genre, Double minRating, String search) {
        List<Book> books = bookRepository.findAll();

        if (language != null && !language.isBlank())
            books = books.stream().filter(b -> language.equalsIgnoreCase(b.getLanguage())).toList();
        if (genre != null && !genre.isBlank())
            books = books.stream().filter(b -> genre.equalsIgnoreCase(b.getGenre())).toList();
        if (minRating != null)
            books = books.stream().filter(b -> b.getRating() != null && b.getRating() >= minRating).toList();
        if (search != null && !search.isBlank()) {
            String q = search.toLowerCase();
            books = books.stream().filter(b ->
                    b.getTitle().toLowerCase().contains(q) ||
                    b.getAuthor().toLowerCase().contains(q)).toList();
        }
        return books;
    }

    public Book getBookById(Long id) {
        return bookRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Book not found with id: " + id));
    }

    public List<Book> getFeaturedBooks() {
        return bookRepository.findByIsFeaturedTrueOrIsBestsellerTrue();
    }

    public List<Book> getTopRatedBooks() {
        return bookRepository.findTop10ByOrderByRatingDesc();
    }

    public List<Book> getBooksByLanguage(String language) {
        return bookRepository.findTop4ByLanguageOrderByRatingDesc(language);
    }

    public List<Book> getRelatedBooks(Long bookId) {
        Book book = getBookById(bookId);
        return bookRepository.findTop4ByLanguageOrGenreAndIdNotOrderByRatingDesc(
                book.getLanguage(), book.getGenre(), bookId);
    }

    public Book createBook(BookRequest request) {
        Book book = Book.builder()
                .title(request.getTitle())
                .author(request.getAuthor())
                .language(request.getLanguage())
                .genre(request.getGenre())
                .coverUrl(request.getCoverUrl())
                .description(request.getDescription())
                .publisher(request.getPublisher())
                .year(request.getYear())
                .pages(request.getPages())
                .isFeatured(request.getIsFeatured())
                .isBestseller(request.getIsBestseller())
                .rating(0.0)
                .reviewCount(0)
                .build();
        return bookRepository.save(book);
    }

    public Book updateBook(Long id, BookRequest request) {
        Book book = getBookById(id);
        book.setTitle(request.getTitle());
        book.setAuthor(request.getAuthor());
        book.setLanguage(request.getLanguage());
        book.setGenre(request.getGenre());
        book.setCoverUrl(request.getCoverUrl());
        book.setDescription(request.getDescription());
        book.setPublisher(request.getPublisher());
        book.setYear(request.getYear());
        book.setPages(request.getPages());
        book.setIsFeatured(request.getIsFeatured());
        book.setIsBestseller(request.getIsBestseller());
        return bookRepository.save(book);
    }

    public void deleteBook(Long id) {
        Book book = getBookById(id);
        bookRepository.delete(book);
    }

    public void updateBookRating(Long bookId) {
        Book book = getBookById(bookId);
        List<com.pageturn.entity.Review> approvedReviews = book.getReviews().stream()
                .filter(r -> r.getStatus() == com.pageturn.entity.Review.Status.APPROVED)
                .toList();
        if (!approvedReviews.isEmpty()) {
            double avg = approvedReviews.stream().mapToInt(com.pageturn.entity.Review::getRating).average().orElse(0);
            book.setRating(Math.round(avg * 10.0) / 10.0);
            book.setReviewCount(approvedReviews.size());
        } else {
            book.setRating(0.0);
            book.setReviewCount(0);
        }
        bookRepository.save(book);
    }
}
