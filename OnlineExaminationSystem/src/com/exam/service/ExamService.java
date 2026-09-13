package com.exam.service;

import com.exam.model.Question;
import com.exam.model.Result;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ExamService {
    private final List<Question> questions;

    public ExamService(List<Question> questions) {
        this.questions = questions;
    }

    public Result submitExam(int userId, Map<Integer, Integer> selectedAnswers) {
        int score = 0;
        for (Question question : questions) {
            Integer selected = selectedAnswers.get(question.getId());
            if (selected != null && selected == question.getCorrectOptionIndex()) {
                score++;
            }
        }

        Result result = new Result();
        result.setUserId(userId);
        result.setScore(score);
        result.setTotalQuestions(questions.size());
        result.setAnswerMap(selectedAnswers != null ? selectedAnswers : new HashMap<>());
        return result;
    }
}
