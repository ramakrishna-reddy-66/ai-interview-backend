package com.interviewai.dto;

import java.util.List;

public class DetailedReportResponse {

    private Long interviewId;
    private String interviewTitle;
    private String interviewDescription;
    private int totalScore;
    private int maxScore;
    private double percentage;
    private String performance;
    private int totalQuestions;
    private int answeredQuestions;
    private String summaryFeedback;
    private List<ReportItem> items;

    public static class ReportItem {
        private Long questionId;
        private String questionText;
        private String candidateAnswer;
        private String modelAnswer;
        private Integer score;
        private String feedback;

        public ReportItem() {
        }

        public ReportItem(Long questionId, String questionText, String candidateAnswer, String modelAnswer, Integer score, String feedback) {
            this.questionId = questionId;
            this.questionText = questionText;
            this.candidateAnswer = candidateAnswer;
            this.modelAnswer = modelAnswer;
            this.score = score;
            this.feedback = feedback;
        }

        public Long getQuestionId() {
            return questionId;
        }

        public void setQuestionId(Long questionId) {
            this.questionId = questionId;
        }

        public String getQuestionText() {
            return questionText;
        }

        public void setQuestionText(String questionText) {
            this.questionText = questionText;
        }

        public String getCandidateAnswer() {
            return candidateAnswer;
        }

        public void setCandidateAnswer(String candidateAnswer) {
            this.candidateAnswer = candidateAnswer;
        }

        public String getModelAnswer() {
            return modelAnswer;
        }

        public void setModelAnswer(String modelAnswer) {
            this.modelAnswer = modelAnswer;
        }

        public Integer getScore() {
            return score;
        }

        public void setScore(Integer score) {
            this.score = score;
        }

        public String getFeedback() {
            return feedback;
        }

        public void setFeedback(String feedback) {
            this.feedback = feedback;
        }
    }

    public DetailedReportResponse() {
    }

    public Long getInterviewId() {
        return interviewId;
    }

    public void setInterviewId(Long interviewId) {
        this.interviewId = interviewId;
    }

    public String getInterviewTitle() {
        return interviewTitle;
    }

    public void setInterviewTitle(String interviewTitle) {
        this.interviewTitle = interviewTitle;
    }

    public String getInterviewDescription() {
        return interviewDescription;
    }

    public void setInterviewDescription(String interviewDescription) {
        this.interviewDescription = interviewDescription;
    }

    public int getTotalScore() {
        return totalScore;
    }

    public void setTotalScore(int totalScore) {
        this.totalScore = totalScore;
    }

    public int getMaxScore() {
        return maxScore;
    }

    public void setMaxScore(int maxScore) {
        this.maxScore = maxScore;
    }

    public double getPercentage() {
        return percentage;
    }

    public void setPercentage(double percentage) {
        this.percentage = percentage;
    }

    public String getPerformance() {
        return performance;
    }

    public void setPerformance(String performance) {
        this.performance = performance;
    }

    public int getTotalQuestions() {
        return totalQuestions;
    }

    public void setTotalQuestions(int totalQuestions) {
        this.totalQuestions = totalQuestions;
    }

    public int getAnsweredQuestions() {
        return answeredQuestions;
    }

    public void setAnsweredQuestions(int answeredQuestions) {
        this.answeredQuestions = answeredQuestions;
    }

    public String getSummaryFeedback() {
        return summaryFeedback;
    }

    public void setSummaryFeedback(String summaryFeedback) {
        this.summaryFeedback = summaryFeedback;
    }

    public List<ReportItem> getItems() {
        return items;
    }

    public void setItems(List<ReportItem> items) {
        this.items = items;
    }
}
