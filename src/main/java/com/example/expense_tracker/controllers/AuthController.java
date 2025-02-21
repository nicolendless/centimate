package com.example.expense_tracker.controllers;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.expense_tracker.dtos.LoginRequestDto;
import com.example.expense_tracker.dtos.LoginResponseDto;
import com.example.expense_tracker.dtos.SignUpRequestDto;
import com.example.expense_tracker.entities.User;
import com.example.expense_tracker.security.JwtUtil;
import com.example.expense_tracker.services.AuthService;

@RestController
@RequestMapping("api/v1/auth")
public class AuthController {

    private final AuthService authService;
    private final JwtUtil jwtUtil;

    public AuthController(AuthService authService, JwtUtil jwtUtil) {
        this.authService = authService;
        this.jwtUtil = jwtUtil;
    }

    /**
     * Handles user signup.
     *
     * @param request The signup request containing username and password.
     * @return A JSON response with a success message or an error message.
    */
    @PostMapping("/signup")
    public ResponseEntity<?> signup (@RequestBody SignUpRequestDto request) {
        try {
            authService.registerUser(request);
            return ResponseEntity.ok().body(
                Map.of("message", "User registered successfully")
            );
        } catch (IllegalStateException e) {
            return ResponseEntity.badRequest().body(
                Map.of("message", e.getMessage())
            );
        }
    }

    /**
     * Handles user login.
     *
     * @param request The login request containing username and password.
     * @return A JSON response with a success message or an error message.
    */
    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> authenticate(@RequestBody LoginRequestDto request) {
        User authenticatedUser = authService.authenticatUser(request);

        String jwtToken = jwtUtil.generateToken(authenticatedUser.getUsername());
        LoginResponseDto loginResponse = new LoginResponseDto();
        loginResponse.setToken(jwtToken);
        loginResponse.setExpiresIn(jwtUtil.getExpirationTime());
        
        return ResponseEntity.ok(loginResponse);
    }
}
