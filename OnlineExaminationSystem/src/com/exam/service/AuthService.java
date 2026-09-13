package com.exam.service;

import com.exam.data.UserDatabase;
import com.exam.model.User;

public class AuthService {
    private final UserDatabase userDatabase;

    public AuthService(UserDatabase userDatabase) {
        this.userDatabase = userDatabase;
    }

    public boolean login(String username, String password) {
        return userDatabase.validateLogin(username, password);
    }

    public User getUserByUsername(String username) {
        return userDatabase.findByUsername(username);
    }

    public boolean registerUser(String fullName, String username, String email, String password) {
        if (fullName == null || username == null || email == null || password == null) {
            return false;
        }

        String trimmedFullName = fullName.trim();
        String trimmedUsername = username.trim();
        String trimmedEmail = email.trim();
        String trimmedPassword = password.trim();

        if (trimmedFullName.isEmpty() || trimmedUsername.isEmpty() || trimmedEmail.isEmpty()
                || trimmedPassword.isEmpty()) {
            return false;
        }

        if (trimmedPassword.length() < 6) {
            return false;
        }

        if (userDatabase.findByUsername(trimmedUsername) != null) {
            return false;
        }

        int nextId = userDatabase.getAllUsers().stream()
                .mapToInt(User::getId)
                .max()
                .orElse(0) + 1;

        User newUser = new User(nextId, trimmedUsername, trimmedPassword, trimmedFullName, trimmedEmail);
        userDatabase.addUser(newUser);
        return true;
    }

    public boolean updateProfile(User user, String newFullName, String newEmail) {
        if (user == null) {
            return false;
        }
        user.setFullName(newFullName);
        user.setEmail(newEmail);
        return true;
    }
}
