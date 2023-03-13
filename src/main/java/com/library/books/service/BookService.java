package com.library.books.service;

import com.library.books.model.Book;
import com.library.books.repository.BookRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookService {

    private final BookRepository bookRepository;

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public List<Book> getAllBooks(){
        return bookRepository.findAll();
    }

    public Optional<Book> getBookByIsbn(long isbn) {
        return bookRepository.findByIsbn(isbn);
    }

    public Book createBook(Book book) {
        return bookRepository.save(new Book(0, book.getIsbn(), book.getTitle(),
                book.getAuthor(), book.getPublicationDate()));
    }

    public Book updateBook(long isbn, Book book) {
        Optional<Book> bookData = bookRepository.findByIsbn(isbn);
        if (bookData.isPresent()) {
            Book _book = bookData.get();
            _book.setIsbn(book.getIsbn());
            _book.setTitle(book.getTitle());
            _book.setAuthor(book.getAuthor());
            _book.setPublicationDate(book.getPublicationDate());
            return bookRepository.save(_book);
        }
        return null;
    }

    public HttpStatus deleteBookByIsbn(long isbn) {
        Optional<Book> bookData = bookRepository.findByIsbn(isbn);
        if (bookData.isPresent()) {
            bookRepository.delete(bookData.get());
            return HttpStatus.NO_CONTENT;
        } else {
            return HttpStatus.NOT_FOUND;
        }
    }



}
