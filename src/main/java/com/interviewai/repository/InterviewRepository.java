package com.interviewai.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.interviewai.entity.Interview;

public interface InterviewRepository extends JpaRepository<Interview, Long> {

}
