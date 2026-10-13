package com.interviewai.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.interviewai.dto.AIRequest;
import com.interviewai.dto.ScoreRequest;
import com.interviewai.service.AIService;

@RestController
@RequestMapping("/api/ai")
public class AIController {

    private final AIService aiService;

    public AIController(AIService aiService) {
        this.aiService = aiService;
    }

    @GetMapping("/question")
    public String generateQuestion(@RequestParam String topic) {

        return aiService.generateQuestion(topic, 0);
    }

    @PostMapping("/answer")
    public String generateAnswer(@RequestBody AIRequest request) {

        return aiService.generateAnswer(
                request.getTopic(),
                request.getQuestion()
        );
    }

    @PostMapping("/score")
    public String scoreAnswer(@RequestBody ScoreRequest request) {

        return aiService.scoreAnswer(
                request.getQuestion(),
                request.getAnswer()
        );
    }
}