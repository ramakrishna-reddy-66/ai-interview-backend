package com.interviewai.controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.interviewai.dto.QuestionRequest;
import com.interviewai.entity.Interview;
import com.interviewai.entity.Question;
import com.interviewai.repository.InterviewRepository;
import com.interviewai.repository.QuestionRepository;

@RestController
@RequestMapping("/api/questions")
public class QuestionController {

    private final QuestionRepository questionRepository;
    private final InterviewRepository interviewRepository;

    public QuestionController(
            QuestionRepository questionRepository,
            InterviewRepository interviewRepository) {
        this.questionRepository = questionRepository;
        this.interviewRepository = interviewRepository;
    }

    @GetMapping
    public List<Question> getQuestions() {
        return questionRepository.findAll();
    }

    @PostMapping
    public Question createQuestion(@RequestBody QuestionRequest request) {

        Interview interview = interviewRepository.findById(request.getInterviewId())
                .orElseThrow(() -> new RuntimeException("Interview not found"));

        Question question = new Question(
                request.getQuestionText(),
                request.getAnswer(),
                interview
        );

        return questionRepository.save(question);
    }

    @PutMapping("/{id}")
    public Question updateQuestion(
            @PathVariable Long id,
            @RequestBody QuestionRequest request) {

        Question question = questionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Question not found"));

        question.setQuestionText(request.getQuestionText());
        question.setAnswer(request.getAnswer());

        return questionRepository.save(question);
    }

    @DeleteMapping("/{id}")
    public String deleteQuestion(@PathVariable Long id) {

        questionRepository.deleteById(id);

        return "Question deleted successfully";
    }
}
