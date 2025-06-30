package com.learnbydoing.controller;

import com.learnbydoing.dto.LoginRequest;
import com.learnbydoing.dto.OtpRequest;
import com.learnbydoing.dto.RegisterRequest;
import com.learnbydoing.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;

    // ====================
    // 1. REGISTER
    // ====================
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest request) {
        userService.register(request);
        return ResponseEntity.ok("Register berhasil");
    }

    // ====================
    // 2. LOGIN (Email + Password)
    // ====================
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        String token = userService.login(request);
        return ResponseEntity.ok(Map.of("token", token));
    }

    // ====================
    // 3. VERIFIKASI OTP
    // ====================
    @PostMapping("/verify-otp")
    public ResponseEntity<?> verifyOtp(@RequestBody OtpRequest request) {
        userService.verifyOtp(request);
        return ResponseEntity.ok("Login berhasil");
    }

    @PostMapping("/verify-register-otp")
    public ResponseEntity<?> verifyRegisterOtp(@RequestBody OtpRequest request) {
        userService.verifyRegisterOtp(request);
        return ResponseEntity.ok("Akun berhasil diverifikasi");
    }

}
