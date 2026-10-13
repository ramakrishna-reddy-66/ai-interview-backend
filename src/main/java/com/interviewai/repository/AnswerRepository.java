package com.interviewai.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.interviewai.entity.Answer;
import com.interviewai.entity.Question;

public interface AnswerRepository extends JpaRepository<Answer, Long> {

    List<Answer> findByQuestion(Question question);

    List<Answer> findByQuestion_Interview_Id(Long interviewId);
}
