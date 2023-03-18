package com.library.books.controller;

import com.library.books.model.Book;
import com.library.books.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Controller
public class WebController {

    @Autowired
    private BookService bookService;

    @GetMapping("/home")
    public String showHomePage(Model model) {
        return "home";
    }

    @GetMapping("/books")
    public String getAllBooks(Model model) {
        List<Book> books = bookService.getAllBooks();
        model.addAttribute("books", books);
        return "books";
    }

    @GetMapping("/books/search")
    public String searchBookByIsbn(@RequestParam("isbn") long isbn, Model model) {
        Optional<Book> bookData = bookService.getBookByIsbn(isbn);
        if (bookData.isPresent()) {
            model.addAttribute("book", bookData.get());
            return "searchbook";
        } else {
            model.addAttribute("searchedIsbn", isbn);
            return "book_not_found";
        }
    }

    @GetMapping("/books/new_book")
    public String showNewBookForm(Model model) {
        model.addAttribute("book", new Book());
        return "new_book";
    }

    @PostMapping("/books/new_book")
    public String saveNewBook(@ModelAttribute @Valid Book book, BindingResult bindingResult, Model model, RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "new_book";
        }
        bookService.createBook(book);
        redirectAttributes.addFlashAttribute("successMessage", "Book created successfully!");
        return "redirect:/books";
    }

    @PostMapping("/books/delete")
    public String deleteBookByIsbn(@RequestParam("isbn") long isbn, Model model) {
        HttpStatus status = bookService.deleteBookByIsbn(isbn);
        if (status == HttpStatus.NO_CONTENT) {
            model.addAttribute("deletedIsbn", isbn);
            return "delete_book";
        } else {
            model.addAttribute("searchedIsbn", isbn);
            return "book_not_found";
        }
    }

    @GetMapping("/books/update")
    public String showUpdateBookForm(@RequestParam("isbn") long isbn, Model model) {
        Optional<Book> bookData = bookService.getBookByIsbn(isbn);
        if (bookData.isPresent()) {
            model.addAttribute("book", bookData.get());
            return "update_book";
        } else {
            model.addAttribute("searchedIsbn", isbn);
            return "book_not_found";
        }
    }

    @PostMapping("/books/update")
    public String updateBook(@ModelAttribute("book") Book book, RedirectAttributes redirectAttributes) {
        Book updatedBook = bookService.updateBook(book.getIsbn(), book);
        if (updatedBook != null) {
            redirectAttributes.addFlashAttribute("message", "Book updated successfully");
            return "redirect:/books";
        } else {
            redirectAttributes.addFlashAttribute("errorMessage", "Error updating book");
            return "redirect:/books/update?isbn=" + book.getIsbn();
        }
    }

    @GetMapping("/access_denied")
    public String showAccessDeniedPage() {
        System.out.println("Access_Denied is being executed");
        return "access_denied";
    }

}
