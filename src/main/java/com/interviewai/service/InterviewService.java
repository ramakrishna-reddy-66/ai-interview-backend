package com.interviewai.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.interviewai.dto.InterviewRequest;
import com.interviewai.entity.Interview;
import com.interviewai.repository.InterviewRepository;

@Service
public class InterviewService {


private final InterviewRepository interviewRepository;

public InterviewService(InterviewRepository interviewRepository) {
    this.interviewRepository = interviewRepository;
}

public List<Interview> getAllInterviews() {
    return interviewRepository.findAll();
}

public Interview getInterviewById(Long id) {
    return interviewRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Interview not found"));
}

public Interview createInterview(InterviewRequest request) {
    Interview interview = new Interview(
            request.getTitle(),
            request.getDescription()
    );

    return interviewRepository.save(interview);
}

public Interview updateInterview(Long id, InterviewRequest request) {
    Interview interview = interviewRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Interview not found"));

    interview.setTitle(request.getTitle());
    interview.setDescription(request.getDescription());

    return interviewRepository.save(interview);
}

public void deleteInterview(Long id) {
    interviewRepository.deleteById(id);
}


}
