package com.interviewai.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.interviewai.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);
}
