package com.library.books.controller;

import com.library.books.model.User;
import com.library.books.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

@Controller
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new User());
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(@ModelAttribute("user") @Valid User user, BindingResult bindingResult, Model model) {

        if (bindingResult.hasErrors()) {
            return "register";
        }

        if (userService.userExists(user.getEmail())) {
            model.addAttribute("userExistsError", "User with this email already exists");
            return "register";
        }

        userService.saveUser(user);
        return "redirect:/login";
    }

    @GetMapping("/login")
    public String showLoginForm(Model model) {
        model.addAttribute("user", new User());
        return "login";
    }

    @PostMapping("/login")
    public String loginUser(@ModelAttribute("user") User user, HttpServletRequest request, Model model) {
        User authenticatedUser = userService.authenticateUser(user.getEmail(), user.getPassword());

        if (authenticatedUser == null) {
            model.addAttribute("loginError", "Invalid email or password");
            return "login";
        }

        HttpSession session = request.getSession();
        session.setAttribute("user", authenticatedUser);

        return "redirect:/books";
    }

    @GetMapping("/logout")
    public String logoutUser(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        return "redirect:/books";
    }


}
