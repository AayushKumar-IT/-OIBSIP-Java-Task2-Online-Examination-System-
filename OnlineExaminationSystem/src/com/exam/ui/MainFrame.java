package com.exam.ui;

import com.exam.data.QuestionBank;
import com.exam.data.UserDatabase;
import com.exam.model.Result;
import com.exam.service.AuthService;
import com.exam.service.ExamService;
import com.exam.util.SessionManager;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {

    private final CardLayout cardLayout;
    private final JPanel cardPanel;
    private final LoginPanel loginPanel;
    private final RegistrationPanel registrationPanel;
    private final ProfilePanel profilePanel;
    private final ExamPanel examPanel;
    private final ResultPanel resultPanel;
    private final UserDatabase userDatabase;
    private final AuthService authService;

    public MainFrame() {

        this.userDatabase = new UserDatabase();
        this.authService = new AuthService(userDatabase);

        QuestionBank questionBank = new QuestionBank();
        ExamService examService =
                new ExamService(questionBank.getQuestions());

        setTitle("Online Examination System");

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Compact window size
        setSize(700, 500);

        setMinimumSize(new Dimension(700, 500));

        setLocationRelativeTo(null);

        setResizable(false);

        cardLayout = new CardLayout();

        cardPanel = new JPanel(cardLayout);

        loginPanel = new LoginPanel(this, authService);

        registrationPanel = new RegistrationPanel(this, authService);

        profilePanel = new ProfilePanel(this, authService);

        examPanel = new ExamPanel(this, examService);

        resultPanel = new ResultPanel(this);

        cardPanel.add(loginPanel, "LOGIN");
        cardPanel.add(registrationPanel, "REGISTER");
        cardPanel.add(profilePanel, "PROFILE");
        cardPanel.add(examPanel, "EXAM");
        cardPanel.add(resultPanel, "RESULT");

        setContentPane(cardPanel);

        showLogin();

        setVisible(true);
    }

    public void showLogin() {
        cardLayout.show(cardPanel, "LOGIN");
    }

    public void showRegister() {
        cardLayout.show(cardPanel, "REGISTER");
    }

    public void showProfile() {

        if (SessionManager.isLoggedIn()) {

            profilePanel.refreshProfile();

            cardLayout.show(cardPanel, "PROFILE");

        } else {

            showLogin();

        }
    }

    public void showExam() {

        if (SessionManager.isLoggedIn()) {

            cardLayout.show(cardPanel, "EXAM");

        } else {

            showLogin();

        }
    }

    public void showResult(Result result) {

        resultPanel.setResult(result);

        cardLayout.show(cardPanel, "RESULT");
    }

    public void showResult() {

        cardLayout.show(cardPanel, "RESULT");
    }

    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> new MainFrame());

    }
}