package com.harkesh.letterbox.controller;

import com.harkesh.letterbox.dto.JwtResponse;
import com.harkesh.letterbox.dto.LoginRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.harkesh.letterbox.service.AuthService;

@RestController
public class AuthController {
    private final AuthService authService;

    @Autowired
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/auth/login")
    public JwtResponse login(@RequestBody LoginRequest loginRequest) {
        return authService.login(loginRequest);
    }
}
