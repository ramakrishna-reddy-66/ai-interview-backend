package com.interviewai.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.interviewai.dto.AuthResponse;
import com.interviewai.dto.LoginRequest;
import com.interviewai.dto.RegisterRequest;
import com.interviewai.dto.UserResponse;
import com.interviewai.entity.User;
import com.interviewai.service.AuthService;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public AuthResponse register(@RequestBody RegisterRequest request) {

        User user = authService.register(request);

        return new AuthResponse(
                user.getId(),
                user.getName(),
                user.getEmail(),
                "iai_reg_" + user.getId(),
                "User registered successfully"
        );
    }

    @PostMapping("/login")
    public AuthResponse login(@RequestBody LoginRequest request) {
        return authService.login(request);
    }

    @GetMapping("/user")
    public UserResponse getUser(@RequestParam(required = false) Long id, @RequestParam(required = false) String email) {
        User user = null;
        if (id != null) {
            user = authService.findById(id);
        } else if (email != null) {
            user = authService.findByEmail(email);
        }
        if (user == null) {
            throw new RuntimeException("User not found");
        }
        return new UserResponse(user.getId(), user.getName(), user.getEmail());
    }
}
