package com.library.books.service;

import com.library.books.model.User;
import com.library.books.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

/*
    This UserService class injects a UserRepository instance via constructor injection and provides
    two methods for retrieving and saving users.
    The getUserByEmail method returns an Optional<User> object representing a user
    with the given email address, while the saveUser method saves a new or
    updated user to the database.
 */
@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Optional<User> getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public User saveUser(User user) {
        return userRepository.save(user);
    }

    public boolean userExists(String email) {
        return userRepository.existsByEmail(email);
    }

    public User authenticateUser(String email, String password) {
        Optional<User> userOptional = getUserByEmail(email);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            if (user.getPassword().equals(password)) {
                return user;
            }
        }
        return null;
    }


}