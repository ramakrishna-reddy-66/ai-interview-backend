package com.interviewai.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Question {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @jakarta.persistence.Lob
    @jakarta.persistence.Column(columnDefinition = "TEXT")
    private String questionText;

    @jakarta.persistence.Lob
    @jakarta.persistence.Column(columnDefinition = "TEXT")
    private String answer;

    @ManyToOne
    @JoinColumn(name = "interview_id")
    private Interview interview;

    public Question() {
    }

    public Question(String questionText, String answer, Interview interview) {
        this.questionText = questionText;
        this.answer = answer;
        this.interview = interview;
    }

    public Long getId() {
        return id;
    }

    public String getQuestionText() {
        return questionText;
    }

    public void setQuestionText(String questionText) {
        this.questionText = questionText;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public Interview getInterview() {
        return interview;
    }

    public void setInterview(Interview interview) {
        this.interview = interview;
    }
}
