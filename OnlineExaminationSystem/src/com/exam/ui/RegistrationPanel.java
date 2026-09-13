package com.exam.ui;

import com.exam.service.AuthService;
import com.exam.util.Constants;

import javax.swing.*;
import java.awt.*;

public class RegistrationPanel extends JPanel {
    private final MainFrame mainFrame;
    private final AuthService authService;

    public RegistrationPanel(MainFrame mainFrame, AuthService authService) {
        this.mainFrame = mainFrame;
        this.authService = authService;
        initComponents();
    }

    private void initComponents() {
        setLayout(new GridBagLayout());
        setBackground(Constants.BACKGROUND_COLOR);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        JLabel title = new JLabel("Register");
        title.setFont(Constants.TITLE_FONT);

        JTextField fullNameField = new JTextField(20);
        JTextField usernameField = new JTextField(20);
        JTextField emailField = new JTextField(20);
        JPasswordField passwordField = new JPasswordField(20);
        JPasswordField confirmPasswordField = new JPasswordField(20);

        JButton registerButton = new JButton("Create Account");
        registerButton.setBackground(Constants.PRIMARY_COLOR);
        registerButton.setForeground(Color.WHITE);
        registerButton.setFont(Constants.BUTTON_FONT);

        JButton backButton = new JButton("Back to Login");
        backButton.setFont(Constants.BUTTON_FONT);

        registerButton.addActionListener(e -> {
            String fullName = fullNameField.getText();
            String username = usernameField.getText();
            String email = emailField.getText();
            String password = new String(passwordField.getPassword());
            String confirmPassword = new String(confirmPasswordField.getPassword());

            if (fullName.trim().isEmpty() || username.trim().isEmpty() || email.trim().isEmpty()
                    || password.isEmpty() || confirmPassword.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please fill in all fields.");
                return;
            }

            if (!password.equals(confirmPassword)) {
                JOptionPane.showMessageDialog(this, "Passwords do not match.");
                return;
            }

            boolean registered = authService.registerUser(fullName, username, email, password);
            if (registered) {
                JOptionPane.showMessageDialog(this, "Registration successful. Please log in.");
                fullNameField.setText("");
                usernameField.setText("");
                emailField.setText("");
                passwordField.setText("");
                confirmPasswordField.setText("");
                mainFrame.showLogin();
            } else {
                JOptionPane.showMessageDialog(this,
                        "Registration failed. User already exists or password is too short.");
            }
        });

        backButton.addActionListener(e -> mainFrame.showLogin());

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        add(title, gbc);

        gbc.gridwidth = 1;
        gbc.gridy = 1;
        gbc.gridx = 0;
        add(new JLabel("Full Name:"), gbc);
        gbc.gridx = 1;
        add(fullNameField, gbc);

        gbc.gridy = 2;
        gbc.gridx = 0;
        add(new JLabel("Username:"), gbc);
        gbc.gridx = 1;
        add(usernameField, gbc);

        gbc.gridy = 3;
        gbc.gridx = 0;
        add(new JLabel("Email:"), gbc);
        gbc.gridx = 1;
        add(emailField, gbc);

        gbc.gridy = 4;
        gbc.gridx = 0;
        add(new JLabel("Password:"), gbc);
        gbc.gridx = 1;
        add(passwordField, gbc);

        gbc.gridy = 5;
        gbc.gridx = 0;
        add(new JLabel("Confirm Password:"), gbc);
        gbc.gridx = 1;
        add(confirmPasswordField, gbc);

        gbc.gridy = 6;
        gbc.gridx = 0;
        gbc.gridwidth = 2;
        add(registerButton, gbc);

        gbc.gridy = 7;
        add(backButton, gbc);
    }
}
