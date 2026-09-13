package com.exam.ui;

import com.exam.model.Result;
import com.exam.util.Constants;

import javax.swing.*;
import java.awt.*;

public class ResultPanel extends JPanel {
    private final MainFrame mainFrame;
    private final JLabel scoreLabel;
    private final JLabel summaryLabel;

    public ResultPanel(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
        this.scoreLabel = new JLabel("Score: 0/0");
        this.summaryLabel = new JLabel("No exam submitted yet.");
        initComponents();
    }

    private void initComponents() {
        setLayout(new GridBagLayout());
        setBackground(Constants.BACKGROUND_COLOR);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        JLabel title = new JLabel("Exam Result");
        title.setFont(Constants.TITLE_FONT);

        scoreLabel.setFont(Constants.LABEL_FONT);
        summaryLabel.setFont(Constants.LABEL_FONT);

        JButton backButton = new JButton("Back to Profile");
        backButton.addActionListener(e -> mainFrame.showProfile());

        gbc.gridx = 0;
        gbc.gridy = 0;
        add(title, gbc);

        gbc.gridy = 1;
        add(scoreLabel, gbc);

        gbc.gridy = 2;
        add(summaryLabel, gbc);

        gbc.gridy = 3;
        add(backButton, gbc);
    }

    public void setResult(Result result) {
        if (result == null) {
            scoreLabel.setText("Score: 0/0");
            summaryLabel.setText("No exam submitted yet.");
            return;
        }

        scoreLabel.setText("Score: " + result.getScore() + "/" + result.getTotalQuestions());
        double percentage = result.getTotalQuestions() == 0 ? 0
                : (result.getScore() * 100.0) / result.getTotalQuestions();
        summaryLabel.setText(String.format("Percentage: %.0f%% | Submitted at %s",
                percentage, result.getSubmittedAt().toLocalTime()));
    }
}
