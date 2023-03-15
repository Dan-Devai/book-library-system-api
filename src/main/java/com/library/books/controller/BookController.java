package com.library.books.controller;

import com.library.books.model.Book;
import com.library.books.service.BookService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;



/*

*********************************************************************************
*    This controller is used as a RestController.                               *
*    This controller handles API request and return JSON data.                  *
*********************************************************************************


 */

@RestController
@RequestMapping("/api/books")
public class BookController {

    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }



    @GetMapping("")
    public List<Book> getAllBooks() {
        return bookService.getAllBooks();
    }



    @GetMapping("/{isbn}")
    public ResponseEntity<Book> getBookByIsbn(@PathVariable("isbn") long isbn) {
        Optional<Book> bookData = bookService.getBookByIsbn(isbn);
//        return bookData.map(book -> new ResponseEntity<>(book, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));

        if (bookData.isPresent()) {
            return new ResponseEntity<>(bookData.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


    @PostMapping("")
    public ResponseEntity<Book> createBook(@RequestBody Book book) {
        Book savedBook = bookService.createBook(book);
        if (savedBook != null) {
            return new ResponseEntity<>(savedBook, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @PutMapping("/{isbn}")
    public ResponseEntity<Book> updateBook(@PathVariable("isbn") long isbn, @RequestBody Book book) {
        Book updatedBook = bookService.updateBook(isbn, book);
        if (updatedBook != null) {
            return new ResponseEntity<>(updatedBook, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


    @DeleteMapping("/{isbn}")
    public ResponseEntity<HttpStatus> deleteBookByIsbn(@PathVariable("isbn") long isbn) {
        HttpStatus status = bookService.deleteBookByIsbn(isbn);
        return new ResponseEntity<>(status);
    }

}
