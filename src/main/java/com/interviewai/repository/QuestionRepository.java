
package com.interviewai.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.interviewai.entity.Interview;
import com.interviewai.entity.Question;

public interface QuestionRepository extends JpaRepository<Question, Long> {

    List<Question> findByInterview(Interview interview);

    List<Question> findByInterview_Id(Long interviewId);
}

