package com.interviewai.service;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.Mockito;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.interviewai.dto.AuthResponse;
import com.interviewai.dto.LoginRequest;
import com.interviewai.dto.RegisterRequest;
import com.interviewai.entity.User;
import com.interviewai.repository.UserRepository;

class AuthServiceTest {

    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;
    private AuthService authService;

    @BeforeEach
    void setUp() {
        userRepository = mock(UserRepository.class);
        passwordEncoder = new BCryptPasswordEncoder();
        authService = new AuthService(userRepository, passwordEncoder);
    }

    @Test
    void testRegisterSuccess() {
        RegisterRequest req = new RegisterRequest();
        req.setName("Test User");
        req.setEmail("test@example.com");
        req.setPassword("Secret123!");

        when(userRepository.findByEmail("test@example.com")).thenReturn(Optional.empty());
        when(userRepository.save(any(User.class))).thenAnswer(invocation -> invocation.getArgument(0));

        User user = authService.register(req);
        assertNotNull(user);
        assertEquals("Test User", user.getName());
        assertEquals("test@example.com", user.getEmail());
        verify(userRepository).save(any(User.class));
    }

    @Test
    void testRegisterDuplicateEmailThrows() {
        RegisterRequest req = new RegisterRequest();
        req.setEmail("existing@example.com");
        req.setPassword("Secret123!");

        when(userRepository.findByEmail("existing@example.com")).thenReturn(Optional.of(new User("Old", "existing@example.com", "hash")));

        assertThrows(RuntimeException.class, () -> authService.register(req));
    }

    @Test
    void testLoginSuccess() {
        String encoded = passwordEncoder.encode("Secret123!");
        User user = new User("Valid User", "user@example.com", encoded);

        when(userRepository.findByEmail("user@example.com")).thenReturn(Optional.of(user));

        LoginRequest req = new LoginRequest();
        req.setEmail("user@example.com");
        req.setPassword("Secret123!");

        AuthResponse res = authService.login(req);
        assertNotNull(res);
        assertEquals("Valid User", res.getName());
        assertNotNull(res.getToken());
    }

    @Test
    void testLoginInvalidPasswordThrows() {
        String encoded = passwordEncoder.encode("Secret123!");
        User user = new User("Valid User", "user@example.com", encoded);

        when(userRepository.findByEmail("user@example.com")).thenReturn(Optional.of(user));

        LoginRequest req = new LoginRequest();
        req.setEmail("user@example.com");
        req.setPassword("WrongPassword");

        assertThrows(RuntimeException.class, () -> authService.login(req));
    }
}
