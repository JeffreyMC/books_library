package com.jeff.library.repository;

import com.jeff.library.model.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface AuthorRepository extends JpaRepository<Author, Long> {
    Optional<Author> findIdByName(String name);

    @Query("SELECT a FROM Author a WHERE birthYear < :year AND deathYear > :year")
    List<Author> findALiveAuthorsByYear(String year);
}
