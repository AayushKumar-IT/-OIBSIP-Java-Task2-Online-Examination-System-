package com.exam.ui;

import com.exam.model.User;
import com.exam.service.AuthService;
import com.exam.util.Constants;
import com.exam.util.SessionManager;

import javax.swing.*;
import java.awt.*;

public class LoginPanel extends JPanel {
    private final MainFrame mainFrame;
    private final AuthService authService;

    public LoginPanel(MainFrame mainFrame, AuthService authService) {
        this.mainFrame = mainFrame;
        this.authService = authService;
        initComponents();
    }

    private void initComponents() {
        setLayout(new GridBagLayout());
        setBackground(Constants.BACKGROUND_COLOR);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        JLabel title = new JLabel("Login");
        title.setFont(Constants.TITLE_FONT);

        JLabel usernameLabel = new JLabel("Username:");
        usernameLabel.setFont(Constants.LABEL_FONT);
        JTextField usernameField = new JTextField(20);

        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setFont(Constants.LABEL_FONT);
        JPasswordField passwordField = new JPasswordField(20);

        JButton loginButton = new JButton("Login");
        loginButton.setBackground(Constants.PRIMARY_COLOR);
        loginButton.setForeground(Color.WHITE);
        loginButton.setFont(Constants.BUTTON_FONT);

        JButton registerButton = new JButton("Register");
        registerButton.setFont(Constants.BUTTON_FONT);

        JButton profileButton = new JButton("Open Profile");
        profileButton.setFont(Constants.BUTTON_FONT);

        loginButton.addActionListener(e -> {
            String username = usernameField.getText().trim();
            String password = new String(passwordField.getPassword());

            if (authService.login(username, password)) {
                User user = authService.getUserByUsername(username);
                SessionManager.login(user);
                JOptionPane.showMessageDialog(this, "Login successful!");
                usernameField.setText("");
                passwordField.setText("");
                mainFrame.showProfile();
            } else {
                JOptionPane.showMessageDialog(this, "Invalid username or password");
            }
        });

        registerButton.addActionListener(e -> mainFrame.showRegister());
        profileButton.addActionListener(e -> mainFrame.showProfile());

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        add(title, gbc);

        gbc.gridwidth = 1;
        gbc.gridx = 0;
        gbc.gridy = 1;
        add(usernameLabel, gbc);

        gbc.gridx = 1;
        add(usernameField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        add(passwordLabel, gbc);

        gbc.gridx = 1;
        add(passwordField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        add(loginButton, gbc);

        gbc.gridy = 4;
        add(registerButton, gbc);

        gbc.gridy = 5;
        add(profileButton, gbc);
    }
}
