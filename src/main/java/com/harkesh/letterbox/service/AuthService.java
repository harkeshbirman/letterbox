package com.harkesh.letterbox.service;

import com.harkesh.letterbox.dto.JwtResponse;
import com.harkesh.letterbox.dto.LoginRequest;
import com.harkesh.letterbox.jwt.JwtAuthenticationHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    private final JwtAuthenticationHelper authenticationHelper;
    private final AuthenticationManager authenticationManager;
    private final UserDetailsService userDetailsService;

    @Autowired
    public AuthService(
        JwtAuthenticationHelper authenticationHelper,
        AuthenticationManager authenticationManager,
        UserDetailsService userDetailsService
    ) throws ClassNotFoundException {
        this.authenticationHelper = authenticationHelper;
        this.authenticationManager = authenticationManager;
        this.userDetailsService = userDetailsService;
    }

    public JwtResponse login(LoginRequest loginRequest) {
        this.doAuthenticate(loginRequest.getUsername(), loginRequest.getPassword());

        UserDetails userDetails = userDetailsService.loadUserByUsername(loginRequest.getUsername());
        String jwtToken = authenticationHelper.generateToken(userDetails);

        return JwtResponse.builder().jwtToken(jwtToken).build();
    }

    private void doAuthenticate(String username, String password) {
        UsernamePasswordAuthenticationToken authenticationToken =
            new UsernamePasswordAuthenticationToken(username, password);

        try {
            authenticationManager.authenticate(authenticationToken);
        }catch (BadCredentialsException e) {
            throw new BadCredentialsException("Invalid Username or Password");

        }
    }
}
