package com.exam.ui;

import com.exam.model.User;
import com.exam.service.AuthService;
import com.exam.util.Constants;
import com.exam.util.SessionManager;

import javax.swing.*;
import java.awt.*;

public class ProfilePanel extends JPanel {

    private final MainFrame mainFrame;
    private final AuthService authService;

    private JLabel welcomeLabel;
    private JLabel fullNameValueLabel;
    private JLabel usernameValueLabel;
    private JLabel emailValueLabel;

    public ProfilePanel(MainFrame mainFrame, AuthService authService) {

        this.mainFrame = mainFrame;
        this.authService = authService;

        initComponents();

        refreshProfile();
    }

    private void initComponents() {

        setLayout(new BorderLayout());

        setBackground(Constants.BACKGROUND_COLOR);

        setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));

        // Main centered container
        JPanel mainPanel = new JPanel();

        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

        mainPanel.setOpaque(false);

        // Header
        JLabel titleLabel = new JLabel("Profile Dashboard");

        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 20));

        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        welcomeLabel = new JLabel();

        welcomeLabel.setFont(new Font("SansSerif", Font.BOLD, 16));

        welcomeLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel subtitleLabel =
                new JLabel("Your exam account is ready.");

        subtitleLabel.setFont(new Font("SansSerif", Font.PLAIN, 12));

        subtitleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        mainPanel.add(titleLabel);

        mainPanel.add(Box.createVerticalStrut(8));

        mainPanel.add(welcomeLabel);

        mainPanel.add(Box.createVerticalStrut(4));

        mainPanel.add(subtitleLabel);

        mainPanel.add(Box.createVerticalStrut(18));

        // Profile information card
        JPanel infoCard = new JPanel(new GridLayout(3, 2, 10, 8));

        infoCard.setBackground(Color.WHITE);

        infoCard.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(
                        new Color(220, 220, 220)
                ),
                BorderFactory.createEmptyBorder(
                        15, 20, 15, 20
                )
        ));

        infoCard.setPreferredSize(new Dimension(500, 145));

        infoCard.setMinimumSize(new Dimension(500, 145));

        infoCard.setMaximumSize(new Dimension(500, 145));

        infoCard.add(createInfoLabel("Full Name"));

        fullNameValueLabel = createValueLabel("User");

        infoCard.add(fullNameValueLabel);

        infoCard.add(createInfoLabel("Username"));

        usernameValueLabel = createValueLabel("guest");

        infoCard.add(usernameValueLabel);

        infoCard.add(createInfoLabel("Email"));

        emailValueLabel = createValueLabel("No email");

        infoCard.add(emailValueLabel);

        mainPanel.add(infoCard);

        mainPanel.add(Box.createVerticalStrut(18));

        // Buttons
        JPanel actionPanel = new JPanel(
                new GridLayout(1, 2, 15, 0)
        );

        actionPanel.setOpaque(false);

        actionPanel.setPreferredSize(new Dimension(500, 60));

        actionPanel.setMinimumSize(new Dimension(500, 60));

        actionPanel.setMaximumSize(new Dimension(500, 60));

        JButton takeTestButton = new JButton("Take Test");

        takeTestButton.setBackground(Constants.PRIMARY_COLOR);

        takeTestButton.setForeground(Color.WHITE);

        takeTestButton.setFont(new Font(
                "SansSerif",
                Font.BOLD,
                14
        ));

        takeTestButton.setFocusPainted(false);

        JButton logoutButton = new JButton("Logout");

        logoutButton.setFont(new Font(
                "SansSerif",
                Font.BOLD,
                14
        ));

        logoutButton.setFocusPainted(false);

        takeTestButton.addActionListener(
                e -> mainFrame.showExam()
        );

        logoutButton.addActionListener(e -> {

            SessionManager.logout();

            mainFrame.showLogin();

        });

        actionPanel.add(takeTestButton);

        actionPanel.add(logoutButton);

        mainPanel.add(actionPanel);

        // Center the main panel
        JPanel centerPanel = new JPanel(new GridBagLayout());

        centerPanel.setOpaque(false);

        centerPanel.add(mainPanel);

        add(centerPanel, BorderLayout.CENTER);
    }

    public void refreshProfile() {

        User currentUser = SessionManager.getCurrentUser();

        String fullName =
                currentUser != null
                        && currentUser.getFullName() != null
                        && !currentUser.getFullName().isBlank()
                        ? currentUser.getFullName()
                        : "User";

        String username =
                currentUser != null
                        && currentUser.getUsername() != null
                        && !currentUser.getUsername().isBlank()
                        ? currentUser.getUsername()
                        : "guest";

        String email =
                currentUser != null
                        && currentUser.getEmail() != null
                        && !currentUser.getEmail().isBlank()
                        ? currentUser.getEmail()
                        : "No email";

        welcomeLabel.setText("Welcome, " + fullName + "!");

        fullNameValueLabel.setText(fullName);

        usernameValueLabel.setText(username);

        emailValueLabel.setText(email);
    }

    private JLabel createInfoLabel(String text) {

        JLabel label = new JLabel(text + ":");

        label.setFont(new Font(
                "SansSerif",
                Font.PLAIN,
                13
        ));

        label.setForeground(new Color(100, 100, 100));

        return label;
    }

    private JLabel createValueLabel(String text) {

        JLabel label = new JLabel(text);

        label.setFont(new Font(
                "SansSerif",
                Font.BOLD,
                14
        ));

        label.setForeground(new Color(30, 30, 30));

        return label;
    }
}