package com.interviewai.service;

import java.util.UUID;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.interviewai.dto.AuthResponse;
import com.interviewai.dto.LoginRequest;
import com.interviewai.dto.RegisterRequest;
import com.interviewai.entity.User;
import com.interviewai.repository.UserRepository;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthService(
            UserRepository userRepository,
            PasswordEncoder passwordEncoder) {

        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User register(RegisterRequest request) {

        if (request.getEmail() == null || request.getEmail().trim().isEmpty()) {
            throw new RuntimeException("Email cannot be empty");
        }
        if (request.getPassword() == null || request.getPassword().trim().isEmpty()) {
            throw new RuntimeException("Password cannot be empty");
        }

        if (userRepository.findByEmail(request.getEmail().trim().toLowerCase()).isPresent()) {
            throw new RuntimeException("Email already registered");
        }

        String encodedPassword =
                passwordEncoder.encode(request.getPassword());

        User user = new User(
                request.getName() != null && !request.getName().trim().isEmpty() ? request.getName().trim() : "Candidate",
                request.getEmail().trim().toLowerCase(),
                encodedPassword
        );

        return userRepository.save(user);
    }

    public AuthResponse login(LoginRequest request) {

        if (request.getEmail() == null || request.getPassword() == null) {
            throw new RuntimeException("Email and password are required");
        }

        User user = userRepository.findByEmail(request.getEmail().trim().toLowerCase())
                .orElseThrow(() ->
                        new RuntimeException("Invalid email or password"));

        if (!passwordEncoder.matches(
                request.getPassword(),
                user.getPassword())) {

            throw new RuntimeException("Invalid email or password");
        }

        String simulatedToken = "iai_" + UUID.randomUUID().toString().replace("-", "");

        return new AuthResponse(
                user.getId(),
                user.getName(),
                user.getEmail(),
                simulatedToken,
                "Login successful"
        );
    }

    public User findByEmail(String email) {
        return userRepository.findByEmail(email).orElse(null);
    }

    public User findById(Long id) {
        return userRepository.findById(id).orElse(null);
    }
}
