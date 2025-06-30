package com.learnbydoing.service;

import com.learnbydoing.dto.LoginRequest;
import com.learnbydoing.dto.OtpRequest;
import com.learnbydoing.dto.RegisterRequest;
import com.learnbydoing.entity.User;
import com.learnbydoing.repository.UserRepository;
import com.learnbydoing.security.JwtUtil;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Random;

@Service
@RequiredArgsConstructor

public class UserService {

    private final UserRepository userRepository;
    private final JavaMailSender mailSender;
    private final JwtUtil jwtUtil;

    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    @Transactional
    public void register(RegisterRequest request) {
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new RuntimeException("Email sudah terdaftar");
        }

        String otp = String.valueOf(new Random().nextInt(900000) + 100000);

        User user = new User();
        user.setEmail(request.getEmail());
        user.setPassword(encoder.encode(request.getPassword()));
        user.setOtp(otp);
        user.setVerified(false); // belum aktif

        userRepository.save(user);
        sendOtp(request.getEmail(), otp);
    }

    @Transactional
    public String login(LoginRequest request) {
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("Email tidak ditemukan"));

        if (!encoder.matches(request.getPassword(), user.getPassword())) {
            throw new RuntimeException("Password salah");
        }

        if (!user.isVerified()) {
            throw new RuntimeException("Email belum diverifikasi");
        }

        return jwtUtil.generateToken(user.getEmail());

    }

    @Transactional
    public void verifyOtp(OtpRequest request) {
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("Email tidak ditemukan"));

        if (!request.getOtp().equals(user.getOtp())) {
            throw new RuntimeException("OTP salah");
        }

        user.setOtp(null);
        user.setVerified(true);
        userRepository.save(user);
    }

    @Transactional
    public void verifyRegisterOtp(OtpRequest request) {
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("User tidak ditemukan"));

        if (!request.getOtp().equals(user.getOtp())) {
            throw new RuntimeException("OTP salah");
        }

        user.setOtp(null);
        user.setVerified(true); // aktifkan akun
        userRepository.save(user);
    }

    private void sendOtp(String email, String otp) {
        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setTo(email);
        msg.setSubject("kode OTP Login Anda");
        msg.setText("OTP Anda adalah: " + otp);
        mailSender.send(msg);
    }

}