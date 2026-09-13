package com.exam.ui;

import com.exam.data.QuestionBank;
import com.exam.model.Question;
import com.exam.model.Result;
import com.exam.service.ExamService;
import com.exam.util.Constants;
import com.exam.util.SessionManager;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ExamPanel extends JPanel {
    private final MainFrame mainFrame;
    private final ExamService examService;
    private final List<Question> questions;
    private final Map<Integer, Integer> selectedAnswers = new HashMap<>();
    private int currentIndex = 0;
    private final JLabel questionLabel;
    private final ButtonGroup buttonGroup;
    private final JPanel optionsPanel;
    private final JLabel timerLabel;
    private final JLabel progressLabel;
    private final JLabel statusLabel;
    private final JProgressBar progressBar;
    private final javax.swing.Timer timer;
    private int remainingSeconds;

    public ExamPanel(MainFrame mainFrame, ExamService examService) {
        this.mainFrame = mainFrame;
        this.examService = examService;
        this.questions = new QuestionBank().getQuestions();
        this.remainingSeconds = Constants.EXAM_DURATION_SECONDS;

        setLayout(new BorderLayout(12, 12));
        setBackground(Constants.BACKGROUND_COLOR);

        questionLabel = new JLabel();
        questionLabel.setFont(new Font("SansSerif", Font.BOLD, 18));
        questionLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        optionsPanel = new JPanel();
        optionsPanel.setLayout(new BoxLayout(optionsPanel, BoxLayout.Y_AXIS));
        optionsPanel.setBackground(Constants.BACKGROUND_COLOR);
        buttonGroup = new ButtonGroup();

        timerLabel = new JLabel("Time Left: 05:00", SwingConstants.CENTER);
        timerLabel.setFont(new Font("SansSerif", Font.BOLD, 20));
        timerLabel.setForeground(Constants.PRIMARY_COLOR);

        progressLabel = new JLabel("Question 1 of " + questions.size());
        progressLabel.setFont(new Font("SansSerif", Font.PLAIN, 14));

        statusLabel = new JLabel("Ready to begin");
        statusLabel.setFont(new Font("SansSerif", Font.ITALIC, 13));
        statusLabel.setForeground(new Color(0, 128, 0));

        progressBar = new JProgressBar(0, questions.size());
        progressBar.setValue(1);
        progressBar.setStringPainted(true);

        JPanel topPanel = new JPanel(new BorderLayout(10, 10));
        topPanel.setBackground(Constants.BACKGROUND_COLOR);
        topPanel.add(timerLabel, BorderLayout.NORTH);

        JPanel infoPanel = new JPanel(new GridLayout(1, 2, 10, 10));
        infoPanel.setBackground(Constants.BACKGROUND_COLOR);
        infoPanel.add(progressLabel);
        infoPanel.add(statusLabel);
        topPanel.add(infoPanel, BorderLayout.SOUTH);

        JPanel centerPanel = new JPanel(new BorderLayout(10, 10));
        centerPanel.setBackground(Constants.BACKGROUND_COLOR);
        centerPanel.add(questionLabel, BorderLayout.NORTH);
        centerPanel.add(optionsPanel, BorderLayout.CENTER);

        JButton previousButton = new JButton("Previous");
        JButton nextButton = new JButton("Next");
        JButton submitButton = new JButton("Submit Exam");
        JButton clearButton = new JButton("Clear Selection");

        previousButton.addActionListener(e -> previousQuestion());
        nextButton.addActionListener(e -> nextQuestion());
        submitButton.addActionListener(e -> submitExam());
        clearButton.addActionListener(e -> clearCurrentSelection());

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        buttonPanel.setBackground(Constants.BACKGROUND_COLOR);
        buttonPanel.add(previousButton);
        buttonPanel.add(nextButton);
        buttonPanel.add(clearButton);
        buttonPanel.add(submitButton);

        JPanel bottomPanel = new JPanel(new BorderLayout(10, 10));
        bottomPanel.setBackground(Constants.BACKGROUND_COLOR);
        bottomPanel.add(progressBar, BorderLayout.NORTH);
        bottomPanel.add(buttonPanel, BorderLayout.SOUTH);

        add(topPanel, BorderLayout.NORTH);
        add(centerPanel, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);

        timer = new javax.swing.Timer(1000, e -> {
            remainingSeconds--;
            if (remainingSeconds <= 0) {
                ((javax.swing.Timer) e.getSource()).stop();
                submitExam();
                return;
            }
            updateTimerLabel();
        });

        timer.start();
        loadQuestion();
    }

    private void updateTimerLabel() {
        int minutes = remainingSeconds / 60;
        int seconds = remainingSeconds % 60;
        timerLabel.setText(String.format("Time Left: %02d:%02d", minutes, seconds));
    }

    private void updateProgress() {
        int answeredCount = 0;
        for (Question question : questions) {
            if (selectedAnswers.containsKey(question.getId())) {
                answeredCount++;
            }
        }

        progressLabel.setText("Question " + (currentIndex + 1) + " of " + questions.size());
        progressBar.setValue(answeredCount);
        statusLabel.setText("Answered: " + answeredCount + " / " + questions.size());
    }

    private void loadQuestion() {
        if (questions.isEmpty()) {
            return;
        }

        Question question = questions.get(currentIndex);
        questionLabel.setText((currentIndex + 1) + ". " + question.getText());
        optionsPanel.removeAll();
        buttonGroup.clearSelection();

        for (int i = 0; i < question.getOptions().size(); i++) {
            String optionText = question.getOptions().get(i);
            JRadioButton radio = new JRadioButton(optionText);
            radio.setActionCommand(String.valueOf(i));
            radio.setFont(new Font("SansSerif", Font.PLAIN, 15));
            if (selectedAnswers.containsKey(question.getId()) && selectedAnswers.get(question.getId()) == i) {
                radio.setSelected(true);
            }
            radio.addActionListener(e -> {
                selectedAnswers.put(question.getId(), Integer.parseInt(radio.getActionCommand()));
                updateProgress();
            });
            buttonGroup.add(radio);
            optionsPanel.add(radio);
        }

        optionsPanel.revalidate();
        optionsPanel.repaint();
        updateTimerLabel();
        updateProgress();
    }

    private void previousQuestion() {
        if (currentIndex > 0) {
            currentIndex--;
            loadQuestion();
        }
    }

    private void nextQuestion() {
        if (currentIndex < questions.size() - 1) {
            currentIndex++;
            loadQuestion();
        }
    }

    private void clearCurrentSelection() {
        Question question = questions.get(currentIndex);
        selectedAnswers.remove(question.getId());
        loadQuestion();
    }

    private void submitExam() {
        if (!timer.isRunning()) {
            timer.start();
        }

        Result result = examService.submitExam(
                SessionManager.getCurrentUser() != null ? SessionManager.getCurrentUser().getId() : 0, selectedAnswers);
        timer.stop();
        JOptionPane.showMessageDialog(this, "Your score: " + result.getScore() + "/" + result.getTotalQuestions());
        mainFrame.showResult(result);
    }
}
