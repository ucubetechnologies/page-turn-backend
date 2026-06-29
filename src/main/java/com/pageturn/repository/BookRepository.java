package com.pageturn.repository;

import com.pageturn.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Long>, JpaSpecificationExecutor<Book> {

    List<Book> findByIsFeaturedTrueOrIsBestsellerTrue();

    List<Book> findTop10ByOrderByRatingDesc();

    List<Book> findTop4ByLanguageOrderByRatingDesc(String language);

    List<Book> findTop4ByLanguageOrGenreAndIdNotOrderByRatingDesc(
            String language, String genre, Long id);
}
