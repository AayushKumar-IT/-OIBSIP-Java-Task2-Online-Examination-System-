package com.exam.data;

import com.exam.model.User;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserDatabase {
    private static final File DATA_DIRECTORY = new File("data");
    private static final File USERS_FILE = new File(DATA_DIRECTORY, "users.dat");

    private final Map<String, User> usersByUsername = new HashMap<>();
    private final List<User> users = new ArrayList<>();

    public UserDatabase() {
        loadUsers();
        if (users.isEmpty()) {
            seedDefaultUsers();
            persistUsers();
        }
    }

    private void seedDefaultUsers() {
        addUser(new User(1, "admin", "admin123", "Admin User", "admin@example.com"));
        addUser(new User(2, "student", "student123", "Student User", "student@example.com"));
    }

    private void loadUsers() {
        if (!USERS_FILE.exists()) {
            return;
        }

        try (ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(USERS_FILE))) {
            Object object = inputStream.readObject();
            if (object instanceof List<?>) {
                for (Object item : (List<?>) object) {
                    if (item instanceof User user) {
                        users.add(user);
                        usersByUsername.put(user.getUsername(), user);
                    }
                }
            }
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Unable to load users: " + e.getMessage());
        }
    }

    private void persistUsers() {
        try {
            if (!DATA_DIRECTORY.exists()) {
                DATA_DIRECTORY.mkdirs();
            }
            try (ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(USERS_FILE))) {
                outputStream.writeObject(new ArrayList<>(users));
            }
        } catch (IOException e) {
            System.err.println("Unable to save users: " + e.getMessage());
        }
    }

    public void addUser(User user) {
        if (user == null) {
            return;
        }

        if (usersByUsername.containsKey(user.getUsername())) {
            usersByUsername.put(user.getUsername(), user);
            for (int i = 0; i < users.size(); i++) {
                if (users.get(i).getUsername().equals(user.getUsername())) {
                    users.set(i, user);
                    break;
                }
            }
        } else {
            users.add(user);
            usersByUsername.put(user.getUsername(), user);
        }

        persistUsers();
    }

    public User findByUsername(String username) {
        return usersByUsername.get(username);
    }

    public boolean validateLogin(String username, String password) {
        User user = usersByUsername.get(username);
        return user != null && user.getPassword().equals(password);
    }

    public List<User> getAllUsers() {
        return new ArrayList<>(users);
    }
}
