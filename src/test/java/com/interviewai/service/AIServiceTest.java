package com.interviewai.service;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class AIServiceTest {

    private AIService aiService;

    @BeforeEach
    void setUp() {
        aiService = new AIService();
    }

    @Test
    void testGenerateQuestionForJava() {
        String question = aiService.generateQuestion("Java Full-Stack", 0);
        assertNotNull(question);
        assertTrue(question.length() > 5);
    }

    @Test
    void testGenerateQuestionForPython() {
        String question = aiService.generateQuestion("Python Backend", 1);
        assertNotNull(question);
        assertTrue(question.toLowerCase().contains("python") || question.toLowerCase().contains("list"));
    }

    @Test
    void testGenerateAnswer() {
        String answer = aiService.generateAnswer("Python", "What are the main features of Python? Explain with examples.");
        assertNotNull(answer);
        assertTrue(answer.contains("Python is a high-level"));
    }

    @Test
    void testScoreAnswerWithValidExplanation() {
        String question = "What is the difference between a list and a tuple in Python?";
        String answer = "A list is mutable so its elements can be changed, while a tuple is immutable and cannot be changed. Lists use square brackets [] and tuples use parentheses (). For example numbers = [1, 2, 3].";

        String result = aiService.scoreAnswer(question, answer);
        assertNotNull(result);
        assertTrue(result.contains("Score:"));
        assertTrue(result.contains("Feedback:"));
    }

    @Test
    void testScoreEmptyAnswer() {
        String result = aiService.scoreAnswer("Any question", "");
        assertTrue(result.contains("Score: 0/10"));
    }
}
