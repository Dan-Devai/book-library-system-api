package com.library.books.controller;

import com.library.books.model.Book;
import com.library.books.service.BookService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/books")
public class BookController {

    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    // TODO: Temporal Delete
//    @GetMapping("")
//    public List<Book> list() {
//        return bookRepository.findAll();
//    }

    @GetMapping("")
    public List<Book> getAllBooks() {
        return bookService.getAllBooks();
    }

//    // Bring all the records by id
//    @GetMapping("/{id}")
//    public ResponseEntity<Book> getBookByISBN(@PathVariable("id") int id){
//        Optional<Book> bookData = bookRepository.findById(id);
//
//        if (bookData.isPresent()){
//            return new ResponseEntity<>(bookData.get(), HttpStatus.OK);
//        } else {
//            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//        }
//    }

//    // TODO: Temporal Delete
//    // Bring all the records by ISBN
//    @GetMapping("/{isbn}")
//    public ResponseEntity<Book> getBookByISBN(@PathVariable("isbn") long isbn) {
//        Optional<Book> bookData = bookRepository.findByIsbn(isbn);
//
//        if (bookData.isPresent()) {
//            return new ResponseEntity<>(bookData.get(), HttpStatus.OK);
//        } else {
//            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//        }
//    }

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

//    // TODO: Temporal Delete
//    // Create new records
//    @PostMapping("")
//    public ResponseEntity<Book> createBook(@RequestBody Book book) {
//        try {
//            Book _book = bookRepository.save(
//                    new Book(0, book.getIsbn(), book.getTitle(),
//                            book.getAuthor(), book.getPublicationDate()));
////                    Book savedBook = bookRepository.save(book);
//            return new ResponseEntity<>(_book, HttpStatus.CREATED);
//        } catch (Exception e) {
//            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }

    @PostMapping("")
    public ResponseEntity<Book> createBook(@RequestBody Book book) {
        Book savedBook = bookService.createBook(book);
        if (savedBook != null) {
            return new ResponseEntity<>(savedBook, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

//    // TODO: Temporal Delete
//    @PutMapping("/{isbn}")
//    public ResponseEntity<Book> updateBook(@PathVariable("isbn") long isbn, @RequestBody Book book) {
//        Optional<Book> bookData = bookRepository.findByIsbn(isbn);
//
//        if (bookData.isPresent()) {
//            Book _book = bookData.get();
//            _book.setIsbn(book.getIsbn());
//            _book.setTitle(book.getTitle());
//            _book.setAuthor(book.getAuthor());
//            _book.setPublicationDate(book.getPublicationDate());
//
//            return new ResponseEntity<>(bookRepository.save(_book), HttpStatus.OK);
//        } else {
//            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//        }
//    }

    @PutMapping("/{isbn}")
    public ResponseEntity<Book> updateBook(@PathVariable("isbn") long isbn, @RequestBody Book book) {
        Book updatedBook = bookService.updateBook(isbn, book);
        if (updatedBook != null) {
            return new ResponseEntity<>(updatedBook, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

//    // TODO: Temporal Delete
//    @DeleteMapping("/{isbn}")
//    public ResponseEntity<HttpStatus> deleteBookByIsbn(@PathVariable("isbn") long isbn) {
//        Optional<Book> bookData = bookRepository.findByIsbn(isbn);
//
//        if (bookData.isPresent()) {
//            bookRepository.delete(bookData.get());
//            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
//        } else {
//            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//        }
//    }

    @DeleteMapping("/{isbn}")
    public ResponseEntity<HttpStatus> deleteBookByIsbn(@PathVariable("isbn") long isbn) {
        HttpStatus status = bookService.deleteBookByIsbn(isbn);
        return new ResponseEntity<>(status);
    }

}
