
package com.interviewai.controller;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.interviewai.dto.AnswerRequest;
import com.interviewai.entity.Answer;
import com.interviewai.entity.Question;
import com.interviewai.repository.AnswerRepository;
import com.interviewai.repository.QuestionRepository;
import com.interviewai.service.AIService;

@RestController
@RequestMapping("/api/answers")
public class AnswerController {

    private final AnswerRepository answerRepository;
    private final QuestionRepository questionRepository;
    private final AIService aiService;

    public AnswerController(
            AnswerRepository answerRepository,
            QuestionRepository questionRepository,
            AIService aiService) {

        this.answerRepository = answerRepository;
        this.questionRepository = questionRepository;
        this.aiService = aiService;
    }

    @PostMapping
    public Answer submitAnswer(@RequestBody AnswerRequest request) {

        Question question = questionRepository.findById(request.getQuestionId())
                .orElseThrow(() -> new RuntimeException("Question not found with id: " + request.getQuestionId()));

        String result = aiService.scoreAnswer(
                question.getQuestionText(),
                request.getAnswerText()
        );

        int score = extractScore(result);
        String feedback = extractFeedback(result);

        List<Answer> existing = answerRepository.findByQuestion(question);
        Answer answer;
        if (!existing.isEmpty()) {
            answer = existing.get(existing.size() - 1);
            answer.setAnswerText(request.getAnswerText());
        } else {
            answer = new Answer(
                    request.getAnswerText(),
                    question
            );
        }

        answer.setScore(score);
        answer.setFeedback(feedback);

        return answerRepository.save(answer);
    }

    @GetMapping
    public List<Answer> getAllAnswers(@RequestParam(required = false) Long interviewId) {
        if (interviewId != null) {
            return answerRepository.findByQuestion_Interview_Id(interviewId);
        }
        return answerRepository.findAll();
    }

    @GetMapping("/interview/{interviewId}")
    public List<Answer> getAnswersByInterview(@PathVariable Long interviewId) {
        return answerRepository.findByQuestion_Interview_Id(interviewId);
    }

    private int extractScore(String result) {
        if (result == null || result.trim().isEmpty()) {
            return 0;
        }

        try {
            Pattern pattern = Pattern.compile("(?i)score\\s*:\\s*(\\d+)(?:/10)?");
            Matcher matcher = pattern.matcher(result);
            if (matcher.find()) {
                int score = Integer.parseInt(matcher.group(1));
                return Math.max(0, Math.min(10, score));
            }

            if (result.contains(":") && result.contains("/10")) {
                int colonIdx = result.indexOf(":");
                int slashIdx = result.indexOf("/10", colonIdx);
                if (slashIdx > colonIdx) {
                    String scorePart = result.substring(colonIdx + 1, slashIdx).trim();
                    return Math.max(0, Math.min(10, Integer.parseInt(scorePart)));
                }
            }
        } catch (Exception e) {
            // fallback
        }

        return 6;
    }

    private String extractFeedback(String result) {
        if (result == null || result.trim().isEmpty()) {
            return "Good effort. Ensure you explain core concepts with concrete examples.";
        }

        int index = result.indexOf("Feedback:");
        if (index >= 0) {
            return result.substring(index + "Feedback:".length()).trim();
        }

        return result;
    }
}

