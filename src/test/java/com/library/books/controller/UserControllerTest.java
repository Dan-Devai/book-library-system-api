package com.library.books.controller;

import com.library.books.model.User;
import com.library.books.security.JwtTokenUtil;
import com.library.books.service.UserService;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;



@ExtendWith(MockitoExtension.class)
class UserControllerTest {

    @Mock
    private UserService userService;

    @Mock
    private JwtTokenUtil jwtTokenUtil;

    @InjectMocks
    private UserController userController;

    private User user;
    private HttpServletResponse response;
    private Model model;

    @BeforeEach
    void setUp() {
        user = new User(1L, "test@example.com", "password", "Test User");
        response = mock(HttpServletResponse.class);
        model = mock(Model.class);
    }

    @AfterEach
    void tearDown() {
        reset(userService, jwtTokenUtil, response, model);
    }

    @Test
    @DisplayName("Test registerUser with valid input")
    void registerUser_withValidInput() {
        when(userService.userExists(user.getEmail())).thenReturn(false);

        String viewName = userController.registerUser(user, mock(BindingResult.class), model);

        assertEquals("redirect:/login", viewName);
        verify(userService).saveUser(user);
    }

    @Test
    @DisplayName("Test registerUser with invalid input")
    void registerUser_withInvalidInput() {
        BindingResult bindingResult = mock(BindingResult.class);
        when(bindingResult.hasErrors()).thenReturn(true);

        String viewName = userController.registerUser(user, bindingResult, model);

        assertEquals("register", viewName);
        assertFalse(model.containsAttribute("userExistsError"));
        verifyNoInteractions(userService);
    }

    @Test
    @DisplayName("Test emailExists with existing email")
    void emailExistsWithExistingEmail() {
        String existingEmail = "test@example.com";

        // Mock userExists to return true
        when(userService.userExists(existingEmail)).thenReturn(true);

        boolean emailExists = userController.emailExists(existingEmail);

        assertTrue(emailExists);
    }

    @Test
    @DisplayName("Test emailExists with non-existing email")
    void emailExistsWithNonExistingEmail() {
        String nonExistingEmail = "nonexisting@example.com";

        // Mock userExists to return false
        when(userService.userExists(nonExistingEmail)).thenReturn(false);

        boolean emailExists = userController.emailExists(nonExistingEmail);

        assertFalse(emailExists);
    }



}