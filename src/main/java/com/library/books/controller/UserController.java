package com.library.books.controller;

import com.library.books.model.User;
import com.library.books.security.JwtTokenUtil;
import com.library.books.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Controller
public class UserController {

    private final UserService userService;
    private final JwtTokenUtil jwtTokenUtil;

    public UserController(UserService userService, JwtTokenUtil jwtTokenUtil) {
        this.userService = userService;
        this.jwtTokenUtil = jwtTokenUtil;
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

    @RequestMapping(value = "/authenticate", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
    @ResponseBody
    public ResponseEntity<Map<String, String>> authenticateUser(@RequestBody User user) {
        System.out.println("Authenticating user: " + user.getEmail());
        User authenticatedUser = userService.authenticateUser(user.getEmail(), user.getPassword());

        if (authenticatedUser == null) {
            System.out.println("Authentication failed for user: " + user.getEmail());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        System.out.println("Authenticated user: " + authenticatedUser.getEmail());
        String jwtToken = jwtTokenUtil.generateToken(authenticatedUser.getEmail());
        Map<String, String> response = new HashMap<>();
        response.put("token", jwtToken);
        return ResponseEntity.ok(response);
    }


    @PostMapping("/logout")
    public String logoutUser(HttpServletRequest request) {
        // JWT tokens don't need to be invalidated on the server side.
        // You can simply remove the token from the client's storage (e.g., cookies, localStorage, or sessionStorage).
        return "redirect:/login";
    }

    @GetMapping("/me")
    public ResponseEntity<Optional<User>> getAuthenticatedUser(@AuthenticationPrincipal UserDetails userDetails) {
        if (userDetails != null) {
            Optional<User> user = userService.getUserByEmail(userDetails.getUsername());
            return ResponseEntity.ok(user);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }
}




//package com.library.books.controller;
//
//import com.library.books.model.User;
//import com.library.books.security.JwtTokenUtil;
//import com.library.books.service.UserService;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.validation.BindingResult;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.ModelAttribute;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
//import org.springframework.web.bind.annotation.ResponseBody;
//
//import javax.validation.Valid;
//import java.util.HashMap;
//import java.util.Map;
//
//@Controller
//public class UserController {
//
//    private final UserService userService;
//    private final JwtTokenUtil jwtTokenUtil;
//
//    public UserController(UserService userService, JwtTokenUtil jwtTokenUtil) {
//        this.userService = userService;
//        this.jwtTokenUtil = jwtTokenUtil;
//    }
//
//    @GetMapping("/register")
//    public String showRegistrationForm(Model model) {
//        model.addAttribute("user", new User());
//        return "register";
//    }
//
//    @PostMapping("/register")
//    public String registerUser(@ModelAttribute("user") @Valid User user, BindingResult bindingResult, Model model) {
//
//        if (bindingResult.hasErrors()) {
//            return "register";
//        }
//
//        if (userService.userExists(user.getEmail())) {
//            model.addAttribute("userExistsError", "User with this email already exists");
//            return "register";
//        }
//
//        userService.saveUser(user);
//        return "redirect:/login";
//    }
//
//    @GetMapping("/login")
//    public String showLoginForm(Model model) {
//        model.addAttribute("user", new User());
//        return "login";
//    }
//
//    @RequestMapping(value = "/authenticate", method = RequestMethod.POST)
//    @ResponseBody
//    public ResponseEntity<Map<String, String>> authenticateUser(@ModelAttribute("user") User user) {
//        User authenticatedUser = userService.authenticateUser(user.getEmail(), user.getPassword());
//
//        if (authenticatedUser == null) {
//            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
//        }
//
//        String jwtToken = jwtTokenUtil.generateToken(authenticatedUser.getEmail());
//        Map<String, String> response = new HashMap<>();
//        response.put("token", jwtToken);
//        return ResponseEntity.ok(response);
//    }
//
//
//
//
//    @PostMapping("/logout")
//    public String logoutUser(HttpServletRequest request) {
//        // JWT tokens don't need to be invalidated on the server side.
//        // You can simply remove the token from the client's storage (e.g., cookies, localStorage, or sessionStorage).
//        return "redirect:/login";
//    }
//}




//package com.library.books.controller;
//
//import com.library.books.model.User;
//import com.library.books.service.UserService;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpSession;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.validation.BindingResult;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.ModelAttribute;
//import org.springframework.web.bind.annotation.PostMapping;
//
//import javax.validation.Valid;
//
//@Controller
//public class UserController {
//
//    private final UserService userService;
//
//    public UserController(UserService userService) {
//        this.userService = userService;
//    }
//
//    @GetMapping("/register")
//    public String showRegistrationForm(Model model) {
//        model.addAttribute("user", new User());
//        return "register";
//    }
//
//    @PostMapping("/register")
//    public String registerUser(@ModelAttribute("user") @Valid User user, BindingResult bindingResult, Model model) {
//
//        if (bindingResult.hasErrors()) {
//            return "register";
//        }
//
//        if (userService.userExists(user.getEmail())) {
//            model.addAttribute("userExistsError", "User with this email already exists");
//            return "register";
//        }
//
//        userService.saveUser(user);
//        return "redirect:/login";
//    }
//
//    @GetMapping("/login")
//    public String showLoginForm(Model model) {
//        model.addAttribute("user", new User());
//        return "login";
//    }
//
//    @PostMapping("/login")
//    public String loginUser(@ModelAttribute("user") User user, HttpServletRequest request, Model model) {
//        User authenticatedUser = userService.authenticateUser(user.getEmail(), user.getPassword());
//
//        if (authenticatedUser == null) {
//            model.addAttribute("loginError", "Invalid email or password");
//            return "login";
//        }
//
//        HttpSession session = request.getSession();
//        session.setAttribute("user", authenticatedUser);
//
//        return "redirect:/home";
//    }
//
//    @PostMapping("/logout")
//    public String logoutUser(HttpServletRequest request) {
//        HttpSession session = request.getSession(false);
//        if (session != null) {
//            session.invalidate();
//        }
//        return "redirect:/login";
//    }
//
//}
