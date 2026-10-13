package com.interviewai.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.interviewai.dto.DetailedReportResponse;
import com.interviewai.dto.InterviewRequest;
import com.interviewai.entity.Answer;
import com.interviewai.entity.Interview;
import com.interviewai.entity.Question;
import com.interviewai.repository.AnswerRepository;
import com.interviewai.repository.InterviewRepository;
import com.interviewai.repository.QuestionRepository;
import com.interviewai.service.AIService;
import com.interviewai.service.InterviewService;

@RestController
@RequestMapping("/api/interviews")
public class InterviewController {

    private final InterviewService interviewService;
    private final AIService aiService;
    private final QuestionRepository questionRepository;
    private final AnswerRepository answerRepository;
    private final InterviewRepository interviewRepository;

    public InterviewController(
            InterviewService interviewService,
            AIService aiService,
            QuestionRepository questionRepository,
            AnswerRepository answerRepository,
            InterviewRepository interviewRepository) {

        this.interviewService = interviewService;
        this.aiService = aiService;
        this.questionRepository = questionRepository;
        this.answerRepository = answerRepository;
        this.interviewRepository = interviewRepository;
    }

    @GetMapping
    public List<Interview> getInterviews() {
        List<Interview> list = interviewService.getAllInterviews();
        if (list.isEmpty()) {
            seedDefaultInterviews();
            list = interviewService.getAllInterviews();
        }
        return list;
    }

    @PostMapping
    public Interview createInterview(@RequestBody InterviewRequest request) {
        return interviewService.createInterview(request);
    }

    @PutMapping("/{id}")
    public Interview updateInterview(@PathVariable Long id, @RequestBody InterviewRequest request) {
        return interviewService.updateInterview(id, request);
    }

    @DeleteMapping("/{id}")
    public String deleteInterview(@PathVariable Long id) {
        List<Question> questions = questionRepository.findByInterview_Id(id);
        for (Question q : questions) {
            List<Answer> answers = answerRepository.findByQuestion(q);
            answerRepository.deleteAll(answers);
        }
        questionRepository.deleteAll(questions);
        interviewService.deleteInterview(id);
        return "Interview deleted successfully";
    }

    @GetMapping("/{id}")
    public Interview getInterview(@PathVariable Long id) {
        return interviewService.getInterviewById(id);
    }

    @GetMapping("/{id}/questions")
    public List<Question> getInterviewQuestions(@PathVariable Long id) {
        return questionRepository.findByInterview_Id(id);
    }

    @GetMapping("/{id}/start")
    public Question startInterview(@PathVariable Long id) {
        Interview interview = interviewService.getInterviewById(id);
        List<Question> existingQuestions = questionRepository.findByInterview(interview);

        if (!existingQuestions.isEmpty()) {
            return existingQuestions.get(0);
        }

        String questionText = aiService.generateQuestion(interview.getTitle(), 0);
        String modelAnswer = aiService.generateAnswer(interview.getTitle(), questionText);

        Question question = new Question();
        question.setQuestionText(questionText);
        question.setAnswer(modelAnswer);
        question.setInterview(interview);

        return questionRepository.save(question);
    }

    @GetMapping("/{id}/next-question")
    public Question nextQuestion(@PathVariable Long id) {
        Interview interview = interviewService.getInterviewById(id);
        List<Question> existingQuestions = questionRepository.findByInterview(interview);

        int questionNumber = existingQuestions.size();
        String questionText = aiService.generateQuestion(interview.getTitle(), questionNumber);
        String modelAnswer = aiService.generateAnswer(interview.getTitle(), questionText);

        Question question = new Question();
        question.setQuestionText(questionText);
        question.setAnswer(modelAnswer);
        question.setInterview(interview);

        return questionRepository.save(question);
    }

    @PostMapping("/{id}/reset")
    public String resetInterview(@PathVariable Long id) {
        List<Answer> answers = answerRepository.findByQuestion_Interview_Id(id);
        answerRepository.deleteAll(answers);
        return "Interview session reset successfully";
    }

    @GetMapping("/{id}/score")
    public String getInterviewScore(@PathVariable Long id) {
        Interview interview = interviewService.getInterviewById(id);
        List<Answer> answers = answerRepository.findByQuestion_Interview_Id(interview.getId());

        int totalScore = 0;
        int answeredQuestions = 0;

        for (Answer answer : answers) {
            if (answer.getScore() != null) {
                totalScore += answer.getScore();
                answeredQuestions++;
            }
        }

        if (answeredQuestions == 0) {
            return "No answers submitted yet.";
        }

        int maximumScore = answeredQuestions * 10;
        return "Interview Score: " + totalScore + "/" + maximumScore;
    }

    @GetMapping("/{id}/detailed-report")
    public DetailedReportResponse getDetailedReport(@PathVariable Long id) {
        Interview interview = interviewService.getInterviewById(id);
        List<Question> questions = questionRepository.findByInterview_Id(id);
        List<Answer> answers = answerRepository.findByQuestion_Interview_Id(id);

        DetailedReportResponse report = new DetailedReportResponse();
        report.setInterviewId(interview.getId());
        report.setInterviewTitle(interview.getTitle());
        report.setInterviewDescription(interview.getDescription());
        report.setTotalQuestions(questions.size());

        List<DetailedReportResponse.ReportItem> items = new ArrayList<>();
        int totalScore = 0;
        int answeredCount = 0;

        for (Question q : questions) {
            Answer ans = answers.stream()
                    .filter(a -> a.getQuestion() != null && a.getQuestion().getId().equals(q.getId()))
                    .findFirst()
                    .orElse(null);

            String candidateAns = ans != null ? ans.getAnswerText() : "Not answered";
            Integer score = ans != null ? ans.getScore() : 0;
            String feedback = ans != null ? ans.getFeedback() : "No response provided.";

            if (ans != null && ans.getScore() != null) {
                totalScore += ans.getScore();
                answeredCount++;
            }

            items.add(new DetailedReportResponse.ReportItem(
                    q.getId(),
                    q.getQuestionText(),
                    candidateAns,
                    q.getAnswer() != null ? q.getAnswer() : aiService.generateAnswer(interview.getTitle(), q.getQuestionText()),
                    score,
                    feedback
            ));
        }

        report.setItems(items);
        report.setAnsweredQuestions(answeredCount);
        report.setTotalScore(totalScore);

        int maxScore = Math.max(1, (answeredCount > 0 ? answeredCount : questions.size())) * 10;
        report.setMaxScore(maxScore);

        double percentage = ((double) totalScore / maxScore) * 100.0;
        report.setPercentage(Math.round(percentage * 10.0) / 10.0);

        String performance;
        String summaryFeedback;

        if (percentage >= 85) {
            performance = "Excellent (Senior Level)";
            summaryFeedback = "Outstanding technical competency demonstrated. Strong command of core architecture, trade-offs, and practical design principles.";
        } else if (percentage >= 70) {
            performance = "Good (Proficient)";
            summaryFeedback = "Solid knowledge and clear explanations. Focus on including more edge-case handling and in-depth performance optimizations.";
        } else if (percentage >= 50) {
            performance = "Average (Intermediate)";
            summaryFeedback = "Good conceptual foundation. Practice articulating answers with more code syntax, structured definitions, and practical examples.";
        } else {
            performance = "Needs Improvement (Junior / Developing)";
            summaryFeedback = "Review fundamental core concepts, revise technical terminology, and practice mock technical interviews regularly.";
        }

        report.setPerformance(performance);
        report.setSummaryFeedback(summaryFeedback);

        return report;
    }

    @GetMapping("/{id}/report")
    public String getInterviewReport(@PathVariable Long id) {
        DetailedReportResponse report = getDetailedReport(id);

        StringBuilder sb = new StringBuilder();
        sb.append("=========================================\n");
        sb.append("        INTERVIEW EVALUATION REPORT      \n");
        sb.append("=========================================\n\n");
        sb.append("Role / Topic: ").append(report.getInterviewTitle()).append("\n");
        if (report.getInterviewDescription() != null) {
            sb.append("Description: ").append(report.getInterviewDescription()).append("\n");
        }
        sb.append("Questions Answered: ").append(report.getAnsweredQuestions()).append("/").append(report.getTotalQuestions()).append("\n");
        sb.append("Total Score: ").append(report.getTotalScore()).append("/").append(report.getMaxScore()).append(" (").append(report.getPercentage()).append("%)\n");
        sb.append("Overall Rating: ").append(report.getPerformance()).append("\n\n");
        sb.append("SUMMARY EVALUATION:\n").append(report.getSummaryFeedback()).append("\n\n");
        sb.append("-----------------------------------------\n");
        sb.append("QUESTION-BY-QUESTION BREAKDOWN:\n");
        sb.append("-----------------------------------------\n\n");

        int i = 1;
        for (DetailedReportResponse.ReportItem item : report.getItems()) {
            sb.append("Q").append(i++).append(": ").append(item.getQuestionText()).append("\n");
            sb.append("Candidate Answer: ").append(item.getCandidateAnswer()).append("\n");
            sb.append("Score: ").append(item.getScore()).append("/10\n");
            sb.append("AI Feedback: ").append(item.getFeedback()).append("\n");
            sb.append("\n");
        }
        sb.append("=========================================\n");

        return sb.toString();
    }

    @PostMapping("/seed")
    public String seedInterviews() {
        seedDefaultInterviews();
        return "Default interview tracks initialized successfully.";
    }

    private void seedDefaultInterviews() {
        if (interviewRepository.count() == 0) {
            interviewRepository.save(new Interview("Java Full-Stack Developer", "Comprehensive technical interview covering Core Java, OOP principles, Spring Boot REST APIs, and JPA."));
            interviewRepository.save(new Interview("Python & Backend Engineer", "Deep-dive technical assessment covering Python internals, data structures, functions, OOP, and memory management."));
            interviewRepository.save(new Interview("React & Modern Frontend Specialist", "Evaluation on React hooks, Virtual DOM, state management, props vs state, and modern UI architectures."));
            interviewRepository.save(new Interview("Database & SQL Architect", "Assessment covering SQL joins, aggregation, subqueries, normalization, and ACID transaction guarantees."));
            interviewRepository.save(new Interview("System Design & Distributed Systems", "High-level architecture interview evaluating load balancing, caching strategies, CAP theorem, and scalability."));
            interviewRepository.save(new Interview("HR & Behavioral Leadership Interview", "Behavioral interview exploring communication, teamwork, conflict resolution, and leadership skills."));
        }
    }
}