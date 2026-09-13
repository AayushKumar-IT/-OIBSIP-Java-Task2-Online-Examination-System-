package com.exam.data;

import com.exam.model.Question;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class QuestionBank {
    private final List<Question> questions = new ArrayList<>();

    public QuestionBank() {
        loadQuestions();
    }

    private void loadQuestions() {
        questions.add(new Question(1, "Which keyword is used to define a class in Java?",
                Arrays.asList("class", "interface", "struct", "object"), 0));

        questions.add(new Question(2, "Which of the following is used to store multiple values of same type?",
                Arrays.asList("Variable", "Array", "Method", "Loop"), 1));

        questions.add(new Question(3, "Which access modifier makes a member accessible only within the same class?",
                Arrays.asList("public", "private", "protected", "default"), 1));

        questions.add(new Question(4, "What is the entry point of a Java application?",
                Arrays.asList("main()", "start()", "run()", "init()"), 0));

        questions.add(new Question(5, "Which operator is used for equality comparison in Java?",
                Arrays.asList("=", "==", "===", "equals()"), 1));
    }

    public List<Question> getQuestions() {
        return questions;
    }
}
