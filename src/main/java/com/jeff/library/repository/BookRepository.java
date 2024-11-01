package com.jeff.library.repository;

import com.jeff.library.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface BookRepository extends JpaRepository<Book, Long> {

    Optional<Book> findBookByTitleContainsIgnoreCase(String title);

    @Query("SELECT b FROM Book b WHERE b.language = :language")
    List<Book> findByLanguage(List<String> language);
}
