package com.inventory.ims.controller;

import com.inventory.ims.dto.ApiResponse;
import com.inventory.ims.dto.LoginRequest;
import com.inventory.ims.dto.LoginResponse;
import com.inventory.ims.dto.RegisterRequest;
import com.inventory.ims.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<ApiResponse> register(@Valid @RequestBody RegisterRequest request) {
        try {
            String message = authService.register(request);
            return ResponseEntity.ok(new ApiResponse(true, message));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ApiResponse(false, e.getMessage()));
        }
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse> login(@Valid @RequestBody LoginRequest request) {
        try {
            LoginResponse loginResponse = authService.login(request);
            return ResponseEntity.ok(new ApiResponse(true, "Login successful", loginResponse));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ApiResponse(false, e.getMessage()));
        }
    }
}