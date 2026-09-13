package com.exam.model;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

public class Result {
    private int userId;
    private int score;
    private int totalQuestions;
    private LocalDateTime submittedAt;
    private Map<Integer, Integer> answerMap;

    public Result() {
        this.submittedAt = LocalDateTime.now();
        this.answerMap = new HashMap<>();
    }

    public Result(int userId, int score, int totalQuestions, Map<Integer, Integer> answerMap) {
        this.userId = userId;
        this.score = score;
        this.totalQuestions = totalQuestions;
        this.submittedAt = LocalDateTime.now();
        this.answerMap = answerMap;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getTotalQuestions() {
        return totalQuestions;
    }

    public void setTotalQuestions(int totalQuestions) {
        this.totalQuestions = totalQuestions;
    }

    public LocalDateTime getSubmittedAt() {
        return submittedAt;
    }

    public void setSubmittedAt(LocalDateTime submittedAt) {
        this.submittedAt = submittedAt;
    }

    public Map<Integer, Integer> getAnswerMap() {
        return answerMap;
    }

    public void setAnswerMap(Map<Integer, Integer> answerMap) {
        this.answerMap = answerMap;
    }
}
