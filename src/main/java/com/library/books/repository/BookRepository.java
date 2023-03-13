package com.library.books.repository;

import com.library.books.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BookRepository extends JpaRepository<Book, Integer> {

    //The Optional book might be or not in the database
    Optional<Book> findByIsbn(long isbn);

    void deleteByIsbn(long isbn);

}
