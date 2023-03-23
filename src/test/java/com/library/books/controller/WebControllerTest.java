package com.library.books.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.library.books.model.Book;
import com.library.books.service.BookService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(WebController.class)
public class WebControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private BookService bookService;

    private Book book;

    @BeforeEach
    public void setUp() {
        book = new Book(1, 1234567890123L, "Test Book", "Test Author", LocalDate.now());
    }

    @Test
    @WithMockUser
    public void testGetAllBooks() throws Exception {
        when(bookService.getAllBooks()).thenReturn(Arrays.asList(book));

        mockMvc.perform(get("/books"))
                .andExpect(status().isOk())
                .andExpect(view().name("books"))
                .andExpect(model().attributeExists("books"));
    }

    @Test
    @WithMockUser
    public void testSearchBookByIsbn() throws Exception {
        when(bookService.getBookByIsbn(book.getIsbn())).thenReturn(Optional.of(book));

        mockMvc.perform(get("/books/search").param("isbn", String.valueOf(book.getIsbn())))
                .andExpect(status().isOk())
                .andExpect(view().name("searchbook"))
                .andExpect(model().attributeExists("book"));
    }

    @Test
    @WithMockUser
    public void testRedirectToHomePage() throws Exception {
        mockMvc.perform(get("/"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/home"));
    }

    @Test
    @WithMockUser
    public void testShowHomePage() throws Exception {
        mockMvc.perform(get("/home"))
                .andExpect(status().isOk())
                .andExpect(view().name("home"));
    }

    @Test
    @WithMockUser
    public void testSearchBookByIsbnNotFound() throws Exception {
        when(bookService.getBookByIsbn(book.getIsbn())).thenReturn(Optional.empty());

        mockMvc.perform(get("/books/search").param("isbn", String.valueOf(book.getIsbn())))
                .andExpect(status().isOk())
                .andExpect(view().name("book_not_found"))
                .andExpect(model().attributeExists("searchedIsbn"));
    }

    @Test
    @WithMockUser
    public void testShowNewBookForm() throws Exception {
        mockMvc.perform(get("/books/new_book"))
                .andExpect(status().isOk())
                .andExpect(view().name("new_book"))
                .andExpect(model().attributeExists("book"));
    }

    @Test
    @WithMockUser
    public void testSaveNewBook() throws Exception {
        when(bookService.createBook(any(Book.class))).thenReturn(book);

        mockMvc.perform(post("/books/new_book")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(book)))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/books"))
                .andExpect(flash().attributeExists("successMessage"));
    }

    @Test
    @WithMockUser
    public void testDeleteBookByIsbn() throws Exception {
        when(bookService.deleteBookByIsbn(book.getIsbn())).thenReturn(HttpStatus.NO_CONTENT);

        mockMvc.perform(post("/books/delete").param("isbn", String.valueOf(book.getIsbn())))
                .andExpect(status().isOk())
                .andExpect(view().name("delete_book"));
    }

    @Test
    @WithMockUser
    public void testShowUpdateBookForm() throws Exception {
        when(bookService.getBookByIsbn(book.getIsbn())).thenReturn(Optional.of(book));

        mockMvc.perform(get("/books/update").param("isbn", String.valueOf(book.getIsbn())))
                .andExpect(status().isOk())
                .andExpect(view().name("update_book"))
                .andExpect(model().attributeExists("book"));
    }

    @Test
    @WithMockUser
    public void testUpdateBook() throws Exception {
        when(bookService.updateBook(book.getIsbn(), book)).thenReturn(book);

        mockMvc.perform(post("/books/update")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(book)))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/books"));
    }

    @Test
    @WithMockUser
    public void testShowAccessDeniedPage() throws Exception {
        mockMvc.perform(get("/access_denied"))
                .andExpect(status().isOk())
                .andExpect(view().name("access_denied"));
    }

}
